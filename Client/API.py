#!/usr/bin/python3

class API:
    def __init__(self, client):
        self.idIt = 0
        self.client = client
        self.callBacks = []
        self.getMethods = [
            (401, self.getConnectionFailed),
            (200, self.missingRedirect),
        ]

    def genId(self):
        self.idIt += 1
        return self.idIt

    def newCallBack(self, id, callBack):
        self.callBacks.append((id, callBack))

    def send(self, msg): # OUT
        if self.client.running is True:
            # print('send: '+msg)
            self.client.socket.sendall((msg+'\n').encode('utf-8'))

    def parse(self, msg): # IN
        msg = msg.split('#')
        code = None
        id = None

        if '@' in msg[0]:
            msg_ = msg[0].split('@')
            code = int(msg_[0])
            id = int(msg_[1])
        else:
            code = int(msg[0])

        msg = '#'.join(msg[1:])
        callBacks = [c for c in self.callBacks if c[0] == id]
        if len(callBacks) != 0:
            for callBack in callBacks:
                callBack[1](code, msg)
                self.callBacks.remove(callBack)
        else:
            getMethods = [m for m in self.getMethods if m[0] == code]
            if len(getMethods) != 0:
                for (c, m) in getMethods:
                    m(code, msg)
            else:
                print('no callback for code: '+str(code)) # debug. must be removed.

# I Connection

    def connect(self, callBack): # OUT
        pass

    def end(self): # OUT (no CB)
        i = self.genId()
        self.newCallBack(i, defaultCallBack)
        self.send(str(i)+'#connection: end')
        pass

# II get information

    def getConnectionFailed(self, code, msg): # GET 401
        self.client.close()
        pass

    def missingRedirect(self, code, msg):
        self.debug('missing redirection for code '+str(code))
        

# III do something

    def rollDice(self, callBack): # OUT
        pass

    def exchange(self, callBack): # OUT
        pass

    def exchangeT(self, exchangeId, callBack): # OUT
        pass

    def exchangeF(self, exchangeId, callBack): # OUT
        pass

    def draCard(self, callBack): # OUT
        pass

    def passTurn(self, callBack): # OUT
        pass

    def buyProperty(self, callBack): # OUT
        pass

    def mortgage(self, callBack): # OUT
        pass

    def unmortgage(self, callBack): # OUT
        pass

    def sendMsg(self, msg, callBack): # OUT
        i = self.genId()
        self.newCallBack(i, defaultCallBack)
        self.send(str(i)+'#'+msg)

# IV manage party

    def createParty(self, callBack): # OUT
        pass

    def destroyParty(self, callBack): # OUT
        pass

    def joinParty(self, callBack): # OUT
        pass

    def inviteInParty(self, callBack): # OUT
        pass

# V Errors
    def debug(self, msg): # OUT (no CB)
        self.send('debug: '+msg)
        pass

def defaultCallBack(code, msg):
    if code != 200:
        print(code+': '+msg)
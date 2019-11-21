#!/usr/bin/python3

import threading
import socket
import sys

class Client:
  def __init__(self, socket):
    self.socket = socket
    self.running = True

  def recv(self):
    try:
      line = self.socket.recv(512)
      if line is not None:
        return line.decode('ascii')
    except:
      if self.running == True:
        print('server error')
    return None

  def start(self):
    self.reader = threading.Thread(target=listenServer, args=(c,))
    self.reader.start()
    self.getInput()

  def getInput(self):
    try:
      line = self.prompt()
      while line is not None and self.running == True:
        self.socket.sendall((line+'\n').encode('utf-8'))
        if line == "end":
          break
        line = self.prompt()
    except EOFError: # never call when ctrl+D. bug ?
      print('EOFException')
      self.socket.sendall(('end\n').encode('utf-8'))
    finally:
      self.close()
      self.join()

  def close(self):
    if self.running is True:
      print('closing socket')
      self.running = False
      self.socket.close()

  def join(self):
    self.reader.join()

  def prompt(self):
    try:
      return input("> ")
    except:
      return None



def listenServer(client):
  print('start listen server')
  try:
    line = client.recv()
    while line is not None:
      if line == 'end connection\n' or line[0:3] == '401': # API get end of connection
        client.close()
        break
      print('\nrecv: "'+line+'"> ',end='')
      line = client.recv()
  finally:
    print('end of connection')



if __name__ == "__main__":
  # Create a TCP/IP socket
  sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

  # Connect the socket to the port where the server is listening
  server_address = ('localhost', 4242)
  print('connecting to {} port {}'.format(*server_address))
  sock.connect(server_address)

  c = Client(sock)
  c.start()


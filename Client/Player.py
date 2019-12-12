
import pygame

posIcon = [
    (640, 730),
    (561, 750),
    (447, 750),
    (390, 750),
    (333, 750),
    (276, 750),
    (219, 750),
    (162, 750),
    (504, 750),
    (105, 750),
    (30, 730), # 10
    (15, 661),
    (15, 604),
    (15, 547),
    (15, 490),
    (15, 433),
    (15, 376),
    (15, 319),
    (15, 262),
    (15, 205),
    (30, 130), # 20
    (105, 120),
    (162, 120),
    (219, 120),
    (276, 120),
    (333, 120),
    (390, 120),
    (447, 120),
    (504, 120),
    (561, 120),
    (640, 130), # 30
    (652, 205),
    (652, 262),
    (652, 319),
    (652, 376),
    (652, 433),
    (652, 490),
    (652, 547),
    (652, 604),
    (652, 661),
]

i1 = pygame.image.load('./imgs/player_icon.png')
i1 = pygame.transform.scale(i1, (15, 15))
i2 = pygame.image.load('./imgs/player_icon2.png')
i2 = pygame.transform.scale(i2, (15, 15))
i3 = pygame.image.load('./imgs/player_icon3.png')
i3 = pygame.transform.scale(i3, (15, 15))
i4 = pygame.image.load('./imgs/player_icon4.png')
i4 = pygame.transform.scale(i4, (15, 15))


class Player():
    def __init__(self, id, name, icon):
        self.id = id
        self.account = 1500
        self.name = name
        self.property = []
        self.pos = 0
        self.inJail = False
        self.icon = pygame.image.load(icon)
        self.icon = pygame.transform.scale(self.icon, (20, 20))

    def drawIcon(self, screen):
        screen.blit(self.icon, self.getIconPos())

    def getIconPos(self):
        if self.id == 0:
            return (0, 0)
        if self.id == 1:
            return (20, 0)
        if self.id == 2:
            return (0, 20)
        if self.id == 3:
            return (20, 20)
        return None

    def drawInfos(self):
        pass

    def draw(self, screen):
        # self.drawIcon()
        # self.drawInfos()
        for p in posIcon:
            screen.blit(i1, (p[0], p[1]))
            screen.blit(i2, (p[0]+15, p[1]))
            screen.blit(i3, (p[0], p[1]+15))
            screen.blit(i4, (p[0]+15, p[1]+15))


# nom
# account$
# list
# 1100
# 
# 
# 
# 
# 
# 
# 

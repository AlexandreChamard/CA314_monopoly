#!/usr/bin/python3

import pygame
import random

# class Button(pygame.TextSprite):
#     def __init__ (self, message, fontsize, location, color):
#         pygame.TextSprite.__init__(self, message, fontsize, location, color)
#         self.place = location

#     def clicked(self):
#         if self.rect.collidepoint(pygame.mouse.get_pos()) and pygame.mouse.get_pressed()[0] == True:
#             return True
#         else:
#             return False

#     def over(self):
#         if self.rect.collidepoint(pygame.mouse.get_pos()):
#             return True
#         else:
#             return False

class Piece(pygame.ImageSprite):
    def __init__ (self, position):
        pygame.ImageSprite.__init__(self, "./imgs/player_icon.png", position)
        pygame.ImageSprite.__init__(self, "./imgs/player_icon2.png", position)
        pygame.ImageSprite.__init__(self, "./imgs/player_icon3.png", position)
        pygame.ImageSprite.__init__(self, "./imgs/player_icon4.png", position)
        pygame.ImageSprite.__init__(self, "./imgs/player_icon5.png", position)
        pygame.ImageSprite.__init__(self, "./imgs/player_icon6.png", position)

    def move(self, amount, previous):
        self.num = previous + amount
        if self.num > 38:
            self.num = self.num - 39 #40 is max tiles
        return self.num
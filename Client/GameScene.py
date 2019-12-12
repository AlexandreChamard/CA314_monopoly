
import pygame
from Sprite import Sprite as Sprite
from Player import Player as Player


class GameScene():
    def __init__(self, players):
        self.drawables = [
            Sprite('./imgs/board_image.bmp', (0, 100), (700, 700)),
            Player(None, None, './imgs/player_icon3.png')
            ]
        self.players = players

    def update(self, screen):
        for drawable in self.drawables:
            drawable.draw(screen)
        for player in self.players:
            player.drawIcon(screen)

    def onClick(self):
        pass
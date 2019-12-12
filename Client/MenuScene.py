
import pygame
from Sprite import Sprite as Sprite
from Button import Button

class MenuScene():
    def __init__(self, changeScene, newScene):
        buttonStart = Button((1100 / 2) - (128 / 2), (800 / 2) - (58 / 2), 128, 58, "imgs/button.png", lambda: changeScene(newScene))
        self.drawables = [buttonStart]
        self.clickables = [buttonStart]

    def update(self, screen):
        for drawable in self.drawables:
            drawable.draw(screen)

    def onClick(self):
        for clickable in self.clickables:
            clickable.onClick()
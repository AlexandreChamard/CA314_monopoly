
import pygame

class Sprite():
    def __init__(self, url, pos, size=None):
        self.sprite = pygame.image.load('./imgs/board_image.bmp')
        if size is not None:
            self.sprite = pygame.transform.scale(self.sprite, size)
        self.pos = pos

    def draw(self, screen):
        screen.blit(self.sprite, self.pos)
#!/usr/bin/python3

# from classes import *
import random
from Player import Player as Player
from GameScene import GameScene as GameScene
from MenuScene import MenuScene as MenuScene
# from pg_helpers import *
import pygame, random, sys
# import MSDie


white = (255, 255, 255)
black = (0, 0, 0)
green = (0, 255, 0)
blue = (0, 0, 128)


pygame.init()

pygame.display.set_caption("GRP2 - Monopoly")

font = pygame.font.Font('./imgs/Roboto-Black.ttf', 32)

size=(1100,800)
screen = pygame.display.set_mode(size, 0, 32)
# background = pygame.Surface(size)
background = pygame.image.load('./imgs/board_image.bmp')
player = pygame.image.load('./imgs/player_icon.png')

pygame.mouse.set_visible(True)

clock = pygame.time.Clock()

dice1 = "6"
dice2 = "hello la mif"

# roll = Button("Roll", 50, [115,80], (0,0,0))
textdice1 = font.render(dice1, True, black)
textdice2 = font.render(dice2, True, black)
# textdice1 = pygame.sprite.text(str(dice1), 50, [100, 120], (0,0,0))
# textdice2 = TextSprite(str(dice2), 50, [100, 160], (0,0,0))
# gamepiece = Piece([start_x,start_y])

# drawgrp = pygame.sprite.OrderedUpdates()
# drawgrp.add(roll)
# drawgrp.add(textdice1)
# drawgrp.add(textdice2)
# gamepiece.scale([30, 15])

spaces = [(700,600), (620,630), (560,630), (500,630), (430,630), (370,630), (310,630), (240,630), (180,630), (120,630), (40,655), (30,550), (30,500), (30,440), (30,380), (30,330), (30,270), (30,210), (30,160), (30,100), (30,40), (120,30), (180,30), (240,30), (310,30), (370,30), (430,30), (500,30), (560,30), (620,30), (700,30), (700,100), (700,160), (700,210), (700,270), (700,330), (700,380), (700,440), (700,500), (700,550)]
start_x = 700
start_y = 600
start_location = [start_x, start_y]
join_spot = [([start_x, start_y]), ([start_x, start_y+20]) ,([start_x + 20, start_y]) ,([start_x + 20, start_y+20]) ,([start_x + 40, start_y]) ,([start_x + 40, start_y+20])]


# dice = MSDie.MSDie(6)

prev = 0

players = [
    Player(0, "player 1", './imgs/player_icon.png'),
    Player(1, "player 2", './imgs/player_icon2.png')
    ]



p1 = pygame.image.load('./imgs/player_icon.png')
p2 = pygame.image.load('./imgs/player_icon2.png')
p3 = pygame.image.load('./imgs/player_icon3.png')
p4 = pygame.image.load('./imgs/player_icon4.png')

def drawGameScene():
    # screen.blit(textdice1, (1000, 300))
    # screen.blit(textdice2, (1000, 400))
    screen.blit(background,(0,0))
    # screen.blit(player,(0,0))
    # for p in players:
    #     p.drawIcon(screen)


gameScene = GameScene(players)


def updateScreen(scene):
    screen.fill(white)
    if (scene is not None):
        scene.update(screen)
    pygame.display.update()

scene = None

def changeScene(newScene):
    global scene
    scene = newScene

scene = MenuScene(changeScene, gameScene)

while True:
    for event in pygame.event.get():
        if event.type == pygame.QUIT or (event.type == pygame.KEYDOWN and event.key == pygame.K_ESCAPE):
            pygame.quit()
            sys.exit()
        # if event.type == pygame.MOUSEBUTTONDOWN and event.button == 1:
        #     scene = drawGameScene
        # else:
        #     scene = None

    updateScreen(scene)
    time_passed = clock.tick(60)

    if pygame.mouse.get_pressed()[0] == True:
        print( pygame.mouse.get_pos())
        scene.onClick()

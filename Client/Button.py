#!/usr/bin/python3

import pygame

class Button:
	def __init__(self, posX, posY, width, height, imagePath, action):
		self.posX = posX
		self.posY = posY
		self.width = width
		self.height = height
		self.sprite = pygame.image.load(imagePath)
		self.sprite = pygame.transform.scale(self.sprite, (width, height))
		self.action = action

	def onClick(self):
		pos = pygame.mouse.get_pos()
		if self.posX <= pos[0] <= self.posX + self.width and self.posY <= pos[1] <= self.posY + self.height:
			# mouse inside button
			self.action()

	def draw(self, screen):
		screen.blit(self.sprite, (self.posX, self.posY))
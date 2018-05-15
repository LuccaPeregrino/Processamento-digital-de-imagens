# -*- coding: utf-8 -*-
"""
Created on Fri May 11 18:49:04 2018

@author: Lucca Peregrino
"""
from PIL import Image
import numpy as np 
import colorsys

im = Image.open("D:/Imagens/fotos akita/GoogleMapsRapida.jpg")
img_rgb = np.ones((im.width, im.height, 3), 'uint8')
img_hsv = np.ones((im.width, im.height, 3), 'float32')
def rgb_hsv(im):
    im_hsv = np.ones((im.width, im.height, 3), 'float32')
    for i in range(im_hsv.shape[1]):
        for j in range(im_hsv.shape[0]):
            Red,Green,Blue = im.getpixel((j,i))
            Red/=255
            Green/=255
            Blue/=255
            
            h,s,v = colorsys.rgb_to_hsv(Red, Green, Blue)
            im_hsv[j,i,0] = h
            im_hsv[j,i,1] = s
            im_hsv[j,i,2] = v
    
    return im_hsv   

##############################################converter de HSV para RGB################################################
def hsv_rgb(im_hsv):
    im_rgb = np.ones((im.width, im.height, 3), 'uint8')
    for i in range(im_hsv.shape[1]):
        for j in range(im_hsv.shape[0]):
            pix = im_hsv[j,i]
            Hue=pix[0]
            Sat=pix[1] 
            Val=pix[2]
            
            r,g,b = colorsys.hsv_to_rgb(Hue, Sat, Val)
            im_rgb[j,i,0]=r*255
            im_rgb[j,i,1]=g*255
            im_rgb[j,i,2]=b*255
    return im_rgb   
################################################área verde###########################################################
img_hsv = rgb_hsv(im)
aux = 0
for i in range(im.height):
        for j in range(im.width):
            if ((img_hsv[j,i,0])*360 >= 60 and (img_hsv[j, i, 0])*360 <= 135 and img_hsv[j,i,1] >= 0.18  and img_hsv[j,i,2] >= 0.05 and img_hsv[j,i,2] <= 0.8):
                img_hsv[j,i,0] = 330/360
                img_hsv[j,i,1] = 0.4
                img_hsv[j,i,2] = 0.7
                aux += 1
                
                
###############################################dados área verde###################################################


######################### info sobre um dos pixels do mar que estava sendo pintado no inicio######################
h,s,v = colorsys.rgb_to_hsv(126/255, 134/255, 113/255)
print("cor: %f   saturação:  %f     valor:  %f   " % ((h*360, s, v)))

#####################################informações que vao ser tiradas da imagem final###############################
percentualAreaVerde = (aux*100)/im.width*im.height
print('Percentua de área verde nessa imagem: %f %%' % ((percentualAreaVerde)))

metrosQuadradosAreaVerde = (aux*2944.1929)/(im.width*im.height)
print('Metros quadrados de área verde nessa imagem: %f' % ((metrosQuadradosAreaVerde)))
###################################################################################################################
img_rgb = hsv_rgb(img_hsv)
img_result = Image.fromarray(img_rgb).transpose(Image.TRANSPOSE)


img_result.show()

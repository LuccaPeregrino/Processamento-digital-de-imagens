import cv2
import numpy as np
import pandas as pd
from sklearn.naive_bayes import GaussianNB
from sklearn.model_selection import train_test_split
from sklearn import metrics


dataframe = pd.read_csv('dataframe.csv')

# Divide o dataset para treino e teste
train_data, test_data = train_test_split(dataframe, test_size = 0.3)

atributes = list(dataframe.columns[:6])
labels = list(dataframe.columns[6:])

gnb = GaussianNB()
gnb.fit(train_data[atributes], np.ravel(train_data[labels]))

# Calculando acurácia no dataset de teste
predictions = gnb.predict(test_data[atributes])
accuracy = metrics.accuracy_score(predictions,test_data[labels])
confusion_matrix = metrics.confusion_matrix(test_data[labels], predictions)
print('Acurácia: {}'.format(accuracy))
print('Matriz: {}'.format(confusion_matrix))

img = cv2.imread('images/image1.png')
hsv = cv2.cvtColor(img, cv2.COLOR_BGR2HSV)
blur3 = cv2.GaussianBlur(hsv, (3,3), 0)
blur5 = cv2.GaussianBlur(hsv, (5,5), 0)
blur7 = cv2.GaussianBlur(hsv, (7,7), 0)
height, width, channels = hsv.shape
mask = np.zeros_like(img)

for j in range(height):
    for i in range(width):
        pixel = [np.array([hsv.item(j,i,0), hsv.item(j,i,1), hsv.item(j,i,2), blur3.item(j,i,0), blur5.item(j,i,0), blur7.item(j,i,0)], dtype=np.float32)]
        results = gnb.predict(pixel)
        if(results == 1):
            mask[j, i] = [0, 0, 255]

mask = cv2.medianBlur(mask, 5)
img = cv2.addWeighted(img, 0.8, mask, 0.5, 0.2)
cv2.imshow('result', img)
cv2.waitKey(0)
cv2.destroyAllWindows()

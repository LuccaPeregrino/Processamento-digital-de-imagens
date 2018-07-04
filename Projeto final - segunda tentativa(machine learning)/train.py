import cv2
import csv

def write_rows(img, label, output):
    hsv = cv2.cvtColor(img, cv2.COLOR_BGR2HSV)
    blur3 = cv2.GaussianBlur(hsv, (3,3), 0)
    blur5 = cv2.GaussianBlur(hsv, (5,5), 0)
    blur7 = cv2.GaussianBlur(hsv, (7,7), 0)
    height, width, channels = hsv.shape

    for j in range(height):
        for i in range(width):
            r, g, b = img[j, i]
            h, s, v = hsv[j, i]
            h_blur3 = blur3[j, i, 0]
            h_blur5 = blur5[j, i, 0]
            h_blur7 = blur7[j, i, 0]
            label_number = 0 if (label[j, i] == [0, 0, 0]).all() else 1
            output.writerow([h, s, v, h_blur3, h_blur5, h_blur7, label_number])

def create_csv(num_img, file_name):
    file = open(file_name + '.csv', 'w')
    output = csv.writer(file)
    output.writerow(['h', 's', 'v', 'h_blur3', 'h_blur5', 'h_blur7', 'label'])

    for i in range(1, num_img + 1):
        img_path = 'images/train images/img{}.jpg'.format(i)
        img = cv2.imread(img_path)
        label_path = 'images/train images/img{}_label.jpg'.format(i)
        label = cv2.imread(label_path)
        write_rows(img, label, output)

if __name__ == '__main__':
    create_csv(4, 'dataframe')
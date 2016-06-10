[![Codacy Badge](https://api.codacy.com/project/badge/Grade/9669f28b3d144aa69de632e121b735b7)](https://www.codacy.com/app/tiagobertolo/imagifier?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=bertolo1988/imagifier&amp;utm_campaign=Badge_Grade)

# **imagifier**
Imagifier lets you build an image using a collection of images as the "new pixels".
It transforms every pixel of the source image to an image wich pixels are made by images of the given collection. 

----------

### How it works

The images, from the given collection,  will be choosen by averaging the color of all pixels in comparison to the source pixel. 
The average color is calculated by adding up the amount of RGB.Red, Green, Blue divided by 3.
 
### Compile

> mvn package

### Run

**Windows**

> mvn exec:java -Dexec.mainClass="org.bertolo1988.imagifier.imagifier.Run" -Dexec.args="10 C:\dir\path\sample_images C:\another\path\logo.png C:\the\path\fds.jpg"

**Unix**

to do

### Arguments

Arguments must be ordered.
First argument should be numerical and it specifies the amount of pixels that a single source pixel will ocupy in the resulting image.
The other arguments are the collections directory, source image (must have image extension) and the last one is the path where the image should be saved with the according file type.


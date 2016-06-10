[![Codacy Badge](https://api.codacy.com/project/badge/Grade/9669f28b3d144aa69de632e121b735b7)](https://www.codacy.com/app/tiagobertolo/imagifier?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=bertolo1988/imagifier&amp;utm_campaign=Badge_Grade)

# **imagifier**
Imagifier lets you build an image using a collection of images as the "new pixels".
It transforms every pixel of the source image to an image wich pixels are made by images of the given collection. 

| Before       | After           | Detail  |
|:------------:|:---------------:|:-------:|
|<img src="http://i.imgur.com/F60pE8I.jpg" width="200"/>   | <img src="http://i.imgur.com/g4i9ImH.jpg" width="200" /> | <img src="http://i.imgur.com/fjybkeb.jpg" width="200" /> |

----------
 
### Compile

> mvn package

### Run

> mvn exec:java -Dexec.mainClass="org.bertolo1988.imagifier.imagifier.Run" -Dexec.args="3 10 C:\dir\path\sample_images C:\another\path\logo.png C:\the\path\fds.jpg"

### Arguments

Arguments must be in the following order:

1. Source square side
2. Target square side
3. Collections directory path
4. Source image (must have extension)
5. Output image (must have extension)


First two arguments should be numerical. They specify how many pixels from the source image are going to be converted in 1 single collection image in the output.
For instance 3 10 means, a square 3x3 will look like a 10x10 in the output image.
The other arguments are the collections directory, source image (must have image extension) and the last one is the path where the image should be saved with the corresponding file type.


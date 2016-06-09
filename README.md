[![Codacy Badge](https://api.codacy.com/project/badge/Grade/9669f28b3d144aa69de632e121b735b7)](https://www.codacy.com/app/tiagobertolo/imagifier?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=bertolo1988/imagifier&amp;utm_campaign=Badge_Grade)
# imagifier
Imagifier lets you build an image using a collection of images as the "new pixels".

# How it works

It transforms every square of pixels of a source image to an image wich pixels are made by images of the given collection.

# Compile

mvn package

# Run

#### Windows

mvn exec:java -Dexec.mainClass="org.bertolo1988.imagifier.imagifier.Run" -Dexec.args="10 C:\dir\path\sample_images C:\another\path\logo.png C:\the\path\fds.jpg"

#### Unix

to do

# Arguments

Arguments must be ordered.
First argument should be numerical and it specifies the amount of pixels that a single source pixel will ocupy in the resulting image.
The other arguments are collections directory, source image (must have image extension) and the last one is the path where the image should be saved with the according file type.

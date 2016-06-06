package org.bertolo1988.imagifier.imagifier;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class Imagifier {

    static final String PNG = "png";
    static final String DEFAULT_IMAGES_PATH = "./sample_images";
    static final String DEFAULT_TARGET_IMAGE = "image_target";
    static final String DEFAULT_RESULT_IMAGE = "image_result";
    static final int N = 100;

    public static void main( String[] args ) throws IOException {
        HashMap<String,BufferedImage> sampleImages = buildSampleColorImages();
        //todo:build average representation of color for every sample image

        BufferedImage targetImage = ImageIO.read( new File( DEFAULT_TARGET_IMAGE + PNG ) );
        final int k = targetImage.getWidth() / N;
        final int p = targetImage.getHeight() / N;

        //todo:imagify here
    }

    private static HashMap<String,BufferedImage> buildSampleColorImages() throws IOException {
        File[] sampleImagesFiles = new File( DEFAULT_IMAGES_PATH ).listFiles();
        HashMap<String,BufferedImage> sampleImages = new HashMap<String,BufferedImage>();
        for ( int i = 0 ; i < sampleImagesFiles.length ; i++ ) {
            sampleImages.put( sampleImagesFiles[ i ].getName(), ImageIO.read( sampleImagesFiles[ i ] ) );
        }
        return sampleImages;
    }

}

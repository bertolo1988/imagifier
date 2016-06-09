package org.bertolo1988.imagifier.imagifier;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Run {

    static final String PNG = "png";
    static final String DEFAULT_IMAGES_PATH = "./sample_images";
    static final String DEFAULT_SOURCE_IMAGE = "image_source";
    static final String DEFAULT_RESULT_IMAGE = "image_result";
    static final int SQUARE_SIZE = 12;

    public static void main( String[] args ) throws IOException {
        BufferedImage sourceImage = ImageIO.read( new File( DEFAULT_SOURCE_IMAGE + "." + PNG ) );
        ArrayList<PixelImage> sampleImages = buildSampleColorImages();
        System.out.println( "Done with sample conversion!" );
        System.out.println( "Starting to imagify..." );
        Imagifier imagifier = Imagifier.getInstance();
        BufferedImage resultImage = null;
        try {
            resultImage = imagifier.imagify( sampleImages, sourceImage, SQUARE_SIZE );
        } catch ( Exception e ) {
            e.printStackTrace();
            System.exit( 1 );
        }
        if ( resultImage != null ) {
            System.out.println( "100%!\nWriting to file..." );
            ImageIO.write( resultImage, PNG, new File( DEFAULT_RESULT_IMAGE + "." + PNG ) );
            System.out.println( "Saved to " + DEFAULT_RESULT_IMAGE + "." + PNG );
        }

    }

    private static ArrayList<PixelImage> buildSampleColorImages() throws IOException {
        File[] sampleImagesFiles = new File( DEFAULT_IMAGES_PATH ).listFiles();
        ArrayList<PixelImage> sampleImages = new ArrayList<PixelImage>();
        for ( int i = 0 ; sampleImagesFiles != null && i < sampleImagesFiles.length ; i++ ) {
            BufferedImage image = ImageManipulationUtils.resize( ImageIO.read( sampleImagesFiles[ i ] ), SQUARE_SIZE,
                SQUARE_SIZE );
            Color averageColor = ImageManipulationUtils.averageColor( image );
            int averageGrayLevel = ImageManipulationUtils.calcAverageGrayLevel( image );
            sampleImages.add( new PixelImage( image, averageColor, averageGrayLevel ) );
        }
        return sampleImages;
    }

}

package org.bertolo1988.imagifier.imagifier;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Imagifier {

    static final String PNG = "png";
    static final String DEFAULT_IMAGES_PATH = "./sample_images";
    static final String DEFAULT_SOURCE_IMAGE = "image_source";
    static final String DEFAULT_RESULT_IMAGE = "image_result";
    static final int N = 1;

    public static void main( String[] args ) throws IOException {
        BufferedImage sourceImage = ImageIO.read( new File( DEFAULT_SOURCE_IMAGE + "." + PNG ) );
        ArrayList<PixelImage> sampleImages = buildSampleColorImages();
        System.out.println( "Done with sample conversion!" );
        BufferedImage resultImage;
        resultImage = buildImagifiedImage( sampleImages, sourceImage );
        if ( resultImage != null ) {
            System.out.println( "100%!\nWriting to file..." );
            ImageIO.write( resultImage, PNG, new File( DEFAULT_RESULT_IMAGE + "." + PNG ) );
            System.out.println( "Saved to " + DEFAULT_RESULT_IMAGE + "." + PNG );
        }
    }

    private static BufferedImage buildImagifiedImage( ArrayList<PixelImage> sampleImages, BufferedImage sourceImage ) {
        int sourceWidth = sourceImage.getWidth();
        int sourceHeight = sourceImage.getHeight();
        BufferedImage finalImage = createOutputBufferedImage( sourceImage, sourceWidth, sourceHeight );
        for ( int x = 0, y = 0, lastPercentage = 0 ; y < sourceHeight / N ; y++ ) {
            lastPercentage = printPercentage( y, sourceHeight / N, lastPercentage );
            for ( x = 0 ; x < sourceWidth / N ; x++ ) {
                BufferedImage crop = ImageManipulationUtils.cropImage( sourceImage, x * N, y * N, N, N );
                Color cropColor = ImageManipulationUtils.averageColor( crop );
                BufferedImage bestSuitedSample = calcBestColor( sampleImages, cropColor );
                ImageManipulationUtils.replaceAt( finalImage, bestSuitedSample, x * SQUARE_SIDE, y * SQUARE_SIDE );
            }
        }
        return finalImage;
    }

    private static BufferedImage createOutputBufferedImage( BufferedImage sourceImage, int sourceWidth, int sourceHeight ) {
        int targetWidth = sourceWidth / N * SQUARE_SIDE;
        int targetHeight = sourceHeight / N * SQUARE_SIDE;
        if ( targetWidth * targetHeight >= Integer.MAX_VALUE / 4 || targetWidth * targetHeight < 0 ) {
            System.out.println( "Target image too big... Size will be adjusted!" );
            if ( targetWidth > targetHeight ) {
                targetWidth = (int)Math.sqrt( ( Integer.MAX_VALUE / 4 ) * (float)( targetWidth / targetHeight ) );
                targetHeight = ( Integer.MAX_VALUE / 4 ) / targetWidth;
            } else {
                targetHeight = (int)Math.sqrt( ( Integer.MAX_VALUE / 4 ) * (float)( targetHeight / targetWidth ) );
                targetWidth = ( Integer.MAX_VALUE / 4 ) / targetHeight;
            }
        }
        BufferedImage finalImage = new BufferedImage( targetWidth, targetHeight, sourceImage.getType() );
        return finalImage;
    }

    private static BufferedImage calcBestColor( ArrayList<PixelImage> sampleImages, Color cropColor ) {
        int result = 0;

        for ( int minimum = 255 * 3, i = 0 ; i < sampleImages.size() ; i++ ) {
            int quantification = quantifyColorDeviation( cropColor, sampleImages.get( i ).getRepresentedColor() );
            if ( minimum > quantification ) {
                minimum = quantification;
                result = i;
            }
        }
        return sampleImages.get( result ).getImage();
    }

    private static ArrayList<PixelImage> buildSampleColorImages() throws IOException {
        File[] sampleImagesFiles = new File( DEFAULT_IMAGES_PATH ).listFiles();
        ArrayList<PixelImage> sampleImages = new ArrayList<PixelImage>();
        for ( int i = 0 ; sampleImagesFiles != null && i < sampleImagesFiles.length ; i++ ) {
            BufferedImage image = ImageManipulationUtils.resize( ImageIO.read( sampleImagesFiles[ i ] ), SQUARE_SIDE,
                SQUARE_SIDE );
            Color averageColor = ImageManipulationUtils.averageColor( image );
            int averageGrayLevel = ImageManipulationUtils.calcAverageGrayLevel( image );
            sampleImages.add( new PixelImage( image, averageColor, averageGrayLevel ) );
        }
        return sampleImages;
    }

    public static int quantifyColorDeviation( Color colorA, Color colorB ) {
        int result = 0;
        result += Math.abs( colorA.getBlue() - colorB.getBlue() );
        result += Math.abs( colorA.getRed() - colorB.getRed() );
        result += Math.abs( colorA.getGreen() - colorB.getGreen() );
        return result;
    }

    private static int printPercentage( int y, int i, int lastPercentage ) {
        int percentage = ( y * 100 ) / i;
        if ( percentage % PERCENTAGE_INCREMENTS == 0 && percentage > lastPercentage ) {
            System.out.print( percentage + "%..." );
        }
        return percentage;
    }

}

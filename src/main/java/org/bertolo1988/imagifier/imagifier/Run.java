package org.bertolo1988.imagifier.imagifier;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

public class Run {

    private static final String DEFAULT_IMAGES_PATH = "./sample_images";
    private static final String DEFAULT_SOURCE_IMAGE_PATH = "image_source.png";
    private static final String DEFAULT_RESULT_IMAGE_PATH = "image_result.png";
    private static final int DEFAULT_SQUARE_SIZE = 20;
    private static final int VALID_NUMBER_OF_ARGS = 4;

    public static void main( String[] args ) throws Exception {
        try {
            if ( args.length == 0 ) {
                run( DEFAULT_SQUARE_SIZE, DEFAULT_IMAGES_PATH, DEFAULT_SOURCE_IMAGE_PATH, DEFAULT_RESULT_IMAGE_PATH,
                    ImageType.PNG );
            } else if ( args.length == VALID_NUMBER_OF_ARGS ) {
                int squareSide = Integer.parseInt( args[ 0 ] );
                if ( !new File( args[ 1 ] ).isDirectory() ) {
                    throw new Exception( "Invalid sample images dir path!" );
                }
                if ( !new File( args[ 2 ] ).isFile() ) {
                    throw new Exception( "Invalid source image path!" );
                }
                if ( args[ 3 ].length() < 5 ) {
                    throw new Exception( "Invalid result image path!" );
                } else if ( isValidImageTypeExtension( getOutputFileType( args[ 3 ] ) ) ) {
                    throw new Exception( "Invalid output file type!" );
                } else {
                    run( squareSide, args[ 1 ], args[ 2 ], args[ 3 ], getImageType( args[ 3 ] ) );
                }
            } else {
                throw new Exception( "Invalid number of arguments!" );
            }
        } catch (

        Exception e ) {
            e.printStackTrace();
            System.exit( 1 );
        }
    }

    private static boolean isValidImageTypeExtension( String str ) {
        Pattern pattern = Pattern.compile( "([^\\s]+(\\.(?i)(jpg|png|bmp))$)" );
        Matcher matcher = pattern.matcher( str );
        return matcher.matches();

    }

    private static ImageType getImageType( String type ) {
        switch ( type.toUpperCase() ) {
            case "PNG":
                return ImageType.PNG;
            case "JPG":
                return ImageType.JPG;
            case "BMP":
                return ImageType.BMP;
            case "JPEG":
                return ImageType.JPEG;
            default:
                return ImageType.PNG;
        }
    }

    private static String getOutputFileType( String fileName ) {
        return fileName.substring( fileName.length() - 3 );
    }

    private static void run( int squareSize, String sampleImagesDirPath, String sourceImagePath, String resultImagePath, ImageType outputType ) throws Exception {
        BufferedImage sourceImage = ImageIO.read( new File( sourceImagePath ) );
        ArrayList<PixelImage> sampleImages = buildSampleColorImages( sampleImagesDirPath, squareSize );
        System.out.println( "Done with sample conversion!" );
        System.out.println( "Starting to imagify..." );
        Imagifier imagifier = Imagifier.getInstance();
        BufferedImage resultImage = null;
        resultImage = imagifier.imagify( sampleImages, sourceImage, squareSize );
        if ( resultImage != null ) {
            System.out.println( "100%!\nWriting to file..." );
            ImageIO.write( resultImage, outputType.toString(), new File( resultImagePath ) );
            System.out.println( "Saved to " + resultImagePath );
        }
    }

    private static ArrayList<PixelImage> buildSampleColorImages( String sampleImagesDirPath, int squareSize ) throws IOException {
        File[] sampleImagesFiles = new File( sampleImagesDirPath ).listFiles();
        ArrayList<PixelImage> sampleImages = new ArrayList<PixelImage>();
        for ( int i = 0 ; sampleImagesFiles != null && i < sampleImagesFiles.length ; i++ ) {
            BufferedImage image = ImageManipulationUtils.resize( ImageIO.read( sampleImagesFiles[ i ] ), squareSize,
                squareSize );
            Color averageColor = ImageManipulationUtils.averageColor( image );
            int averageGrayLevel = ImageManipulationUtils.calcAverageGrayLevel( image );
            sampleImages.add( new PixelImage( image, averageColor, averageGrayLevel ) );
        }
        return sampleImages;
    }

}

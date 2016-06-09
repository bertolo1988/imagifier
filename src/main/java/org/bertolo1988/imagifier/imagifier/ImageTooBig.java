package org.bertolo1988.imagifier.imagifier;

public class ImageTooBig extends Exception {

    private static final long serialVersionUID = 1520887243539103437L;

    public ImageTooBig( String message ) {
        super( message );
    }

}

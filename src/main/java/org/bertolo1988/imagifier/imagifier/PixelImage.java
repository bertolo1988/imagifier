package org.bertolo1988.imagifier.imagifier;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class PixelImage {

	private BufferedImage image;
	private Color representedColor;
	private int averageGrayLevel;

	public PixelImage(BufferedImage image, Color representedColor, int averageGrayLevel) {
		super();
		this.image = image;
		this.representedColor = representedColor;
		this.averageGrayLevel = averageGrayLevel;
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public Color getRepresentedColor() {
		return representedColor;
	}

	public void setRepresentedColor(Color representedColor) {
		this.representedColor = representedColor;
	}

	public int getAverageGrayLevel() {
		return averageGrayLevel;
	}

	public void setAverageGrayLevel(int averageGrayLevel) {
		this.averageGrayLevel = averageGrayLevel;
	}

}

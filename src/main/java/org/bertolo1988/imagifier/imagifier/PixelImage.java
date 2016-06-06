package org.bertolo1988.imagifier.imagifier;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class PixelImage {

	private String name;
	private BufferedImage image;
	private Color representedColor;

	public PixelImage(String name, BufferedImage image, Color representedColor) {
		super();
		this.name = name;
		this.image = image;
		this.representedColor = representedColor;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}

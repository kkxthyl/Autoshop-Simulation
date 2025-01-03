package environment;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import util.ImageLoader;
import util.Util;

/*
 * this leaf class implements the environment interface with the common draw method 
 * the leaf image changes according to the colour parameter which value is determined randomly 
 * it is to appear that the leaves are animated 
 * the leaves are drawn at the end of the branches in a recursive tree until they run out 
 * 
 * ECO: the leaves are drawn at the end of the branches for a more relaisitc look
 * ECO: leaves are animted to change colours randomly to make the tree not look so flat and 2D
 */

public class LeafDecorator implements EnviroInterface{
	private BufferedImage leaf, leafLight; 
	
	public LeafDecorator() {
		leaf = ImageLoader.loadImage("assets/leaf.png");
		leafLight = ImageLoader.loadImage("assets/leafLight.png");
	}
	
	private void drawLeaf(Graphics2D g2, int colour) {
		AffineTransform at = g2.getTransform();
		double scale = 0.08;
		g2.scale(scale, scale);
		if (colour == 0) {
			g2.drawImage(leaf, -leaf.getWidth()/2, -leaf.getHeight()/2, null);
		}
		else {
			g2.drawImage(leafLight, -leafLight.getWidth()/2, -leafLight.getHeight()/2, null);
		}
		g2.setTransform(at);
	}

	@Override
	public void draw(Graphics2D g2) {
		// TODO Auto-generated method stub
		drawLeaf(g2, (int)Util.random(0, 2));
	}
	

}

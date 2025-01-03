package tools;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import main.AutoshopPanel;
import util.ImageLoader;
import util.Util;

/*
 * this is a subclass of the Tool superclass
 * the asbtract method draw is implememted spceifically for this object
 * all images are made by me on adobe illustrator
 * methods to draw the different stages of the drill and for interaction scenarios 
 */

public class Drill extends Tool{
	private BufferedImage drill; 
	
	public Drill() {
		super(140, 470);
		drill = ImageLoader.loadImage("assets/drill.png"); 
	}
	
	public void draw(Graphics2D g2) {
		AffineTransform at = g2.getTransform();
		g2.translate(xPos, yPos);
		g2.scale(0.2, 0.2);
		g2.drawImage(drill, -drill.getWidth()/2, -drill.getHeight()/2, null);
		g2.translate(xPos*26, 0);
		g2.drawImage(drill, -drill.getWidth()/2, -drill.getHeight()/2, null);
		g2.setTransform(at);
	}
	
	public void drawDrillDrag(Graphics2D g2) {
		AffineTransform at = g2.getTransform();
		g2.translate(xPos, yPos);
		g2.scale(0.2, 0.2);
		g2.drawImage(drill, -drill.getWidth()/2, -drill.getHeight()/2, null);
		g2.setTransform(at);
	}
	
	public double getWidthDrill() {
		return drill.getWidth(); 
	}
	
	
	public double getX() {
		return xPos; 
	}
	
	public double getY() {
		return yPos; 
	}
	
	public boolean clicked(double x, double y){
		boolean clicked = false;
		
		if (x > (xPos - ((double) drill.getWidth()) / 2 * 0.2) && x < (xPos + ((double) drill.getWidth())/2*0.2) && y > (yPos - ((double) drill.getHeight())/2*0.2) && y < (yPos + ((double) drill.getHeight())/2*0.2)) 
			clicked = true;
		
		return clicked;
	}
	
	
	public boolean hitFront(Tires tire) {
		boolean hit = false;

		if (Math.abs(xPos - tire.getX()) < 50 && Math.abs(yPos- tire.getY()) < 30)
			hit = true;
		
		return hit;
	}
	
	
}

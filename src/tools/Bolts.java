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
 * methods to draw the different stages of bolts for the tires and for interaction scenarios 
 */

public class Bolts extends Tool{
	private BufferedImage bolts, boltsOnRim; 
	
	public Bolts() { 
		super(140, 520);
		bolts = ImageLoader.loadImage("assets/bolts.png"); 
		boltsOnRim = ImageLoader.loadImage("assets/centerRimBolts.png"); 
	}
	
	
	public void draw(Graphics2D g2) {
		AffineTransform at = g2.getTransform();
		g2.translate(xPos, yPos);
		g2.scale(0.2, 0.2);
		g2.drawImage(bolts, -bolts.getWidth()/2, -bolts.getHeight()/2, null);
		g2.translate(xPos*27, 0);
		g2.drawImage(bolts, -bolts.getWidth()/2, -bolts.getHeight()/2, null);
		g2.setTransform(at);
	}
	
	
	public void drawBoltsDrag(Graphics2D g2) {
		AffineTransform at = g2.getTransform();
		g2.translate(xPos, yPos);
		g2.scale(0.2, 0.2);
		g2.drawImage(bolts, -bolts.getWidth()/2, -bolts.getHeight()/2, null);
		g2.setTransform(at);
	}
	
	public void drawRimBolts(Graphics2D g2, int x, int state) {
		AffineTransform at = g2.getTransform();
		if (state == 11) {
			if (x == 0) {
				g2.translate(300, 460);
			}
			else {
				g2.translate(850, 460);
			}
		}
		else {
			if (x == 0) {
				g2.translate(340, 460);
			}
			else {
				g2.translate(900, 460);
			}
		}
		g2.scale(0.2, 0.2);
		g2.drawImage(boltsOnRim, -boltsOnRim.getWidth()/2, -boltsOnRim.getHeight()/2, null);
		g2.setTransform(at);
	}
	
	
	public boolean clicked(double x, double y){
		boolean clicked = false;
		
		if (x > (xPos - ((double) bolts.getWidth()) / 2 * 0.2) && x < (xPos + ((double) bolts.getWidth())/2*0.2) && y > (yPos - ((double) bolts.getHeight())/2*0.2) && y < (yPos + ((double) bolts.getHeight())/2*0.2)) 
			clicked = true;
		
		return clicked;
	}
	
	public boolean hitTire(Tires tire) {
		boolean hit = false;

		if (Math.abs(xPos - tire.getX()) < 50 && Math.abs(yPos- tire.getY()) < 30)
			hit = true;
		
		return hit;
	}
	
	
}

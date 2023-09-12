package autoshop;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import main.AutoshopPanel;
import processing.core.PVector;
import util.ImageLoader;

/*
 * this class holds all the images, fields, and methods related to the car
 * all the images loaded into this class were made by me on Adobe Illustrator  
 * separate methods for drawing the different scenes 
 * a few methods animating the object
 * 
 * ECO: the car wheels are animated for a realisitc scene
 * ECO: the taillights of the car turn a bright red as if the breaks were on while the car is 
 * driving in or out of the garage.  lights are off when the car is turned off and elevated onto the lift
 */
public class Car {
	private PVector pos; 
	private BufferedImage carProfile, carBack, redCarBack; 
	private boolean setPos = false; 
	private double scale = 0.3; 
	
	public Car() {
		carProfile = ImageLoader.loadImage("assets/carProfile.png"); 
		carBack = ImageLoader.loadImage("assets/carBack.png"); 
		redCarBack = ImageLoader.loadImage("assets/redLight.png"); 
		
		pos = new PVector(AutoshopPanel.W_WIDTH + carProfile.getWidth()/6, 500);
	}
	
	public void drawCarProfile(Graphics2D g2) {
		AffineTransform at = g2.getTransform();
		g2.translate(pos.x, pos.y);
		g2.scale(0.25, 0.25);
		g2.drawImage(carProfile, -carProfile.getWidth()/2, -carProfile.getHeight()/2, null);
		
		g2.setTransform(at);
	}
	
	public void drawCarBack(Graphics2D g2, boolean red) {
		AffineTransform at = g2.getTransform();
		if (setPos == false) {
			setPos = true; 
			pos.x = 530; 
			pos.y = 800;
		}
		g2.translate(pos.x, pos.y);
		g2.scale(scale, scale);
		if (red) {
			g2.drawImage(redCarBack, -redCarBack.getWidth()/2, -redCarBack.getHeight()/2, null);
		}
		else{
			g2.drawImage(carBack, -carBack.getWidth()/2, -carBack.getHeight()/2, null);
		}
		g2.setTransform(at);
	}
	
	//car moving sideways 
	public boolean moveProfile() {
		if (pos.x >(AutoshopPanel.W_WIDTH/4)*3) {
			pos.x -= 15;
			return false;
		}
		else {
			return true;
		}
	}
	
	//car moves into the garage
	public boolean moveIn() {
		if (pos.y >550) {
			pos.y -= 7;
			scale -= 0.0013;
			return false; 
		}
		return true; 
	}
	
	//car moves out of the garage
	public boolean moveOut() {
		if (pos.y <700) {
			pos.y += 7;
			scale += 0.0015;
			return false; 
		}
		return true; 
	}
	
	//car is elevated 
	public boolean moveUp() {
		if (pos.y >400) {
			pos.y -= 5;
			return false; 
		}
		return true; 
	}
	
	//car is lowered 
	public boolean moveDown() {
		if (pos.y <530) {
			pos.y += 5;
			return false; 
		}
		return true; 
	}
}

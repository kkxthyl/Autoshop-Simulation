package autoshop;

/*
 * this class holds all the images, fields, and methods related to the instructions posted
 * all the images loaded into this class were made by me on Adobe Illustrator  
 */
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import util.ImageLoader;

public class Info {
	private BufferedImage enterGarage, liftCar, drill, tires, restart, lowerCar; 
	
	public Info() {
		enterGarage = ImageLoader.loadImage("assets/info/info-enterGarage.png"); 
		liftCar = ImageLoader.loadImage("assets/info/info-liftCar.png"); 
		drill = ImageLoader.loadImage("assets/info/info-drill1.png"); 
		tires = ImageLoader.loadImage("assets/info/info-newTires.png"); 
		restart = ImageLoader.loadImage("assets/info/info-restart.png"); 
		lowerCar = ImageLoader.loadImage("assets/info/info-lowerCar.png");
	}
	
	public void drawInfo(Graphics2D g2, int state) {
		AffineTransform at = g2.getTransform();
		g2.translate(150, 250);
		g2.scale(0.2, 0.2);
		if (state == 1) {
			g2.drawImage(enterGarage, -enterGarage.getWidth()/2, -enterGarage.getHeight()/2, null);
		}
		else if (state == 2) {
			g2.drawImage(liftCar, -liftCar.getWidth()/2, -liftCar.getHeight()/2, null);	
		}
		
		else if (state == 4) {
			g2.drawImage(drill, -drill.getWidth()/2, -drill.getHeight()/2, null);
		}
		
		else if (state == 5) {
			g2.drawImage(tires, -tires.getWidth()/2, -tires.getHeight()/2, null);	
		}
		
		else if (state == 8) {
			g2.translate(0, -150);
			g2.drawImage(restart, -restart.getWidth()/2, -restart.getHeight()/2, null);	
		}
		
		else if (state == 12) {
			g2.translate(0, -150);
			g2.drawImage(lowerCar, -lowerCar.getWidth()/2, -lowerCar.getHeight()/2, null);	
		}
		
		g2.setTransform(at);
	}
	
}

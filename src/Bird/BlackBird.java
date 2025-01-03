package Bird;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import processing.core.PVector;
import util.ImageLoader;

/*
 * this is a subclass of the Bird superclass
 * the asbtract method draw is implememted spceifically for this object
 * the images are in an animated sequence 
 * all images are made by me on adobe illustrator
 * 
 * ECO: the bird is animated to mimic their flapping wings as they move across the screen
 */

public class BlackBird extends Bird{
	private BufferedImage[] birdRot = new BufferedImage[6];
	
	public BlackBird(PVector pos, PVector vel, double scale) {
		super(pos, vel, scale);
		for (int i = 0; i < 6; i++) {
			birdRot[i] = ImageLoader.loadImage("assets/blackBird/bird-0"+i+".png");
		}
	}

	@Override
	public void draw(Graphics2D g2) {
		// TODO Auto-generated method stub
		AffineTransform at = g2.getTransform(); 
		g2.translate(pos.x, pos.y); 
		g2.scale(-scale,  scale);
		//System.out.println(currentFrame);
		g2.drawImage(birdRot[currentFrame], -birdRot[currentFrame].getWidth()/2, -birdRot[currentFrame].getHeight()/2, null);
		g2.setTransform(at);
	}

}

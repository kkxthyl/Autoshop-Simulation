package environment;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import Bird.Bird;
import main.AutoshopPanel;
import processing.core.PVector;

/*
* this is a class that draws and initalizes all the objects for the outside environment 
* the sky and tree components were made using a decorator pattern 
* all images are made by me on adobe illustrator
* there is a method called timeChange that shows the time lapses with the adjust of the sky colour 
* 
* ECO: there is a change in time during the way when the sky gradually gets darker as if the sun is setting
* ECO: the birds are animated across the window
*/
public class Enviro  {
	EnviroInterface enviro, baseEnviro; 
	
	private int timer = 0, alpha = 0, birdTimer = 0; 
	
	public Enviro() {
		enviro = new SimpleEnviro(); 
		baseEnviro = new SimpleEnviro();
	}
	
	public void draw(Graphics2D g2, int x, int y) {
		createEnviro(g2, x, y);
		enviro.draw(g2);
		timeChange(g2);
	}
	
	private void createEnviro(Graphics2D g2, int x, int y) {
		//System.out.println(timer + " " + alpha);  
		
		AffineTransform at = g2.getTransform();
		g2.translate(200, 0);
		
		enviro = new SkyDecorator(baseEnviro); 
		
		g2.setTransform(at);
		enviro = new TreeDecorator(enviro, x, y); 
		
	}
	
	private void timeChange(Graphics2D g2) {
		timer++;
		g2.setColor(new Color(0,0, 0,  alpha));
		if (alpha < 190 && (timer%4 == 0)) {
			alpha+=3;  
		}
		
		g2.fill(new Rectangle2D.Double(0, 0, AutoshopPanel.W_WIDTH, AutoshopPanel.W_HEIGHT));
		
	}
	
	public void drawBirds(ArrayList<Bird> arr) {
		birdTimer++;
		if (birdTimer %4 == 0) {
			for (int i = 0; i < arr.size(); i++) {
				arr.get(i).setCurrentFrame(1); 
				if (arr.get(i).getCurrentFrame() == 6) {
					arr.get(i).resetCurrentFrame(); 
				}
			}
			
		}
		
	}
	
}

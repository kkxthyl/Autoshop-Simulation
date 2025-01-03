package environment;

import java.awt.Color;


import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.Random;

import main.AutoshopPanel;
import processing.core.PApplet;
import util.Util;

/*
* the skyDecorator subclass extends the EnviroDecorator superclass 
* adds onto the environment texture of a natural sky using the perlin noise 
* 
* ECO: perlin noise was used to texturize and animate the sky in the outdoor environment
*/

public class SkyDecorator extends EnviroDecorator{
	private float xStart; 
	private float xSeed; 
	private float ySeed; 
	private PApplet pa; 
	
	public SkyDecorator(EnviroInterface baseEnviro) {
		super(baseEnviro); 
		xStart = Util.random(10); 
		xSeed = xStart; 
		ySeed = Util.random(10); 
		pa = new PApplet(); 
	}
	
	public void draw(Graphics2D g2) {
		super.draw(g2);
		addSky(g2); 
	}
	
	private void addSky(Graphics2D g2) {
		float noiseFactor; 
		for (int y = 0; y <= AutoshopPanel.W_WIDTH; y+=12) {
			ySeed += 0.1; 
			xSeed = xStart; 
			
			for (int x = 0; x <= AutoshopPanel.W_HEIGHT; x+=12) {
				xSeed += 0.1; 
				noiseFactor = pa.noise(xSeed, ySeed);
				
				AffineTransform at = g2.getTransform(); 
				g2.translate(x,  y);
				g2.rotate(noiseFactor * Math.toRadians(360));
				g2.setColor(new Color(57, 174, 237, 90));
				g2.drawLine(0, 0, 20, 0);
				g2.setTransform(at);
			}
		}
	}
}

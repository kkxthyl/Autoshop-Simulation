package autoshop;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;

import processing.core.PApplet;
import util.Util;

/*
 * this class holds all the images, fields, and methods related to the Perlin Noise smoke animation
 * all the images loaded into this class were made by me on Adobe Illustrator  
 * 
 * ECO: smoke is animated to mimic the smoke from a powerful drill and friction against two materials
 */

public class Smoke{
	private float xPos, yPos;
	private int width, height;

	private float xstart;
	private float xnoise;
	private float ynoise;
	private PApplet pa;

	
	public Smoke(float x , float y, int w, int h) {
		xPos = x;
		yPos = y;
		width = w;
		height = h;
		xstart = 2;
		xnoise = xstart;
		ynoise = 2;
		pa = new PApplet();
	}
	
	//perlin noise
	public void drawSmoke(Graphics2D g2) {
		float noiseFactor; 
		AffineTransform at = g2.getTransform();
		g2.translate(xPos, yPos);
		for(int i = 0; i < height; i+= 5) {
			ynoise += 0.4; 
			xnoise = xstart; 
			
			for (int j = 0; j < width; j+= 5) {
				xnoise += 0.4; 
				noiseFactor = pa.noise(xnoise, ynoise);
				
				AffineTransform af = g2.getTransform();
				g2.translate(i,  j);
				
				g2.rotate(noiseFactor * Math.toRadians(540));
				float diameter = noiseFactor *35; 
				int grey = (int)(80 + (noiseFactor*105));
				int alph = (int)(50 + (noiseFactor*80));
				g2.setColor(new Color(grey, grey, grey, alph));
				g2.fill(new Ellipse2D.Float(-diameter/2, -diameter/4, diameter, diameter/2));
				g2.setTransform(af);
			}
		}
		g2.setTransform(at);
	}
	public void setWidth(int newWidth) {
		width = newWidth;
	}
	
	public void setHeight(int newHeight) {
		height = newHeight; 
	}
	
	public void setPos(float x, float y) {
		xPos = x; 
		yPos = y; 
	}
}

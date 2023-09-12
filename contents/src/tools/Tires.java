package tools;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.awt.image.BufferedImage;

import main.AutoshopPanel;
import processing.core.PVector;
import util.ImageLoader;

/*
 * this is a subclass of the Tool superclass
 * the asbtract method draw is implememted spceifically for this object
 * all images are made by me on adobe illustrator
 * methods to draw the different tires, recursive rim design, and for interaction scenarios 
 * 
 * ECO: there is a recursive rim design on the new tires for the car
 */

public class Tires extends Tool{
	private BufferedImage oldTireSpokes, newTires, stackedTires; 
	
	private double angle = 0; 
	private double angleInc = 0.5; 
	private int xStop; 
	private double scale = 0.25;
	
	public Tires(int x, int y) {
		super(x,y);
		oldTireSpokes = ImageLoader.loadImage("assets/oldTire-spokes.png"); 
		newTires = ImageLoader.loadImage("assets/tire-noSpokes.png"); 
		stackedTires = ImageLoader.loadImage("assets/tireStack.png"); 
		
		if (y == 600) {
			xPos = AutoshopPanel.W_WIDTH + (oldTireSpokes.getWidth()/3)*2;
		}
		else {
			xPos = AutoshopPanel.W_WIDTH + 1120;
		}
		
		xStop = x; 
	}
	
	public void draw(Graphics2D g2) {
		AffineTransform at = g2.getTransform();
		g2.translate(xPos, yPos);
		g2.rotate(angle);
		g2.scale(scale, scale);
		g2.drawImage(oldTireSpokes, -oldTireSpokes.getWidth()/2, -20-oldTireSpokes.getHeight()/2, null);
		g2.setTransform(at);
	}
	
	public void drawNewTire(Graphics2D g2) {
		AffineTransform at = g2.getTransform();
		g2.translate(xPos, yPos);
		g2.scale(scale, scale);
		g2.drawImage(newTires, -newTires.getWidth()/2, -newTires.getHeight()/2, null);
		drawRecursiveRim(g2, 0, 0, 80);
		g2.setTransform(at);
	}
	
	public void drawStackedTires(Graphics2D g2, int state) {
		AffineTransform at = g2.getTransform();
		if (state == 0) {
			g2.translate(10, 80);
		}
		else {
			g2.translate(1190, 80);
		}
		g2.scale(0.08, 0.08);
		g2.drawImage(stackedTires, -stackedTires.getWidth()/2, -stackedTires.getHeight()/2, null);
		g2.setTransform(at);
	}
	
	public void drawRecursiveRim(Graphics2D g2, float x, float y, float d) {
		//System.out.println("FRACTAL");
		AffineTransform at = g2.getTransform(); 
		g2.translate(x, y); 
		g2.scale(4, 4);
		g2.setStroke(new BasicStroke(2));
		g2.setColor(new Color(201, 189, 109));
		g2.draw(new Ellipse2D.Float(-d/2, -d/2, d, d));
		g2.setTransform(at); 
		
		if (d > 35) {
			//g2.rotate(-Math.PI/6);
			d *= 0.5; 
			drawRecursiveRim(g2, x + d, y, d); 
			drawRecursiveRim(g2, x - d, y, d);
			
			drawRecursiveRim(g2, x, y + d, d); 
			drawRecursiveRim(g2, x, y - d, d);
		}
	}
	
	public void moveFrontTire() {
		if (xPos >xStop) {
			xPos -= 15;
			angle += angleInc; 
		}
	}
	
	public void moveBackTire() {
		if (yPos >xStop) {
			yPos -= 15;
			angle += angleInc; 
		}
		
	}
	
	public double getWidth() {
		return oldTireSpokes.getWidth(); 
	}
	public double getX() {
		return xPos; 
	}
	
	public double getY() {
		return yPos; 
	}
	
	public void setScale(double x) {
		scale =x;
	}
	
	
	public boolean clickedTireStack(double x, double y, int state ){
		boolean clicked = false;
		int yPos = 80;
		int xPos = 10; 
		if (state == 9) {
			xPos = 1190; 
		}
		
		if (x > (xPos - ((double) stackedTires.getWidth()) / 2 * 0.1) && x < (xPos + ((double) stackedTires.getWidth())/2*0.1) && y > (yPos - ((double) stackedTires.getHeight())/2*0.1) && y < (yPos + ((double) stackedTires.getHeight())/2*0.1)) 
			clicked = true;
		//System.out.println(clicked);
		return clicked;
	}
	
	
}

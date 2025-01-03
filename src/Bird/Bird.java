package Bird;

import java.awt.Graphics2D;
import java.util.ArrayList;

import processing.core.PVector;

/*
 * this is an abstract class that holds the methods that all the subclasses have in common for the bird object
 */
public abstract class Bird {
	protected PVector pos, vel; 
	protected double scale; 
	protected int currentFrame = 1; 
	
	public Bird(PVector pos, PVector vel, double scale) {
		this.pos = pos;
		this.vel = vel;
		this.scale = scale;
	}
	
	public void move() {
		pos.add(vel);
	}
	
	public double getScale() {
		return scale; 
	}
	
	public void setCurrentFrame(int x) {
		currentFrame+= x; 
	}
	
	public void resetCurrentFrame() {
		currentFrame = 1; 
	}
	
	public int getCurrentFrame() {
		return currentFrame; 
	}
	
	public abstract void draw(Graphics2D g2);
}

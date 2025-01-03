package tools;

import java.awt.Graphics2D;

/*
 * this is an abstract class that holds the methods that all the subclasses have in common for the tool object
 */

public abstract class Tool {
	protected int xPos, yPos; 
	public Tool(int x, int y) {
		xPos = x; 
		yPos = y; 
	}
	
	public void setPos(int x, int y) {
		xPos = x; 
		yPos = y; 
	}
	
	public abstract void draw(Graphics2D g2); 
	
}

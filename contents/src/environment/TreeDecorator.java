package environment;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import util.ImageLoader;
import util.Util;

/*
* the treeDecorator subclass extends the EnviroDecorator superclass 
* adds onto the environment an animated recursive fractal tree 
* 
* ECO: a recursive tree is drawn
*/

public class TreeDecorator extends EnviroDecorator{
	private ArrayList<LeafDecorator> leaves = new ArrayList<LeafDecorator>(); 
	private ArrayList<Float> sizes = new ArrayList<Float>(); 
	private LeafDecorator leaf; 
	private int xPos, yPos; 
	
	
	public TreeDecorator(EnviroInterface baseEnviro, int x, int y) {
		super(baseEnviro); 
		for (int i = 0; i < 120; i++) {
			sizes.add((float)Util.random(0.03, 0.08));
		}
		xPos = x;
		yPos = y;
		leaf = new LeafDecorator(); 
		
	}
	
	public void draw(Graphics2D g2) {
		super.draw(g2);
		AffineTransform at = g2.getTransform();
		g2.getTransform();
		g2.translate(xPos, yPos);
		g2.scale(0.8, 0.8);
		
		drawTree(g2, 70, 20);
		g2.setTransform(at);
		
	}
	
	
	private void drawTree(Graphics2D g2, float len, int branchWidth) {
		g2.setStroke(new BasicStroke(branchWidth));
		g2.setColor(new Color(74, 43, 0));
		g2.draw(new Line2D.Float(0, 0, 0, -len)); 

		g2.translate(0, -len); 
		len *= 0.75; 		
		branchWidth *= 0.75;
		
		
		if (len > 5) { 
			AffineTransform at = g2.getTransform();
			g2.rotate(Math.PI / 5); 
			drawTree(g2, len, branchWidth); 
			g2.setTransform(at);
			at = g2.getTransform();
			g2.rotate(-Math.PI / 5);
			drawTree(g2, len, branchWidth); 
			
			leaf.draw(g2);
			
			g2.setTransform(at);
		}
	}


}


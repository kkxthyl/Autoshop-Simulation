package environment;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import main.AutoshopPanel;

/*
* this class serves as the background environment base for the whole scene 
* it colours the background blue 
* this class implements the Enviornment Interface with the common draw method  
*/

public class SimpleEnviro implements EnviroInterface{
	public SimpleEnviro() {
		
	}

	@Override
	public void draw(Graphics2D g2) {
		// TODO Auto-generated method stub
		g2.setColor(new Color(50, 142, 191));
		g2.fill(new Rectangle2D.Double(0, 0, AutoshopPanel.W_WIDTH, AutoshopPanel.W_HEIGHT));
		
	}
	
}

package environment;

import java.awt.Graphics2D;

/*
* this class implements the EnviroInterface with the draw method 
*/

public class EnviroDecorator implements EnviroInterface{
	protected EnviroInterface baseEnviro; 
	
	public EnviroDecorator(EnviroInterface baseEnviro) {
		this.baseEnviro = baseEnviro; 
	}

	@Override
	public void draw(Graphics2D g2) {
		// TODO Auto-generated method stub
		baseEnviro.draw(g2); 
		
	}
}

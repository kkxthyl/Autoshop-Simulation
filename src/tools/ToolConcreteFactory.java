package tools;

/*
 * this is the concrete factory class that is used to inialize the tool objects and hide the parameters from the users
 */

public class ToolConcreteFactory extends ToolFactory{
	public Tool createTool(String type) {
		Tool tool = null; 
		
		if (type == "bolts") {
			tool = new Bolts(); 
		}
		else if (type == "drill") {
			tool = new Drill(); 
		}
		else if (type == "frontTire") {
			tool = new Tires(550, 600); 
		}
		else if (type == "backTire") {
			tool = new Tires(1200, 590); 
		}
		return tool; 
	}
}

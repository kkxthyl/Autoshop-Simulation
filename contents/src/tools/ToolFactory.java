package tools;

/*
 * this is an interface for the Tool Concrete Factory class 
 * for the user to tell the program which object they want to initliaze 
 */
public abstract class ToolFactory {
	public abstract Tool createTool(String type); 
}

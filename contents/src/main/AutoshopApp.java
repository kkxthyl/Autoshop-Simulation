package main;
import javax.swing.JFrame;

public class AutoshopApp extends JFrame {
	
	public AutoshopApp(String title) {
		super(title);
		this.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
		this.setLocation(0, 0);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		AutoshopPanel bpnl = new AutoshopPanel(this);
		this.add(bpnl); 
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	public static void main(String[] args) {
		AutoshopApp app = new AutoshopApp("Autoshop App");
	}
}

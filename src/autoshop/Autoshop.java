package autoshop;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import main.AutoshopPanel;
import processing.core.PVector;
import util.ImageLoader;

/*
 * this class holds all the images, fields, and methods related to the environment of the autoshop
 * all the images loaded into this class were made by me on Adobe Illustrator  
 * separate methods for drawing the different scenes 
 * a few click methods for detecting if one object hit another or the mouse clicked the object
 * 
 * ECO: different angles of the car and the autoshop 
 * ECO: the animated lift that elevates the car and lowers the car
 */
public class Autoshop {
	private BufferedImage startScreen, startButton, autoshopOutside, autoshopLiftDown, autoshopLiftUp, autoshopLeft, autoshopRight, redArrow, greenArrow, bar, endScreen, restartButton; 
	private boolean upSelect = false; 
	private PVector barPos; 
	
	public Autoshop(){
		startScreen = ImageLoader.loadImage("assets/startPage.png"); 
		endScreen = ImageLoader.loadImage("assets/restart.png");
		restartButton = ImageLoader.loadImage("assets/restartButton.png");
		startButton = ImageLoader.loadImage("assets/startButton.png"); 
		autoshopOutside = ImageLoader.loadImage("assets/garage/garageOutside.png");
		autoshopLiftDown = ImageLoader.loadImage("assets/garage/garage-liftDown.png");
		autoshopLiftUp = ImageLoader.loadImage("assets/garage/garage-liftUp.png");
		autoshopLeft = ImageLoader.loadImage("assets/garage/garageLeft.png");
		autoshopRight = ImageLoader.loadImage("assets/garage/garageRight.png");
		greenArrow = ImageLoader.loadImage("assets/greenArrow.png");
		redArrow = ImageLoader.loadImage("assets/redArrow.png");
		bar = ImageLoader.loadImage("assets/liftBar.png");
		
		barPos = new PVector(450, 625); 
	}
	
	public void drawScreen(Graphics2D g2, String type) {
		AffineTransform at = g2.getTransform();
		g2.translate(0, 0);
		g2.scale(1, 1);
		if (type == "start") {
			g2.drawImage(startScreen, 0, 0, AutoshopPanel.W_WIDTH, AutoshopPanel.W_HEIGHT, null);
		}
		else if (type == "end"){
			g2.drawImage(endScreen, 0, 0, AutoshopPanel.W_WIDTH, AutoshopPanel.W_HEIGHT, null);
		}
		
		g2.setTransform(at);
	}

	public void drawStartButton(Graphics2D g2) {
		AffineTransform at = g2.getTransform();
		g2.translate(380, 620);
		g2.scale(0.2, 0.2);
		g2.drawImage(startButton, -startButton.getWidth()/2, -startButton.getHeight()/2, null);
		g2.setTransform(at);
	}
	
	public void drawRestartButton(Graphics2D g2) {
		AffineTransform at = g2.getTransform();
		g2.translate(AutoshopPanel.W_WIDTH/2, AutoshopPanel.W_HEIGHT/3);
		g2.scale(0.2, 0.2);
		g2.drawImage(restartButton, -restartButton.getWidth()/2, -restartButton.getHeight()/2, null);
		g2.setTransform(at);
	}
	
	public void drawAutoshop(Graphics2D g2, String type) {
		AffineTransform at = g2.getTransform();
		g2.translate(0, 0);
		g2.scale(1, 1);
		if (type == "outside") {
			g2.drawImage(autoshopOutside, 0, 0, AutoshopPanel.W_WIDTH, AutoshopPanel.W_HEIGHT, null);
		}
		else if (type == "liftDown") {
			g2.drawImage(autoshopLiftDown, 0, 0,AutoshopPanel.W_WIDTH, AutoshopPanel.W_HEIGHT, null);
		}
		else if (type == "liftUp") {
			g2.drawImage(autoshopLiftUp, 0, 0,AutoshopPanel.W_WIDTH, AutoshopPanel.W_HEIGHT, null);
		}
		else if (type == "left") {
			g2.drawImage(autoshopLeft, 0, 0,AutoshopPanel.W_WIDTH, AutoshopPanel.W_HEIGHT, null);
		}
		else if (type == "right") {
			g2.drawImage(autoshopRight, 0, 0,AutoshopPanel.W_WIDTH, AutoshopPanel.W_HEIGHT, null);
		}
		g2.setTransform(at);
	}
	
	public void drawEnterArrow(Graphics2D g2) {
		AffineTransform at = g2.getTransform();
		g2.translate(450, 280);
		g2.rotate(45);
		g2.scale(0.2,0.2);
		g2.drawImage(greenArrow, -greenArrow.getWidth()/2, -greenArrow.getHeight()/2, null);
		g2.setTransform(at);
	}
	
	public void drawUpArrow(Graphics2D g2) {
		AffineTransform at = g2.getTransform();
		g2.translate(370, 415);
		g2.scale(0.2,0.2);
		g2.drawImage(greenArrow, -greenArrow.getWidth()/2, -greenArrow.getHeight()/2, null);
		g2.setTransform(at);
		upSelect = true; 
	}
	
	public void drawDownArrow(Graphics2D g2) {
		AffineTransform at = g2.getTransform();
		g2.translate(340, 410);
		g2.scale(0.2,0.2);
		g2.drawImage(redArrow, -redArrow.getWidth()/2, -redArrow.getHeight()/2, null);
		g2.setTransform(at);
		upSelect = true; 
	}
	
	public void drawBar(Graphics2D g2) {
		AffineTransform at = g2.getTransform();
		g2.translate(barPos.x, barPos.y);
		g2.scale(0.25,0.25);
		g2.drawImage(bar, -greenArrow.getWidth()/2, -greenArrow.getHeight()/2, null);
		g2.drawImage(bar, -greenArrow.getWidth()/2+900, -greenArrow.getHeight()/2, null);
		g2.setTransform(at);
	}
	
	public boolean moveUP() {
		if (barPos.y > 480) {
			barPos.y -= 10; 
			return false;
		}
		
		return true; 
	}
	
	public boolean moveDown() {
		if (barPos.y < 600) {
			barPos.y += 5; 
			return false;
		}
		
		return true; 
	}
	
	public boolean startButtonclicked(double x, double y){
		boolean clicked = false;
		int xPos = 380; 
		int yPos = 620;
		
		if (x > (xPos - ((double) startButton.getWidth()) / 2 * 0.2) && x < (xPos + ((double) startButton.getWidth())/2*0.2) && y > (yPos - ((double) startButton.getHeight())/2*0.2) && y < (yPos + ((double) startButton.getHeight())/2*0.2)) 
			clicked = true;
		
		return clicked;
	}
	
	public boolean restartButtonClicked(double x, double y){
		boolean clicked = false;
		int xPos = AutoshopPanel.W_WIDTH/2; 
		int yPos = AutoshopPanel.W_HEIGHT/3; ;
		
		if (x > (xPos - ((double) restartButton.getWidth()) / 2 * 0.2) && x < (xPos + ((double) restartButton.getWidth())/2*0.2) && y > (yPos - ((double) restartButton.getHeight())/2*0.2) && y < (yPos + ((double) restartButton.getHeight())/2*0.2)) 
			clicked = true;
		
		return clicked;
	}
	
	public boolean arrowClicked(double x, double y){
		boolean clicked = false;
		int xPos = 450; 
		int yPos = 280;
		if (upSelect == true) {
			xPos = 370; 
			yPos = 415;
		
		}
		
		if (x > (xPos - ((double) greenArrow.getWidth()) / 2 * 0.2) && x < (xPos + ((double) greenArrow.getWidth())/2*0.2) && y > (yPos - ((double) greenArrow.getHeight())/2*0.2) && y < (yPos + ((double) greenArrow.getHeight())/2*0.2)) 
			clicked = true;
		
		return clicked;
	}
	
	public boolean arrowClickedRed(double x, double y){
		boolean clicked = false;
		int xPos = 340; 
		int yPos = 405;
		
		if (x > (xPos - ((double) redArrow.getWidth()) / 2 * 0.2) && x < (xPos + ((double) redArrow.getWidth())/2*0.2) && y > (yPos - ((double) redArrow.getHeight())/2*0.2) && y < (yPos + ((double) redArrow.getHeight())/2*0.2)) 
			clicked = true;
		
		return clicked;
	}
	
}

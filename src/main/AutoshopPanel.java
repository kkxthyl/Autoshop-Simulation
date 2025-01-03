package main;
import java.awt.Color;

/*
 * https://mixkit.co/free-sound-effects/click/
 * https://www.soundsnap.com/hydraulic_lift
 *https://www.youtube.com/watch?v=tpNMZyVAvX4&t=9s
 *https://www.youtube.com/watch?v=tpNMZyVAvX4&t=9s
 *https://www.soundsnap.com/tags/mechanic
 */
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import Bird.Bird;
import Bird.BirdConcreteFactory;
import Bird.BirdFactory;
import Bird.BlackBird;
import Bird.GreyBird;
import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import environment.Enviro;
import environment.SkyDecorator;
import environment.TreeDecorator;
import processing.core.PVector;
import tools.Bolts;
import tools.Drill;
import tools.Tires;
import tools.ToolConcreteFactory;
import tools.ToolFactory;
import util.ImageLoader;
import util.MinimHelper;
import util.Util;
import autoshop.Autoshop;
import autoshop.Car;
import autoshop.Info;
import autoshop.Smoke;

/*
 * ECO: there is a search algorithm used for the order that the birds are drawn according to their randomly generated sizes
 * ECO: there is an ArrayList that contains two different types of objects 
 * ECO: FSM states are used to determine what comes next in the simulation
 */

public class AutoshopPanel extends JPanel implements ActionListener{
	public static int W_WIDTH = 1200;
	public static int W_HEIGHT = 800;
	
	//private boolean dragDrill = false; 
	private boolean frontRemove = false; 
	private boolean backRemove = false; 
	private boolean frontBolts = false; 
	private boolean backBolts = false; 
	private boolean tireStackClick = false; 
	private boolean finishDrill = false;
	private boolean drawSmoke = false; 
	
	
	private ArrayList<Bird> birdArr = new ArrayList<Bird>(); 
	
	private int state = 0;
	private Timer timer; 
	private int smokeTimer = 0;
	private int drillTireTimer = 30; 
	
	// variables for holding mouse position
	private double mouseX;
	private double mouseY;
	
	private Autoshop autoshop; 
	private Car car; 
	private Tires frontTire; 
	private Tires backTire;  
	private Drill drill; 
	private Bolts bolts; 
	private Info info; 
	private Smoke smoke; 
	private Enviro enviro;
	
	ToolFactory toolFactory; 
	BirdFactory birdFactory; 
	
	private JFrame frame; 
	
	private Minim minim; 
	private AudioPlayer bgEffect, click, bgMusic, hydraulicLift, drillSound, tireSound, boltSound, newTireSound;
	
	public AutoshopPanel(JFrame frame) {
		setPreferredSize(new Dimension(W_WIDTH, W_HEIGHT));
		this.frame = frame; 
		
		autoshop = new Autoshop();
		car = new Car();
		enviro = new Enviro(); 
		info = new Info(); 
		smoke = new Smoke(320, 450, 200, 80);
		
		toolFactory = new ToolConcreteFactory(); 
		frontTire = (Tires)toolFactory.createTool("frontTire");
		backTire = (Tires)toolFactory.createTool("backTire");
		bolts = (Bolts)toolFactory.createTool("bolts");
		drill = (Drill)toolFactory.createTool("drill");
		
		birdFactory = new BirdConcreteFactory(); 
		loadBirds(); 
		
		timer = new Timer(30, this);
		timer.start();

		minim = new Minim(new MinimHelper());
		loadMusic(); 
		
		MyMouseListener ml = new MyMouseListener();
		addMouseListener(ml);
		
		MyMouseMotionListener mml = new MyMouseMotionListener();
		addMouseMotionListener(mml);
	}
	
	public void loadBirds() {
		
		birdArr.add(birdFactory.createBird("blackBirdOne")); 
		birdArr.add(birdFactory.createBird("greyBird")); 
		birdArr.add(birdFactory.createBird("blackBirdTwo")); 
		for (int i = 0; i < birdArr.size(); i++) {
	        for (int j = i+1; j < birdArr.size(); j++) {
	        	 System.out.println(birdArr.get(j).getScale());
	        	 
	            if (birdArr.get(j).getScale() < birdArr.get(i).getScale()) {
	                Bird temp = birdArr.get(i);
	                birdArr.set(i, birdArr.get(j));
	                birdArr.set(j, temp);
	            }
	           
	        }
	        System.out.println(birdArr.get(i).getScale());
		}
	}
	
	public void loadMusic() {
		bgEffect = minim.loadFile("audio/background.mp3");
		bgMusic = minim.loadFile("audio/music.mp3");
		//https://www.youtube.com/watch?v=pqoFaU6julY
		hydraulicLift = minim.loadFile("audio/hydraulic.mp3");
		//https://www.youtube.com/watch?v=lev-KeieTzU
		drillSound = minim.loadFile("audio/drill.mp3");
		//https://www.youtube.com/watch?v=qEGB2jF6pZw
		tireSound = minim.loadFile("audio/tires.mp3");
		//https://www.youtube.com/watch?v=h6_8SlZZwvQ
		click = minim.loadFile("audio/click.mp3");
		//https://www.youtube.com/watch?v=fO3mrrfkhjs
		boltSound = minim.loadFile("audio/bolt.mp3");
		//https://www.youtube.com/watch?v=wSRUfwyPGkw
		newTireSound = minim.loadFile("audio/newTire.mp3");
		
		bgEffect.loop();
		bgEffect.setGain(-10);
		//bgMusic.loop();
		//bgMusic.setGain(-12);
		boltSound.setGain(90);
		hydraulicLift.cue(2000);
		tireSound.cue(9000);
		click.cue(1000);
	}

	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		//System.out.println(state);
		if (state == 0) {
			autoshop.drawScreen(g2, "start");
			autoshop.drawStartButton(g2);
			
		}
		
		else if (state == 1) {
			autoshop.drawAutoshop(g2, "outside");
			autoshop.drawEnterArrow(g2);
			car.drawCarProfile(g2);
			frontTire.draw(g2);
			backTire.draw(g2);
			
			info.drawInfo(g2, 1);
		}
		
		else if (state == 2) {
			enviro.draw(g2, 80, 450);
			
			autoshop.drawAutoshop(g2, "liftDown");
			car.drawCarBack(g2, true);
			autoshop.drawUpArrow(g2);
			drill.draw(g2);
			bolts.draw(g2);
			
			info.drawInfo(g2, 2);
		}
		
		else if (state == 3) {
			enviro.draw(g2, 80, 450);
			
			autoshop.drawAutoshop(g2, "liftUp");
			car.drawCarBack(g2, false);
			autoshop.drawUpArrow(g2);
			autoshop.drawBar(g2);
			drill.draw(g2);
			bolts.draw(g2);
			
		}
		
		else if (state == 4) {
			
			autoshop.drawAutoshop(g2, "left");
			frontTire.setPos(340, 460);
			backTire.setPos(900, 460);
			frontTire.setScale(0.23);
			backTire.setScale(0.23);
			
			if (!frontRemove) {
				frontTire.draw(g2);
			}
			if (!backRemove) {
				backTire.draw(g2);
			}
			
			if (frontRemove && backRemove) {
				//state = 5;
				frontTire.setPos(-100,100);
				frontTire.setScale(0.23);
				backTire.setPos(-100,100);
				backTire.setScale(0.23);
				frontTire.drawStackedTires(g2, 0);
				
				info.drawInfo(g2, 5);
			}
			else {
				info.drawInfo(g2, 4);
			}
		
			drill.drawDrillDrag(g2);
			bolts.drawBoltsDrag(g2);
			
			if (drawSmoke) {
				smoke.drawSmoke(g2);
				drillSound.play();
			}
			else {
				drillSound.pause();
			}
			hydraulicLift.pause();
			hydraulicLift.cue(2000);
		}
		
		else if (state == 5) {
			autoshop.drawAutoshop(g2, "left");
			drill.drawDrillDrag(g2);
			bolts.drawBoltsDrag(g2);
			
			frontTire.drawStackedTires(g2, 0);
			frontTire.drawNewTire(g2);
			info.drawInfo(g2, 5);
		}
		
		else if (state == 6) {
			autoshop.drawAutoshop(g2, "left");
			drill.drawDrillDrag(g2);
			bolts.drawBoltsDrag(g2);
			frontTire.drawNewTire(g2);
			backTire.drawNewTire(g2);
			
			frontTire.drawStackedTires(g2, 0);
			info.drawInfo(g2, 5);
		}
		
		else if (state == 7) {
			autoshop.drawAutoshop(g2, "left");
			drill.drawDrillDrag(g2);
			frontTire.drawNewTire(g2);
			backTire.drawNewTire(g2);
			
			if (frontBolts) {
				bolts.drawRimBolts(g2, 0, 0);
				
			}
			if (backBolts) {
				bolts.drawRimBolts(g2, 1, 0);
			}
			
			bolts.drawBoltsDrag(g2);
			info.drawInfo(g2, 5);
		}
		
		else if (state == 8) {
			enviro.draw(g2, 800, 150);
			enviro.drawBirds(birdArr);
			for (int i = 0; i < birdArr.size(); i++) {
				birdArr.get(i).draw(g2);
				birdArr.get(i).move();
			}
			
			autoshop.drawAutoshop(g2, "right");
			frontTire.setPos(300,460);
			backTire.setPos(850,470);
			
			if (!frontRemove) {
				frontTire.draw(g2);
			}
			if (!backRemove) {
				backTire.draw(g2);
			}
			
			if (frontRemove && backRemove) {
				frontTire.setPos(-100,100);
				frontTire.setScale(0.23);
				backTire.setPos(-100,100);
				backTire.setScale(0.23);
				frontTire.drawStackedTires(g2, 1);
				
			}
			
			
			drill.drawDrillDrag(g2);
			bolts.drawBoltsDrag(g2);
			
			if (drawSmoke) {
				smoke.drawSmoke(g2);
				drillSound.play();
				
			}
			else {
				drillSound.rewind();
				drillSound.pause();
			}
			info.drawInfo(g2, 8);
		}
		
		else if (state == 9) {
			enviro.draw(g2, 800, 150);
			enviro.drawBirds(birdArr);
			for (int i = 0; i < birdArr.size(); i++) {
				birdArr.get(i).draw(g2);
				birdArr.get(i).move();
			}
			
			autoshop.drawAutoshop(g2, "right");
			drill.drawDrillDrag(g2);
			bolts.drawBoltsDrag(g2);

			frontTire.drawStackedTires(g2, 1);
			frontTire.drawNewTire(g2);
			backTire.drawNewTire(g2);
			
			info.drawInfo(g2, 8);
		}
		
		else if (state == 10 || state == 11) {
			enviro.draw(g2, 800, 150);
			
			autoshop.drawAutoshop(g2, "right");
			drill.drawDrillDrag(g2);
			frontTire.drawNewTire(g2);
			backTire.drawNewTire(g2);
			
			if (frontBolts) {
				bolts.drawRimBolts(g2, 0, 11);
				
			}
			if (backBolts) {
				bolts.drawRimBolts(g2, 1, 11);
			}
			
			if (backBolts && frontBolts) {
				state = 12;
			}
			
			bolts.drawBoltsDrag(g2);
			info.drawInfo(g2, 8);
		}
		
		else if (state == 12) {
			enviro.draw(g2, 80, 450);
			
			autoshop.drawAutoshop(g2, "liftUp");
			car.drawCarBack(g2, false);
			autoshop.drawBar(g2);
			autoshop.drawDownArrow(g2);
			info.drawInfo(g2,  12);
		}
		
		else if (state == 13) {
			enviro.draw(g2, 80, 450);
			
			autoshop.drawAutoshop(g2, "liftUp");
			autoshop.drawBar(g2);
			car.drawCarBack(g2, false);
			autoshop.drawDownArrow(g2);
		}
		
		else if (state == 14) {
			enviro.draw(g2, 80, 450);
			
			autoshop.drawAutoshop(g2, "liftDown");
			car.drawCarBack(g2, true);
			autoshop.drawDownArrow(g2);
			hydraulicLift.pause();
		}
		
		else if (state == 15) {
			autoshop.drawScreen(g2,"end");
			autoshop.drawRestartButton(g2);
			g2.setColor(new Color(0,0, 0,  90));
			g2.fill(new Rectangle2D.Double(0, 0, AutoshopPanel.W_WIDTH, AutoshopPanel.W_HEIGHT));
			
		}
		
	}

	public void actionPerformed(ActionEvent e) {
		smoke.setWidth(smokeTimer/2);
		//smoke.setHeight(drillTimer *2);
		
		smokeTimer++;
		if (smokeTimer >= 60) {
			smokeTimer = 60;
		}
		
		if (state == 1) {
			if (!car.moveProfile()) {
				tireSound.play();
			}
			else {
				tireSound.pause();
			}
			frontTire.moveFrontTire();
			backTire.moveBackTire();
		}
		
		if (state == 2){
			car.moveIn();
		}
		
		if (state == 3) {
			car.moveUp();
			autoshop.moveUP();
;			if (car.moveUp() && autoshop.moveUP()) {
				state = 4; 
				drill.setPos(900,  580);
				bolts.setPos(1050,  580);
			}
		}
		
		if (state == 14) {
			car.moveOut(); 
			if (car.moveOut()) {
				state = 15;
			}
		}
		
		if (state == 13) {
			car.moveDown(); 

			hydraulicLift.play();
			if (autoshop.moveDown()) {
				state = 14; 
				//System.out.println("state == 14");
			}
		}
		
		
		repaint();
	}
	
	public class MyMouseListener extends MouseAdapter {

		public void mouseClicked(MouseEvent e) {
			mouseX = e.getX();
			mouseY = e.getY();
			
			if (state ==2 && car.moveIn() && autoshop.arrowClicked(mouseX, mouseY)) {
				state = 3;

				hydraulicLift.play(0);
			} 
			
			if (state == 0 && autoshop.startButtonclicked(mouseX, mouseY)) {
				state = 1; 
				
			}
			
			if (state ==1 && car.moveProfile() && autoshop.arrowClicked(mouseX, mouseY)) {
				state = 2;
			}
			
			if (state == 12 && autoshop.arrowClickedRed(mouseX,  mouseY)) {
				state = 13;
			}
			
			if (state == 15 && autoshop.restartButtonClicked(mouseX, mouseY)) {
				frame.dispose(); 
				new AutoshopApp("Pizza App");
			}
			
		}
		
		public void mousePressed(MouseEvent e) {

			click.cue(1000);
			click.play();
		}
		
		
		public void mouseReleased(MouseEvent e) {
			if (state == 4) {
				drill.setPos(900, 580);
				bolts.setPos(1050, 600);
				
				if (frontRemove && backRemove) {
					finishDrill = true; 
				}
			}
		
			
			else if (state == 5) {
				state = 6;
				frontTire.setPos(340, 460);
				newTireSound.play(0);
			}
			
			else if (state == 6) {
				state = 7;
				backTire.setPos(900, 460);
				newTireSound.play(0);
			}
			
			else if (state == 7) {
				bolts.setPos(1050, 600);
				if (frontBolts && backBolts) {
					state = 8;
					drill.setPos(210, 600);
					bolts.setPos(90, 610);
					frontRemove = false; 
					backRemove = false; 
				}
			}
			
			else if (state == 8) {
				drill.setPos(210, 600);
			}
			
			else if (state == 9) {
				state = 10; 
				frontBolts = false; 
				backBolts = false;
				frontTire.setPos(300, 460);
				newTireSound.play(0);
			}
			
			else if (state == 10) {
				state = 11;
				backTire.setPos(850, 460);
				bolts.setPos(90, 610);
				newTireSound.play(0);
			}
			else if (state == 11) {
				bolts.setPos(90, 610);
			}
		}
		
	}
	
	public class MyMouseMotionListener extends MouseAdapter {

		
		public void mouseDragged(MouseEvent e){
			if (drill.clicked(e.getX(), e.getY()) && (state == 4 || state == 8)) {
				drill.setPos(e.getX(), e.getY());
				
				
				if (drill.hitFront(frontTire) &&(state == 4 || state == 8)) {
					if (drillTireTimer >0 && !frontRemove ) {
						drillTireTimer--; 
						drawSmoke = true; 
						if (state == 4) {
							smoke.setPos(320, 450);
						}
						else {
							smoke.setPos(280, 450);
						}
					}
					if (drillTireTimer == 0) {
						drillTireTimer = 30; 
						frontRemove = true;
						drawSmoke = false;
					}
				}
				if (drill.hitFront(backTire) && (state == 4 || state == 8)) {
					if (drillTireTimer >0 && !backRemove ) {
						drillTireTimer--; 
						drawSmoke = true; 
						if (state == 4) {
							smoke.setPos(880, 450);
						}
						else {
							smoke.setPos(840, 450);
						}
					}
					if (drillTireTimer == 0) {
						drillTireTimer = 30; 
						backRemove = true;
						drawSmoke = false;
					}
				}
			}
			
			if (state == 5 || state == 9) {
				frontTire.setPos(e.getX(), e.getY());
			}
			
			if (state == 4 && frontTire.clickedTireStack(e.getX(),  e.getY(), 0)) {
				//tireStackClick = true; 
				state = 5;
			}
			
			if (state == 6 || state == 10) {
				backTire.setPos(e.getX(), e.getY());
			}
			
			if (bolts.clicked(e.getX(), e.getY()) && (state == 7 || state == 11)){
				bolts.setPos(e.getX(), e.getY());
				
				if (bolts.hitTire(frontTire)) {
					frontBolts = true; 
					boltSound.cue(7500);
					boltSound.play(0);
				}
				if (bolts.hitTire(backTire)) {
					backBolts = true; 
					boltSound.cue(7500);
					boltSound.play(0);
				}
				
			}
			
			if (state == 8 && frontTire.clickedTireStack(e.getX(), e.getY(), 9)) {
				state = 9; 
			}
			
		}
		
	}
	
	
}

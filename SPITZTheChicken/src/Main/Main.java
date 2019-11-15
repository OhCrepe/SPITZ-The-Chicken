package Main;

/*
 * SPITZ the Chicken, made by Tyler Thorn
 */

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import Main.Entities.Coin;
import Main.Entities.Crate;
import Main.Entities.Seed;
import Main.Entities.Enemies.BarbedWire;
import Main.Entities.Enemies.CannonPig;

/*TODO
 * -Fix CannonPigs, their coordinates are at 0, 0 for some reason
 * -Add CannonPigs AI
 * -Add CannonBalls
 * -Collision
 * -CannonPigs in general really
 */

public class Main extends Canvas implements Runnable, KeyListener, MouseListener{
	private static final long serialVersionUID = 1L;
	
	public static int width = 1024;
	public static int totalWidth = 1024;
	public static int height = width * 3/4;
	public static int playerX = 0, playerY = 0;
	public static int xVel = 0, yVel = 0;
	public static int playerSize = 64;
	public static String direction = "RIGHT", shootDirection;
	public static int offSet = 0;
	
	public static int coinSprite = 0;
	
	public static Rectangle playerCollision;
	
	private Thread thread;
	private JFrame frame;
	private boolean running = false;
	public boolean moveLeft = false, moveRight = false, jumpPressed = false;
	public static boolean tileMapDone = false;
	
	public static int tileMap[][];
	public static Rectangle rectMap[][];
	
	public static int playerHealth, lives;
	
	public static ArrayList<Seed> seeds = new ArrayList<Seed>();
	public static ArrayList<BarbedWire> wires = new ArrayList<BarbedWire>();
	public static ArrayList<Coin> coins = new ArrayList<Coin>();
	public static ArrayList<Crate> crates = new ArrayList<Crate>();
	public static ArrayList<CannonPig> cPigs = new ArrayList<CannonPig>();
	
	public static boolean immunity = false;
	public static int immuneCounter;

	public int shootCounter = 0;
	public static int score;

	public static int hiscore;
	
	Font font = new Font("Courier New", Font.CENTER_BASELINE, 22);
	static String levelName = "TEST LEVEL NAME PLEZ WORK";
	
	public static Image[] ammoBar = new Image[4];
	BufferedImage[] dirts = new BufferedImage[7];
	BufferedImage[] wireSprites = new BufferedImage[10];
	BufferedImage[] coinSprites = new BufferedImage[10];
	BufferedImage[] crateSprites = new BufferedImage[3];
	BufferedImage[] idlePigSprites = new BufferedImage[6];
	Image HUDBackground;
	/* 0 - Top
	 * 1 - Middle
	 * 2 - Bottom
	 * 3 - LeftLedge
	 * 4 - RightLedge
	 * 5 - LeftFixer
	 * 6 - RightFixer
	 */
	BufferedImage seed = new BufferedImage(6, 6, BufferedImage.TYPE_INT_ARGB);
	
	BufferedImage life = new BufferedImage(23, 33, BufferedImage.TYPE_INT_ARGB);
	BufferedImage lifeMany = new BufferedImage(70, 33, BufferedImage.TYPE_INT_ARGB);
	
	BufferedImage spitzHeadLeft, spitzHeadLeftOpen = new BufferedImage(64, 68, BufferedImage.TYPE_INT_ARGB);
	BufferedImage spitzHeadRight, spitzHeadRightOpen = new BufferedImage(64, 68, BufferedImage.TYPE_INT_ARGB);
	BufferedImage spitzBodyLeft = new BufferedImage(64, 68, BufferedImage.TYPE_INT_ARGB);
	BufferedImage spitzBodyRight = new BufferedImage(64, 68, BufferedImage.TYPE_INT_ARGB);
	
	BufferedImage damageIcon = new BufferedImage(9, 9, BufferedImage.TYPE_INT_ARGB);
	
	Image image;
	Graphics graphics;
	
	public Main() throws IOException{
		
		Dimension size = new Dimension(width, height);
		setPreferredSize(size);
		HUDBackground = ImageIO.read(new File("resources/sprites/HUD/HUDBackground.png"));
		ammoBar[0] = ImageIO.read(new File("resources/sprites/HUD/AmmoFull.png"));
		ammoBar[1] = ImageIO.read(new File("resources/sprites/HUD/AmmoYellow.png"));
		ammoBar[2] = ImageIO.read(new File("resources/sprites/HUD/AmmoRed.png"));
		ammoBar[3] = ImageIO.read(new File("resources/sprites/HUD/AmmoEmpty.png"));
		life = ImageIO.read(new File("resources/sprites/HUD/Lives.png"));
		lifeMany = ImageIO.read(new File("resources/sprites/HUD/LivesMany.png"));
		
		for(int i = 0; i < dirts.length; i++){
			dirts[i] = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
		}
		
		dirts[0] = ImageIO.read(new File("resources/sprites/Tiles/DirtTop.png"));
		dirts[1] = ImageIO.read(new File("resources/sprites/Tiles/DirtCentral.png"));
		dirts[2] = ImageIO.read(new File("resources/sprites/Tiles/DirtBottom.png"));
		dirts[3] = ImageIO.read(new File("resources/sprites/Tiles/LeftLedge.png"));
		dirts[4] = ImageIO.read(new File("resources/sprites/Tiles/RightLedge.png"));
		dirts[5] = ImageIO.read(new File("resources/sprites/Tiles/DirtFixerLeft.png"));
		dirts[6] = ImageIO.read(new File("resources/sprites/Tiles/DirtFixerRight.png"));
		
		try {
			for(int i = 1; i < 7; i++){
				idlePigSprites[i - 1] = ImageIO.read(new File("resources/sprites/Entities/PigCannon/Idle/Sprite" + i + ".png"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		coinSprites[0] = ImageIO.read(new File("resources/sprites/Entities/Coin1.png"));
		coinSprites[1] = ImageIO.read(new File("resources/sprites/Entities/Coin2.png"));
		coinSprites[2] = ImageIO.read(new File("resources/sprites/Entities/Coin3.png"));
		coinSprites[3] = ImageIO.read(new File("resources/sprites/Entities/Coin4.png"));
		coinSprites[4] = ImageIO.read(new File("resources/sprites/Entities/Coin5.png"));
		coinSprites[5] = ImageIO.read(new File("resources/sprites/Entities/Coin6.png"));
		coinSprites[6] = ImageIO.read(new File("resources/sprites/Entities/Coin7.png"));
		coinSprites[7] = ImageIO.read(new File("resources/sprites/Entities/Coin8.png"));
		coinSprites[8] = ImageIO.read(new File("resources/sprites/Entities/Coin9.png"));
		coinSprites[9] = ImageIO.read(new File("resources/sprites/Entities/Coin10.png"));
		
		crateSprites[0] = ImageIO.read(new File("resources/sprites/Entities/Box1.png"));
		crateSprites[1] = ImageIO.read(new File("resources/sprites/Entities/Box2.png"));
		crateSprites[2] = ImageIO.read(new File("resources/sprites/Entities/Box3.png"));
		
		wireSprites[0] = ImageIO.read(new File("resources/sprites/Entities/BarbedConnector.png"));
		wireSprites[1] = ImageIO.read(new File("resources/sprites/Entities/BarbedLeftBottom.png"));
		wireSprites[2] = ImageIO.read(new File("resources/sprites/Entities/BarbedLeftCentre.png"));
		wireSprites[3] = ImageIO.read(new File("resources/sprites/Entities/BarbedLeftTop.png"));
		wireSprites[4] = ImageIO.read(new File("resources/sprites/Entities/BarbedRightBottom.png"));
		wireSprites[5] = ImageIO.read(new File("resources/sprites/Entities/BarbedRightCentre.png"));
		wireSprites[6] = ImageIO.read(new File("resources/sprites/Entities/BarbedRightTop.png"));
		wireSprites[7] = ImageIO.read(new File("resources/sprites/Entities/BarbedSingleBottom.png"));
		wireSprites[8] = ImageIO.read(new File("resources/sprites/Entities/BarbedSingleCentre.png"));
		wireSprites[9] = ImageIO.read(new File("resources/sprites/Entities/BarbedSingleTop.png"));
		
		seed = ImageIO.read(new File("resources/sprites/Entities/Seed.png"));
		
		spitzHeadLeft = ImageIO.read(new File("resources/sprites/Entities/SPITZHeadLeft.png"));
		spitzHeadRight = ImageIO.read(new File("resources/sprites/Entities/SPITZHeadRight.png"));
		spitzBodyLeft = ImageIO.read(new File("resources/sprites/Entities/SPITZBodyLeft.png"));
		spitzBodyRight = ImageIO.read(new File("resources/sprites/Entities/SPITZBodyRight.png"));
		spitzHeadLeftOpen = ImageIO.read(new File("resources/sprites/Entities/SPITZHeadLeftOpen.png"));
		spitzHeadRightOpen = ImageIO.read(new File("resources/sprites/Entities/SPITZHeadRightOpen.png"));
		
		damageIcon = ImageIO.read(new File("resources/sprites/Decor/DamageIcon.png"));
			
		this.addKeyListener(this);
		this.addMouseListener(this);
		
		frame = new JFrame();
		
	}

	public synchronized void start(){
		
		running = true;
		thread = new Thread(this, "Game");
		thread.start();		
		
	}
	
	public synchronized void stop(){
		
		running = false;
		try{
			thread.join();
		} catch (InterruptedException e){
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) throws IOException{
				
		readLevel("test", true);

		Main main = new Main();
		
		//Properties of the game window
		main.frame.setResizable(false);
		main.frame.setTitle("SPITZ the Chicken");
		main.frame.add(main);
		main.frame.pack();
		main.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		main.frame.setLocationRelativeTo(null);
		main.frame.setVisible(true);
		main.frame.setFocusable(true);
		
		main.start();
		
	}
	
	public static void readLevel(String fileName, boolean first) throws IOException{
	
		levelName = fileName;
		
		FileReader file = new FileReader("resources/levels/" + levelName + ".txt");
		@SuppressWarnings("resource")
		BufferedReader reader = new BufferedReader(file);
		
		String line;
		line = reader.readLine();
		totalWidth = line.length() * 16;
		
		tileMap = new int[totalWidth/16][(height-80)/16];
		rectMap = new Rectangle[totalWidth/16][(height-80)/16];
		
		for(int y = 0; y < (height-80)/16; y++){
			if (y != 0){
				line = reader.readLine();
			}
			if(line.length() < totalWidth/16){
				while(line.length() != totalWidth/16){
					line = line + "0";
				}
			}
			for(int x = 0; x < (totalWidth/16); x++){
				String digit = line.substring(x, x + 1);
				try{
					tileMap[x][y] = Integer.parseInt(digit);
				}catch(NumberFormatException e){
					if(digit.equals("C")){
						playerX = x * 16;
						playerY = y * 16;
						tileMap[x][y] = 0;
						playerCollision = new Rectangle(playerX, playerY, playerSize, playerSize);
					}
					if(digit.equals("W")){
						BarbedWire wire = new BarbedWire(x * 16, y * 16);
						wires.add(wire);
						tileMap[x][y] = 10;
					}
					if(digit.equals("c")){
						Coin coin = new Coin(x*16, y*16);
						coins.add(coin);
						tileMap[x][y] = 0;
					}
					if(digit.equals("b")){
						Crate crate = new Crate(x * 16, y * 16);
						crates.add(crate);
						System.out.println("Crate created at " + crate.getX() + ", " + crate.getY());
						tileMap[x][y] = 0;
					}
					if(digit.equals("p")){
						CannonPig pig = new CannonPig(x * 16, y * 16);
						pig.setX(x * 16);
						pig.setY(y * 16);
						cPigs.add(pig);
						System.out.println("CannonPig created at " + pig.getX() + ", " + pig.getY());
						tileMap[x][y] = 0;
					}
				}
			}
		}
		
		for(int i = 0; i < wires.size(); i++){
			//Decides which sprite the wires should use
			int x = wires.get(i).getX()/16;
			int y = wires.get(i).getY()/16;
			boolean top = false, left = false, right = false, bottom = false;
			
			if(x < 3){
				left = true;
			} else if(x >= totalWidth/16 - 2){
				right = true;
			} else {
				if((tileMap[x - 1][y] == 0 || tileMap[x - 1][y + 1] == 0 || tileMap[x - 1][y + 2] == 0) && (tileMap[x - 3][y] != 10)){
					left = true;
				}
				if((tileMap[x + 3][y] == 0 || tileMap[x + 3][y + 1] == 0 || tileMap[x + 3][y + 2] == 0) && (tileMap[x + 3][y] != 10)){
					right = true;
				}
			}
			if(y < 3){
				top = true;
			} else if(y >= (height-80)/16 - 2){
				bottom = true;
			} else {
				if((tileMap[x][y - 1] == 0 || tileMap[x + 1][y - 1] == 0 || tileMap[x + 2][y - 1] == 0) && (tileMap[x][y - 3] != 10)){
					top = true;
				}
				if((tileMap[x][y + 3] == 0 || tileMap[x + 1][y + 3] == 0 || tileMap[x + 2][y + 3] == 0) && (tileMap[x][y + 3] != 10)){
					bottom = true;
				}
			}
			
			if(!left && !right){
				wires.get(i).setSprite(0);
			}else if(left && bottom && !right && !top){
				wires.get(i).setSprite(1);
			}else if(left && !top && !bottom && !right){
				wires.get(i).setSprite(2);
			}else if(left && top && !bottom && !right){
				wires.get(i).setSprite(3);
			}else if(right && bottom && !top && !left){
				wires.get(i).setSprite(4);
			}else if(right && !top && !bottom && !left){
				wires.get(i).setSprite(5);
			}else if(right && top && !bottom && !left){
				wires.get(i).setSprite(6);
			}else if(left && right && !top && bottom){
				wires.get(i).setSprite(7);
			}else if(left && right && !top && !bottom){
				wires.get(i).setSprite(8);
			}else if(left && right && top && !bottom){
				wires.get(i).setSprite(9);
			}
			
		}
		
		levelName = "test";
		
		playerHealth = 100;
		if(first){
			lives = 5;
		}
		score = 0;
		reader.readLine();
		reader.readLine();
		hiscore = Integer.parseInt(reader.readLine());
		offSet = 0;
		immuneCounter = 0;
		immunity = false;
			
		System.out.println("Loaded level: " + levelName);	
		
		tileMapDone = true;
		
	}

	@Override
	public void run() {
		
		while(running){
			
			doLogic();

			repaint();
			
			try {
				Thread.sleep(1000/60);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		
	}
	
	public void doLogic(){

		playerCollision.x = playerX - offSet + 1;
		playerCollision.y = playerY;
		playerCollision.width = playerSize - 2;
		playerCollision.height = playerSize;
		
		if(shootCounter > 0){
			shootCounter--;
		}
		
		if (moveLeft){
			xVel = -8;
			direction = "LEFT";
		}
		if (moveRight){
			xVel = 8;
			direction = "RIGHT";
		}
		if (!moveRight && !moveLeft) xVel = 0;
		
		if(playerX + xVel <= 0){
			if(xVel < 0){
				xVel = 0;
			}
			playerX = 0;
		}
		if(playerX + playerSize + xVel >= totalWidth){
			if(xVel > 0){
				xVel = 0;
			}
			playerX = totalWidth - playerSize;
		}
			
		if(playerY + playerSize >= this.getHeight()){
			if(yVel > 0){
				yVel = 0;
			}
			playerY = this.getHeight() - 48;
		}
		
		if(seeds != null){
			for(int i = 0; i < seeds.size(); i++){
				seeds.get(i).update();
			}
		}
		
		//PLAYER COLLISION
		boolean collisionDown = false;
		boolean collisionUp = false;
		
		//PLAYER AND TILES
		for(int x = 0; x < totalWidth/16; x++){
			for(int y = 0; y < (height - 80)/16; y++){
				rectMap[x][y] = new Rectangle(x*16 - offSet, y*16, 16, 16);
				if(tileMap[x][y] == 1){
					if(checkDownCollisions(playerCollision, rectMap[x][y])){
						collisionDown = true;
						if(playerY + playerSize + yVel > rectMap[x][y].y && tileMap[x][y-1] != 1){
							playerY = rectMap[x][y].y - playerSize;
						}
						yVel = 0;
					}
					if(checkUpCollisions(playerCollision, rectMap[x][y])){
						collisionUp = true;
						if(tileMap[x][y+1] != 1){
							playerY = rectMap[x][y].y + rectMap[x][y].height + 1;
							if(yVel < 0){
								yVel = 0;
							}
						}
					}
					if(checkLeftCollisions(playerCollision, rectMap[x][y])){
						if(tileMap[x+1][y] != 1){
							if(tileMap[x][y-1] == 1){
								playerX = rectMap[x][y].x + rectMap[x][y].width + 1 + offSet;
								if(xVel < 0){
									xVel = 0;
								}
							}
						}
					}
					if(checkRightCollisions(playerCollision, rectMap[x][y])){
						if(tileMap[x-1][y] != 1){
							if (y != 0){
								if(tileMap[x][y-1] == 1){
									playerX = rectMap[x][y].x - playerSize - 1 + offSet;
										if(xVel > 0){
											xVel = 0;
										}
									}
								}
							else if(tileMap[x][y-1] == 1){
								playerX = rectMap[x][y].x - playerSize - 1;
								if(xVel > 0){
									xVel = 0;
								}
							}
						}
					}
				}
			}						
		}
		
		//PLAYER AND CRATES
		for(int i = 0; i < crates.size(); i++){
			if(checkUpCollisions(playerCollision, crates.get(i).getCollisionRect())){
				yVel = 0;
				doDamage(20);
				crates.remove(i);
				collisionUp = true;
			}
			if(checkRightCollisions(playerCollision, crates.get(i).getCollisionRect())){
				playerX = crates.get(i).getX() - playerSize - 1;
				if(xVel > 0){
					xVel = 0;
				}
			}
			if(checkLeftCollisions(playerCollision, crates.get(i).getCollisionRect())){
				playerX = crates.get(i).getX() + 64;
				if(xVel < 0){
					xVel = 0;
				}
			}
			if(checkDownCollisions(playerCollision, crates.get(i).getCollisionRect())){
				yVel = 0;
				collisionDown = true;
				playerY = crates.get(i).getY() - playerSize;
			}
		}
		
		//THE PART THAT DEALS WITH SEEDS
		for(int i = 0; i < seeds.size(); i++){
			if(seeds.get(i).getX() + 6 - offSet < 0 || seeds.get(i).getX() - offSet > width){
				seeds.get(i).setVisible(false);
				break;
			}
			Rectangle seed = new Rectangle(seeds.get(i).getX() - offSet, seeds.get(i).getY(), 6, 6);
			for(int x = 0; x < totalWidth/16; x++){
				for(int y = 0; y < (height-80)/16; y ++){
					if(checkLeftCollisions(seed, rectMap[x][y]) || checkRightCollisions(seed, rectMap[x][y])){
						if(tileMap[x][y] == 1){
							seeds.get(i).setVisible(false);
						}
					}
				}
			}
		}
		
		for(int i = 0; i< seeds.size(); i++){
			if(!seeds.get(i).isVisible()){
				seeds.remove(i);
				i = i - 1;
			}
		}
		
		//Sorts out the wires (collision, visibility, etc.)
		for(int i = 0; i < wires.size(); i++){
			
			if(wires.get(i).getX() + 48 - offSet < 0 || wires.get(i).getX() - offSet > width){
				wires.get(i).setVisible(false);
			}else{
				wires.get(i).setVisible(true);
				wires.get(i).setCollisionRect(new Rectangle(wires.get(i).getX() - offSet + 2, wires.get(i).getY() + 2, 44, 44));
				if(checkUpCollisions(playerCollision, wires.get(i).getCollisionRect())){
					doDamage(10);
				} else if(checkDownCollisions(playerCollision, wires.get(i).getCollisionRect())){
					doDamage(10);
				} else if(checkLeftCollisions(playerCollision, wires.get(i).getCollisionRect())){
					doDamage(10);
				} else if(checkRightCollisions(playerCollision, wires.get(i).getCollisionRect())){
					doDamage(10);
				}
			}
			
		}
		
		//Sorts out Coins
		for(int i = 0; i < coins.size(); i++){
			
			if(coins.get(i).getX() + 16 - offSet < 0 || coins.get(i).getX() - offSet > width){
				coins.get(i).setVisible(false);
			}else{
				if(!coins.get(i).isCollected()){
					coins.get(i).setVisible(true);
				}
				coins.get(i).setCollisionRect(new Rectangle(coins.get(i).getX() - offSet - 1, coins.get(i).getY() - 1, 18, 18));
				if(checkUpCollisions(playerCollision, coins.get(i).getCollisionRect())){
					coins.get(i).setCollected(true);
				} else if(checkDownCollisions(playerCollision, coins.get(i).getCollisionRect())){
					coins.get(i).setCollected(true);
				} else if(checkLeftCollisions(playerCollision, coins.get(i).getCollisionRect())){
					coins.get(i).setCollected(true);
				} else if(checkRightCollisions(playerCollision, coins.get(i).getCollisionRect())){
					coins.get(i).setCollected(true);
				}
			}
			
		}
		
		for(int i = 0; i < coins.size(); i++){
			if(coins.get(i).isCollected()){
				coins.remove(i);
				score += 10;
			}
		}
		
		//Crates
		for(int i = 0; i < crates.size(); i++){				
			if(crates.get(i).getX() - offSet > width && crates.get(i).getX() + 64 - offSet < 0){
				crates.get(i).setVisible(false);
			}else{
				crates.get(i).setVisible(true);
				crates.get(i).setActivated(true);
			}
			
			if(crates.get(i).isActivated()){
			
				boolean tileCrateCollision = false;
				boolean crateCrateCollision = false;
				
				for(int x = 0; x < totalWidth/16; x++){
					for(int y = 0; y < (height-80)/16; y ++){
						if(tileMap[x][y] == 1){
							if(checkDownCollisions(crates.get(i).getCollisionRect(), rectMap[x][y])){
								if(crates.get(i).getyVel() == 20){
									crates.get(i).setHealth(crates.get(i).getHealth() - 1);
								}
								crates.get(i).setyVel(0);
								int tileFix = 0;
								while(tileMap[x][y - tileFix - 1] != 0){
									tileFix += 1;
								}
								crates.get(i).setY((int)rectMap[x][y - tileFix].getY() - 64);
								tileCrateCollision = true;
							} else {
								for(int j = 0; j < crates.size(); j++){
									
									if(i != j){
										if(checkDownCollisions(crates.get(i).getCollisionRect(), crates.get(j).getCollisionRect())){
											if(crates.get(i).getyVel() - crates.get(j).getyVel() == 20){
												crates.get(i).setHealth(crates.get(i).getHealth() - 1);
												crates.get(j).setHealth(crates.get(j).getHealth() - 1);
											}
											crates.get(i).setyVel(crates.get(j).getyVel());
											crateCrateCollision = true;
										}
									}
									
								}
							}
						}
					}
				}
				
				for(int s = 0; s < seeds.size(); s++){
					boolean seedCollision = false;
					Rectangle seed = new Rectangle(seeds.get(s).getX() - offSet, seeds.get(s).getY(), 6, 6);

					if(checkLeftCollisions(seed, crates.get(i).getCollisionRect()))seedCollision = true;
					if(checkRightCollisions(seed, crates.get(i).getCollisionRect()))seedCollision = true;
					
					if(seedCollision){
						seeds.remove(s);
						crates.get(i).setHealth(crates.get(i).getHealth() - 1);
					}
					
				}
				
				if(!tileCrateCollision && !crateCrateCollision){
					crates.get(i).setyVel(crates.get(i).getyVel() + 1);
				}
				if(crates.get(i).getyVel() > 20)crates.get(i).setyVel(20);
					
				crates.get(i).setY(crates.get(i).getY() + crates.get(i).getyVel());
				crates.get(i).setCollisionRect(new Rectangle(crates.get(i).getX() + 1 - offSet, crates.get(i).getY() + crates.get(i).getyVel(), 62, 64));
			
				if(crates.get(i).getHealth() == 0){
					crates.remove(i);
				}else if(crates.get(i).getY() > height - 80){
					crates.remove(i);
				}
				
			}
			
		}
		
		for(int i = 0; i < cPigs.size(); i++){
			if(cPigs.get(i).getX() - offSet < width && cPigs.get(i).getX() + 80 > offSet){
				cPigs.get(i).setVisible(true);
				cPigs.get(i).setActivated(true);
			}else{
				cPigs.get(i).setVisible(false);
			}
			
			if(cPigs.get(i).isActivated()){
				if(cPigs.get(i).getSpriteDelay() == 0){
					if(cPigs.get(i).getFrame() != 5){
						cPigs.get(i).setFrame(cPigs.get(i).getFrame() + 1);
					}else{
						cPigs.get(i).setFrame(0);
					}
					cPigs.get(i).setSpriteDelay(10);
				} else{
					cPigs.get(i).setSpriteDelay(cPigs.get(i).getSpriteDelay() - 1);
				}
				
				if(cPigs.get(i).getAttackDelay() == 0){
					if(cPigs.get(i).isVisible() && playerX < cPigs.get(i).getX()){
						
					}
				}
				
			}
			
		}
		
		if(collisionDown){
			yVel = 0;
			if(jumpPressed && !collisionUp){
				yVel = -20;
			}
		} else {
			if(!jumpPressed || yVel < 5){
				yVel += 1;
			} else if (yVel > 5){
				yVel -= 1;
			}
		}
				
		playerX += xVel;
		playerY += yVel;
		
		if(playerX - offSet > getWidth()/2 && xVel > 0){
			offSet += xVel;
		}
		if(offSet > totalWidth - getWidth()){
			offSet = totalWidth - getWidth();
		}
		if(playerX - offSet < getWidth()/2 && xVel < 0){
			offSet += xVel;
		}
		if(offSet < 0){
			offSet = 0;
		}
		
		if(playerHealth <= 0){
			lives -= 1;
			try {
				readLevel("test", false);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		if(playerY > height - 80){
			lives -= 1;
			try {
				readLevel("test", false);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		if(lives > 9){
			lives = 9;
		}
		
		if(lives < 0){
			lives = 0;
		}
		
		if(immunity){
			immuneCounter --;
			if(immuneCounter == 0){
				immunity = false;
			}
		}
		
		coinSprite += 1;
		if(coinSprite > 27){
			coinSprite = 0;
		}
		
		

	}
	
	public void doDamage(int damage){
		if(!immunity){
			playerHealth -= damage;
			immunity = true;
			immuneCounter = 60;
		}
	}
	
	public boolean checkDownCollisions(Rectangle rect1, Rectangle rect2){
		if(rect1 == playerCollision){
			if(rect2.y <= rect1.y + rect1.height + yVel && rect1.x <= rect2.x + rect2.width && rect2.x <= rect1.x + rect1.width){
					if(rect1.y + rect1.height - 20 <= rect2.y){
						return true;
					}
				return false;
			}else{
				return false;
			}
		}else{
			if(rect2.y <= rect1.y + rect1.height && rect1.x <= rect2.x + rect2.width && rect2.x <= rect1.x + rect1.width && rect1.y < rect2.y){
				return true;
			}
			return false;
		}
	}
	
	public boolean checkUpCollisions(Rectangle rect1, Rectangle rect2){
		if(rect1 == playerCollision){
			if(rect1.y + yVel <= rect2.y + rect2.height && rect1.x <= rect2.x + rect2.width && rect2.x <= rect1.x + rect1.width){
				if(rect1.y + 20 >= rect2.y + rect2.height){
						return true;
				}
			}else{
				return false;
			}
			return false;
		} else {
			if(rect1.y <= rect2.y + rect2.height && rect1.x <= rect2.x + rect2.width && rect2.x <= rect1.x + rect1.width){
				return true;
			}
			return false;
		}
	}
	
	public boolean checkLeftCollisions(Rectangle rect1, Rectangle rect2){
		if(playerCollision == rect1){
			if(rect2.x + rect2.width >= rect1.x + xVel && rect1.y< rect2.y + rect2.height && rect2.y < rect1.y + rect1.height){
				if(rect1.x + 20 >= rect2.x){
					return true;
				}
				return false;
			}
			return false;
		}else{
			if(rect2.x + rect2.width >= rect1.x && rect1.y< rect2.y + rect2.height && rect2.y < rect1.y + rect1.height){
				if(rect1.x + rect1.width >= rect2.x){
					return true;
				}
				return false;
			}
			return false;
		}
	}
	
	public boolean checkRightCollisions(Rectangle rect1, Rectangle rect2){
		if(playerCollision == rect1){
			if(rect1.x + rect1.width + xVel > rect2.x && rect1.y < rect2.y + rect2.height && rect2.y < rect1.y + rect1.height){
				if(rect1.x + 20 <= rect2.x){
					return true;
				}
				return false;
			}
			return false;
		}else{
			if(rect1.x + rect1.width > rect2.x && rect1.y < rect2.y + rect2.height && rect2.y < rect1.y + rect1.height){
				if(rect1.x + rect1.width <= rect2.x){
					return true;
				}
				return false;
			}
			return false;
		}
	}

	public void paint(Graphics g){
		
		//Draws the shit
		Graphics2D g2 = (Graphics2D)g;

		g2.setColor(Color.CYAN);		
		g2.fillRect(0, 0, getWidth(), getHeight());


		
		//DECIDES WHICH SPRITE THE DIRT WILL USE... JESUS CHRIST
		for(int j = 0; j < totalWidth/16; j++){
			if(j * 16 - offSet + 16 >= 0 && j*16 - offSet <= getWidth()){
				for(int i = (height-80)/16 - 1; i  > - 1; i--){
					if(tileMap[j][i] == 1){
						if(j != 0 && j != totalWidth/16 - 1 && i != 0 && i != (height-80)/16 - 1){
							if(tileMap[j - 1][i] == 1 && tileMap[j + 1][i] == 0 && tileMap[j][i - 1] == 0){
								g2.drawImage(dirts[4], j * 16 - offSet, i * 16, 16, 16, this);
								g2.drawImage(dirts[6], j * 16 - offSet, (i+1) * 16, 16, 3, this);
							}else if(tileMap[j - 1][i] == 0 && tileMap[j + 1][i] == 1 && tileMap[j][i - 1] == 0){
								g2.drawImage(dirts[3], j * 16 - offSet, i * 16, 16, 16, this);
								g2.drawImage(dirts[5], j * 16 - offSet, (i+1) * 16, 16, 3, this);
							}else if(tileMap[j][i-1] == 0){
								g2.drawImage(dirts[0], j * 16 - offSet, i * 16, 16, 16, this);
							}else if(tileMap[j][i+1] == 0){
								g2.drawImage(dirts[2], j * 16 - offSet, i * 16, 16, 16, this);
							}else{
								g2.drawImage(dirts[1], j * 16 - offSet, i * 16, 16, 16, this);
							}
						}else if(j == 0){
							if(i != 0 && i != (height-80)/16 - 1){
								if(tileMap[j + 1][i] == 0 && tileMap[j][i - 1] == 0){
									g2.drawImage(dirts[4], j * 16 - offSet, i * 16, 16, 16, this);
									g2.drawImage(dirts[6], j * 16 - offSet, (i+1) * 16, 16, 3, this);
								}else if(tileMap[j][i - 1] == 0){
									g2.drawImage(dirts[0], j * 16 - offSet, i * 16, 16, 16, this);
								}else {
									if(tileMap[j][i + 1] == 0){
										g2.drawImage(dirts[2], j * 16 - offSet, i * 16, 16, 16, this);
									}else{
										g2.drawImage(dirts[1], j * 16 - offSet, i * 16, 16, 16, this);
									}
								}
							}else if(i == 0){
								if(tileMap[j][i + 1] == 0){
									g2.drawImage(dirts[2], j * 16 - offSet, i * 16, 16, 16, this);
								} else{
									g2.drawImage(dirts[1], j * 16 - offSet, i * 16, 16, 16, this);
								}
							}else{
								if(tileMap[j][i - 1] == 0){
									g2.drawImage(dirts[0], j * 16 - offSet, i * 16, 16, 16, this);
								} else{
									g2.drawImage(dirts[1], j * 16 - offSet, i * 16, 16, 16, this);
								}
							}
						}else if(j == totalWidth/16 - 1){
							if(i != 0 && i != (height-80)/16 - 1){
								if(tileMap[j - 1][i] == 0 && tileMap[j][i - 1] == 0){
									g2.drawImage(dirts[3], j * 16 - offSet, i * 16, 16, 16, this);
									g2.drawImage(dirts[5], j * 16 - offSet, (i+1) * 16, 16, 3, this);
								}else if(tileMap[j][i - 1] == 0){
									g2.drawImage(dirts[0], j * 16 - offSet, i * 16, 16, 16, this);
								}else {
									if(tileMap[j][i + 1] == 0){
										g2.drawImage(dirts[2], j * 16 - offSet, i * 16, 16, 16, this);
									}else{
										g2.drawImage(dirts[1], j * 16 - offSet, i * 16, 16, 16, this);
									}
								}
							}else if(i == 0){
								if(tileMap[j][i + 1] == 0){
									g2.drawImage(dirts[2], j * 16 - offSet, i * 16, 16, 16, this);
								} else{
									g2.drawImage(dirts[1], j * 16 - offSet, i * 16, 16, 16, this);
								}
							}else{
								if(tileMap[j][i - 1] == 0){
									g2.drawImage(dirts[0], j * 16 - offSet, i * 16, 16, 16, this);
								} else{
									g2.drawImage(dirts[1], j * 16 - offSet, i * 16, 16, 16, this);
								}
							}							
						}else if(i == 0){
							if(tileMap[j][i + 1] == 0){
								g2.drawImage(dirts[2], j * 16 - offSet, i * 16, 16, 16, this);
							}else{
								g2.drawImage(dirts[1], j * 16 - offSet, i * 16, 16, 16, this);
							}
						}else if(i == (height - 80)/16 - 1){
							if(tileMap[j][i - 1] == 0){
								g2.drawImage(dirts[0], j * 16 - offSet, i * 16, 16, 16, this);
							}else{
								g2.drawImage(dirts[1], j * 16 - offSet, i * 16, 16, 16, this);
							}							
						}
					}
				}
			}
		}
		
		
		g2.setColor(Color.BLACK);
		for(int x = 0; x < totalWidth/16; x++){
			for(int y = 0; y < (height-80)/16; y++){
				if(tileMap[x][y] == 1){			
					if(y != 0){
						if(tileMap[x][y - 1] == 1){
							if(x != totalWidth/16 - 1){
								if(tileMap[x + 1][y] == 0){
									g2.drawLine(x*16 + 15 - offSet, y * 16, x * 16 + 15 - offSet, y * 16 + 15);
								}
							}
							if(x != 0){
								if(tileMap[x - 1][y] == 0){
									g2.drawLine(x * 16 - offSet, y * 16, x * 16 - offSet, y * 16 + 15);
								}
							}
						}
					}
					else{
						if(x != totalWidth/16 - 1){
							if(tileMap[x + 1][y] == 0){
								g2.drawLine(x*16 + 15 - offSet, y * 16, x * 16 + 15 - offSet, y * 16 + 15);
							}
						}
						if(x != 0){
							if(tileMap[x - 1][y] == 0){
								g2.drawLine(x * 16 - offSet, y * 16, x * 16 - offSet, y * 16 + 15);
							}							
						}
					}
				}
			}			
		}
		
		if((shootCounter == 0 && direction == "LEFT") || (shootCounter != 0 && shootDirection == "LEFT")){
			g2.drawImage(spitzBodyLeft, playerX - offSet, playerY, playerSize, 68, this);
			if(shootCounter == 0){
				g2.drawImage(spitzHeadLeft, playerX - offSet, playerY + 1, playerSize, 68, this);
			} else {
				g2.drawImage(spitzHeadLeftOpen, playerX - offSet, playerY + 1, playerSize, 68, this);
			}
			if(immunity){
				g2.drawImage(damageIcon, playerX - offSet + 16, playerY + 10, 9, 9, this);
			}
		} else {
			g2.drawImage(spitzBodyRight, playerX - offSet, playerY, playerSize, 68, this);
			if(shootCounter == 0){
				g2.drawImage(spitzHeadRight, playerX - offSet, playerY + 1, playerSize, 68, this);
			} else {
				g2.drawImage(spitzHeadRightOpen, playerX - offSet, playerY + 1, playerSize, 68, this);
			}
			if(immunity){
				g2.drawImage(damageIcon, playerX - offSet + playerSize - 25, playerY + 10, 9, 9, this);
			}
		}
		
		//DRAWING ENTITIES
		if(coins != null){
			for(int i = 0; i < coins.size(); i++){
				if(coins.get(i).isVisible()){				
					g2.drawImage(coinSprites[(int)coinSprite/3], coins.get(i).getX() - offSet, coins.get(i).getY(), 16, 16, this);
				}
			}
		}
		
		if(wires != null){
			for(int i = 0; i < wires.size(); i++){
				if(wires.get(i).isVisible()){
					g2.drawImage(wireSprites[wires.get(i).getSprite()], wires.get(i).getX() - offSet, wires.get(i).getY(), 48, 48, this);
				}
			}
		}
		
		if(seeds != null){
			for(int i = 0; i < seeds.size(); i++){
				g2.drawImage(seed, seeds.get(i).getX() - offSet, seeds.get(i).getY(), 6, 6, this);
			}
		}
		
		if(crates != null){
			for(int i = 0; i < crates.size(); i++){
				if(crates.get(i).isVisible()){
					g2.drawImage(crateSprites[3 - crates.get(i).getHealth()], crates.get(i).getX() - offSet, crates.get(i).getY(), 64, 64, this);
				}
			}
		}
		
		if(cPigs != null){
			for(int i = 0; i < cPigs.size(); i++){
				if(cPigs.get(i).isVisible()){
					g2.drawImage(idlePigSprites[cPigs.get(i).getFrame()], cPigs.get(i).getX() - offSet, cPigs.get(i).getY(), 80, 48, this);
				}
			}

		}
		
		g2.drawImage(HUDBackground, 0, height - 80, getWidth(), 80, this);	
		g2.drawImage(ammoBar[seeds.size()], getWidth() - 35, getHeight() - 75, 25, 70, this);
		
		g2.setColor(Color.BLACK);
		g2.setFont(font);
		FontMetrics fontMetrics = g2.getFontMetrics();
		if(levelName.length() >= 20){
			g2.drawString(levelName.substring(0, 20), getWidth() - 40 - fontMetrics.stringWidth(levelName.substring(0, 20)), getHeight() - 10);
		}else{
			g2.drawString(levelName, getWidth() - 40 - fontMetrics.stringWidth(levelName), getHeight() - 10);
		}
		g2.drawString("HP:", 95 - fontMetrics.stringWidth("HP:"), getHeight() - 20);
		g2.drawString("Lives:", 95 - fontMetrics.stringWidth("Lives:") , getHeight() - 45);
		g2.drawString("Score: " + score, 370 - fontMetrics.stringWidth("Score: "), getHeight() - 45);
		g2.drawString("Hiscore: " + hiscore, 600 - fontMetrics.stringWidth("Hiscore: ") , getHeight() - 45);
		g2.fillRect(100, getHeight() - 32, 452, 12);
		if(playerHealth >= 50){
			g2.setColor(Color.GREEN);
		} else if(playerHealth >= 25){
			g2.setColor(Color.YELLOW);
		} else {
			g2.setColor(Color.RED);
		}
		g2.fillRect(101, getHeight() - 31, (int)(playerHealth * 4.5), 10);
		
		g2.setColor(Color.BLACK);
		if(lives <= 5){
			for(int x = 0; x < lives; x++){
				g2.drawImage(life, 105 + x * 30, getHeight() - 70, 23, 33, this);
			}
		}else{
			g2.drawImage(lifeMany, 105, getHeight() - 70, 70, 33, this);
			g2.drawString("x" + lives, 135, getHeight() - 45);
		}
			
		g.drawImage(image, 0, 0, this);
		
	}
	
	@Override
	public void update(Graphics g) {

	    if (image == null) {
	        image = createImage(this.getWidth(), this.getHeight());
	        graphics = image.getGraphics();
	    }
	    graphics.setColor(getBackground());
	    graphics.fillRect(0,  0,  this.getWidth(),  this.getHeight());
	    graphics.setColor(getForeground());
	    paint(graphics);
	    graphics.drawImage(image, 0, 0, this);
		g.drawImage(image, 0, 0, this);
		
	}

	@Override
	public void keyPressed(KeyEvent key) {
		if(key.getKeyCode() == KeyEvent.VK_A || key.getKeyCode() == KeyEvent.VK_LEFT){
			moveLeft = true;
		}
		if(key.getKeyCode() == KeyEvent.VK_D || key.getKeyCode() == KeyEvent.VK_RIGHT){
			moveRight = true;
		}
		if(key.getKeyCode() == KeyEvent.VK_SPACE || key.getKeyCode() == KeyEvent.VK_W || key.getKeyCode() == KeyEvent.VK_UP){
			jumpPressed = true;
		}
		
	}
	
	public Seed createNewSeed(int x){
		shootCounter = 5;
		Seed seed = new Seed();
		if(x > playerX + playerSize/2 - offSet){
			seed = new Seed(playerX + playerSize/2 + 10, playerY + playerSize/2 - 12, "RIGHT");
			direction = "RIGHT";
			shootDirection = "RIGHT";
		}else{
			seed = new Seed(playerX + playerSize/2 - 10, playerY + playerSize/2 - 12, "LEFT");
			direction = "LEFT";
			shootDirection = "LEFT";
		}
		return seed;
	}

	@Override
	public void keyReleased(KeyEvent key) {
		if(key.getKeyCode() == KeyEvent.VK_A || key.getKeyCode() == KeyEvent.VK_LEFT){
			moveLeft = false;
		}
		if(key.getKeyCode() == KeyEvent.VK_D || key.getKeyCode() == KeyEvent.VK_RIGHT){
			moveRight = false;
		}
		if(key.getKeyCode() == KeyEvent.VK_SPACE || key.getKeyCode() == KeyEvent.VK_W || key.getKeyCode() == KeyEvent.VK_UP){
			jumpPressed = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		
	}

	@Override
	public void mouseReleased(MouseEvent mouse) {

		if(seeds.size() < 3){
			seeds.add(createNewSeed(mouse.getX()));
		}
			
	}
	
}
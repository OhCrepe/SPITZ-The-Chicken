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
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;

import org.gnet.client.ClientEventListener;
import org.gnet.client.GNetClient;
import org.gnet.client.ServerModel;
import org.gnet.packet.Packet;
import org.gnet.server.GNetServer;

import Main.Animation.BarbedWireConnector;
import Main.Animation.BarbedWireLeftBottom;
import Main.Animation.BarbedWireLeftCentre;
import Main.Animation.BarbedWireLeftTop;
import Main.Animation.BarbedWireRightBottom;
import Main.Animation.BarbedWireRightCentre;
import Main.Animation.BarbedWireRightTop;
import Main.Animation.BarbedWireSingleBottom;
import Main.Animation.BarbedWireSingleCentre;
import Main.Animation.BarbedWireSingleTop;
import Main.Animation.CannonPigAttacking;
import Main.Animation.CannonPigIdle;
import Main.Animation.FoxLeft;
import Main.Animation.FoxRight;
import Main.Animation.SpitzBodyIdleLeft;
import Main.Animation.SpitzBodyIdleRight;
import Main.Animation.SpitzBodyMovingLeft;
import Main.Animation.SpitzBodyMovingRight;
import Main.Animation.SpitzHeadIdleLeft;
import Main.Animation.SpitzHeadIdleRight;
import Main.Animation.SpitzHeadShootingLeft;
import Main.Animation.SpitzHeadShootingRight;
import Main.Collectable.Coin;
import Main.Collectable.Collectable;
import Main.Collectable.Health;
import Main.Collectable.LetterI;
import Main.Collectable.LetterP;
import Main.Collectable.LetterS;
import Main.Collectable.LetterT;
import Main.Collectable.LetterZ;
import Main.Entity.CannonPig;
import Main.Entity.Entity;
import Main.Entity.Fox;
import Main.Entity.Player;
import Main.Goal.Goal;
import Main.Hazard.BarbedWire;
import Main.Hazard.Hazard;
import Main.Obstacle.Crate;
import Main.Obstacle.Obstacle;
import Main.Projectile.CannonBall;
import Main.Projectile.Projectile;
import Main.Projectile.Seed;
import Main.Tile.BlankTile;
import Main.Tile.DirtTile;
import Main.Tile.Tile;

/* Level files key
 * 0 - Empty
 * 1 - Dirt
 * b - Crates
 * p - CannonPigs
 * W - Barbed Wire
 * C - Player
 * c - Coin
 * S - Letter S
 * P - Letter P
 * I - Letter I
 * T - Letter T
 * Z - Letter Z
 * f - Fox
 * h - Health
 * G - Goal
 */


public class Main extends Canvas implements Runnable, KeyListener, MouseListener{
	private static final long serialVersionUID = 1L;
	
	public static String host = null;
	final static int port = 43594;
	
	public static int width = 1024;
	public static int totalWidth = 1024;
	public static int mapWidth;
	public static int height = width * 3/4;
	public static int mapHeight = (height - 80)/16;
	public static int offSet = 0;
	
	public static int updates = 0;
	public static int timer = 0;
	public static int completeTimer = 0;
	
	private Thread thread;
	private JFrame frame;
	private boolean running = false;
	public static boolean loadTick;

	private static boolean loaded = false;
	public static boolean complete = false;
	public static boolean started = false;
	
	public static boolean sCollected = false;
	public static boolean pCollected = false;
	public static boolean iCollected = false;
	public static boolean tCollected = false;
	public static boolean zCollected = false;
	public static boolean letterBonus = false;
	
	public static Tile tileMap[][];
	public static int intMap[][];
	public static int corescore;
	public static int score;
	public static int hiscore = 5000;
	public static int lives;
	
	public static LetterS letterS;
	public static LetterP letterP;
	public static LetterI letterI;
	public static LetterT letterT;
	public static LetterZ letterZ;
	
	public static Goal goal;
	
	Font font = new Font("Courier New", Font.CENTER_BASELINE, 22);
	static String levelName = "TEST LEVEL NAME PLEZ WORK";
	
	BufferedImage HUDBackground = new BufferedImage(width, 80, BufferedImage.TYPE_INT_ARGB);
	BufferedImage lifeImage = new BufferedImage(23, 33, BufferedImage.TYPE_INT_ARGB);
	BufferedImage lifeManyImage = new BufferedImage(70, 33, BufferedImage.TYPE_INT_ARGB);
	BufferedImage background = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
	BufferedImage ammoBar[] = new BufferedImage[4];
	
	BufferedImage spriteS = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
	BufferedImage spriteP = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
	BufferedImage spriteI = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
	BufferedImage spriteT = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
	BufferedImage spriteZ = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
	
	public boolean leftPressed = false, 
			rightPressed = false, 
			spacePressed = false;
	
	public static ArrayList<Entity> entities = new ArrayList<Entity>();
	public static ArrayList<Seed> seeds = new ArrayList<Seed>();
	public static ArrayList<Coin> coins = new ArrayList<Coin>();
	public static ArrayList<BarbedWire> wires = new ArrayList<BarbedWire>();
	public static ArrayList<Crate> crates = new ArrayList<Crate>();
	public static ArrayList<CannonPig> cPigs = new ArrayList<CannonPig>();
	public static ArrayList<CannonBall> cBalls = new ArrayList<CannonBall>();
	public static ArrayList<Fox> foxes = new ArrayList<Fox>();
	public static ArrayList<Health> hKits = new ArrayList<Health>();
	
	public static Player player;
	public static boolean upCollision, downCollision, collisionFound;
	
	File cannon = new File("resources/sounds/cannon.wav");
	File coinCollect = new File("resources/sounds/coin collect.wav");
	File playerDamage = new File("resources/sounds/playerDamage.wav");
	File playerShoot = new File("resources/sounds/Spitting.wav");
	File letterCollect = new File("resources/sounds/letter collect.wav");
	File footStep = new File("resources/sounds/step.wav");
	File letterBonusSound = new File("resources/sounds/letterBonus.wav");
	File foxBite = new File("resources/sounds/bite.wav");
	File foxDeath = new File("resources/sounds/foxDie.wav");
	File health = new File("resources/sounds/health.wav");
	
	int sqr = 4256;
	int d = (sqr + "").length()/2;
	int r = Integer.parseInt(("" + sqr).substring(d + 1, ("" + sqr).length()));
	
	public int stepDelay = 10;
	
	Image image;
	Graphics graphics;
	
	public Main() throws IOException{
		
		Dimension size = new Dimension(width, height);
		setPreferredSize(size);
		
		//Loading images
		HUDBackground = ImageIO.read(new File("resources/sprites/HUD/HUDBackground.png"));
		lifeImage = ImageIO.read(new File("resources/sprites/HUD/Lives.png"));
		lifeManyImage = ImageIO.read(new File("resources/sprites/HUD/LivesMany.png"));
		ammoBar[0] = ImageIO.read(new File("resources/sprites/HUD/AmmoFull.png"));
		ammoBar[1] = ImageIO.read(new File("resources/sprites/HUD/AmmoYellow.png"));
		ammoBar[2] = ImageIO.read(new File("resources/sprites/HUD/AmmoRed.png"));
		ammoBar[3] = ImageIO.read(new File("resources/sprites/HUD/AmmoEmpty.png"));
		
		spriteS = ImageIO.read(new File("resources/sprites/Entities/Letters/S.png"));
		spriteP = ImageIO.read(new File("resources/sprites/Entities/Letters/P.png"));
		spriteI = ImageIO.read(new File("resources/sprites/Entities/Letters/I.png"));
		spriteT = ImageIO.read(new File("resources/sprites/Entities/Letters/T.png"));
		spriteZ = ImageIO.read(new File("resources/sprites/Entities/Letters/Z.png"));
		
		//Backgrounds
		background = ImageIO.read(new File("resources/sprites/Tiles/Sky.png"));
		
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
		}
		
	}
	
	public static void main(String[] args) throws IOException{
		
		readLevel("test", true);
		Main main = new Main();
		host = Inet4Address.getLocalHost().getHostAddress();
		final GNetClient netclient = new GNetClient(host, port);
		
		netclient.addEventListener(new ClientEventListener() {

			protected void clientConnected(ServerModel server) {

				System.out.println("Client connect to the server");
				
			}

			protected void clientDisconnected(ServerModel arg0) {
				
				System.out.println("Client Disconnected from Server");
				
			}

			protected void debugMessage(String arg0) {
				// TODO Auto-generated method stub
				
			}

			protected void errorMessage(String arg0) {
				// TODO Auto-generated method stub
				
			}

			protected void packetReceived(ServerModel server, Packet packet) {
				
				if(packet.getPacketName().equals("score")){
					String hs = (String) packet.getEntry("hiscore");
					hiscore = Integer.parseInt(hs);
					return;
				}
				
			}
			
		});
		
		try{
			netclient.bind();
			netclient.start();
		}catch(Exception e){

		}
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
	
	//Plays sound effects
	public void playSound(File file){
		
		if(started){
				
				Clip clip;
				try {
					clip = AudioSystem.getClip();
					clip.open(AudioSystem.getAudioInputStream(file));
					clip.start();
				} catch (LineUnavailableException e) {
				} catch (IOException e) {
				} catch (UnsupportedAudioFileException e) {
				}
				
				
		}
		
	}
	
	public static void readLevel(String fileName, boolean first) throws IOException{
	
		loaded = false;
		loadTick = false;
		sCollected = false;
		pCollected = false;
		iCollected = false;
		tCollected = false;
		zCollected = false;
		letterBonus = false;
		
		//Clear all ArrayList, ensures no objects remain after a reset
		crates.clear();
		cPigs.clear();
		seeds.clear();
		cBalls.clear();
		foxes.clear();
		coins.clear();
		wires.clear();
		
		levelName = fileName;
		
		FileReader file = new FileReader("resources/levels/" + levelName + ".txt");
		BufferedReader reader = new BufferedReader(file);
		
		String line;
		line = reader.readLine();
		totalWidth = line.length() * 16;
		mapWidth = totalWidth/16;
		
	
		intMap = new int[mapWidth][mapHeight];
		tileMap = new Tile[mapWidth][mapHeight];
		
		for(int y = 0; y < mapHeight; y++){
			if (y != 0){
				line = reader.readLine();
			}
			if(line.length() < mapWidth){
				while(line.length() != mapWidth){
					line = line + "0";
				}
			}
			for(int x = 0; x < mapWidth; x++){
				String digit = line.substring(x, x + 1);
				if(digit.equals("0")){
					intMap[x][y] = 0;
					tileMap[x][y] = new BlankTile(x * 16, y * 16);
				}
				if(digit.equals("1")){
					intMap[x][y] = 1;
					tileMap[x][y] = new DirtTile(x * 16, y * 16);
				}
				if(digit.equals("C")){
					intMap[x][y] = 0;
					tileMap[x][y] = new BlankTile(x * 16, y * 16);
					player = new Player(x * 16, y * 16);
				}
				if(digit.equals("W")){
					intMap[x][y] = 50;
					tileMap[x][y] = new BlankTile(x * 16, y * 16);
					BarbedWire bw = new BarbedWire(x * 16, y * 16);
					wires.add(bw);
				}
				if(digit.equals("c")){
					intMap[x][y] = 0;
					tileMap[x][y] = new BlankTile(x * 16, y * 16);
					Coin coin = new Coin(x * 16, y * 16);
					coins.add(coin);
				}
				if(digit.equals("b")){
					intMap[x][y] = 0;
					tileMap[x][y] = new BlankTile(x * 16, y * 16);
					Crate crate = new Crate(x * 16, y * 16);
					crates.add(crate);
				}
				if(digit.equals("p")){
					intMap[x][y] = 0;
					tileMap[x][y] = new BlankTile(x * 16, y * 16);
					CannonPig pig = new CannonPig(x * 16, y * 16);
					cPigs.add(pig);
				}
				if(digit.equals("S")){
					intMap[x][y] = 0;
					tileMap[x][y] = new BlankTile(x * 16, y * 16);
					letterS = new LetterS(x * 16, y * 16);
				}
				if(digit.equals("P")){
					intMap[x][y] = 0;
					tileMap[x][y] = new BlankTile(x * 16, y * 16);
					letterP = new LetterP(x * 16, y * 16);
				}
				if(digit.equals("I")){
					intMap[x][y] = 0;
					tileMap[x][y] = new BlankTile(x * 16, y * 16);
					letterI = new LetterI(x * 16, y * 16);
				}
				if(digit.equals("T")){
					intMap[x][y] = 0;
					tileMap[x][y] = new BlankTile(x * 16, y * 16);
					letterT = new LetterT(x * 16, y * 16);
				}
				if(digit.equals("Z")){
					intMap[x][y] = 0;
					tileMap[x][y] = new BlankTile(x * 16, y * 16);
					letterZ = new LetterZ(x * 16, y * 16);
				}
				if(digit.equals("f")){
					intMap[x][y] = 0;
					tileMap[x][y] = new BlankTile(x * 16, y * 16);
					Fox fox = new Fox(x * 16, y * 16);
					foxes.add(fox);
				}
				if(digit.equals("h")){
					intMap[x][y] = 0;
					tileMap[x][y] = new BlankTile(x * 16, y * 16);
					Health health = new Health(x * 16, y * 16);
					hKits.add(health);
				}
				if(digit.equals("G")){
					intMap[x][y] = 0;
					tileMap[x][y] = new BlankTile(x * 16, y * 16);
					goal = new Goal(x * 16, y * 16);
				}
			}
		}
		
		for (int x = 0; x < mapWidth; x++){
			for (int y = 0; y < mapHeight; y++){
				if(intMap[x][y] == 1){
					selectDirtType(tileMap[x][y], x, y);
				}
			}
		}
		
		//Sorting out wires
		for(int i = 0; i < wires.size(); i++){
			selectWireType(wires.get(i));
		}
		for(int x = 0; x < mapWidth; x++){
			for(int y = 0; y < mapHeight; y++){
				if(intMap[x][y] == 50)intMap[x][y] = 0;
			}
		}
		
		levelName = "test";
		
		if(first){
			lives = 6;
		}else{
			lives -= 1;
		}
		score = 0;
		offSet = 0;
		
		seeds.clear();
			
		System.out.println("Loaded level: " + levelName);
		reader.close();
		
		loaded = true;
		
	}
	
	public static void selectDirtType(Tile tile, int x, int y) throws IOException{
		
		boolean clearUp = false, clearLeft = false, clearRight = false, clearDown = false;
		
		if(x != 0){
			if(intMap[x - 1][y] == 0){
				clearLeft = true;
			}
		}
		if(x != mapWidth - 1){
			if(intMap[x + 1][y] == 0){
				clearRight = true;
			}
		}
		if(y != 0){
			if(intMap[x][y - 1] == 0){
				clearUp = true;
			}
		}
		if(y != mapHeight - 1){
			if(intMap[x][y + 1] == 0){
				clearDown = true;
			}
		}
		
		if(!clearUp && !clearDown && !clearLeft && !clearRight){
			tile.setSprite(ImageIO.read(new File("resources/sprites/Tiles/DirtCentral.png")));
			return;
		}
		if(clearUp && clearLeft && !clearRight && !clearDown){
			tile.setSprite(ImageIO.read(new File("resources/sprites/Tiles/LeftLedge.png")));
			tile.setStep(true);
			return;
		}
		if(clearUp && clearRight && !clearLeft && !clearDown){
			tile.setSprite(ImageIO.read(new File("resources/sprites/Tiles/RightLedge.png")));
			tile.setStep(true);
			return;
		}
		if(clearUp && !clearRight && !clearLeft && !clearDown){
			tile.setSprite(ImageIO.read(new File("resources/sprites/Tiles/DirtTop.png")));
			return;
		}
		if(!clearUp && clearDown){
			if(clearLeft){
				tile.setSprite(ImageIO.read(new File("resources/sprites/Tiles/DirtBottomLeft.png")));
				return;
			}
			if(clearRight){
				tile.setSprite(ImageIO.read(new File("resources/sprites/Tiles/DirtBottomRight.png")));
				return;
			}
			tile.setSprite(ImageIO.read(new File("resources/sprites/Tiles/DirtBottom.png")));
			return;
		}
		if(!clearDown && !clearUp){
			if(clearLeft){
				tile.setSprite(ImageIO.read(new File("resources/sprites/Tiles/DirtLeft.png")));
			}
			if(clearRight){
				tile.setSprite(ImageIO.read(new File("resources/sprites/Tiles/DirtRight.png")));
			}
		}
		
	}
	
	public static void selectWireType(BarbedWire wire) throws IOException{
		
		int x = wire.getX()/16;
		int y = wire.getY()/16;
		boolean upClear = false, leftClear = false, rightClear = false, downClear = false;
				
		if(x == 0){
			leftClear = true;
		} else {
			if(y < mapHeight - 4){
				if(intMap[x - 1][y] == 0 && intMap[x - 1][y + 1] == 0 && intMap[x - 1][y + 2] == 0 && intMap[x - 1][y + 3] == 0
						&& intMap[x - 4][y] != 50){
					leftClear = true;
				}
			}
		}
		if(x >= mapWidth - 4){
			rightClear = true;
		} else {
			if(y < mapHeight - 4){
				if(intMap[x + 4][y] == 0 && intMap[x + 4][y + 1] == 0 && intMap[x + 4][y + 2] == 0 && intMap[x + 4][y + 3] == 0){
					rightClear = true;
				}
			}
		}
		if(y >= mapHeight - 4){
			downClear = true;
		} else {
			if (x <= mapWidth - 4){
				if(intMap[x][y + 4] == 0 && intMap[x + 1][y + 4] == 0 && intMap[x + 2][y + 4] == 0 && intMap[x + 3][y + 4] == 0){
					downClear = true;
				}
			}
		}
		if(y == 0){
			upClear = false;
		}else{
			if(x <= mapWidth - 4){
				if(intMap[x][y - 1] == 0 && intMap[x + 1][y - 1] == 0 && intMap[x + 2][y - 1] == 0 && intMap[x + 3][y - 1] == 0
						&& intMap[x][y - 4] != 50){
					upClear = true;
				}
			}
		}
		
		if(!leftClear && !rightClear){
			wire.setAnimation(new BarbedWireConnector());
			return;
		}
		if(leftClear && rightClear){
			if(upClear && !downClear){
				wire.setAnimation(new BarbedWireSingleTop());
				return;
			}
			if(!upClear && downClear){
				wire.setAnimation(new BarbedWireSingleBottom());
				return;
			}
			if(!upClear && !downClear){
				wire.setAnimation(new BarbedWireSingleCentre());
				return;
			}
		}
		if(leftClear && !rightClear){
			if(upClear && !downClear){
				wire.setAnimation(new BarbedWireLeftTop());
				return;
			}
			if(!upClear && downClear){
				wire.setAnimation(new BarbedWireLeftBottom());
				return;
			}
			if(!upClear && !downClear){
				wire.setAnimation(new BarbedWireLeftCentre());
				return;
			}
		}
		if(!leftClear && rightClear){
			if(upClear && !downClear){
				wire.setAnimation(new BarbedWireRightTop());
				return;
			}
			if(!upClear && downClear){
				wire.setAnimation(new BarbedWireRightBottom());
				return;
			}
			if(!upClear && !downClear){
				wire.setAnimation(new BarbedWireRightCentre());
				return;
			}
		}
		
	}
	
	//Function called when level is complete (goal reached)
	public void levelFinished(){
		corescore = score;
		score = calcScore(score);
		try {
			host = Inet4Address.getLocalHost().getHostAddress();
		} catch (UnknownHostException e1) {
		}
		
		final GNetClient netclient2 = new GNetClient(host, port);
		
		netclient2.addEventListener(new ClientEventListener(){

			@Override
			protected void clientConnected(ServerModel server) {
				
				Packet newscore = new Packet("newscore", 1);
				newscore.addEntry("newscore","" + score);
				server.sendPacket(newscore);
				
			}

			@Override
			protected void clientDisconnected(ServerModel arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			protected void debugMessage(String arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			protected void errorMessage(String arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			protected void packetReceived(ServerModel arg0, Packet arg1) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		try{
			netclient2.bind();
			netclient2.start();
		}catch(Exception e){
			System.out.println("Server not found");
		}
		complete = true;
	}
	
	public int calcScore(int score){
		score += 1000;
		if(lives == 6){
			score += 1000;
		}else{
			score -= (6-lives) * 1000;
		}
		score += player.getHealth() * 25;
		score -= timer * 20;
		return score;
	}

	@Override
	public void run() {
		
		while(running){
			
			if(loaded){
				
				if(started){
					doLogic();
					timer();
					
				}
				
				repaint();
				
				try {
					Thread.sleep(1000/60);
				} catch (InterruptedException e) {
				}
			
			}
						
		}
		
	}
	
	public void timer(){
		
		if(!complete){
			if(updates < 60){
				updates += 1;
			}else if(updates >= 60){
				updates = 0;
				timer += 1; 
			}
		}else{
			completeTimer += 1;
		}
		
	}
	
	public void doLogic(){
		
		if(!complete){
		
			player.setxVel(0);
			downCollision = false;
			upCollision = false;
			collisionFound = false;
			//CONTROLS
			//Moving Left
			if(leftPressed){
				player.setxVel(-8);
			}
			
			//Moving Right
			if(rightPressed){
				player.setxVel(8);			
			}
			
			if(goalReached(player, goal)){
				if(!complete)levelFinished();
			}
			
			for(int x = 0; x < mapWidth; x++){
				for(int y = 0; y < mapHeight; y++){
					
					if(upCollision(player, tileMap[x][y])) upCollision = true;
					if(downCollision(player, tileMap[x][y])) downCollision = true;
					if(leftCollision(player, tileMap[x][y])) collisionFound = true;
					if(rightCollision(player, tileMap[x][y])) collisionFound = true;
					
				}
			}
			
			for(int c = 0; c < crates.size(); c++){
				if(crates.get(c).getX() < offSet + width && crates.get(c).getX() + crates.get(c).getWidth() > offSet){
					crates.get(c).setVisible(true);
				}else{
					crates.get(c).setVisible(false);
				}
				if(crates.get(c).isVisible()){
					if(upCollision(player, crates.get(c))){
						upCollision = true;
						if(crates.get(c).getyVel() > 0){
							crates.get(c).setDestroyed(true);
							int modifier;
							if(crates.get(c).getHealth() == 25) modifier = 25;
							else if(crates.get(c).getHealth() > 12)modifier = 10;
							else modifier = 0;
							if(crates.get(c).getyVel() - 25 + modifier > 0){
								if(!player.isImmunity()){
									playSound(playerDamage);
								}
								player.doDamage(crates.get(c).getyVel() + modifier);
							}
						}
					}
					if(downCollision(player, crates.get(c))) downCollision = true;
					if(leftCollision(player, crates.get(c))) collisionFound = true;
					if(rightCollision(player, crates.get(c))) collisionFound = true;
				}
			}
			
			for(int p = 0; p  < cPigs.size(); p++){
				if(cPigs.get(p).isVisible()){
					if(downCollision(player, cPigs.get(p))){
						if(!player.isImmunity()){
							playSound(playerDamage);
						}
						player.doDamage(10);	
						player.setyVel(-10);
						cPigs.get(p).doDamage(1);
					}
					if(rightCollision(player, cPigs.get(p))){
						if(!player.isImmunity()){
							playSound(playerDamage);
						}
						player.doDamage(10);
						if(downCollision){
							player.setyVel(-3);
						}
					}
					if(leftCollision(player, cPigs.get(p))){
						if(!player.isImmunity()){
							playSound(playerDamage);
						}
						player.doDamage(10);
						if(downCollision){
							player.setyVel(-3);
						}
					}
				}
			}
			
			for(int f = 0; f < foxes.size(); f++){
				if(foxes.get(f).isVisible()){
					if(downCollision(player, foxes.get(f))){
						player.setyVel(-8);
						player.setY(foxes.get(f).getY() - player.getHeight() - 5);
						if(!player.isImmunity()){
							playSound(playerDamage);
							playSound(foxBite);
						}
						player.doDamage(10);
						foxes.get(f).setFull(60);
					}
				}
			}
			
			for(int c = 0; c < cBalls.size(); c++){
				if(collision(player, cBalls.get(c)) && !cBalls.get(c).isRemoved()){
					if(!player.isImmunity()){
						playSound(playerDamage);
					}
					player.doDamage(cBalls.get(c).getDamage());
					cBalls.get(c).setRemoved(true);
				}
			}
			
			if(collisionFound){
				player.setxVel(0);
			}
			
			//Footstep sound effect
			if(player.getxVel() == 0 || !downCollision){
				stepDelay = 10;
			}else{
				if(stepDelay == 10){
					playSound(footStep);
				}
				stepDelay -= 1;
				if(stepDelay == 0){
					stepDelay = 10;
				}
			}
			
			if(!downCollision){
					int GLIDESPEED = 3;
					if((spacePressed && player.getyVel() < GLIDESPEED) || !spacePressed){
						player.setyVel(player.getyVel() + 1);
					} else if (spacePressed && player.getyVel() > GLIDESPEED){
						player.setyVel(player.getyVel() - 1);
					}
			}
			
			//Falling of the map
			if(player.getY() > height - 80){
				try {
					readLevel(levelName, false);
				} catch (IOException e) {
				}
			}
			
			//TILE VISIBILITY
			for(int x = 0; x < mapWidth; x++){
				for(int y = 0; y < mapHeight; y++){
					Tile tile = tileMap[x][y];
					if(tile.getX() - tile.getWidth() < width + offSet && tile.getX() + (2 *tile.getWidth()) > offSet){
						tileMap[x][y].setVisible(true);
					}else{
						tileMap[x][y].setVisible(false);
					}
				}
			}
	
			//Sorts out the wires (collision, visibility, etc.)
			for(int i = 0; i < wires.size(); i++){
				if(wires.get(i).getX() + wires.get(i).getWidth() - offSet >= 0 && wires.get(i).getX() - offSet <= width){
					wires.get(i).setVisible(true);
				}else{
					wires.get(i).setVisible(false);
				}
				if(wires.get(i).isVisible()){
					if(collisionFound(player, wires.get(i))){
						if(!player.isImmunity()){
							playSound(playerDamage);
						}
						player.doDamage(wires.get(i).getDamage());
					}
				}
			}
			
			//Sorts out Coins
			for(int i = 0; i < coins.size(); i++){
				coins.get(i).update();
				if(collision(player, coins.get(i)) && !coins.get(i).isCollected()){
					score += 10;
					coins.get(i).setCollected(true);
					playSound(coinCollect);
				}
				if(coins.get(i).getX() + coins.get(i).getWidth() - offSet >= 0 && coins.get(i).getX() - offSet <= width){
					if(coins.get(i).isCollected()){
						coins.get(i).setVisible(false);
					}else{
						coins.get(i).setVisible(true);
					}
				}else{
					coins.get(i).setVisible(false);
				}
			}
			for(int i = 0; i < coins.size(); i++){
				if(coins.get(i).isCollected()){
					coins.remove(i);
					i -= 1;
				}
			}
			
			//Sorts out Health Kits
			for(int i = 0; i < hKits.size(); i++){
				hKits.get(i).update();
				if(hKits.get(i).isVisible()){
					if(collision(player, hKits.get(i)) && !hKits.get(i).isCollected()){
						player.setHealth(player.getHealth() + 25);
						hKits.get(i).setCollected(true);
						playSound(health);
					}
				}
				if(hKits.get(i).getX() + hKits.get(i).getWidth() - offSet >= 0 && hKits.get(i).getX() - offSet <= width){
					if(hKits.get(i).isCollected()){
						hKits.get(i).setVisible(false);
					}else{
						hKits.get(i).setVisible(true);
					}
				}else{
					hKits.get(i).setVisible(false);
				}
			}
			for(int i = 0; i < hKits.size(); i++){
				if(hKits.get(i).isCollected()){
					hKits.remove(i);
					i -= 1;
				}
			}
			
			//Sorts out letter collectables
			if(!sCollected){
				if(collision(player, letterS)){
					score += 500;
					sCollected = true;
					if(sCollected && pCollected && iCollected && tCollected && zCollected){
						playSound(letterBonusSound);
					}else{
						playSound(letterCollect);
					}
				}
				letterS.update();
			}
			if(!pCollected){
				if(collision(player, letterP)){
					score += 500;
					pCollected = true;
					if(sCollected && pCollected && iCollected && tCollected && zCollected){
						playSound(letterBonusSound);
					}else{
						playSound(letterCollect);
					}
				}
				letterP.update();
			}
			if(!iCollected){
				if(collision(player, letterI)){
					score += 500;
					iCollected = true;
					if(sCollected && pCollected && iCollected && tCollected && zCollected){
						playSound(letterBonusSound);
					}else{
						playSound(letterCollect);
					}
				}
				letterI.update();
			}
			if(!tCollected){
				if(collision(player, letterT)){
					score += 500;
					tCollected = true;
					if(sCollected && pCollected && iCollected && tCollected && zCollected){
						playSound(letterBonusSound);
					}else{
						playSound(letterCollect);
					}
				}
				letterT.update();
			}
			if(!zCollected){
				if(collision(player, letterZ)){
					score += 500;
					zCollected = true;
					if(sCollected && pCollected && iCollected && tCollected && zCollected){
						playSound(letterBonusSound);
					}else{
						playSound(letterCollect);
					}
				}
				letterZ.update();
			}
			if(!letterBonus){
				if(sCollected && pCollected && iCollected && tCollected && zCollected){
					letterBonus = true;
					score += 10000;
				}
			}
			
			//Crates
			for(int i = 0; i < crates.size(); i++){
				boolean crateDownCollision = false;
				if(crates.get(i).isVisible()){					
					if(crates.get(i).getX() + crates.get(i).getWidth() - offSet > 0 && crates.get(i).getX() - offSet < width){
						crates.get(i).setVisible(true);
					}else{
						crates.get(i).setVisible(false);
					}
					for(int s = 0; s < seeds.size(); s++){
						if(collision(seeds.get(s), crates.get(i))){
							crates.get(i).doDamage(seeds.get(s).getDamage());
							seeds.get(s).setRemoved(true);
						}
					}
					for(int j = 0; j < crates.size(); j++){
						
						if(i != j){
							if(upCollision(crates.get(i), crates.get(j))){
								if(crates.get(i).getyVel() < crates.get(j).getyVel()){
									crates.get(i).setY(crates.get(j).getY() + crates.get(j).getHeight());
									crates.get(i).setyVel(crates.get(j).getyVel());
									crates.get(i).doDamage(crates.get(j).getyVel() - crates.get(i).getyVel());
								}
							}
							if(downCollision(crates.get(i), crates.get(j))){
								if(crates.get(i).getyVel() >= crates.get(j).getyVel()){
									crates.get(i).setY(crates.get(j).getY() - crates.get(i).getHeight());
									crates.get(i).setyVel(crates.get(j).getyVel());
									crateDownCollision = true;
								}
							}
							crates.get(i).fixCollisionRect();
							crates.get(j).fixCollisionRect();
						}
						
					}
					for(int x = 0; x < mapWidth; x++){
						for(int y = 0; y < mapHeight; y++){
							if(Math.abs(tileMap[x][y].getX() - crates.get(i).getX()) < 100){
								if(upCollision(crates.get(i), tileMap[x][y])){
									if(crates.get(i).getyVel() < 0){
										crates.get(i).doDamage(-crates.get(i).getyVel());
										crates.get(i).setyVel(0);
										crates.get(i).setY(tileMap[x][y].getY() + tileMap[x][y].getHeight());
									}
								}
								if(downCollision(crates.get(i), tileMap[x][y])){
									if(crates.get(i).getyVel() > 5)crates.get(i).doDamage(crates.get(i).getyVel() - 5);
									crates.get(i).setY(y * 16 - crates.get(i).getHeight());
									crates.get(i).setyVel(0);
									crateDownCollision = true;
								}
								crates.get(i).fixCollisionRect();	
							}
						}
					}
					if(!crateDownCollision)crates.get(i).setyVel(crates.get(i).getyVel() + 1);
					if(crates.get(i).getY() > height){
						crates.get(i).setDestroyed(true);
					}
					for(int c = 0; c < cBalls.size(); c++){
						if(collision(cBalls.get(c), crates.get(i)))crates.get(i).setHealth(0);
					}
					crates.get(i).update();
				}
			}
			for(int i = 0; i < crates.size(); i++){
				if(crates.get(i).isDestroyed()){
					crates.get(i).setVisible(false);
					crates.remove(i);
					i -= 1;
				}
			}
			
			//Fixing the offSet
			offSet = player.getX() + player.getWidth()/2 - width/2 + player.getxVel();
			if(offSet < 0){
				offSet = 0;
			}
			if(offSet > totalWidth - width){
				offSet = totalWidth - width;
			}
	
			
			//ENTITIES MOVING
			player.update();
			
			//Player
			if(player.getX() < 0){
				player.setX(0);
			}
			if(player.getX() + player.getWidth() > totalWidth){
				player.setX(totalWidth - player.getWidth());
			}
			
			//CannonPig
			for(int i = 0; i < cPigs.size(); i++){
				CannonPig pig = cPigs.get(i);
				cPigs.get(i).update();
				if(pig.getX() + pig.getWidth() - offSet > 0 && pig.getX() - offSet < width){
					cPigs.get(i).setActivated(true);
					cPigs.get(i).setVisible(true);
				}else{
					cPigs.get(i).setVisible(false);
				}
				if(cPigs.get(i).isActivated()){
					boolean collisionDown = false;
					for(int x = 0; x < mapWidth; x++){
						for(int y = 0; y < mapHeight; y++){
							if(downCollision(pig, tileMap[x][y])){
								collisionDown = true;
								cPigs.get(i).setY(tileMap[x][y].getY() - pig.getHeight());
							}
						}
					}
					for(int c = 0; c < crates.size(); c++){
						if(downCollision(pig, crates.get(c))){
							collisionDown = true;
							cPigs.get(i).setY(crates.get(c).getY() - pig.getHeight());
						}
					}
					for(int s = 0; s < seeds.size(); s++){
						if(collision(pig, seeds.get(s))){
							seeds.get(s).setRemoved(true);
							cPigs.get(i).doDamage(seeds.get(s).getDamage());
							if(pig.getHealth() <= 0){
								score += 300;
							}
						}
					}
					for(int c = 0; c < cBalls.size(); c++){
						if(cBalls.get(c).getPigDelay() == 0){
							if(collision(pig, cBalls.get(c))){
								cBalls.get(c).setRemoved(true);
								cPigs.get(i).doDamage(cBalls.get(c).getDamage());
							}
						}
					}
					if(!collisionDown){
						if(cPigs.get(i).getyVel() < 20){
							cPigs.get(i).setyVel(cPigs.get(i).getyVel() + 1);
						}
					}
					if(pig.getY() - 48 < player.getY() + player.getHeight() && pig.getY() + pig.getHeight() + 48 > player.getY()){
						if(player.getX() < pig.getX()){
							if(pig.getReloadCounter() >= pig.getReloadTime()){
								if(pig.getAttackCounter() == 0){
									pig.setAttackCounter(pig.getAttackDelay());
									cPigs.get(i).setAnimations(0, new CannonPigAttacking());
								}
	
							}
						}
					}
					if(pig.getAttackCounter() == 1){
						CannonBall c = new CannonBall(pig.getX(), pig.getY() + 18, "LEFT");
						cBalls.add(c);
						playSound(cannon);
						cPigs.get(i).setReloadCounter(0);
						cPigs.get(i).setAnimations(0, new CannonPigIdle());
					}
				}
				if(cPigs.get(i).getHealth() <= 0 || cPigs.get(i).getY() > height - 80){
					playSound(foxDeath);
					cPigs.remove(i);
					i -= 1;
					if(pig.getY() > height - 80){
						score += 300;
					}			
				}
			}
			
			//Foxes (AI)
			for(int i = 0; i < foxes.size(); i++){
				if(foxes.get(i).getX() + foxes.get(i).getWidth() > offSet && foxes.get(i).getX() < offSet + width){
					foxes.get(i).setActivated(true);
					foxes.get(i).setVisible(true);
				}else{
					foxes.get(i).setVisible(false);
				}
				if(foxes.get(i).isActivated()){
					foxes.get(i).update();
					boolean foxCollision = false;
					boolean chickenProx = false;
					if((foxes.get(i).getX() < 0 && foxes.get(i).getxVel() < 0) || (foxes.get(i).getX() + foxes.get(i).getWidth() > totalWidth)){
						foxCollision = true;
					}
					if(foxes.get(i).getxVel() > 0){
						for(int c = 0; c < crates.size(); c++){
							if(rightCollision(foxes.get(i), crates.get(c))){
								foxCollision = true;
							}
						}
						if(!foxCollision){
							for(int x = 0; x < mapWidth; x++){
								for(int y = 0; y < mapHeight; y++){
									if(rightCollision(foxes.get(i), tileMap[x][y])){
										foxCollision = true;
									}
								}
							}
						}
						if(!foxCollision){
							for(int p = 0; p < cPigs.size(); p++){
								if(rightCollision(foxes.get(i), cPigs.get(p))){
									foxCollision = true;
								}
							}
						}
						if(!foxCollision){
							for(int f = 0; f < foxes.size(); f++){
								if(i != f){
									if(rightCollision(foxes.get(i), foxes.get(f))){
										foxCollision = true;
									}
								}
							}
						}
						if(foxes.get(i).getX() - player.getX() < 400 && player.getX() > foxes.get(i).getX()){
							if(player.getY() + player.getHeight() > foxes.get(i).getY() &&
									player.getY() < foxes.get(i).getY() + foxes.get(i).getHeight()){
								chickenProx = true;
							}
						}
					}
					if(foxes.get(i).getxVel() < 0){
						for(int c = 0; c < crates.size(); c++){
							if(leftCollision(foxes.get(i), crates.get(c))){
								foxCollision = true;
							}
						}
						if(!foxCollision){
							for(int x = 0; x < mapWidth; x++){
								for(int y = 0; y < mapHeight; y++){
									if(leftCollision(foxes.get(i), tileMap[x][y])){
										foxCollision = true;
									}
								}
							}
						}
						if(!foxCollision){
							for(int p = 0; p < cPigs.size(); p++){
								if(leftCollision(foxes.get(i), cPigs.get(p))){
									foxCollision = true;
								}
							}
						}
						if(!foxCollision){
							for(int f = 0; f < foxes.size(); f++){
								if(i != f){
									if(leftCollision(foxes.get(i), foxes.get(f))){
										foxCollision = true;
									}
								}
							}
						}
						if(foxes.get(i).getX() - player.getX() < 400 && foxes.get(i).getX() > player.getX()){
							if(player.getY() + player.getHeight() > foxes.get(i).getY() &&
									player.getY() < foxes.get(i).getY() + foxes.get(i).getHeight()){
								chickenProx = true;
							}
						}
					}
					
					//Reacting to Wires
					for(int w = 0; w < wires.size(); w++){
						if(foxes.get(i).getY() - 20 < wires.get(w).getY() + wires.get(w).getHeight() &&
								foxes.get(i).getY() + foxes.get(i).getHeight() + 20 > wires.get(w).getY() &&
								foxes.get(i).getX() + foxes.get(i).getWidth() + 30 > wires.get(w).getX() &&
								foxes.get(i).getX() - 30 < wires.get(w).getX() + wires.get(w).getWidth()){
							if(foxes.get(i).isVisible() && 
									wires.get(w).isVisible()){
								if(collisionFound(foxes.get(i), wires.get(w))){
									foxes.get(i).doDamage(wires.get(w).getDamage());
								} else {
									if(foxes.get(i).getxVel() > 0){
										Fox foxTest = new Fox(foxes.get(i).getX() + 12, foxes.get(i).getY());
										foxTest.update();
										if(collisionFound(foxTest, wires.get(w))){
											foxCollision = true;
										}
									} else{
										Fox foxTest = new Fox(foxes.get(i).getX() - 12, foxes.get(i).getY());
										foxTest.update();
										if(collisionFound(foxTest, wires.get(w))){
											foxCollision = true;
										}
									}
								}
							}
						}
					}
					
					//Checking for ledges
					boolean foxDownCollision = false;
					for(int x = 1; x < mapWidth - 1; x++){
						for(int y = foxes.get(i).getY()/16; y < mapHeight; y++){
							if(tileMap[x][y].isVisible()){
								if(downCollision(foxes.get(i), tileMap[x][y])){
									foxDownCollision = true;
									if((!tileMap[x + 1][y].isSolid() && foxes.get(i).getxVel() > 0) ||
											(!tileMap[x - 1][y].isSolid() && foxes.get(i).getxVel() < 0)){
										foxCollision = true;
									}
								}
							}
						}
					}
					
					//Decides speed and direction based on circumstances
					if(foxes.get(i).getxVel() > 0){
						if(foxCollision){
							foxes.get(i).setxVel(-2);
							try {
								foxes.get(i).setAnimations(0, new FoxLeft());
							} catch (IOException e) {
							}
						}else{
							foxes.get(i).setxVel(2);
						}
					} else {
						if(foxCollision){
							foxes.get(i).setxVel(2);
							try {
								foxes.get(i).setAnimations(0, new FoxRight());
							} catch (IOException e) {
							}
						}else{
							foxes.get(i).setxVel(-2);
						}
					}
					if(chickenProx){
						foxes.get(i).setxVel(foxes.get(i).getxVel() * 4);
						foxes.get(i).getAnimation(0).setframeDelay(2);
					}
					if(!foxDownCollision && foxes.get(i).getyVel() < 20 && foxes.get(i).isVisible()){
						foxes.get(i).setyVel(foxes.get(i).getyVel() + 1);
					}
					if(leftCollision(player, foxes.get(i))){
						if(foxes.get(i).getxVel() < 0){
							if(foxes.get(i).getFull() == 0){
								foxes.get(i).setxVel(8);
							}
						}else{
							player.setX(player.getX() + 12);
							foxes.get(i).setxVel(-2);
							try {
								foxes.get(i).setAnimations(0, new FoxRight());
							} catch (IOException e) {
							}
							player.doDamage(30);
							foxes.get(i).setFull(60);
							playSound(foxBite);
						}
					}
					if(rightCollision(player, foxes.get(i))){
						if(foxes.get(i).getxVel() > 0){
							if(foxes.get(i).getFull() == 0){
								foxes.get(i).setxVel(-8);
							}
						}else{
							player.setX(player.getX() - 12);
							foxes.get(i).setxVel(2);
							try {
								foxes.get(i).setAnimations(0, new FoxLeft());
							} catch (IOException e) {
							}
							player.doDamage(30);
							foxes.get(i).setFull(60);
							playSound(foxBite);
						}
					}
					//Damage from Projectiles
					for(int s = 0; s < seeds.size(); s++){
						if(collision(foxes.get(i), seeds.get(s))){
							foxes.get(i).doDamage(seeds.get(s).getDamage());
							seeds.remove(s);
						}
					}
					for(int c = 0; c < cBalls.size(); c++){
						if(collision(foxes.get(i), cBalls.get(c))){
							foxes.get(i).doDamage(cBalls.get(c).getDamage());
							cBalls.remove(c);
						}
					}
					if(foxes.get(i).getHealth() <= 0){
						foxes.remove(i);
						i -= 1;
						score += 200;
						playSound(foxDeath);
					}
				}
			}
			
			//PROJECTILES
			//Seeds
			for(int i = 0; i < seeds.size(); i++){
				
				seeds.get(i).update();
				if(!(seeds.get(i).getX() - offSet >= 0 && seeds.get(i).getX() - offSet <= width)){
					seeds.get(i).setRemoved(true);
				}
				for(int x = 0; x < mapWidth; x++){
					for(int y = 0; y < mapHeight; y++){
						if(collision(seeds.get(i), tileMap[x][y]))seeds.get(i).setRemoved(true);
					}
				}
				if(seeds.get(i).isRemoved()){
					seeds.remove(i);
					i -= 1;
				}
			}
			
			//Cannonballs
			for(int i = 0; i < cBalls.size(); i++){
				
				cBalls.get(i).update();
				
				for(int x = 0; x < mapWidth; x++){
					for(int y = 0; y < mapHeight; y++){
						if(collision(cBalls.get(i), tileMap[x][y]))cBalls.get(i).setRemoved(true);
					}
				}
				if(cBalls.get(i).getX() + cBalls.get(i).getWidth() <= offSet || cBalls.get(i).getX() - offSet > width){
					cBalls.get(i).setRemoved(true);
				}
				if(cBalls.get(i).isRemoved()){
					cBalls.remove(i);
					i -= 1;
				}
				
			}
			
			if(player.getHealth() <= 0){
				try {
					if(loadTick){
						readLevel("test", false);
					}else{
						loadTick = true;
					}
				} catch (IOException e) {
				}
			}
		
		}
		
	}

	
	//ENTITY AND TILE COLLISION
	public boolean downCollision(Entity entity, Tile tile){
		ArrayList<Rectangle> cRects = entity.getCollisionRects();
		Rectangle tRect = tile.getCollisionRect();
		if(tile.isSolid()){
			for(int r = 0; r < cRects.size(); r++){
				Rectangle cRect = cRects.get(r);
				if(cRect.y + cRect.height + entity.getyVel() + 1 > tRect.y && cRect.y + cRect.height/2 + entity.getyVel() < tRect.y + tRect.height/2 &&
						cRect.x + cRect.width > tRect.x && cRect.x < tRect.x + tRect.width){
					if (tile.getY() != 0){
						if(!tileMap[tile.getX()/16][tile.getY()/16 - 1].isSolid()){
							entity.setY(tRect.y - entity.getHeight());
						}
					}
					entity.setyVel(0);
					entity.fixCollisionRects();
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean leftCollision(Entity entity, Tile tile){
		ArrayList<Rectangle> cRects = entity.getCollisionRects();
		Rectangle tRect = tile.getCollisionRect();
		if(tile.isSolid()){
			for(int r = 0; r < cRects.size(); r++){
				Rectangle cRect = cRects.get(r);
				if(cRect.x + entity.getxVel() < tRect.x + tRect.width && cRect.x + entity.getxVel() + (cRect.width/2) >= tRect.x + (tRect.width/2) &&
						cRect.y + cRect.height - cRect.height/4 - 1 > tRect.y && cRect.y < tRect.y + tRect.height){
					entity.setX(tRect.x + tRect.width);
					entity.setxVel(0);
					entity.fixCollisionRects();
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean rightCollision(Entity entity, Tile tile){
		ArrayList<Rectangle> cRects = entity.getCollisionRects();
		Rectangle tRect = tile.getCollisionRect();
		if(tile.isSolid()){
			for(int r = 0; r < cRects.size(); r++){
				Rectangle cRect = cRects.get(r);
				if(cRect.x + entity.getxVel() + cRect.width > tRect.x && cRect.x + (cRect.width/2) + entity.getxVel() <= tRect.x + (tRect.width)/2 &&
						cRect.y + cRect.height - cRect.height/4 - 1 > tRect.y && cRect.y < tRect.y + tRect.height){
					entity.setX(tRect.x - entity.getWidth());
					entity.setxVel(0);
					entity.fixCollisionRects();
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean upCollision(Entity entity, Tile tile){
		ArrayList<Rectangle> cRects = entity.getCollisionRects();
		Rectangle tRect = tile.getCollisionRect();
		if(tile.isSolid()){
			for(int r = 0; r < cRects.size(); r++){
				Rectangle cRect = cRects.get(r);
				if(cRect.y + entity.getyVel() - 1 < tRect.y + tRect.height && cRect.y + (cRect.height/2) + entity.getyVel() > tRect.y + (tRect.height/2) &&
						cRect.x + cRect.width > tRect.x && cRect.x < tRect.x + tRect.width){
					if(tile.getY()/16 != mapHeight - 1){
						if(!tileMap[tile.getX()/16][tile.getY()/16 + 1].isSolid()){
							entity.setY(tRect.y + tRect.height + 16);
						}else{
							entity.setY(tRect.y + tRect.height);
						}
					}else{
						entity.setY(tRect.y + tRect.height);
					}
					entity.setyVel(0);
					entity.fixCollisionRects();
					return true;
				}
			}
		}
		return false;
	}
	
	//ENTITY AND OBSTACLE COLLISION
	public boolean downCollision(Entity entity, Obstacle obstacle){
		ArrayList<Rectangle> cRects = entity.getCollisionRects();
		Rectangle oRect = obstacle.getCollisionRect();
		for(int r = 0; r < cRects.size(); r++){
			Rectangle cRect = cRects.get(r);
			if(cRect.y + cRect.height + entity.getyVel() + 1 > oRect.y && cRect.y + cRect.height/2 + entity.getyVel() < oRect.y + oRect.height/2 &&
					cRect.x + cRect.width > oRect.x && cRect.x < oRect.x + oRect.width){
				entity.setY(oRect.y - cRect.height);
				entity.setyVel(0);
				entity.fixCollisionRects();
				return true;
			}
		}
		return false;
	}
		
	public boolean leftCollision(Entity entity, Obstacle obstacle){
		ArrayList<Rectangle> cRects = entity.getCollisionRects();
		Rectangle oRect = obstacle.getCollisionRect();
			for(int r = 0; r < cRects.size(); r++){
				Rectangle cRect = cRects.get(r);
				if(cRect.x + entity.getxVel() < oRect.x + oRect.width && cRect.x + entity.getxVel() + (cRect.width/2) >= oRect.x + (oRect.width/2) &&
						cRect.y + cRect.height - cRect.height/4 - 1 > oRect.y && cRect.y < oRect.y + oRect.height){
					entity.setX(oRect.x + oRect.width);
					entity.fixCollisionRects();
					return true;
				}
			}
		return false;
	}
		
	public boolean rightCollision(Entity entity, Obstacle obstacle){
		ArrayList<Rectangle> cRects = entity.getCollisionRects();
		Rectangle oRect = obstacle.getCollisionRect();
			for(int r = 0; r < cRects.size(); r++){
				Rectangle cRect = cRects.get(r);
				if(cRect.x + entity.getxVel() + cRect.width > oRect.x && cRect.x + (cRect.width/2) + entity.getxVel() <= oRect.x + (oRect.width)/2 &&
						cRect.y + cRect.height - cRect.height/4 - 1 > oRect.y && cRect.y < oRect.y + oRect.height){
					entity.setX(oRect.x - entity.getWidth());
					entity.fixCollisionRects();
					return true;
				}
			}
		return false;
	}
		
	public boolean upCollision(Entity entity, Obstacle obstacle){
		ArrayList<Rectangle> cRects = entity.getCollisionRects();
		Rectangle oRect = obstacle.getCollisionRect();
		for(int r = 0; r < cRects.size(); r++){
			Rectangle cRect = cRects.get(r);
			if(cRect.y + entity.getyVel() - 1 < oRect.y + oRect.height && cRect.y + (cRect.height/2) + entity.getyVel() > oRect.y + (oRect.height/2) &&
					cRect.x + cRect.width > oRect.x && cRect.x < oRect.x + oRect.width){
				entity.setyVel(0);
				entity.fixCollisionRects();
				return true;
			}
		}
		return false;
	}
	
	//ENTITY AND ENTITY COLLISION
	public boolean downCollision(Entity e1, Entity e2){
		ArrayList<Rectangle> cRects1 = e1.getCollisionRects();
		ArrayList<Rectangle> cRects2 = e2.getCollisionRects();
		for(int i = 0; i < cRects1.size(); i++){
			Rectangle rect1 = cRects1.get(i);
			for(int j = 0; j < cRects2.size(); j++){
				Rectangle rect2 = cRects2.get(j);
				if(rect1.x + rect1.width + e1.getxVel() > rect2.x + e2.getxVel() && rect1.x + e1.getxVel() < rect2.x + rect2.width + e2.getxVel() &&
						rect1.y + rect1.height + e1.getyVel() + 1 > rect2.y + e2.getyVel() && rect1.y + rect1.height/2 + e1.getyVel() < rect2.y + e2.getyVel()){
					if(e2.getyVel() == 0){
						e1.setyVel(0);
						e1.setY(rect2.y - rect1.height);
					}
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean rightCollision(Entity e1, Entity e2){
		ArrayList<Rectangle> cRects1 = e1.getCollisionRects();
		ArrayList<Rectangle> cRects2 = e2.getCollisionRects();
		for(int i = 0; i < cRects1.size(); i++){
			Rectangle rect1 = cRects1.get(i);
			for(int j = 0; j < cRects2.size(); j++){
				Rectangle rect2 = cRects2.get(j);
				if(rect1.y + rect1.height + e1.getyVel() > rect2.y + e2.getyVel() && rect1.y + e1.getyVel() < rect2.y + rect2.height + e2.getyVel() &&
						rect1.x + rect1.width + e1.getxVel() + 1 > rect2.x + e2.getxVel() && rect1.x + rect1.width/2 + e1.getxVel() < rect2.x + rect2.width/2 + e2.getxVel()){
					if(e2.getyVel() == 0){
						e1.setX(rect2.x - rect1.width - 4);
					}
					return true;
				}
					
			}
		}
		return false;
	}
	
	public boolean leftCollision(Entity e1, Entity e2){
		ArrayList<Rectangle> cRects1 = e1.getCollisionRects();
		ArrayList<Rectangle> cRects2 = e2.getCollisionRects();
		for(int i = 0; i < cRects1.size(); i++){
			Rectangle rect1 = cRects1.get(i);
			for(int j = 0; j < cRects2.size(); j++){
				Rectangle rect2 = cRects2.get(j);
				if(rect1.y + rect1.height + e1.getyVel() > rect2.y + e2.getyVel() && rect1.y + e1.getyVel() < rect2.y + rect2.height + e2.getyVel() &&
						rect1.x + e1.getxVel() < rect2.x + rect2.width + e2.getxVel() && rect1.x + rect1.width/2 + e1.getxVel() > rect2.x + rect2.width/2 + e2.getxVel()){
					if(e2.getyVel() == 0){
						e1.setX(rect2.x + rect2.width + 4);
					}
					return true;
				}
			}
		}
		return false;
	}
	
	//ENTITY AND COLLECTABLE COLLISION
	public boolean collision(Entity entity, Collectable collectable){
		
		ArrayList<Rectangle> enRects = entity.getCollisionRects();
		Rectangle cRect = collectable.getCollisionRect();
		for(int i = 0; i < enRects.size(); i++){
			Rectangle enRect = enRects.get(i);
			if(enRect.x + enRect.width > cRect.x &&
					enRect.x  < cRect.x + cRect.width &&
					enRect.y  < cRect.y + cRect.height &&
					enRect.y + enRect.height > cRect.y){
				return true;
			}
		}
		return false;
		
	}
	
	//ENTITY AND HAZARD COLLISION
	public boolean collisionFound(Entity entity, Hazard hazard){
		ArrayList<Rectangle> cRects = entity.getCollisionRects();
		Rectangle hRect = hazard.getCollisionRect();
		for(int r = 0; r < cRects.size(); r++){
			Rectangle cRect = cRects.get(r);
			if(cRect.x + cRect.width > hRect.x &&
					cRect.x < hRect.x + hRect.width &&
					cRect.y + cRect.height > hRect.y &&
					cRect.y < hRect.y + hRect.height){
				return true;
			}
		}
		return false;
	}
	
	//ENTITY AND PROJECTILE COLLISION
	public boolean collision(Entity e, Projectile p){
		ArrayList<Rectangle> enRects = e.getCollisionRects();
		Rectangle pRect = p.getCollisionRect();
		for(int r = 0; r < enRects.size(); r++){
			Rectangle enRect = enRects.get(r);
			if(enRect.getX() + enRect.getWidth() + e.getxVel() > p.getX() && enRect.getX() + e.getxVel() < pRect.getX() + pRect.getWidth() &&
					enRect.getY() + enRect.getHeight() + e.getyVel() > pRect.getY() && enRect.getY() + e.getyVel() < pRect.getY() + pRect.getWidth()){
				return true;
			}
		}
		return false;
	}
	
	//PROJECTILE AND TILE COLLISION
	public boolean collision(Projectile p, Tile t){
		Rectangle pRect = p.getCollisionRect();
		Rectangle tRect = t.getCollisionRect();
		if(t.isSolid()){
			if(pRect.x + pRect.width > tRect.x && tRect.x + tRect.width > pRect.x &&
					pRect.y + pRect.height > tRect.y && tRect.y + tRect.height > pRect.y){
				return true;
			}
		}
		return false;
	}
	
	//PROJECTILE AND OBSTACLE COLLISION
	public boolean collision(Projectile p, Obstacle o){
		Rectangle pRect = p.getCollisionRect();
		Rectangle oRect = o.getCollisionRect();
		if(pRect.x + pRect.width > oRect.x && pRect.x < oRect.x + oRect.width &&
				pRect.y + pRect.height > oRect.y && pRect.y < oRect.y + oRect.width){
			return true;
		}
		return false;
	}
	
	//OBSTACLE AND OBSTACLE COLLISION
	public boolean upCollision(Obstacle ob1, Obstacle ob2){
		Rectangle rect1 = ob1.getCollisionRect();
		Rectangle rect2 = ob2.getCollisionRect();
		if(rect1.y + ob1.getyVel() < rect2.y + rect2.height + ob2.getyVel() &&
				rect1.y + rect1.height/2 + ob1.getyVel() > rect2.y + rect2.height/2 + ob2.getyVel() &&
				rect1.x + rect1.width + ob1.getxVel() > rect2.x + ob2.getxVel() &&
				rect1.x + ob1.getxVel() < rect2.x + rect2.width + ob2.getxVel()){
			return true;
		}
		return false;
	}
	
	public boolean downCollision(Obstacle ob1, Obstacle ob2){
		Rectangle rect1 = ob1.getCollisionRect();
		Rectangle rect2 = ob2.getCollisionRect();
		if(rect1.y + rect1.height + 2 + ob1.getyVel() > rect2.y + ob2.getyVel() &&
				rect1.y + rect1.height/2 + ob1.getyVel() < rect2.y + rect2.height/2 + ob2.getyVel() &&
				rect1.x + rect1.width + ob1.getxVel() > rect2.x + ob2.getxVel() &&
				rect1.x + ob1.getxVel() < rect2.x + rect2.width + ob2.getxVel()){
			return true;
		}
		return false;
	}
	
	public boolean leftCollision(Obstacle ob1, Obstacle ob2){
		Rectangle rect1 = ob1.getCollisionRect();
		Rectangle rect2 = ob2.getCollisionRect();
		if(rect1.x + rect1.width + ob1.getxVel() > rect2.x + ob2.getxVel() &&
				rect1.x + rect1.width/2 + ob1.getxVel() > rect2.x + rect2.width/2 + ob2.getxVel() &&
				rect1.y + rect1.height + ob1.getyVel() > rect2.y + ob2.getyVel() &&
				rect1.y + ob1.getyVel() < rect2.y + rect2.height + ob2.getyVel()){
			return true;
		}
		return false;
	}
	
	public boolean rightCollision(Obstacle ob1, Obstacle ob2){
		Rectangle rect1 = ob1.getCollisionRect();
		Rectangle rect2 = ob2.getCollisionRect();
		if(rect1.x + ob1.getxVel() < rect2.x + rect2.width + ob2.getxVel() &&
				rect1.x + rect1.width/2 + ob1.getxVel() < rect2.x + rect2.width/2 + ob2.getxVel() &&
				rect1.y + rect1.height + ob1.getyVel() > rect2.y + ob2.getyVel() &&
				rect1.y + ob1.getyVel() < rect2.y + rect2.height + ob2.getyVel()){
			return true;
		}
		return false;
	}
	
	//OBSTACLE AND TILE COLLISION
	public boolean upCollision(Obstacle ob, Tile tile){
		
		if(tile.isSolid()){
			Rectangle obRect = ob.getCollisionRect();
			Rectangle tRect = tile.getCollisionRect();
			if(obRect.x + obRect.width + ob.getxVel() > tRect.x &&
					obRect.x + ob.getxVel() < tRect.x + tRect.width &&
					obRect.y + ob.getyVel() < tRect.y + tRect.height &&
					obRect.y + obRect.height/2 + ob.getyVel() > tRect.y + tRect.height/2){
				return true;
			}
		}
		return false;
			
	}
	
	public boolean downCollision(Obstacle ob, Tile tile){
		
		if(tile.isSolid()){
			Rectangle obRect = ob.getCollisionRect();
			Rectangle tRect = tile.getCollisionRect();
			if(obRect.x + obRect.width + ob.getxVel() > tRect.x &&
					obRect.x + ob.getxVel() < tRect.x + tRect.width &&
					obRect.y + 2 + obRect.height + ob.getyVel() > tRect.y &&
					obRect.y + obRect.height/2 + ob.getyVel() < tRect.y + tRect.height/2){
				return true;
			}
		}
		return false;
		
	}
	
	public boolean leftCollision(Obstacle ob, Tile tile){
		
		if(tile.isSolid()){
			Rectangle obRect = ob.getCollisionRect();
			Rectangle tRect = tile.getCollisionRect();
			if(obRect.y + obRect.height + ob.getyVel() > tRect.y &&
					obRect.y + ob.getyVel() < tRect.y + tRect.height &&
					obRect.x + obRect.width + ob.getxVel() > tRect.x &&
					obRect.x + obRect.width/2 + ob.getxVel() < tRect.x + tRect.width/2){
				return true;
			}
		}
		return false;
		
	}
	
	public boolean rightCollision(Obstacle ob, Tile tile){
		
		if(tile.isSolid()){
			Rectangle obRect = ob.getCollisionRect();
			Rectangle tRect = tile.getCollisionRect();
			if(obRect.y + obRect.height + ob.getyVel() > tRect.y &&
					obRect.y + ob.getyVel() < tRect.y + tRect.height &&
					obRect.x + obRect.width + ob.getxVel() > tRect.x &&
					obRect.x + obRect.width/2 + ob.getxVel() > tRect.x + tRect.width/2){
				return true;
			}
		}
		return false;
		
	}
	
	//ENTITY AND GOAL
	public boolean goalReached(Entity e, Goal g){
		Rectangle entity = new Rectangle(e.getX(), e.getY(), e.getWidth(), e.getHeight());
		Rectangle goal = new Rectangle(g.getX(), g.getY(), g.getWidth(), g.getHeight());
		if ((entity.x + entity.width < goal.x + goal.width + 32) &&
				(entity.x - 32 > goal.x) &&
				(entity.y - 32 > goal.y) &&
				(entity.y + entity.height < goal.y + goal.height + 32)){
			return true;
		}
		return false;
	}
	
	public void paint(Graphics g){
		
		Graphics2D g2 = (Graphics2D)g;
		g2.setFont(font);
		FontMetrics fontMetrics = g2.getFontMetrics();
		
		if(!started){
			
			g2.setColor(Color.BLACK);
			g2.fillRect(0, 0, getWidth(), getHeight());
			
			g2.setColor(Color.WHITE);
			g2.drawString("PRESS ANY KEY TO START", width/2 - fontMetrics.stringWidth("PRESS ANY KEY TO START")/2, getHeight()/2);
			
		}
		
		if(!complete && started){
	
			g2.setColor(Color.BLACK);		
			g2.fillRect(0, 0, getWidth(), getHeight());
			
			//DRAWING TILES
			for(int x = 0; x < mapWidth; x++){
				for(int y = 0; y < mapHeight; y++){
					Tile tile = tileMap[x][y];
					if(tile.isVisible()){
						if(intMap[x][y] == 1){
							g2.drawImage(background, tile.getX() - offSet, tile.getY(), 16, 16, this);
						}
						g2.drawImage(tile.getSprite(), tile.getX() - offSet, tile.getY(), tile.getWidth(), tile.getHeight(), this);
					}
				}
			}
			
			//DRAWING THE PLAYER
			g2.drawImage(player.getAnimation(0).getSprite(), player.getX() - offSet, player.getY(), 64, 68, this);
			g2.drawImage(player.getAnimation(1).getSprite(), player.getX() - offSet, player.getY(), 64, 68, this);			
			
			//DRAWING COLLECTABLES
			for(int i = 0; i < coins.size(); i++){
				if(coins.get(i).isVisible()){
					g2.drawImage(coins.get(i).getAnimation().getSprite(), coins.get(i).getX() - offSet,
							coins.get(i).getY(), coins.get(i).getWidth(), coins.get(i).getHeight(), this);
				}
			}
			
			g2.setColor(Color.BLACK);
			if(!sCollected){
				g2.drawImage(letterS.getAnimation().getSprite(), letterS.getX() - offSet, letterS.getY(), letterS.getWidth(), letterS.getHeight(), this);
			}
			if(!pCollected){
				g2.drawImage(letterP.getAnimation().getSprite(), letterP.getX() - offSet, letterP.getY(), letterP.getWidth(), letterP.getHeight(), this);
			}
			if(!iCollected){
				g2.drawImage(letterI.getAnimation().getSprite(), letterI.getX() - offSet, letterI.getY(), letterI.getWidth(), letterI.getHeight(), this);
			}
			if(!tCollected){
				g2.drawImage(letterT.getAnimation().getSprite(), letterT.getX() - offSet, letterT.getY(), letterT.getWidth(), letterT.getHeight(), this);
			}
			if(!zCollected){
				g2.drawImage(letterZ.getAnimation().getSprite(), letterZ.getX() - offSet, letterZ.getY(), letterZ.getWidth(), letterZ.getHeight(), this);
			}
			
			for(int i = 0; i < hKits.size(); i++){
				if(hKits.get(i).isVisible()){
					g2.drawImage(hKits.get(i).getAnimation().getSprite(), hKits.get(i).getX() - offSet,
							hKits.get(i).getY(), hKits.get(i).getWidth(), hKits.get(i).getHeight(), this);
				}
			}
			
			//DRAWING CRATES
			for(int i = 0; i < crates.size(); i++){
				if(crates.get(i).isVisible()){
					Crate crate = crates.get(i);
					g2.drawImage(crate.getAnimation().getSprite(), crate.getX() - offSet,
							crate.getY(), crate.getWidth(), crate.getHeight(), this);
				}
			}
			
			//DRAWING CANNONPIGS
			for(int i = 0; i < cPigs.size(); i++){
				if(cPigs.get(i).isVisible()){
					g2.drawImage(cPigs.get(i).getAnimation(0).getSprite(), cPigs.get(i).getX() - offSet, cPigs.get(i).getY(), cPigs.get(i).getWidth(), cPigs.get(i).getHeight(), this);
				}
			}
			
			//DRAWING FOXES
			for(int i = 0; i < foxes.size(); i++){
				if(foxes.get(i).isVisible()){
					Fox fox = foxes.get(i);
					g2.drawImage(fox.getAnimation(0).getSprite(), fox.getX() - offSet, fox.getY(), fox.getWidth(), fox.getHeight() + 2, this);
				}
			}
			
			//DRAWING WIRES
			for(int i = 0; i < wires.size(); i++){
				if(wires.get(i).isVisible()){
					g2.drawImage(wires.get(i).getAnimation().getSprite(), wires.get(i).getX() - offSet,
							wires.get(i).getY(), wires.get(i).getWidth(), wires.get(i).getHeight(), this);
				}
			}
			
			//DRAWING PROJECTILES
			for(int i = 0; i < seeds.size(); i++){
				g2.drawImage(seeds.get(i).getAnimation().getSprite(), seeds.get(i).getX() - offSet,
						seeds.get(i).getY(), seeds.get(i).getWidth(), seeds.get(i).getHeight(), this);
			}
			for(int i = 0; i < cBalls.size(); i++){
				g2.drawImage(cBalls.get(i).getAnimation().getSprite(), cBalls.get(i).getX() - offSet,
						cBalls.get(i).getY(), cBalls.get(i).getWidth(), cBalls.get(i).getHeight(), this);
			}
			
			//DRAWING THE GOAL
			if(goal.getX()  + goal.getWidth() > offSet && goal.getX() < offSet + width){
				g2.drawImage(goal.getSprite(), goal.getX() - offSet, goal.getY(), goal.getWidth(), goal.getHeight() + 2, this);
			}
			
			//DRAWING THE HUD
			g2.drawImage(HUDBackground, 0, height - 80, width, 80, this);
			g2.setColor(Color.BLACK);
			g2.setFont(font);
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
			if(player.getHealth() >= 50)g2.setColor(Color.GREEN);
			else if(player.getHealth() >= 25)g2.setColor(Color.YELLOW);
			else g2.setColor(Color.RED);
			g2.fillRect(101, getHeight() - 31, (int)(player.getHealth() * 4.5), 10);
			
			if(lives < 6){
				for(int i = 0; i < lives; i++){
					g2.drawImage(lifeImage, 100 + (i * 30), getHeight() - 70, 23, 33, this);
				}
			} else {
				g2.drawImage(lifeManyImage, 100, getHeight() - 70, 70, 33, this);
				g2.setColor(Color.BLACK);
				g2.drawString("x" + lives, 125, getHeight() - 45);
			}
			
			g2.drawImage(ammoBar[seeds.size()], width - 35, height - 75, 25, 70, this);
			
			//DRAWING HEALTH BARS
			//Crates
			for(int i = 0; i < crates.size(); i++){
				Crate crate = crates.get(i);
				if(crate.getY() + crate.getHeight() < height - 80){
					g2.setColor(Color.BLACK);
					g2.fillRect(crate.getX() + crate.getWidth()/2 - 24 - offSet, crate.getY() + crate.getHeight() + 2, 48, 10);
					g2.setColor(Color.GREEN);
					if(crate.getHealth() < 13){
						g2.setColor(Color.YELLOW);
					}
					if(crate.getHealth() < 7){
						g2.setColor(Color.RED);
					}
					g2.fillRect(crate.getX() + crate.getWidth()/2 - 23 - offSet, crate.getY() + crate.getHeight() + 3, 46 * crate.getHealth()/25 , 8);
				}
			}
			//Cannon Pigs
			for(int i = 0; i < cPigs.size(); i++){
				CannonPig pig = cPigs.get(i);
				if(pig.getY() + pig.getHeight() < height - 80){
					g2.setColor(Color.BLACK);
					g2.fillRect(pig.getX() + 16 - offSet, pig.getY() + pig.getHeight() + 2, 48, 10);
					g2.setColor(Color.GREEN);
					if(pig.getHealth() < 50){
						g2.setColor(Color.YELLOW);
					}
					if(pig.getHealth() < 25){
						g2.setColor(Color.RED);
					}
					g2.fillRect(pig.getX() + 17 - offSet, pig.getY() + pig.getHeight() + 3, 46*pig.getHealth()/100, 8);
				}
			}
			//Foxes
			for(int i = 0; i < foxes.size(); i++){
				Fox fox = foxes.get(i);
				if(fox.getY() + fox.getHeight() < height - 80){
					g2.setColor(Color.BLACK);
					g2.fillRect(fox.getX() + 16 - offSet, fox.getY() + fox.getHeight() + 2, 48, 10);
					g2.setColor(Color.GREEN);
					if(fox.getHealth() < 25){
						g2.setColor(Color.YELLOW);
					}
					if(fox.getHealth() < 13){
						g2.setColor(Color.RED);
					}
					g2.fillRect(fox.getX() + 17 - offSet, fox.getY() + fox.getHeight() + 3, 46*fox.getHealth()/50, 8);
				}
			}
			
			g2.setColor(Color.YELLOW);
			g2.fillRect(width - 200, height - 72, 140, 28);
			g2.setColor(Color.BLACK);
			g2.drawString("" + timer, width - 210 - fontMetrics.stringWidth(timer + ""), height - 52);
			if(!sCollected){
				g2.drawRect(width - 200, height - 72, 28, 28);
			}else{
				g2.drawImage(spriteS, width - 202, height - 74, 32, 32, this);
			}
			if(!pCollected){
				g2.drawRect(width - 172, height - 72, 28, 28);
			}else{
				g2.drawImage(spriteP, width - 174, height - 74, 32, 32, this);
			}
			if(!iCollected){
				g2.drawRect(width - 144, height - 72, 28, 28);
			}else{
				g2.drawImage(spriteI, width - 146, height - 74, 32, 32, this);
			}
			if(!tCollected){
				g2.drawRect(width - 116, height - 72, 28, 28);
			}else{
				g2.drawImage(spriteT, width - 118, height - 74, 32, 32, this);
			}
			if(!zCollected){
				g2.drawRect(width - 88, height - 72, 28, 28);
			}else{
				g2.drawImage(spriteZ, width - 90, height - 74, 32, 32, this);
			}
			
			g.drawImage(image, 0, 0, this);
			
		}else if(started){
			
			g2.setColor(Color.BLACK);
			g2.fillRect(0, 0, width, height);
			g2.setColor(Color.WHITE);
			
			//Display the score
			if(completeTimer > 60){
				g2.drawString("LEVEL COMPLETE!", width/2 - fontMetrics.stringWidth("LEVEL COMPLETE!")/2, 100);
				if(completeTimer > 120){
					g2.drawString("Score: ", width/2 - fontMetrics.stringWidth("Score: "), 300);
					g2.drawString("Leftover Health Bonus: ", width/2 - fontMetrics.stringWidth("Leftover Health Bonus: "), 350);
					if(lives == 6){
						g2.drawString("No Deaths Bonus: ", width/2 - fontMetrics.stringWidth("No deaths bonus: "), 400);
					}else{
						g2.drawString("Lives Lost Penalty", width/2 - fontMetrics.stringWidth("Lives Lost Penatly: "), 400);
					}
					g2.drawString("Time Penalty: ", width/2 - fontMetrics.stringWidth("Time Penatly: "), 450);
					g2.drawString("Final Score: ", width/2 - fontMetrics.stringWidth("Final Score: "), 600);
					if(completeTimer > 180){
						g2.setColor(Color.GREEN);
						g2.drawString("+ " + corescore, width/2, 300);
						if(completeTimer > 210){
							g2.drawString("+ " + player.getHealth() * 25, width/2, 350);
							if(completeTimer > 240){
								if(lives == 6){
									g2.drawString("+ 1000", width/2, 400);
								}else{
									g2.setColor(Color.RED);
									g2.drawString("- " + (6-lives)*1000, width/2, 400);
								}
								if(completeTimer > 270){
									g2.setColor(Color.RED);
									g2.drawString("- " + (timer * 20), width/2, 450);
									if(completeTimer > 360){
										g2.setColor(Color.CYAN);
										g2.drawString("" + score, width/2, 600);
										if(completeTimer > 400 && score > hiscore){
											g2.setColor(Color.CYAN);
											g2.drawString("NEW HIGHSCORE!", width/2 - fontMetrics.stringWidth("NEW HIGHSCORE!")/2, 650);
										}
									}
								}
							}
						}
					}
				}
			}
			
		}
			
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
		
		if(!started){
			started = true;
		}
		else{
			if(key.getKeyCode() == KeyEvent.VK_LEFT || key.getKeyCode() == KeyEvent.VK_A){
				
				leftPressed = true;
				//As movement in the right direction takes priority
				if(!rightPressed){
					try {
						if(player.getAnimBody() != 1){
							player.setAnimations(0, new SpitzHeadIdleLeft());
							player.setAnimations(1, new SpitzBodyMovingLeft());
							player.setAnimBody(1);
						}	
					} catch (IOException e) {
					}
				}
				
			}
			if(key.getKeyCode() == KeyEvent.VK_RIGHT || key.getKeyCode() == KeyEvent.VK_D){
				
				rightPressed = true;
				try {
					if(player.getAnimBody() != 1){
						player.setAnimations(0, new SpitzHeadIdleRight());
						player.setAnimations(1, new SpitzBodyMovingRight());
						player.setAnimBody(1);
					}
				} catch (IOException e) {
				}
					
			}
			if(key.getKeyCode() == KeyEvent.VK_SPACE){
				spacePressed = true;
				if(downCollision && !upCollision){
					player.setyVel(-21);
				} 
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent key) {
		if(key.getKeyCode() == KeyEvent.VK_LEFT || key.getKeyCode() == KeyEvent.VK_A){
			
			leftPressed = false;
			if(!rightPressed){			
				try {
					player.setAnimHead(0);
					player.setAnimBody(0);
					player.setAnimations(0, new SpitzHeadIdleLeft());
					player.setAnimations(1, new SpitzBodyIdleLeft());
				} catch (IOException e) {
				}
			}else{
				try {
					player.setAnimHead(0);
					player.setAnimBody(0);
					player.setAnimations(0, new SpitzHeadIdleRight());
					player.setAnimations(1, new SpitzBodyMovingRight());
				} catch (IOException e) {
				}
			}
			
		}
		if(key.getKeyCode() == KeyEvent.VK_RIGHT || key.getKeyCode() == KeyEvent.VK_D){
			
			rightPressed = false;
			if(!leftPressed){
				try {
					player.setAnimHead(0);
					player.setAnimBody(0);
					player.setAnimations(0, new SpitzHeadIdleRight());
					player.setAnimations(1, new SpitzBodyIdleRight());
				} catch (IOException e) {
				}
			}else{
				try {
					player.setAnimHead(0);
					player.setAnimBody(0);
					player.setAnimations(0, new SpitzHeadIdleLeft());
					player.setAnimations(1, new SpitzBodyMovingLeft());
				} catch (IOException e) {
				}
			}
			
		}
		if(key.getKeyCode() == KeyEvent.VK_SPACE){
			spacePressed = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent key) {

	}

	@Override
	public void mouseReleased(MouseEvent m) {
		
		if(m.getY() < height - 80){
			if(seeds.size() < 3){
				playSound(playerShoot);
				if(m.getX() >= player.getX() + player.getWidth()/2 - offSet){
					Seed seed = new Seed(player.getX() + player.getWidth() - 6, player.getY() + 17, "RIGHT");
					seeds.add(seed);
					player.setAnimHead(1);
					if(rightPressed){
						player.setAnimations(0, new SpitzHeadShootingRight());
					}else{
						player.setAnimBody(0);
						
						try {
							player.setAnimations(0, new SpitzHeadShootingRight());
							player.setAnimations(1, new SpitzBodyIdleRight());
						} catch (IOException e) {
						}
						
					}
				} else {
					Seed seed = new Seed(player.getX() + 6, player.getY() + 17, "LEFT");
					seeds.add(seed);
					player.setAnimHead(1);
					if(!rightPressed && leftPressed){
						player.setAnimations(0, new SpitzHeadShootingLeft());
					}else{

						player.setAnimBody(0);
						
						try{
							player.setAnimations(0, new SpitzHeadShootingLeft());
							player.setAnimations(1, new SpitzBodyIdleLeft());
						} catch (IOException e){
						}
						
					}
					
				}
			}
		}
		
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
	public void mouseClicked(MouseEvent mouse) {

			
	}
	
}
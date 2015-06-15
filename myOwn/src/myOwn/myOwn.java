package myOwn;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;

import akyStudio.framework.Animate;

public class myOwn extends Applet implements Runnable, KeyListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	final int appWidth = 800, appHeight = 480;

	MyPlayer myPlayer;
	static MyBg myBG1, myBG2, myBG3;
	Graphics myGraphics;
	BufferedImage myImage, myCharacter, myBGImage, myDuckChar, myJumpChar,
			heliBoy1Image, heliBoy2Image, myPlayerBulletImage;
	Timer gameTimer;
	Enemy_HeliBoy heliBoy1, heliBoy2;
	PlayerBullet myPlayerBullet;
	ArrayList<BufferedImage> myPlayerAnimationImages, enemyAnimationImages,myFSSimpleTileMapImages;
	Animate myPlayerAnimation, enemyAnimation;
	FSSimpleTileMap myTileMapper;

	@Override
	public void init() {

		setSize(appWidth, appHeight);
		setBackground(Color.BLACK);
		setForeground(Color.WHITE);
		setFocusable(true);
		Frame myFrame = (Frame) this.getParent().getParent();
		myFrame.setTitle("myOwn App");
		myFrame.setLocation(200, 200);

		// Grabbing Required Images
		myDuckChar = getBufferedImageResource("characterAnimation/down.png");
		myJumpChar = getBufferedImageResource("characterAnimation/jumped.png");
		myBGImage = getBufferedImageResource("environment_n_backgrounds/background.png");
		myPlayerBulletImage = getBufferedImageResource("bullet.jpg");
		
		//Grabbing Environment Tile Images and adding them to array list, Sequence mentioned below
		//TileDirt	GrassTop	GrassBot	GrassLeft	GrassRight	Ocean
		myFSSimpleTileMapImages = new ArrayList<BufferedImage>();		
		myFSSimpleTileMapImages.add(getBufferedImageResource("environment_n_backgrounds/tiledirt.png"));
		myFSSimpleTileMapImages.add(getBufferedImageResource("environment_n_backgrounds/tilegrasstop.png"));
		myFSSimpleTileMapImages.add(getBufferedImageResource("environment_n_backgrounds/tilegrassbot.png"));
		myFSSimpleTileMapImages.add(getBufferedImageResource("environment_n_backgrounds/tilegrassleft.png"));
		myFSSimpleTileMapImages.add(getBufferedImageResource("environment_n_backgrounds/tilegrassright.png"));
		myFSSimpleTileMapImages.add(getBufferedImageResource("environment_n_backgrounds/tileocean.png"));

		// Grabbing Animation resources
		myPlayerAnimationImages = animate("resource/images/characterAnimation");
		enemyAnimationImages = animate("resource/images/enemyAnimation");

		// Initializing Player Animation
		int[] myPlayerAnimationdurations = { 25, 10, 15, 10 };
		myPlayerAnimation = new Animate(myPlayerAnimationImages,
				myPlayerAnimationdurations);

		// Initializing Enemy Animation
		int[] enemyAnimationDurations = { 4, 7, 7, 7, 7, 7, 7, 7 };
		enemyAnimation = new Animate(enemyAnimationImages,
				enemyAnimationDurations);

		// Grabbing Animation images for Players and Enemy
		myCharacter = (BufferedImage) myPlayerAnimation.calculateFrame();
		heliBoy1Image = heliBoy2Image = (BufferedImage) enemyAnimation
				.calculateFrame();

		// Adding Key Listeners so that program can respond to the key events
		addKeyListener(this);

	}

	@Override
	public void start() {

		// Initializing Background
		myBG1 = new MyBg(0, 0);
		myBG2 = new MyBg(2160, 0);
		myBG3 = new MyBg(-2160, 0);

		// Initializing Grounds and oceans, 2nd Background
		myTileMapper = new FSSimpleTileMap(appWidth, appHeight, myFSSimpleTileMapImages.get(0).getWidth(), myFSSimpleTileMapImages.get(0).getHeight());
		// Loading Maps
		myTileMapper.mapLoader("/resource/data/level_maps/map1.txt");

		// Initializing Player and his animation
		myPlayer = new MyPlayer(100, 400);

		// Initializing Enemies and their respective timers to allow them born
		heliBoy1 = new Enemy_HeliBoy(550, 40);
		heliBoy2 = new Enemy_HeliBoy(570, 250);
		gameTimer = new Timer();
		startNextEnemyCounter(heliBoy1);

		// Initializing bullets
		myPlayerBullet = new PlayerBullet(0, 0);

		// Finally starting the Applet/Game in a thread
		Thread myThread = new Thread(this);
		myThread.start();
	}

	private void bulletShoot() {
		if (myPlayer.ducked) {
			myPlayerBullet.setProjectileX(myPlayer.getX() + 95);
			myPlayerBullet.setProjectileY(myPlayer.getY() + 10);
			myPlayerBullet.setJumpShoot(false);
		}

		else if (myPlayer.getJumping() > 0) {
			myPlayerBullet.setProjectileX(myPlayer.getX() + 110);
			myPlayerBullet.setProjectileY(myPlayer.getY() - 41);
			myPlayerBullet.setJumpShoot(true);
		} else {
			myPlayerBullet.setProjectileX(myPlayer.getX() + 110);
			myPlayerBullet.setProjectileY(myPlayer.getY() - 23);
			myPlayerBullet.setJumpShoot(false);
		}
		myPlayerBullet.visible = true;
	}

	@Override
	public void stop() {

	}

	@Override
	public void destroy() {

	}

	@Override
	public void run() {

		while (true) {
			try {
				Thread.sleep(17);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			myBG1.calculatePosition();
			myBG2.calculatePosition();
			myBG3.calculatePosition();

			myPlayer.calculatePosition();
			myCharacter = myPlayerAnimation.calculateFrame();
			
			myTileMapper.calculatePosition(myPlayer.getSpeedx());

			if (myPlayerBullet.visible)
				myPlayerBullet.calculatePosition();
			if (checkBornForEachEnemy(heliBoy1)) {
				heliBoy1.buzzPosition();
				heliBoy1Image = enemyAnimation.calculateFrame();
			}
			if (checkBornForEachEnemy(heliBoy2)) {
				heliBoy2.buzzPosition();
				heliBoy2Image = enemyAnimation.calculateFrame();
			}
			repaint();
			// System.out.println(a+"Updated");
			// a+=1;
		}
	}

	@Override
	public void update(Graphics g) {

		if (myImage == null) {
			myImage = (BufferedImage) createImage(this.getWidth(),
					this.getHeight());
			myGraphics = myImage.getGraphics();
		}

		myGraphics.setColor(getBackground());
		myGraphics.fillRect(0, 0, getWidth(), getHeight());
		myGraphics.setColor(getForeground());
		paint(myGraphics);
		g.drawImage(myImage, 0, 0, this);
	}

	@Override
	public void paint(Graphics g) {

		// System.out.println("paint called");

		// Background drawings
		g.drawImage(myBGImage, myBG3.getBgx(), myBG3.getBgy(), this);
		g.drawImage(myBGImage, myBG1.getBgx(), myBG1.getBgy(), this);
		g.drawImage(myBGImage, myBG2.getBgx(), myBG2.getBgy(), this);

/*		// 2nd Background drawings
		for (int i = 0; i < myGameGroundSpread1.length; i++) {
			g.drawImage(myGameGrassTopImage, myGameGroundSpread1[i][0],
					myGameGroundSpread1[i][1], this);
			g.drawImage(myGameGrassTopImage, myGameGroundSpread2[i][0],
					myGameGroundSpread2[i][1], this);
			g.drawImage(myGameGrassTopImage, myGameGroundSpread3[i][0],
					myGameGroundSpread3[i][1], this);
		}
		for (int i = 0; i < myGameOceanSpread1.length; i++) {
			g.drawImage(myGameOceanImage, myGameOceanSpread1[i][0],
					myGameOceanSpread1[i][1], this);
			g.drawImage(myGameOceanImage, myGameOceanSpread2[i][0],
					myGameOceanSpread2[i][1], this);
			g.drawImage(myGameOceanImage, myGameOceanSpread3[i][0],
					myGameOceanSpread3[i][1], this);
		}
*/
		//Tile Mapping
		myTileMapper.mapDesigner(myFSSimpleTileMapImages, g, this);
		
		// Player Drawings
		if (myPlayer.ducked)
			g.drawImage(myDuckChar, myPlayer.getX(), myPlayer.getY() - 63, this);
		else if (myPlayer.getJumping() > 0)
			g.drawImage(myJumpChar, myPlayer.getX(), myPlayer.getY() - 63, this);
		else
			g.drawImage(myCharacter, myPlayer.getX(), myPlayer.getY() - 63,
					this);

		// Enemy Drawings
		if (checkBornForEachEnemy(heliBoy1)) {
			g.drawImage(heliBoy1Image, heliBoy1.getX(), heliBoy1.getY(), this);
			startNextEnemyCounter(heliBoy2);
		}
		if (checkBornForEachEnemy(heliBoy2)) {
			g.drawImage(heliBoy2Image, heliBoy2.getX(), heliBoy2.getY(), this);
		}

		// Player Bullet Drawings
		if (myPlayerBullet.visible) {
			g.drawImage(myPlayerBulletImage, myPlayerBullet.projectileX,
					myPlayerBullet.projectileY, this);
		}

	}

	@Override
	public void keyPressed(KeyEvent e) {

		switch (e.getKeyCode()) {
		case KeyEvent.VK_DOWN:
			myPlayer.moveDown();
			myPlayer.setDucked(true);
			break;
		case KeyEvent.VK_UP:
			myPlayer.moveUp();
			break;
		case KeyEvent.VK_LEFT:
			myPlayer.moveLeft();
			break;
		case KeyEvent.VK_RIGHT:
			myPlayer.moveRight();
			break;
		case KeyEvent.VK_SPACE:
			myPlayer.jump();
			break;
		case KeyEvent.VK_CONTROL:
			bulletShoot();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_DOWN:
			myPlayer.setDucked(false);
			break;
		case KeyEvent.VK_UP:
			break;
		case KeyEvent.VK_LEFT:
			myPlayer.stopMovingHorizontally();
			break;
		case KeyEvent.VK_RIGHT:
			myPlayer.stopMovingHorizontally();
			break;
		case KeyEvent.VK_SPACE:
			break;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	public void startNextEnemyCounter(Enemy_HeliBoy tempEnemy) {
		gameTimer.schedule(new TimerTask() {

			@Override
			public void run() {
				tempEnemy.readyToBorn = true;
			}

		}, 5000);

	}

	public boolean checkBornForEachEnemy(Enemy_HeliBoy tempEnemy) {
		return tempEnemy.readyToBorn;
	}

	private BufferedImage getBufferedImageResource(String ImageName) {
		try {
			return ImageIO.read(this.getClass().getResource(
					"/resource/images/" + ImageName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private ArrayList<BufferedImage> animate(String relativePath) {

		File resourceDir = new File(relativePath);
		File[] imageList = resourceDir.listFiles();
		ArrayList<BufferedImage> tempList = new ArrayList<BufferedImage>();

		for (int i = 0; i < imageList.length; i++) {
			String s = imageList[i].getName().substring(0,
					imageList[i].getName().length() - 4);
			if (s.equalsIgnoreCase(Integer.toString(i))) {
				try {
					tempList.add(ImageIO.read(this.getClass().getResource(
							"/" + relativePath + "/" + imageList[i].getName())));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return tempList;
	}
}

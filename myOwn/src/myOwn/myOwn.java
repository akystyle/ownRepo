package myOwn;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import akyStudio.framework.Animate;

public class myOwn extends Applet implements Runnable, KeyListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	MyPlayer myPlayer;
	static MyBg myBG1, myBG2, myBG3;
	Graphics myGraphics;
	Image myImage, myCharacter, myBGImage, myDuckChar, myJumpChar,
			heliBoy1Image, heliBoy2Image, myPlayerBulletImage;
	Timer gameTimer;
	Enemy_HeliBoy heliBoy1, heliBoy2;
	PlayerBullet myPlayerBullet;
	ArrayList<Image> myPlayerAnimationImages, enemyAnimationImages;
	Animate myPlayerAnimation, enemyAnimation;

	@Override
	public void init() {

		setSize(800, 480);
		setBackground(Color.BLACK);
		setForeground(Color.WHITE);
		setFocusable(true);
		Frame myFrame = (Frame) this.getParent().getParent();
		myFrame.setTitle("myOwn App");
		myFrame.setLocation(200, 200);

		// Grabbing Required Images
		myDuckChar = getImageResource("characterAnimation/down.png");
		myJumpChar = getImageResource("characterAnimation/jumped.png");
		myBGImage = getImageResource("background.png");
		myPlayerBulletImage = getImageResource("bullet.jpg");

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
		myCharacter = myPlayerAnimation.calculateFrame();
		heliBoy1Image = heliBoy2Image = enemyAnimation.calculateFrame();

		// Adding Key Listeners so that program can respond to the key events
		addKeyListener(this);

	}

	@Override
	public void start() {

		// Initializing Background
		myBG1 = new MyBg(0, 0);
		myBG2 = new MyBg(2160, 0);
		myBG3 = new MyBg(-2160, 0);

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

	public void bulletShoot() {
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
			myImage = createImage(this.getWidth(), this.getHeight());
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

	private Image getImageResource(String ImageName) {
		return getImage(this.getClass().getResource(
				"/resource/images/" + ImageName));
	}

	private ArrayList<Image> animate(String relativePath) {

		File resourceDir = new File(relativePath);
		File[] imageList = resourceDir.listFiles();
		ArrayList<Image> tempList = new ArrayList<Image>();

		for (int i = 0; i < imageList.length; i++) {
			String s = imageList[i].getName().substring(0,
					imageList[i].getName().length() - 4);
			if (s.equalsIgnoreCase(Integer.toString(i))) {
				tempList.add(getImage(this.getClass().getResource(
						"/" + relativePath + "/" + imageList[i].getName())));
			}
		}
		return tempList;
	}
}

package myOwn;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Timer;
import java.util.TimerTask;

public class myOwn extends Applet implements Runnable, KeyListener {

	MyPlayer myPlayer;
	static MyBg myBG;
	Graphics myGraphics;
	Image myImage, myCharacter, myBGImage, myDuckChar, myJumpChar,heliBoy1Image,heliBoy2Image;
	Timer gameTimer;
	Enemy_HeliBoy heliBoy1,heliBoy2;

	@Override
	public void init() {

		setSize(800, 480);
		setBackground(Color.BLACK);
		// setForeground(Color.WHITE);
		setFocusable(true);
		Frame myFrame = (Frame) this.getParent().getParent();
		myFrame.setTitle("myOwn App");
		myFrame.setLocation(200, 200);

		myCharacter = getImage(this.getClass().getResource(
				"/resource/myCharacter.png"));
		myDuckChar = getImage(this.getClass().getResource("/resource/down.png"));
		myJumpChar = getImage(this.getClass().getResource(
				"/resource/jumped.png"));
		myBGImage = getImage(this.getClass().getResource(
				"/resource/background.png"));
		heliBoy1Image = heliBoy2Image = getImage(this.getClass().getResource(
				"/resource/heliboy.png"));;

		addKeyListener(this);

	}

	@Override
	public void start() {
		myBG = new MyBg();
		myPlayer = new MyPlayer();
		heliBoy1 = new Enemy_HeliBoy(550,40);
		heliBoy2 = new Enemy_HeliBoy(570,250);
		gameTimer = new Timer();
		Thread myThread = new Thread(this);

		startNextEnemyCounter(heliBoy1);
		
		myThread.start();
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
			myBG.calculatePosition();
			myPlayer.calculatePosition();
			if(checkBornForEachEnemy(heliBoy1))
				heliBoy1.buzzPosition();
			if(checkBornForEachEnemy(heliBoy2))
				heliBoy2.buzzPosition();
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

		// if (myBG.getBgx() < 0){
		// g.drawImage(myBGImage, (myBG.getBgx()%2160)+2160, myBG.getBgy(),
		// this);
		// g.drawImage(myBGImage,myBG.getBgx(),myBG.getBgy(),this);
		// }
		// else if(myBG.getBgx() > 0){
		// g.drawImage(myBGImage, (myBG.getBgx()%2160)-2160, myBG.getBgy(),
		// this);
		// g.drawImage(myBGImage,myBG.getBgx(),myBG.getBgy(),this);
		// }
		// else
		// g.drawImage(myBGImage,myBG.getBgx(),myBG.getBgy(),this);

		if((myBG.getBgx()/2160)>=1){
			System.out.println("Getting here" + myBG.getBgx());
			g.drawImage(myBGImage, myBG.getBgx()-(myBG.getBgx()%2160), myBG.getBgy(), this);
			g.drawImage(myBGImage, (myBG.getBgx()-(myBG.getBgx()%2160)) + 2160, myBG.getBgy(), this);
			g.drawImage(myBGImage, (myBG.getBgx()-(myBG.getBgx()%2160)) - 2160, myBG.getBgy(), this);	
		}
		else if((myBG.getBgx()/2160)<1){
			System.out.println("Getting outta"+ myBG.getBgx());
			g.drawImage(myBGImage, myBG.getBgx()+(myBG.getBgx()%2160), myBG.getBgy(), this);
			g.drawImage(myBGImage, (myBG.getBgx()+(myBG.getBgx()%2160)) + 2160, myBG.getBgy(), this);
			g.drawImage(myBGImage, (myBG.getBgx()+(myBG.getBgx()%2160)) - 2160, myBG.getBgy(), this);			
		}
//		else{
//			g.drawImage(myBGImage, myBG.getBgx(), myBG.getBgy(), this);
//			g.drawImage(myBGImage, myBG.getBgx() + 2160, myBG.getBgy(), this);
//			g.drawImage(myBGImage, myBG.getBgx() - 2160, myBG.getBgy(), this);	
//		}

		if (myPlayer.ducked)
			g.drawImage(myDuckChar, myPlayer.getX(), myPlayer.getY() - 63, this);
		else if (myPlayer.getJumping() > 0)
			g.drawImage(myJumpChar, myPlayer.getX(), myPlayer.getY() - 63, this);
		else
			g.drawImage(myCharacter, myPlayer.getX(), myPlayer.getY() - 63,
					this);
		
		if(checkBornForEachEnemy(heliBoy1)){
			g.drawImage(heliBoy1Image,heliBoy1.getX(),heliBoy1.getY(),this);
			startNextEnemyCounter(heliBoy2);
		}
		if(checkBornForEachEnemy(heliBoy2)){
			g.drawImage(heliBoy2Image,heliBoy2.getX(),heliBoy2.getY(),this);
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
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub

	}
	
	public void startNextEnemyCounter(Enemy_HeliBoy tempEnemy){
		gameTimer.schedule(new TimerTask() {

			@Override
			public void run() {
				tempEnemy.readyToBorn = true;
			}

		}, 5000);

	}
	
	public boolean checkBornForEachEnemy(Enemy_HeliBoy tempEnemy){
		return tempEnemy.readyToBorn;
	}
}

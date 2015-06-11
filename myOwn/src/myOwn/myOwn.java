package myOwn;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;

public class myOwn extends Applet implements Runnable, KeyListener {

	MyPlayer myPlayer;
	MyBg myBG;
	Graphics myGraphics;
	URL myDocURL;
	Image myImage, myCharacter, myBGImage;

	@Override
	public void init() {

		setSize(800, 480);
		setBackground(Color.BLACK);
		// setForeground(Color.WHITE);
		setFocusable(true);
		Frame myFrame = (Frame) this.getParent().getParent();
		myFrame.setTitle("myOwn App");
		myFrame.setLocation(200, 200);
		try {
			// myDocURL = new
			// URL("file:/D:/MyDocuments/Projects/Android/Game Maker/myOwn/Repository/myOwn/");

			myDocURL = new URL("resource/myCharacter.png");

		} catch (Exception e) {
			e.printStackTrace();
		}

		
		System.out.println(this.getClass().getResource("resource/myCharacter.png"));
		myCharacter = getImage(myDocURL);
		//myBGImage = getImage(myDocURL, "resource/background.png");

		addKeyListener(this);

	}

	@Override
	public void start() {
		myBG = new MyBg();
		myPlayer = new MyPlayer();
		Thread myThread = new Thread(this);
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
		//g.drawImage(myBGImage, myBG.getBgx(), myBG.getBgy(), this);
		g.drawImage(myCharacter, myPlayer.getX() - 61, myPlayer.getY() - 63,
				this);
	}

	@Override
	public void keyPressed(KeyEvent e) {

		switch (e.getKeyCode()) {
		case KeyEvent.VK_DOWN:
			myPlayer.moveDown();
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

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}
}

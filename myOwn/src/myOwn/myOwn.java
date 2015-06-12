package myOwn;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;

import javax.xml.bind.JAXBElement.GlobalScope;

public class myOwn extends Applet implements Runnable, KeyListener {

	MyPlayer myPlayer;
	public static MyBg myBG;
	Graphics myGraphics;
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

		myCharacter = getImage(this.getClass().getResource(
				"/resource/myCharacter.png"));
		myBGImage = getImage(this.getClass().getResource(
				"/resource/background.png"));

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
//		if (myBG.getBgx() < 0){
//				g.drawImage(myBGImage, (myBG.getBgx()%2160)+2160, myBG.getBgy(), this);
//				g.drawImage(myBGImage,myBG.getBgx(),myBG.getBgy(),this);
//		}
//		else if(myBG.getBgx() > 0){
//				g.drawImage(myBGImage, (myBG.getBgx()%2160)-2160, myBG.getBgy(), this);
//				g.drawImage(myBGImage,myBG.getBgx(),myBG.getBgy(),this);
//		}
//		else
//			g.drawImage(myBGImage,myBG.getBgx(),myBG.getBgy(),this);

		g.drawImage(myBGImage,myBG.getBgx(),myBG.getBgy(),this);
		g.drawImage(myBGImage,myBG.getBgx()+2160,myBG.getBgy(),this);
		g.drawImage(myBGImage,myBG.getBgx()-2160,myBG.getBgy(),this);
		
		g.drawImage(myCharacter, myPlayer.getX(), myPlayer.getY() - 63, this);
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

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

	int x = 100, y = 100, speedx = 0, speedy = 0;
	Graphics myGraphics;
	URL myDocURL;
	Image myImage,myCharacter;

	@Override
	public void init() {

		setSize(800, 480);
		setBackground(Color.BLACK);
		//setForeground(Color.WHITE);
		setFocusable(true);
		Frame myFrame = (Frame) this.getParent().getParent();
		myFrame.setTitle("myOwn App");
		myFrame.setLocation(200, 200);
		try{
		myDocURL = getDocumentBase();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		myCharacter = getImage(myDocURL,"resources/myCharacter.png");
		
		addKeyListener(this);

	}

	@Override
	public void start() {
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
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			calculatePosition();
			repaint();
			// System.out.println(a+"Updated");
			// a+=1;
		}
	}

	private void calculatePosition() {
		if (x < 1 && speedx < 0)
			speedx = 0;
		else
			x = x + speedx;

		if (y < 1 && speedy < 0)
			speedy = 0;
		else
			y = y + speedy;
	}

	@Override
	public void update(Graphics g) {

		if (myImage == null) {
			myImage = createImage(this.getWidth(),this.getHeight());
			myGraphics = myImage.getGraphics();
		}

		myGraphics.setColor(getBackground());
		myGraphics.fillRect(0, 0, getWidth(), getHeight());
		myGraphics.setColor(getForeground());
		paint(myGraphics);
		g.drawImage(myImage, 0, 0, this);
	}

	@Override
	public void paint(Graphics g){
		
		System.out.println("paint called");
		g.drawImage(myCharacter, x, y, this);
	}

	@Override
	public void keyPressed(KeyEvent e) {

		switch (e.getKeyCode()) {
		case KeyEvent.VK_DOWN:
			if (speedy == -1)
				speedy = 1;
			else
				speedy += 1;
			break;

		case KeyEvent.VK_UP:
			if (speedy == 1)
				speedy = -1;
			else
				speedy -= 1;
			break;
		case KeyEvent.VK_LEFT:
			if (speedx == 1)
				speedx = -1;
			else
				speedx -= 1;
			break;
		case KeyEvent.VK_RIGHT:
			if (speedx == -1)
				speedx = 1;
			else
				speedx += 1;
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

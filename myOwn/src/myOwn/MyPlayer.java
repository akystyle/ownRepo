package myOwn;

import java.awt.Rectangle;

public class MyPlayer {

	int bodyBoundX, bodyBoundY, bodyBoundWidth, bodyBoundHeight;
	int x, y, speedx = 0, speedy = -3, jumping = 0;
	boolean ducked;
	Rectangle myPlayerBoundRect, leftHandBoundRect, rightHandBoundRect;

	public MyPlayer(int initx, int inity) {
		x = initx;
		y = inity;
		myPlayerBoundRect = leftHandBoundRect = rightHandBoundRect = new Rectangle(
				0, 0, 0, 0);
		updateBodyBounder();
	}

	public void calculatePosition() {
		if (x <= 1) {
			x = 1;
			if (speedx < 0)
				MyBg.bgSpeedx = 2;
			else
				x = x + speedx;
		} else if (x >= 300) {
			x = 300;
			if (speedx > 0)
				MyBg.bgSpeedx = -2;
			else
				x = x + speedx;
		} else {
			MyBg.bgSpeedx = 0;
			x = x + speedx;
		}

		if (jumping == 1) {
			y = y + speedy;
			if (y <= 237)
				jumping = 2;
		} else if (jumping == 2) {
			y = y - speedy;
			if (y >= 337)
				jumping = 0;
		}
		updateBodyBounder();
	}

	public void moveDown() {
	}

	public void moveUp() {

	}

	public void stopMovingHorizontally() {
		speedx = 0;
		MyBg.bgSpeedx = 0;
	}

	public void moveLeft() {

		speedx = -6;
		/*
		 * if (speedx <= -10) speedx = -12;
		 */
	}

	public void moveRight() {
		speedx = 6;
		/*
		 * if (speedx >= 10) speedx = 12;
		 */
	}

	public void jump() {

		if (jumping == 0) {
			jumping = 1;
		}
	}

	private void updateBodyBounder() {
		if (jumping == 0 && ducked == false) {
			bodyBoundHeight = 125;
			bodyBoundWidth = 66;
			bodyBoundX = x + 28;
			bodyBoundY = y;
			myPlayerBoundRect.setRect(bodyBoundX, bodyBoundY, bodyBoundWidth,
					bodyBoundHeight);
			leftHandBoundRect.setRect(bodyBoundX - 26, bodyBoundY + 33, 26, 18);
			rightHandBoundRect
					.setRect(bodyBoundX + 67, bodyBoundY + 33, 26, 18);
		} else if (ducked) {

		} else {

		}
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getSpeedx() {
		return speedx;
	}

	public void setSpeedx(int speedx) {
		this.speedx = speedx;
	}

	public int getSpeedy() {
		return speedy;
	}

	public void setSpeedy(int speedy) {
		this.speedy = speedy;
	}

	public int getJumping() {
		return jumping;
	}

	public void setJumping(int jumping) {
		this.jumping = jumping;
	}

	public boolean isDucked() {
		return ducked;
	}

	public void setDucked(boolean ducked) {
		this.ducked = ducked;
	}

	public Rectangle getMyPlayerBoundRect() {
		return myPlayerBoundRect;
	}

	public void setMyPlayerBoundRect(Rectangle myPlayerBoundRect) {
		this.myPlayerBoundRect = myPlayerBoundRect;
	}

	public Rectangle getLeftHandBoundRect() {
		return leftHandBoundRect;
	}

	public void setLeftHandBoundRect(Rectangle leftHandBoundRect) {
		this.leftHandBoundRect = leftHandBoundRect;
	}

	public Rectangle getRightHandBoundRect() {
		return rightHandBoundRect;
	}

	public void setRightHandBoundRect(Rectangle rightHandBoundRect) {
		this.rightHandBoundRect = rightHandBoundRect;
	}

}

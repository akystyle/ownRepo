package myOwn;

import java.awt.Rectangle;
import java.awt.geom.Line2D;

public class MyPlayer {

	int bodyBoundX, bodyBoundY, bodyBoundWidth, bodyBoundHeight;
	int x, y, speedx = 0, speedy = -3, jumping = 0;
	boolean ducked, canMoveRight, canMoveLeft;
	Rectangle myPlayerBoundRect, leftHandBoundRect, rightHandBoundRect;

	public MyPlayer(int initx, int inity) {
		x = initx;
		y = inity;
		myPlayerBoundRect = new Rectangle(0, 0, 0, 0);
		leftHandBoundRect = new Rectangle(0, 0, 0, 0);
		rightHandBoundRect = new Rectangle(0, 0, 0, 0);
		canMoveLeft = true;
		canMoveRight = true;
		updateBodyBounder();
	}

	public void calculatePosition() {

		if (speedx > 0) {
			if (x >= 380) {
				x = 380;
				MyBg.bgSpeedx = -2;
				// System.out.println("Maximum Character Right limit");
			} else {
				// System.out.println("Should get here");
				movement();
			}
		} else if (speedx < 0) {
			if (x <= 1) {
				x = 1;
				MyBg.bgSpeedx = 2;
				// System.out.println("Minimum character left limit");
			} else {
				// System.out.println("Should get here");
				movement();
			}
		} else {
			MyBg.bgSpeedx = 0;
		}

		if (jumping == 1) {
			y = y + speedy;
			if (y <= 215)
				jumping = 2;
		} else if (jumping == 2) {
			y = y - speedy;
			if (y >= 315)
				jumping = 0;
		}
		updateBodyBounder();
	}

	private void movement() {
		if ((canMoveRight && speedx > 0) || (canMoveLeft && speedx < 0))
			x += speedx;
		else
			stopMovingHorizontally();
	}

	public boolean collided() {

		for (int row = 0; row < myOwn.myTileMapper.tileBounder.length; row++) {
			for (int column = 0; column < myOwn.myTileMapper.tileBounder[row].length; column++) {

				if (myPlayerBoundRect
						.intersects(myOwn.myTileMapper.tileBounder[row][column])) {
					String collisionSide = whichSidecollided(myPlayerBoundRect,
							myOwn.myTileMapper.tileBounder[row][column]);
//					System.out.println(x + 66 + " " + myOwn.myTileMapper.tileBounder[row][column].x);

					if (collisionSide == null) {
						canMoveRight = true;
						canMoveLeft = true;
					} else if (collisionSide == "left") {
						canMoveRight = true;
						canMoveLeft = false;
						stopMovingHorizontally();
						return true;
					} else if (collisionSide == "right") {
						canMoveRight = false;
						canMoveLeft = true;
						stopMovingHorizontally();
						return true;
					}
				}

				if (leftHandBoundRect
						.intersects(myOwn.myTileMapper.tileBounder[row][column])) {
					// System.out.println("Player Left Hand Collided");
					return true;
				}
				if (rightHandBoundRect
						.intersects(myOwn.myTileMapper.tileBounder[row][column])) {
					// System.out.println("Player Right Hand Collided");
					return true;
				}

			}
		}
		return false;
	}

	private String whichSidecollided(Rectangle rect, Rectangle rect2) {
		Line2D rightSide = new Line2D.Float(rect.x + rect.width, rect.y, rect.x
				+ rect.width, rect.y + rect.height);
		Line2D leftSide = new Line2D.Float(rect.x, rect.y, rect.x, rect.y
				+ rect.height);

		if (rightSide.intersects(rect2))
			return "right";
		else if (leftSide.intersects(rect2))
			return "left";
		else
			return null;
	}

	public void moveDown() {
	}

	public void moveUp() {

	}

	public void moveLeft() {
		if (canMoveLeft && !collided())
			speedx = -6;
		else
			speedx = 0;
	}

	public void moveRight() {
		if (canMoveRight && !collided())
			speedx = 6;
		else
			speedx = 0;
	}

	public void jump() {
		if (jumping == 0)
			jumping = 1;
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
			bodyBoundHeight = 72;
			bodyBoundWidth = 68;
			bodyBoundX = x + 28;
			bodyBoundY = y + 53;
			myPlayerBoundRect.setRect(bodyBoundX, bodyBoundY, bodyBoundWidth,
					bodyBoundHeight);
			leftHandBoundRect.setRect(bodyBoundX, bodyBoundY + 13, 26, 18);
			rightHandBoundRect
					.setRect(bodyBoundX + 42, bodyBoundY + 13, 26, 18);

		} else {
			bodyBoundHeight = 125;
			bodyBoundWidth = 85;
			bodyBoundX = x + 18;
			bodyBoundY = y;
			myPlayerBoundRect.setRect(bodyBoundX, bodyBoundY, bodyBoundWidth,
					bodyBoundHeight);
			leftHandBoundRect.setRect(bodyBoundX - 13, bodyBoundY + 15, 26, 18);
			rightHandBoundRect
					.setRect(bodyBoundX + 73, bodyBoundY + 15, 26, 18);
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

	public int getBodyBoundX() {
		return bodyBoundX;
	}

	public void setBodyBoundX(int bodyBoundX) {
		this.bodyBoundX = bodyBoundX;
	}

	public int getBodyBoundY() {
		return bodyBoundY;
	}

	public void setBodyBoundY(int bodyBoundY) {
		this.bodyBoundY = bodyBoundY;
	}

	public int getBodyBoundWidth() {
		return bodyBoundWidth;
	}

	public void setBodyBoundWidth(int bodyBoundWidth) {
		this.bodyBoundWidth = bodyBoundWidth;
	}

	public int getBodyBoundHeight() {
		return bodyBoundHeight;
	}

	public void setBodyBoundHeight(int bodyBoundHeight) {
		this.bodyBoundHeight = bodyBoundHeight;
	}

	public boolean isCanMoveRight() {
		return canMoveRight;
	}

	public void setCanMoveRight(boolean canMoveRight) {
		this.canMoveRight = canMoveRight;
	}

	public boolean isCanMoveLeft() {
		return canMoveLeft;
	}

	public void setCanMoveLeft(boolean canMoveLeft) {
		this.canMoveLeft = canMoveLeft;
	}

	public void stopMovingHorizontally() {
		speedx = 0;
		MyBg.bgSpeedx = 0;
	}

	public void stopMovingVertically() {
		speedy = 0;
		jumping = 0;
	}
}

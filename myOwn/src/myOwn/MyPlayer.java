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
			} else {
				movement();
			}
		} else if (speedx < 0) {
			if (x <= 1) {
				x = 1;
				MyBg.bgSpeedx = 2;
			} else {
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
		if (speedx > 0 && canMoveRight){
			System.out.println(canMoveRight + " : And Speed is 6");
			x += speedx;
		}
		else if (speedx < 0 && canMoveLeft){
			System.out.println(canMoveRight + " : And Speed is -6");
			x += speedx;
		}
		collided();
	}
	public void collided(){
		for (int row = 0; row < myOwn.myTileMapper.tileBounder.length; row++) {
			for (int column = 0; column < myOwn.myTileMapper.tileBounder[0].length; column++) {
				
				
				if(myPlayerBoundRect.intersects(myOwn.myTileMapper.tileBounder[row][column])){
					stopMovingHorizontally();
					avoidCollisionRevertMovement();
					
					String collisionSide = whichSidecollided(myPlayerBoundRect,myOwn.myTileMapper.tileBounder[row][column]);
					System.out.println(collisionSide);
					if(collisionSide == "right"){
						canMoveRight = true;
						canMoveLeft = false;
					}else if(collisionSide == "left"){
						canMoveRight = false;
						canMoveLeft = true;
					}
				}else{
					System.out.println("Setting both true");
					canMoveRight = true;
					canMoveLeft = true;
				}
					
				if(leftHandBoundRect.intersects(myOwn.myTileMapper.tileBounder[row][column])){
					//System.out.println("Player Left Hand Collided");
				}
				if(rightHandBoundRect.intersects(myOwn.myTileMapper.tileBounder[row][column])){
					//System.out.println("Player Right Hand Collided");
				}
				
			}
		}

	}
	
	private String whichSidecollided(Rectangle rect,Rectangle rect2) {
		Line2D rightSide = new Line2D.Float((rect.x+rect.width),rect.y,rect.x+rect.width,rect.y+rect.height);
		Line2D leftSide = new Line2D.Float(rect.x,rect.y,rect.x,rect.y+rect.height);
		if(rightSide.intersects(rect2))
			return "right";
		else if(leftSide.intersects(rect2))
			return "left";
		else
			return null;
	}
	
	
	public void moveDown() {
	}

	public void moveUp() {

	}

	public void stopMovingHorizontally() {
		speedx = 0;
		MyBg.bgSpeedx = 0;
	}

	public void avoidCollisionRevertMovement() {
		x -= speedx;
	}

	public void moveLeft() {
		if (canMoveLeft)
			speedx = -6;
	}

	public void moveRight() {
		if (canMoveRight)
			speedx = 6;
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

}

package myOwn;

public class MyPlayer {

	int x = 100, y = 400, speedx = 0, speedy = -3, jumping = 0;
	boolean ducked;

	public void calculatePosition() {
		if (x <= 1) {
			x = 1;
			if (speedx < 0) {
				myOwn.myBG.bgSpeedx = 3;
			}
		} else if (x >= 300) {
			x = 300;
			if (speedx > 0) {
				myOwn.myBG.bgSpeedx = -3;
			}
		} else
			myOwn.myBG.bgSpeedx = 0;
		x = x + speedx;

		if (jumping == 1) {
			y = y + speedy;
			if (y <= 350)
				jumping = 2;
		} else if (jumping == 2) {
			y = y - speedy;
			if (y >= 400)
				jumping = 0;
		}
	}

	public void moveDown() {
	}

	public void moveUp() {

	}

	public void stopMovingHorizontally() {
		speedx = 0;
		myOwn.myBG.bgSpeedx = 0;
	}

	public void moveLeft() {
		if (speedx > 1)
			speedx = -3;
		else
			speedx -= 3;

		if (speedx <= -7)
			speedx = -7;
	}

	public void moveRight() {
		if (speedx < 1)
			speedx = 3;
		else
			speedx += 3;

		if (speedx >= 7)
			speedx = 7;
	}

	public void jump() {

		if (jumping == 0) {
			jumping = 1;
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

}

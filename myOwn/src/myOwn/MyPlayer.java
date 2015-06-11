package myOwn;

public class MyPlayer {

	int x = 100, y = 400, speedx = 0, speedy = -3,jumping=0;
	

	public void calculatePosition() {
		if (x <= 1 && speedx < 0)
			x = 1;
		else if (x >= 260 && speedx > 0)
			x = 260;
		else
			x = x + speedx;
		
		if (jumping == 1){
			y = y+speedy;
			if( y <= 350)
				jumping = 2;
		}
		else if(jumping == 2){
			y = y-speedy;
			if( y >= 400)
				jumping = 0;
		}
	}

	public void moveDown() {

	}

	public void moveUp() {

	}

	public void moveLeft() {
		if (speedx > 1)
			speedx = -1;
		else
			speedx -= 1;

		if (speedx <= -3)
			speedx = -3;
	}

	public void moveRight() {
		if (speedx < 1)
			speedx = 1;
		else
			speedx += 1;

		if (speedx >= 5)
			speedx = 5;
	}
	
	public void jump(){
		
		if(jumping == 0){
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

}

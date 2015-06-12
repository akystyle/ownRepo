package myOwn;

public class Enemy {

	int x = 250,y = 100,speedx,speedy,maxHealth, currentHealth, power;
	boolean readyToBorn;
	
	public void calculatePosition(){
		
	}
	
	public void attack(){
		
	}
	
	public void die(){
		
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

	public int getMaxHealth() {
		return maxHealth;
	}

	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
	}

	public int getCurrentHealth() {
		return currentHealth;
	}

	public void setCurrentHealth(int currentHealth) {
		this.currentHealth = currentHealth;
	}

	public int getPower() {
		return power;
	}

	public void setPower(int power) {
		this.power = power;
	}

	public boolean isReadyToBorn() {
		return readyToBorn;
	}

	public void setReadyToBorn(boolean readyToBorn) {
		this.readyToBorn = readyToBorn;
	}

}

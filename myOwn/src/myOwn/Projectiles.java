package myOwn;

public class Projectiles {

	int projectileX, projectileY, projSpeedX, projSpeedY;
	boolean visible,jumpShoot;
	
public Projectiles(int initX, int initY){
	projectileX = initX;
	projectileY = initY;
	projSpeedX = 6;
	projSpeedY = 3;
	//visible = true;
}

public void calculatePosition(){
	
}

public int getProjectileX() {
	return projectileX;
}

public void setProjectileX(int projectileX) {
	this.projectileX = projectileX;
}

public int getProjectileY() {
	return projectileY;
}

public void setProjectileY(int projectileY) {
	this.projectileY = projectileY;
}

public int getProjSpeedX() {
	return projSpeedX;
}

public void setProjSpeedX(int projSpeedX) {
	this.projSpeedX = projSpeedX;
}

public int getProjSpeedY() {
	return projSpeedY;
}

public void setProjSpeedY(int projSpeedY) {
	this.projSpeedY = projSpeedY;
}

public boolean isVisible() {
	return visible;
}

public void setVisible(boolean visible) {
	this.visible = visible;
}

public boolean isJumpShoot() {
	return jumpShoot;
}

public void setJumpShoot(boolean jumpShoot) {
	this.jumpShoot = jumpShoot;
}
	
	
}

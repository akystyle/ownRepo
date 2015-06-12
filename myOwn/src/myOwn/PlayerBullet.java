package myOwn;

public class PlayerBullet extends Projectiles {

	public PlayerBullet(int initX, int initY) {
		super(initX, initY);
		// TODO Auto-generated constructor stub
	}

	public void calculatePosition() {
		if (jumpShoot)
			projectileY -= projSpeedY;

		projectileX += projSpeedX;

		if (projectileX >= 800 || projectileY<=0) {
			visible = false;
		}
	}

}

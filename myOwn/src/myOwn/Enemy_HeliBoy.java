package myOwn;

public class Enemy_HeliBoy extends Enemy {
	
	int buzzing = 6, initX, initY;

	public Enemy_HeliBoy(int axisX,int axisY) {
		// TODO Auto-generated constructor stub
		x = initX = axisX;
		y = initY = axisY;
	}

	public void buzzPosition(){
		if(x == initX && y == initY){
			x -= buzzing;
		}
		else if(x < initX && y==initY && x > initX-60 ){
			x -=buzzing;
		}
		else if(x <= initX-60 && y==initY){
			y +=buzzing;
		}
		else if(x <= initX-60 && y > initY && y < initY+60 ){
			y +=buzzing;
		}
		else if(x <= initX-60 && y >= initY+60 && x <= initX){
			x += buzzing;
		}
		else if(y >= initY+60 && x <= initX){
			x += buzzing;
		}
		else if(y >= initY+60 && x <= initX && y <= initY ){
			y -= buzzing;
		}
		else if(x <= initX && y <= initY){
			y -= buzzing;
		}
	}
}

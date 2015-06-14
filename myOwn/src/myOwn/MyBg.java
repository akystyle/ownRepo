package myOwn;

public class MyBg {

	int bgx, bgy;
	static int bgSpeedx;
	static int bgSpeedy;
	
	public MyBg(int backX,int backY){
		
		bgx = backX;
		bgy = backY;
	}
	
	public void calculatePosition(){
		bgx +=bgSpeedx;
		
		if(bgx<=-2160)
			bgx+=4320;
		if(bgx>=2160)
			bgx-=4320;
	}

	public int getBgx() {
		return bgx;
	}

	public void setBgx(int bgx) {
		this.bgx = bgx;
	}

	public int getBgy() {
		return bgy;
	}

	public void setBgy(int bgy) {
		this.bgy = bgy;
	}

	public int getBgSpeedx() {
		return bgSpeedx;
	}

	public void setBgSpeedx(int bgSpeedx) {
		MyBg.bgSpeedx = bgSpeedx;
	}

	public int getBgSpeedy() {
		return bgSpeedy;
	}

	public void setBgSpeedy(int bgSpeedy) {
		MyBg.bgSpeedy = bgSpeedy;
	}
	
	
	
}

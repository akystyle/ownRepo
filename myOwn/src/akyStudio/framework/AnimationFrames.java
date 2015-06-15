package akyStudio.framework;

import java.awt.image.BufferedImage;

public class AnimationFrames {

	BufferedImage myImage;
	int index,imageDuration;

	public AnimationFrames(int x,BufferedImage img,int time){
		index = x;
		myImage = img;
		imageDuration = time;
	}

	public BufferedImage getMyImage() {
		return myImage;
	}

	public void setMyImage(BufferedImage myImage) {
		this.myImage = myImage;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getImageDuration() {
		return imageDuration;
	}

	public void setImageDuration(int imageDuration) {
		this.imageDuration = imageDuration;
	}
	

}

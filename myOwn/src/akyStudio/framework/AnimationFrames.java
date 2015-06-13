package akyStudio.framework;

import java.awt.Image;

public class AnimationFrames {

	Image myImage;
	int index,imageDuration;

	public AnimationFrames(int x,Image img,int time){
		index = x;
		myImage = img;
		imageDuration = time;
	}

	public Image getMyImage() {
		return myImage;
	}

	public void setMyImage(Image myImage) {
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

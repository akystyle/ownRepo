package akyStudio.framework;

import java.awt.Image;
import java.util.ArrayList;

public class Animate {

	ArrayList<AnimationFrames> frameList;
	Image currentFrame;
	int currentIndex = 0, currentDuration = 1, elapsedFrames = 0;

	public Animate(ArrayList<Image> animationImages, int[] durations) {
		frameList = new ArrayList<AnimationFrames>();
		for (int i = 0; i < animationImages.size(); i++) {
			frameList.add(new AnimationFrames(i, animationImages.get(i),
					durations[i]));
			// totalDuration = totalDuration + durations[i];
		}
	}

	public Image calculateFrame() {
		if (elapsedFrames == 60)
			elapsedFrames = 0;
		elapsedFrames += 1;

		for (int i = 0; i < frameList.size(); i++) {
			if (currentIndex == i) {
				if (currentDuration <= frameList.get(i).getImageDuration()) {
					currentDuration++;
				}
				else if(currentDuration > frameList.get(i).getImageDuration()){
					currentDuration = 1;
					currentIndex++;
					if (currentIndex == frameList.size())
						currentIndex = 0;
				}
				return frameList.get(i).getMyImage();
			}
		}
		return null;
	}

}

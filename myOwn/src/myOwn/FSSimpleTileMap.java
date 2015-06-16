package myOwn;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class FSSimpleTileMap {

	BufferedReader fileReader;
	ArrayList<String> ReadedMap;
	int x=0,y,tileHeight, tileWidth, appHeight, appWidth, speedX;
	int[][] tilePosition;

	public FSSimpleTileMap(int myAppWidth, int myAppHeight, int myTileWidth,
			int myTileHeight) {
		appWidth = myAppWidth;
		appHeight = myAppHeight;
		tileWidth = myTileWidth;
		tileHeight = myTileHeight;
	}

	public void mapLoader(String mapPath) {
		ReadedMap = new ArrayList<String>();
		mapPath = this.getClass().getResource(mapPath).getPath();
		mapPath = mapPath.substring(1);
		mapPath = mapPath.replace('/', '\\');
		mapPath = mapPath.replace("%20", " ");
		int maxWidth=0;
		try {
			fileReader = new BufferedReader(new FileReader(mapPath));
			while (true) {
				String line = fileReader.readLine();
				if (line == null) {
					fileReader.close();
					break;
				} else if (!line.startsWith("!")) {
					ReadedMap.add(line);
					maxWidth = Math.max(maxWidth, line.length());
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println(e);
		} catch (IOException e) {
			System.out.println(e);
		}
		tilePosition = new int[ReadedMap.size()][maxWidth];
		for (int row = 0; row < ReadedMap.size(); row++) {
			char[] tempChar = ReadedMap.get(row).toCharArray();
			for (int column = 0; column < tempChar.length; column++) {
				tilePosition[row][column] = column * tileWidth; 
			}
		}
	}

	public void calculatePosition(int playerSpeedX) {
		if (playerSpeedX > 0)
			speedX = -4;
		else if (playerSpeedX == 0)
			speedX = playerSpeedX;
		else
			speedX = 4;
	}

	public void mapDesigner(ArrayList<BufferedImage> tileImages, Graphics g,
			ImageObserver myImageObsrvr) {
		for (int row = 0; row < ReadedMap.size(); row++) {
			char[] tempChar = ReadedMap.get(row).toCharArray();
			for (int column = 0; column < tempChar.length; column++) {
				tilePosition[row][column] += speedX;
				y = row * tileHeight;
				switch (tempChar[column]) {
				case '5':
					g.drawImage(tileImages.get(0), tilePosition[row][column], y, myImageObsrvr);
					break;
				case '8':
					g.drawImage(tileImages.get(1), tilePosition[row][column], y, myImageObsrvr);
					break;
				case '2':
					g.drawImage(tileImages.get(2), tilePosition[row][column], y, myImageObsrvr);
					break;
				case '4':
					g.drawImage(tileImages.get(3), tilePosition[row][column], y, myImageObsrvr);
					break;
				case '6':
					g.drawImage(tileImages.get(4), tilePosition[row][column], y, myImageObsrvr);
					break;
				case '0':
					g.drawImage(tileImages.get(5), tilePosition[row][column], y, myImageObsrvr);
					break;
				}
			}
		}
	}
}
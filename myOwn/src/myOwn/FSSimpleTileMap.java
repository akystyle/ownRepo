package myOwn;

import java.awt.Rectangle;
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
	Rectangle[][] tileBounder;

	public FSSimpleTileMap(int myAppWidth, int myAppHeight, int myTileWidth,
			int myTileHeight) {
		appWidth = myAppWidth;
		appHeight = myAppHeight;
		tileWidth = myTileWidth;
		tileHeight = myTileHeight;
		tileBounder = null;
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
		tileBounder = new Rectangle[ReadedMap.size()][maxWidth];
		for (int row = 0; row < ReadedMap.size(); row++) {
			char[] tempChar = ReadedMap.get(row).toCharArray();
			for (int column = 0; column < tempChar.length; column++) {
				tilePosition[row][column] = column * tileWidth;
				if(tempChar[column] != ' ')
				tileBounder[row][column].setRect(column*tileWidth, y, tileWidth, tileHeight);
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

		for (int row = 0; row < ReadedMap.size(); row++) {
			char[] tempChar = ReadedMap.get(row).toCharArray();
			for (int column = 0; column < tempChar.length; column++) {
				 tilePosition[row][column] += speedX;
				 y = row * tileHeight;
			}
		}
	}
	public BufferedReader getFileReader() {
		return fileReader;
	}

	public void setFileReader(BufferedReader fileReader) {
		this.fileReader = fileReader;
	}

	public ArrayList<String> getReadedMap() {
		return ReadedMap;
	}

	public void setReadedMap(ArrayList<String> readedMap) {
		ReadedMap = readedMap;
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

	public int getTileHeight() {
		return tileHeight;
	}

	public void setTileHeight(int tileHeight) {
		this.tileHeight = tileHeight;
	}

	public int getTileWidth() {
		return tileWidth;
	}

	public void setTileWidth(int tileWidth) {
		this.tileWidth = tileWidth;
	}

	public int getAppHeight() {
		return appHeight;
	}

	public void setAppHeight(int appHeight) {
		this.appHeight = appHeight;
	}

	public int getAppWidth() {
		return appWidth;
	}

	public void setAppWidth(int appWidth) {
		this.appWidth = appWidth;
	}

	public int getSpeedX() {
		return speedX;
	}

	public void setSpeedX(int speedX) {
		this.speedX = speedX;
	}

	public int[][] getTilePosition() {
		return tilePosition;
	}
	public void setTilePosition(int[][] tilePosition) {
		this.tilePosition = tilePosition;
	}
	public Rectangle[][] getTileBounder() {
		return tileBounder;
	}
	public void setTileBounder(Rectangle[][] tileBounder) {
		this.tileBounder = tileBounder;
	}
}
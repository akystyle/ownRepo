package myOwn;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class TileMap {

	int x,y,appWidth, appHeight, tileWidth, tileHeight, speedX, speedY,
			spreadPosition,speedVariable;
	String rowOrColumnSpread;
	BufferedReader fileReader;
	ArrayList<String> ReadedMap;

	// TODO currently working with only 2D array, need to automate the
	// dimension detection
	int spreaded[][];

	public TileMap(int initX, int initY,int myAppWidth, int myAppHeight, int myTileWidth,
			int myTileHeight, String myRowOrColumnSpread, int mySpreadPosition,int mySpeedVariable) {
		x = initX;
		y = initY;
		appWidth = myAppWidth;
		appHeight = myAppHeight;
		tileWidth = myTileWidth;
		tileHeight = myTileHeight;
		rowOrColumnSpread = myRowOrColumnSpread;
		spreadPosition = mySpreadPosition;
		speedVariable = mySpeedVariable;

		if (rowOrColumnSpread == "row")
			spreaded = new int[(appWidth / tileWidth) + 2][2];
		else if (rowOrColumnSpread == "column")
			spreaded = new int[(appHeight / tileHeight) + 2][2];

	}
	public int[][] calculatePosition(int[][] spreadedPositions, int playerSpeedX) {
		if (playerSpeedX > 0)
			speedX = (-1*speedVariable); //- playerSpeedX;

		else if (playerSpeedX < 0)
			speedX = speedVariable;  // - playerSpeedX;

		else
			speedX = playerSpeedX;

		if (rowOrColumnSpread == "row") {
			for (int column = 0; column < (appWidth / tileWidth) + 2; column++)
				spreaded[column][0] += speedX;
		} else if (rowOrColumnSpread == "column") {
			for (int row = 0; row < (appHeight / tileHeight) + 2; row++)
				spreaded[row][1] += speedX;
		}
		
		if (findMinRowValue(spreaded, 0) < -800)
			addValue(spreaded, 0,1600);
		if (findMaxRowValue(spreaded, 0) > 1600)
			subtractValue(spreaded,0, 1600);
		
		return spreaded;
	}
	public int[][] tileSpreader() {
		if (rowOrColumnSpread == "row") {
			for (int column = 0; column < (appWidth / tileWidth) + 2; column++) {
				spreaded[column][0] = ((column - 1) * tileWidth) + x;
				spreaded[column][1] = spreadPosition * tileHeight;
			}
		} else if (rowOrColumnSpread == "column") {
			for (int row = 0; row < (appHeight / tileHeight) + 2; row++) {
				spreaded[row][0] = spreadPosition * tileWidth;
				spreaded[row][1] = ((row - 1) * tileHeight) + y;
			}
		}
		return spreaded;
	}
	public void mapLoader(String mapPath){
		ReadedMap = new ArrayList<String>();
		while(true){
		try{
			File mapFile = new File(mapPath);
			FileReader myMapFileReader = new FileReader(mapFile);
			System.out.println(mapFile.getAbsolutePath());
			System.out.println(mapFile.getCanonicalPath());
			//fileReader = new BufferedReader();
			String line = fileReader.readLine();
			if(line == null){
				fileReader.close();
				break;
			}else if(!line.startsWith("!")){
				ReadedMap.add(line);
				System.out.println(line);
			}
		}catch (FileNotFoundException e){
			System.out.println(e.getMessage());
		}catch(IOException e){
			System.out.println(e.getMessage());
		}
		}
		
	}
	private int findMinRowValue(int[][] temp, int row) {
		int minValue = 2000;
		for (int i = 0; i < temp.length; i++) {
			if (temp[i][row] < minValue)
				minValue = temp[i][row];
		}

		return minValue;
	}
	private int findMaxRowValue(int[][] temp, int row) {
		int maxValue = -2000;
		for (int i = 0; i < temp.length; i++) {
			if (temp[i][row] > maxValue)
				maxValue = temp[i][row];
		}

		return maxValue;
	}
	private int[][] addValue(int[][] temp,int row,int value){
		for(int i=0;i<temp.length;i++){
			temp[i][row] += value;
		}
		return temp;
	}
	private int[][] subtractValue(int[][] temp,int row,int value){
		for(int i=0;i<temp.length;i++){
			temp[i][row] -= value;
		}
		return temp;
	}
}
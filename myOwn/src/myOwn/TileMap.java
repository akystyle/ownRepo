package myOwn;

public class TileMap {

	int appWidth, appHeight, tileWidth, tileHeight, speedX, speedY, spreadPosition;
	String rowOrColumnSpread;
	
	//TO-DO currently working with only 2D array, need to automate the dimension detection
	int mappingArray[][],spreaded[][];

	public TileMap(int myAppWidth, int myAppHeight, int myTileWidth,
			int myTileHeight,String myRowOrColumnSpread,int mySpreadPosition) {
		appWidth = myAppWidth;
		appHeight = myAppHeight;
		tileWidth = myTileWidth;
		tileHeight = myTileHeight;
		rowOrColumnSpread = myRowOrColumnSpread;
		spreadPosition = mySpreadPosition;
		mappingArray = new int[appHeight/tileHeight][appWidth/tileWidth];

		if(rowOrColumnSpread == "row")
			spreaded = new int[appWidth/tileWidth][2];
		else if (rowOrColumnSpread == "column")
			spreaded = new int[appHeight/tileHeight][2];
		
	}
	
	public int[][] tileSpreader(){
		if(rowOrColumnSpread == "row"){
			for(int column=0;column<(appWidth/tileWidth);column++){
				spreaded[column][0] = column*tileWidth;
				spreaded[column][1] = spreadPosition*tileHeight;
			}
		}
		else if (rowOrColumnSpread == "column"){
			for(int row=0;row<(appHeight/tileHeight);row++){
				spreaded[row][0] = row*tileHeight;
				spreaded[row][1] = spreadPosition*tileWidth;
			}
		}
		return spreaded;
	}

}

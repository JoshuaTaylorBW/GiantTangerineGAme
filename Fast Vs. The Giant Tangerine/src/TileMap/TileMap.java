package TileMap;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;

import javax.imageio.ImageIO;

import Main.GamePanel;

public class TileMap {
	
	private int PIECES_AMOUNT = 7;
	
	// position
	private double x;
	private double y;
	
	// bounds
	private int xmin;
	private int ymin;
	private int xmax;
	private int ymax;
	
	private double tween;
	
	// map
	private int[][] map;
	public String everything;
	private int tileSize;
	private String[] mapLines = new String[18];
	private int numRows;
	private int numCols;
	private int width;
	private int height;
	
	// tileset
	private BufferedImage tileset;
	private int numTilesAcross;
	private Tile[][] tiles;
	
	// drawing
	private int rowOffset;
	private int colOffset;
	private int numRowsToDraw;
	private int numColsToDraw;
	
	public TileMap(int tileSize) {
		this.tileSize = tileSize;
		numRowsToDraw = GamePanel.HEIGHT / tileSize + 2;
		numColsToDraw = GamePanel.WIDTH / tileSize + 2;
		tween = 0.07;
	}
	
	public void loadTiles(String s) {
		
		try {

			tileset = ImageIO.read(
				getClass().getResourceAsStream(s)
			);
			numTilesAcross = tileset.getWidth() / tileSize;
			tiles = new Tile[2][numTilesAcross];
			
			BufferedImage subimage;
			for(int col = 0; col < numTilesAcross; col++) {
				subimage = tileset.getSubimage(
							col * tileSize,
							0,
							tileSize,
							tileSize
						);
				tiles[0][col] = new Tile(subimage, Tile.NORMAL);
				subimage = tileset.getSubimage(
							col * tileSize,
							tileSize,
							tileSize,
							tileSize
						);
				tiles[1][col] = new Tile(subimage, Tile.BLOCKED);
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void loadMap(int parts){

		int whichMap;
		String tempLine = "";
		try{
			File newMap = new File("some.txt");
			BufferedWriter bw = new BufferedWriter(new FileWriter(newMap));
			BufferedReader br;
			for(int i = 1; i < parts + 1; i++){
				Random r = new Random();
				whichMap = r.nextInt(PIECES_AMOUNT) + 1;	
				InputStream in = getClass().getResourceAsStream("/Pieces/map" + whichMap + ".txt");
				br = new BufferedReader(new InputStreamReader(in));
				numCols = numCols + Integer.parseInt(br.readLine());
				numRows = Integer.parseInt(br.readLine());
				if(i == parts - 1){
					bw.write(numCols + "\r\n");
					bw.write(numRows + "\r\n");
				}
				for(int j = 0; j < numRows; j++){
					tempLine = br.readLine();
					if(i == 1){
						//mapLines[j] = tempLine;
					}else{
						mapLines[j] += tempLine;
					}
				if(i == parts){
				
				bw.write(mapLines[j] + "\r\n");
				}
				//System.out.println(mapLines[j]);
				//writeMap();
				}
			 
				
			}
			bw.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void makeBeginning(){
		numCols = 0;
		numRows = 0;
		String tempLine = "";
		try{
			File newMap = new File("some.txt");
			BufferedWriter bw = new BufferedWriter(new FileWriter(newMap));
			BufferedReader br;
			
			InputStream in = getClass().getResourceAsStream("/Pieces/beginning.txt");
			br = new BufferedReader(new InputStreamReader(in));
			numCols = Integer.parseInt(br.readLine());
			numRows = Integer.parseInt(br.readLine());
			
			for(int i = 0; i < numRows; i++){
				tempLine = br.readLine();
				mapLines[i] = tempLine;
				
			}
			for(int i = 0; i < numRows; i++){
				bw.write(mapLines[i]);
			}
			bw.close();
		}catch(Exception e){
			e.printStackTrace();
	}			
}

	public void makeEnd(){
		
		String tempLine = "";
		try{
			File newMap = new File("some.txt");
			BufferedWriter bw = new BufferedWriter(new FileWriter(newMap));
			BufferedReader br;
			
			InputStream in = getClass().getResourceAsStream("/Pieces/end.txt");
			br = new BufferedReader(new InputStreamReader(in));
			numCols = numCols + Integer.parseInt(br.readLine()) - 35;
			numRows = Integer.parseInt(br.readLine());
			
			for(int i = 0; i < numRows; i++){
				tempLine = br.readLine();
				mapLines[i] += tempLine;
				
			}
			for(int i = 0; i < numRows; i++){
				if(i == 0){
				bw.write(numCols + "\r\n");
				bw.write(numRows + "\r\n");
				}
				bw.write(mapLines[i] + "\r\n");

			}
			bw.close();
		}catch(Exception e){
			e.printStackTrace();
	}			
}

	
public void makeMap(String s) {
	
	
	
		try {
			
			BufferedReader br = new BufferedReader(
					new FileReader("some.txt")
				);
			
		StringBuilder sb = new StringBuilder();
			//
			
			
			numCols = Integer.parseInt(br.readLine());
			sb.append(numCols);
			sb.append(System.lineSeparator());
			
			numRows = Integer.parseInt(br.readLine());
			sb.append(numRows);
			sb.append(System.lineSeparator());
			
			map = new int[numRows][numCols];
			width = numCols * tileSize;
			height = numRows * tileSize;
			
			xmin = GamePanel.WIDTH - width;
			xmax = 0;
			ymin = GamePanel.HEIGHT - height + 100;
			ymax = 0;
			
			String delims = "\\s+";
			for(int row = 0; row < numRows; row++) {
				String line = br.readLine();
				sb.append(line);
				sb.append(System.lineSeparator());
				//line = br.readLine();
				String[] tokens = line.split(delims);
				for(int col = 0; col < numCols; col++) {
					map[row][col] = Integer.parseInt(tokens[col]);
				}
			}
			everything = sb.toString();
			System.out.println(everything);
			br.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public int getTileSize() { return tileSize; }
	public double getx() { return x; }
	public double gety() { return y; }
	public int getWidth() { return width; }
	public int getHeight() { return height; }
	
	public int getType(int row, int col) {
		int rc = map[row][col];
		int r = rc / numTilesAcross;
		int c = rc % numTilesAcross;
		return tiles[r][c].getType();
	}
	
	public void setTween(double d) { tween = d; }
	
	public void setPosition(double x, double y) {
		
		//System.out.println(x);
		//System.out.println(this.x);
		//System.out.println((x - this.x) * tween);
		
		this.x += (x - this.x) * tween;
		this.y += (y - this.y) * tween;
		
		fixBounds();
		
		colOffset = (int)-this.x / tileSize;
		rowOffset = (int)-this.y / tileSize;
		
	}
	
	private void fixBounds() {
		if(x < xmin) x = xmin;
		if(y < ymin) y = ymin;
		if(x > xmax) x = xmax;
		if(y > ymax) y = ymax;
	}
	
	public void draw(Graphics2D g) {
		
		for(
			int row = rowOffset;
			row < rowOffset + numRowsToDraw;
			row++) {
			
			if(row >= numRows) break;
			
			for(
				int col = colOffset;
				col < colOffset + numColsToDraw;
				col++) {
				
				if(col >= numCols) break;
				
				if(map[row][col] == 0) continue;
				
				int rc = map[row][col];
				int r = rc / numTilesAcross;
				int c = rc % numTilesAcross;
				
				g.drawImage(
					tiles[r][c].getImage(),
					(int)x + col * tileSize,
					(int)y + row * tileSize,
					null
				);
				
			}
			
		}
		
	}

	public int getNumRows() {
		// TODO Auto-generated method stub
		return numRows;
	}
	public int getNumCols(){
		return numCols;
	}
	
	
}
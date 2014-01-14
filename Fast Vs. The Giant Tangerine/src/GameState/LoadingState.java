package GameState;

import java.awt.Graphics2D;
import java.io.File;

import TileMap.TileMap;

public class LoadingState extends GameState {

	private TileMap tileMap;
	private GameStateManager gsm;
	
	public LoadingState(GameStateManager gsm){
		this.gsm = gsm;
		init();
		
	}

	public void init(){
		
		System.out.println(new File(".").getAbsolutePath());
		
		tileMap = new TileMap(32);
		tileMap.makeBeginning();
		tileMap.loadMap(5);
		tileMap.makeEnd();
		//tileMap.loadTiles("/Tiles/OnlyOne.png");
		//tileMap.makeMap("/Pieces/some.txt");
		///tileMap.setPosition(0, 0);
	
		gsm.setState(GameStateManager.LEVEL1STATE);
		
	}

	
	public void update() {
		//tileMap.setPosition(0, 0);
	}

	
	public void draw(Graphics2D g) {
		//tileMap.draw(g);
	}

	
	public void handleInput() {
		
		
	}
}



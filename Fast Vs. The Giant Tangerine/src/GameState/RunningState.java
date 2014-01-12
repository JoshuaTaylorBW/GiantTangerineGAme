package GameState;

import java.awt.Graphics2D;

import TileMap.TileMap;

public class RunningState extends GameState {

	private TileMap tileMap;

	public RunningState(GameStateManager gsm){
		init();
	}

	public void init(){
		
		tileMap = new TileMap(32);
		
		tileMap.makeMap("some.txt");
		tileMap.loadTiles("/Tiles/OnlyOne.png");
		tileMap.setPosition(0, 0);
		
	}

	@Override
	public void update() {
		tileMap.setPosition(0,0);
		
	}

	@Override
	public void draw(Graphics2D g) {
		tileMap.draw(g);
	}

	@Override
	public void handleInput() {
		
		
	}
}



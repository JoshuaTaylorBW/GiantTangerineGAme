package GameState;

import java.awt.Graphics2D;

import TileMap.TileMap;

public class LoadingState extends GameState {

	private TileMap tileMap;
	private GameStateManager gsm;
	
	public LoadingState(GameStateManager gsm){
		this.gsm = gsm;
		init();
		
	}

	public void init(){
		
		tileMap = new TileMap(32);
		tileMap.makeBeginning();
		tileMap.loadMap(3);
		tileMap.makeEnd();
	
		gsm.setState(GameStateManager.LEVEL1STATE);
		
	}

	@Override
	public void update() {
		
	}

	@Override
	public void draw(Graphics2D g) {
	
	}

	@Override
	public void handleInput() {
		
		
	}
}



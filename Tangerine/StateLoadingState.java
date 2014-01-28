import java.awt.Graphics2D;
import java.io.File;

public class StateLoadingState extends GameState {

	private TileMap tileMap;
	private GameStateManager gsm;
	
	private int MAP_LENGTH = 6;

	public StateLoadingState(GameStateManager gsm){
		this.gsm = gsm;
		init();
		
	}

	public void init(){
		
		System.out.println(new File(".").getAbsolutePath());
		
		tileMap = new TileMap(32);
		tileMap.makeBeginning();
		tileMap.loadMap(lengthOfMap());
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
	public int lengthOfMap(){
		int length = 0;
		if(gsm.getCurrentLevel() == 1){
			length = 8;
		}else if(gsm.getCurrentLevel() >= 2){
			length = 32;
		}
		return length;
	}
}



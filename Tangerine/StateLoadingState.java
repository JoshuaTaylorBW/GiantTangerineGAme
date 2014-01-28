import java.awt.Graphics2D;
import java.io.File;

public class StateLoadingState extends GameState {

	private TileMap tileMap;
	private GameStateManager gsm;
	
	public StateLoadingState(GameStateManager gsm){
		this.gsm = gsm;
		init();
		
	}

	public void init(){
		
		System.out.println(new File(".").getAbsolutePath());
		
		tileMap = new TileMap(32);
		tileMap.makeBeginning();
		tileMap.loadMap(10);
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
		return 0;
	}
}



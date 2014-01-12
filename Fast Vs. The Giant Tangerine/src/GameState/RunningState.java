package GameState;

import java.awt.Color;
import java.awt.Graphics2D;

import TileMap.Background;
import TileMap.TileMap;

public class RunningState extends GameState {

	private TileMap tileMap;
	private Background bg;
	private int mapX = 0, mapY = 0;

	public RunningState(GameStateManager gsm){
		init();
	}

	public void init(){
		
		System.out.println("you're here");
		
		bg = new Background("/Backgrounds/Forest.png", 0.0);
		
		tileMap = new TileMap(32);
		tileMap.loadTiles("/Tiles/OnlyOne.png");
		tileMap.makeMap("/some.txt");
	
		tileMap.setPosition(mapX, 0);
		tileMap.setTween(1);
		
	}

	
	public void update() {
		handleInput();
		bg.update();
		mapX -= 5;
		tileMap.setPosition(
				mapX,
				0);
		System.out.println(mapX);
		System.out.println(tileMap.getx());
	}

	
	public void draw(Graphics2D g) {
		bg.draw(g);
		tileMap.draw(g);
		g.setColor(Color.yellow);
		g.drawString("this is my argument", 100, 100);
	}


	public void handleInput() {
		if(Keys.isPressed(Keys.RIGHT)){
			mapX += 5;
		}
		
	}
}



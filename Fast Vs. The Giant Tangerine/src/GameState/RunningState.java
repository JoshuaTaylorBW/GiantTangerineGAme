package GameState;

import java.awt.Color;
import java.awt.Graphics2D;

import Entity.Fast;
import TileMap.Background;
import TileMap.TileMap;

public class RunningState extends GameState {

	private TileMap tileMap;
	private Background bg;
	private int mapX = 0, mapY = 0;
	private Fast fast;
	private int MAP_SPEED = 10;
	private int started = 0;
	private int switched = 0;

	public RunningState(GameStateManager gsm){
		init();
	}

	public void init(){
		
		
		System.out.println("you're here");
		
		bg = new Background("/Backgrounds/Forest.png", -.8);
		
		tileMap = new TileMap(32);
		tileMap.loadTiles("/Tiles/TiledOnlyOne.png");
		tileMap.makeMap("/some.txt");
		
		tileMap.setPosition(mapX, 0);
		tileMap.setTween(1);
		
		fast = new Fast(tileMap, MAP_SPEED);
		fast.setPosition(300, 464);
		
	}

	
	public void update() {
		handleInput();
		bg.update();	
		fast.update();
		
		tileMap.setPosition( 
				mapX,
				0);
		
		if(started >= 1){
			mapX -= MAP_SPEED;
			fast.setRight(true);
		}
	//	System.out.println(fast.getdy());
		System.out.println(fast.getCurrentAction());
		System.out.println(fast.getFlew());
	}

	
	public void draw(Graphics2D g) {
		bg.draw(g);
		tileMap.draw(g);
		fast.draw(g);
		g.setColor(Color.yellow);
		g.drawString("this is my argument", 100, 100);
		
	}

	public void handleInput() {
		if(Keys.isPressed(Keys.ENTER)){
			started++;
		}
		if(Keys.isPressed(Keys.RIGHT)){
			fast.startFlying();
		}
		if(Keys.isPressed(Keys.UP)){
			fast.startJumping();
		}
		if(Keys.isPressed(Keys.DOWN)){
			fast.startDowning();
		}
	
		if(Keys.isPressed(Keys.LEFT)){
			fast.startSliding();
		}
		//fast.setSliding(Keys.isPressed(Keys.LEFT));	
	
	//} if(!Keys.isPressed(Keys.DOWN) && !Keys.isPressed(Keys.UP)){ switched = 0; }
	}
}




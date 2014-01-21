package GameState;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

import Entity.Banana;
import Entity.Enemy;
import Entity.Fast;
import TileMap.Background;
import TileMap.TileMap;

public class RunningState extends GameState {

	private TileMap tileMap;
	private Background bg;
	private int mapX = 0, mapY = 0;
	private Fast fast;
	public int score = 0;
	private int MAP_SPEED = 8;
	private int started = 0;
	private int switched = 0;

	private ArrayList<Enemy> enemies;
	private ArrayList<Banana> bananas;

	public RunningState(GameStateManager gsm){
		init();
	}

	public void init(){
		
		
		System.out.println("you're here");
		
		bg = new Background("/Backgrounds/Forest.png", .8);
		
		tileMap = new TileMap(32);
		tileMap.loadTiles("/Tiles/TiledOnlyOne.png");
		tileMap.makeMap("/some.txt");
		
		tileMap.setPosition(mapX, 0);
		tileMap.setTween(1);
		
		fast = new Fast(tileMap, MAP_SPEED);
		fast.setPosition(300, 464);

		populateEnemies(100);
		
	}

	public void populateEnemies(int distance){
		enemies = new ArrayList<Enemy>();
		bananas = new ArrayList<Banana>();

		Banana b;

		Point[] bananaPoints = new Point[]{
			new Point(1500, 0)
		};

		for(int i = 0; i < bananaPoints.length; i++){
			b = new Banana(tileMap);
			b.setPosition(bananaPoints[i].x, bananaPoints[i].y);
			enemies.add(b);	
			bananas.add(b);
		}
	}
	
	public void update() {
		handleInput();
		bg.update();	
		fast.update();
		fast.checkBananas(bananas);
		
		System.out.println(fast.isRunning());

		for(int i = 0; i < enemies.size(); i++){
			Enemy e = enemies.get(i);
			e.update();
			if(e.isDead()){
				enemies.remove(i);
				i--;
			}
		}

		tileMap.setPosition( 
				mapX,
				0);
		
		if(started >= 1){
			mapX -= MAP_SPEED;
			fast.setRight(true);
			score += 1;
		}
	//	System.out.println(fast.getdy());
		System.out.println(fast.getCurrentAction());
		System.out.println(fast.getFlew());
	}

	
	public void draw(Graphics2D g) {
		bg.draw(g);
		tileMap.draw(g);
		fast.draw(g);
		for(int i = 0; i < enemies.size(); i++){
			enemies.get(i).draw(g);
		}
		
		g.setColor(Color.yellow);
		g.drawString("Score: " + score, 100, 100);
		
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
		if(fast.isRunning()){
			fast.startSliding();
		}else{
			fast.startDowning();
			}
		}
	}	
	
	//} if(!Keys.isPressed(Keys.DOWN) && !Keys.isPressed(Keys.UP)){ switched = 0; }
	
	public int getScore(){
		return score;
	}
	public void addScore(int s){
		score += s;
	}
	public void setScore(int s){
		score = s;
	}
	
}




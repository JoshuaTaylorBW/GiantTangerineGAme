package GameState;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import Entity.Banana;
import Entity.Cherry;
import Entity.Enemy;
import Entity.Fast;
import Entity.Tangerine;
import TileMap.Background;
import TileMap.TileMap;

public class RunningState extends GameState {

	//Enemy Spawning Stuff
	private int SPACING_MINIMUM = 500;
	private int SPACING_MAXIMUM = 800	;
	private int lastPosition = 0;
	private int amountOfEnemies;

	private TileMap tileMap;
	private Background bg;
	private int mapX = 0, mapY = 0;
	private Fast fast;
	public int score = 0;
	private int MAP_SPEED = 6;
	private int started = 0;
	private int switched = 0;

	private Tangerine tangerine;

	private ArrayList<Enemy> enemies;
	private ArrayList<Banana> bananas;
	private ArrayList<Cherry> cherries;

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

		

		populateEnemies();
	}

	public void populateEnemies(){
		enemies = new ArrayList<Enemy>();
		bananas = new ArrayList<Banana>();
		cherries = new ArrayList<Cherry>();
		
		tangerine = new Tangerine(tileMap, MAP_SPEED);
		tangerine.setPosition(59, 200);

		amountOfEnemies = (int)(tileMap.getWidth() / SPACING_MINIMUM); 

		Random r = new Random();
		for(int i = 0; i < amountOfEnemies; i++){
		int whichEnemy = r.nextInt(2);
			if(whichEnemy == 0){
				makeBanana();
			}else{
				makeCherry();
			}
		}	
	}
	
	public void makeBanana(){

		int x = makeXPos();
		lastPosition = x;
		Banana b;
		Point bananaPoint = new Point(x, makeBananaY());
		b = new Banana(tileMap);
		b.setPosition(bananaPoint.x, bananaPoint.y);
		enemies.add(b);
		bananas.add(b);

	}

	public void makeCherry(){

		int x = makeXPos();
		lastPosition = x;
		Cherry c;
		Point cherryPoint = new Point(x, makeCherryY()); 
		c = new Cherry(tileMap);
		c.setPosition(cherryPoint.x, cherryPoint.y);
		enemies.add(c);
		cherries.add(c);

	}

	public int makeXPos(){

	Random r = new Random();
	int possibilities = SPACING_MAXIMUM - SPACING_MINIMUM;
	int answer = lastPosition + SPACING_MINIMUM + r.nextInt(possibilities);
	
	return answer;	

	}

	public int makeBananaY(){

		int y = 0;
		int decider = 0;
		Random r = new Random();
		decider = r.nextInt(3);

		if(decider == 0){
			y = 0;
		}else if(decider == 1){
			y = 267;
		}else if(decider == 2){
			y = 469;
		}
		return y;
	}

	public int makeCherryY(){

		int y = 70;
		int decider = 0;
		Random r = new Random();
		decider = r.nextInt(3);

		if(decider == 0){
			y = 70;
		}else if(decider == 1){
			y = 262;
		}else if(decider == 2){
			y = 454;
		}
		return y;
	}

	public void update() {
		handleInput();
		bg.update();	
		fast.update();
		fast.checkBananas(bananas);
		
tangerine.update();
		
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
			tangerine.setRight(true);
			fast.setRight(true);
			score += 1;
		}
	}
	
	public void draw(Graphics2D g) {
		bg.draw(g);
		tileMap.draw(g);
		fast.draw(g);
		tangerine.draw(g);
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




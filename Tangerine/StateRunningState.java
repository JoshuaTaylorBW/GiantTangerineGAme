import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

public class StateRunningState extends GameState {

	//Enemy Spawning Stuff
	
	private int SPACING_MINIMUM = 1200;
	private int SPACING_MAXIMUM = 1600;
	private int lastPosition = 0;
	private int amountOfEnemies;

	private int MAP_SPEED = 6;
	private TileMap tileMap;
	private TileBackground bg;
	private int mapX = 0, mapY = 0;
	private EntityFast fast;
	
	public boolean lose = false;
	public boolean win = false;
	private int started = 0;
	private int switched = 0;

	private EntityTangerine tangerine;
	private ArrayList<EntityEnemy> enemies;
	private ArrayList<EntityBanana> bananas;
	private ArrayList<EntityCherry> cherries;
	private ArrayList<EntityCoin> coins;

	public StateRunningState(GameStateManager gsm){
		this.gsm = gsm;
		init();
	}

	public void init(){
		
		System.out.println("welcome to level " + gsm.getCurrentLevel());

		bg = new TileBackground("/Backgrounds/Forest.png", .8);
		
		setSpeed();
		setSpacing();
		tileMap = new TileMap(32);
		tileMap.loadTiles("/Tiles/TiledOnlyOne.png");
		tileMap.makeMap("/some.txt");
		tileMap.setPosition(mapX, 0);
		tileMap.setTween(1);
		
		fast = new EntityFast(tileMap, MAP_SPEED);
		fast.setPosition(300, 464);
		
		tangerine = new EntityTangerine(tileMap, MAP_SPEED);
		tangerine.setPosition(59, 250);

		populateEnemies();
		populateCoins();
	}

	public void populateEnemies(){
		enemies = new ArrayList<EntityEnemy>();
		bananas = new ArrayList<EntityBanana>();
		cherries = new ArrayList<EntityCherry>();

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
	
	public void populateCoins(){
		coins = new ArrayList<EntityCoin>();
		EntityCoin c;
		Point CoinPoint = new Point(800,520);
		c = new EntityCoin(tileMap, gsm);
		c.setPosition(CoinPoint.x, CoinPoint.y);
		coins.add(c);
	}

	public void makeBanana(){

		int x = makeXPos();
		lastPosition = x;
		EntityBanana b;
		Point bananaPoint = new Point(x, makeBananaY());
		b = new EntityBanana(tileMap);
		b.setPosition(bananaPoint.x, bananaPoint.y);
		enemies.add(b);
		bananas.add(b);

	}

	public void makeCherry(){

		int x = makeXPos();
		lastPosition = x;
		EntityCherry c;
		Point cherryPoint = new Point(x, makeCherryY()); 
		c = new EntityCherry(tileMap);
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
		
		if(fast.getx() > tileMap.getWidth() - 80){
			win = true;
			fast.setWon(true);
			tangerine.setGo(false);
				gsm.nextLevel();
				gsm.setState(GameStateManager.LOADINGSTATE);
			started--;
		}
		if(fast.getXScreen() < -23 || fast.fellDead()){//
			lose = true;
		}
		if(gsm.getCurrentLevel() > 1 && fast.isIdle()){
			started = 1;
		}
		if(lose){
			gsm.setCurrentLevel(1);
			gsm.setState(GameStateManager.LOADINGSTATE);
			gsm.setScore(0);
		}
		
		for(int i = 0; i < enemies.size(); i++){
			EntityEnemy e = enemies.get(i);
			e.update();
			if(e.isDead()){
				enemies.remove(i);
				i--;
			}
		}
		for(int i = 0; i < coins.size(); i++){
			EntityCoin c = coins.get(i);
			c.update();
		}

		tileMap.setPosition( 
				mapX,
				0);
		
		if(started >= 1){
			mapX -= MAP_SPEED;
			tangerine.setGo(true);
			fast.setRight(true);
			gsm.addScore(1);
		}
	}
	
	public void draw(Graphics2D g) {
		bg.draw(g);
		for(int i = 0; i < enemies.size(); i++){
			enemies.get(i).draw(g);
		}
		for(int i = 0; i < coins.size(); i++){
			coins.get(i).draw(g);	
		}
		tileMap.draw(g);
		tangerine.draw(g);
		fast.draw(g);
		
		
		g.setColor(Color.yellow);
		g.drawString("Score: " + gsm.getScore(), 100, 100);
		
	}

	public void handleInput() {
		if(Keys.isPressed(Keys.ENTER)){
			if(!win){
			started++;
			}else if(win){
				gsm.nextLevel();
				gsm.setState(GameStateManager.LOADINGSTATE);
			}
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
	

	public void setSpeed(){
		switch(gsm.getCurrentLevel()){
			case 1:
				MAP_SPEED = 6;
				break;
			case 2:
				MAP_SPEED = 6;
				break;
			case 3:
				MAP_SPEED = 5;
				break;
			case 4:
				MAP_SPEED = 8;
				break;
			default:
				MAP_SPEED = 10;
				break;
		}
	}
	public void setSpacing(){
		switch(gsm.getCurrentLevel()){
			case 1:
				SPACING_MINIMUM = 1200;
				SPACING_MAXIMUM = 1600;		
				break;
			case 2:
				SPACING_MINIMUM = 600;
				SPACING_MAXIMUM = 1000;		
				break;
			case 3:
				SPACING_MINIMUM = 300;
				SPACING_MAXIMUM = 800;		
				break;
			default:
				SPACING_MINIMUM = 1200;
				SPACING_MAXIMUM = 1600;		
				break;
		}
	}
}




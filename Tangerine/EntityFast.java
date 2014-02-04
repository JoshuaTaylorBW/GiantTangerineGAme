import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;
public class EntityFast extends EntityMapObject{

	private int health;
	private int maxHealth;
	private TileMap tiley;

	private boolean flinching; 
	private long flinchTimer;
	private boolean dead;
	
	private boolean running;
	
	private boolean flying;
	private long flyingTimer;
	private int alreadyFlew = 0;

	private boolean sliding;
	private long slidingTimer;
	private int alreadySlid = 0;

	private long jumpingTimer;
	private int jumped = 0;

	private int downing = 0;
	private boolean goDown;

	private int whereJump = 0;

	public boolean won = false;

	private ArrayList<BufferedImage[]> sprites;
	private final int[] numFrames = {
		1, 8, 3, 3, 1, 3 
	};
	
	public static final int IDLE = 0;
	public static final int RUNNING = 1;	
	public static final int FLYING = 2;	
	public static final int SLIDING = 3; 
	public static final int JUMPING = 4;
	public static final int WINNING = 5;
	public EntityFast(TileMap tm, double dx){

		super(tm); 
		this.tiley = tm;

		width = 35;
		cwidth = 32;
		height = 160;
		cheight = 128
				;

		moveSpeed = dx;
		maxSpeed = dx;
		stopSpeed = 0.0;
		fallSpeed = 0.1;
		maxFallSpeed = 4.0;
		jumpStart = -6.5;//-5.8
		stopJumpSpeed = 0.3;
		
		facingRight = true;

		health = maxHealth = 2;
	try{

		BufferedImage spritesheet = ImageIO.read(
				getClass().getResourceAsStream("/Sprites/Image2.png"));

		sprites = new ArrayList<BufferedImage[]>();
		for(int i = 0; i < 6; i++){
			BufferedImage[] bi = new BufferedImage[numFrames[i]];

			for(int j = 0; j < numFrames[i]; j++){
				if(i == 0){
					bi[j] = spritesheet.getSubimage(
							j * width,
							i * height,
							width,
							height);
				}else if(i == 1 || i == 4){
					bi[j] = spritesheet.getSubimage(j* 46, i * height, 46, height);
				}else if(i == 2){
					bi[j] = spritesheet.getSubimage(j * 115,i * height, 115, height);
				}else if(i == 3){
					bi[j] = spritesheet.getSubimage(j * 85,i * height, 85, height);
				}else if(i == 5){
					bi[j] = spritesheet.getSubimage(j * 61,i * height, 61, height);
				}
			}
			sprites.add(bi);
		}
	}catch(Exception e){
		e.printStackTrace();
	}
	animation = new Animation();
	currentAction = IDLE;
	animation.setFrames(sprites.get(IDLE));
	animation.setDelay(40);
	}

	public int getHealth() {return health;}
	public boolean isDead(){return dead;}
	public void kill(){dead = true;}
	public void revive(){dead = false;}
	public void setDx(double x){
		dx = x;
	}
	
	public void setCurrentAction(int action){
		currentAction = action;
	}
	public int getFlew(){return alreadyFlew;}
	public int getCurrentAction(){
		return currentAction;
	}
	public boolean isRunning(){
		return running;
	}
	public void startFlying(){
      		if(alreadyFlew == 0){
			alreadyFlew++;
			flying = true;
			flyingTimer = System.nanoTime();
		}
	} 
	public void startSliding(){
      		if(alreadySlid == 0){
			alreadySlid++;
			sliding = true;
			slidingTimer = System.nanoTime();
		}
	} 
	public void startJumping(){
      		if(jumped == 0){
			jumped++;
			jumping = true;
			jumpingTimer = System.nanoTime();
		}
	} 
	public void startDowning(){
		if(jumping || falling || flying){
      		if(downing == 0){
			downing++;
			goDown = true;
      		}
		}
	} 
	public void hit(){
		if(flinching) return;
		flinching = true;
		flinchTimer = System.nanoTime();

	}
	public void setSliding(boolean b){sliding = b;}
	public void checkFallDead(){
		if(y > tiley.getHeight() - 70){
			dead = true;
		} else {
			dead = false;
		}
	}
	
	public boolean fellDead(){
		return dead;
	}
	public void reset(){
		health = setMaxHealth(1);
	}
	
	private int setMaxHealth(int i) {
		 maxHealth = i;
		 return maxHealth;
	}
	public boolean isIdle(){
		if(currentAction == IDLE){
			return true;
		}else{
			return false;
		}
	}
	public void setWon(boolean w){
		won = w;	 	
		if(w){
			flinching = false;
			left = false;
			right = false;
			jumping = false;
			flying = false;
			sliding = false;
		}
	}

	public boolean getWon(){
		return won;
	}

	private void getNextPosition() {
		
		if(flinching){
			x -= 1.6;
		}
		
		if(left) {
			dx -= moveSpeed;
			if(dx < -maxSpeed) {
				dx = -maxSpeed;
			}
		}
		else if(right) {
			
			dx += moveSpeed;
			if(dx > maxSpeed) {
				dx = maxSpeed;
			
			}
		}
		else {
			if(dx > 0) {
				dx -= stopSpeed;
				if(dx < 0) {
					dx = 0;
				}
			}
			else if(dx < 0) {
				dx += stopSpeed;
				if(dx > 0) {
					dx = 0;
				}
			}
		}
		//thisl
		if(flying){
			dy = 0;
			dy -= fallSpeed; 
		}
		if(jumping && !falling) {
			
			dy = jumpStart;
			falling = true;	
		}
		if(!falling && !jumping){
			dy = 0;
		}
		
		if(goDown){
			calculateCorners(x, ydest);
			if(!bottomLeft && !bottomRight) {
				dy = 10;
				flying = false;
		       	       sliding = false;
			       jumping = false;
			}else{
			dy = 0;
			ytemp = (currRow) * tileSize - tileSize;
			}
		}	
if(falling) {
			if(!goDown){
			dy += fallSpeed;
			
			if(dy > 0) jumping = false;
			if(dy < 0 && !jumping) dy += stopJumpSpeed;
			
			if(dy > maxFallSpeed) dy = maxFallSpeed;
			}
}
if(!jumping && !falling){
	if(dy != 0){
		dy = 0;
	}
	
}
	}
	
	public void checkBananas(ArrayList<EntityBanana> ba){
		for(int i = 0; i < ba.size(); i++){

			EntityBanana b = ba.get(i);

			if(flying){
				if(
					b.getx() > x &&
					b.getx() < x + (cwidth / 2) &&
					b.gety() > y - height / 2 &&
					b.gety() < y + height / 2
 				){
					b.hit(1);
					}
				}
		if(intersects(b) && !flying){
			if(b.getHealth() > 0){
			hit();
				}
			}
		}
	}
	public void checkCherries(ArrayList<EntityCherry> ch){
		for(int i = 0; i < ch.size(); i++){

			EntityCherry c = ch.get(i);

			if(sliding){
				if(
					c.getx() > x &&
					c.getx() < x + (cwidth / 2) &&
					c.gety() > y - height / 2 &&
					c.gety() < y + height / 2
 				){
					c.hit(1);
					}
				}
			
		if(intersects(c)){
			hit();
			}
		}
	}

	

	public void update(){
		getNextPosition();
		checkTileMapCollision();
		checkFallDead();
		setPosition(xtemp, ytemp);

		if(flinching){
			long elapsed = 
				(System.nanoTime() - flinchTimer) / 1000000;
			if(elapsed > 500){
				flinching = false;
			}
		}

		if(flying){
			long elapsed =
				(System.nanoTime() - flyingTimer) / 1000000;
		       if(elapsed > 1000){
			       flying = false;
		       }
		}

		if(sliding){
			long elapsed =
				(System.nanoTime() - slidingTimer) / 1000000;
		       if(elapsed > 1000){
			       sliding = false;
		       }
		}

		if(jumping){
			long elapsed =
				(System.nanoTime() - jumpingTimer) / 1000000;
		       if(elapsed > 2000){
			       jumping = false;
		       }
		}

		if(dy > 0 || dy < 0){
			if(currentAction != JUMPING){
				currentAction = JUMPING;
				animation.setFrames(sprites.get(JUMPING));
				animation.setDelay(40);
				width = 46;
				cwidth = 46;
				cheight = 128;
			}
		}else if((right || left) && !flying && !sliding){
			if(currentAction != RUNNING){
				currentAction = RUNNING;
				if(!falling){
				alreadyFlew = 0;
				jumped = 0;
				downing = 0;
				goDown = false;
				alreadySlid = 0;
				}
				animation.setFrames(sprites.get(RUNNING));
				animation.setDelay(40);
				width = 46;
				cwidth = 46;
				cheight = 128;
			}
		}else if(flying){
			if(currentAction != FLYING){
				currentAction = FLYING;
				downing = 0;
				goDown = false;
			       	animation.setFrames(sprites.get(FLYING));
				animation.setDelay(40);
				width = 115;
				cwidth = 115;
				cheight = 100;
			}
		}else if(sliding){
			if(currentAction != SLIDING){
				currentAction = SLIDING;
				animation.setFrames(sprites.get(SLIDING));
				animation.setDelay(40);
				width = 85;
				cwidth = 85;
				cheight = 128;
			}
		}else if(won){
			if(currentAction != WINNING){
				currentAction = WINNING;
				animation.setFrames(sprites.get(WINNING));
				animation.setDelay(400);
				width = 61;
				cwidth = 61;
				cheight = 128;
			}
		}else{
			if(currentAction != IDLE){
				currentAction = IDLE;
				animation.setFrames(sprites.get(IDLE));
				animation.setDelay(400);
				width = 35;
				cwidth = 35;
				cheight = 128;
			}
		}
		
		if(currentAction == RUNNING){
			running = true;
		}else{
			running = false;
		}
		
		animation.update();
	}
	
	public void draw(Graphics2D g){
		setMapPosition();
//		if(flinching) {
//			long elapsed =
//				(System.nanoTime() - flinchTimer) / 1000000;
//			if(elapsed / 400 % 2 == 0) {
//				return;
//			}
//		}
		super.draw(g);
      	}
}

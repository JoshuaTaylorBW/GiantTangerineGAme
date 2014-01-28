import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class EntityTangerine extends EntityEnemy{

	private BufferedImage[] sprites;
	private boolean go = false;

	public EntityTangerine(TileMap tm, int speed){
		super(tm);

		moveSpeed = speed - 2;
		maxSpeed = speed - 2;
		fallSpeed = .1;
		maxFallSpeed = 4.0;

		width = 118;
		cwidth = 118;
		height = 658;	
		cheight = 658;	

		health = maxHealth = 1;
		
		try{
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Tangerine.png"));
			sprites = new BufferedImage[1];
			for(int i = 0; i < sprites.length; i++){
				sprites[i] = spritesheet.getSubimage(i * width, 0, width, height);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		animation = new Animation();
		animation.setFrames(sprites);
		animation.setDelay(100);
		
		facingRight = true;
	}

	public void setGo(boolean b){
		right = b;
	}
	public boolean getGo(){
		return go;
	}	

	public void getNextPosition(){
		if(right) {
			dx += moveSpeed;
			if(dx > maxSpeed) {
				dx = maxSpeed;
			}
		}else{
			dx = 0;
		}
	}

	public void update(){
		getNextPosition();
		checkTileMapCollision();
		setPosition(xtemp, ytemp);

		animation.update();

	}

	public void draw(Graphics2D g){
		setMapPosition();
		super.draw(g);
	}
}

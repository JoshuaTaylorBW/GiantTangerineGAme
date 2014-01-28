import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class EntityBanana extends EntityEnemy{

	private BufferedImage[] sprites;

	public EntityBanana(TileMap tm){
		super(tm);

		moveSpeed = 0;
		maxSpeed = 0;
		fallSpeed = .1;
		maxFallSpeed = 4.0;

		width = 90;
		cwidth = 90;
		height = 150;	
		cheight = 160;	

		health = maxHealth = 1;
		
		try{
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Banana.png"));
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

	public void getNextPosition(){

		calculateCorners(x, y + 1);
		if(bottomLeft || bottomRight){
			return;
		}else{
			y = y + 10;
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

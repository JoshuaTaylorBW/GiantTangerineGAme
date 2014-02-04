import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class EntityCoin extends EntityEnemy{

	public int coinScore = 100;
	public boolean picked = false;
	public boolean remove = false;

	private BufferedImage[] sprites;
	private GameStateManager gsm;

	public EntityCoin(TileMap tm, GameStateManager gsm){
		super(tm);
		this.gsm = gsm;

		moveSpeed = 0;
		maxSpeed = 0;
		fallSpeed = 0;
		maxFallSpeed = 0;

		width = 30;
		cwidth = 30;
		height = 30;	
		cheight = 30;	

		health = maxHealth = 1;
		
		try{
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/goldCoin.png"));
			sprites = new BufferedImage[9];
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
	public boolean isPicked(){return picked;}

	public int getScore(){return coinScore;}

	public void addScore(){
		gsm.addScore(1);
	}

	public void setMapPosition(int x, int y){
		xmap = x;
		ymap = y;
	}

	public void update(){
		animation.update();
	}

	public boolean shouldRemove(){return remove;}	
	public void done(){remove = true;}

	public void draw(Graphics2D g){

		setMapPosition();
		g.drawImage(
				animation.getImage(),
				(int)(x + xmap - width / 2),
				(int)(y + ymap - height / 2),
				null

			);
	}
}

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Images extends EntityMapObject{
	
	private ArrayList<BufferedImage[]> sprites;
	public Images(TileMap tm, String s){
		
		super(tm);

		
		width = 640;
		height = 480;
		
		cwidth = 0;
		cheight = 0;
		
		facingRight = true;
		
		try{
			
			BufferedImage spritesheet = ImageIO.read(
					getClass().getResourceAsStream(s));
			
			sprites = new ArrayList<BufferedImage[]>();
			for(int i = 0; i < 1; i++){
				BufferedImage[] bi =
						new BufferedImage[1];
				for(int j = 0; j < 1; j++){
					bi[j] = spritesheet.getSubimage(
							j * width,
							i * height,
							width,
							height
						);
				}
				sprites.add(bi);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		animation = new Animation();
		animation.setFrames(sprites.get(0));
		animation.setDelay(400);
	

	}
	
public Images(TileMap tm, String s, int w, int h){
		
		super(tm);

		
		width = w;
		height = h;
		
		cwidth = 0;
		cheight = 0;
		
		facingRight = true;
		
		try{
			
			BufferedImage spritesheet = ImageIO.read(
					getClass().getResourceAsStream(s));
			
			sprites = new ArrayList<BufferedImage[]>();
			for(int i = 0; i < 1; i++){
				BufferedImage[] bi =
						new BufferedImage[1];
				for(int j = 0; j < 1; j++){
					bi[j] = spritesheet.getSubimage(
							j * width,
							i * height,
							width,
							height
						);
				}
				sprites.add(bi);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		animation = new Animation();
		animation.setFrames(sprites.get(0));
		animation.setDelay(400);
	

	}

	public void update(){
		
		checkTileMapCollision();
		setPosition(xtemp, ytemp);
	
		
	}

	public void setWidth(int w){
		width = w;
	}
	
	public void setHeight(int h){
		height = h;
	}
	
	public void draw(Graphics2D g){
	setMapPosition();
	super.draw(g);	
	}
}

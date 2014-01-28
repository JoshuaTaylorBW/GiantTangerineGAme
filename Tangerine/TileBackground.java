import java.awt.*;
import java.awt.image.*;

import javax.imageio.ImageIO;

public class TileBackground {

	private BufferedImage image;
	
	private double x;
	private double y;
	private double dx;
	private double dy;
	
	private double moveScale;
	
	public TileBackground(String s, double ms){
		try{
			image = ImageIO.read(getClass().getResourceAsStream(s));
			moveScale = ms;
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void setPosition(double x, double y){
		this.x = (x * moveScale) % MainGamePanel.WIDTH;
		this.y = (y * moveScale) % MainGamePanel.HEIGHT;	
	}
	
	public void setVector(double dx, double dy){
		this.dx = dx;
		this.dy = dy;
	}
	public void update(){
		x += dx;
		y += dy;
	}
	public void draw(Graphics2D g){
		g.drawImage(image, (int)x, (int)y, null);
		
		if(x < 0){
			g.drawImage(image, (int)x + MainGamePanel.WIDTH, (int)y, null);
		}
		if(x > 0){
			g.drawImage(image, (int)x - MainGamePanel.WIDTH, (int)y, null);
		}
	}
}

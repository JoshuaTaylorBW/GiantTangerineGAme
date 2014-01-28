public class EntityEnemy extends EntityMapObject{

	protected int health;
	protected int maxHealth;
	protected int deathScore;
	protected boolean dead;
	protected int damage;
	
	protected boolean flinching;
	protected long flinchTimer;

	
	public EntityEnemy(TileMap tm){
		super(tm);
	}
	
	public boolean isDead(){return dead;}
	public int getDamage(){return damage;}
	public int getScore(){return deathScore;}
	public int getHealth(){return health;}

public void kill(){dead = true;}
	
	public void hit(int damage){
		if(dead || flinching) return;
		health -= damage;
		if(health < 0) health = 0;
		if(health == 0) dead = true;
		flinching = true;
		flinchTimer = System.nanoTime();
	}
	
	public void update(){
		
	}
	
}


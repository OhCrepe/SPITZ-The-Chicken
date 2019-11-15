package Main.Projectile;

import Main.Animation.SeedAnimation;

public class Seed extends Projectile{

	public Seed(int x, int y, String direction){
		
		super(x, y, direction);
		this.width = 6;
		this.height = 6;
		this.animation = new SeedAnimation();
		this.gravity = false;
		this.vel = 16;
		this.damage = 10;
				
	}
	
	public void update(){
		super.update();
	}
	
}

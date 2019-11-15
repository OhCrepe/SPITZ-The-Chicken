package Main.Projectile;

import Main.Animation.CannonBallAnimation;

public class CannonBall extends Projectile{

	protected int pigDelay;
	
	public CannonBall(int x, int y, String direction){
		
		super(x, y, direction);
		this.width = 17;
		this.height = 17;
		this.animation = new CannonBallAnimation();
		this.gravity = false;
		this.vel = 8;
		this.damage = 25;
		this.pigDelay = 5;
				
	}
	
	public void update(){
		super.update();
		if(this.pigDelay > 0){
			pigDelay -= 1;
		}
	}

	public int getPigDelay() {
		return pigDelay;
	}

	public void setPigDelay(int pigDelay) {
		this.pigDelay = pigDelay;
	}
	
}

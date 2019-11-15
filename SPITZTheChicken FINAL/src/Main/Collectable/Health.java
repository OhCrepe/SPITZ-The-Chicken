package Main.Collectable;

import Main.Animation.HealthAnimation;

public class Health extends Collectable{

	public Health(int x, int y){
		
		super(x, y);
		this.width = 32;
		this.height = 32;
		this.animation = new HealthAnimation();
		
	}
	
}

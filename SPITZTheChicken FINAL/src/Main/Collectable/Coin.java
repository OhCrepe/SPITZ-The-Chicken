package Main.Collectable;

import Main.Animation.CoinAnimation;

public class Coin extends Collectable{

	public Coin(int x, int y){
		
		super(x, y);
		this.width = 16;
		this.height = 16;
		this.animation = new CoinAnimation();
		
	}
	
}

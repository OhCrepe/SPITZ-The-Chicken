package Main.Collectable;

import java.io.IOException;

import Main.Animation.LetterPAnimation;

public class LetterP extends Collectable{

	public LetterP(int x, int y) throws IOException{
		
		super(x, y);
		this.width = 32;
		this.height = 32;
		this.animation = new LetterPAnimation();
		
	}
	
}

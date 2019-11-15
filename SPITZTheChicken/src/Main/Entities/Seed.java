package Main.Entities;

public class Seed {

	int x, y;
	String direction;
	boolean visible;
	
	public Seed(){
		
	}
	
	public Seed(int x, int y, String direction){
		
		this.x = x;
		this.y = y;
		this.direction = direction;
		this.visible = true;
		
	}
	
	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public void update(){
		
		if(this.direction == "LEFT"){
			this.x -= 16;
		} else if(this.direction == "RIGHT"){
			this.x += 16;
		}
		
	}
	
}

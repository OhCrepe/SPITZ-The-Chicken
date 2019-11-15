package Main.Entities.Enemies;

public class CannonPig extends Enemy{

	int x, y, width, height, frame, totalFrames, spriteDelay, attackDelay;
	String mode;
	boolean visible, activated;
	
	public int getSpriteDelay() {
		return spriteDelay;
	}
	public void setSpriteDelay(int spriteDelay) {
		this.spriteDelay = spriteDelay;
	}

	public String getMode() {
		return mode;
	}
	
	public int getFrame() {
		return frame;
	}
	public void setFrame(int frame) {
		this.frame = frame;
	}
	public boolean isActivated() {
		return activated;
	}
	public void setActivated(boolean activated) {
		this.activated = activated;
	}
	public CannonPig(int x, int y){
	
		this.x = x;
		this.y = y;
		this.width = 80;
		this.height = 48;
		this.frame = 0;
		this.totalFrames = 10;
		this.visible = false;
		this.mode = "idle";
		this.spriteDelay = 8;
		this.activated = false;
		this.attackDelay = 0;
		
	}
	
	public int getAttackDelay() {
		return attackDelay;
	}
	public void setAttackDelay(int attackDelay) {
		this.attackDelay = attackDelay;
	}
	public void setMode(String mode){
		
		this.mode = mode;
		this.frame = 0;
		if(mode.equals("idle")){
			this.totalFrames = 6;
			this.spriteDelay = 10;
		}
		
	}
	
}

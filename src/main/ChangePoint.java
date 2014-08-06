package main;


public class ChangePoint {
	
	private float x;
	private float y;
	private LeftRight side;
	
	public ChangePoint(float x, float y, LeftRight s){
		this.x = x;
		this.y = y;
		this.side = s;
	}	
	
	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public LeftRight getSide() {
		return side;
	}
	
	public boolean isLeft(){
		return this.side == LeftRight.L;
	}

	@Override
	public String toString() {
		return String.format("(%4.2f,%4.2f)", x,y);
	}

	public enum LeftRight{
		L,
		R		
	}
}

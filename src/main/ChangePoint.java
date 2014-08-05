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

	@Override
	public String toString() {
		return "ChangePoint [x=" + x + ", y=" + y + ", side=" + side + "]";
	}

	public enum LeftRight{
		L,
		R		
	}
}

package main;

public class Building {

	private float left;
	private float right;
	private float height;
	
	public Building(String b){
		b = b.substring(1, b.length()-1);
		String[] strings = b.split(",");		
		left = Float.parseFloat(strings[0]);
		right = Float.parseFloat(strings[1]);
		height = Float.parseFloat(strings[2]);		
	}

	public float getLeft() {
		return left;
	}

	public float getRight() {
		return right;
	}

	public float getHeight() {
		return height;
	}

	@Override
	public String toString() {
		return "Building [left=" + left + ", right=" + right + ", height="
				+ height + "]";
	}
	
}

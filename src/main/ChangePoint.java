package main;

import java.awt.geom.Point2D;

public class ChangePoint {
	
	private Point2D.Float point;
	private LeftRight side;
	
	public ChangePoint(Point2D.Float p, LeftRight s){
		this.point = p;
		this.side = s;
	}
	
	
	public enum LeftRight{
		L,
		R		
	}
}

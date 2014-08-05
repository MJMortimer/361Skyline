package main;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import main.ChangePoint.LeftRight;

public class Skyline {
	
	private List<Building> buildings;
	
	public Skyline(List<Building> buildings){
		this.buildings = buildings;
	}
	
	public void runAlgorithm(){
		divideAndConquer(buildings);
	}
	
	private List<ChangePoint> divideAndConquer(List<Building> inputBuildings){
		List<ChangePoint> currentSkyline = new ArrayList<ChangePoint>();
		//if input size is 1, (one building),
		//Do Direct Case (rectangle of single building)
		if(inputBuildings.size() == 1){
				Building b = inputBuildings.get(0);
				currentSkyline.add(new ChangePoint(b.getLeft(), b.getHeight(), LeftRight.L));
				currentSkyline.add(new ChangePoint(b.getRight(), 0.0f, LeftRight.R));
				System.out.println("BC :   "+currentSkyline);
				return currentSkyline;
		}
		//else if size > 1
		//Divide (Split input list in two)
		int mid = inputBuildings.size()/2;
		List<Building> firstHalfBuildings = new ArrayList<Building>(inputBuildings.subList(0, mid));
		List<Building> secondHalfBuildings = new ArrayList<Building>(inputBuildings.subList(mid, inputBuildings.size()));
		
		//For each half list run divideAndConquer
		List<ChangePoint> firstHalfSkyline = divideAndConquer(firstHalfBuildings);
		List<ChangePoint> secondHalfSkyline = divideAndConquer(secondHalfBuildings);
		
		System.out.println("FHS:   "+firstHalfSkyline);
		System.out.println("SHS:   "+secondHalfSkyline);
		
		
		//Combine (Calculate the skyline)		
		return currentSkyline;
	}
	


	
	public static void main(String[] args){
		List<Building> buildings = new ArrayList<Building>();
		for(String building : args){
			buildings.add(new Building(building));
		}
		
		Skyline sky = new Skyline(buildings);
		sky.runAlgorithm();
		
	}
}

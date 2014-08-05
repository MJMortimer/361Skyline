package main;

import java.util.ArrayList;
import java.util.List;

public class Skyline {
	
	private List<Building> buildings;
	
	public Skyline(List<Building> buildings){
		this.buildings = buildings;
	}

	
	public static void main(String[] args){
		List<Building> buildings = new ArrayList<Building>();
		for(String building : args){
			buildings.add(new Building(building));
		}
		
		Skyline sky = new Skyline(buildings);
		
	}
}

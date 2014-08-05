package main;

import java.util.ArrayList;
import java.util.List;

public class Skyline {
	
	private List<Building> buildings;
	
	public Skyline(List<Building> buildings){
		this.buildings = buildings;
		System.out.println(buildings);
	}
	
	public void divideAndConquer(){
		//if input size is 1, (one building),
			//Do Direct Case (rectangle of single building)
		//else if size > 1
			//Divide (Split input list in two)
			//For each half list run divideAndConquer
			//Combine (Calculate the skyline)
	}
	


	
	public static void main(String[] args){
		List<Building> buildings = new ArrayList<Building>();
		for(String building : args){
			buildings.add(new Building(building));
		}
		
		Skyline sky = new Skyline(buildings);
		
	}
}

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
		List<ChangePoint> skyline = divideAndConquer(buildings);
		System.out.println(skyline);
	}

	private List<ChangePoint> divideAndConquer(List<Building> inputBuildings){
		List<ChangePoint> currentSkyline = new ArrayList<ChangePoint>();
		//if input size is 1, (one building),
		//Do Direct Case (rectangle of single building)
		if(inputBuildings.size() == 1){
			Building b = inputBuildings.get(0);
			currentSkyline.add(new ChangePoint(b.getLeft(), b.getHeight(), LeftRight.L));
			currentSkyline.add(new ChangePoint(b.getRight(), 0.0f, LeftRight.R));
			//System.out.println("BC :   "+currentSkyline);
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
		currentSkyline = combineSkylines(firstHalfSkyline, secondHalfSkyline);

		//System.out.println("FHS:   "+firstHalfSkyline);
		//System.out.println("SHS:   "+secondHalfSkyline);


		//Combine (Calculate the skyline)

		//If comparing 2 L points
		//If same x 
		//take point with larger y. discard one with smaller
		//Else
		//take point with smaller x. Leave other in the other set.

		//If comparing 2 R points
		//If same x 
		//just take one, discard other??????
		//Else
		//If the last point from your sets left is taller than last point from other sets left
		//change point to have right of other sets last right and add it.
		//Else
		//discard point
		//Else (comparing different L R points)
		//add point with smaller x keep other.

		return currentSkyline;
	}

	public List<ChangePoint> combineSkylines(List<ChangePoint> firstHalf, List<ChangePoint> secondHalf){
		float currentHighestLeftSet1 = 0.0f;
		float currentHighestLeftSet2 = 0.0f;

		int fInd = 0;
		int sInd = 0;

		List<ChangePoint> currentSkyline = new ArrayList<ChangePoint>();

		while(fInd < firstHalf.size() && sInd < secondHalf.size()){
			ChangePoint first = firstHalf.get(fInd);
			ChangePoint second = secondHalf.get(sInd);

			//both left
			if(first.isLeft() && second.isLeft()){
				//if same x
				if(first.getX() == second.getX()){
					if(first.getY() > second.getY()){
						currentSkyline.add(first);
						currentHighestLeftSet1 = first.getY();
						fInd++;
						sInd++;
					}else if(first.getY() < second.getY()){
						currentSkyline.add(second);
						currentHighestLeftSet2 = second.getY();
						fInd++;
						sInd++;
					}else{
						currentSkyline.add(first);
						currentHighestLeftSet1 = first.getY();
						currentHighestLeftSet2 = first.getY();
						fInd++;
						sInd++;
					}
					//if first x smaller
				}else if(first.getX() < second.getX()){
					currentSkyline.add(first);
					currentHighestLeftSet1 = first.getY();
					fInd++;				
				}else if(first.getX() > second.getX()){
					currentSkyline.add(second);
					currentHighestLeftSet2 = second.getY();
					sInd++;
				}

				continue;
			}

			//both right
			if(!first.isLeft() && !second.isLeft()){
				//if second x is smaller
				if(first.getX() > second.getX()){
					if(currentHighestLeftSet2 > currentHighestLeftSet1){
						ChangePoint newPoint = new ChangePoint(second.getX(), currentHighestLeftSet1, LeftRight.R);
						currentSkyline.add(newPoint);
						sInd++;
					}else if(currentHighestLeftSet2 <= currentHighestLeftSet1){
						sInd++;
					}
					//if first x is smaller
				}else if(first.getX() < second.getX()){
					if(currentHighestLeftSet1 > currentHighestLeftSet2){
						ChangePoint newPoint = new ChangePoint(first.getX(), currentHighestLeftSet2, LeftRight.R);
						currentSkyline.add(newPoint);
						fInd++;
					}else if(currentHighestLeftSet1 <= currentHighestLeftSet2){
						fInd++;
					}					
				}else if(first.getX() == second.getX()){
					currentSkyline.add(first);
					fInd++;
					sInd++;
				}
				continue;
			}

			//one left one right
			if((first.isLeft() && !second.isLeft()) || (!first.isLeft() && second.isLeft())){
				if(first.getX() < second.getX()){
					currentSkyline.add(first);
					fInd++;
					if(first.isLeft()){
						currentHighestLeftSet1 = first.getY();
					}
				}else if(first.getX() > second.getX()){
					currentSkyline.add(second);
					sInd++;
					if(second.isLeft()){
						currentHighestLeftSet2 = second.getY();
					}
				}else if(first.getX() == second.getX()){
					if(first.isLeft()){
						currentSkyline.add(first);
						currentHighestLeftSet1 = first.getY();
						fInd++;
						sInd++;
					}else{
						currentSkyline.add(second);
						currentHighestLeftSet2 = second.getY();
						fInd++;
						sInd++;
					}
				}
			}
			continue;
		}

		
		
			
		while(fInd < firstHalf.size()){
			currentSkyline.add(firstHalf.get(fInd));
			fInd++;
		}
			
		while(sInd < secondHalf.size()){
			currentSkyline.add(secondHalf.get(sInd));
			sInd++;
		}


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

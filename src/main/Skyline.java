package main;

import java.util.ArrayList;
import java.util.List;

import main.ChangePoint.LeftRight;

public class Skyline {

	private List<Building> _buildings;
	private List<ChangePoint> _skyline;

	public Skyline(List<Building> buildings){
		this._buildings = buildings;
	}

	public Skyline(String[] buildings){
		List<Building> buildingsList = new ArrayList<Building>();
		for(String building : buildings){
			buildingsList.add(new Building(building));
		}
		this._buildings = buildingsList;
	}

	public void runAlgorithm(){
		_skyline = computeSkyline(_buildings);		
	}

	public List<ChangePoint> computeSkyline(List<Building> inputBuildings){
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
		List<ChangePoint> firstHalfSkyline = computeSkyline(firstHalfBuildings);
		List<ChangePoint> secondHalfSkyline = computeSkyline(secondHalfBuildings);

		//Combine (Calculate the skyline)
		currentSkyline = combineSkylines(firstHalfSkyline, secondHalfSkyline);

		return currentSkyline;
	}

	private List<ChangePoint> combineSkylines(List<ChangePoint> firstHalf, List<ChangePoint> secondHalf){
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
						currentHighestLeftSet2 = second.getY();
						fInd++;
						sInd++;
					}else if(first.getY() < second.getY()){
						currentSkyline.add(second);
						currentHighestLeftSet1 = first.getY();
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
						currentHighestLeftSet2 = 0.0f; //need to reset as we have use a right
						sInd++;
					}else if(currentHighestLeftSet2 <= currentHighestLeftSet1){
						currentHighestLeftSet2 = 0.0f;
						sInd++;
					}
					//if first x is smaller
				}else if(first.getX() < second.getX()){
					if(currentHighestLeftSet1 > currentHighestLeftSet2){
						ChangePoint newPoint = new ChangePoint(first.getX(), currentHighestLeftSet2, LeftRight.R);
						currentSkyline.add(newPoint);
						currentHighestLeftSet1 = 0.0f; //need to reset as we have used a right
						fInd++;
					}else if(currentHighestLeftSet1 <= currentHighestLeftSet2){
						currentHighestLeftSet1 = 0.0f;
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
					if(!second.isLeft() && currentHighestLeftSet2 >= first.getY()){
						currentSkyline.add(second);
						currentHighestLeftSet2 = 0.0f;
						sInd++;
						fInd++;//remove first point as it is consumed
					}else{					
						currentSkyline.add(first);
						fInd++;
						if(first.isLeft()){
							currentHighestLeftSet1 = first.getY();
						}
					}	
				}else if(first.getX() > second.getX()){
					if(!first.isLeft() && currentHighestLeftSet1 >= second.getY()){
						currentSkyline.add(first);
						currentHighestLeftSet1 = 0.0f;
						fInd++;
						sInd++;//remove second point as it is consumed
					}else{
						currentSkyline.add(second);
						sInd++;
						if(second.isLeft()){
							currentHighestLeftSet2 = second.getY();
						}
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



		//only one of the following loops will run as only one set list will still have unchecked points
		boolean needCheck = true;
		while(fInd < firstHalf.size()){
			if(!firstHalf.get(fInd).isLeft() && currentHighestLeftSet1 == 0.00f && needCheck){
				fInd++;
				continue;
			}
			currentSkyline.add(firstHalf.get(fInd));
			if(firstHalf.get(fInd).isLeft()){
				currentHighestLeftSet1 = firstHalf.get(fInd).getY();
			}else{
				currentHighestLeftSet1 = 0.00f;
				needCheck = false;
			}
			fInd++;
		}

		needCheck = true;
		while(sInd < secondHalf.size()){
			if(!secondHalf.get(sInd).isLeft() && currentHighestLeftSet2 == 0.00f && needCheck){
				sInd++;
				continue;
			}
			currentSkyline.add(secondHalf.get(sInd));
			if(secondHalf.get(sInd).isLeft()){
				currentHighestLeftSet2 = secondHalf.get(sInd).getY();
			}
			else{
				currentHighestLeftSet2 = 0.00f;
				needCheck = false;
			}
			sInd++;
		}


		return currentSkyline;
	}

	public static void main(String[] args){
		Skyline sky = new Skyline(args);
		sky.runAlgorithm();
		System.out.println(sky.getSkyline());
	}

	public List<ChangePoint> getSkyline() {
		return this._skyline;
	}
}

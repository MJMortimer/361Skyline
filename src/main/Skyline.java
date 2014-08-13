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
		float currentHeight1 = 0.0f;
		float currentHeight2 = 0.0f;
		
		int fInd = 0;
		int sInd = 0;
		
		List<ChangePoint> currentSkyline = new ArrayList<ChangePoint>();

		while(fInd < firstHalf.size() && sInd < secondHalf.size()){
			ChangePoint first = firstHalf.get(fInd);
			ChangePoint second = secondHalf.get(sInd);
			
			if(first.getX() == second.getX()){													//SAME X
				if(first.isLeft() && second.isLeft()){													//BOTH LEFT
					if(first.getY() > second.getY()){
						currentSkyline.add(first);
						fInd++;
						sInd++;
					}else if(first.getY() < second.getY()){
						currentSkyline.add(second);
						fInd++;
						sInd++;
					}else if(first.getY() == second.getY()){
						currentSkyline.add(first);
						fInd++;
						sInd++;
					}
					currentHeight1 = first.getY();
					currentHeight2 = second.getY();
				}else if((first.isLeft() && !second.isLeft()) || (!first.isLeft() && second.isLeft())){	//ONE LEFT ONE RIGHT
					if(first.getY() > second.getY()){
						ChangePoint newPoint = new ChangePoint(first.getX(), first.getY(), LeftRight.R);
						currentSkyline.add(newPoint);						
						fInd++;
						sInd++;						
					}else if(first.getY() < second.getY()){
						ChangePoint newPoint = new ChangePoint(second.getX(), second.getY(), LeftRight.R);
						currentSkyline.add(newPoint);		
						fInd++;
						sInd++;
					}else if(first.getY() == second.getY()){
						ChangePoint newPoint = new ChangePoint(second.getX(), second.getY(), LeftRight.R);
						currentSkyline.add(newPoint);
						fInd++;
						sInd++;	
					}
					currentHeight1 = first.getY();
					currentHeight2 = second.getY();
				}else if(!first.isLeft() && !second.isLeft()){											//BOTH RIGHT
					if(first.getY() > second.getY()){
						currentSkyline.add(first);
						fInd++;
						sInd++;
					}else if(first.getY() < second.getY()){
						currentSkyline.add(second);
						fInd++;
						sInd++;
					}else if(first.getY() == second.getY()){
						currentSkyline.add(first);
						fInd++;
						sInd++;
					}
					currentHeight1 = first.getY();
					currentHeight2 = second.getY();
				}
			}else if(first.getX() < second.getX()){												//FIRST X SMALLER
				if(first.isLeft() && second.isLeft()){													//BOTH LEFT
					currentSkyline.add(first);
					currentHeight1 = first.getY();
					fInd++;
				}else if((first.isLeft() && !second.isLeft()) || (!first.isLeft() && second.isLeft())){	//ONE LEFT ONE RIGHT
					if(!first.isLeft()){
						float height = Math.max(first.getY(), currentHeight2);
						ChangePoint newPoint = new ChangePoint(first.getX(), height, LeftRight.R);
						currentSkyline.add(newPoint);
						currentHeight1 = first.getY();
						fInd++;
					}else{
						if(first.getY() > currentHeight2){
							currentSkyline.add(first);
						}
						currentHeight1 = first.getY();
						fInd++;
					}
				}else if(!first.isLeft() && !second.isLeft()){											//BOTH RIGHT
					if(currentHeight1 > currentHeight2){
						float height = Math.max(currentHeight2, first.getY());
						ChangePoint newPoint = new ChangePoint(first.getX(), height, LeftRight.R);
						currentHeight1 = first.getY();
						currentSkyline.add(newPoint);
						fInd++;
					}else{
						currentHeight1 = first.getY();
						fInd++;
					}					
				}
			}else if(first.getX() > second.getX()){												//SECOND X SMALLER
				if(first.isLeft() && second.isLeft()){													//BOTH LEFT
					currentSkyline.add(second);
					currentHeight2 = second.getY();
					sInd++;
				}else if((first.isLeft() && !second.isLeft()) || (!first.isLeft() && second.isLeft())){	//ONE LEFT ONE RIGHT
					if(!second.isLeft()){
						float height = Math.max(second.getY(), currentHeight1);
						ChangePoint newPoint = new ChangePoint(second.getX(), height, LeftRight.R);
						currentSkyline.add(newPoint);
						currentHeight2 = second.getY();
						sInd++;
					}else{
						if(second.getY() > currentHeight1){
							currentSkyline.add(second);
						}
						currentHeight2 = second.getY();
						sInd++;
					}
				}else if(!first.isLeft() && !second.isLeft()){											//BOTH RIGHT
					if(currentHeight2 > currentHeight1){
						float height = Math.max(currentHeight1, second.getY());
						ChangePoint newPoint = new ChangePoint(second.getX(), height, LeftRight.R);
						currentHeight2 = second.getY();
						currentSkyline.add(newPoint);
						sInd++;
					}else{
						currentHeight2 = second.getY();
						sInd++;
					}
				}
			}
			
			
		}
		
		//only one of the following loops will run as only one set list will still have unchecked points
				boolean needCheck = true;
				while(fInd < firstHalf.size()){
					if(!firstHalf.get(fInd).isLeft() && currentHeight1 == 0.00f && needCheck){
						fInd++;
						continue;
					}
					currentSkyline.add(firstHalf.get(fInd));
					if(firstHalf.get(fInd).isLeft()){
						currentHeight1 = firstHalf.get(fInd).getY();
					}else{
						currentHeight1 = firstHalf.get(fInd).getY();
						needCheck = false;
					}
					fInd++;
				}

				needCheck = true;
				while(sInd < secondHalf.size()){
					if(!secondHalf.get(sInd).isLeft() && currentHeight2 == 0.00f && needCheck){
						sInd++;
						continue;
					}
					currentSkyline.add(secondHalf.get(sInd));
					if(secondHalf.get(sInd).isLeft()){
						currentHeight2 = secondHalf.get(sInd).getY();
					}
					else{
						currentHeight2 = secondHalf.get(sInd).getY();
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

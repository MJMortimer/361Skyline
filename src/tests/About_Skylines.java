package tests;

import java.util.ArrayList;
import java.util.List;

import main.Building;
import main.ChangePoint;
import main.Skyline;

import org.junit.Test;
import static org.junit.Assert.*;

public class About_Skylines {

	private Skyline _skyline;
	private List<ChangePoint> _outputSkyline;
	
	@Test
	public void AssignmentCase() {
		String buildings = "(5.0,6.0,2.0) (0.0,2.0,1.0) (2.0,3.0,3.0) (1.0,4.0,2.0)";
		String expectedSkyline = "[(0.00,1.00), (1.00,2.00), (2.00,3.00), (3.00,2.00), (4.00,0.00), (5.00,2.00), (6.00,0.00)]";
		
		Given_A_Skyline_With_The_Buildings(buildings);
		
		After_Computing_The_Skyline();
		
		The_Skyline_Should_Be(expectedSkyline);	
		
	}
	
	@Test
	public void MultipleLeftEdgeTest1(){
		String buildings = "(0,2,1) (0,3,2) (0,4,3)";
		String expectedSkyline = "[(0.00,3.00), (4.00,0.00)]";
		
		Given_A_Skyline_With_The_Buildings(buildings);
		
		After_Computing_The_Skyline();
		
		The_Skyline_Should_Be(expectedSkyline);
	}
	
	@Test
	public void MultipleLeftEdgeTest2(){
		String buildings = "(0,1,3) (0,2,2) (0,3,1)";
		String expectedSkyline = "[(0.00,3.00), (1.00,2.00), (2.00,1.00), (3.00,0.00)]";
		
		Given_A_Skyline_With_The_Buildings(buildings);
		
		After_Computing_The_Skyline();
		
		The_Skyline_Should_Be(expectedSkyline);
	}
	
	@Test
	public void MultipleRightEdgeTest1(){
		String buildings = "(0,4,3) (1,4,2)";
		String expectedSkyline = "[(0.00,3.00), (4.00,0.00)]";
		
		Given_A_Skyline_With_The_Buildings(buildings);
		
		After_Computing_The_Skyline();
		
		The_Skyline_Should_Be(expectedSkyline);
	}
	
	@Test
	public void MultipleRightEdgeTest2(){
		String buildings = "(3,6,1) (4,6,2) (5,6,3)";
		String expectedSkyline = "[(3.00,1.00), (4.00,2.00), (5.00,3.00), (6.00,0.00)]";
		
		Given_A_Skyline_With_The_Buildings(buildings);
		
		After_Computing_The_Skyline();
		
		The_Skyline_Should_Be(expectedSkyline);
	}
	
	@Test
	public void PosiblyExtremeCase1(){
		String buildings = "(0,5,3) (0,2,8) (1,3,5) (1,5,3) (1,5,4) (7,9,10) (8,10,6) (10,11,8) (11,14,4) (6,15,3) (11,15,7)";
		String expectedSkyline = "[(0.00,8.00), (2.00,5.00), (3.00,4.00), (5.00,0.00), (6.00,3.00), (7.00,10.00), (9.00,6.00), (10.00,8.00), (11.00,7.00), (15.00,0.00)]";
		
		Given_A_Skyline_With_The_Buildings(buildings);
		
		After_Computing_The_Skyline();
		
		The_Skyline_Should_Be(expectedSkyline);
	}
	
	@Test
	public void PosiblyExtremeCase2(){
		String buildings = "(0,1,3) (1,4,5) (2,3,3) (2,3,8) (3,8,4) (4,8,2) (4,7,2) (5,9,11) (6,11,6) (7,10,8) (9,13,4) (10,13,5) (12,13,8) (14,17,3) (15,16,7) (15,20,2) (18,19,8) (18,20,3) (18,20,2)";
		String expectedSkyline = "[(0.00,3.00), (1.00,5.00), (2.00,8.00), (3.00,5.00), (4.00,4.00), (5.00,11.00), (9.00,8.00), (10.00,6.00), (11.00,5.00), (12.00,8.00), (13.00,0.00), (14.00,3.00), (15.00,7.00), (16.00,3.00), (17.00,2.00), (18.00,8.00), (19.00,3.00), (20.00,0.00)]";
		
		Given_A_Skyline_With_The_Buildings(buildings);
		
		After_Computing_The_Skyline();
		
		The_Skyline_Should_Be(expectedSkyline);
	}

	private void The_Skyline_Should_Be(String expectedSkyline) {
		assertTrue("The Expected Sklyline '" + expectedSkyline + "' did not equal the calculated skyline "+_outputSkyline , 
				this._outputSkyline.toString().equals(expectedSkyline));
	}

	private void After_Computing_The_Skyline() {
		_skyline.runAlgorithm();
		_outputSkyline = _skyline.getSkyline();
	}

	private void Given_A_Skyline_With_The_Buildings(String buildings) {
		_skyline = new Skyline(buildings.split(" "));
	}

}

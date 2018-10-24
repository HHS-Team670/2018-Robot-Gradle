package org.usfirst.frc.team670.robot.constants;

public class Field {

	//All Distances in Inches
	public static final double TOLERANCE = 3;
	
	public static final double DS_TO_SWITCH = 140.0;
	public static final double DS_TO_SCALE = 299.65;

	public static final double SWITCH_WIDTH = 56.0;

	public static final double SIDE_TO_SCALE = 71.57;
	public static final double SCALE_WIDTH = 48;


	public static final double DS_TO_BASELINE = 120.0;
	
	public static final double SCALE_SIDE_BACKUP = 18;
	
	public static final double SIDE_TO_SWITCH = 85.25;
	
	public static final double DRIVER_SIDE_LENGTH = 264.0;

	public static final double EXCHANGE_LENGTH = 48.0;

	public static final double EXCHANGE_WIDTH = 36.0;
	
	public static final double SIDE_TRIANGLE_WIDTH = 29.69;
	public static final double SIDE_TRIANGLE_HEIGHT = 36.0;
	
	public static final double SWITCH_LENGTH = 152.88;
	
	public static final double PLATFORM_WIDTH = 95.25;
	
	public static final double DS_TO_CUBEPILE = 98.0;
	public static final double CUBEPILE_WIDTH = 45.0; // parallel to width of the field
	public static final double CUBEPILE_LENGTH = 42.0; // parallel to length of the field
	public static final double SIDE_TO_CUBEPILE = SIDE_TO_SWITCH + SWITCH_LENGTH/2 - CUBEPILE_WIDTH/2;
	
	public static final double EDGE_TO_PLATFORM = 95.25;
	public static final double DS_TO_PLATFORM = 261.47;
	
	public static final double CUBE_WIDTH = 13;
	public static final double DIST_BETWEEN_CUBES = (SWITCH_WIDTH - CUBE_WIDTH * 6)/5;
}

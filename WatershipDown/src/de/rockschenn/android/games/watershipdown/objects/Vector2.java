package de.rockschenn.android.games.watershipdown.objects;

import android.graphics.Point;

public class Vector2 {
	public double x;
	public double y;
	
	//Constructs from Coordinates
	public Vector2(double x, double y){
		this.x = x;
		this.y = y;
	}
	//Constructs from Coordinates from Point pos
	public Vector2(Point pos){
		this.x = pos.x;
		this.y = pos.y;
	}
	//Creates Direction-Vector from Point a to Point b
	public Vector2(Point a, Point b){
		this.x = b.x-a.x;
		this.y = b.y-a.y;
	}
	
	//Returns the Scalar-Product from a and b
	public static double getDotProduct(Vector2 a, Vector2 b){
		return ((a.x*b.x)+(a.y*b.y));
	}
	
	//Returns the length of this Vector
	public double length(){
		return Math.sqrt(this.x*this.x + this.y*this.y);
	}
	//Returns the length Vector a
	public static double length(Vector2 a){
		return Math.sqrt(a.x*a.x + a.y*a.y);
	}
	
	//Normalizes this Vector - !!! Overrides existing values !!!
	public void normalize(){
		x = x/this.length();
		y = y/this.length();
	}
	//Normalizes Vector a
	public static Vector2 normalize(Vector2 a){
		double nx = a.x/a.length();
		double ny = a.y/a.length();
		return new Vector2(nx,ny);
	}
	
	//Returns radian angle between this Vector and x-Axis
	public double getAngleToXAxis(){
		return this.getAngle(new Vector2(1,0));
	}
	//Returns radian angle between Vector a and x-Axis
	public static double getAngleToXAxis(Vector2 a){
		return a.getAngle(new Vector2(1,0));
	}
	
	//Returns radian angle between this Vector and Vector v
	public double getAngle(Vector2 v){
		return Math.acos(getDotProduct(this, v)/(length(this)*length(v)));
	}
	//Returns radian angle between Vector a and Vector b
	public static double getAngle(Vector2 a, Vector2 b){
		return Math.acos(getDotProduct(a, b)/(length(a)*length(b)));
	}
	
	//Rotates this Vector towards target Position with specific speed
	public void rotateTowards(Point target, double speed){
		//TODO
	}
	
}
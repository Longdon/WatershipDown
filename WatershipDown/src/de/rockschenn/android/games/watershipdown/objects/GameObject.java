package de.rockschenn.android.games.watershipdown.objects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Point;

public class GameObject {
	private Bitmap bitmap;		//Bitmap of the Object
	
	private Point position;		//Position of Bitmap-Center
	private Vector2 velocity;	//Velocity-Vector
	private double speed;		//speed-factor of Velocity-Vector, maybe redundant
	private double rotation;	//Rotation-Angle in radian
	
	
	public GameObject(Bitmap b){
		this.bitmap = b;
		this.position = new Point(0,0);
		this.velocity = new Vector2(0,0);
	}
	
	public void update(){		
		//Just spinning around
		rotation+=0.1;
		//Repositioning
		moveObject();
	}
	
	public void drawGameObject(Canvas c){
		Matrix matrix = new Matrix();	//Create new Matix
		matrix.setRotate((float)rotation,bitmap.getWidth()/2,bitmap.getHeight()/2);		//Rotate around bitmap-center
		matrix.postTranslate(position.x-bitmap.getWidth()/2, position.y-bitmap.getHeight()/2);	//Move to Position
		c.drawBitmap(bitmap, matrix, null);		//Draw
	}
	
	public void moveObject(){
		position.x += velocity.x;
		position.y += velocity.y;
	}
	
	public Point getPosition(){
		return position;
	}
	public void setPosition(Point pos){
		position = pos;
	}
}

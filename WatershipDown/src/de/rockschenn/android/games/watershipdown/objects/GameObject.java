package de.rockschenn.android.games.watershipdown.objects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;

public class GameObject {
	private Bitmap bitmap;		//Bitmap of the Object
	
	private Vector2 position;		//Position of Bitmap-Center
	private Vector2 velocity;	//Velocity-Vector (should be normalized)
	private double moveSpeed = 1;	//speed-factor of Velocity-Vector
	private double rotation;	//Rotation-Angle in radian
	//private double rotationSpeed = 0.1; //maximum angle the rotation will be manipulated
	
	private Vector2 target = null;
	
	public GameObject(Bitmap b){
		this.bitmap = b;
		this.position = new Vector2(0,0);
		this.velocity = new Vector2(0,1);
		this.rotation = velocity.getAngleToXAxis();
	}
	
	public void update(){		
		if(target != null){
			//Align Direction
			alignToTarget();
			
			//Repositioning
			moveObject();
		}
	}
	//TODO: Scale doesn't work correctly
	public void drawGameObject(Canvas c, double scale, boolean debug){
		Matrix matrix = new Matrix();	//Create new Matix
		matrix.setRotate((float)Math.toDegrees(rotation), (float)(bitmap.getWidth()/2), (float)(bitmap.getHeight()/2));	//TODO:Rotate around bitmap-center, dont work
		matrix.postTranslate((float)(position.x-bitmap.getWidth()/2), (float)(position.y-bitmap.getHeight()/2));	//Move to Position
		//matrix.postScale((float)scale, (float)scale);
		
		c.drawBitmap(bitmap, matrix, null);		//Draw
		
		if(debug){
			Paint green = new Paint();
			green.setColor(Color.GREEN);
			c.drawText("rot: "+rotation, 10, 10, green);
			
			velocity.drawVector(c, position, green);
			c.drawText("("+(int)position.x+"|"+(int)position.y+")", (int)(position.x+30), (int)(position.y+30), green);
		}
	}
	
	public void moveObject(){
		if(!position.equals(target, 10)){	//Check for same Position with tolerance +-10px
			position.x += (velocity.x*moveSpeed);
			position.y += (velocity.y*moveSpeed);
		}
		else{
			target = null;
		}
		
	}
	public void alignToTarget(){
		
		if(target != null){
			velocity = new Vector2(position, target);
			velocity.normalize();
			
 			if(position.y > target.y){
 				rotation = -velocity.getAngleToXAxis();
			}
			else{
				rotation = velocity.getAngleToXAxis();
			}
		}
	}
	
	public Vector2 getPosition(){
		return position;
	}
	public void setPosition(Vector2 pos){
		position = pos;
	}
	public void setTarget(Vector2 t){
		target = t;
	}
}

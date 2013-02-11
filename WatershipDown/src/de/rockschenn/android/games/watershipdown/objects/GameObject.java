package de.rockschenn.android.games.watershipdown.objects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;

public class GameObject {
	private boolean alive;
	public Bitmap bitmap;		//Bitmap of the Object
	
	private Vector2 position;		//Position of Bitmap-Center
	private Vector2 velocity;	//Velocity-Vector (should be normalized)
	private double moveSpeed = 5;	//speed-factor of Velocity-Vector
	private double rotation;	//Rotation-Angle in radian
	//private double rotationSpeed = 0.1; //maximum angle the rotation will be manipulated
	
	private Vector2 target = null;
	private boolean aligned = false;
	
	public Paint antiAlias;
	public Paint green;
	public Paint blendShadow;
	
	public GameObject(Bitmap b){
		this.alive = true;
		this.bitmap = b;
		this.position = new Vector2(0,0);
		this.velocity = new Vector2(0,1);
		this.rotation = velocity.getAngleToXAxis();
		
		antiAlias = new Paint();
		antiAlias.setAntiAlias(true);
		green = new Paint();
		green.setAntiAlias(true);
		green.setColor(Color.GREEN);
		blendShadow = new Paint(Color.BLACK);
		blendShadow.setAlpha(150);
        ColorFilter filter = new LightingColorFilter(Color.BLACK, 1);
        blendShadow.setColorFilter(filter);
        
	}
	
	public void update(){		
		if(target != null){
			//Align Direction
			if(!aligned){
				alignToTarget();
			}
			
			//Repositioning
			moveObject();
		}
	}
	
	//Draw Game Object
	public void drawGameObject(Canvas c, double scale, boolean debug, Vector2 shadowOffset){
		
		Matrix matShadow = new Matrix();	//Create new Matix
		Matrix matBmp = new Matrix();	//Create new Matix
		
		matShadow.postRotate((float)Math.toDegrees(rotation), (float)(bitmap.getWidth()/2), (float)(bitmap.getHeight()/2));	// Rotate
		matBmp.postRotate((float)Math.toDegrees(rotation), (float)(bitmap.getWidth()/2), (float)(bitmap.getHeight()/2));	// Rotate

		matShadow.postTranslate((float)((position.x-(bitmap.getWidth())/2)+shadowOffset.x), (float)((position.y-(bitmap.getHeight())/2)+shadowOffset.y));	//Move to Position
		matBmp.postTranslate((float)(position.x-(bitmap.getWidth()/2) ), (float)(position.y-(bitmap.getHeight()/2)));	//Move to Position
		
		matShadow.postScale((float)scale, (float)scale);	//Scale All Changes
		matBmp.postScale((float)scale, (float)scale);		//Scale All Changes
	
		c.drawBitmap(bitmap, matShadow, blendShadow);	// Draw Shadow
		c.drawBitmap(bitmap, matBmp, antiAlias);		//Draw Bitmap
		
		if(debug){
			//draws Green Dot as Ship Position
			c.drawCircle((float)(position.x*scale), (float)(position.y*scale), 5, green);
			
			//draws Ships VelocityVector and prints Coords
			Vector2 pos = new Vector2(position.x*scale,position.y*scale);
			velocity.drawVector(c, pos, green);
			c.drawText("("+(int)(position.x*scale)+"|"+(int)(position.y*scale)+")", (int)(position.x*scale+30), (int)(position.y*scale+30), green);
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
 			aligned = true;
		}
	}
	
	public Vector2 getPosition(){
		return position;
	}
	public void setPosition(Vector2 pos){
		position = pos;
	}
	public void setTarget(Vector2 t){
		aligned = false;
		target = t;
	}
}

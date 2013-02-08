package de.rockschenn.android.games.watershipdown.objects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;
import android.view.View;

public class GameObject {
	
	private Bitmap bitmap;		//Bitmap of the Object
	
	private Vector2 position;		//Position of Bitmap-Center
	private Vector2 velocity;	//Velocity-Vector (should be normalized)
	private double moveSpeed = 1;	//speed-factor of Velocity-Vector
	private double rotation;	//Rotation-Angle in radian
	//private double rotationSpeed = 0.1; //maximum angle the rotation will be manipulated
	
	private Vector2 target = null;
	private boolean aligned = false;
	
	private Paint antiAlias;
	private Paint green;
	private Paint blendShadow;
	
	public GameObject(Bitmap b){
		this.bitmap = b;
		this.position = new Vector2(0,0);
		this.velocity = new Vector2(0,1);
		this.rotation = velocity.getAngleToXAxis();
		
		antiAlias = new Paint();
		antiAlias.setAntiAlias(true);
		green = new Paint();
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
	//TODO: Scale doesn't work correctly
	public void drawGameObject(Canvas c, double scale, boolean debug, Vector2 shadowOffset){
		
		Matrix matShadow = new Matrix();	//Create new Matix
		//matrix.postScale((float)scale, (float)scale);
		matShadow.postRotate((float)Math.toDegrees(rotation), (float)(bitmap.getWidth()/2), (float)(bitmap.getHeight()/2));	// Rotate
		Matrix matBmp = new Matrix(matShadow);	//Create new Matix
		
		matShadow.postScale((float)scale, (float)scale);
		matBmp.postScale((float)scale, (float)scale);
		
		matShadow.postTranslate((float)((position.x-(bitmap.getWidth()*scale)/2)+shadowOffset.x*scale), (float)((position.y-(bitmap.getHeight()*scale)/2)+shadowOffset.y*scale));	//Move to Position
		matBmp.postTranslate((float)(position.x-(bitmap.getWidth()*scale)/2), (float)(position.y-(bitmap.getHeight()*scale)/2));	//Move to Position
		
		/* CodeBkp
		matrix.postScale((float)scale, (float)scale);
		matrix.postRotate((float)Math.toDegrees(rotation), (float)(bitmap.getWidth()/2), (float)(bitmap.getHeight()/2));	// Rotate
		matrix.postTranslate((float)(position.x-bitmap.getWidth()/2), (float)(position.y-bitmap.getHeight()/2));	//Move to Position*/
		
		c.drawBitmap(bitmap, matShadow, blendShadow);	// Draw Shadow
		c.drawBitmap(bitmap, matBmp, antiAlias);		//Draw Bitmap
		
		if(debug){
			//c.drawText("rot: "+rotation, 10, 10, green);
			
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

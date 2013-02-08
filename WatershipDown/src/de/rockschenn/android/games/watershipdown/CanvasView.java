package de.rockschenn.android.games.watershipdown;

import de.rockschenn.android.games.watershipdown.objects.GameObject;
import de.rockschenn.android.games.watershipdown.objects.Vector2;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.MotionEvent.PointerCoords;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class CanvasView extends SurfaceView{
	private boolean debug = true;
	private SurfaceHolder surfaceHolder;
	
	// some paints
	private Paint paintBlack;
	private Paint paintWhite;
	private Paint paintRed;
	private Paint paintYellow;
	private Paint paintBlue;
	
	private Point screenSize;
	private Rect viewPort;
	private Vector2 shadowOffset;
	private double scale = 0.5;
	
	private Vector2 downTouch1;
	private Vector2 moveTouch1;
	private Vector2 downTouch2;
	private Vector2 moveTouch2;
	
	//-- Testing Stuff ---
	private Bitmap bm;
	private GameObject go;
	private Vector2 testTarget;
	//--------------------
	
	public CanvasView(Context context, Point size) {
		super(context);
		
		// init some paints
		Log.d("CanView","");
		paintBlack = new Paint();
		paintBlack.setColor(Color.BLACK);
		paintWhite = new Paint();
		paintWhite.setColor(Color.WHITE);
		paintYellow = new Paint();
		paintYellow.setColor(Color.argb(255, 210, 210, 100));
		paintRed = new Paint();
		paintRed.setColor(Color.RED);
		paintBlue = new Paint();
		paintBlue.setColor(Color.BLUE);
		
		// init surfaceholder
		surfaceHolder = getHolder();
        surfaceHolder.addCallback(new SurfaceHolder.Callback() {
            public void surfaceDestroyed(SurfaceHolder holder) {
            	
            }
            public void surfaceCreated(SurfaceHolder holder) {
                Canvas can = surfaceHolder.lockCanvas(null);
                onDraw(can);
                surfaceHolder.unlockCanvasAndPost(can);
                
                
            }
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            	Log.d("CanView","SurfaceChanged");
            	
            }
        });
        
        // set screensize
        screenSize = size;
        screenSize.set(size.x, size.y);
        viewPort = new Rect(0,0,size.x,size.y);
        shadowOffset = new Vector2(-5,10);
        
        //-- testing Stuff --
        bm = BitmapFactory.decodeResource(getResources(), R.drawable.carrier);
        go = new GameObject(bm);
        go.setPosition(new Vector2(250,500));
        
        
        //testTarget = new GameObject(bm);
        testTarget = new Vector2(300,500);
        go.setTarget(testTarget);
        //-------------------
	}
	
	//Update GameObjects here
	public void update(){
		go.update();
		
	}
	
	@Override
	protected void onDraw(Canvas c){
		super.onDraw(c);
		
		//Log.d("CanView","drawing");
		if(c != null){
			// draws blue background
			c.drawColor(Color.BLUE);
			
			// draw helping stuff here for bugfixing
			if(debug){
	    		go.drawGameObject(c, scale, debug, shadowOffset);	//Draw Test-GameObject
	    		
	    		c.drawCircle((float)testTarget.x, (float)testTarget.y, 10, paintRed);
	    		c.drawText("("+(int)(testTarget.x)+"|"+(int)(testTarget.y)+")", (int)(testTarget.x)-30, (int)(testTarget.y)-50, paintRed);
	    			    		
			}
		}
	}
	
	//TODO: Get Multitouch working
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int action = event.getAction() & MotionEvent.ACTION_MASK;
		
		if(action == MotionEvent.ACTION_DOWN){
			if(event.getPointerCount() == 1){
				Log.d("CanView","SingleDown");
				downTouch1 = new Vector2(event.getX(0),event.getY(0));
				
				testTarget = new Vector2(downTouch1);
				go.setTarget(testTarget);
			}
		}
		else if(action == MotionEvent.ACTION_POINTER_DOWN){
			Log.d("CanView","DoubleDown");
			downTouch1 = new Vector2(event.getX(0),event.getY(0));
			downTouch2 = new Vector2(event.getX(1),event.getY(1));
		}
		else if(action == MotionEvent.ACTION_UP){
			
		}
		else if(action == MotionEvent.ACTION_POINTER_UP){
			
		}
		else if(action == MotionEvent.ACTION_MOVE){
			if(event.getPointerCount() == 1){
				Log.d("CanView","SingleMove");
				moveTouch1 = new Vector2(event.getX(0),event.getY(0));
			}
			else if(event.getPointerCount() == 2){
				//Pinch&Zoom
				Log.d("CanView","DoubleMove");
				moveTouch1 = new Vector2(event.getX(0),event.getY(0));
				moveTouch2 = new Vector2(event.getX(1),event.getY(1));
				
				double downLength = new Vector2(downTouch1,downTouch2).length();
				double moveLength = new Vector2(moveTouch1, moveTouch2).length();
				
				scale = moveLength/downLength;
			}
		}
		
		
		
		return true;//super.onTouchEvent(event);
	}
	
	
}

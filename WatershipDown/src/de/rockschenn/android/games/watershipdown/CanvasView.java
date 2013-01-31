package de.rockschenn.android.games.watershipdown;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class CanvasView extends SurfaceView{
	private boolean debug = true;
	private SurfaceHolder surfaceHolder;
	
	// some colors
	private Paint paintBlack;
	private Paint paintWhite;
	private Paint paintRed;
	private Paint paintYellow;
	private Point screenSize;
	
	
	public CanvasView(Context context, Point size) {
		super(context);
		
		// init some colors
		Log.d("CanView","");
		paintBlack = new Paint();
		paintBlack.setColor(Color.BLACK);
		paintWhite = new Paint();
		paintWhite.setColor(Color.WHITE);
		paintYellow = new Paint();
		paintYellow.setColor(Color.argb(255, 210, 210, 100));
		paintRed = new Paint();
		paintRed.setColor(Color.RED);
		
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
	}
	
	@Override
	protected void onDraw(Canvas c){
		super.onDraw(c);
		
		Log.d("CanView","drawing");
		if(c != null){
			// draws black background
			c.drawColor(Color.BLACK);
	        
			// draw helping stuff here for bugfixing
			if(debug){
	    		
			}
		}
        
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		
		return super.onTouchEvent(event);
	}
	
	// returns the distance between two points
	public double getDistance(Point prevPoint, Point newPoint){
		Point dVec = getDeltaVector(prevPoint, newPoint);
		return Math.sqrt(dVec.x*dVec.x + dVec.y*dVec.y);
	}
	
	// returns the directionVector from Point a to Point b
	public Point getDeltaVector(Point a, Point b){
		int dx = b.x-a.x;
		int dy = b.y-a.y;
		return new Point(dx,dy);
	}
}

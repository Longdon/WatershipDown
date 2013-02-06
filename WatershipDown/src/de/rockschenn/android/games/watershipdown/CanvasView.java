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
	private Paint paintBlue;
	
	private Point screenSize;
	
	
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
			// draws black background
			c.drawColor(Color.BLACK);
			
			// draw helping stuff here for bugfixing
			if(debug){
	    		go.drawGameObject(c,0.1, debug);	//Draw Test-GameObject
	    		
	    		c.drawCircle((float)testTarget.x, (float)testTarget.y, 10, paintRed);
	    		c.drawText("("+(int)(testTarget.x)+"|"+(int)(testTarget.y)+")", (int)(testTarget.x)-30, (int)(testTarget.y)-50, paintRed);
			}
		}
        
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		testTarget = new Vector2(event.getX(),event.getY());
        go.setTarget(testTarget);
		
        
        return super.onTouchEvent(event);
	}
	
	
}

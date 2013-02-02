package de.rockschenn.android.games.watershipdown;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {
	
	private CanvasView canView;
	private RedrawThread redrawThread;
	private Point screenSize;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // set to fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
	    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
	    
	    // check ScreenSize
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        screenSize = new Point(metrics.widthPixels,metrics.heightPixels);
	    
        // create CanvasView
	    canView = new CanvasView(this, screenSize);
	    Log.d("Main","Canvas init complete.");
        
        setContentView(canView);
	    //setContentView(R.layout.activity_main);
	    
        
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
	protected void onResume() {
		super.onStart();
		// start redraw-thread
        redrawThread = new RedrawThread(canView);
        redrawThread.start();
	}
	@Override
	protected void onPause() {
		super.onDestroy();
		redrawThread.endThread = true;
	}
    
    
}

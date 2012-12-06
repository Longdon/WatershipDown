package de.rockschenn.android.games.watershipdown;

import android.graphics.Canvas;
import android.util.Log;

public class RedrawThread extends Thread{

	public boolean endThread;
	private CanvasView canView;
	
	public RedrawThread(CanvasView v){
		endThread = false;
		this.canView = v;
	}
	
	@Override
	public void run() {
		Log.d("RedrawThread", "Started");
		endThread = false;
		
		while(!endThread){
			// redraw Canvas
			redraw();
		}
		Log.d("AnimationThread", "Ended");
	}
	
	// redraws Canvas
	public void redraw(){
		Canvas can = null;
		try {
			can = canView.getHolder().lockCanvas();
		    synchronized (canView.getHolder()) {
		    	canView.onDraw(can);
		    }
		   
		} finally {
			if (can != null) {
				canView.getHolder().unlockCanvasAndPost(can);
		    }
		}
	}
}

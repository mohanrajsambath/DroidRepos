package com.camerasurfvw_02.view;

import java.io.IOException;
import java.util.List;

import android.content.Context;
import android.hardware.Camera;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
	private SurfaceHolder mHolder;
	private Camera mCamera;
	private ZoomCallback zoomCallback = null;
	private float mDist = 0;

	public interface ZoomCallback {
		public void onZoomChanged(int progress);
	}



	public CameraPreview(Context context, Camera camera) {
		super(context);
		mCamera = camera;
		mHolder = getHolder();
		mHolder.addCallback(this);
		// deprecated setting, but required on Android versions prior to 3.0
		mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}

	public void surfaceCreated(SurfaceHolder holder) {
		try {
			// create the surface and start camera preview
			if (mCamera == null) {
				mCamera.setPreviewDisplay(holder);
				mCamera.startPreview();
			}
		} catch (IOException e) {
			Log.d(VIEW_LOG_TAG, "Error setting camera preview: " + e.getMessage());
		}
	}

	public void refreshCamera(Camera camera) {
		if (mHolder.getSurface() == null) {
			// preview surface does not exist
			return;
		}
		// stop preview before making changes
		try {
			mCamera.stopPreview();
		} catch (Exception e) {
			// ignore: tried to stop a non-existent preview
		}
		// set preview size and make any resize, rotate or
		// reformatting changes here
		// start preview with new settings
		setCamera(camera);
		try {
			mCamera.setPreviewDisplay(mHolder);
			mCamera.startPreview();
		} catch (Exception e) {
			Log.d(VIEW_LOG_TAG, "Error starting camera preview: " + e.getMessage());
		}
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
		// If your preview can change or rotate, take care of those events here.
		// Make sure to stop the preview before resizing or reformatting it.
		refreshCamera(mCamera);
	}

	public void setCamera(Camera camera) {
		//method to set a camera instance
		mCamera = camera;
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		// mCamera.release();

	}

//Methods and Action Need to Handle the Zoom

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// Get the pointer ID

		Camera.Parameters params = mCamera.getParameters();

		if (params == null)
			return false;

		int action = event.getAction();


		if (event.getPointerCount() > 1) {
			// handle multi-touch events
			if (action == MotionEvent.ACTION_POINTER_DOWN) {
				Log.v("Camera","action == MotionEvent.ACTION_POINTER_DOWN");
				mDist = getFingerSpacing(event);
			} else if (action == MotionEvent.ACTION_MOVE && params.isZoomSupported()) {
				mCamera.cancelAutoFocus();
				handleZoom(event, params);
				Log.v("Camera", "action == MotionEvent.ACTION_MOVE && params.isZoomSupported()");
			}
		} else {
			// handle single touch events
			if (action == MotionEvent.ACTION_UP) {
				mCamera.cancelAutoFocus();
				handleFocus(event, params);
				Log.v("Camera","action == MotionEvent.ACTION_UP");
			}
		}
		return true;
	}


	private void handleZoom(MotionEvent event, Camera.Parameters params) {
		int maxZoom = params.getMaxZoom();
		int zoom = params.getZoom();
		float newDist = getFingerSpacing(event);
		if (newDist > mDist) {
			//zoom in
			if (zoom < maxZoom)
				zoom++;
		} else if (newDist < mDist) {
			//zoom out
			if (zoom > 0)
				zoom--;
		}
		mDist = newDist;
		params.setZoom(zoom);
		mCamera.setParameters(params);

		if (zoomCallback != null) {
			zoomCallback.onZoomChanged((zoom * 100 / maxZoom));
		}

		Log.e("Zoom", "MAX :" + maxZoom + "Zoom %" + (zoom * 100 / maxZoom));
	}


	public void handleFocus(MotionEvent event, Camera.Parameters params) {

		int pointerId = event.getPointerId(0);
		int pointerIndex = event.findPointerIndex(pointerId);
		// Get the pointer's current position
		float x = event.getX(pointerIndex);
		float y = event.getY(pointerIndex);

		List<String> supportedFocusModes = params.getSupportedFocusModes();
		if (supportedFocusModes != null && supportedFocusModes.contains(Camera.Parameters.FOCUS_MODE_AUTO)) {
			try {
				mCamera.autoFocus(new Camera.AutoFocusCallback() {
					@Override
					public void onAutoFocus(boolean b, Camera camera) {
						// currently set to auto-focus on single touch
					}
				});
			} catch (RuntimeException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Determine the space between the first two fingers
	 */
	private float getFingerSpacing(MotionEvent event) {
		// ...
		double x = event.getX(0) - event.getX(1);
		double y = event.getY(0) - event.getY(1);
		return (float) Math.sqrt(x * x + y * y);
	}

	public void setOnZoomCallback(ZoomCallback zoomCallback) {
		this.zoomCallback = zoomCallback;
	}
}
package com.surfaceviewcamerarecording.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.hardware.Camera;
import android.media.MediaRecorder;
import android.os.Build;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;
import java.util.List;

@SuppressLint("NewApi")
public class CloneCamPreview extends SurfaceView implements SurfaceHolder.Callback {
    private final String VIDEO_PATH_NAME = "/mnt/sdcard/AND_Rec_1.mp4";
    protected Camera mCamera;
    protected Activity mActivity;
    protected boolean mInitSuccesful = false;
    protected MediaRecorder mMediaRecorder;
    protected List<Camera.Size> mPreviewSizeList;
    private SurfaceHolder mHolder;
    private CloneCamPreview.LayoutMode mLayoutMode;
    private int mCameraId;
    private int mCenterPosX = -1;
    private int mCenterPosY;


    public CloneCamPreview(Activity activity, int cameraId, CloneCamPreview.LayoutMode mode) {
        super(activity);
        mActivity = activity;
        mLayoutMode = mode;
        mHolder = getHolder();
        mHolder.addCallback(this);
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            if (Camera.getNumberOfCameras() > cameraId) {
                mCameraId = cameraId;
            } else {
                mCameraId = 0;
            }
        } else {
            mCameraId = 0;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            mCamera = Camera.open(mCameraId);
        } else {
            mCamera = Camera.open();
        }
        Camera.Parameters cameraParams = mCamera.getParameters();
        mPreviewSizeList = cameraParams.getSupportedPreviewSizes();
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        try {
            mCamera.setPreviewDisplay(mHolder);
        } catch (IOException e) {
            mCamera.release();
            mCamera = null;
            e.printStackTrace();
        }

    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }

    /**
     * @param x X coordinate of center position on the screen. Set to negative
     *          value to unset.
     * @param y Y coordinate of center position on the screen.
     */
    public void setCenterPosition(int x, int y) {
        mCenterPosX = x;
        mCenterPosY = y;
    }

    public enum LayoutMode {
        FitToParent, // Scale to the size that no side is larger than the parent
        NoBlank // Scale to the size that no side is smaller than the parent
    }


}
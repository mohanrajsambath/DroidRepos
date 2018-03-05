package com.surfaceviewcamerarecording.utils;

import android.annotation.SuppressLint;
import android.hardware.Camera;
import android.media.MediaRecorder;
import android.os.Build;
import android.util.Log;

public class CaptureUtils {

    // //////////////////////////////////////////////////////////////////////
    // Fields
    // //////////////////////////////////////////////////////////////////////
   // private Camera mCamera;

    int activeCamera = 0;
    private int currentOrientation = 90;
    private MediaRecorder mMediaRecorder;

    // //////////////////////////////////////////////////////////////////////
    // Public methods
    // //////////////////////////////////////////////////////////////////////
//    @SuppressWarnings("unused")
//    private CaptureUtils() {
//        initCamera();
//    }


    public CaptureUtils(){

    }
    public CaptureUtils(Camera camera) {
       // mCamera = camera;

       // Camera.Parameters params = mCamera.getParameters();
       // List<String> focusList = params.getSupportedFocusModes();

//        if (focusList.contains(Camera.Parameters.FOCUS_MODE_AUTO)) {
//            params.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
//        }
//
//        mCamera.setParameters(params);
    }

    public void releaseCamera() {
//        if (mCamera != null) {
//            mCamera.release();
//        }
    }

   // public Camera getCamera() {
       // return mCamera;
    //}

    @SuppressLint("NewApi")
    public void flipCamera() {
        if (Build.VERSION.SDK_INT < 9)
            return;

        if (Camera.getNumberOfCameras() > 1) {
            activeCamera = activeCamera == 0 ? 1 : 0;
            initCamera();
        }
    }

    public void toggleFlash(boolean isEnabled) {
//        if (!isCameraFlashAvailable())
//            return;

        String mode;
        if (isEnabled)
            mode = Camera.Parameters.FLASH_MODE_ON;
        else
            mode = Camera.Parameters.FLASH_MODE_OFF;

//        Camera.Parameters params = mCamera.getParameters();
//        params.setFlashMode(mode);
//        try {
//            mCamera.setParameters(params);
//        } catch (Exception e) {
//            // bypass
//        }
    }

//    public boolean isCameraFlashAvailable() {
//       // Camera.Parameters p = mCamera.getParameters();
//        return p.getFlashMode() == null ? false : true;
//    }
//
//    public boolean isCameraFlashEnabled() {
//        Camera.Parameters p = mCamera.getParameters();
//        String flashMode = p.getFlashMode();
//        if (flashMode == null)
//            return false;
//
//        return flashMode.equals(Camera.Parameters.FLASH_MODE_ON);
//    }

    public void takeShot() throws Exception {
       // final Camera.Parameters p = mCamera.getParameters()THREE_GPP;
        final String VIDEO_PATH_NAME = "/mnt/sdcard/rec0001.3gp";
try {
            if (mMediaRecorder == null)
                mMediaRecorder = new MediaRecorder();
            //mMediaRecorder.setCamera(mCamera);
           // mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);
            mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
            // mMediaRecorder.setOutputFormat(8);
            mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
           mMediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.DEFAULT);
           // mMediaRecorder.setVideoEncodingBitRate(512 * 1000);
            //mMediaRecorder.setVideoFrameRate(30);
            //mMediaRecorder.setVideoSize(640, 480);
            mMediaRecorder.setOutputFile(VIDEO_PATH_NAME);
            mMediaRecorder.prepare();
            //mMediaRecorder.start();
            Log.v("Camera", "surfaceCreated");
            mMediaRecorder.start();
            Log.v("Camera", "StartRecording");
        }
        catch (Exception e){
    throw  e;
        }

    }

    public void stopRecording() {
        if (mMediaRecorder != null) {
            mMediaRecorder.stop();
            mMediaRecorder.reset();
            mMediaRecorder.release();
        }
    }
//
//    public boolean hasAutofocus() {
//        Camera.Parameters params = mCamera.getParameters();
//        List<String> focusList = params.getSupportedFocusModes();
//        return focusList.contains(Camera.Parameters.FOCUS_MODE_AUTO);
//    }
//
//    public PictureSize getPreviewSize(int reqHeight) {
//        if (mCamera == null)
//            return null;
//
//        Camera.Parameters params = mCamera.getParameters();
//        List<Camera.Size> sizes = params.getSupportedPreviewSizes();
//        if (sizes.size() == 0)
//            return null;
//
//        Camera.Size size = sizes.get(0);
//        float k = (float) reqHeight / size.height;
//        int reqWidth = (int) (size.width * k);
//
//        PictureSize reqSize = new PictureSize();
//        reqSize.width = reqWidth;
//        reqSize.height = reqHeight;
//
//        params.setPreviewSize(reqWidth, reqHeight);
//
//        return reqSize;
//    }

    @SuppressWarnings("deprecation")
    @SuppressLint("NewApi")
    private void initCamera() {
        releaseCamera();
//
//        if (Build.VERSION.SDK_INT < 9)
//            mCamera = Camera.open();
//        else
//            mCamera = Camera.open(activeCamera);
//
//        if (mCamera != null) {
//            // mCamera.setDisplayOrientation(currentOrientation);
//            toggleFlash(true);
//
//        }
    }

    // //////////////////////////////////////////////////////////////////////
    // Private methods
    // //////////////////////////////////////////////////////////////////////

    public static class PictureSize {
        public int width;
        public int height;
    }

}
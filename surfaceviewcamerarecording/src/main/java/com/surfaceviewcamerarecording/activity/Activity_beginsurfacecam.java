package com.surfaceviewcamerarecording.activity;

 /*
 * Copyright (c) 2018. Created by Mohanraj.S, on 25/1/18 for DroidRepos
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.content.pm.ActivityInfo;
import android.hardware.Camera;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.ZoomControls;

import com.surfaceviewcamerarecording.R;

import java.io.IOException;
import java.util.List;

public class Activity_beginsurfacecam extends AppCompatActivity implements SurfaceHolder.Callback{
    private SurfaceView preview = null;
    private SurfaceHolder previewHolder = null;
    public Camera camera;
    private boolean inPreview = false;
    ImageButton btnCapture;
    private ZoomControls zoomControls;
    RelativeLayout pCameraLayout;
    public boolean isRecord = false;
    private boolean mInitSuccesful = false;
    private final String VIDEO_PATH_NAME = "/mnt/sdcard/SRFrec_1.mp4";
    private MediaRecorder mMediaRecorder;


    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.begin_surface_cam_layout);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        preview = (SurfaceView) findViewById(R.id.surface);
        pCameraLayout = (RelativeLayout) findViewById(R.id.pCameraLayout);
        previewHolder = preview.getHolder();
        previewHolder.addCallback(this);
        previewHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        previewHolder.setFixedSize(getWindow().getWindowManager()
                .getDefaultDisplay().getWidth(), getWindow().getWindowManager()
                .getDefaultDisplay().getHeight());

        btnCapture = (ImageButton) findViewById(R.id.btnCapture);
        enableZoom();

        btnCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), "Capture Image(s)", Toast.LENGTH_LONG).show();
                System.out.println("isRecord:"+isRecord);
                if(!isRecord) {
                    mMediaRecorder.start();
                    btnCapture.setImageResource(!isRecord ? R.drawable.stop : R.drawable.record);
                    isRecord=true;
                }else{
                    btnCapture.setImageResource(isRecord ? R.drawable.record : R.drawable.stop);
                    mMediaRecorder.stop();
                    mMediaRecorder.reset();
                    isRecord=false;
                }

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        camera = Camera.open();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (inPreview) {
            camera.stopPreview();
        }
        camera.release();
        camera = null;
        inPreview = false;
    }

    /* Init the MediaRecorder, the order the methods are called is vital to
     * its correct functioning */
    private void initRecorder(Surface surface) throws IOException {
        // It is very important to unlock the camera before doing setCamera
        // or it will results in a black preview
        if(camera == null) {
            camera = android.hardware.Camera.open();
            camera.unlock();
        }

        if(mMediaRecorder == null)  mMediaRecorder = new MediaRecorder();
        mMediaRecorder.setPreviewDisplay(surface);
        mMediaRecorder.setCamera(camera);

        mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.DEFAULT);
        //       mMediaRecorder.setOutputFormat(8);
        mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        mMediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
        mMediaRecorder.setVideoEncodingBitRate(512 * 1000);
        mMediaRecorder.setVideoFrameRate(30);
        mMediaRecorder.setVideoSize(640, 480);
        mMediaRecorder.setOutputFile(VIDEO_PATH_NAME);

        try {
            mMediaRecorder.prepare();
        } catch (IllegalStateException e) {
            // This is thrown if the previous calls are not called with the
            // proper order
            e.printStackTrace();
        }

        mInitSuccesful = true;
    }
    private Camera.Size getBestPreviewSize(int width, int height, Camera.Parameters parameters) {
        Camera.Size result = null;
        for (Camera.Size size : parameters.getSupportedPreviewSizes()) {
            if (size.width <= width && size.height <= height) {
                if (result == null) {
                    result = size;
                } else {
                    int resultArea = result.width * result.height;
                    int newArea = size.width * size.height;
                    if (newArea > resultArea) {
                        result = size;
                    }
                }
            }
        }
        return (result);
    }

    /*SurfaceHolder.Callback surfaceCallback = new SurfaceHolder.Callback() {

        public void surfaceCreated(SurfaceHolder holder) {
            try {
                //camera.setPreviewDisplay(previewHolder);
                try {
                    if(!mInitSuccesful)
                        initRecorder(previewHolder.getSurface());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (Throwable t) {
                Log.e("SurfaceCallback",
                        "Exception in setPreviewDisplay()", t);
                Toast.makeText(Activity_beginsurfacecam.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        }

        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            Camera.Parameters parameters = camera.getParameters();
            Camera.Size size = getBestPreviewSize(width, height,
                    parameters);
            if (size != null) {
                parameters.setPreviewSize(size.width, size.height);
                camera.setParameters(parameters);
                camera.startPreview();
                inPreview = true;
            }
        }

        public void surfaceDestroyed(SurfaceHolder holder) {

        }
    };*/

    public void takeShot(final Camera.PictureCallback callback) {
        final Camera.Parameters p = camera.getParameters();

        if (hasAutofocus()) {
            camera.autoFocus(new Camera.AutoFocusCallback() {

                @Override
                public void onAutoFocus(boolean success, Camera camera) {

                    Log.v(" takeShot", "onAutoFocus");

                    try {
                        camera.takePicture(null, null, callback);
                        p.setRotation(180);
                        // camera.setParameters(p);
                    } catch (RuntimeException e) {
                        // bypass
                        CamActivity.isClicked = false;
                        e.printStackTrace();
                    }
                }
            });
        } else {
            try {
                // mCamera.setParameters(p);
                camera.takePicture(null, null, callback);
                p.setRotation(180);
            } catch (RuntimeException e) {
                // bypass
                e.printStackTrace();
            }
        }
    }

    public boolean hasAutofocus() {
        Camera.Parameters params = camera.getParameters();
        List<String> focusList = params.getSupportedFocusModes();
        return focusList.contains(Camera.Parameters.FOCUS_MODE_AUTO);
    }


    private void enableZoom() {
        zoomControls = new ZoomControls(this);
        zoomControls.setIsZoomInEnabled(true);
        zoomControls.setIsZoomOutEnabled(true);
        zoomControls.setOnZoomInClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                zoomCamera(false);

            }
        });
        zoomControls.setOnZoomOutClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                zoomCamera(true);
            }
        });
        pCameraLayout.addView(zoomControls);
    }

    /**
     * Enables zoom feature in native camera .  Called from listener of the view
     * used for zoom in  and zoom out.
     *
     * @param zoomInOrOut "false" for zoom in and "true" for zoom out
     */
    public void zoomCamera(boolean zoomInOrOut) {
        if (camera != null) {
            final Camera.Parameters parameter = camera.getParameters();

            if (parameter.isZoomSupported()) {
                int MAX_ZOOM = parameter.getMaxZoom();
                int currnetZoom = parameter.getZoom();
                if (zoomInOrOut && (currnetZoom < MAX_ZOOM && currnetZoom >= 0)) {
                    parameter.setZoom(++currnetZoom);
                } else if (!zoomInOrOut && (currnetZoom <= MAX_ZOOM && currnetZoom > 0)) {
                    parameter.setZoom(--currnetZoom);
                }
            } else
                Toast.makeText(Activity_beginsurfacecam.this, "Zoom Not Avaliable", Toast.LENGTH_LONG).show();

            camera.setParameters(parameter);
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        try {
            //camera.setPreviewDisplay(previewHolder);
            try {
                if(!mInitSuccesful)
                    initRecorder(previewHolder.getSurface());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Throwable t) {
            Log.e("SurfaceCallback",
                    "Exception in setPreviewDisplay()", t);
            Toast.makeText(Activity_beginsurfacecam.this, t.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Camera.Parameters parameters = camera.getParameters();
        Camera.Size size = getBestPreviewSize(width, height,
                parameters);
        if (size != null) {
            parameters.setPreviewSize(size.width, size.height);
            camera.setParameters(parameters);
            camera.startPreview();
            inPreview = true;
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        shutdown();
    }

    private void shutdown() {
        // Release MediaRecorder and especially the Camera as it's a shared
        // object that can be used by other applications
        mMediaRecorder.reset();
        mMediaRecorder.release();
        camera.release();

        // once the objects have been released they can't be reused
        mMediaRecorder = null;
        camera = null;
    }
}


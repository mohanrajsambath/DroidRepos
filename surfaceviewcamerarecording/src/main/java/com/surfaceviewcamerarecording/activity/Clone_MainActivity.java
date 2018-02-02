package com.surfaceviewcamerarecording.activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.surfaceviewcamerarecording.R;
import com.surfaceviewcamerarecording.utils.CaptureUtils;
import com.surfaceviewcamerarecording.view.CamPreview;
import com.surfaceviewcamerarecording.view.CloneCamPreview;

public class Clone_MainActivity extends AppCompatActivity {

   // CamPreview mPreview;
    private int activeCamera = 0;
    private RelativeLayout previewParent;
    private LinearLayout blackTop, blackBottom;
    private boolean isRecording = false;
    ImageButton imageButton1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Hide status-bar
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // Hide title-bar, must be before setContentView
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.clone_activity_cam);
        InitControls();

    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    void InitControls() {

        previewParent = (RelativeLayout) findViewById(R.id.rlPreview_clone);
        blackTop = (LinearLayout) findViewById(R.id.llBlackTop);
        blackBottom = (LinearLayout) findViewById(R.id.llBlackBottom);
        imageButton1 = (ImageButton)findViewById(R.id.imageButton1);


    }

    @Override
    protected void onResume() {
        super.onResume();
       //setupCamera(activeCamera);
    }

    private void setupCamera(final int camera) {
        // Set the second argument by your choice.
        // Usually, 0 for back-facing camera, 1 for front-facing camera.
        // If the OS is pre-gingerbreak, this does not have any effect.
        try {
           // mPreview = new CamPreview(this, camera, CamPreview.LayoutMode.NoBlank);// .FitToParent);
        } catch (Exception e) {
            Toast.makeText(this, R.string.cannot_connect_to_camera, Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        RelativeLayout.LayoutParams previewLayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        int width = outMetrics.widthPixels;
        int height = outMetrics.heightPixels;

        previewLayoutParams.height = width;
        previewLayoutParams.width = width;

        // Un-comment below line to specify the position.
       // mPreview.setCenterPosition(width / 2, height / 2);

       // previewParent.addView(mPreview, 0, previewLayoutParams);


    }

    public void startRecording(View view) {
        if (!isRecording) {
            isRecording = true;
            imageButton1.setImageResource(R.drawable.stop);
            CaptureUtils recordingInstance = new CaptureUtils();
            try {
                recordingInstance.takeShot();
            }
            catch (Exception e){
                Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
            }
        }else {
            isRecording = false;
            imageButton1.setImageResource(R.drawable.record);
            CaptureUtils recordingInstance = new CaptureUtils();
            recordingInstance.stopRecording();
            //mPreview.
        }
    }
}
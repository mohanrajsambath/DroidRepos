package com.cc.facedetection;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.io.InputStream;

/**
 * Created by Paul on 11/4/15.
 */
public class MainActivity extends AppCompatActivity {

    private FaceOverlayView mFaceOverlayView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFaceOverlayView = (FaceOverlayView) findViewById( R.id.face_overlay );

        InputStream stream = getResources().openRawResource( R.raw.swatkats_profile );
        //InputStream stream = getResources().openRawResource( R.raw.passport );
        //InputStream stream = getResources().openRawResource( R.raw.face );
        Bitmap bitmap = BitmapFactory.decodeStream(stream);

        mFaceOverlayView.setBitmap(bitmap);

    }
}

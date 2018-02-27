package com.autocam.facedetection;

import android.graphics.Rect;
import android.hardware.Camera;
import android.hardware.camera2.params.Face;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by innobot-linux-4 on 28/2/17.
 */

public class MyFaceDetectionListener implements Camera.FaceDetectionListener {



    @Override
    public void onFaceDetection(Camera.Face[] faces, Camera camera) {
        if (faces.length == 0) {
            Log.i(TAG, "No faces detected");
        } else if (faces.length > 0) {
            Log.i(TAG, "Faces Detected = " +
                    String.valueOf(faces.length));

             List<Rect> faceRects;
            faceRects = new ArrayList<Rect>();

            for (int i=0; i<faces.length; i++) {
                int left = faces[i].rect.left;
                int right = faces[i].rect.right;
                int top = faces[i].rect.top;
                int bottom = faces[i].rect.bottom;
                //Rect uRect = new Rect(left0, top0, right0, bottom0);
                Rect uRect = new Rect(30, 30, 30, 30);
                faceRects.add(uRect);
            }

            // add function to draw rects on view/surface/canvas
        }
    }
}
package com.cc.aws;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.content.Intent;

import com.aws.R;


 /*
 * Copyright (c) 2018. Created by Mohanraj.S,on 9/2/18 for DroidRepos
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

public class Aws_HomeActivity extends AppCompatActivity {
    private Button btnDownload,btnUpload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aws_homeactivity);
        initUI();

    }

    private void initUI() {
        btnDownload = (Button)findViewById(R.id.buttonDownloadMain);
        btnUpload = (Button)findViewById(R.id.buttonUploadMain);
        btnDownload.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(Aws_HomeActivity.this, DownloadActivity.class);
                startActivity(intent);
            }
        });

        btnUpload.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(Aws_HomeActivity.this, UploadActivity.class);
                startActivity(intent);
            }
        });
    }
}


class AMAZE{
    // Initialize the Amazon Cognito credentials provider
    /*CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
            getApplicationContext(),
            "ap-south-1:cdfd9012-52f5-423d-beec-17f9936077f7", // Identity pool ID
            Regions.AP_SOUTH_1 // Region
    );*/


    // Initialize the Cognito Sync client
    /*CognitoSyncManager syncClient = new CognitoSyncManager(
            getApplicationContext(),
            Regions.AP_SOUTH_1, // Region
            credentialsProvider);

    // Create a record in a dataset and synchronize with the server
    Dataset dataset = syncClient.openOrCreateDataset("myDataset");
dataset.put("myKey", "myValue");
dataset.synchronize(new DefaultSyncCallback() {
        @Override
        public void onSuccess(Dataset dataset, List newRecords) {
            //Your handler code here
        }
    });*/

}
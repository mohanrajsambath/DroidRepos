package com.cc.aws;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


 /*
 * Copyright (c) 2018. Created by Mohanraj.S, on 9/2/18 for DroidRepos
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
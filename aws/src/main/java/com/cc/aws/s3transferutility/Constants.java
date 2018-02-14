package com.cc.aws.s3transferutility;

 /*
 * Copyright (c) 2018. Created by Mohanraj.S,Innobot Systems on 12/2/18 for DroidRepos
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

public class Constants {
    /*
    * You should replace these values with your own. See the README for details
    * on what to fill in.
    */
    public static final String COGNITO_POOL_ID = "ap-south-1:cdfd9012-52f5-423d-beec-17f9936077f7";

    /*
     * Region of your Cognito identity pool ID.
     */
    public static final String COGNITO_POOL_REGION = "Regions.AP_SOUTH_1";

    /*
     * Note, you must first create a bucket using the S3 console before running
     * the sample (https://console.aws.amazon.com/s3/). After creating a bucket,
     * put it's name in the field below.
     */
    public static final String BUCKET_NAME = "cc.aws.android";

    /*
     * Region of your bucket.
     */
    public static final String BUCKET_REGION = "Regions.AP_SOUTH_1";
}

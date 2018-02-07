package com.mytime.utils;

 /*
 * Copyright (c) 2018. Created by Mohanraj.S,Innobot Systems on 7/2/18 for DroidRepos
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

import com.mytime.activity.MainActivity;

public class CountDownRunner implements Runnable{
    @Override
    public void run() {
        while(!Thread.currentThread().isInterrupted()){
            try {
                MainActivity mObj= new MainActivity();
                mObj.doWork();
                Thread.sleep(1000); // Pause of 1 Second
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }catch(Exception e){
            }
        }
    }
}

package com.example.project_android.services;

import android.content.Context;
import android.hardware.Camera;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;

import com.example.project_android.ui.adapter.DevicesAdapter;

import static android.content.Context.VIBRATOR_SERVICE;

public class Alert {

    private boolean isFlashOn;
    private Camera camera;
    Camera.Parameters params;
    private Vibrator vibrator;

    private void turnOnFlash(){
        if (!isFlashOn) {
            if (camera == null && params == null) {
                return;
            }
            params = camera.getParameters();
            params.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            camera.setParameters(params);
            camera.startPreview();
            isFlashOn = true;


        }

    }

    private void turnOffFlash() {
        if (isFlashOn) {
            if (camera == null && params == null) {
                return;
            }

            params = camera.getParameters();
            params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            camera.setParameters(params);
            camera.stopPreview();
            isFlashOn = false;

        }
    }

    private void flash_effect() throws InterruptedException
    {

        Thread a = new Thread()
        {

            public void run()
            {
                camera= Camera.open();

                try {
                    //vibrator = (Vibrator) context.getSystemService(VIBRATOR_SERVICE);
                    //vibrator.hasVibrator();
                    camera= Camera.open();
                    for(int i =0; i < 6; i++)
                    {

                        turnOnFlash();
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        turnOffFlash();
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e) {

                            e.printStackTrace();
                        }

                    }
                    camera.stopPreview();
                    camera.release();
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

        };

        a.start();
    }
}

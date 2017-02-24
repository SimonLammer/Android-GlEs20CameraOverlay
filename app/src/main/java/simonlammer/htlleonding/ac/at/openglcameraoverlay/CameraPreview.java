package simonlammer.htlleonding.ac.at.openglcameraoverlay;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

import static android.content.ContentValues.TAG;

/**
 * Created by Simon Lammer on 22.02.2017.
 */

public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder mHolder;
    private Camera mCamera;

    public CameraPreview(Context context) {
        super(context);
        setCamera(context);
    }

    public CameraPreview(Context context, AttributeSet attrs) {
        super(context, attrs);
        setCamera(context);
    }

    public CameraPreview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setCamera(context);
    }

    public void surfaceCreated(SurfaceHolder holder) {
        if (mCamera == null) {
            return;
        }

        mCamera.startPreview();
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        // empty. Take care of releasing the Camera preview in your activity.
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        if (mCamera == null) {
            return;
        }

        // If your preview can change or rotate, take care of those events here.
        // Make sure to stop the preview before resizing or reformatting it.

        if (mHolder.getSurface() == null){
            // preview surface does not exist
            return;
        }

        // stop preview before making changes
        try {
            mCamera.stopPreview();
        } catch (Exception e){
            // ignore: tried to stop a non-existent preview
        }

        // set preview size and make any resize, rotate or
        // reformatting changes here

        // start preview with new settings
        try {
            mCamera.setPreviewDisplay(mHolder);
            mCamera.startPreview();

        } catch (Exception e){
            Log.d(TAG, "Error starting camera preview: " + e.getMessage());
        }
    }

    private void setCamera(Context context) {
        mCamera = getCameraInstance(context);

        // Install a SurfaceHolder.Callback so we get notified when the
        // underlying surface is created and destroyed.
        mHolder = getHolder();
        mHolder.addCallback(this);
        // deprecated setting, but required on Android versions prior to 3.0
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    public void onDestroy() {
        if (mCamera != null) {
            mCamera.release();
        }
    }

    public static void requestCameraPermissions(MainActivity mainActivity) {
        if (ContextCompat.checkSelfPermission(mainActivity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            //if (ActivityCompat.shouldShowRequestPermissionRationale(mainActivity, Manifest.permission.CAMERA)) {

            //} else {
                ActivityCompat.requestPermissions(mainActivity, new String[]{
                        Manifest.permission.CAMERA
                }, 1);
            //}
        }
    }

    private static Camera getCameraInstance(Context context){
        Camera c = null;
        try {
            c = Camera.open(0); // attempt to get a Camera instance
        }
        catch (Exception e){
            // Camera is not available (in use or does not exist)
            throw new IllegalStateException("Camera is not available", e);
        }
        return c; // returns null if camera is unavailable
    }
}
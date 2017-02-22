package simonlammer.htlleonding.ac.at.openglcameraoverlay;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private CameraPreview cameraPreview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cameraPreview = (CameraPreview) findViewById(R.id.camera_preview);
        //cameraPreview.setCamera(getCameraInstance(this));

        RelativeLayout rl = (RelativeLayout) findViewById(R.id.activity_main);
        CameraPreview cp = new CameraPreview(this);
        cp.setCamera(getCameraInstance(this));
        rl.addView(cp);
    }

    public static Camera getCameraInstance(Context context){
        Camera c = null;
        try {
            c = Camera.open(0); // attempt to get a Camera instance
        }
        catch (Exception e){
            // Camera is not available (in use or does not exist)
            Toast.makeText(context, "Camera is not available", Toast.LENGTH_SHORT);
            throw new IllegalStateException("Camera is not available", e);
        }
        return c; // returns null if camera is unavailable
    }

    @Override
    protected void onDestroy() {
        cameraPreview.onDestroy();
        super.onDestroy();
    }
}

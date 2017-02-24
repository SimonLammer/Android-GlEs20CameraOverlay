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
    private GlOverlay glOverlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CameraPreview.requestCameraPermissions(this);
        setContentView(R.layout.activity_main);

        cameraPreview = (CameraPreview) findViewById(R.id.camera_preview);

        glOverlay = (GlOverlay) findViewById(R.id.overlay);
    }

    @Override
    protected void onDestroy() {
        cameraPreview.onDestroy();
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        glOverlay.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        glOverlay.onResume();
    }
}

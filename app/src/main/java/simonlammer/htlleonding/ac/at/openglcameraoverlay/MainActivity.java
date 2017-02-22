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
    }

    @Override
    protected void onDestroy() {
        cameraPreview.onDestroy();
        super.onDestroy();
    }
}

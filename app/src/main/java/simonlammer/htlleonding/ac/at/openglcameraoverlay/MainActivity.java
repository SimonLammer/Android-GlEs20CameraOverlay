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
    private GlOverlay glOverlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        glOverlay = (GlOverlay) findViewById(R.id.overlay);
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

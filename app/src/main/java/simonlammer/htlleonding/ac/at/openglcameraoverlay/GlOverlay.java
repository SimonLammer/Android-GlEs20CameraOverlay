package simonlammer.htlleonding.ac.at.openglcameraoverlay;

import android.content.Context;
import android.graphics.PixelFormat;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

/**
 * Created by Simon Lammer on 22.02.2017.
 */

public class GlOverlay extends GLSurfaceView {
    private GlEs20Renderer renderer;

    public GlOverlay(Context context) {
        super(context);
        init();
    }

    public GlOverlay(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        this.renderer = new GlEs20Renderer();
        this.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
        this.getHolder().setFormat(PixelFormat.RGBA_8888);
        this.setZOrderOnTop(true);
        this.setEGLContextClientVersion(2);
        this.setRenderer(renderer);
        this.setRenderMode(RENDERMODE_CONTINUOUSLY);
    }
}

package simonlammer.htlleonding.ac.at.openglcameraoverlay;

import android.graphics.Color;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.FloatMath;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Simon Lammer on 22.02.2017.
 */

public class GlEs20Renderer implements GLSurfaceView.Renderer {
    private static final int COORDS_PER_VERTEX = 4;
    private final float[] triangleCoords = {
            -0.5f, -0.5f, 0.0f, 1.0f,
             0.5f, -0.5f, 0.0f, 1.0f,
             0.5f,  0.5f, 0.0f, 1.0f,
    };
    private final int vertexCount = triangleCoords.length / COORDS_PER_VERTEX;
    private final int vertexStride = COORDS_PER_VERTEX * 4; // 4 bytes per float
    private FloatBuffer vertexBuffer;

    private boolean setup = false;
    private int program = -1;
    private int animation = 0;

    private void setup() {
        vertexBuffer = ByteBuffer.allocateDirect(triangleCoords.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        vertexBuffer.put(triangleCoords);
        vertexBuffer.position(0);

        String vertexShaderSource =
                "attribute vec4 vPosition;" +
                "uniform vec4 vTranspose;" +
                "void main() {" +
                "  gl_Position = vPosition + vTranspose;" +
                "}";

        String fragmentShaderSource =
                "precision mediump float;" +
                "uniform vec4 vColor;" +
                "void main() {" +
                "  gl_FragColor = vColor;" +
                "}";


        int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER,
                vertexShaderSource);
        int fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER,
                fragmentShaderSource);

        // create empty OpenGL ES Program
        program = GLES20.glCreateProgram();

        // add the vertex shader to program
        GLES20.glAttachShader(program, vertexShader);

        // add the fragment shader to program
        GLES20.glAttachShader(program, fragmentShader);

        // creates OpenGL ES program executables
        GLES20.glLinkProgram(program);
    }

    private static int loadShader(int type, String shaderCode){

        // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);

        // add the source code to the shader and compile it
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {

    }

    @Override
    public void onDrawFrame(GL10 gl) {
        if (!setup) {
            setup();
            setup = true;
        }

        animation++;

        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        // Add program to OpenGL ES environment
        GLES20.glUseProgram(program);

        // get handle to vertex shader's vPosition member
        int mPositionHandle = GLES20.glGetAttribLocation(program, "vPosition");

        // Enable a handle to the triangle vertices
        GLES20.glEnableVertexAttribArray(mPositionHandle);

        // Prepare the triangle coordinate data
        GLES20.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX,
                GLES20.GL_FLOAT, false,
                vertexStride, vertexBuffer);

        // get handle to fragment shader's vColor member
        int mColorHandle = GLES20.glGetUniformLocation(program, "vColor");

        // Set color for drawing the triangle
        GLES20.glUniform4fv(mColorHandle, 1, new float[] {0.5f, 0.0f, 0.0f, 1.0f}, 0);

        // move
        GLES20.glUniform4fv(GLES20.glGetUniformLocation(program, "vTranspose"), 1, new float[] {(float) Math.sin(animation / 100.0), 0f, 0f, 1f}, 0);

        // Draw the triangle
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);

        // Disable vertex array
        GLES20.glDisableVertexAttribArray(mPositionHandle);
    }
}

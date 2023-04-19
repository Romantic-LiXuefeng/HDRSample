package com.jonanorman.android.hdrsample.player.opengl.env;


import android.opengl.EGL14;
import android.opengl.EGLExt;
import android.opengl.EGLSurface;
import android.view.Surface;

class EnvWindowSurfaceImpl implements GLEnvWindowSurface {


    private final Surface surface;
    private final EGLSurface eglSurface;
    private final GLEnvDisplay envDisplay;
    private final GLEnvConfig envConfig;

    private final int[] surfaceSize = new int[2];
    private boolean release;

    public EnvWindowSurfaceImpl(GLEnvDisplay envDisplay, GLEnvConfig envConfig, Surface surface, GLEnvWindowSurfaceAttrib windowSurfaceAttrib) {
        this.envDisplay = envDisplay;
        this.envConfig = envConfig;
        this.surface = surface;
        eglSurface = EGL14.eglCreateWindowSurface(
                envDisplay.getEGLDisplay(),
                envConfig.getEGLConfig(),
                surface,
                windowSurfaceAttrib.getAttrib(),
                0);
        if (eglSurface == null || eglSurface == EGL14.EGL_NO_SURFACE) {
            GLEnvException.checkAndThrow();
        }
    }


    @Override
    public final int getWidth() {
        if (!release) {
            boolean querySurface = EGL14.eglQuerySurface(envDisplay.getEGLDisplay(), eglSurface, EGL14.EGL_WIDTH, surfaceSize, 0);
            if (!querySurface) {
                GLEnvException.checkAndThrow();
            }
        }
        return surfaceSize[0];
    }

    @Override
    public final int getHeight() {
        if (!release) {
            boolean querySurface = EGL14.eglQuerySurface(envDisplay.getEGLDisplay(), eglSurface, EGL14.EGL_HEIGHT, surfaceSize, 1);
            if (!querySurface) {
                GLEnvException.checkAndThrow();
            }
        }
        return surfaceSize[1];
    }

    @Override
    public void release() {
        if (release) return;
        release = true;
        boolean destroySurface = EGL14.eglDestroySurface(envDisplay.getEGLDisplay(), eglSurface);
        if (!destroySurface) {
            GLEnvException.checkAndThrow();
        }
    }

    @Override
    public boolean isRelease() {
        return release;
    }


    @Override
    public GLEnvDisplay getEnvDisplay() {
        return envDisplay;
    }

    @Override
    public GLEnvConfig getEnvConfig() {
        return envConfig;
    }

    @Override
    public EGLSurface getEGLSurface() {
        return eglSurface;
    }


    @Override
    public void setPresentationTime(long presentationNs) {
        if (release) return;
        boolean presentationTimeANDROID = EGLExt.eglPresentationTimeANDROID(envDisplay.getEGLDisplay(), eglSurface, presentationNs);
        if (!presentationTimeANDROID) {
            GLEnvException.checkAndThrow();
        }
    }


    @Override
    public void swapBuffers() {
        if (release) return;
        boolean swapBuffers = EGL14.eglSwapBuffers(envDisplay.getEGLDisplay(), eglSurface);
        if (!swapBuffers) {
            GLEnvException.checkAndThrow();
        }
    }

    @Override
    public Surface getSurface() {
        return surface;
    }
}

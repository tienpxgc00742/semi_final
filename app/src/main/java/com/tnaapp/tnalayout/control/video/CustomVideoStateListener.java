package com.tnaapp.tnalayout.control.video;

public interface CustomVideoStateListener {

    void onFirstVideoFrameRendered();

    void onPlay();

    void onBuffer();

    boolean onStopWithExternalError(int position);

}

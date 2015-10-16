package com.tnaapp.tnalayout.control.video;

public interface CustomPlayerController {

    void setMediaPlayer(CustomPlayer customPlayer);

    void setEnabled(boolean value);

    void show(int timeInMilliSeconds);

    void show();

    void hide();

    void setVisibilityListener(CustomPlayerControllerVisibilityListener visibilityListener);
}

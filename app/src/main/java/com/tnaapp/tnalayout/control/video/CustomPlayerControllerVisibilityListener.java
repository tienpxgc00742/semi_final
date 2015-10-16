package com.tnaapp.tnalayout.control.video;

/**
 * Called to notify that the control have been made visible or hidden.
 * Implementation might want to show/hide actionbar or do other ui adjustments.
 * <p/>
 * Implementation must be provided via the corresponding setter.
 */
public interface CustomPlayerControllerVisibilityListener {

    void onControlsVisibilityChange(boolean value);

    void requestFullScreen();

}

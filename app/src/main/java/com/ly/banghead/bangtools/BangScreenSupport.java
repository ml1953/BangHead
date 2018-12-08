package com.ly.banghead.bangtools;

import android.content.Context;
import android.graphics.Rect;
import android.view.Window;

import java.util.List;

public interface BangScreenSupport {
    boolean hasNotBangScreen(Window window);

    List<Rect> getBangSize(Window window);


    void extendStatusCutout(Window window, Context context);

    void setWindowLayoutBlockNotch(Window window);

    void transparentStatusCutout(Window window, Context context);

    void fullscreen(Window window, Context context);
}

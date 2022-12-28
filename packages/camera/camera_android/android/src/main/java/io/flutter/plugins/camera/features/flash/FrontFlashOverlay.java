package io.flutter.plugins.camera.features.flash;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

public class FrontFlashOverlay {
    public static final int WARM_WHITE_COLOR = 0xFFFAF1D8;
    private View previewOverlay;
    private final Activity activity;

    public FrontFlashOverlay(final Activity activity) {
        this.activity = activity;
    }

    public void setVisibility(boolean visible) {
        final WindowManager.LayoutParams layout = activity.getWindow().getAttributes();
        if (visible) {
            layout.screenBrightness = WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_FULL;
        } else {
            layout.screenBrightness = WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_NONE;
        }
        activity.runOnUiThread(() -> {
            if (visible) {
                addOverlay();
            } else {
                removeOverlay();
            }
            activity.getWindow().setAttributes(layout);
        });
    }

    private void addOverlay() {
        removeOverlay();
        previewOverlay = new View(activity);
        previewOverlay.setBackgroundColor(WARM_WHITE_COLOR);
        getRootView().addView(previewOverlay, new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    private void removeOverlay() {
        if (previewOverlay != null) {
            getRootView().removeView(previewOverlay);
            previewOverlay = null;
        }
    }

    private ViewGroup getRootView() {
        return activity.findViewById(android.R.id.content);
    }
}

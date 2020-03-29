package com.example.guidedkeyboardfixerlib;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import androidx.leanback.widget.GuidedActionEditText;
import androidx.leanback.widget.ImeKeyMonitor;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.EditText;
import android.widget.TextView;

public class MyEditText extends EditText implements ImeKeyMonitor {
    static final class NoPaddingDrawable extends Drawable {
        @Override
        public boolean getPadding(Rect padding) {
            padding.set(0, 0, 0, 0);
            return true;
        }

        @Override
        public void draw(Canvas canvas) {
        }

        @Override
        public void setAlpha(int alpha) {
        }

        @Override
        public void setColorFilter(ColorFilter colorFilter) {
        }

        @Override
        public int getOpacity() {
            return PixelFormat.TRANSPARENT;
        }
    }

    private ImeKeyListener mKeyListener;
    private final Drawable mSavedBackground;
    private final Drawable mNoPaddingDrawable;

    public MyEditText(Context ctx) {
        this(ctx, null);
    }

    public MyEditText(Context ctx, AttributeSet attrs) {
        this(ctx, attrs, android.R.attr.editTextStyle);
    }

    public MyEditText(Context ctx, AttributeSet attrs, int defStyleAttr) {
        super(ctx, attrs, defStyleAttr);
        mSavedBackground = getBackground();
        mNoPaddingDrawable = new MyEditText.NoPaddingDrawable();
        setBackground(mNoPaddingDrawable);
    }

    @Override
    public void setImeKeyListener(ImeKeyListener listener) {
        mKeyListener = listener;
    }

    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        boolean result = false;
//        if (mKeyListener != null) {
//            result = mKeyListener.onKeyPreIme(this, keyCode, event);
//        }
//        if (!result) {
//            result = super.onKeyPreIme(keyCode, event);
//        }
        return result;
    }

    @Override
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        info.setClassName(isFocused() ? EditText.class.getName() : TextView.class.getName());
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        if (focused) {
            setBackground(mSavedBackground);
        } else {
            setBackground(mNoPaddingDrawable);
        }
        // Make the TextView focusable during editing, avoid the TextView gets accessibility focus
        // before editing started. see also GuidedActionAdapterGroup where setFocusable(true).
        if (!focused) {
            setFocusable(false);
        }
    }
}

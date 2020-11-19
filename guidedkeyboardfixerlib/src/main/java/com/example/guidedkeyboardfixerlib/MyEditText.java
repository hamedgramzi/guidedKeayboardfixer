package com.example.guidedkeyboardfixerlib;

import android.app.Instrumentation;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.leanback.widget.GuidedActionEditText;
import androidx.leanback.widget.ImeKeyMonitor;
import androidx.leanback.widget.VerticalGridView;

import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import java.util.logging.Handler;

public class MyEditText extends AppCompatEditText implements ImeKeyMonitor {
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
    public boolean dispatchTouchEvent(MotionEvent event) {
        try {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                VerticalGridView verticalGridView = ((VerticalGridView) getParent().getParent().getParent());
                int dest = verticalGridView.getChildAdapterPosition((View) getParent().getParent());
                if (verticalGridView.getSelectedPosition() != dest)
                    verticalGridView.setSelectedPositionSmooth(dest);

                requestFocus();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Instrumentation inst = new Instrumentation();
                            inst.sendKeyDownUpSync(KeyEvent.KEYCODE_NUMPAD_ENTER);
                        } catch (Exception e) {
                        }
                    }
                }).start();
            }
        } catch (Exception e) {

        }
        return super.dispatchTouchEvent(event);
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

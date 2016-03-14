package com.example.administrator.thehealthy.util;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/3/8.
 */
public class MarqueueTextView extends TextView {
    public MarqueueTextView(Context context) {
        super(context);
    }

    public MarqueueTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MarqueueTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean isFocused() {
        return true;
    }
}

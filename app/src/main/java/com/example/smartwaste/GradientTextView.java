package com.example.smartwaste;

import android.content.Context;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Shader;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

public class GradientTextView extends AppCompatTextView {

    private LinearGradient gradient;
    private Matrix gradientMatrix;

    public GradientTextView(Context context) {
        super(context);
        init();
    }

    public GradientTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GradientTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        post(() -> {
            int width = getMeasuredWidth();
            int height = getMeasuredHeight();
            if (width == 0) width = 200; // default width if not measured yet

            // Gradient colors
            gradient = new LinearGradient(
                    0, 0, width, 0,
                    new int[]{
                            0xFFE91E63, // Pink
                            0xFFFFC107, // Amber
                            0xFF4CAF50, // Green
                            0xFF03A9F4, // Blue
                            0xFF9C27B0  // Purple
                    },
                    null,
                    Shader.TileMode.CLAMP
            );

            gradientMatrix = new Matrix();
            getPaint().setShader(gradient);
            invalidate();
        });
    }
}

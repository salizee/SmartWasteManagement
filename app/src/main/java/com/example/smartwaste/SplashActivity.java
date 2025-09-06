package com.example.smartwaste;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Shader;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.RotateAnimation;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private TextView title;
    private LinearGradient gradient;
    private Matrix gradientMatrix;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        title = findViewById(R.id.appName);
        ImageView logo = findViewById(R.id.logo);

        // Fade animation
        AlphaAnimation fade = new AlphaAnimation(0f, 1f);
        fade.setDuration(800);
        logo.startAnimation(fade);
        title.startAnimation(fade);

        // Logo spin animation
        RotateAnimation rotate = new RotateAnimation(
                0, 360,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f
        );
        rotate.setDuration(2500); // full spin in 2.5s
        rotate.setInterpolator(new LinearInterpolator());
        rotate.setRepeatCount(RotateAnimation.INFINITE);
        logo.startAnimation(rotate);

        // Gradient text animation
        title.post(() -> {
            int width = title.getMeasuredWidth();
            int height = title.getMeasuredHeight();

            gradient = new LinearGradient(
                    0, 0, width, height,
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
            title.getPaint().setShader(gradient);
            gradientMatrix = new Matrix();

            ValueAnimator animator = ValueAnimator.ofFloat(0, width);
            animator.setDuration(2500);
            animator.setRepeatCount(ValueAnimator.INFINITE);
            animator.addUpdateListener(animation -> {
                float translateX = (float) animation.getAnimatedValue();
                gradientMatrix.setTranslate(translateX, 0);
                gradient.setLocalMatrix(gradientMatrix);

                // Pulsing effect
                float scale = 1f + 0.1f * (float) Math.sin(System.currentTimeMillis() * 0.005);
                title.setScaleX(scale);
                title.setScaleY(scale);

                title.invalidate();
            });
            animator.start();
        });

        // Navigate to LoginActivity after 2.5 seconds
        title.postDelayed(() -> {
            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            finish();
        }, 2500);
    }
}

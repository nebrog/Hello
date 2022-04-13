package com.example.hello;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private View view;
    private TextView textView;
    private ObjectAnimator animator;
    private ObjectAnimator animatorUp;
    private ObjectAnimator animatorDown;
    private static final int ANIMATION_DURATION = 5000;
    private static final int ANIMATION_START_DELAY = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);
        view = findViewById(R.id.mainView);
        setupAnimators();
        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animator.pause();
                animatorDown.pause();
                animatorUp.pause();
            }
        };
        textView.setOnClickListener(clickListener);

        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float x = event.getX();
                float y = event.getY();


                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    textView.setX(x - textView.getWidth() / 2f);
                    textView.setY(y - textView.getHeight() / 2f);
                    textView.setTextColor(getResources().getColor(R.color.color));
                    animator.setFloatValues(y - textView.getHeight() / 2f, view.getHeight() - textView.getHeight());
                    animatorUp.setFloatValues(view.getHeight() - textView.getHeight(), 0);
                    animatorDown.setFloatValues(0, view.getHeight() - textView.getHeight());
                    animator.pause();
                    animatorDown.pause();
                    animatorUp.pause();
                    animator.setStartDelay(ANIMATION_START_DELAY);
                    animator.start();
                    return true;
                }
                return false;
            }
        });

    }

    private void setupAnimators() {
        animator = ObjectAnimator.ofFloat(textView, View.Y, 0);
        animatorUp = ObjectAnimator.ofFloat(textView, View.Y, 0);
        animatorDown = ObjectAnimator.ofFloat(textView, View.Y, 0);

        animator.setDuration(ANIMATION_DURATION);
        animatorUp.setDuration(ANIMATION_DURATION);
        animatorDown.setDuration(ANIMATION_DURATION);


        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                animatorUp.start();
            }
        });
        animatorUp.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                animatorDown.start();
            }
        });
        animatorDown.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                animatorUp.start();
            }
        });
    }
}
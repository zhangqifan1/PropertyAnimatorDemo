package com.example.anadministrator.propertyanimatordemo;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.graphics.PointF;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mImage;
    private Button mButAlpha;
    private Button mButScale;
    private Button mButTranslatatin;
    private Button mBuRotation;
    private Button mButSR;
    private Button mButRT;
    private Button mButAlphaRepeat;
    private Button mBuBGcolor;
    private Button mButPaoWuxian;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mImage = (ImageView) findViewById(R.id.image);
        mButAlpha = (Button) findViewById(R.id.butAlpha);
        mButAlpha.setOnClickListener(this);
        mButScale = (Button) findViewById(R.id.butScale);
        mButScale.setOnClickListener(this);
        mButTranslatatin = (Button) findViewById(R.id.butTranslatatin);
        mButTranslatatin.setOnClickListener(this);
        mBuRotation = (Button) findViewById(R.id.buRotation);
        mBuRotation.setOnClickListener(this);
        mButSR = (Button) findViewById(R.id.butSR);
        mButSR.setOnClickListener(this);
        mButRT = (Button) findViewById(R.id.butRT);
        mButRT.setOnClickListener(this);
        mButAlphaRepeat = (Button) findViewById(R.id.butAlphaRepeat);
        mButAlphaRepeat.setOnClickListener(this);
        mBuBGcolor = (Button) findViewById(R.id.buBGcolor);
        mBuBGcolor.setOnClickListener(this);
        mButPaoWuxian = (Button) findViewById(R.id.butPaoWuxian);
        mButPaoWuxian.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.butAlpha://透明
                ObjectAnimator
                        .ofFloat(mImage, "alpha", 0.0f, 1.0f)
                        .setDuration(1000)
                        .start();

                break;
            case R.id.butScale://缩放   同时播放俩种 XY并存的都可以这样写
                ObjectAnimator
                        .ofFloat(mImage, "scaleX", 0.0f, 1.0f)
                        .setDuration(1000)
                        .start();
                break;
            case R.id.butTranslatatin://位移
                PropertyValuesHolder translationX = PropertyValuesHolder.ofFloat("translationX", 20f, 80f);
                PropertyValuesHolder translationY = PropertyValuesHolder.ofFloat("translationY", 20f, 80f);
                ObjectAnimator
                        .ofPropertyValuesHolder(mImage, translationX, translationY)
                        .setDuration(1000)
                        .start();
                break;
            case R.id.buRotation://旋转
                ObjectAnimator
                        .ofFloat(mImage,"rotationY",0.0f,360f)
                        .setDuration(1000)
                        .start();
                break;
            case R.id.butSR://先播放缩放动画，完成后播放旋转动画
                AnimatorSet animatorSetGroup1 = new AnimatorSet();
                ObjectAnimator objectAnimatorScaleX1 = ObjectAnimator.ofFloat(mImage, "scaleX", 0f, 1f);
                ObjectAnimator objectAnimatorScaleY1 = ObjectAnimator.ofFloat(mImage, "scaleY", 0f, 1f);
                ObjectAnimator objectAnimatorRotateX1 = ObjectAnimator.ofFloat(mImage, "rotationX", 0f, 360f);
                ObjectAnimator objectAnimatorRotateY1 = ObjectAnimator.ofFloat(mImage, "rotationY", 0f, 360f);
                animatorSetGroup1.setDuration(1000);
                animatorSetGroup1.play(objectAnimatorScaleX1).with(objectAnimatorScaleY1)
                        .before(objectAnimatorRotateX1).before(objectAnimatorRotateY1);
                animatorSetGroup1.start();
                break;
            case R.id.butRT://先播放旋转动画，完成后播放位移动画
                AnimatorSet animatorSetGroup2 = new AnimatorSet();
                ObjectAnimator objectAnimatorTranslate2 = ObjectAnimator.ofFloat(mImage, "translationX", 0f, 500f);
                ObjectAnimator objectAnimatorRotateX2 = ObjectAnimator.ofFloat(mImage, "rotationX", 0f, 360f);
                ObjectAnimator objectAnimatorRotateY2 = ObjectAnimator.ofFloat(mImage, "rotationY", 0f, 360f);
                animatorSetGroup2.setDuration(1000);
                animatorSetGroup2.play(objectAnimatorTranslate2).after(objectAnimatorRotateX2)
                        .after(objectAnimatorRotateY2);
                animatorSetGroup2.start();
                break;
            case R.id.butAlphaRepeat://重复的透明度动画  闪烁效果
                //TODO
                ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(mImage, "alpha", 0f, 1f);
                objectAnimator2.setDuration(500);
                objectAnimator2.setRepeatCount(3);
                objectAnimator2.start();
                break;
            case R.id.buBGcolor://背景颜色不断改变
                //TODO
                ObjectAnimator objectAnimatorBg = ObjectAnimator.ofInt(mImage, "backgroundColor", Color.BLUE, Color.YELLOW, Color.RED);
                objectAnimatorBg.setDuration(3000);
                objectAnimatorBg.start();
                break;
            case R.id.butPaoWuxian://背景颜色不断改变
                //TODO
                parabola();
                break;
            default:
                break;
        }
    }
    /**
     * 抛物线动画
     */
    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    public void parabola()
    {

        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setDuration(3000);
        valueAnimator.setObjectValues(new PointF(0, 0));
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setEvaluator(new TypeEvaluator<PointF>()
        {

            @Override
            public PointF evaluate(float fraction, PointF startValue,
                                   PointF endValue)
            {
                /**x方向200px/s ，则y方向0.5 * 200 * t**/
                PointF point = new PointF();
                point.x = 200 * fraction * 3;
                point.y = 0.5f * 200 * (fraction * 3) * (fraction * 3);
                return point;
            }
        });

        valueAnimator.start();
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
        {
            @Override
            public void onAnimationUpdate(ValueAnimator animation)
            {
                PointF point = (PointF) animation.getAnimatedValue();
                mImage.setX(point.x);
                mImage.setY(point.y);

            }
        });
    }
}

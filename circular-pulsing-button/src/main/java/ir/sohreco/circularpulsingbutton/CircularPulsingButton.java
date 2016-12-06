package ir.sohreco.circularpulsingbutton;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.TextView;

public class CircularPulsingButton extends FrameLayout {
    private class Circle extends View {
        private Paint paint;

        private Circle(Context context) {
            super(context);
            paint = new Paint();
            paint.setColor(Color.GRAY);
            paint.setAntiAlias(true);
        }

        private void setColor(int color) {
            paint.setColor(color);
            invalidate();
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            float cx = canvas.getWidth() / 2;
            float cy = canvas.getHeight() / 2;
            // Circle must be 20% smaller than button's size.
            float radius = cx - (cx * 20) / 100;
            canvas.drawCircle(cx, cy, radius, paint);
        }
    }

    private Circle backgroundCircle;
    private TextView tvButtonText;
    private float zoomOutScale;

    public CircularPulsingButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context, attrs);
    }

    public CircularPulsingButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CircularPulsingButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initialize(context, attrs);
    }

    private void initialize(Context context, AttributeSet attrs) {
        tvButtonText = new TextView(context);
        addBackgroundCircle();
        TypedArray arr = context.obtainStyledAttributes(attrs, R.styleable.CircularPulsingButton);
        setColor(arr.getColor(R.styleable.CircularPulsingButton_cpb_color, Color.GRAY));
        tvButtonText.setText(arr.getString(R.styleable.CircularPulsingButton_cpb_text));
        tvButtonText.setTextColor(arr.getColor(R.styleable.CircularPulsingButton_cpb_textColor, Color.BLACK));
        setZoomOutScale(arr.getFloat(R.styleable.CircularPulsingButton_cpb_zoomOutScale, 0.5f));
        arr.recycle();
    }

    /**
     * @param scale The value must be between 0 and 1.
     */
    public void setZoomOutScale(float scale) {
        if (scale >= 1) {
            zoomOutScale = 0.9f;
        } else if (scale <= 0) {
            zoomOutScale = 0.1f;
        } else {
            zoomOutScale = scale;
        }
    }

    /**
     * Set the color of this button.
     *
     * @param color Color code
     */
    public void setColor(int color) {
        backgroundCircle.setColor(color);
    }

    public void setText(@StringRes int resId) {
        tvButtonText.setText(resId);
    }

    /**
     * Set the string value of this button's TextView.
     */
    public void setText(String text) {
        tvButtonText.setText(text);
    }

    /**
     * Set the color of this button's text.
     *
     * @param color Color code
     */
    public void setTextColor(int color) {
        tvButtonText.setTextColor(color);
    }

    public String getText() {
        return tvButtonText.getText().toString();
    }

    public float getZoomOutScale() {
        return zoomOutScale;
    }

    private void addBackgroundCircle() {
        backgroundCircle = new Circle(getContext());
        backgroundCircle.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        scaleView(backgroundCircle, 1, zoomOutScale);
                        scaleView(tvButtonText, 1, zoomOutScale);
                        break;

                    case MotionEvent.ACTION_UP:
                        scaleView(backgroundCircle, zoomOutScale, 1f);
                        scaleView(tvButtonText, zoomOutScale, 1f);
                        scaleView(backgroundCircle, 1.2f, 1f);
                        scaleView(tvButtonText, 1.2f, 1f);
                        break;
                }
                return true;
            }
        });
        addView(backgroundCircle);
        backgroundCircle.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                addButtonTextView();
                backgroundCircle.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    private void addButtonTextView() {
        int width = backgroundCircle.getWidth() - (backgroundCircle.getWidth() * 35 / 100);
        int height = backgroundCircle.getHeight() - (backgroundCircle.getHeight() * 50 / 100);
        LayoutParams params = new LayoutParams(width, height, Gravity.CENTER);
        addView(tvButtonText, params);
        tvButtonText.setGravity(Gravity.CENTER);
    }

    private void scaleView(View v, float startScale, float endScale) {
        Animation anim = new ScaleAnimation(
                startScale, endScale,
                startScale, endScale,
                Animation.ABSOLUTE, v.getWidth() / 2, // Pivot point of X scaling
                Animation.ABSOLUTE, v.getHeight() / 2); // Pivot point of Y scaling
        anim.setFillAfter(true);
        anim.setDuration(300);
        v.startAnimation(anim);
    }
}

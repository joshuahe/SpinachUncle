package cn.com.spinachzzz.spinachuncle.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.util.FloatMath;
import android.util.Log;
import android.view.MotionEvent;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import cn.com.spinachzzz.spinachuncle.ImageViewActivity;

public class TouchImageView extends ImageView {
    protected static final String TAG = TouchImageView.class.getSimpleName();

    private int screenW;
    private int screenH;
    private float screenWidthHeight;
    private float widthHeight;

    private TouchMode touchMode;

    private float beforeLenght;
    private float afterLenght;
    private float scale = 0.04f;

    private int stopX;
    private int stopY;
    private int startX;
    private int startY;

    private long lastClick;

    private TranslateAnimation trans;

    public TouchImageView(Context context, AttributeSet attrs) {
	super(context, attrs);
    }

    public void setScreenW(int screenW) {
	this.screenW = screenW;
    }

    public void setScreenH(int screenH) {
	this.screenH = screenH;
    }

    @SuppressLint("NewApi")
    @Override
    public void setImageBitmap(Bitmap bm) {
	super.setImageBitmap(bm);
	this.widthHeight = (float) bm.getWidth() / (float) bm.getHeight();
	this.screenWidthHeight = (float) screenW / (float) screenH;

    }

    public int getScreenW() {
	return screenW;
    }

    public int getScreenH() {
	return screenH;
    }

    public void fadeIn(boolean right) {

	int dis = right ? screenW : -screenW;
	this.setPosition(dis, this.getTop());
	trans = new TranslateAnimation(dis, 0, 0, 0);
	trans.setDuration(500);
	this.startAnimation(trans);
    }

    public void fadeOut(boolean left) {

	int dis = left ? -this.getWidth() : this.getWidth();
	trans = new TranslateAnimation(0, dis, 0, 0);
	trans.setDuration(100);
	this.setPosition(dis, this.getTop());
	this.startAnimation(trans);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
	switch (event.getAction() & MotionEvent.ACTION_MASK) {
	case MotionEvent.ACTION_DOWN:
	    touchMode = TouchMode.DRAG;
	    stopX = (int) event.getRawX();
	    stopY = (int) event.getRawY();
	    startX = (int) event.getX();
	    startY = stopY - this.getTop();
	    if (event.getPointerCount() == 2)
		beforeLenght = spacing(event);

	    long currentClick = System.currentTimeMillis();
	    if (currentClick - lastClick < 500) {
		zoomReset(event);
	    }
	    lastClick = currentClick;
	    break;
	case MotionEvent.ACTION_POINTER_DOWN:
	    if (spacing(event) > 10f) {
		touchMode = TouchMode.ZOOM;
		beforeLenght = spacing(event);
	    }
	    break;

	case MotionEvent.ACTION_UP:
	    if (this.getLeft() < 0 && this.getRight() < screenW - 40) {
		ImageViewActivity activity = (ImageViewActivity) this
			.getContext();
		activity.displayNextImage();
	    }

	    else if (this.getRight() > screenW && this.getLeft() > 40) {
		ImageViewActivity activity = (ImageViewActivity) this
			.getContext();
		activity.displayPrevImage();
	    }

	    touchMode = TouchMode.NONE;
	    break;
	case MotionEvent.ACTION_POINTER_UP:
	    touchMode = TouchMode.NONE;
	    break;
	case MotionEvent.ACTION_MOVE:
	    if (touchMode == TouchMode.DRAG) {
		if (Math.abs(stopX - startX - getLeft()) < 88
			&& Math.abs(stopY - startY - getTop()) < 85) {
		    this.setPosition(stopX - startX, stopY - startY);
		    stopX = (int) event.getRawX();
		    stopY = (int) event.getRawY();
		} else {
		    Log.i(TAG, "Big move!");
		}
	    } else if (touchMode == TouchMode.ZOOM) {
		if (spacing(event) > 10f) {
		    afterLenght = spacing(event);
		    float gapLenght = afterLenght - beforeLenght;
		    if (gapLenght == 0) {
			break;
		    } else if (Math.abs(gapLenght) > 5f) {
			if (gapLenght > 0) {
			    this.setScale(scale, ScaleMode.BIGGER);
			} else {
			    this.setScale(scale, ScaleMode.SMALLER);
			}
			beforeLenght = afterLenght;
		    }
		}
	    }
	    break;
	}
	return true;
    }

    private void setScale(float temp, ScaleMode scaleMode) {

	if (scaleMode == ScaleMode.BIGGER) {
	    this.setFrame(this.getLeft() - (int) (temp * this.getWidth()),
		    this.getTop() - (int) (temp * this.getHeight()),
		    this.getRight() + (int) (temp * this.getWidth()),
		    this.getBottom() + (int) (temp * this.getHeight()));
	} else if (scaleMode == ScaleMode.SMALLER) {
	    this.setFrame(this.getLeft() + (int) (temp * this.getWidth()),
		    this.getTop() + (int) (temp * this.getHeight()),
		    this.getRight() - (int) (temp * this.getWidth()),
		    this.getBottom() - (int) (temp * this.getHeight()));
	}
    }

    private void zoomReset(MotionEvent event) {
	if (widthHeight >= screenWidthHeight) {
	    if (this.getHeight() >= screenH) {
		zoomReset();

	    } else {
		float changeScale = (float) screenH / (float) this.getHeight();
		int leftDis = (int) (event.getX() * changeScale);
		int newWeight = (int) (screenH * widthHeight);
		this.setFrame((int) event.getRawX() - leftDis, 0,
			(int) event.getRawX() - leftDis + newWeight, screenH);
	    }
	} else {
	    if (this.getWidth() >= screenW) {
		zoomReset();
	    } else {
		float changeScale = (float) screenW / (float) this.getWidth();
		int topDis = (int) (event.getY() * changeScale);
		int newHeight = (int) (screenW / widthHeight);
		this.setFrame(0, ((int) event.getRawY() - topDis), screenW,
			((int) event.getRawY() - topDis) + newHeight);

		this.getParent();
	    }
	}
	Animation alphaAnimation = new AlphaAnimation(0.1f, 1.0f);
	alphaAnimation.setDuration(500);
	this.startAnimation(alphaAnimation);
    }

    private void zoomReset() {
	if (widthHeight >= screenWidthHeight) {
	    int newHeight = (int) (screenW / widthHeight);
	    this.setFrame(0, (screenH - newHeight) / 2, screenW,
		    (screenH + newHeight) / 2);

	} else {
	    int newWidth = (int) (screenH * widthHeight);
	    this.setFrame((screenW - newWidth) / 2, 0,
		    (screenW + newWidth) / 2, screenH);

	}
    }

    private void setPosition(int left, int top) {
	int right = left + this.getWidth();
	int bottom = top + this.getHeight();
	this.layout(left, top, right, bottom);
    }

    private float spacing(MotionEvent event) {
	float x = event.getX(0) - event.getX(1);
	float y = event.getY(0) - event.getY(1);
	return FloatMath.sqrt(x * x + y * y);
    }
}

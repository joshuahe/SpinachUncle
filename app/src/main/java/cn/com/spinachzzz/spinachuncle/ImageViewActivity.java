package cn.com.spinachzzz.spinachuncle;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView.ScaleType;
import android.widget.Toast;
import cn.com.spinachzzz.spinachuncle.view.TouchImageView;

public class ImageViewActivity extends Activity {
    private static final String TAG = "ImageViewActivity";

    private TouchImageView imageView;

    private String[] pics;

    private int index;

    private Bitmap originalBmp;
    
    private Bitmap initBmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);

	this.setContentView(R.layout.image_view);
	
	initBmp = BitmapFactory.decodeResource(this.getResources(),
		    R.drawable.logo);

	imageView = (TouchImageView) this.findViewById(R.id.img);
	int screenW = this.getWindowManager().getDefaultDisplay().getWidth();
	int screenH = this.getWindowManager().getDefaultDisplay().getHeight();
	imageView.setScreenW(screenW);
	imageView.setScreenH(screenH);
	imageView.setScaleType(ScaleType.FIT_CENTER);
	
	
	imageView.setDrawingCacheEnabled(true);

	Intent intent = this.getIntent();

	Bundle bundle = intent.getExtras();
	pics = (String[]) bundle.get(Constants.PICS);

	if (pics == null || pics.length == 0) {
	    Toast.makeText(this, R.string.no_pictures_found, Toast.LENGTH_LONG).show();
	  

	} else {
	    displayCurrImage();
	}
	
	
    }

    private void displayCurrImage() {
	imageView.setImageBitmap(initBmp);
	
	BitmapFactory.Options op = new BitmapFactory.Options();
	op.inSampleSize = 1;
	originalBmp = BitmapFactory.decodeFile(pics[index],op);

	if (originalBmp == null) {
	    Toast toast = Toast.makeText(this, "Not able to open:"
		    + pics[index], Toast.LENGTH_LONG);
	    toast.show();
	    originalBmp = BitmapFactory.decodeResource(this.getResources(),
		    R.drawable.error);
	}
	
	imageView.destroyDrawingCache();
	float widthHeight = (float) originalBmp.getWidth()
		/ (float) originalBmp.getHeight();
	float screenWidthHeight = (float) imageView.getScreenW()
		/ (float) imageView.getScreenH();
	if (widthHeight >= screenWidthHeight) {
	    int newHeight = (int) (imageView.getScreenW() / widthHeight);
	    imageView.getLayoutParams().width = imageView.getScreenW();
	    imageView.getLayoutParams().height = newHeight;

	} else {
	    int newWidth = (int) (imageView.getScreenH() * widthHeight);
	    imageView.getLayoutParams().width = newWidth;
	    imageView.getLayoutParams().height = imageView.getScreenH();

	}
	
	imageView.setImageBitmap(originalBmp);	
    }

    public void displayNextImage() {
	nextIndex();
	Log.i(TAG, "Index:" + index);
	imageView.fadeOut(true);
	displayCurrImage();
	imageView.fadeIn(true);
    }

    public void displayPrevImage() {
	prewIndex();
	imageView.fadeOut(false);
	displayCurrImage();
	imageView.fadeIn(false);
    }

    private void nextIndex() {
	index++;
	if (index >= pics.length) {
	    index = 0;
	}
    }

    private void prewIndex() {
	index--;
	if (index < 0) {
	    index = pics.length - 1;
	}
    }

    @Override
    protected void onDestroy() {
	super.onDestroy();
	Log.i(TAG, "onDestroy");
    }

    @Override
    public void onLowMemory() {
	super.onLowMemory();
	Log.i(TAG, "onLowMemory");
    }

    @Override
    protected void onRestart() {
	super.onRestart();
	Log.i(TAG, "onRestart");
    }

    @Override
    protected void onStart() {
	super.onStart();
	Log.i(TAG, "onStart");
    }

    @Override
    protected void onStop() {
	super.onStop();
	Log.i(TAG, "onStop");
    }

    @SuppressLint("NewApi")
    @Override
    public void onTrimMemory(int level) {
	super.onTrimMemory(level);
	Log.i(TAG, "onTrimMemory");
    }

    @SuppressLint("NewApi")
    @Override
    public void recreate() {
	super.recreate();
	Log.i(TAG, "recreate");
    }
    
    

}

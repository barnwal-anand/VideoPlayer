package com.example.videoplayer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class AndroidCamera extends Activity implements SurfaceHolder.Callback {
	View mView;
	Camera mCamera;
	SurfaceView mSurfaceView;
	SurfaceHolder mSurfaceHolder;
	boolean mPreviewing = false;
	LayoutInflater controlInflater = null;
	private ImageView mImage;
	private Bitmap mBitmap;
	/** Called when the activity is first created. */
	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		getWindow().setFormat(PixelFormat.UNKNOWN);
		mSurfaceView = (SurfaceView) findViewById(R.id.camerapreview);
		mSurfaceHolder = mSurfaceView.getHolder();
		mSurfaceHolder.addCallback(this);
		mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

		controlInflater = LayoutInflater.from(getBaseContext());
		View viewControl = controlInflater.inflate(R.layout.control, null);
		LayoutParams layoutParamsControl = new LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		this.addContentView(viewControl, layoutParamsControl);

		Button buttonTakePicture = (Button) findViewById(R.id.takepicture);
		buttonTakePicture.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				mCamera.takePicture(myShutterCallback, myPictureCallback_RAW,
						myPictureCallback_JPG);
			}
		});
	}

	private File getDir() {
		File sdDir = Environment
				.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
		return new File(sdDir, "CameraAPIDemo");
	}

	ShutterCallback myShutterCallback = new ShutterCallback() {

		@Override
		public void onShutter() {
			// TODO Auto-generated method stub

		}
	};

	PictureCallback myPictureCallback_RAW = new PictureCallback() {

		@Override
		public void onPictureTaken(byte[] arg0, Camera arg1) {
			// TODO Auto-generated method stub

		}
	};

	PictureCallback myPictureCallback_JPG = new PictureCallback() {

		@Override
		public void onPictureTaken(byte[] data, Camera camera) {

			// File pictureFileDir = getExternalFilesDir();
			File folder = new File(Environment.getExternalStorageDirectory()
					+ "/ImageScanner");
			if (!folder.exists()) {
				folder.mkdir();
			}
			String mFileName = Environment.getExternalStorageDirectory()
					.getAbsolutePath();

			

			mFileName += "/ImageScanner/" + "image.jpg";
			
			File pictureFile = new File(mFileName);

			try {
				FileOutputStream fos = new FileOutputStream(pictureFile);
				fos.write(data);
				fos.close();
				//Toast.makeText(getBaseContext(),"New Image saved:" + mFileName, Toast.LENGTH_LONG).show();
			} catch (Exception error) {
				// Log.d(MakePhotoActivity.DEBUG_TAG, "File" + filename+
				// "not saved: " + error.getMessage());
				Toast.makeText(getBaseContext(), "Image could not be saved.",
						Toast.LENGTH_LONG).show();
			}
			
			//check if image exists
			if(pictureFile.exists())
			{
				//get image
				mBitmap = BitmapFactory.decodeFile(pictureFile.getAbsolutePath());
				mImage = (ImageView) findViewById(R.id.imageView1);
				mImage.setImageBitmap(mBitmap); //add image to ImageView
			}            

			Bitmap croppedBmp = Bitmap.createBitmap(mBitmap, 0, 0, mBitmap.getWidth(), mBitmap.getHeight()-30);
            mImage.setImageBitmap(croppedBmp);
 
            Toast.makeText(getBaseContext(),"Video is loading.\nPlease wait...", Toast.LENGTH_LONG).show();
            for(int i = 0; i < 100000; i++);
            Intent intent = new Intent(AndroidCamera.this, PlayVideo.class);
            startActivity(intent);
            //finish();
		}

	};

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		if (mPreviewing) {
			mCamera.stopPreview();
			mPreviewing = false;
		}

		if (mCamera != null) {
			try {
				mCamera.setPreviewDisplay(mSurfaceHolder);
				mCamera.startPreview();
				mPreviewing = true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		mCamera = Camera.open();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		mCamera.stopPreview();
		mCamera.release();
		mCamera = null;
		mPreviewing = false;
	}

}

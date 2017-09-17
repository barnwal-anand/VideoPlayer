package com.example.videoplayer;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

/*Imports and package declarations omitted*/
public class PlayVideo extends Activity {
	private VideoView vView;
	private Button mButtonBack;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// sets the Bundle
		super.onCreate(savedInstanceState);
		// sets the context
		setContentView(R.layout.video_play);
		mButtonBack = (Button)findViewById(R.id.backtocapture);
		mButtonBack.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				//if(view != mButtonBack)
				//{
					Intent intent = new Intent(getBaseContext(), AndroidCamera.class);
					startActivity(intent);
				//}
			}
		});
		
			
			// get the VideoView from the layout file
		vView = (VideoView) findViewById(R.id.vview);

		// use this to get touch events
		vView.requestFocus();

		/*
		 * This is where you put the code that loads the video file
		 */
		// ...
		String vSource = "http://192.168.44.1/video_airtel.3gp";
		// set the video URI, passing the vSourse as a URI
		vView.setVideoURI(Uri.parse(vSource));

		// enable this if you want to enable video controllers, such as pause
		// and forward
		vView.setMediaController(new MediaController(this));
		// plays the movie
		vView.start();
	}

}
package transparent.iconback.nitin.activity;

import transparent.iconback.nitin.R;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Window;

public class AboutDev extends SherlockActivity {

	private ImageButton
    twitter,
    gplus;
	
	// This creates your About Dev Activity
	@Override
	  public void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  noTitle();
	  setContentView(R.layout.about_dev);  
      
      Typeface font1 = Typeface.createFromAsset(getAssets(), "RobotoSlab-Regular.ttf");
      TextView txt1 = (TextView) findViewById(R.id.devFont);
      txt1.setTypeface(font1); 
      TextView title1 = (TextView) findViewById(R.id.title1);
      title1.setTypeface(font1); 
      TextView desc1 = (TextView) findViewById(R.id.description1);
      desc1.setTypeface(font1); 
      TextView title2 = (TextView) findViewById(R.id.title2);
      title2.setTypeface(font1); 
      TextView desc2 = (TextView) findViewById(R.id.description2);
      desc2.setTypeface(font1); 

      gplus = (ImageButton) findViewById(R.id.gplus_button);
      gplus.setOnClickListener(new OnClickListener() {
              public void onClick(View v) {
              	try {
              		final Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://plus.google.com/u/0/+NitinkumarChobhe/about"));
              		startActivity(intent);
              	} 
              	catch (RuntimeException gp) {
              		gp.printStackTrace();
              	}	
              }
      });
      
      twitter = (ImageButton) findViewById(R.id.twitter_button);
      twitter.setOnClickListener(new OnClickListener() {
              public void onClick(View v) {
              	try {
              		final Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://twitter.com/nitin_chobhe"));
              		startActivity(intent);
              	} 
              	catch (RuntimeException tw) {
              		tw.printStackTrace();
              	}	
              }
          });
}

	// Hides the title bar
	public void noTitle() {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}
	
	// This will return the Activity to the Main Screen when the app is in a Paused (not used) state
	@Override
	  public void onPause(){
		  super.onPause();
		  finish();
	  }
}

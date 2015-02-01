/*
 * Copyright 2013 the1dynasty
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package transparent.iconback.nitin.activity;

import java.util.List;


import transparent.iconback.nitin.R;
import transparent.iconback.nitin.fragment.MainFragment;
import transparent.iconback.nitin.util.GlassActionBarHelper;
import transparent.iconback.nitin.util.Utils;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;


/** 
 ** Some lines may be off a few numbers
 ** Just be sure you're in the general area
 **/


public class MainActivity extends SherlockFragmentActivity {
	
	private SharedPreferences prefs;
	private GlassActionBarHelper helper;

	boolean doubleBackToExitPressedOnce = false;
	
	// Starts the Activity for the gridview
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		prefs = getSharedPreferences(getResources().getString(R.string.theme_name), 0);
		checkBuild();

		helper = new GlassActionBarHelper().contentLayout(R.layout.gridview_main);
		setContentView(helper.createView(this));
		
		getSupportActionBar().setDisplayShowHomeEnabled(true); // Set this to false to hide AB Icon
		getSupportActionBar().setDisplayShowTitleEnabled(true); // Set this to false to hide AB Title
		
		getSupportFragmentManager().beginTransaction()
		.replace(R.id.container, new MainFragment())
		.commit();
	}

	/************************************************************************
	 ******************** This is your Changelog Stuff **********************
	 ************************************************************************/
	public void checkBuild() {
	  int buildNum = prefs.getInt("Build Number", 1);
	  int currentVersion = 0;
	  
	  try {
	    currentVersion = getPackageManager()
	    		.getPackageInfo(getPackageName(), 0).versionCode;
	  }
	  catch (NameNotFoundException e) {
	    e.printStackTrace();
	  }
	    if(currentVersion > buildNum) {
	    	  getChangelog().show();
	    	  Editor editor = prefs.edit();
	    	  editor.putInt("Build Number", currentVersion);
	    	  editor.commit();
	    	}
	  }
	
	public Dialog getChangelog()
	 {
	 	final Dialog CDialog = new Dialog(MainActivity.this);
	 	CDialog.setTitle(getResources().getString(R.string.changelog_title));
	 	CDialog.setContentView(R.layout.changelog);
	 	CDialog.setCanceledOnTouchOutside(true);
	 	CDialog.setCancelable(true);
	 	 
	 	Button Close = (Button) CDialog.findViewById(R.id.close);
	 	Close.setOnClickListener(new View.OnClickListener()
	 	{
	 	 @Override
	 	 public void onClick(View v)
	 	 {
	 	 CDialog.dismiss();
	 	 }
	 	});
	 	 
	 	return CDialog;
	 }

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
        int itemId = item.getItemId();
		if (itemId == R.id.shareButton) {
			Intent shareIntent = new Intent(Intent.ACTION_SEND);
			shareIntent.setType("text/plain");
			shareIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.app_link));
			startActivity(Intent.createChooser(shareIntent, "Share Via"));
		} else if (itemId == R.id.rateButton) {
			Intent rate = new Intent(Intent.ACTION_VIEW).setData(Uri.parse
					(getResources().getString(R.string.market_rate)));
			startActivity(rate);
		} else if (itemId == R.id.emailButton) {
			Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
			emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] 
					{getResources().getString(R.string.email_address)});
			emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, 
					getResources().getText(R.string.email_subject));
			emailIntent.setType("plain/text");
			startActivity(Intent.createChooser(emailIntent, "Contact Developer"));
		} else if (itemId == R.id.aboutButton) {
			Intent about = new Intent(MainActivity.this, AboutDev.class);
			startActivity(about);
		}
        
        return true;
	}

	@Override
    public void onBackPressed() {
		if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, getResources().getString(R.string.back_exit), Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
             doubleBackToExitPressedOnce=false;   

            }
        }, 2000);
    }
}

package com.fileexplorer;

import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.android.AndroidAuthSession;
import com.dropbox.client2.session.AccessTokenPair;
import com.dropbox.client2.session.AppKeyPair;
import com.fileexplorer.adapter.Listadapter;
import com.fileexplorer.fragments.LeftFragment.OnHeadlineSelectedListener;
import com.fileexplorer.fragments.RightFragment;
import com.fileexplorer.fragments.RightFragment.RightItemSelectedListener;

public class MainActivity extends FragmentActivity implements
		OnHeadlineSelectedListener,RightItemSelectedListener {
	RightFragment rfragment;
	private static boolean gridView = false;
	// dropbox
	final static private String APP_KEY = "0mvt99wtoimz9fz";
	final static private String APP_SECRET = "5su2k3up7zlvbgu";
	final static private String ACCOUNT_PREFS_NAME = "prefs";
	final static private String ACCESS_KEY_NAME = "ACCESS_KEY";
	final static private String ACCESS_SECRET_NAME = "ACCESS_SECRET";
	final static private String TAG = "MainActivitys";

	private static final boolean USE_OAUTH1 = false;

	DropboxAPI<AndroidAuthSession> mApi;

	private boolean mLoggedIn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		rfragment = (RightFragment) getSupportFragmentManager()
				.findFragmentById(R.id.fragment2);
		AndroidAuthSession session = buildSession();
		mApi = new DropboxAPI<AndroidAuthSession>(session);
		checkAppKeySetup();

	}

	@Override
	protected void onResume() {
		super.onResume();
		AndroidAuthSession session = mApi.getSession();

		// The next part must be inserted in the onResume() method of the
		// activity from which session.startAuthentication() was called, so
		// that Dropbox authentication completes properlyfd.
		if (session.authenticationSuccessful()) {
			try {
				// Mandatory call to complete the auth
				session.finishAuthentication();

				// Store it locally in our app for later use
				storeAuth(session);
				// setLoggedIn(true);
			} catch (IllegalStateException e) {
				// showToast("Couldn't authenticate with Dropbox:" +
				// e.getLocalizedMessage());
				Log.i(TAG, "Error authenticating", e);
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		// Associate searchable configuration with the SearchView
		/*
		 * SearchManager searchManager = (SearchManager)
		 * getSystemService(Context.SEARCH_SERVICE); SearchView searchView =
		 * (SearchView) menu.findItem(R.id.action_search).getActionView();
		 * searchView.setSearchableInfo(searchManager
		 * .getSearchableInfo(getComponentName()));
		 * searchView.setIconifiedByDefault(false);
		 */
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.

		int id = item.getItemId();
		/*if (id == R.id.action_settings) {

			return true;
		}*/
		
		 if (id == R.id.createfolder) {
		startActivity(new Intent(MainActivity.this, Copy.class));
			return true;
		}
		
		else if (id == R.id.Application){
			 ListView apps;
			 PackageManager packageManager;
			    
			 item.setTitle("Applications");
			 
			   
			        setContentView(R.layout.list);
			         
			        apps = (ListView) findViewById(R.id.listView1);
			        packageManager = getPackageManager();
			  final List <PackageInfo> packageList = packageManager
			    .getInstalledPackages(PackageManager.GET_META_DATA); // all apps in the phone
			  final List <PackageInfo> packageList1 = packageManager
			    .getInstalledPackages(0);
			 
			    try {
			      packageList1.clear();
			      for (int n = 0; n < packageList.size(); n++)
			      {
			 
			       PackageInfo PackInfo = packageList.get(n);
			       if (((PackInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) != true)
			        //check weather it is system app or user installed app
			       {
			        try
			        {
			          
			          packageList1.add(packageList.get(n)); // add in 2nd list if it is user installed app
			           Collections.sort(packageList1,new Comparator <PackageInfo >()
			             // this will sort App list on the basis of app name
			           {
			            public int compare(PackageInfo o1,PackageInfo o2)
			            {
			             return o1.applicationInfo.loadLabel(getPackageManager()).toString()
			               .compareToIgnoreCase(o2.applicationInfo.loadLabel(getPackageManager())
			                   .toString());// compare and return sorted packagelist.
			            }
			           });
			            
			          
			        } catch (NullPointerException e) {
			         e.printStackTrace();
			        }
			       }
			      }
			     } catch (Exception e) {
			      e.printStackTrace();
			     }
			    Listadapter Adapter = new Listadapter(this,packageList1, packageManager);
			    apps.setAdapter(Adapter);
			         return true;
		}
		
				else if (id == R.id.view_settings) {
			if (gridView) {
				item.setTitle("Grid View");
				rfragment.changeView(gridView);
				gridView = false;
			} else {
				item.setTitle("List View");
				rfragment.changeView(gridView);
				gridView = true;
			}
			return true;
		} else if (id == R.id.action_search) {
			final Dialog renamedilog = new Dialog(MainActivity.this);
			renamedilog.setContentView(R.layout.renamedialog);
			renamedilog.setTitle("SEARCH");
			renamedilog.show();
			final EditText renametxt = (EditText) renamedilog
					.findViewById(R.id.renametxt);
			renametxt.setText("");
			renametxt.requestFocus();
			Button rnmcnl = (Button) renamedilog.findViewById(R.id.rnmcnl);
			rnmcnl.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					renamedilog.dismiss();
				}
			});
			Button rnmok = (Button) renamedilog.findViewById(R.id.rnmok);
			rnmok.setText("SEARCH");
			rnmok.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					if (renametxt.getText().length() == 0) {
						AlertDialog.Builder builder1 = new AlertDialog.Builder(
								MainActivity.this);
						builder1.setMessage("PLEASE ENTER THE TEXT TO SEARCH")
								.setTitle("BLUE TOUCH FILE MANAGER")
								.setCancelable(false)
								.setPositiveButton("OK",
										new DialogInterface.OnClickListener() {
											public void onClick(
													DialogInterface dialog,
													int id) {
												dialog.cancel();
											}
										});

						AlertDialog alert1 = builder1.create();
						alert1.show();
					} else {
						rfragment.searchFiles(renametxt.getText().toString());
						renamedilog.dismiss();
					}
				}
			});
			return true;
		} else {

			startActivity(new Intent(Settings.ACTION_BLUETOOTH_SETTINGS));
			return true;
		}

	}

	private void logOut() {
		// Remove credentials from the session
		mApi.getSession().unlink();

		// Clear our stored keys
		clearKeys();
		// Change UI state to display logged out version
		// //setLoggedIn(false);
	}

	private void clearKeys() {
		SharedPreferences prefs = getSharedPreferences(ACCOUNT_PREFS_NAME, 0);
		Editor edit = prefs.edit();
		edit.clear();
		edit.commit();
	}

	/**
	 * Convenience function to change UI state based on being logged in
	 */
	/*
	 * private void setLoggedIn(boolean loggedIn) { mLoggedIn = loggedIn; if
	 * (loggedIn) { //mSubmit.setText("Unlink from Dropbox");
	 * mDisplay.setVisibility(View.VISIBLE); } else {
	 * mSubmit.setText("Link with Dropbox"); mDisplay.setVisibility(View.GONE);
	 * mImage.setImageDrawable(null); } }
	 */
	@Override
	public void onArticleSelected(String filepath) {
		rfragment.setFilesFrom(filepath, MainActivity.this);
	}

	private void loadAuth(AndroidAuthSession session) {
		SharedPreferences prefs = getSharedPreferences(ACCOUNT_PREFS_NAME, 0);
		String key = prefs.getString(ACCESS_KEY_NAME, null);
		String secret = prefs.getString(ACCESS_SECRET_NAME, null);
		if (key == null || secret == null || key.length() == 0
				|| secret.length() == 0)
			return;

		if (key.equals("oauth2:")) {
			// If the key is set to "oauth2:", then we can assume the token is
			// for OAuth 2.
			session.setOAuth2AccessToken(secret);
		} else {
			// Still support using old OAuth 1 tokens.
			session.setAccessTokenPair(new AccessTokenPair(key, secret));
		}
	}

	private void storeAuth(AndroidAuthSession session) {
		// Store the OAuth 2 access token, if there is one.
		String oauth2AccessToken = session.getOAuth2AccessToken();
		if (oauth2AccessToken != null) {
			SharedPreferences prefs = getSharedPreferences(ACCOUNT_PREFS_NAME,
					0);
			Editor edit = prefs.edit();
			edit.putString(ACCESS_KEY_NAME, "oauth2:");
			edit.putString(ACCESS_SECRET_NAME, oauth2AccessToken);
			edit.commit();
			return;
		}
		// Store the OAuth 1 access token, if there is one. This is only
		// necessary if
		// you're still using OAuth 1.
		AccessTokenPair oauth1AccessToken = session.getAccessTokenPair();
		if (oauth1AccessToken != null) {
			SharedPreferences prefs = getSharedPreferences(ACCOUNT_PREFS_NAME,
					0);
			Editor edit = prefs.edit();
			edit.putString(ACCESS_KEY_NAME, oauth1AccessToken.key);
			edit.putString(ACCESS_SECRET_NAME, oauth1AccessToken.secret);
			edit.commit();
			return;
		}
	}

	private AndroidAuthSession buildSession() {
		AppKeyPair appKeyPair = new AppKeyPair(APP_KEY, APP_SECRET);

		AndroidAuthSession session = new AndroidAuthSession(appKeyPair);
		loadAuth(session);
		return session;
	}

	private void checkAppKeySetup() {
		// Check to make sure that we have a valid app key
		if (APP_KEY.startsWith("CHANGE") || APP_SECRET.startsWith("CHANGE")) {
			Toast.makeText(
					MainActivity.this,
					"You must apply for an app key and secret from developers.dropbox.com, and add them to the DBRoulette ap before trying it.",
					3000).show();
			finish();
			return;
		}
	}

	@Override
	public void oncontextSelected(String filepath) {

		if (mLoggedIn) {
			logOut();
		} else {
			// Start the remote authentication
			if (USE_OAUTH1) {
				mApi.getSession().startAuthentication(MainActivity.this);
			} else {
				mApi.getSession().startOAuth2Authentication(
						MainActivity.this);
			}
		}
	}
	
	public void onBackPressed()
	{
	    // Catch back action and pops from backstack
	    // (if you called previously to addToBackStack() in your transaction)
	    if (getSupportFragmentManager().getBackStackEntryCount() > 0){
	        getSupportFragmentManager().popBackStack();
	    }
	    // Default action on back pressed
	    else 
	    	super.onBackPressed();
	}
}
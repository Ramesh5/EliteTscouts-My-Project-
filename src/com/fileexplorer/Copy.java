package com.fileexplorer;





import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Copy extends Activity
{

	private static final String TAG1 = null;
	protected static final String TAG = null;
	EditText ed1, ed2, ed3, ed4, ed5, ed7;
	TextView t1, t2, t3, t4, t5, t6, t7;
	Button b1, b3, b4, b2;
	private String s1;
	private String s2, s4, s5;

	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.folder);
	
		

		ed1 = (EditText) findViewById(R.id.editText1);
		ed2 = (EditText) findViewById(R.id.editText2);
		ed3 = (EditText) findViewById(R.id.editText3);
		final Editable s1 = ed1.getText();
		final Editable s2 = ed2.getText();
		final Editable s3 = ed3.getText();
		t1 = (TextView) findViewById(R.id.textView1);
		

		t3 = (TextView) findViewById(R.id.textView3);
		t4 = (TextView) findViewById(R.id.textView4);
		b1 = (Button) findViewById(R.id.button1);
		ed4 = (EditText) findViewById(R.id.editText4);
		ed5 = (EditText) findViewById(R.id.editText5);
		ed7 = (EditText) findViewById(R.id.editText7);

		// final Editable s3= ed3.getText();

		final Editable s4 = ed4.getText();
		final Editable s5 = ed5.getText();
		final Editable s6 = ed7.getText();

		t6 = (TextView) findViewById(R.id.textView6);

		t7 = (TextView) findViewById(R.id.textView7);

		b3 = (Button) findViewById(R.id.button3);
		b2 = (Button) findViewById(R.id.button2);

		b4 = (Button) findViewById(R.id.button4);
		b1 = (Button) findViewById(R.id.button1);

		
		/*
		 * File direct = new File(Environment.getExternalStorageDirectory() +
		 * "/New Folder");
		 * 
		 * if(!direct.exists()) { if(direct.mkdir()) { //directory is created; }
		 * 
		 * }
		 * 
		 * 
		 * }
		 */

		
		b1.setOnClickListener(new View.OnClickListener() {

			@SuppressLint("SdCardPath")
			public void onClick(View v) {
				// TODO Auto-generated method stub

				File dbdir = new File("/sdcard/" + s1);
				dbdir.mkdir();

				File f = new File("/sdcard/" + s1 + "/" + s2 + ".txt");
				try {
					f.createNewFile();
					
					FileOutputStream fOut = new FileOutputStream(f);
					OutputStreamWriter myOutWriter = new OutputStreamWriter(
							fOut);
					myOutWriter.append(ed3.getText());
					myOutWriter.close();
					fOut.close();
					Toast.makeText(getBaseContext(),
							"Done writing SD" + "" + s2 + ".txt",
							Toast.LENGTH_SHORT).show();
				} catch (Exception e) {
					Toast.makeText(getBaseContext(), e.getMessage(),
							Toast.LENGTH_SHORT).show();
				}

			}

			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub

			}

		});


		Button btnReadSDFile = (Button) findViewById(R.id.button2);
		btnReadSDFile.setOnClickListener(new View.OnClickListener() {
			@SuppressLint("SdCardPath")
			public void onClick(View v) {
				// write on SD card file data in the text box
				try {
					File myFile = new File("/sdcard/" + s1 + "/" + s2 + ".txt");
					FileInputStream fIn = new FileInputStream(myFile);
					BufferedReader myReader = new BufferedReader(
							new InputStreamReader(fIn));
					String aDataRow = "";
					String aBuffer = "";
					while ((aDataRow = myReader.readLine()) != null) {
						aBuffer += aDataRow + "\n";
					}
					t4.setText(aBuffer);
					myReader.close();
					Toast.makeText(getBaseContext(),
							"Done reading SD" + s2 + ".txt", Toast.LENGTH_SHORT)
							.show();
				} catch (Exception e) {
					Toast.makeText(getBaseContext(), e.getMessage(),
							Toast.LENGTH_SHORT).show();
				}
			}// onClick

			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub

			}
		}); // btnReadSDFile

		b3.setOnClickListener(new View.OnClickListener() {

			@SuppressLint("SdCardPath")
			public void onClick(View v) {
				// TODO Auto-generated method stub
				File dbdir1 = new File("/sdcard/" + s4);
				dbdir1.mkdir();

				File f1 = new File("/sdcard/" + s4 + "/" + s5 + ".txt");
				
				try {
					f1.createNewFile();
					FileOutputStream fOut = new FileOutputStream(f1);
					OutputStreamWriter myOutWriter = new OutputStreamWriter(
							fOut);
					myOutWriter.append(ed7.getText());
					myOutWriter.close();
					fOut.close();
					Toast.makeText(getBaseContext(),
							"Done writing SD" + "" + s5 + ".txt",
							Toast.LENGTH_SHORT).show();
				} catch (Exception e) {
					Toast.makeText(getBaseContext(), e.getMessage(),
							Toast.LENGTH_SHORT).show();
				}
			}
		});

		b4.setOnClickListener(new View.OnClickListener() {

			@SuppressLint("SdCardPath")
			public void onClick(View v) {

				File sourceLocation = new File("/sdcard/" + s1 + "/" + s2+ ".txt");

				// make sure your target location folder exists!
				// File targetLocation = new File ("/sdcard/"+s5+"/"+s2+".txt");
				File targetLocation = new File("/sdcard/" + "s4");
				//1File targetLocation = new File("/sdcard/" + "s4/" + s2 + ".txt");

				// just to take note of the location sources
				Log.v(TAG, "sourceLocation: " + sourceLocation);
				Log.v(TAG, "targetLocation: " + targetLocation);

				try {

					// 1 = move the file, 2 = copy the file
					int actionChoice = 2;

					// moving the file to another directory
					if (actionChoice == 2) {

						if (sourceLocation.renameTo(targetLocation)) {
							Log.v(TAG, "Move file successful.");
						} else {
							Log.v(TAG, "Move file failed.");
						}

					}

					// we will copy the file
					else {

						// make sure the target file exists

						if (sourceLocation.exists()) {

							InputStream in = new FileInputStream(sourceLocation);
							OutputStream out = new FileOutputStream(
									targetLocation);

							// Copy the bits from instream to outstream
							byte[] buf = new byte[1024];
							int len;

							while ((len = in.read(buf)) > 0) {
								out.write(buf, 0, len);
							}

							in.close();
							out.close();

							Log.v(TAG, "Copy file successful.");

						} else {
							Log.v(TAG, "Copy file failed. Source file missing.");
						}

					}

				} catch (NullPointerException e1) {
					e1.printStackTrace();
				} catch (Exception e) {
					Throwable e1 = null;
					e1.printStackTrace();
				}

			}

		});
	}


	}

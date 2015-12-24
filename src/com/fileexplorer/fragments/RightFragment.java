package com.fileexplorer.fragments;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.fileexplorer.MainActivity;
import com.fileexplorer.R;
import com.fileexplorer.adapter.RightFileListAdapter;

public class RightFragment extends Fragment {
	public ListView lv;
	public GridView gv;
	private List<File> folderlist;
	private RightFileListAdapter fladapter;
	static View view;
	public String totext;
	public String fromtext;
	File f;

	// Container Activity must implement this interface
	public interface RightItemSelectedListener {
		public void oncontextSelected(String filepath);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		MenuInflater m = getActivity().getMenuInflater();
		m.inflate(R.menu.long_menu, menu);
		super.onCreateContextMenu(menu, v, menuInfo);
	}

	@SuppressWarnings({ })
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		
		switch (item.getItemId()) {
		case R.id.copy:
			break;
			
		case R.id.rename:
			/*File sdcard = Environment.getExternalStorageDirectory();
			File from = new File(sdcard,"from.txt");
		 	File to = new File(sdcard,"to.txt");
			from.renameTo(to);
			break;*/
			
			 /*File directory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES), MEDIA_NAME);
				if(!directory.exists()){
				directory.mkdirs();
				} 
		        File from      = new File(directory, fromtext);
		        File to        = new File(directory, totext); 
				if(from.exists())
		        from.renameTo(to);
				return true;
			*/
			
			
			
			break;
		case R.id.delete:
			
     /*       String path;
            
           // private Stack<String> mPathStack;
			File f = new File(path);

			File [] files = f.listFiles();

			Arrays.sort( files, new Comparator()
			{
			    public int compare(Object o1, Object o2) {

			        if (((File)o1).lastModified() > ((File)o2).lastModified()) {
			            return -1;
			        } else if (((File)o1).lastModified() < ((File)o2).lastModified()) {
			            return +1;
			        } else {
			            return 0;
			        }
			    }

			});*/
			/*String path1;
			path1 = Environment.getExternalStorageDirectory().getAbsoluteFile();
			Files file = new Files(path);
			boolean deleted = file.delete();
             break;*/
						    break;
		  case R.id.dropbox:
			AdapterView.AdapterContextMenuInfo infodropbox = (AdapterView.AdapterContextMenuInfo) item
					.getMenuInfo();
			((MainActivity) getActivity()).oncontextSelected(folderlist.get(
					infodropbox.position).getAbsolutePath());

			break;
		case R.id.share:
			AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
					.getMenuInfo();
			Intent theIntent = new Intent(Intent.ACTION_SEND);
			theIntent.setType("*/*");
			theIntent.putExtra(Intent.EXTRA_STREAM,
					Uri.parse(folderlist.get(info.position).getAbsolutePath()));
			startActivity(Intent.createChooser(theIntent, "SHARING APPS"));
			break;
		}
		return super.onContextItemSelected(item);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.right_fragment, container, true);

		lv = (ListView) view.findViewById(R.id.listfilelv);
		gv = (GridView) view.findViewById(R.id.listfilegv);
		registerForContextMenu(lv);
		folderlist = new ArrayList<File>();
		f = new File(Environment.getExternalStorageDirectory()
				.getAbsolutePath());
		File[] flist = f.listFiles();

		for (File fit : flist) {
			folderlist.add(fit);
		}
		fladapter = new RightFileListAdapter(folderlist, getActivity());
		lv.setAdapter(fladapter);

		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if (folderlist.get(arg2).isDirectory())
					setFilesFrom(folderlist.get(arg2).getAbsolutePath(),
							getActivity());
				else {
					Intent intent = new Intent();
					intent.setAction(android.content.Intent.ACTION_VIEW);
					File file = new File(folderlist.get(arg2).getAbsolutePath());
					MimeTypeMap mime = MimeTypeMap.getSingleton();
					String type = mime.getMimeTypeFromExtension(folderlist
							.get(arg2)
							.getAbsolutePath()
							.substring(
									folderlist.get(arg2).getAbsolutePath()
											.lastIndexOf('.') + 1));
					intent.setDataAndType(Uri.fromFile(file), type);
					// intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

					try {
						startActivity(intent);
					} catch (ActivityNotFoundException e) {
					}
				}

			}
		});

		gv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if (folderlist.get(arg2).isDirectory())
					setFilesFrom(folderlist.get(arg2).getAbsolutePath(),
							getActivity());
				else {
					Intent intent = new Intent();
					intent.setAction(android.content.Intent.ACTION_VIEW);
					File file = new File(folderlist.get(arg2).getAbsolutePath());
					MimeTypeMap mime = MimeTypeMap.getSingleton();
					String type = mime.getMimeTypeFromExtension(folderlist
							.get(arg2)
							.getAbsolutePath()
							.substring(
									folderlist.get(arg2).getAbsolutePath()
											.lastIndexOf('.') + 1));
			 		intent.setDataAndType(Uri.fromFile(file), type);
					// intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

					try {
						startActivity(intent);
					} catch (ActivityNotFoundException e) {
					}
				}

			}
		});
		return view;
	}

	public void setFilesFrom(String filepath, Context c) {
		f = new File(filepath);
		File[] flist = f.listFiles();
		folderlist.clear();
		for (File fit : flist) {
			folderlist.add(fit);
		}

		fladapter.setFolderlist(folderlist);
		fladapter.notifyDataSetChanged();

	}

	public void searchFiles(final String searchText) {
		File[] searchreslt = f.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String filename) {
				return filename.matches("(?i).*" + searchText + ".*");
			}
		});
		folderlist.clear();
		if (searchreslt.length < 1) {
			Toast.makeText(getActivity(), "SORRY, NO MATCHES FOUND",
					Toast.LENGTH_SHORT).show();
		} else {
			for (File fit : searchreslt) {
				folderlist.add(fit);
			}

			fladapter.setFolderlist(folderlist);
			fladapter.notifyDataSetChanged();
		}
	}

	public void changeView(boolean gridview) {
		if (gridview) {
			lv.setVisibility(View.VISIBLE);
			gv.setVisibility(View.GONE);
			lv.setAdapter(fladapter);
			registerForContextMenu(lv);

		} else {
			lv.setVisibility(View.GONE);
			gv.setVisibility(View.VISIBLE);
			gv.setAdapter(fladapter);
			registerForContextMenu(gv);

		}
	}
	
	
	public static boolean deleteDirectory(File path) {
	    if( path.exists() ) {
	      File[] file = path.listFiles();
	      if (file == null) {
	          return true;
	      }
	      for(int i=0; i<file.length; i++) {
	         if(file[i].isDirectory()) {
	           deleteDirectory(file[i]);
	         }
	         else {
	           file[i].delete();
	         }
	      }
	    }
	    return( path.delete() );
	  }
}

package com.fileexplorer.fragments;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.fileexplorer.MainActivity;
import com.fileexplorer.R;
import com.fileexplorer.adapter.FileListAdapter;

@SuppressLint("NewApi")
public class LeftFragment extends Fragment {
	ListView lv;
	List<File> folderlist;
	FileListAdapter fladapter;
	OnHeadlineSelectedListener mCallback;

	// Container Activity must implement this interface
	public interface OnHeadlineSelectedListener {
		public void onArticleSelected(String filepath);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.left_fragment, container, true);
		lv = (ListView) view.findViewById(R.id.leftdirlistv);
		folderlist = new ArrayList<File>();
		File f = new File(Environment.getExternalStorageDirectory()
				.getAbsolutePath());
		File[] flist = f.listFiles();

		for (File fit : flist) {
			if (fit.isDirectory()) {
				folderlist.add(fit);
			}
		}
		fladapter = new FileListAdapter(folderlist, getActivity());
		lv.setAdapter(fladapter);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				((MainActivity) getActivity()).onArticleSelected(folderlist
						.get(position).getAbsolutePath());
			}
		});

		return view;
	}

	@Override
	public void onAttach(Activity activity) {

		super.onAttach(activity);
	}
}

package com.fileexplorer.adapter;

import java.io.File;
import java.util.List;

import com.fileexplorer.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class FileListAdapter  extends BaseAdapter {
	List<File> folderlist;
	Context c;
	private LayoutInflater inflater = null;

	public FileListAdapter(List<File> folderlist, Context c) {
		this.folderlist = folderlist;
		this.c = c;
		inflater = (LayoutInflater) c
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {

		return folderlist.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		if (convertView == null)
			vi = inflater.inflate(R.layout.left_list_item, null);
		// ImageView
		// compleatImageView=(ImageView)vi.findViewById(R.id.folderimgv);
		TextView name = (TextView) vi.findViewById(R.id.foldernametv); // name
		name.setText(folderlist.get(position).getName());
		return vi;
	}

}


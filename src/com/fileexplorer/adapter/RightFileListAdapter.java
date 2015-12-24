package com.fileexplorer.adapter;

import java.io.File;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fileexplorer.R;

public class RightFileListAdapter  extends BaseAdapter {
	List<File> folderlist;
	Context c;
	private LayoutInflater inflater = null;

	public RightFileListAdapter(List<File> folderlist, Context c) {
		this.folderlist = folderlist;
		this.c = c;
		inflater = (LayoutInflater) c
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {

		return folderlist.size();
	}

	public List<File> getFolderlist() {
		return folderlist;
	}

	public void setFolderlist(List<File> folderlist) {
		this.folderlist = folderlist;
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
			vi = inflater.inflate(R.layout.right_list_item, null);

		ImageView imageView = (ImageView) vi.findViewById(R.id.rightlistimgv);
		if (folderlist.get(position).isFile()) {
			String type = folderlist
					.get(position)
					.getAbsolutePath()
					.substring(folderlist.get(position).getAbsolutePath()
									.lastIndexOf('.') + 1);
			if (type.equalsIgnoreCase("mp4")) {
				imageView.setImageDrawable(c.getResources().getDrawable(
						R.drawable.mpfour));

			}
			else if(type.equalsIgnoreCase("jpg")||type.equalsIgnoreCase("jpeg")){
				imageView.setImageDrawable(c.getResources().getDrawable(
						R.drawable.jpg));	
			}
			else {
				imageView.setImageDrawable(c.getResources().getDrawable(
						R.drawable.file));
			}
		} else
			imageView.setImageDrawable(c.getResources().getDrawable(
					R.drawable.folder));
		TextView name = (TextView) vi.findViewById(R.id.rightlisttv); // name
		name.setText(folderlist.get(position).getName());
		return vi;
	}

}
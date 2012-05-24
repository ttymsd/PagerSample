package com.bonboru.fragment;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
	private String[] fileList;
	
	public MyFragmentPagerAdapter(FragmentManager fm, ContentResolver resolver) {
		super(fm);
		getFileList(resolver);
	}

	@Override
	public Fragment getItem(int position) {
		if (fileList.length == 0 || fileList.length < position) {
			return null;
		}
		return new ImageFragment(fileList[position]);
	}

	@Override
	public int getCount() {
		return fileList.length;
	}
	
	private String[] getFileList(ContentResolver resolver) {
		Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
		Cursor c = resolver.query(uri, null, null, null, null);  
		fileList = new String[c.getCount()];
		int k = 0;
		while(c.moveToNext()) {
			fileList[k] = c.getString(c.getColumnIndexOrThrow("_id"));
			k++;
		}
		return fileList;
	}
}

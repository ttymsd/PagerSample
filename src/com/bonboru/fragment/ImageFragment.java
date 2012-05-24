package com.bonboru.fragment;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bonboru.activity.R;

public class ImageFragment extends Fragment {
	private String fileName;
	
	public ImageFragment(String fileName) {
		this.fileName = fileName;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		LinearLayout layout =(LinearLayout) inflater.inflate(R.layout.image, null);
		TextView text = (TextView) layout.findViewById(R.id.image_title);
		text.setText(fileName);
		ImageView image = (ImageView) layout.findViewById(R.id.image);
		image.setImageBitmap(loadImage());
		return layout;
	}
	
	private Bitmap loadImage() {
		String[] projection = {MediaStore.Images.Media.DATA};
		String[] selectionArgs = {fileName};
		Cursor c = getActivity().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
											  projection, "_id = ?", selectionArgs, null);
		Bitmap bmp = null;
		if (c.moveToFirst()) {
			String path = c.getString(0);
			//読み込み用のオプションオブジェクトを生成
			BitmapFactory.Options options = new BitmapFactory.Options();
			//この値をtrueにすると実際には画像を読み込まず、
			//画像のサイズ情報だけを取得することができます。
			options.inJustDecodeBounds = true;

			//画像ファイル読み込み
			//ここでは上記のオプションがtrueのため実際の
			//画像は読み込まれないです。
			BitmapFactory.decodeFile(path, options);

			//読み込んだサイズはoptions.outWidthとoptions.outHeightに
			//格納されるので、その値から読み込む際の縮尺を計算します。
			//このサンプルではどんな大きさの画像でもHVGAに収まるサイズを
			//計算しています。
			int scaleW = options.outWidth / 380 + 1;
			int scaleH = options.outHeight / 420 + 1;

			//縮尺は整数値で、2なら画像の縦横のピクセル数を1/2にしたサイズ。
			//3なら1/3にしたサイズで読み込まれます。
			int scale = Math.max(scaleW, scaleH);

			//今度は画像を読み込みたいのでfalseを指定
			options.inJustDecodeBounds = false;

			//先程計算した縮尺値を指定
			options.inSampleSize = scale;

			//これで指定した縮尺で画像を読み込めます。
			//もちろん容量も小さくなるので扱いやすいです。
			bmp = BitmapFactory.decodeFile(path, options);
		}
		return bmp;
	}
}

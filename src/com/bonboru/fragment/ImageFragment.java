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
			//�ǂݍ��ݗp�̃I�v�V�����I�u�W�F�N�g�𐶐�
			BitmapFactory.Options options = new BitmapFactory.Options();
			//���̒l��true�ɂ���Ǝ��ۂɂ͉摜��ǂݍ��܂��A
			//�摜�̃T�C�Y��񂾂����擾���邱�Ƃ��ł��܂��B
			options.inJustDecodeBounds = true;

			//�摜�t�@�C���ǂݍ���
			//�����ł͏�L�̃I�v�V������true�̂��ߎ��ۂ�
			//�摜�͓ǂݍ��܂�Ȃ��ł��B
			BitmapFactory.decodeFile(path, options);

			//�ǂݍ��񂾃T�C�Y��options.outWidth��options.outHeight��
			//�i�[�����̂ŁA���̒l����ǂݍ��ލۂ̏k�ڂ��v�Z���܂��B
			//���̃T���v���ł͂ǂ�ȑ傫���̉摜�ł�HVGA�Ɏ��܂�T�C�Y��
			//�v�Z���Ă��܂��B
			int scaleW = options.outWidth / 380 + 1;
			int scaleH = options.outHeight / 420 + 1;

			//�k�ڂ͐����l�ŁA2�Ȃ�摜�̏c���̃s�N�Z������1/2�ɂ����T�C�Y�B
			//3�Ȃ�1/3�ɂ����T�C�Y�œǂݍ��܂�܂��B
			int scale = Math.max(scaleW, scaleH);

			//���x�͉摜��ǂݍ��݂����̂�false���w��
			options.inJustDecodeBounds = false;

			//����v�Z�����k�ڒl���w��
			options.inSampleSize = scale;

			//����Ŏw�肵���k�ڂŉ摜��ǂݍ��߂܂��B
			//�������e�ʂ��������Ȃ�̂ň����₷���ł��B
			bmp = BitmapFactory.decodeFile(path, options);
		}
		return bmp;
	}
}

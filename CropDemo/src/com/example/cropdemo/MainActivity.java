package com.example.cropdemo;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * 
 * @author 邓浩宸
 *
 */
public class MainActivity extends Activity implements OnClickListener {
	private static final int PICK_FROM_CAMERA = 1024;
	private static final int CROP_FROM_CAMERA = 1025;
	private static final int SELECT_FROM_AMBL = 1026;
	private CropParams mCropParams;
	private ImageView iv;
	public String test;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button photograph = (Button) findViewById(R.id.photograph);
		Button select = (Button) findViewById(R.id.select);
		iv = (ImageView) findViewById(R.id.iv);
		photograph.setOnClickListener(this);
		select.setOnClickListener(this);
		// 裁切的比例为2:1 ,图片的分辨率大小:300*300
		mCropParams = new CropParams(this, 2, 1, 1080, 540);
		// 选择大图片是必须使用文件读取的方式
		mCropParams.uri = FileUtils.generateUri();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.photograph:// 执行拍照并裁切
			ImageUtil.takePhotoForResult(this, PICK_FROM_CAMERA);
			break;
		case R.id.select:// 执行图片选择拍照
			ImageUtil.openAbleForResult(this, SELECT_FROM_AMBL);
			break;
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != Activity.RESULT_OK) {
			return;
		}
		switch (requestCode) {
		case PICK_FROM_CAMERA:
			ImageUtil.cropForReslt(this, CROP_FROM_CAMERA, mCropParams);
			break;
		case CROP_FROM_CAMERA:
			if (null != data) {
				setCropImg(data);
			}
			break;
		case SELECT_FROM_AMBL:
			ImageUtil.cropForReslt(this, data.getData(), CROP_FROM_CAMERA, mCropParams);
			break;
		}
	}

	/**
	 * set the bitmap
	 * 
	 * @param picdata
	 */
	private void setCropImg(Intent picdata) {
		Bitmap revitionImageSize;
		try {
			// 根据图片处理,进行采样压缩
			Toast.makeText(this, mCropParams.uri.getPath(), Toast.LENGTH_SHORT).show();
			revitionImageSize = BitmpUtils.revitionImageSize(mCropParams.uri.getPath());
			iv.setImageBitmap(revitionImageSize);
			test = "1";
		} catch (IOException e) {
			e.printStackTrace();

			Toast.makeText(this, "出错了", Toast.LENGTH_SHORT).show();
		}
	}


}

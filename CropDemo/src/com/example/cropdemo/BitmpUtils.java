package com.example.cropdemo;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
		/**
		 * 图片处理类,图片指定大小的采样
		 * @author 邓浩宸
		 *
		 */
public class BitmpUtils {
	/**
	 * 
	 * @param path  图片的路径
	 * @return    返回经过比例压缩的图片
	 * @throws IOException
	 */
	public static Bitmap revitionImageSize(String path) throws IOException {
		BufferedInputStream in = new BufferedInputStream(new FileInputStream(new File(path)));
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeStream(in, null, options);
		in.close();
		int i = 0;
		Bitmap bitmap = null;
		while (true) {
			if ((options.outWidth >> i <= 1080) && (options.outHeight >> i <= 540)) {
				in = new BufferedInputStream(new FileInputStream(new File(path)));
				options.inSampleSize = (int) Math.pow(2.0D, i);
				options.inJustDecodeBounds = false;
				bitmap = BitmapFactory.decodeStream(in, null, options);
				break;
			}
			i += 1;
		}
		return bitmap;
	}
	
	
	
			/**
			 * 
			 * @param path  图片的路径
			 * @param outWidth  指定输出宽
			 * @param outHeight  指定输出高
			 * @return  返回经过比例压缩的图片
			 * @throws IOException
			 */
	public static Bitmap revitionImageSize(String path,int outWidth,int outHeight ) throws IOException {
		BufferedInputStream in = new BufferedInputStream(new FileInputStream(new File(path)));
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeStream(in, null, options);
		in.close();
		int i = 0;
		Bitmap bitmap = null;
		while (true) {
			if ((options.outWidth >> i <= outWidth) && (options.outHeight >> i <= outHeight)) {
				in = new BufferedInputStream(new FileInputStream(new File(path)));
				options.inSampleSize = (int) Math.pow(2.0D, i);
				options.inJustDecodeBounds = false;
				bitmap = BitmapFactory.decodeStream(in, null, options);
				break;
			}
			i += 1;
		}
		return bitmap;
	}
	
	
	
	
	
	
}

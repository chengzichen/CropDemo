package com.example.cropdemo;

import java.io.File;

import android.net.Uri;
import android.os.Environment;
import android.util.Log;

/**
 * 裁切图片的保存的路径的工具类 1,用于获取有时间戳的保存文件的文件路径 2,清空缓存图片
 * 
 * @author 邓浩宸
 *
 */
public class FileUtils {
	private static final String TAG = "FileUtils";
	public static final String CROP_CACHE_FOLDER = "PhotoCropper";

	/**
	 * 1,用于获取有时间戳的保存文件的文件路径
	 * 
	 * @return 返回具有时间戳的文件路径,具有唯一性
	 */
	public static Uri generateUri() {
		File cacheFolder = new File(Environment.getExternalStorageDirectory() + File.separator + CROP_CACHE_FOLDER);
		if (!cacheFolder.exists()) {
			try {
				boolean result = cacheFolder.mkdir();
				Log.d(TAG, "generateUri " + cacheFolder + " result: " + (result ? "succeeded" : "failed"));
			} catch (Exception e) {
				Log.e(TAG, "generateUri failed: " + cacheFolder, e);
			}
		}
		String name = String.format("image-%d.jpg", System.currentTimeMillis());
		return Uri.fromFile(cacheFolder).buildUpon().appendPath(name).build();
	}

	/**
	 * 清空缓存的裁切图片
	 * 
	 * @return 是否成功
	 */
	public static boolean clearCacheDir() {
		File cacheFolder = new File(Environment.getExternalStorageDirectory() + File.separator + CROP_CACHE_FOLDER);
		if (cacheFolder.exists() && cacheFolder.listFiles() != null) {
			for (File file : cacheFolder.listFiles()) {
				boolean result = file.delete();
				Log.d(TAG, "Delete " + file.getAbsolutePath() + (result ? " succeeded" : " failed"));
			}
			return true;
		}
		return false;
	}

	/**
	 * 清除指定路径的缓存图片
	 * 
	 * @param uri
	 *            指定的路径
	 * @return 是否删除成功
	 */
	public static boolean clearCachedCropFile(Uri uri) {
		if (uri == null)
			return false;

		File file = new File(uri.getPath());
		if (file.exists()) {
			boolean result = file.delete();
			Log.d(TAG, "Delete " + file.getAbsolutePath() + (result ? " succeeded" : " failed"));
			return result;
		}
		return false;
	}
}

package com.example.cropdemo;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.Toast;

/**
 * 邓浩宸
 * 截图相关：兼容4.4以下和4.4以上 <div>1. 截图</div> <div>2. 拍照</div>
 *
 */
public class ImageUtil {
	public static Uri imgUri;

	/**
	 * 拍照并回调onActivityResult方法，照片保存到指定路径，你也可以指定自己的路径
	 * 
	 * @param activity
	 *            这里可以是传入activity作为开启返回的页面
	 * @param reqCode
	 *            请求码,自己可以定义
	 * 
	 */
	public static void takePhotoForResult(Activity activity, int reqCode) {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		imgUri = FileUtils.generateUri();
		intent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri);
		activity.startActivityForResult(intent, reqCode);
	}

	/**
	 * 拍照并回调onActivityResult方法，照片保存到指定路径，你也可以指定自己的路径
	 * 
	 * @param fragment
	 *            这里可以是传入fragment作为开启返回的页面
	 * @param reqCode
	 *            请求码,自己可以定义
	 * 
	 */
	public static void takePhotoForResult(Fragment fragment, int reqCode) {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		imgUri = FileUtils.generateUri();
		intent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri);
		fragment.startActivityForResult(intent, reqCode);
	}

	/**
	 * 通过选择系统相册选择
	 * 
	 * @param fragment
	 *            这里可以是传入fragment作为开启返回的页面
	 * 
	 * @param reqCode
	 *            请求码,自己可以定义
	 * 
	 */
	public static void openAbleForResult(Fragment activity, int reqCode) {
		Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
		photoPickerIntent.setType("image/*");
		activity.startActivityForResult(photoPickerIntent, reqCode);
	}

	/**
	 * 通过选择系统相册选择
	 * 
	 * @param fragment
	 *            这里可以是传入fragment作为开启返回的页面
	 * @param reqCode
	 *            请求码,自己可以定义
	 */
	public static void openAbleForResult(Activity activity, int reqCode) {
		Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
		photoPickerIntent.setType("image/*");
		activity.startActivityForResult(photoPickerIntent, reqCode);
	}

	/**
	 * 截图并回调onActivityResult方法
	 * 
	 * @param activity
	 *            这里可以是传入activity作为开启返回的页面
	 * @param reqCode
	 *            请求码
	 * 
	 */
	public static void cropForReslt(final Activity activity, final int reqCode, CropParams cropParams) {
		cropForReslt(activity, imgUri, reqCode, cropParams);
	}

	/**
	 * 截图并回调onActivityResult方法
	 * 
	 * @param fragment
	 *            这里可以是传入fragment作为开启返回的页面
	 * @param reqCode
	 *            请求码
	 * 
	 */
	public static void cropForReslt(final Fragment fragment, final int reqCode, CropParams cropParams) {

		cropForReslt(fragment, imgUri, reqCode, cropParams);
	}

	/**
	 * 截图并回调onActivityResult方法
	 * 
	 * @param activity
	 * @param reqCode
	 *            请求码
	 */
	public static void cropForReslt(final Activity activity, Uri uri, final int reqCode, CropParams cropParams) {
			setImgUri(uri);
			final ArrayList<CropOption> cropOptions = new ArrayList<CropOption>();
			Intent intent = new Intent("com.android.camera.action.CROP");
			intent.setDataAndType(imgUri, "image/*");
			List<ResolveInfo> list = activity.getApplicationContext().getPackageManager()
					.queryIntentActivities(intent, 0);
			int size = list.size();
			if (size == 0) {
				Toast.makeText(activity, "can't find crop app", Toast.LENGTH_SHORT).show();
				return;
			} else {
				intent.putExtra("outputX", cropParams.outputX);
				intent.putExtra("outputY", cropParams.outputY);
				intent.putExtra("aspectX", cropParams.aspectX);
				intent.putExtra("aspectY", cropParams.aspectY);
				intent.putExtra("scale", cropParams.scale);
				intent.putExtra(MediaStore.EXTRA_OUTPUT, cropParams.uri);
				// only one
				if (size == 1) {
					Intent i = new Intent(intent);
					ResolveInfo res = list.get(0);
					i.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
					activity.startActivityForResult(i, reqCode);
				} else {
					// many crop app
					for (ResolveInfo res : list) {
						final CropOption co = new CropOption();
						co.title = activity.getPackageManager()
								.getApplicationLabel(res.activityInfo.applicationInfo);
						co.icon = activity.getPackageManager()
								.getApplicationIcon(res.activityInfo.applicationInfo);
						co.appIntent = new Intent(intent);
						co.appIntent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
						cropOptions.add(co);
					}

					CropOptionAdapter adapter = new CropOptionAdapter(activity.getApplicationContext(),
							cropOptions);

					AlertDialog.Builder builder = new AlertDialog.Builder(activity);
					builder.setTitle("choose a app");
					builder.setAdapter(adapter, new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog, int item) {
							activity.startActivityForResult(cropOptions.get(item).appIntent, reqCode);
						}
					});

					builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
						@Override
						public void onCancel(DialogInterface dialog) {

							if (imgUri != null) {
								activity.getContentResolver().delete(imgUri, null, null);
								imgUri = null;
							}
						}
					});

					AlertDialog alert = builder.create();
					alert.show();
				}
			}

		}

	/**
	 * 截图并回调onActivityResult方法
	 * 
	 * @param activity
	 * @param reqCode
	 *            请求码
	 */
	public static void cropForReslt(final Fragment activity, Uri uri, final int reqCode, CropParams cropParams) {
		setImgUri(uri);
		final ArrayList<CropOption> cropOptions = new ArrayList<CropOption>();
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(imgUri, "image/*");
		List<ResolveInfo> list = activity.getActivity().getApplicationContext().getPackageManager()
				.queryIntentActivities(intent, 0);
		int size = list.size();
		if (size == 0) {
			Toast.makeText(activity.getActivity(), "can't find crop app", Toast.LENGTH_SHORT).show();
			return;
		} else {
			intent.putExtra("outputX", cropParams.outputX);
			intent.putExtra("outputY", cropParams.outputY);
			intent.putExtra("aspectX", cropParams.aspectX);
			intent.putExtra("aspectY", cropParams.aspectY);
			intent.putExtra("scale", cropParams.scale);
			intent.putExtra(MediaStore.EXTRA_OUTPUT, cropParams.uri);
			// only one
			if (size == 1) {
				Intent i = new Intent(intent);
				ResolveInfo res = list.get(0);
				i.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
				activity.startActivityForResult(i, reqCode);
			} else {
				// many crop app
				for (ResolveInfo res : list) {
					final CropOption co = new CropOption();
					co.title = activity.getActivity().getPackageManager()
							.getApplicationLabel(res.activityInfo.applicationInfo);
					co.icon = activity.getActivity().getPackageManager()
							.getApplicationIcon(res.activityInfo.applicationInfo);
					co.appIntent = new Intent(intent);
					co.appIntent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
					cropOptions.add(co);
				}

				CropOptionAdapter adapter = new CropOptionAdapter(activity.getActivity().getApplicationContext(),
						cropOptions);

				AlertDialog.Builder builder = new AlertDialog.Builder(activity.getActivity());
				builder.setTitle("choose a app");
				builder.setAdapter(adapter, new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int item) {
						activity.startActivityForResult(cropOptions.get(item).appIntent, reqCode);
					}
				});

				builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
					@Override
					public void onCancel(DialogInterface dialog) {

						if (imgUri != null) {
							activity.getActivity().getContentResolver().delete(imgUri, null, null);
							imgUri = null;
						}
					}
				});

				AlertDialog alert = builder.create();
				alert.show();
			}
		}

	}

	/**
	 * 获取拍照图片保存路径
	 * 
	 * @return
	 */
	public static Uri getTakePhotoUri() {
		return imgUri;
	}

	/**
	 * 设置拍照图片保存路径
	 * 
	 * @param uri
	 */
	private static void setImgUri(Uri uri) {
		if (uri != null)
			imgUri = uri;
	}

}

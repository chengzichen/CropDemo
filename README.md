
作者:邓浩宸
版本:1.0
#CropDemo 兼容
	兼容4.4以下和4.4以上,以及厂商定制手机
	工具类 Base64Utils	上传图片是进行处理
		  BitmpUtils   图片处理
		 FileUtils    文件缓存
#Croparams类:裁切图片的配置类
 		aspectX 属性是宽度比例
        aspectY 属性是高度的比例
        outputX 输出照片的宽度

        outputY 输出照片的高度
		uri   输出图片的路径
		其他的属性暂时没有用

#ImageUtil类
	1.使用ImageUtil.takePhotoForResult(this, PICK_FROM_CAMERA) 返回 Uri imgUri    (开启startActvity//执行拍照) 
	 返回图片的imgUri,在通过流读写成Bitmap	
	 这里有两个重载的方法
		1 takePhotoForResult(Fragment fragment, int reqCode)//拍照并回调onActivityResult方法，照片保存到指定路径，你也可以指定自己的路径
		2 takePhotoForResult(Activity activity, int reqCode)//拍照并回调onActivityResult方法，照片保存到指定路径，你也可以指定自己的路径
	2.使用ImageUtil.openAbleForResult(this,SELECT_FROM_AMBL) 返回 Uri imgUri     (开启startActvity//执行相册选择)   
	返回图片的imgUri,在通过流读写成Bitmap
	这里有两个重载的方法
		1 openAbleForResult(Fragment fragment, int reqCode)//通过选择系统相册选择
			@param fragment 这里可以是传入fragment作为开启返回的页面
			@param reqCode 请求码,自己可以定义
		2 openAbleForResult(Activity activity, int reqCode)//通过选择系统相册选择
			@param activity 这里可以是传入activity作为开启返回的页面
			@param reqCode 请求码
	3.使用 ImageUtil.cropForReslt(this, CROP_FROM_CAMERA, mCropParams)  返回 Uri imgUri  (进行裁切的方法)
	这里有两个重载的方法
		1 cropForReslt(final Fragment fragment, final int reqCode, CropParams cropParams)//裁切对应大小的图片
			@param fragment 这里可以是传入fragment作为开启返回的页面
			@param reqCode 请求码,自己可以定义
			@param CropParams 裁切图片的配置类,可以定义宽高
		2 cropForReslt(final Activity activity, final int reqCode, CropParams cropParams)//通过选择系统相册选择
			@param activity 这里可以是传入activity作为开启返回的页面
			@param reqCode 请求码
			@param CropParams 裁切图片的配置类,可以定义宽高
#使用方法
	1,直接调用ApitakePhotoForResult或者openAbleForResult
	2,在Fragment或者Activity中重写onActivityResult方法 
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
			ImageUtil.cropForReslt(this, data.getData(), CROP_FROM_CAMERA,
					mCropParams);
			break;
		}
	}
	3,图片处理
		private void setCropImg(Intent picdata) {
			Bitmap revitionImageSize;
			try {
				//根据图片处理,进行采样压缩
				Toast.makeText(this,mCropParams.uri.getPath(), Toast.LENGTH_SHORT).show();
				revitionImageSize = BitmpUtils
						.revitionImageSize(mCropParams.uri.getPath());
				iv.setImageBitmap(revitionImageSize);
				test="1";
			} catch (IOException e) {
				e.printStackTrace();
				
				Toast.makeText(this, "出错了", Toast.LENGTH_SHORT).show();
			}
	}
	

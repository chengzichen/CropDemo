package com.example.cropdemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;

/**
 * Date: 
 * Time: 11:12 AM
 * Desc: CropParams
 * Revision:
 * - 11:00 2014/10/03 Encapsulate the crop params.
 * - 13:20 2014/10/03 Put the initialization into constructor method.
 * - 14:00 2014/10/03 Make the crop as String instead of Boolean.
 * - 14:30 2014/10/03 Increase the default output size from 200 to 300.
 * - 12:20 2014/10/04 Add "scaleUpIfNeeded" crop options for scaling up cropped images if the size is too small.
 * - 23:10 2015/08/20 Add enable to toggle on/off the crop function.
 * - 13:30 2015/08/21 Add compress options to avoid OOM or other problems.
 * - 23:00 2015/09/05 Add default compress width & height.
 */
public class CropParams {

    public static final String CROP_TYPE = "image/*";
    public static final String OUTPUT_FORMAT = Bitmap.CompressFormat.JPEG.toString();

    public static final int DEFAULT_ASPECT = 1;
    public static final int DEFAULT_OUTPUT = 300;
//    public  int DEFAULT_COMPRESS_WIDTH = 60;
//    public   int DEFAULT_COMPRESS_HEIGHT = 60;
    public static final int DEFAULT_COMPRESS_QUALITY = 100;

    public Uri uri;

    public String type;
    public String outputFormat;
    public String crop;

    public boolean scale;
    public boolean returnData;
    public boolean noFaceDetection;
    public boolean scaleUpIfNeeded;

    /**
     * Default is true, if set false, crop function will not work,
     * it will only pick up images from gallery or take pictures from camera.
     */
    public boolean enable;

    /**
     * Default is false, if it is from capture and without crop, the image could be large
     * enough to trigger OOM, it is better to compress image while enable is false
     */
    public boolean compress;

    public boolean rotateToCorrectDirection;

    public int compressWidth;
    public int compressHeight;
    public int compressQuality;

    public int aspectX;
    public int aspectY;

    public int outputX;
    public int outputY;

    public Context context;
    public CropParams(Context context,int aspectX_,int aspectY_, int outputX_,int outputY_) {
        this.context = context;
        type = CROP_TYPE;
        outputFormat = OUTPUT_FORMAT;
        crop = "true";
        scale = true;
        returnData = false;
        noFaceDetection = true;
        scaleUpIfNeeded = true;
        enable = true;
        rotateToCorrectDirection = false;
        compress = false;
        compressQuality = DEFAULT_COMPRESS_QUALITY;
        compressWidth = outputX_;
        compressHeight = outputY_;
        aspectX = aspectX_;
        aspectY = aspectY_;
        outputX = outputX_;
        outputY = outputY_;
        refreshUri();
    }

    public void refreshUri() {
        uri = FileUtils.generateUri();
    }
}

package com.test.midasmobile9.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ImageUtil {
    public static int MAX_DIMENSION = 300;

    public final static String FILE_NAME_FORMAT = "yyyyMMddHHmmssSSS";
    public final static String FILE_NAME_HEADER = "MIDAS_PROFILE_CACHE";
    public final static String FILE_NAME_IMAGE_FORMAT = ".png";

    private Context context;

    public static Bitmap scaleImageDownToBitmap(Context context, Uri uri){
        //Uri(이미지의 주소)의 이미지를 MAX_DIMENSION 미만의 크기로 줄여서 비트맵으로 반환
        Bitmap bitmap = null;
        try {
            bitmap = scaleBitmapDown(
                    context,
                    MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri)
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public static File scaleImageDownToFile(Context context, Uri uri){
        File file = createImageFile(context, scaleImageDownToBitmap(context, uri));
        return file;
    }

    private static Bitmap scaleBitmapDown(Context context, Bitmap bitmap) {
        //파라미터로 들어온 비트맵을 MAX_DIMENSION 미만의 크기로 줄여서 비트맵을 반환
        int originalWidth = bitmap.getWidth();
        int originalHeight = bitmap.getHeight();
        int resizedWidth = MAX_DIMENSION;
        int resizedHeight = MAX_DIMENSION;

        if (originalHeight > originalWidth) {
            resizedWidth = (int) (resizedHeight * (float) originalWidth / (float) originalHeight);
        } else if (originalWidth > originalHeight) {
            resizedHeight = (int) (resizedWidth * (float) originalHeight / (float) originalWidth);
        }

        return Bitmap.createScaledBitmap(bitmap, resizedWidth, resizedHeight, false);
    }

    private static File createImageFile(Context context, Bitmap bitmap) {

        //캐시 이미지가 저장될 디렉토리와 이미지가 저장될 빈 파일 객체를 만듬
        String timeStamp = new SimpleDateFormat(FILE_NAME_FORMAT, Locale.KOREA).format(new Date());
        String imageFileName = FILE_NAME_HEADER + timeStamp + FILE_NAME_IMAGE_FORMAT;
        File resultFile = new File(context.getCacheDir(), imageFileName);
        try {
            resultFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        //Bitmap을 PNG로 변환하여 파일로 저장
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(resultFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return resultFile;
    }

}

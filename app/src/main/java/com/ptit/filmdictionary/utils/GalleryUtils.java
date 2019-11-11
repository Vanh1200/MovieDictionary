package com.ptit.filmdictionary.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;

import com.ptit.filmdictionary.data.model.ImageAndVideoModel;

import java.io.File;

/**
 * Created by vanh1200 on 09/11/2019
 */
public class GalleryUtils {
    public static Bitmap getThumbnail(Context context, ImageAndVideoModel item) {
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        if (item.getMeDiaType() == MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO) {

            return MediaStore.Video.Thumbnails.getThumbnail(context.getContentResolver(), item.id, MediaStore.Images.Thumbnails.MINI_KIND, bmOptions);
        } else {
            Bitmap bitmap = MediaStore.Images.Thumbnails.getThumbnail(context.getContentResolver(), item.id, MediaStore.Images.Thumbnails.MINI_KIND, bmOptions);
            return GalleryUtils.createImageBitmap(bitmap, item.getUrl());
        }
    }

    private static Bitmap createImageBitmap(Bitmap bitmap, String path) {
        Matrix matrix = null;

        if (path != null) {
            ExifInterface exif;
            try {
                exif = new ExifInterface(path);
                int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
                matrix = new Matrix();
                switch (orientation) {
                    case ExifInterface.ORIENTATION_ROTATE_90:
                        matrix.postRotate(90);
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_180:
                        matrix.postRotate(180);
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_270:
                        matrix.postRotate(270);
                        break;
                }
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }

        Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(),
                matrix, true);
        if (newBitmap != bitmap) {
            bitmap.recycle();
            bitmap = newBitmap;
        }
        return bitmap;
    }

    public static long getDuration(Context context, String filePath) {
        MediaPlayer mp = MediaPlayer.create(context, Uri.fromFile(new File(filePath)));
        long duration = -1;
        if (mp != null) {
            duration = mp.getDuration();
            mp.release();
        }
        return duration;
    }
}

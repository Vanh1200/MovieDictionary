package com.ptit.filmdictionary.data.source.local;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Log;

import com.ptit.filmdictionary.data.model.ImageAndVideoModel;
import com.ptit.filmdictionary.utils.GalleryUtils;
import com.ptit.filmdictionary.utils.MyApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vanh1200 on 09/11/2019
 */
public class GalleryLoader extends AsyncTask<Void, List<ImageAndVideoModel>, List<ImageAndVideoModel>> {
    private GalleryLoaderCallback mCallback;
    private final String TAG = "GalleryLoader";
    private final int DEFAULT_SIZE_PAGE = 30;
    private int currentPage = 0;

    public static final String TYPE_IMAGE = "select_image";
    public static final String TYPE_VIDEO = "select_video";

    private static final String MEDIA_URL_COLUMN = MediaStore.Files.FileColumns.DATA;
    private static final String MEDIA_ID_COLUMN = MediaStore.Files.FileColumns._ID;
    private static final String MEDIA_TYPE_COLUMN = MediaStore.Files.FileColumns.MEDIA_TYPE;
    private static final String MEDIA_DATE_ADDED_COLUMN = MediaStore.Files.FileColumns.DATE_ADDED;

    private Context mContext;
    private List<ImageAndVideoModel> mGalleryItemList = new ArrayList<>();
    private String mMediaType;

    public GalleryLoader(GalleryLoaderCallback callback) {
        mCallback = callback;
        mContext = MyApplication.getContext();
    }

    @Override
    protected void onProgressUpdate(List<ImageAndVideoModel>... values) {
        super.onProgressUpdate(values);
        if (mCallback != null) {
            mCallback.onLoadedGallery(values[0]);
        }
    }

    @Override
    protected List<ImageAndVideoModel> doInBackground(Void... voids) {
        String[] columns = {MEDIA_ID_COLUMN,
                MEDIA_URL_COLUMN,
                MEDIA_TYPE_COLUMN,
                MEDIA_DATE_ADDED_COLUMN,
        };
        String selection;
        if (mMediaType != null) {
            if (mMediaType.equals(TYPE_IMAGE)) {
                selection = MEDIA_TYPE_COLUMN + "="
                        + MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;
            } else if (mMediaType.equals(TYPE_VIDEO)) {
                selection = MEDIA_TYPE_COLUMN + "="
                        + MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO;
            } else {
                selection = MEDIA_TYPE_COLUMN + "="
                        + MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE
                        + " OR "
                        + MEDIA_TYPE_COLUMN + "="
                        + MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO;
            }
        } else {
            selection = MEDIA_TYPE_COLUMN + "="
                    + MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE
                    + " OR "
                    + MEDIA_TYPE_COLUMN + "="
                    + MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO;
        }
        Uri queryUri = MediaStore.Files.getContentUri("external");
        Cursor cursor = mContext.getContentResolver().query(
                queryUri,
                columns,
                selection,
                null, // Selection args (none).
                MediaStore.Files.FileColumns.DATE_ADDED + " DESC" // Sort order.
        );
        if (cursor != null && cursor.getCount() >= 1) {
            int column_index_data = cursor.getColumnIndexOrThrow(MEDIA_URL_COLUMN);
            int column_index_id = cursor.getColumnIndexOrThrow(MEDIA_ID_COLUMN);
            int column_index_type = cursor.getColumnIndex(MEDIA_TYPE_COLUMN);
            String mediaPath;
            int type, id;

            while (cursor.moveToNext()) {
                mediaPath = cursor.getString(column_index_data);
                type = cursor.getInt(column_index_type);
                id = cursor.getInt(column_index_id);
                Log.d(TAG, "First time load media items got " + mediaPath);
                ImageAndVideoModel item = new ImageAndVideoModel();
                item.id = id;
                item.setUrl(mediaPath);
                item.setThumb(mediaPath);
                item.setMeDiaType(type);
                if (type == MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO) {
                    item.setVideoDur(GalleryUtils.getDuration(mContext, item.getUrl()));
                }
                mGalleryItemList.add(item);
                if (mGalleryItemList.size() % DEFAULT_SIZE_PAGE == 0) {
                    List<ImageAndVideoModel> tmp = new ArrayList<>(mGalleryItemList.subList(currentPage * DEFAULT_SIZE_PAGE, currentPage * DEFAULT_SIZE_PAGE + DEFAULT_SIZE_PAGE));
                    publishProgress(tmp);
                    currentPage++;
                }
            }
            cursor.close();
        }
        return mGalleryItemList;
    }

    @Override
    protected void onPostExecute(List<ImageAndVideoModel> imageAndVideoModels) {
        super.onPostExecute(imageAndVideoModels);
    }

    public interface GalleryLoaderCallback {
        void onLoadedGallery(List<ImageAndVideoModel> data);
    }
}

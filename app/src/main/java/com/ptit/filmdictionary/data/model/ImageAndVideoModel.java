package com.ptit.filmdictionary.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;


@Entity(tableName = "RickMediaImageAndVideoDraf")
public class ImageAndVideoModel implements Parcelable {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    public Integer id;

    @ColumnInfo(name = "url")
    private String url;

    @ColumnInfo(name = "meDiaType")
    private int meDiaType;

    @ColumnInfo(name = "isTopMedia") // để phân biệt là upload image ở top hay là hard media
    private int isTop;

    @ColumnInfo(name = "order") // để phân biệt là upload image ở top hay là hard media
    private int order;
    //sao deo thay tren nhanh build nhi
    boolean isSelect = false;
    private int numberPosition = -1;

    boolean isTemporary = false;

    //ThomPV add field
    private String desc;
    private String thumb;
    private long videoDur;



    String diskBitmapName;

    public ImageAndVideoModel() {
    }

    @Ignore
    public ImageAndVideoModel(String url, int meDiaType, boolean isSelect) {
        this.url = url;
        this.meDiaType = meDiaType;
        this.isSelect = isSelect;
    }

    @Ignore
    protected ImageAndVideoModel(Parcel in) {
        url = in.readString();
        meDiaType = in.readInt();
        isSelect = in.readByte() != 0;
        isTemporary = in.readByte() != 0;
        numberPosition = in.readInt();
        isTop = in.readInt();
        diskBitmapName = in.readString();
        order = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(url);
        dest.writeInt(meDiaType);
        dest.writeByte((byte) (isSelect ? 1 : 0));
        dest.writeByte((byte) (isTemporary ? 1 : 0));
        dest.writeInt(numberPosition);
        dest.writeInt(isTop);
        dest.writeString(diskBitmapName);
        dest.writeInt(order);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ImageAndVideoModel> CREATOR = new Creator<ImageAndVideoModel>() {
        @Override
        public ImageAndVideoModel createFromParcel(Parcel in) {
            return new ImageAndVideoModel(in);
        }

        @Override
        public ImageAndVideoModel[] newArray(int size) {
            return new ImageAndVideoModel[size];
        }
    };

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getMeDiaType() {
        return meDiaType;
    }

    public void setMeDiaType(int meDiaType) {
        this.meDiaType = meDiaType;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public String getDiskBitmapName() {
        return diskBitmapName;
    }

    public void setDiskBitmapName(String diskBitmapName) {
        this.diskBitmapName = diskBitmapName;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public int getNumberPosition() {
        return numberPosition;
    }

    public void setNumberPosition(int numberPosition) {
        this.numberPosition = numberPosition;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public long getVideoDur() {
        return videoDur;
    }

    public void setVideoDur(long videoDur) {
        this.videoDur = videoDur;
    }

    public int getIsTop() {
        return isTop;
    }

    public void setIsTop(int isTop) {
        this.isTop = isTop;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public boolean isTemporary() {
        return isTemporary;
    }

    public void setTemporary(boolean temporary) {
        isTemporary = temporary;
    }

    public ImageAndVideoModel cloneData() {
        ImageAndVideoModel tmp = new ImageAndVideoModel();
        tmp.setUrl(getUrl());
        tmp.setMeDiaType(getMeDiaType());
        tmp.setDesc(getDesc());
        tmp.setNumberPosition(getNumberPosition());
        tmp.setSelect(isSelect());
        tmp.setSelect(isTemporary());
        tmp.setThumb(getThumb());
        tmp.setIsTop(getIsTop());
        tmp.setOrder(getOrder());
        return tmp;
    }
}

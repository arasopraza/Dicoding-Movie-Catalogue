package com.smk.informatics.model;

import android.annotation.SuppressLint;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.smk.informatics.database.DatabaseContract;

import org.json.JSONObject;

import java.text.SimpleDateFormat;

import static android.provider.BaseColumns._ID;
import static com.smk.informatics.database.DatabaseContract.getColumnInt;
import static com.smk.informatics.database.DatabaseContract.getColumnString;

@SuppressWarnings("unused")
public class Movie implements Parcelable {
    private int mId;
    private String mType;
    private String mName;
    private String mRelease;
    private String mDesc;
    private String mPhoto;
    private String mPopularity;
    private String mVote;

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmType() {
        return mType;
    }

    public void setmType(String mType) {
        this.mType = mType;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmRelease() {
        return mRelease;
    }

    public void setmRelease(String mRelease) {
        this.mRelease = mRelease;
    }

    public String getmDesc() {
        return mDesc;
    }

    public void setmDesc(String mDesc) {
        this.mDesc = mDesc;
    }

    public String getmPhoto() {
        return mPhoto;
    }

    public void setmPhoto(String mPhoto) {
        this.mPhoto = mPhoto;
    }

    public String getmPopularity() {
        return mPopularity;
    }

    public void setmPopularity(String mPopularity) {
        this.mPopularity = mPopularity;
    }

    public String getmVote() {
        return mVote;
    }

    public void setmVote(String mVote) {
        this.mVote = mVote;
    }

    public Movie() {

    }

    public Movie(JSONObject object) {
        try {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("EEE, dd, mm, yyyy");
            @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
            int id = object.getInt("id");
            String description = object.getString("overview");
            String popularity = object.getString("popularity");
            String release = object.getString("release_date");
            String title = object.getString("title");
            String url_image = object.getString("poster_path");
            String vote = object.getString("vote_average");

            this.mId = id;
            this.mDesc = description;
            this.mPopularity = popularity;
            this.mRelease = formatter.format(dateFormat.parse(release));
            this.mName = title;
            this.mPhoto = url_image;
            this.mVote = vote;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("SameReturnValue")
    @Override
    public int describeContents() {
        return 0;
    }

    public Movie(Cursor cursor) {
        this.mId = getColumnInt(cursor, _ID);
        this.mType = getColumnString(cursor, DatabaseContract.MovieColumns.TYPE);
        this.mName = getColumnString(cursor, DatabaseContract.MovieColumns.TITLE);
        this.mRelease = getColumnString(cursor, DatabaseContract.MovieColumns.RELEASE_DATE);
        this.mDesc = getColumnString(cursor, DatabaseContract.MovieColumns.OVERVIEW);
        this.mPhoto = getColumnString(cursor, DatabaseContract.MovieColumns.URL_IMAGE);
        this.mPopularity = getColumnString(cursor, DatabaseContract.MovieColumns.POPULARITY);
        this.mVote = getColumnString(cursor, DatabaseContract.MovieColumns.VOTE_AVERAGE);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mId);
        dest.writeString(this.mType);
        dest.writeString(this.mName);
        dest.writeString(this.mRelease);
        dest.writeString(this.mDesc);
        dest.writeString(this.mPhoto);
        dest.writeString(this.mPopularity);
        dest.writeString(this.mVote);
    }

    private Movie(Parcel in) {
        this.mId = in.readInt();
        this.mType = in.readString();
        this.mName = in.readString();
        this.mRelease = in.readString();
        this.mDesc = in.readString();
        this.mPhoto = in.readString();
        this.mPopularity = in.readString();
        this.mVote = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}

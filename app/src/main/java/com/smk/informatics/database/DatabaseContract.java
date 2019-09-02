package com.smk.informatics.database;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {
    public static final String TABLE_MOVIE = "movie";
    public static final String AUTHORITY = "com.smk.informatics";
    private static final String SCHEME = "content";

    public static final class MovieColumns implements BaseColumns {
        static final String MOVIE_ID = "id";
        static final String TELEVISION_ID = "id";
        public static final String TYPE = "type";
        public static final String OVERVIEW = "overview";
        public static final String RELEASE_DATE = "release_date";
        public static final String TITLE = "title";
        public static final String URL_IMAGE = "poster_path";
        public static final String VOTE_AVERAGE = "vote_average";
        public static final String POPULARITY = "popularity";
        public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_MOVIE)
                .build();
    }

    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }
    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }
}

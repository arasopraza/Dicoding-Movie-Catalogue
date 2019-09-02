package com.smk.informatics.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.smk.informatics.model.Movie;
import com.smk.informatics.model.Television;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.smk.informatics.database.DatabaseContract.MovieColumns.MOVIE_ID;
import static com.smk.informatics.database.DatabaseContract.MovieColumns.OVERVIEW;
import static com.smk.informatics.database.DatabaseContract.MovieColumns.POPULARITY;
import static com.smk.informatics.database.DatabaseContract.MovieColumns.RELEASE_DATE;
import static com.smk.informatics.database.DatabaseContract.MovieColumns.TELEVISION_ID;
import static com.smk.informatics.database.DatabaseContract.MovieColumns.TITLE;
import static com.smk.informatics.database.DatabaseContract.MovieColumns.TYPE;
import static com.smk.informatics.database.DatabaseContract.MovieColumns.URL_IMAGE;
import static com.smk.informatics.database.DatabaseContract.MovieColumns.VOTE_AVERAGE;
import static com.smk.informatics.database.DatabaseContract.TABLE_MOVIE;

public class MovieHelper {
    private static final String DATABASE_TABLE = TABLE_MOVIE;
    private static DatabaseHelper sDataBaseHelper;
    private static MovieHelper sInstance;
    private static SQLiteDatabase sDatabase;

    private MovieHelper(Context context) {
        sDataBaseHelper = new DatabaseHelper(context);
    }

    public static MovieHelper getInstance(Context context) {
        if (sInstance == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (sInstance == null) {
                    sInstance = new MovieHelper(context);
                }
            }
        }
        return sInstance;
    }

    public void open() throws SQLException {
        sDatabase = sDataBaseHelper.getWritableDatabase();
    }

    public void close() {
        sDataBaseHelper.close();
        if (sDatabase.isOpen())
            sDatabase.close();
    }


    public ArrayList<Movie> getListFavoriteMovie(String type) {
        ArrayList<Movie> arrayList = new ArrayList<>();
        sDatabase = sDataBaseHelper.getReadableDatabase();
        Cursor cursor = sDatabase.query(DATABASE_TABLE,
                new String[]{_ID, TITLE, TYPE, OVERVIEW, POPULARITY, RELEASE_DATE, URL_IMAGE, VOTE_AVERAGE},
                TYPE + "=?",
                new String[]{type},
                null,
                null,
                _ID + " ASC",
                null);
        cursor.moveToFirst();
        Movie movie;
        if (cursor.getCount() > 0) {
            do {
                movie = new Movie();
                movie.setmId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                movie.setmName(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                movie.setmType(cursor.getString(cursor.getColumnIndexOrThrow(TYPE)));
                movie.setmDesc(cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW)));
                movie.setmPopularity(cursor.getString(cursor.getColumnIndexOrThrow(POPULARITY)));
                movie.setmRelease(cursor.getString(cursor.getColumnIndexOrThrow(RELEASE_DATE)));
                movie.setmPhoto(cursor.getString(cursor.getColumnIndexOrThrow(URL_IMAGE)));
                movie.setmVote(cursor.getString(cursor.getColumnIndexOrThrow(VOTE_AVERAGE)));
                arrayList.add(movie);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public ArrayList<Television> getListFavoriteTelevision(String type) {
        ArrayList<Television> arrayList = new ArrayList<>();
        sDatabase = sDataBaseHelper.getReadableDatabase();
        Cursor cursor = sDatabase.query(DATABASE_TABLE,
                new String[]{_ID, TITLE, TYPE, OVERVIEW, POPULARITY, RELEASE_DATE, URL_IMAGE, VOTE_AVERAGE},
                TYPE + "=?",
                new String[]{type},
                null,
                null,
                _ID + " ASC",
                null);
        cursor.moveToFirst();
        Television television;
        if (cursor.getCount() > 0) {
            do {
                television = new Television();
                television.setmId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                television.setmName(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                television.setmType(cursor.getString(cursor.getColumnIndexOrThrow(TYPE)));
                television.setmDesc(cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW)));
                television.setmPopularity(cursor.getString(cursor.getColumnIndexOrThrow(POPULARITY)));
                television.setmRelease(cursor.getString(cursor.getColumnIndexOrThrow(RELEASE_DATE)));
                television.setmPhoto(cursor.getString(cursor.getColumnIndexOrThrow(URL_IMAGE)));
                television.setmVote(cursor.getString(cursor.getColumnIndexOrThrow(VOTE_AVERAGE)));
                arrayList.add(television);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insertMovie(Movie movie) {
        ContentValues args = new ContentValues();
        args.put(_ID, movie.getmId());
        args.put(MOVIE_ID, movie.getmId());
        args.put(TITLE, movie.getmName());
        args.put(TYPE, movie.getmType());
        args.put(OVERVIEW, movie.getmDesc());
        args.put(POPULARITY, movie.getmPopularity());
        args.put(RELEASE_DATE, movie.getmRelease());
        args.put(URL_IMAGE, movie.getmPhoto());
        args.put(VOTE_AVERAGE, movie.getmVote());
        return sDatabase.insert(DATABASE_TABLE, null, args);
    }

    public long insertTelevision(Television television) {
        ContentValues args = new ContentValues();
        args.put(_ID, television.getmId());
        args.put(TELEVISION_ID, television.getmId());
        args.put(TITLE, television.getmName());
        args.put(TYPE, television.getmType());
        args.put(OVERVIEW, television.getmDesc());
        args.put(POPULARITY, television.getmPopularity());
        args.put(RELEASE_DATE, television.getmRelease());
        args.put(URL_IMAGE, television.getmPhoto());
        args.put(VOTE_AVERAGE, television.getmVote());
        return sDatabase.insert(DATABASE_TABLE, null, args);
    }

    public boolean CheckMovie(String id) throws SQLException {
        boolean isFavorite = false;
        sDatabase = sDataBaseHelper.getReadableDatabase();
        Cursor cursor = sDatabase.rawQuery("SELECT * from " + DATABASE_TABLE
                + " where " + MOVIE_ID + "=?", new String[]{id});
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                isFavorite = true;
            } while (cursor.moveToNext());
        }
        assert cursor != null;
        cursor.close();
        return isFavorite;
    }

    public int deleteMovie(int id) {
        return sDatabase.delete(DATABASE_TABLE, _ID + " = '" + id + "'", null);
    }

    public int deleteTelevision(int id) {
        return sDatabase.delete(DATABASE_TABLE, _ID + " = '" + id + "'", null);
    }

    public Cursor queryByIdProvider(String id) {
        return sDatabase.query(DATABASE_TABLE, null
                , _ID + " = ?"
                , new String[]{id}
                , null
                , null
                , null
                , null);
    }

    public Cursor queryProvider() {
        return sDatabase.query(DATABASE_TABLE
                , null
                , null
                , null
                , null
                , null
                , _ID + " ASC");
    }

    public long insertProvider(ContentValues values) {
        return sDatabase.insert(DATABASE_TABLE, null, values);
    }

    public int updateProvider(String id, ContentValues values) {
        return sDatabase.update(DATABASE_TABLE, values, _ID + " = ?", new String[]{id});
    }

    public int deleteProvider(String id) {
        return sDatabase.delete(DATABASE_TABLE, _ID + " = ?", new String[]{id});
    }
}
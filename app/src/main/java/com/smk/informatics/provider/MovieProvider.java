package com.smk.informatics.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.smk.informatics.database.MovieHelper;

import java.util.Objects;

import static com.smk.informatics.database.DatabaseContract.AUTHORITY;
import static com.smk.informatics.database.DatabaseContract.MovieColumns.CONTENT_URI;
import static com.smk.informatics.database.DatabaseContract.TABLE_MOVIE;

public class MovieProvider extends ContentProvider {
    private static final int MOVIE = 1;
    private static final int MOVIE_ID = 2;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private MovieHelper mMovieHelper;

    static {
        sUriMatcher.addURI(AUTHORITY, TABLE_MOVIE, MOVIE);
        sUriMatcher.addURI(AUTHORITY, TABLE_MOVIE + "/#", MOVIE_ID);
    }

    @Override
    public boolean onCreate() {
        mMovieHelper = MovieHelper.getInstance(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        mMovieHelper.open();
        Cursor cursor;
        switch (sUriMatcher.match(uri)) {
            case MOVIE:
                cursor = mMovieHelper.queryProvider();
                break;
            case MOVIE_ID:
                cursor = mMovieHelper.queryByIdProvider(uri.getLastPathSegment());
                break;
            default:
                cursor = null;
                break;
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        mMovieHelper.open();
        long added;
        if (sUriMatcher.match(uri) == MOVIE) {
            added = mMovieHelper.insertProvider(contentValues);
        } else {
            added = 0;
        }
        Objects.requireNonNull(getContext()).getContentResolver().notifyChange(CONTENT_URI, null);
        return Uri.parse(CONTENT_URI + "/" + added);
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String selection, @Nullable String[] selectionArgs) {
        mMovieHelper.open();
        int updated;
        if (sUriMatcher.match(uri) == MOVIE_ID) {
            updated = mMovieHelper.updateProvider(uri.getLastPathSegment(), contentValues);
        } else {
            updated = 0;
        }
        Objects.requireNonNull(getContext()).getContentResolver().notifyChange(CONTENT_URI, null);
        return updated;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        mMovieHelper.open();
        int deleted;
        if (sUriMatcher.match(uri) == MOVIE_ID) {
            deleted = mMovieHelper.deleteProvider(uri.getLastPathSegment());
        } else {
            deleted = 0;
        }
        Objects.requireNonNull(getContext()).getContentResolver().notifyChange(CONTENT_URI, null);
        return deleted;
    }
}

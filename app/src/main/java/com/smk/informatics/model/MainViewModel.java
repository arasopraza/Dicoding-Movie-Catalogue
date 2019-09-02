package com.smk.informatics.model;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.smk.informatics.database.MovieHelper;
import com.smk.informatics.rest.ApiClient;
import com.smk.informatics.rest.ApiInterface;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.smk.informatics.BuildConfig.API_KEY;

public class MainViewModel extends AndroidViewModel {
    private ApiInterface mApiInterface;
    private final MovieHelper mMovieHelper;
    private final MutableLiveData<ArrayList<Movie>> listMovies = new MutableLiveData<>();
    private final MutableLiveData<ArrayList<Movie>> listNowPlayingMovies = new MutableLiveData<>();
    private final MutableLiveData<ArrayList<Television>> listTelevisions = new MutableLiveData<>();
    private final MutableLiveData<ArrayList<Television>> listNowPlayingTv = new MutableLiveData<>();
    private final MutableLiveData<ArrayList<Movie>> listFavoriteMovie = new MutableLiveData<>();
    private final MutableLiveData<ArrayList<Television>> listFavoriteTelevision = new MutableLiveData<>();

    public void setMovie() {
        mApiInterface = ApiClient.getClient().create(ApiInterface.class);
        try {
            Call<String> authorized = mApiInterface.getDiscoverMovie(API_KEY, "en-US");
            final ArrayList<Movie> listItems = new ArrayList<>();
            authorized.enqueue(new Callback<String>() {
                @Override
                public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                    if (response.isSuccessful()) {

                        try {
                            JSONObject responseObject = new JSONObject(response.body());
                            JSONArray list = responseObject.getJSONArray("results");

                            for (int i = 0; i < list.length(); i++) {
                                JSONObject movie = list.getJSONObject(i);
                                Movie movies = new Movie(movie);
                                listItems.add(movies);
                            }
                            listMovies.postValue(listItems);
                        } catch (Exception e) {
                            Log.d("Exception", e.getMessage());

                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<String> call, @NonNull Throwable throwable) {

                }
            });
        } catch (Exception e) {
            System.out.println("error" + e);
        }

    }

    public void setNowPlayingMovie() {
        mApiInterface = ApiClient.getClient().create(ApiInterface.class);
        try {
            Call<String> authorized = mApiInterface.getNowPlayingMovie(API_KEY, "en-US");
            final ArrayList<Movie> listItems = new ArrayList<>();
            authorized.enqueue(new Callback<String>() {
                @Override
                public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                    if (response.isSuccessful()) {

                        try {
                            JSONObject responseObject = new JSONObject(response.body());
                            JSONArray list = responseObject.getJSONArray("results");

                            for (int i = 0; i < list.length(); i++) {
                                JSONObject movie = list.getJSONObject(i);
                                Movie movies = new Movie(movie);
                                listItems.add(movies);
                            }
                            listNowPlayingMovies.postValue(listItems);
                        } catch (Exception e) {
                            Log.d("Exception", e.getMessage());

                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<String> call, @NonNull Throwable throwable) {

                }
            });
        } catch (Exception e) {
            System.out.println("error" + e);
        }

    }

    public void setSearchMovie(String query) {
        mApiInterface = ApiClient.getClient().create(ApiInterface.class);
        try {
            Call<String> authorized = mApiInterface.getSearchMovie(API_KEY, "en-US", query);
            final ArrayList<Movie> listItems = new ArrayList<>();
            authorized.enqueue(new Callback<String>() {
                @Override
                public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                    if (response.isSuccessful()) {

                        try {
                            JSONObject responseObject = new JSONObject(response.body());
                            JSONArray list = responseObject.getJSONArray("results");

                            for (int i = 0; i < list.length(); i++) {
                                JSONObject movie = list.getJSONObject(i);
                                Movie movies = new Movie(movie);
                                listItems.add(movies);
                            }
                            listMovies.postValue(listItems);
                        } catch (Exception e) {
                            Log.d("Exception", e.getMessage());

                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<String> call, @NonNull Throwable throwable) {
                    Toast.makeText(getApplication(), "Something went wrong" + throwable, Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            System.out.println("error" + e);
        }

    }

    public void setTelevision() {
        mApiInterface = ApiClient.getClient().create(ApiInterface.class);
        try {
            Call<String> authorized = mApiInterface.getDiscoverTv(API_KEY, "en-US");
            final ArrayList<Television> listItems = new ArrayList<>();
            authorized.enqueue(new Callback<String>() {
                @Override
                public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                    if (response.isSuccessful()) {
                        try {
                            JSONObject responseObject = new JSONObject(response.body());
                            JSONArray list = responseObject.getJSONArray("results");

                            for (int i = 0; i < list.length(); i++) {
                                JSONObject television = list.getJSONObject(i);
                                Television televisions = new Television(television);
                                listItems.add(televisions);
                            }
                            listTelevisions.postValue(listItems);
                        } catch (Exception e) {
                            Log.d("Exception", e.getMessage());

                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<String> call, @NonNull Throwable throwable) {

                }
            });
        } catch (Exception e) {
            System.out.println("error" + e);
        }

    }

    public void setNowPlayingTelevision() {
        mApiInterface = ApiClient.getClient().create(ApiInterface.class);
        try {
            Call<String> authorized = mApiInterface.getNowPlayingTv(API_KEY, "en-US");
            final ArrayList<Television> listItems = new ArrayList<>();
            authorized.enqueue(new Callback<String>() {
                @Override
                public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                    if (response.isSuccessful()) {

                        try {
                            JSONObject responseObject = new JSONObject(response.body());
                            JSONArray list = responseObject.getJSONArray("results");

                            for (int i = 0; i < list.length(); i++) {
                                JSONObject tv = list.getJSONObject(i);
                                Television television = new Television(tv);
                                listItems.add(television);
                            }
                            listNowPlayingTv.postValue(listItems);
                        } catch (Exception e) {
                            Log.d("Exception", e.getMessage());

                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<String> call, @NonNull Throwable throwable) {

                }
            });
        } catch (Exception e) {
            System.out.println("error" + e);
        }

    }

    public void setSearchTelevision(String query) {
        mApiInterface = ApiClient.getClient().create(ApiInterface.class);
        try {
            Call<String> authorized = mApiInterface.getSearchTelevision(API_KEY, "en-US", query);
            final ArrayList<Television> listItems = new ArrayList<>();
            authorized.enqueue(new Callback<String>() {
                @Override
                public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                    if (response.isSuccessful()) {
                        try {
                            JSONObject responseObject = new JSONObject(response.body());
                            JSONArray list = responseObject.getJSONArray("results");

                            for (int i = 0; i < list.length(); i++) {
                                JSONObject television = list.getJSONObject(i);
                                Television televisions = new Television(television);
                                listItems.add(televisions);
                            }
                            listTelevisions.postValue(listItems);
                        } catch (Exception e) {
                            Log.d("Exception", e.getMessage());

                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<String> call, @NonNull Throwable throwable) {

                }
            });
        } catch (Exception e) {
            System.out.println("error" + e);
        }

    }

    public MainViewModel(@NonNull Application application) {
        super(application);
        this.mMovieHelper = MovieHelper.getInstance(application);
    }

    public void setMovieDatabase(String type){
       ArrayList<Movie> movie = mMovieHelper.getListFavoriteMovie(type);
       listFavoriteMovie.postValue(movie);
    }

    public void setTelevisionDatabase(String type){
        ArrayList<Television> television = mMovieHelper.getListFavoriteTelevision(type);
        listFavoriteTelevision.postValue(television);
    }

    public LiveData<ArrayList<Movie>> getMovieFavorite(String type){
        setMovieDatabase(type);
        return listFavoriteMovie;
    }

    public LiveData<ArrayList<Television>> getTelevisionFavorite(String type){
        setTelevisionDatabase(type);
        return listFavoriteTelevision;
    }

    public LiveData<ArrayList<Movie>> getMovies() {
        return listMovies;
    }

    public LiveData<ArrayList<Movie>> getNowPlayingMovie() {
        return listNowPlayingMovies;
    }

    public LiveData<ArrayList<Television>> getTelevisions() {
        return listTelevisions;
    }

    public LiveData<ArrayList<Television>> getNowPlayingTelevision() {
        return listNowPlayingTv;
    }

    public LiveData<ArrayList<Movie>> getSearchMovies() {
        return listMovies;
    }

    public LiveData<ArrayList<Television>> getSearchTv() {
        return listTelevisions;
    }
}

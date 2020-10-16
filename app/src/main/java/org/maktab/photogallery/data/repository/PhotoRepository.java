package org.maktab.photogallery.data.repository;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.reflect.TypeToken;

import org.maktab.photogallery.data.model.GalleryItem;
import org.maktab.photogallery.data.remote.NetworkParams;
import org.maktab.photogallery.data.remote.retrofit.FlickrService;
import org.maktab.photogallery.data.remote.retrofit.GetGalleryItemsDeserializer;
import org.maktab.photogallery.data.remote.retrofit.RetrofitInstance;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PhotoRepository {

    private static PhotoRepository sInstance;

    private FlickrService mFlickrService;
    private MutableLiveData<List<GalleryItem>> mPopularItemsLiveData = new MutableLiveData<>();
    private MutableLiveData<List<GalleryItem>> mSearchItemsLiveData = new MutableLiveData<>();

    public static PhotoRepository getInstance() {
        if (sInstance == null)
            sInstance = new PhotoRepository();

        return sInstance;
    }

    private PhotoRepository() {
        Type type = new TypeToken<List<GalleryItem>>(){}.getType();
        Object typeAdapter = new GetGalleryItemsDeserializer();

        Retrofit retrofit = RetrofitInstance.getInstance(type, typeAdapter);
        mFlickrService = retrofit.create(FlickrService.class);
    }

    public MutableLiveData<List<GalleryItem>> getPopularItemsLiveData() {
        return mPopularItemsLiveData;
    }

    public MutableLiveData<List<GalleryItem>> getSearchItemsLiveData() {
        return mSearchItemsLiveData;
    }

    public void fetchPopularItemsLiveDataApi() {
        Call<List<GalleryItem>> call =
                mFlickrService.listItems(NetworkParams.getPopularOptions());
        call.enqueue(new Callback<List<GalleryItem>>() {
            @Override
            public void onResponse(Call<List<GalleryItem>> call,
                                   Response<List<GalleryItem>> response) {
                mPopularItemsLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<GalleryItem>> call, Throwable t) {

            }
        });
    }

    public void fetchSearchItemsLiveDataApi(String query) {
        Call<List<GalleryItem>> call =
                mFlickrService.listItems(NetworkParams.getSearchOptions(query));
        call.enqueue(new Callback<List<GalleryItem>>() {
            @Override
            public void onResponse(Call<List<GalleryItem>> call,
                                   Response<List<GalleryItem>> response) {
                mSearchItemsLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<GalleryItem>> call, Throwable t) {

            }
        });
    }
}

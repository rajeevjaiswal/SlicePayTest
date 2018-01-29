
package com.rajeevjaiswal.mvp.ui.main;


import com.rajeevjaiswal.mvp.R;
import com.rajeevjaiswal.mvp.data.DataManager;
import com.rajeevjaiswal.mvp.data.model.Contact;
import com.rajeevjaiswal.mvp.data.model.PhotoResponse;
import com.rajeevjaiswal.mvp.ui.base.BasePresenter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.rajeevjaiswal.mvp.utils.FlickrConstants.API_KEY;
import static com.rajeevjaiswal.mvp.utils.FlickrConstants.PHOTOS_ENDPOINT;
import static com.rajeevjaiswal.mvp.utils.FlickrConstants.QUERY_PARAM_API_KEY;
import static com.rajeevjaiswal.mvp.utils.FlickrConstants.QUERY_PARAM_FORMAT;
import static com.rajeevjaiswal.mvp.utils.FlickrConstants.QUERY_PARAM_METHOD;
import static com.rajeevjaiswal.mvp.utils.FlickrConstants.QUERY_PARAM_NO_JSON_CALLBACK;
import static com.rajeevjaiswal.mvp.utils.FlickrConstants.QUERY_PARAM_PAGE;
import static com.rajeevjaiswal.mvp.utils.FlickrConstants.QUERY_PARAM_PER_PAGE;
import static com.rajeevjaiswal.mvp.utils.FlickrConstants.QUERY_PARAM_VALUE_JSON;
import static com.rajeevjaiswal.mvp.utils.FlickrConstants.QUERY_PARAM_VALUE_NO_JSON_CALLBACK;
import static com.rajeevjaiswal.mvp.utils.FlickrConstants.QUERY_PARAM_VALUE_PER_PAGE;


/**
 * Created by rajeev on 16/12/17.
 */

public class MainPresenter<V extends MainMvpView> extends BasePresenter<V> implements MainMvpPresenter<V> {

    private static final String TAG = MainPresenter.class.getSimpleName();
    private int page = 1;

    @Inject
    public MainPresenter(DataManager dataManager) {
        super(dataManager);
    }


    @Override
    public void fetchImages() {

        showLoading();

        Map<String, String> queryMap = new HashMap<>();
        queryMap.put(QUERY_PARAM_METHOD, PHOTOS_ENDPOINT);
        queryMap.put(QUERY_PARAM_API_KEY, API_KEY);
        queryMap.put(QUERY_PARAM_PER_PAGE, QUERY_PARAM_VALUE_PER_PAGE);
        queryMap.put(QUERY_PARAM_PAGE, String.valueOf(page));
        queryMap.put(QUERY_PARAM_FORMAT, QUERY_PARAM_VALUE_JSON);
        queryMap.put(QUERY_PARAM_NO_JSON_CALLBACK, QUERY_PARAM_VALUE_NO_JSON_CALLBACK);

            getmDataManager().getPhotos(queryMap).enqueue(new Callback<PhotoResponse>() {

                @Override
                public void onResponse(Call<PhotoResponse> call, Response<PhotoResponse> response) {

                    if(response.isSuccessful()){

                        getMvpView().updateImages(response.body().getPhotos().getPhoto());
                    }
                    hideLoading();
                }

                @Override
                public void onFailure(Call<PhotoResponse> call, Throwable t) {

                    hideLoading();

                }
            });

    }

    @Override
    public void onLoadNextPage(int page) {
        this.page = page + 1;
        fetchImages();
    }


    private void showLoading(){
        if(page > 1){
            getMvpView().showLazyLoading();
        }else {
            getMvpView().showLoading(R.string.loading_images);
        }
    }

    private void hideLoading(){
        if(page > 1){
            getMvpView().hideLazyLoading();
        }else {
            getMvpView().hideLoading();
        }
    }

}
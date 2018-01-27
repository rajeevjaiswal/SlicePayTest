
package com.rajeevjaiswal.mvp.di.module;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;

import com.rajeevjaiswal.mvp.data.model.Contact;
import com.rajeevjaiswal.mvp.data.model.Photo;
import com.rajeevjaiswal.mvp.di.ActivityContext;
import com.rajeevjaiswal.mvp.di.PerActivity;
import com.rajeevjaiswal.mvp.ui.main.ImageAdapter;
import com.rajeevjaiswal.mvp.ui.main.MainMvpPresenter;
import com.rajeevjaiswal.mvp.ui.main.MainMvpView;
import com.rajeevjaiswal.mvp.ui.main.MainPresenter;


import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

/**
 * Created by rajeev on 16/12/17.
 */

@Module
public class ActivityModule {

    private Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    @ActivityContext
    Context provideContext() {
        return activity;
    }

    @Provides
    Activity provideActivity() {
        return activity;
    }



    @Provides
    GridLayoutManager provideLayoutManager() {
        return new GridLayoutManager(activity,2);
    }


    @Provides
    @PerActivity
    MainMvpPresenter<MainMvpView> provideMainPresenter(MainPresenter<MainMvpView> presenter) {
        return presenter;
    }

    @Provides
    ImageAdapter provideImageAdapter() {return new ImageAdapter(new ArrayList<Photo>());}

}

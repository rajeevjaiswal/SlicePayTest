package com.rajeevjaiswal.mvp.ui.main;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.rajeevjaiswal.mvp.R;
import com.rajeevjaiswal.mvp.data.model.Photo;
import com.rajeevjaiswal.mvp.ui.base.BaseActivity;
import com.rajeevjaiswal.mvp.utils.EndlessRecyclerViewScrollListener;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements MainMvpView {

    @Inject
    MainMvpPresenter<MainMvpView> mPresenter;

    @Inject
    ImageAdapter mImageAdapter;

    @Inject
    GridLayoutManager mLayoutManager;

    @BindView(R.id.contact_recycler_view)
    RecyclerView  mRecyclerView;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    private SearchView searchView;
    private String searchQuery = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getActivityComponent().inject(this);
        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(this);
        setUp();
    }



    @Override
    protected void setUp() {

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mImageAdapter);
        setUpLoadMoreListener();
        loadImages();
    }

    /**
     * setting listener to get callback for load more
     */
    private void setUpLoadMoreListener() {
        mRecyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                if(searchQuery.length() < 1){
                    mPresenter.onLoadNextPage(page);
                }
            }

        });


    }


    private void loadImages() {

        if(isNetworkConnected()){
            mPresenter.fetchImages();
        }
        else {
            onError(R.string.connection_error);
        }
    }


    @Override
    protected void onDestroy() {
        mPresenter.onDetach();
        super.onDestroy();
    }



    @Override
    public void updateImages(List<Photo> photos) {
        mImageAdapter.addItems(photos);
    }

    @Override
    public void showLazyLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLazyLoading() {
        progressBar.setVisibility(View.GONE);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        // Associate searchable configuration with the SearchView
        initSearchView(menu);

        // listening to search query text change
        setUpSearchListener();

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setUpSearchListener() {

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                mImageAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                searchQuery = query;
                mImageAdapter.getFilter().filter(query);
                return false;
            }
        });
    }

    private void initSearchView(Menu menu) {

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

    }

    @Override
    public void onBackPressed() {

        // close search view on back button pressed
        if (!searchView.isIconified()) {
            searchQuery = "";
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }
}


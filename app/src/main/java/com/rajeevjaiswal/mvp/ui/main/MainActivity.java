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
import android.widget.LinearLayout;
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

    private SearchView searchView;

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
                Log.d("page",""+page);
                mPresenter.onLoadNextPage(page);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        // listening to search query text change
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
                mImageAdapter.getFilter().filter(query);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        // close search view on back button pressed
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }
}


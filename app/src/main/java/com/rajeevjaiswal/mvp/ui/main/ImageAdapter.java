package com.rajeevjaiswal.mvp.ui.main;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rajeevjaiswal.mvp.R;
import com.rajeevjaiswal.mvp.data.model.Contact;
import com.rajeevjaiswal.mvp.data.model.Photo;
import com.rajeevjaiswal.mvp.ui.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.rajeevjaiswal.mvp.utils.FlickrConstants.PHOTO_URL;

/**
 * Created by rajeev on 14/1/18.
 */

public class ImageAdapter extends RecyclerView.Adapter<BaseViewHolder> implements Filterable{

    private List<Photo> mPhotos;
    private List<Photo> mPhotosFiltered;

    public ImageAdapter(List<Photo> photos) {
        mPhotos = photos;
        mPhotosFiltered = photos;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.image_list, parent, false));
    }

    @Override
    public int getItemCount() {

      return mPhotosFiltered.size();

    }

    public void addItems(List<Photo> photos) {
        mPhotosFiltered.addAll(photos);
        notifyDataSetChanged();
    }

    public List<Photo> getPhotos() {
        return mPhotosFiltered;
    }

    public void clear() {
        mPhotos.clear();
//        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    mPhotosFiltered = mPhotos;
                } else {
                    List<Photo> filteredList = new ArrayList<>();
                    for (Photo row : mPhotos) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getTitle().toLowerCase().contains(charString.toLowerCase())) {
                            Log.d("title",row.getTitle());
                            filteredList.add(row);
                        }
                    }

                    mPhotosFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mPhotosFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mPhotosFiltered = (ArrayList<Photo>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }


    public class ViewHolder extends BaseViewHolder {

        @BindView(R.id.tv_name)
        TextView title;

        @BindView(R.id.iv_image)
        ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        protected void clear() {

        }


        public void onBind(final int position) {
            super.onBind(position);

            final Photo photo = mPhotosFiltered.get(position);

            String photoUrl = String.format(PHOTO_URL, photo.getFarm(), photo.getServer(),
                    photo.getId(), photo.getSecret());

            Glide.with(itemView.getContext()).load(photoUrl).placeholder(R.drawable.drawable_placeholder).error(
                    R.drawable.drawable_placeholder).into(image);

            title.setText(photo.getTitle());

        }
    }


}

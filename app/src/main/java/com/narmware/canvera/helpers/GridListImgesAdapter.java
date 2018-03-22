package com.narmware.canvera.helpers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.narmware.canvera.R;
import com.narmware.canvera.activity.FeaturedGalleryActivity;
import com.narmware.canvera.pojo.TopTakes;
import com.squareup.picasso.Picasso;

import java.util.List;

public class GridListImgesAdapter extends RecyclerView.Adapter {

    public interface Callbacks {
        public void onClickLoadMoreImages();
    }

    private Callbacks mCallbacks;
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private static final int TYPE_FOOTER = 2;

    private boolean mWithHeader = false;
    private boolean mWithFooter = false;
    private List<TopTakes> mFeedList;
    Context mContext;


    public GridListImgesAdapter(List<TopTakes> feedList, Context mContext) {
        this.mFeedList = feedList;
        this.mContext=mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = null;

        if (viewType == TYPE_HEADER) {

            itemView = View.inflate(parent.getContext(), R.layout.item_video, null);
            return new LoadMoreViewHolder(itemView);

        } else {

            if(SharedPreferencesHelper.getIsGrid(mContext)==true) {
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grid_img, parent, false);
            }
            if(SharedPreferencesHelper.getIsGrid(mContext)==false) {
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_img, parent, false);
            }            return new ElementsViewHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof LoadMoreViewHolder) {

            LoadMoreViewHolder loadMoreViewHolder = (LoadMoreViewHolder) holder;

            loadMoreViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mCallbacks!=null)
                        mCallbacks.onClickLoadMoreImages();
                }
            });

        } else {
            ElementsViewHolder elementsViewHolder = (ElementsViewHolder) holder;

            TopTakes elements = mFeedList.get(position);
            Picasso.with(mContext)
                    .load(elements.getUrl())
                    .fit()
                    .centerCrop()
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(elementsViewHolder.icon);

            elementsViewHolder.mItem=elements;
        }

    }

    @Override
    public int getItemCount() {
        int itemCount = mFeedList.size();
        if (mWithHeader)
            itemCount++;
        if (mWithFooter)
            itemCount++;
        return itemCount;
    }

    @Override
    public int getItemViewType(int position) {
        if (mWithHeader && isPositionHeader(position))
            return TYPE_HEADER;
        if (mWithFooter && isPositionFooter(position))
            return TYPE_FOOTER;
        return TYPE_ITEM;
    }

    public boolean isPositionHeader(int position) {
        return position == 0 && mWithHeader;
    }

    public boolean isPositionFooter(int position) {
        return position == getItemCount() - 1 && mWithFooter;
    }

    public void setWithHeader(boolean value){
        mWithHeader = value;
    }

    public void setWithFooter(boolean value){
        mWithFooter = value;
    }

    public void setCallback(Callbacks callbacks){
        mCallbacks = callbacks;
    }

    public class ElementsViewHolder extends RecyclerView.ViewHolder {

        private ImageView icon;
        TopTakes mItem;

        public ElementsViewHolder(View itemView) {
            super(itemView);
            icon = (ImageView) itemView.findViewById(R.id.img_photo);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   // Toast.makeText(mContext, mItem.getUrl(), Toast.LENGTH_SHORT).show();
                    SharedPreferencesHelper.setTopType(Constants.IMAGE_TYPE,mContext);
                    Intent intent=new Intent(mContext, FeaturedGalleryActivity.class);
                    intent.putExtra(Constants.TOP_TYPE,Constants.IMAGE_TYPE);
                    Activity activity= (Activity) mContext;
                    activity.startActivity(intent);
                }
            });
        }
    }

    public class LoadMoreViewHolder  extends RecyclerView.ViewHolder {

        public LoadMoreViewHolder(View itemView) {
            super(itemView);

        }
    }
}
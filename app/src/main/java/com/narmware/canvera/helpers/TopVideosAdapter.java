package com.narmware.canvera.helpers;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.narmware.canvera.R;
import com.narmware.canvera.activity.SingleVideoActivity;
import com.narmware.canvera.pojo.VideoPojo2;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TopVideosAdapter extends RecyclerView.Adapter {

    public interface Callbacks {
        public void onClickLoadMoreVideos();
    }

    private Callbacks mCallbacks;
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private static final int TYPE_FOOTER = 2;

    private boolean mWithHeader = false;
    private boolean mWithFooter = false;
    private List<VideoPojo2> mFeedList;
    Context mContext;


    public TopVideosAdapter(List<VideoPojo2> feedList, Context mContext) {
        this.mFeedList = feedList;
        this.mContext=mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = null;

        if (viewType == TYPE_FOOTER) {

            itemView = View.inflate(parent.getContext(), R.layout.lay_load_more, null);
            return new LoadMoreViewHolder(itemView);

        } else {

            itemView = View.inflate(parent.getContext(), R.layout.item_video, null);
            return new ElementsViewHolder(itemView);
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
                        mCallbacks.onClickLoadMoreVideos();
                }
            });

        } else {
            ElementsViewHolder elementsViewHolder = (ElementsViewHolder) holder;

            VideoPojo2 elements = mFeedList.get(position);
            String videoId = VideoPojo2.getVideoId(elements.getVpath());
            elementsViewHolder.videoId = videoId;

            Picasso.with(mContext)
                    .load("https://img.youtube.com/vi/" + videoId + "/0.jpg")
                    .fit()
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
        VideoPojo2 mItem;
        String videoId;

        public ElementsViewHolder(View itemView) {
            super(itemView);
            icon = (ImageView) itemView.findViewById(R.id.card_video_image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(mContext, mItem.getVpath(), Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(mContext, SingleVideoActivity.class);
                    i.putExtra("VIDEO_ID", videoId );
                    mContext.startActivity(i);

                }
            });
        }
    }

    public class LoadMoreViewHolder  extends RecyclerView.ViewHolder {

        public LoadMoreViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(mContext,"No more images available", Toast.LENGTH_SHORT).show();

                }
            });
        }
    }
}
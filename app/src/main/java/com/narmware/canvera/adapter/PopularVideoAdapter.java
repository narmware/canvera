package com.narmware.canvera.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.narmware.canvera.R;
import com.narmware.canvera.activity.SingleVideoActivity;
import com.narmware.canvera.pojo.VideoPojo2;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


/**
 * Created by savvy on 12/16/2017.
 */

public class PopularVideoAdapter extends RecyclerView.Adapter<PopularVideoAdapter.PopularHolder> {
    private Context mContext;
    private ArrayList<VideoPojo2> mData;
    int[] mBackColorArray = {R.color.red_400_trans, R.color.pink_300_trans,R.color.purple_300_trans, R.color.indigo_300_trans,
            R.color.blue_300_trans, R.color.green_300_trans, R.color.lime_A200_trans, R.color.deep_orange_300_trans};

    private static int sCount = 0;

    public PopularVideoAdapter(Context mContext, ArrayList<VideoPojo2> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public PopularHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_video, parent, false);
        return new PopularHolder(v);
    }

    @Override
    public void onBindViewHolder(PopularHolder holder, int position) {
        VideoPojo2 video = mData.get(position);
        //holder.mTitle.setText(video.getVtitle());
        holder.mVideo = video;
        String videoId = VideoPojo2.getVideoId(video.getVpath());
        holder.videoId = videoId;

        Picasso.with(mContext)
                .load("https://img.youtube.com/vi/" + videoId + "/0.jpg")
                .fit()
                .placeholder(R.drawable.loading)
                .error(R.drawable.ic_launcher_background)
                .into(holder.mThumbnail);

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class PopularHolder extends RecyclerView.ViewHolder {
        TextView mTitle;
        ImageView mThumbnail;
        String videoId;
        VideoPojo2 mVideo;
        public PopularHolder(View itemView) {
            super(itemView);
            //mTitle = itemView.findViewById(R.id.card_video_title);
            mThumbnail = itemView.findViewById(R.id.card_video_image);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(mContext, SingleVideoActivity.class);
                    i.putExtra("VIDEO_ID", videoId );
                    mContext.startActivity(i);
                }
            });
        }
    }
}

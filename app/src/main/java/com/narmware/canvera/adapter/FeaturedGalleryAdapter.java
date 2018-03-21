package com.narmware.canvera.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mzelzoghbi.zgallery.ZGallery;
import com.mzelzoghbi.zgallery.entities.ZColor;
import com.narmware.canvera.R;
import com.narmware.canvera.activity.SingleVideoActivity;
import com.narmware.canvera.helpers.Constants;
import com.narmware.canvera.helpers.SharedPreferencesHelper;
import com.narmware.canvera.pojo.TopTakes;
import com.narmware.canvera.pojo.VideoPojo2;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Lincoln on 31/03/16.
 */

public class FeaturedGalleryAdapter extends RecyclerView.Adapter<FeaturedGalleryAdapter.MyViewHolder> {

     List<TopTakes> photos;
    Context mContext;
    ArrayList<String> photoUrl;

    protected Dialog mNoConnectionDialog;

    /* FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
*/
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView mthumb_title;
       ImageView mImgFrame,mImgPlay;
        TopTakes mItem;
        LinearLayout mLinearItem;
        String videoId;

        public MyViewHolder(View view) {
            super(view);
            mthumb_title= view.findViewById(R.id.thumb_title);
            mImgFrame=view.findViewById(R.id.thumb_img);
            mImgPlay=view.findViewById(R.id.img_play);
            mLinearItem=view.findViewById(R.id.linear_item);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   // Toast.makeText(mContext, mItem.getUrl(), Toast.LENGTH_SHORT).show();
                    int position= (int) mLinearItem.getTag();

                    if(SharedPreferencesHelper.getTopType(mContext).equals(Constants.IMAGE_TYPE)) {

                        Activity activity = (Activity) mContext;
                        ZGallery.with(activity, photoUrl)
                                .setSelectedImgPosition(position)
                                .setToolbarTitleColor(ZColor.WHITE)
                                .setTitle("Hello")
                                .setToolbarColorResId(activity.getResources().getColor(R.color.red_600))
                                .show();
                    }

                    if(SharedPreferencesHelper.getTopType(mContext).equals(Constants.VIDEO_TYPE)) {
                        Intent i = new Intent(mContext, SingleVideoActivity.class);
                        i.putExtra("VIDEO_ID", videoId );
                        mContext.startActivity(i);
                    }
                    }
            });

        }
    }

    public FeaturedGalleryAdapter(Context context, List<TopTakes> photos, ArrayList<String> photoUrl) {
        this.photoUrl=photoUrl;
        this.mContext = context;
        this.photos = photos;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_featured_img_gallery, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        TopTakes photo = photos.get(position);

        String videoId = VideoPojo2.getVideoId(photo.getUrl());
        holder.videoId=videoId;

        if(SharedPreferencesHelper.getTopType(mContext).equals(Constants.IMAGE_TYPE)) {
            Picasso.with(mContext)
                    .load(photo.getUrl())
                    .fit()
                    .centerCrop()
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(holder.mImgFrame);
        }
        if(SharedPreferencesHelper.getTopType(mContext).equals(Constants.VIDEO_TYPE)) {
            Picasso.with(mContext)
                    .load("https://img.youtube.com/vi/" + videoId + "/0.jpg")
                    .fit()
                    .centerCrop()
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(holder.mImgFrame);

            holder.mImgPlay.setVisibility(View.VISIBLE);
        }
        holder.mLinearItem.setTag(position);
        holder.mthumb_title.setText(photo.getTitle());
        holder.mItem=photo;
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }



/*
    private void showNoConnectionDialog() {
        mNoConnectionDialog = new Dialog(mContext, android.R.style.Theme_Light_NoTitleBar_Fullscreen);
        mNoConnectionDialog.setContentView(R.layout.dialog_noconnectivity);
        mNoConnectionDialog.setCancelable(false);
        mNoConnectionDialog.show();

        Button exit = mNoConnectionDialog.findViewById(R.id.dialog_no_connec_exit);
        Button tryAgain = mNoConnectionDialog.findViewById(R.id.dialog_no_connec_try_again);

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AppCompatActivity act = (AppCompatActivity) mContext;
                act.finish();
            }
        });

        tryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new SendAlbumFrame().execute();
                mNoConnectionDialog.dismiss();
            }
        });
    }
*/

}
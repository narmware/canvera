package com.narmware.canvera.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.mzelzoghbi.zgallery.ZGallery;
import com.mzelzoghbi.zgallery.entities.ZColor;
import com.narmware.canvera.R;
import com.narmware.canvera.activity.SingleVideoActivity;
import com.narmware.canvera.helpers.Constants;
import com.narmware.canvera.helpers.SharedPreferencesHelper;
import com.narmware.canvera.pojo.GalleryItem;
import com.narmware.canvera.pojo.VideoPojo2;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Created by Lincoln on 31/03/16.
 */

public class GridImageAdapter extends RecyclerView.Adapter<GridImageAdapter.MyViewHolder> {

     List<GalleryItem> photos;
    Context mContext;
    ArrayList<String> photoUrl;
    int videoCounter=0;
    public class MyViewHolder extends RecyclerView.ViewHolder {
       ImageView mImgFrame;
       ImageView mImgPlay;
        GalleryItem mItem;
        RelativeLayout mLinearItem;
        String videoId;

        public MyViewHolder(View view) {
            super(view);
            mImgFrame=view.findViewById(R.id.img_gallery);
            mImgPlay=view.findViewById(R.id.img_play);
            mLinearItem=view.findViewById(R.id.linear_item);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(mContext,mItem.getImg_path(), Toast.LENGTH_SHORT).show();
                    int position= (int) mLinearItem.getTag();

                    if(mItem.getImg_type().equals(Constants.IMAGE_TYPE)) {
                        Activity activity = (Activity) mContext;
                        ZGallery.with(activity,photoUrl)
                                .setSelectedImgPosition(position-videoCounter)
                                .setToolbarTitleColor(ZColor.WHITE)
                                .setTitle("Hello")
                                .setToolbarColorResId(activity.getResources().getColor(R.color.red_600))
                                .show();

                    }

                    if(mItem.getImg_type().equals(Constants.VIDEO_TYPE)) {
                        Intent i = new Intent(mContext, SingleVideoActivity.class);
                        i.putExtra("VIDEO_ID", videoId );
                        mContext.startActivity(i);
                    }
                }
            });
        }
    }

    public GridImageAdapter(Context context, List<GalleryItem> photos,ArrayList<String> photoUrl) {
        this.photoUrl=photoUrl;
        this.mContext = context;
        this.photos = photos;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = null;
        if(SharedPreferencesHelper.getIsGrid(mContext)==true) {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grid_img, parent, false);
        }
        if(SharedPreferencesHelper.getIsGrid(mContext)==false) {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_img, parent, false);
        }

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        GalleryItem photo = photos.get(position);

        Log.e("PhotoAdapter url size",photos.size()+"");
        if(photo.getImg_type().equals(Constants.VIDEO_TYPE)) {
            String videoId = VideoPojo2.getVideoId(photo.getImg_path());
            holder.videoId=videoId;
            videoCounter++;

            Picasso.with(mContext)
                    .load("https://img.youtube.com/vi/" + videoId + "/0.jpg")
                    .fit()
                    .centerCrop()
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.ic_launcher_background)
                    .into(holder.mImgFrame);

            try {
                holder.mImgPlay.setVisibility(View.VISIBLE);
            }catch (Exception e)
            {}
        }

        if(photo.getImg_type().equals(Constants.IMAGE_TYPE)) {
            Picasso.with(mContext)
                    .load(photo.getImg_path())
                    .fit()
                    .centerCrop()
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(holder.mImgFrame);
        }
        holder.mLinearItem.setTag(position);
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
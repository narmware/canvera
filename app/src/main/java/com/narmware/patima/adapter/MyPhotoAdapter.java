package com.narmware.patima.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.narmware.patima.R;
import com.narmware.patima.activity.GalleryActivity;
import com.narmware.patima.helpers.Constants;
import com.narmware.patima.pojo.MyPhoto;
import com.squareup.picasso.Picasso;

import java.util.List;


/**
 * Created by Lincoln on 31/03/16.
 */

public class MyPhotoAdapter extends RecyclerView.Adapter<MyPhotoAdapter.MyViewHolder> {

     List<MyPhoto> photos;
    Context mContext;
    protected Dialog mNoConnectionDialog;

    /* FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
*/
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView mthumb_title,mthumb_Desc;
       ImageView mImgFrame;
       ImageButton mImgBtnShare;
        MyPhoto mItem;


        public MyViewHolder(View view) {
            super(view);
            mthumb_title= view.findViewById(R.id.txt_photo_name);
            mthumb_Desc= view.findViewById(R.id.txt_photo_desc);
            mImgFrame=view.findViewById(R.id.img_photo);
            mImgBtnShare=view.findViewById(R.id.btn_share);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   //Toast.makeText(mContext, mItem.getAlbum_id(), Toast.LENGTH_SHORT).show();

                    Intent intent=new Intent(mContext, GalleryActivity.class);
                    intent.putExtra(Constants.GALLERY_TITLE,mItem.getPhoto_title());
                    intent.putExtra(Constants.ALBUM_ID,mItem.getAlbum_id());
                    mContext.startActivity(intent);
                }
            });

            mImgBtnShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String shareBody = "Hi you can view my album \""+mItem.getPhoto_title()+"\" from app \""+mContext.getResources().getString(R.string.app_name)+"\" and use following album id and password in shared album\n"+
                            "\n" +
                            "albumId :"+ mItem.getAlbumname() +"\n"+
                            "password:"+mItem.getPassword()+"\n" +
                            "\n" +
                            "<app link>";
                    Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                    sharingIntent.setType("text/plain");
                    //sharingIntent.setType("image*/");
                    sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
                    sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                    mContext.startActivity(Intent.createChooser(sharingIntent,"Share Using"));
                }
            });
        }
    }

    public MyPhotoAdapter(Context context, List<MyPhoto> photos) {
        this.mContext = context;
        this.photos = photos;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_my_photobook, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        MyPhoto photo = photos.get(position);


        Picasso.with(mContext)
                .load(photo.getPhoto_path())
                .fit()
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .into(holder.mImgFrame);

        holder.mthumb_title.setText(photo.getPhoto_title());
        holder.mthumb_Desc.setText(photo.getPhoto_desc());
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
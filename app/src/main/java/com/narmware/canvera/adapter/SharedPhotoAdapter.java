package com.narmware.canvera.adapter;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.narmware.canvera.R;
import com.narmware.canvera.pojo.SharedPhoto;
import com.squareup.picasso.Picasso;

import java.util.List;



/**
 * Created by Lincoln on 31/03/16.
 */

public class SharedPhotoAdapter extends RecyclerView.Adapter<SharedPhotoAdapter.MyViewHolder> {

     List<SharedPhoto> photos;
    Context mContext;
    protected Dialog mNoConnectionDialog;

    /* FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
*/
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView mthumb_title;
       ImageView mImgFrame;
        SharedPhoto mItem;


        public MyViewHolder(View view) {
            super(view);
            mthumb_title= view.findViewById(R.id.txt_photo_name);
            mImgFrame=view.findViewById(R.id.img_photo);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                }
            });
        }
    }

    public SharedPhotoAdapter(Context context, List<SharedPhoto> photos) {
        this.mContext = context;
        this.photos = photos;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_shared_photobook, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        SharedPhoto frame = photos.get(position);


        Picasso.with(mContext)
                .load(frame.getPhoto_path())
                .fit()
                .placeholder(R.drawable.ic_launcher_background)
                .into(holder.mImgFrame);

        holder.mthumb_title.setText(frame.getPhoto_title());
        holder.mItem=frame;
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
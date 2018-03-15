package com.narmware.canvera.adapter;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.narmware.canvera.R;
import com.narmware.canvera.pojo.TopTakes;
import com.squareup.picasso.Picasso;

import java.util.List;


/**
 * Created by Lincoln on 31/03/16.
 */

public class TopTakesAdapter extends RecyclerView.Adapter<TopTakesAdapter.MyViewHolder> {

     List<TopTakes> photos;
    Context mContext;
    protected Dialog mNoConnectionDialog;

    /* FragmentManager fragmentManager;r
    FragmentTransaction fragmentTransaction;
*/
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView mthumb_title,mthumb_Desc;
       ImageView mImgFrame;
        TopTakes mItem;


        public MyViewHolder(View view) {
            super(view);
            mImgFrame=view.findViewById(R.id.img_photo);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(mContext, mItem.getUrl(), Toast.LENGTH_SHORT).show();

                }
            });
        }
    }

    public TopTakesAdapter(Context context, List<TopTakes> photos) {
        this.mContext = context;
        this.photos = photos;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_top_takes, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        TopTakes frame = photos.get(position);


        Picasso.with(mContext)
                .load(frame.getUrl())
                .fit()
                .placeholder(R.drawable.ic_launcher_background)
                .into(holder.mImgFrame);

        holder.mItem=frame;
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }


}
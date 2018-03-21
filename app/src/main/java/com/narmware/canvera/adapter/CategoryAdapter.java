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
import com.narmware.canvera.pojo.Category;
import com.narmware.canvera.support.customfonts.MyTextView;
import com.squareup.picasso.Picasso;

import java.util.List;


/**
 * Created by Lincoln on 31/03/16.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {

     List<Category> photos;
    Context mContext;
    protected Dialog mNoConnectionDialog;

    /* FragmentManager fragmentManager;r
    FragmentTransaction fragmentTransaction;
*/
    public class MyViewHolder extends RecyclerView.ViewHolder {
       ImageView mImgFrame;
       MyTextView mTxtTitle;
        Category mItem;


        public MyViewHolder(View view) {
            super(view);
            mImgFrame=view.findViewById(R.id.img_cat);
            mTxtTitle=view.findViewById(R.id.title);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(mContext, mItem.getCat_name(), Toast.LENGTH_SHORT).show();

                }
            });
        }

    }

    public CategoryAdapter(Context context, List<Category> photos) {
        this.mContext = context;
        this.photos = photos;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_category, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Category frame = photos.get(position);


        Picasso.with(mContext)
                .load(frame.getCat_img())
                .fit()
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .into(holder.mImgFrame);

        holder.mTxtTitle.setText(frame.getCat_name());
        holder.mItem=frame;
    }

    @Override
    public int getItemCount() {
        int size=photos.size();
        return size;
    }


}
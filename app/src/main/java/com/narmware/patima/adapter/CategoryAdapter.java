package com.narmware.patima.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.narmware.patima.R;
import com.narmware.patima.activity.GalleryActivity;
import com.narmware.patima.helpers.Constants;
import com.narmware.patima.pojo.Category;
import com.narmware.patima.support.customfonts.MyTextView;

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
        LinearLayout mLinearCat;

        public MyViewHolder(View view) {
            super(view);
            mImgFrame=view.findViewById(R.id.img_cat);
            mTxtTitle=view.findViewById(R.id.title);
            mLinearCat=view.findViewById(R.id.linear_cat);

                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                           // Toast.makeText(mContext, mItem.getCat_id(), Toast.LENGTH_SHORT).show();

                            if(mItem.is_available()==true) {
                                Intent intent = new Intent(mContext, GalleryActivity.class);
                                intent.putExtra(Constants.GALLERY_TITLE, mItem.getCat_name());
                                intent.putExtra(Constants.CAT_ID, mItem.getCat_id());
                                mContext.startActivity(intent);
                            }
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


        if(frame.is_available()==false)
        {
            holder.mTxtTitle.setTextColor(mContext.getResources().getColor(R.color.grey_400));

            if(frame.getCat_name().equals("Wedding & Pre Wedding"))
            {
                holder.mImgFrame.setImageResource(R.drawable.ic_wedding_off);
            }
            if(frame.getCat_name().equals("Babies & kids"))
            {
                holder.mImgFrame.setImageResource(R.drawable.ic_kid_off);
            }
            if(frame.getCat_name().equals("Fashion & Portfolio"))
            {
                holder.mImgFrame.setImageResource(R.drawable.ic_fashion_off);
            }
            if(frame.getCat_name().equals("Commercial & Object"))
            {
                holder.mImgFrame.setImageResource(R.drawable.ic_commercial_off);
            }
            if(frame.getCat_name().equals("Corporate Event"))
            {
                holder.mImgFrame.setImageResource(R.drawable.ic_corporate_off);
            }
            if(frame.getCat_name().equals("Travel & Portrait"))
            {
            holder.mImgFrame.setImageResource(R.drawable.ic_travel_off);
            }
            if(frame.getCat_name().equals("Nature & Wildlife"))
            {
                holder.mImgFrame.setImageResource(R.drawable.ic_nature_off);
            }
            if(frame.getCat_name().equals("Special Occasions"))
            {
                holder.mImgFrame.setImageResource(R.drawable.ic_special_off);
            }
        }
        else {
            holder.mImgFrame.setImageResource(frame.getCat_img());
        }
        /*Picasso.with(mContext)
                .load(frame.getCat_img())
                .fit()
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .into(holder.mImgFrame);*/

        holder.mTxtTitle.setText(frame.getCat_name());
        holder.mItem=frame;
    }

    @Override
    public int getItemCount() {
        int size=photos.size();
        return size;
    }


}
package com.narmware.canvera.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.GravityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.narmware.canvera.R;
import com.narmware.canvera.activity.BookAppointActivity;
import com.narmware.canvera.activity.FeedbackActivity;
import com.narmware.canvera.activity.FeaturedGalleryActivity;
import com.narmware.canvera.activity.HomeActivity;
import com.narmware.canvera.helpers.Constants;
import com.narmware.canvera.helpers.SharedPreferencesHelper;
import com.narmware.canvera.pojo.NavItem;
import com.narmware.canvera.support.customfonts.MyTextView;

import java.util.ArrayList;

/**
 * Created by rohitsavant on 22/03/18.
 */

public class NavigationListAdapter extends BaseAdapter
{

    ArrayList<NavItem> items;
    Context mContext;

    public NavigationListAdapter(ArrayList<NavItem> items, Context mContext) {
        this.items = items;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nav_list, parent, false);

        MyTextView mTitle=itemView.findViewById(R.id.nav_title);
        ImageView mImg=itemView.findViewById(R.id.nav_img);

        mTitle.setText(items.get(position).getTitle());
        mImg.setImageResource(items.get(position).getImg_id());

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HomeActivity.drawer.closeDrawer(GravityCompat.START);

                String title=items.get(position).getTitle();
                //Toast.makeText(mContext,items.get(position).getTitle(),Toast.LENGTH_SHORT).show();
                Activity activity= (Activity) mContext;

                if(title.equals(Constants.NAV_TOP_IMG))
                {
                    SharedPreferencesHelper.setTopType(Constants.IMAGE_TYPE,mContext);
                    Intent intent=new Intent(mContext, FeaturedGalleryActivity.class);
                    intent.putExtra(Constants.TOP_TYPE,Constants.IMAGE_TYPE);
                    activity.startActivity(intent);
                }

                if(title.equals(Constants.NAV_TOP_VID))
                {
                    SharedPreferencesHelper.setTopType(Constants.VIDEO_TYPE,mContext);
                    Intent intent=new Intent(mContext, FeaturedGalleryActivity.class);
                    intent.putExtra(Constants.TOP_TYPE,Constants.VIDEO_TYPE);
                    activity.startActivity(intent);
                }

                if(title.equals(Constants.NAV_BOOK_APNT))
                {
                    Intent intent=new Intent(mContext, BookAppointActivity.class);
                    activity.startActivity(intent);
                }

                if(title.equals(Constants.NAV_CONTACT))
                {
                    Intent intent=new Intent(mContext, FeedbackActivity.class);
                    activity.startActivity(intent);
                }

            }
        });
        return itemView;
    }
}

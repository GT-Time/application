
package com.gttime.android.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.gttime.android.component.Announcement;
import com.gttime.android.R;

import java.util.List;

public class AnnouncementListAdapter extends BaseAdapter {
    private Context context;
    private List<Announcement> noticeList;

    public AnnouncementListAdapter(Context context, List<Announcement> noticeList) {
        this.context = context;
        this.noticeList = noticeList;
    }

    @Override
    public int getCount() {
        return noticeList.size();
    }

    @Override
    public Object getItem(int position) {
        return noticeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v =  View.inflate(context, R.layout.announcement,null);
        TextView nameText = v.findViewById(R.id.nameText);
        TextView announcementText = v.findViewById(R.id.noticeText);
        TextView dateText = v.findViewById(R.id.dateText);

        announcementText.setText(noticeList.get(position).getContent());
        nameText.setText(noticeList.get(position).getName());
        dateText.setText(noticeList.get(position).getDate());
        v.setTag(noticeList.get(position).getContent());
        return v;
    }
}

package com.gttime.android.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;

import com.gttime.android.CallbackListener;
import com.gttime.android.R;
import com.gttime.android.component.Semester;

import java.util.List;

public class SemesterListAdapter extends BaseAdapter {
    private Context context;
    private List<Semester> semesterList;

    private int selected;
    private CallbackListener callbackListner;

    public SemesterListAdapter(Context context, List<Semester> semesterList, int selected) {
        this.context = context;
        this.semesterList = semesterList;
        this.selected = selected;
    }
    @Override
    public int getCount() {
        return semesterList.size();
    }

    @Override
    public Object getItem(int position) {
        return semesterList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public void setSelected(int selected) {
        this.selected = selected;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v =  View.inflate(context, R.layout.semester,null);
        final RadioButton semesterButton = v.findViewById(R.id.semesterID); // TODO: Make it settable only one selection
        semesterButton.setText(semesterList.get(position).getSemesterText()); // TODO: change it to semester text

        if (selected == position) semesterButton.setChecked(true);
        else {semesterButton.setChecked(false);}
        semesterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSelected(position);
                SemesterListAdapter.this.notifyDataSetChanged();
                callbackListner.callback(semesterList.get(position).getSemesterText()); // HACK
            }
        });

        v.setTag(semesterList.get(position).getSemesterText());
        return v;
    }

    public void setCallback(CallbackListener listener) {

        this.callbackListner = listener;

    }
}

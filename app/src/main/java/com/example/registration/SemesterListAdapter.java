package com.example.registration;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.List;

public class SemesterListAdapter extends BaseAdapter {
    private Context context;
    private List<Semester> semesterList;

    public SemesterListAdapter(Context context, List<Semester> semesterList) {
        this.context = context;
        this.semesterList = semesterList;
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v =  View.inflate(context, R.layout.semester,null);
        final RadioButton semesterButton = v.findViewById(R.id.semesterID); // TODO: Make it settable only one selection
        semesterButton.setText(semesterList.get(position).getSemesterID()); // TODO: change it to semester text
        v.setTag(semesterList.get(position).getSemesterText());

        semesterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callbackListner.callback(semesterButton.getText().toString()); // HACK
            }
        });

        return v;
    }

    // HACK
    private CallbackListener callbackListner;

    public void setCallback(CallbackListener listener) {
        this.callbackListner = listener;
    }
}

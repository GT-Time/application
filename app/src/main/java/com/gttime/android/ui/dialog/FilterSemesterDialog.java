package com.gttime.android.ui.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.gttime.android.CallbackListener;
import com.gttime.android.R;
import com.gttime.android.component.Semester;
import com.gttime.android.ui.adapter.SemesterListAdapter;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

public class FilterSemesterDialog extends BottomSheetDialogFragment {
    public static final String TAG = "FilterSemeterDialog";
    public static FilterSemesterDialog newInstance() {
        return new FilterSemesterDialog();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_bottomsheet, container, false);
    }

    private ListView semesterView;
    private SemesterListAdapter semesterListAdapter;
    private List<Semester> semesterList;
    private CallbackListener callbackListener;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String[] text = getResources().getStringArray(R.array.semesterText);
        String[] id = getResources().getStringArray(R.array.semesterID);

        semesterList = new ArrayList<Semester>();
        semesterView = getView().findViewById(R.id.semesterID);
        semesterListAdapter = new SemesterListAdapter(getContext().getApplicationContext(), semesterList);
        semesterListAdapter.setCallback(callbackListener);
        semesterView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        semesterView.setAdapter(semesterListAdapter);

        int num = Math.min(text.length, id.length);
        for (int i = 0; i < num; i++) semesterList.add(new Semester(text[i], id[i]));
        semesterListAdapter.notifyDataSetChanged();
    }

    public void setCallback(CallbackListener listener) {

        this.callbackListener = listener;

    }
}

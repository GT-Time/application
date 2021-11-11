package com.gttime.android.ui.fragment;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.gttime.android.CallbackListener;
import com.gttime.android.component.Course;
import com.gttime.android.ui.dialog.FilterSemesterDialog;
import com.gttime.android.R;
import com.gttime.android.ui.adapter.StatisticsCourseListAdapter;
import com.gttime.android.util.json.JsonReader;
import com.gttime.android.util.json.JsonUtil;
import com.gttime.android.util.util.Util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StatisticsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StatisticsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FilterSemesterDialog filterSemesterDialog;

    private ListView courseListView;
    private StatisticsCourseListAdapter adapter;
    private List<Course> courseList;

    public static int totalCredit = 0;
    public static TextView statCredit;
    public TextView semesterText;

    private LinearLayout filterSemesterButton;
    private Map<String, String> semester;

    private String semesterID;

    public StatisticsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StatisticsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StatisticsFragment newInstance(String param1, String param2) {
        StatisticsFragment fragment = new StatisticsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        if(savedInstanceState != null) {
            this.semesterID = savedInstanceState.getString("semesterID");
        }

        else {
            this.semesterID = getResources().getStringArray(R.array.semesterText)[0];
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_statistics, container, false);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("semesterID", semesterText.getText().toString());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // HACK: create class that maps it automatically
        String[] text = getResources().getStringArray(R.array.semesterText);
        String[] id = getResources().getStringArray(R.array.semesterID);
        semester = new HashMap<String, String>();
        for (int i = 0; i < Math.min(text.length, id.length); i++) semester.put(text[i], id[i]);

        filterSemesterButton = getView().findViewById(R.id.statisticFilter);
        filterSemesterDialog = new FilterSemesterDialog();
        filterSemesterDialog.setCallback(new CallbackListener() {
            @Override
            public void callback(String filter) {
                setSemester(filter);
                adapter.setSemester(semester.get(semesterText.getText()));
                new BackgroundTask().execute();
            }
        });

        statCredit = getView().findViewById(R.id.totalCredit);
        semesterText = getView().findViewById(R.id.semesterText);
        semesterText.setText(semesterID);

        courseListView = getView().findViewById(R.id.courseListView);
        courseList = new ArrayList<Course>();
        adapter = new StatisticsCourseListAdapter(getContext().getApplicationContext(), courseList, semester.get(semesterText.getText()),this);
        courseListView.setAdapter(adapter);

        filterSemesterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterSemesterDialog.show(getActivity().getSupportFragmentManager(), FilterSemesterDialog.TAG);
            }
        });

        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading");
        progressDialog.show();
        new BackgroundTask().execute();
        progressDialog.dismiss();
        progressDialog.hide();
        totalCredit = 0;
    }

    // TODO: complete statistics fragment fetch by semester
    class BackgroundTask extends AsyncTask {
        String filename;
        @Override
        protected void onPreExecute() {
            try {
                filename = semester.get(semesterText.getText());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        @Override
        protected String doInBackground(Object[] objects) {

            return JsonUtil.readJson(new File(getActivity().getFilesDir(), Util.getFileName(filename)));
        }

        @Override
        protected void onProgressUpdate(Object[] values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Object o) {
            courseList.clear();
            totalCredit = 0;

            courseList.addAll(new JsonReader().fetchCourse((String) o));

            for(int i = 0; i < courseList.size(); i++) totalCredit += Util.parseInt(courseList.get(i).getCourseCredit().split(" ")[0]);

            adapter.notifyDataSetChanged();
            statCredit.setText(totalCredit + " Credits");
        }
    }


    public void setSemester(String semester) {
        this.semesterID = semester;
        this.semesterText.setText(semesterID);

    }

}

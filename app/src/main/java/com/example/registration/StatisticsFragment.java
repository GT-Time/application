package com.example.registration;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_statistics, container, false);
    }


    private ListView courseListView;
    private StatisticsCourseListAdapter adapter;
    private List<Course> courseList;

    public static int totalCredit = 0;
    public static TextView statCredit;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        statCredit = (TextView) getView().findViewById(R.id.totalCredit);
        courseListView = (ListView) getView().findViewById(R.id.courseListView);
        courseList = new ArrayList<Course>();
        adapter = new StatisticsCourseListAdapter(getContext().getApplicationContext(),courseList,this);
        courseListView.setAdapter(adapter);

        new BackgroundTask().execute();
        totalCredit = 0;
    }

    class BackgroundTask extends AsyncTask {
        String address;
        ProgressDialog dialog = new ProgressDialog(getActivity());
        @Override
        protected void onPreExecute() {
            try {
                address = "http://sch1261315.cafe24.com/StatisticsCourseList.php?userID=" + URLEncoder.encode(MainActivity.userID, "UTF-8");
                dialog.setMessage("Loading");
                dialog.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        @Override
        protected String doInBackground(Object[] objects) {
            try {
                URL url = new URL(address);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                int status = httpURLConnection.getResponseCode();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader buffer = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                String temp;
                while((temp = buffer.readLine()) != null) {
                    stringBuilder.append(temp + "\n");
                }

                buffer.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();
            }
            catch(Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Object[] values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Object o) {
            try {
                String result = (String) o;
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonResponse = jsonObject.getJSONArray("response");

                int index = 0;
                String courseNumber;
                String courseTitle;
                String courseDivide;
                String courseTime;
                while(index < jsonResponse.length()) {
                    JSONObject object = jsonResponse.getJSONObject(index);
                    courseNumber = object.getString("courseNumber");
                    courseTitle = object.getString("courseTitle");
                    courseDivide = object.getString("courseDivide");
                    courseTime = object.getString("courseTime");
                    int courseCredit = object.getInt("courseCredit");
                    courseList.add(new Course(courseNumber,courseTitle,courseDivide,courseCredit,courseTime));
                    totalCredit+= courseCredit;
                    ++index;
                }
                adapter.notifyDataSetChanged();
                statCredit.setText(totalCredit + " Credits");

                dialog.dismiss();
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    public boolean alreadyIn(List<String> courseIDList, String item) {
        for(int i = 0; i < courseIDList.size(); i++) {
            if(courseIDList.get(i) == item) {
                return false;
            }
        }
        return true;
    }
}

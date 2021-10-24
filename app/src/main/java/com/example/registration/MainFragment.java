package com.example.registration;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public MainFragment() {

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CourseFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CourseFragment newInstance(String param1, String param2) {
        CourseFragment fragment = new CourseFragment();
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

    private List<Announcement> announcementList;
    private AnnouncementListAdapter adapter;
    private ListView announcementListView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        announcementListView = getView().findViewById(R.id.AnnouncementList);
        announcementList = new ArrayList<Announcement>();
        adapter = new AnnouncementListAdapter(getContext().getApplicationContext(),announcementList);
        announcementListView.setAdapter(adapter);

        final LinearLayout announcement = (LinearLayout) getView().findViewById(R.id.Announcement);

        //new AnnouncementTask().execute();
    }

    class AnnouncementTask extends AsyncTask {
        String address;
        @Override
        protected void onPreExecute() {
            address = "http://ec2-3-222-117-117.compute-1.amazonaws.com/AnnouncementList.php";
        }

        @Override
        protected String doInBackground(Object[] objects) {
            try {
                URL url = new URL(address);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
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
                JSONArray jsonResponse = jsonObject.getJSONArray("announcement");
                boolean success = jsonObject.getBoolean("success");
                int errorCode = jsonObject.getInt("errorCode");

                int index = 0;
                String announcementContent;
                String announcementTitle;
                String announcementDate;

                while(index < jsonResponse.length()) {
                    JSONObject object = jsonResponse.getJSONObject(index);
                    announcementContent = object.getString("announcementContent");
                    announcementTitle = object.getString("announcementTitle");
                    announcementDate = object.getString("announcementDate");
                    announcementList.add(new Announcement(announcementContent,announcementTitle,announcementDate));
                    index++;
                }

            }
            catch(Exception e) {
                e.printStackTrace();
            }
            adapter.notifyDataSetChanged();
        }
    }
}
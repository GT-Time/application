package com.example.registration;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

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
 * Use the {@link CourseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CourseFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CourseFragment() {
        // Required empty public constructor
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

    private Spinner yearSpinner;
    private ArrayAdapter termAdapter;
    private Spinner termSpinner;
    private ArrayAdapter areaAdapter;
    private Spinner areaSpinner;
    private ArrayAdapter majorAdapter;
    private Spinner majorSpinner;

    private String courseUniversity = "";

    private ListView courseListView;
    private CourseListAdapter adapter;
    private List<Course> courseList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_course, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final RadioGroup universityGroupID = (RadioGroup) getView().findViewById(R.id.universityGroupID);
        termSpinner = (Spinner) getView().findViewById(R.id.semesterID);
        majorSpinner = (Spinner) getView().findViewById(R.id.majorID);
        areaSpinner = (Spinner) getView().findViewById(R.id.courseAreaID);

        universityGroupID.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                final RadioButton gradeID = (RadioButton) getView().findViewById(checkedId);
                courseUniversity = gradeID.getText().toString();

                if(courseUniversity.equals("Undergraduate")) {
                    termAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.semester, android.R.layout.simple_spinner_dropdown_item);
                    termSpinner.setAdapter(termAdapter);
                    majorAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.major,android.R.layout.simple_spinner_dropdown_item);
                    majorSpinner.setAdapter(majorAdapter);
                }

                else if(courseUniversity.equals("Graduate")) {
                    termAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.semester, android.R.layout.simple_spinner_dropdown_item);
                    termSpinner.setAdapter(termAdapter);
                    majorAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.graduateMajor,android.R.layout.simple_spinner_dropdown_item);  // to be updated : graduateMajor value
                    majorSpinner.setAdapter(majorAdapter);
                }
            }
        });
        /*
        areaAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.undergraduateCourseType, android.R.layout.simple_spinner_dropdown_item);
        areaSpinner.setAdapter(areaAdapter);*/
    majorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            /*
                Continue work : Retrieve course area with specified courseMajor from database
             */
            switch(majorSpinner.getSelectedItem().toString()) {
                case "":
                    break;
            }
            /*
            if(areaSpinner.getSelectedItem().equals("Major")) {
                majorAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.major,android.R.layout.simple_spinner_dropdown_item);
                majorSpinner.setAdapter(majorAdapter);
            }
            else if(areaSpinner.getSelectedItem().equals("Electives")) {
                majorAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.undergraduateElective,android.R.layout.simple_spinner_dropdown_item);
                majorSpinner.setAdapter(majorAdapter);
            }

            else if(areaSpinner.getSelectedItem().equals("Major(Graduate)")) {
                majorAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.graduateMajor,android.R.layout.simple_spinner_dropdown_item);
                majorSpinner.setAdapter(majorAdapter);
            }
            */
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    });


    courseListView = (ListView) getView().findViewById(R.id.courseListID);
    courseList = new ArrayList<Course>();
    adapter = new CourseListAdapter(getContext().getApplicationContext(),courseList,this);
    courseListView.setAdapter(adapter);


    Button courseSearch = (Button) getView().findViewById(R.id.courseSearchButton);
    courseSearch.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            new BackgroundTask().execute();
        }
    });
    }



    /*
        Recall course Lists into the FrameView and replace fragment to courseListAdapter
     */
    class BackgroundTask extends AsyncTask {
        String address;
        ProgressDialog dialog = new ProgressDialog(getActivity());
        @Override
        protected void onPreExecute() {
            try {
                address = "http://ec2-3-237-63-241.compute-1.amazonaws.com/CourseList.php?courseUniversity="+ URLEncoder.encode(courseUniversity,"UTF-8")+" Semester"
                +"&courseTerm="+URLEncoder.encode(termSpinner.getSelectedItem().toString().substring(0,4),"UTF-8")+"&courseMajor="+URLEncoder.encode(majorSpinner.getSelectedItem().toString(),"UTF-8")
                +"&courseArea="+URLEncoder.encode(areaSpinner.getSelectedItem().toString(),"UTF-8");
                dialog.setMessage("Loading");
                dialog.show();
            }

            catch(Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            try {
                    StringBuilder stringBuilder = new StringBuilder();
                    URL url = new URL(address);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    int status = httpURLConnection.getResponseCode();
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
                    String temp;

                    while ((temp = br.readLine()) != null) {
                        stringBuilder.append(temp + "\n");
                    }
                    br.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    return stringBuilder.toString();
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
                courseList.clear();
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("response");

                int count = 0;

                //course variables
                String courseNumber;
                String courseUniversity;
                int courseYear;
                String courseTerm;
                String courseArea;
                String courseMajor;
                String courseTitle;
                int courseCredit;
                String courseProfessor;
                String courseTime;
                String courseLocation;
                String courseID;
                String courseDivide;

                while(count < jsonArray.length()) {
                    JSONObject object = jsonArray.getJSONObject(count);
                    courseNumber = object.getString("courseNumber");
                    courseUniversity = object.getString("courseUniversity");
                    courseYear = object.getInt("courseYear");
                    courseTerm = object.getString("courseTerm");
                    courseArea = object.getString("courseArea");
                    courseMajor = object.getString("courseMajor");
                    courseTitle = object.getString("courseTitle");
                    courseCredit = object.getInt("courseCredit");
                    courseProfessor = object.getString("courseProfessor");
                    courseTime = object.getString("courseTime");
                    courseLocation = object.getString("courseLocation");
                    courseID = object.getString("courseID");
                    courseDivide = object.getString("courseDivide");
                    Course course = new Course(courseNumber, courseUniversity, courseYear, courseTerm, courseArea, courseMajor, courseTitle, courseCredit, courseProfessor, courseTime ,courseLocation, courseID, courseDivide);
                    courseList.add(course);
                    count++;
                }

                if(count == 0) {
                    AlertDialog dialog;
                    AlertDialog.Builder builder = new AlertDialog.Builder(CourseFragment.this.getActivity());
                    dialog = builder.setMessage("There is not available course")
                                    .setPositiveButton("OK",null)
                                    .create();
                    dialog.show();
                }

                adapter.notifyDataSetChanged();
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }

    }
}

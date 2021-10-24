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
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.example.util.json.JsonUtil;
import com.example.util.util.Util;
import com.github.tlaabs.timetableview.Schedule;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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

    private RadioGroup universityGroupID;
    private ArrayAdapter termAdapter;
    private Spinner termSpinner;
    private ArrayAdapter areaAdapter;
    private Spinner areaSpinner;
    private ArrayAdapter subjectAdapter;
    private Spinner subjectSpinner;
    private ListView courseListView;
    private CourseListAdapter adapter;

    private Map<String, String> params;

    private String selectedUniversity;
    private List<Course> courseScheduleList;
    private Map<String, String> semester;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_course, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        selectedUniversity = "";
        params = new HashMap<String, String>();
        semester = new HashMap<String, String>();

        universityGroupID = getView().findViewById(R.id.universityGroupID);
        termSpinner = getView().findViewById(R.id.semesterID);
        subjectSpinner = getView().findViewById(R.id.subjectID);
        areaSpinner = getView().findViewById(R.id.courseAreaID);


        String[] text = getResources().getStringArray(R.array.semesterText);
        String[] id = getResources().getStringArray(R.array.semesterID);


        universityGroupID.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                final RadioButton gradeID = getView().findViewById(checkedId);
                selectedUniversity = gradeID.getText().toString();

                if(selectedUniversity.equals("Undergraduate")) {
                    termAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.semesterText, android.R.layout.simple_spinner_dropdown_item);
                    subjectAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.subject,android.R.layout.simple_spinner_dropdown_item);
                }

                else if(selectedUniversity.equals("Graduate")) {
                    termAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.semesterText, android.R.layout.simple_spinner_dropdown_item);
                    subjectAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.graduateMajor,android.R.layout.simple_spinner_dropdown_item);
                }

                termSpinner.setAdapter(termAdapter);
                subjectSpinner.setAdapter(subjectAdapter);
            }
        });

    subjectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                //TODO: Retrieve course area with specified courseMajor from database

            switch(subjectSpinner.getSelectedItem().toString()) {
                case "Accounting":
                    areaAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.Accounting,android.R.layout.simple_spinner_dropdown_item);
                    break;

                case "Aerospace Engineering":
                    areaAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.AerospaceEngineering,android.R.layout.simple_spinner_dropdown_item);
                    break;

                case "Air Force Aerospace Studies":
                    areaAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.AirForceAerospaceStudies,android.R.layout.simple_spinner_dropdown_item);
                    break;

                case "Applied Physiology":
                    areaAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.AppliedPhysiology,android.R.layout.simple_spinner_dropdown_item);
                    break;

                case "Applied Systems Engineering":
                    areaAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.AppliedSystemsEngineering,android.R.layout.simple_spinner_dropdown_item);
                    break;

                case "Arabic":
                    areaAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.Arabic,android.R.layout.simple_spinner_dropdown_item);
                    break;

                case "Architecture":
                    areaAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.Architecture,android.R.layout.simple_spinner_dropdown_item);
                    break;

                case "Biological Sciences":
                    areaAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.BiologicalSciences,android.R.layout.simple_spinner_dropdown_item);
                    break;

                case "Biology":
                    areaAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.Biology,android.R.layout.simple_spinner_dropdown_item);
                    break;

                case "Biomed Engr/Joint Emory PKU":
                    areaAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.BiomedEngrJointEmoryPKU,android.R.layout.simple_spinner_dropdown_item);
                    break;

                case "Biomedical Engineering":
                    areaAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.BiomedicalEngineering,android.R.layout.simple_spinner_dropdown_item);
                    break;

                case "Biomedical Engr/Joint Emory":
                    areaAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.BiomedicalEngrJointEmory,android.R.layout.simple_spinner_dropdown_item);
                    break;

                case "Bldg Construction-Professional":
                    areaAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.BldgConstructionProfessional,android.R.layout.simple_spinner_dropdown_item);
                    break;

                case "Building Construction":
                    areaAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.BuildingConstruction,android.R.layout.simple_spinner_dropdown_item);
                    break;

                case "Center Enhancement-Teach/Learn":
                    areaAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.CenterEnhancementTeachLearn,android.R.layout.simple_spinner_dropdown_item);
                    break;

                case "Chemical & Biomolecular Engr":
                    areaAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.ChemicalBiomolecularEngr,android.R.layout.simple_spinner_dropdown_item);
                    break;

                case "Chemistry":
                    areaAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.Chemistry,android.R.layout.simple_spinner_dropdown_item);
                    break;

                case "Chinese":
                    areaAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.Chinese,android.R.layout.simple_spinner_dropdown_item);
                    break;

                case "City Planning":
                    areaAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.CityPlanning,android.R.layout.simple_spinner_dropdown_item);
                    break;

                case "Civil and Environmental Engr":
                    areaAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.CivilandEnvironmentalEngr,android.R.layout.simple_spinner_dropdown_item);
                    break;

                case "College of Architecture":
                    areaAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.CollegeofArchitecture,android.R.layout.simple_spinner_dropdown_item);
                    break;

                case "College of Engineering":
                    areaAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.CollegeofEngineering,android.R.layout.simple_spinner_dropdown_item);
                    break;

                case "Computational Mod, Sim, & Data":
                    areaAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.ComputationalModSimData,android.R.layout.simple_spinner_dropdown_item);
                    break;

                case "Computational Science & Engr":
                    areaAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.ComputationalScienceEngr,android.R.layout.simple_spinner_dropdown_item);
                    break;

                case "Computer Science":
                    areaAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.ComputerScience,android.R.layout.simple_spinner_dropdown_item);
                    break;

                case "Cooperative Work Assignment":
                    areaAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.CooperativeWorkAssignment,android.R.layout.simple_spinner_dropdown_item);
                    break;

                case "Earth and Atmospheric Sciences":
                    areaAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.EarthandAtmosphericSciences,android.R.layout.simple_spinner_dropdown_item);
                    break;

                case "Economics":
                    areaAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.Economics,android.R.layout.simple_spinner_dropdown_item);
                    break;

                case "Elect & Comp Engr-Professional":
                    areaAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.ElectCompEngrProfessional,android.R.layout.simple_spinner_dropdown_item);
                    break;

                case "Electrical & Computer Engr":
                    areaAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.ElectricalComputerEngr,android.R.layout.simple_spinner_dropdown_item);
                    break;

                case "English":
                    areaAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.English,android.R.layout.simple_spinner_dropdown_item);
                    break;

                case "French":
                    areaAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.French,android.R.layout.simple_spinner_dropdown_item);
                    break;

                case "Georgia Tech":
                    areaAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.GeorgiaTech,android.R.layout.simple_spinner_dropdown_item);
                    break;

                case "Georgia Tech Lorraine":
                    areaAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.GeorgiaTechLorraine,android.R.layout.simple_spinner_dropdown_item);
                    break;

                case "German":
                    areaAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.German,android.R.layout.simple_spinner_dropdown_item);
                    break;

                case "Global Media and Cultures":
                    areaAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.GlobalMediaandCultures,android.R.layout.simple_spinner_dropdown_item);
                    break;

                case "Health Systems":
                    areaAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.HealthSystems,android.R.layout.simple_spinner_dropdown_item);
                    break;

                case "Hebrew":
                    areaAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.Hebrew,android.R.layout.simple_spinner_dropdown_item);
                    break;

                case "Hindi":
                    areaAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.Hindi,android.R.layout.simple_spinner_dropdown_item);
                    break;

                case "History":
                    areaAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.History,android.R.layout.simple_spinner_dropdown_item);
                    break;

                case "History, Technology & Society":
                    areaAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.HistoryTechnologySociety,android.R.layout.simple_spinner_dropdown_item);
                    break;

                case "Industrial & Systems Engr":
                    areaAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.IndustrialSystemsEngr,android.R.layout.simple_spinner_dropdown_item);
                    break;

                case "Industrial Design":
                    areaAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.IndustrialDesign,android.R.layout.simple_spinner_dropdown_item);
                    break;

                case "Int\\'l Plan Co-op Abroad":
                    areaAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.IntlPlanCoopAbroad,android.R.layout.simple_spinner_dropdown_item);
                    break;

                case "Int\'l Plan Intern Abroad":
                    areaAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.IntlPlanInternAbroad,android.R.layout.simple_spinner_dropdown_item);
                    break;

                case "Int\\'l Plan-Exchange Program":
                    areaAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.IntlPlanExchangeProgram,android.R.layout.simple_spinner_dropdown_item);
                    break;

                case "Int\\'l Plan-Study Abroad":
                    areaAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.IntlPlanStudyAbroad,android.R.layout.simple_spinner_dropdown_item);
                    break;

                case "International Affairs":
                    areaAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.InternationalAffairs,android.R.layout.simple_spinner_dropdown_item);
                    break;

                case "International Logistics":
                    areaAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.IntlExecutiveMBA,android.R.layout.simple_spinner_dropdown_item);
                    break;

                case "Internship":
                    areaAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.Internship,android.R.layout.simple_spinner_dropdown_item);
                    break;

                case "Intl Executive MBA":
                    areaAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.IntlExecutiveMBA,android.R.layout.simple_spinner_dropdown_item);
                    break;

                case "Ivan Allen College":
                    areaAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.IvanAllenCollege,android.R.layout.simple_spinner_dropdown_item);
                    break;

                case "Japanese":
                    areaAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.Japanese,android.R.layout.simple_spinner_dropdown_item);
                    break;

                case "Korean":
                    areaAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.Korean,android.R.layout.simple_spinner_dropdown_item);
                    break;

                case "Learning Support":
                    areaAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.LearningSupport,android.R.layout.simple_spinner_dropdown_item);
                    break;

                case "Linguistics":
                    areaAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.Linguistics,android.R.layout.simple_spinner_dropdown_item);
                    break;

                case "Literature, Media & Comm":
                    areaAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.LiteratureMediaComm,android.R.layout.simple_spinner_dropdown_item);
                    break;

                case "Management":
                    areaAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.Management,android.R.layout.simple_spinner_dropdown_item);
                    break;

                case "Management of Technology":
                    areaAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.ManagementofTechnology,android.R.layout.simple_spinner_dropdown_item);
                    break;

                case "Materials Science & Engr":
                    areaAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.MaterialsScienceEngr,android.R.layout.simple_spinner_dropdown_item);
                    break;

                case "Mathematics":
                    areaAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.Mathematics,android.R.layout.simple_spinner_dropdown_item);
                    break;

                case "Mechanical Engineering":
                    areaAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.MechanicalEngineering,android.R.layout.simple_spinner_dropdown_item);
                    break;

                case "Medical Physics":
                    areaAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.MedicalPhysics,android.R.layout.simple_spinner_dropdown_item);
                    break;

                case "Military Science & Leadership":
                    areaAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.MilitaryScienceLeadership,android.R.layout.simple_spinner_dropdown_item);
                    break;

                case "Modern Languages":
                    areaAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.ModernLanguages,android.R.layout.simple_spinner_dropdown_item);
                    break;

                case "Music":
                    areaAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.Music,android.R.layout.simple_spinner_dropdown_item);
                    break;

                case "Naval Science":
                    areaAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.NavalScience,android.R.layout.simple_spinner_dropdown_item);
                    break;

                case "Neuroscience":
                    areaAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.Neuroscience,android.R.layout.simple_spinner_dropdown_item);
                    break;

                case "Nuclear & Radiological Engr":
                    areaAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.NuclearRadiologicalEngr,android.R.layout.simple_spinner_dropdown_item);
                    break;

                case "Persian":
                    areaAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.Persian,android.R.layout.simple_spinner_dropdown_item);
                    break;

                case "Philosophy":
                    areaAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.Philosophy,android.R.layout.simple_spinner_dropdown_item);
                    break;

                case "Physics":
                    areaAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.Physics,android.R.layout.simple_spinner_dropdown_item);
                    break;

                case "Political Science":
                    areaAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.PoliticalScience,android.R.layout.simple_spinner_dropdown_item);
                    break;

                case "Polymer, Textile and Fiber Eng":
                    areaAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.PolymerTextileandFiberEng,android.R.layout.simple_spinner_dropdown_item);
                    break;

                case "Portuguese":
                    areaAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.Portuguese,android.R.layout.simple_spinner_dropdown_item);
                    break;

                case "Psychology":
                    areaAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.Psychology,android.R.layout.simple_spinner_dropdown_item);
                    break;

                case "Public Policy":
                    areaAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.PublicPolicy,android.R.layout.simple_spinner_dropdown_item);
                    break;

                case "Public Policy/Joint GSU PhD":
                    areaAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.PublicPolicyJointGSUPhD,android.R.layout.simple_spinner_dropdown_item);
                    break;

                case "Russian":
                    areaAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.Russian,android.R.layout.simple_spinner_dropdown_item);
                    break;

                case "Serve, Learn, Sustain":
                    areaAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.ServeLearnSustain,android.R.layout.simple_spinner_dropdown_item);
                    break;

                case "Sociology":
                    areaAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.Sociology,android.R.layout.simple_spinner_dropdown_item);
                    break;

                case "Spanish":
                    areaAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.Spanish,android.R.layout.simple_spinner_dropdown_item);
                    break;

                case "Swahili":
                    areaAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.Swahili,android.R.layout.simple_spinner_dropdown_item);
                    break;

                case "Vertically Integrated Project":
                    areaAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.VerticallyIntegratedProject,android.R.layout.simple_spinner_dropdown_item);
                    break;

                case "Wolof":
                    areaAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.Wolof,android.R.layout.simple_spinner_dropdown_item);
                    break;

            }
            areaSpinner.setAdapter(areaAdapter);

            updateParams();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            // TODO: Show Error dialog
        }
    });

    universityGroupID.check(R.id.undergraduateID);
    subjectSpinner.setSelection(0);
    termSpinner.setSelection(0);
    areaSpinner.setSelection(0);

    for (int i = 0; i < Math.min(text.length, id.length); i++) semester.put(text[i], id[i]);

    courseListView = getView().findViewById(R.id.courseListID);
    courseScheduleList = new ArrayList<Course>();
    adapter = new CourseListAdapter(getContext().getApplicationContext(), courseScheduleList, semester.get(termSpinner.getSelectedItem().toString()), this);
    courseListView.setAdapter(adapter);


    Button courseSearch = getView().findViewById(R.id.courseSearchButton);
    courseSearch.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ProgressDialog progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Loading");
            progressDialog.show();
            new BackgroundTask().execute();
            progressDialog.dismiss();
            progressDialog.hide();
            }
        });
    }

    class BackgroundTask extends AsyncTask {
        String target;

        @Override
        protected void onPreExecute() {
            try {
                target = "https://raw.githubusercontent.com/cshim31/crawler/data/" + URLEncoder.encode(semester.get(termSpinner.getSelectedItem().toString())) + ".json";
            }

            catch(Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            String json = Util.fetchRemote(target);
            return JsonUtil.filterCourse(json, params);
        }

        @Override
        protected void onProgressUpdate(Object[] values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Object o) {
            try {
                courseScheduleList.clear();
                JSONObject jsonObject = (JSONObject) o;
                JSONArray jsonArray = jsonObject.getJSONArray("response");

                int count = 0;

                String courseTerm;
                String courseMajor;
                String courseTitle;
                String courseCRN;
                String courseArea;
                String courseSection;
                String courseClass;
                String courseTime;
                String courseDay;
                String courseLocation;
                String courseInstructor;
                String courseUniversity;
                String courseCredit;
                String courseAttribute;

                while(count < jsonArray.length()) {
                    JSONObject object = jsonArray.getJSONObject(count);
                    courseTerm = object.getString("courseTerm");
                    courseMajor = object.getString("courseMajor");
                    courseTitle = object.getString("courseTitle");
                    courseCRN = object.getString("courseCRN");
                    courseArea = object.getString("courseArea");
                    courseSection = object.getString("courseSection");
                    courseClass = object.getString("courseClass");
                    courseTime = object.getString("courseTime");
                    courseDay = object.getString("courseDay");
                    courseLocation = object.getString("courseLocation");
                    courseInstructor = object.getString("courseInstructor");
                    courseUniversity = object.getString("courseUniversity");
                    courseCredit = object.getString("courseCredit");
                    courseAttribute = object.getString("courseAttribute");

                    courseScheduleList.add(new Course(courseTerm, courseDay, courseMajor, courseTitle, courseCRN, courseArea, courseSection, courseClass, courseTime, courseLocation, courseInstructor, courseUniversity, courseCredit, courseAttribute));
                    count++;
                }

                if(count == 0) {
                    AlertDialog alertDialog;
                    AlertDialog.Builder builder = new AlertDialog.Builder(CourseFragment.this.getActivity());
                    alertDialog = builder.setMessage("Lecture not found")
                            .setPositiveButton("OK",null)
                            .create();
                    alertDialog.show();
                }

                adapter.notifyDataSetChanged();
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void updateParams() {
//        params.put("courseUniversity", selectedUniversity);
        params.put("courseTerm", semester.get(termSpinner.getSelectedItem().toString()));
        params.put("courseMajor", subjectSpinner.getSelectedItem().toString());
        params.put("courseArea", areaSpinner.getSelectedItem().toString());
    }
}

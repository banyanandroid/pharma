package applicatiom.banyan1.job;

/**
 * Created by Parameswari on 11/01/16.
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import adapter.IMG_REQ_Lazy_Adapter;
import adapter.IMG_REQ_Lazy_Adapter_applied;
import gobal.AppConfig;


public class FragmentAppliedJobs extends Fragment {


    /*request volley*/


    private static final String TAG = "";

    private ProgressDialog pDialog;

    public static final String TAG_JOBID = "job_id";
    public static final String TAG_WANT = "position";
    public static final String TAG_COMPY = "company";
    public static final String TAG_POSTDATE = "posted_date";
    public static final String TAG_EXPERIENCE = "experience";
    public static final String TAG_LOCATION = "location";
    public static final String TAG_JOBDESC = "job_description";
    public static final String TAG_SKILLS = "skills";


    static ArrayList<HashMap<String, String>> complaint_list;

    HashMap<String, String> params = new HashMap<String, String>();

    public static RequestQueue queue;

    IMG_REQ_Lazy_Adapter_applied adapter;
    ListView list;
    private char PICK_REQUEST_CODE;

    public static String value_ji;
    public static String job_id;
    String username;
    public String uemail;

    ImageView imageView;

    public FragmentAppliedJobs() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragement_appliedjobs, container, false);


        complaint_list = new ArrayList<HashMap<String, String>>();
        list = (ListView) rootView.findViewById(R.id.listView2);
        Bundle args = getActivity().getIntent().getExtras();
        value_ji = args.getString("values");
        System.out.println("fish1" + value_ji);


        //image

        imageView = (ImageView) rootView.findViewById(R.id.image_banner);


        try {
            pDialog = new ProgressDialog(getContext());
            pDialog.setMessage("Please wait...");
            pDialog.show();
            pDialog.setCancelable(false);
            queue = Volley.newRequestQueue(getContext());

            makeJsonRequest();


            System.out.print("fufufu inside try");

        } catch (Exception e) {
            // TODO: handle exception
        }


        try {
           /* pDialog = new ProgressDialog(Activity_Select_Place.this);
            pDialog.setMessage("Please wait...");
            pDialog.show();
            pDialog.setCancelable(false);*/
            queue = Volley.newRequestQueue(getContext());
            makeJsonObjectRequest_image();
        } catch (Exception e) {
            // TODO: handle exception
        }


        System.out.print("fufufu inside out");

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long arg3) {
                // TODO Auto-generated method stub
                System.out.print("fufufu inside item");
                if (position != 0) {
                    Intent intent = new Intent(getActivity(), Activity_Joblist.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getActivity(), Activity_Joblist.class);
                    startActivity(intent);
                }


            }
        });
        return rootView;
    }


    private void makeJsonRequest() {

        StringRequest request = new StringRequest(Request.Method.POST,
                AppConfig.applied_jobs_url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());
                JSONObject jsonObject;
                try {

                    jsonObject = new JSONObject(response).getJSONObject("response");
                    JSONArray arr = jsonObject.getJSONArray("job_details");
                    System.out.println("hi inside_opop" + arr);

                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject jsonObj = arr.getJSONObject(i);
                        String position = jsonObj.getString(TAG_WANT);
                        String company = jsonObj.getString(TAG_COMPY);
                        String posted_date = jsonObj.getString(TAG_POSTDATE);
                        String experience = jsonObj.getString(TAG_EXPERIENCE);
                        String location = jsonObj.getString(TAG_LOCATION);
                        String job_description = jsonObj.getString(TAG_JOBDESC);
                        String skills = jsonObj.getString(TAG_SKILLS);
                        // creating new HashMap
                        HashMap<String, String> map = new HashMap<String, String>();

                        // adding each child node to HashMap key => value

                        map.put(TAG_WANT, position);
                        map.put(TAG_COMPY, company);
                        map.put(TAG_POSTDATE, posted_date);
                        map.put(TAG_EXPERIENCE, experience);
                        map.put(TAG_LOCATION, location);
                        map.put(TAG_JOBDESC, job_description);
                        map.put(TAG_SKILLS, skills);


                        complaint_list.add(map);
                        System.out.print("popo" + complaint_list);

                        System.out.println("HASHMAP ARRAY" + complaint_list);

                        adapter = new IMG_REQ_Lazy_Adapter_applied(getActivity(),
                                complaint_list);
                        System.out.println("tytyt" + adapter.getCount());
                        System.out.println("bhbhbh" + adapter);

                        list.setAdapter(adapter);

                        pDialog.dismiss();


                    }


                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                pDialog.hide();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Please Login your account.", Toast.LENGTH_LONG).show();
                Intent i = new Intent(getContext(), Activity_Select_Place.class);
                startActivity(i);
                pDialog.dismiss();

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                System.out.println("gugu1_jjjjjjjjjjjjjjjjjjjjjjjjjj" +
                        "" + Activity_login.email);
                params.put("email", Activity_login.email);


                return params;
            }

        };

        // Adding request to request queue
        queue.add(request);
    }


    // loading the location from json_image
    private void makeJsonObjectRequest_image() {


        System.out.println("method inside");

        StringRequest request = new StringRequest(Request.Method.GET,
                AppConfig.banner_image_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                JSONObject jsonObject;

                try {

                    jsonObject = new JSONObject(response).getJSONObject("response");
                    JSONArray arr = jsonObject.getJSONArray("result");
                    System.out.println("hi inside" + arr);

                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject jsonObj = arr.getJSONObject(i);
                        String image = jsonObj.getString("image");
                        System.out.println("pop" + image);
                        Picasso.with(getContext())
                                .load(image)
                                .into(imageView);
//                        pDialog.dismiss();


                    }


                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        },

                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("kiiikik" + error.toString());
                        Toast.makeText(getContext(), "Check your internet connection..!", Toast.LENGTH_LONG).show();

                    }
                }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                System.out.println("map");


                return params;


            }

        };

        // Adding request to request queue
        queue.add(request);

    }


}




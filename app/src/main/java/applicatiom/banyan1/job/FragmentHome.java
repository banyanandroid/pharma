package applicatiom.banyan1.job;

/**
 * Created by Parameswari.K on 11/01/16.
 */

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
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
import java.util.List;
import java.util.Map;

import adapter.IMG_REQ_Lazy_Adapter;
import adapter.NewLatest_Adapter;
import gobal.AppConfig;
import gobal.GetSet_job;


public class FragmentHome extends Fragment {


    /*request volley*/



    /*progress bar*/

    /*queue*/

    private Toolbar toolbar;



    static ArrayList<HashMap<String, String>> complaint_list;

    HashMap<String, String> params = new HashMap<String, String>();
    ListView list;
    private GetSet_job getSet_newCall;
    private List<GetSet_job> infoLists = new ArrayList<>();




    private static final String TAG = "";

    private ProgressDialog pDialog;






    public static RequestQueue queue;


    private char PICK_REQUEST_CODE;

    public static String value_ji;
    public static String job_id;

    ImageView imageView;

    public FragmentHome() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        complaint_list = new ArrayList<HashMap<String, String>>();
        list = (ListView) rootView.findViewById(R.id.listView1);

        Bundle args = getActivity().getIntent().getExtras();
        value_ji = args.getString("values");
        System.out.println("fish1" + value_ji);

        //image

        imageView = (ImageView) rootView.findViewById(R.id.image_banner);


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


        System.out.print("fufufu inside out");



      /*  list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long arg3) {
                // TODO Auto-generated method stub

                int count_pos=position;

                for(int i = 0; i <= count_pos; i++) {

                    Toast.makeText(getContext(), "position" + count_pos, Toast.LENGTH_SHORT).show();


                    Intent intent = new Intent(getContext(), Activity_Joblist.class);
                    intent.putExtra("id", String.valueOf(adapter.getItem(position)));
                    intent.putExtra("job_id", "" + job_id);
                    startActivity(intent);
                }


            }
        });*/


        return rootView;
    }
    private void makeJsonRequest() {
        infoLists.clear();

        StringRequest request = new StringRequest(Request.Method.POST,
                AppConfig.Job_inform_url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());
                JSONObject jsonObject;
                try {

                    jsonObject = new JSONObject(response).getJSONObject("response");
                    JSONArray array = jsonObject.getJSONArray("job_details");
                    System.out.println("hi inside_newcall" + array);

                    for (int i = 0; i < array.length(); i++) {

                        JSONObject jsonObj = array.getJSONObject(i);

                        getSet_newCall = new GetSet_job();
                        Log.d("MYTAG", jsonObj.getString("job_id"));
                        getSet_newCall.setJOBID(jsonObj.getString("job_id"));
                        getSet_newCall.setWANT(jsonObj.getString("position"));
                        getSet_newCall.setCOMPY(jsonObj.getString("company"));
                        getSet_newCall.setPOSTDATE(jsonObj.getString("posted_date"));
                        getSet_newCall.setEXPERIENCE(jsonObj.getString("experience"));
                        getSet_newCall.setLOCATION(jsonObj.getString("location"));
                        getSet_newCall.setJOBDESC(jsonObj.getString("job_description"));
                        getSet_newCall.setSKILLS(jsonObj.getString("skills"));
                        /******** Take Model Object in ArrayList **********/
                        infoLists.add(getSet_newCall);
                        pDialog.dismiss();

                    }
                    list.setAdapter(new IMG_REQ_Lazy_Adapter(getActivity(),infoLists));


                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                pDialog.hide();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "no internet connection..!", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getContext(), Activity_Select_Place.class);
                startActivity(i);
                pDialog.dismiss();

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("location_path", Activity_JobOpp_Grid.location);
                System.out.println("gugu12" + params);
                System.out.println("gugu1" + value_ji);
                params.put("job_position", value_ji);
                System.out.println("gugu2" + params);


                return params;
            }

        };

        // Adding request to request queue
        queue.add(request);
    }

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
                        Toast.makeText(getContext(), "Check your internet connection..!", Toast.LENGTH_SHORT).show();

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




















/*

//...........................................................................................................................
    private void makeJsonRequest() {

        StringRequest request = new StringRequest(Request.Method.POST,
                AppConfig.Job_inform_url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());
                JSONObject jsonObject;
                try {

                    jsonObject = new JSONObject(response).getJSONObject("response");
                    JSONArray arr = jsonObject.getJSONArray("job_details");
                    System.out.println("hi inside_opop" + arr.length());

                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject jsonObj = arr.getJSONObject(i);


                        // creating new HashMap
                        HashMap<String, String> map = new HashMap<String, String>();

                        // adding each child node to HashMap key => value
                        System.out.println("job_id_check ::"+job_id);
                        map.put(TAG_JOBID, job_id);
                        map.put(TAG_WANT, position);
                        map.put(TAG_COMPY, company);
                        map.put(TAG_POSTDATE, posted_date);
                        map.put(TAG_EXPERIENCE, experience);
                        map.put(TAG_LOCATION, location);
                        map.put(TAG_JOBDESC, job_description);
                        map.put(TAG_SKILLS, skills);


                        complaint_list.add(map);

                        System.out.println("HASHMAP ARRAY" + complaint_list);

                        adapter = new IMG_REQ_Lazy_Adapter(getActivity(),
                                complaint_list);
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



    // loading the location from json_image
*/







package applicatiom.banyan1.job;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adapter.NewLatest_Adapter;
import adapter.QansA_Adapter;
import adapter.ViewAdapter;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import gobal.AppConfig;
import gobal.GetSetView;


/**
 * Created by Banyan1 on 4/21/2016.
 */
public class Activity_ViewAppliedPosted extends ActionBarActivity {

    ImageView imageView;
    /*progress bar*/
    private ProgressDialog pDialog;
    /*queue*/
    public static RequestQueue queue;
    private Toolbar toolbar;

    private static final String TAG = "";

    static ArrayList<HashMap<String, String>> complaint_list;

    HashMap<String, String> params = new HashMap<String, String>();
    ListView list;
    private GetSetView getSet_newCall;
    private List<GetSetView> infoLists = new ArrayList<>();


    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewlist);
        imageView = (ImageView) findViewById(R.id.image_banner);

        //action bar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Applied Jobs");

        try {
          /*  pDialog = new ProgressDialog(Activity_Select_Place.this);
            pDialog.setMessage("Please wait...");
            pDialog.show();
            pDialog.setCancelable(false);*/
            queue = Volley.newRequestQueue(Activity_ViewAppliedPosted.this);
            makeJsonObjectRequest_image();
        } catch (Exception e) {
            // TODO: handle exception
        }


        try

        {
            pDialog = new ProgressDialog(Activity_ViewAppliedPosted.this);
            pDialog.setMessage("Please wait...");
            pDialog.show();
            pDialog.setCancelable(false);
            queue = Volley.newRequestQueue(Activity_ViewAppliedPosted.this);

            makeJsonRequest();


            System.out.print("fufufu inside try");

        } catch (Exception e) {
            // TODO: handle exception
        }
        complaint_list = new ArrayList<HashMap<String, String>>();

        list = (ListView) findViewById(R.id.listView_viewall);


    }

    private void makeJsonRequest() {
        infoLists.clear();

        StringRequest request = new StringRequest(Request.Method.POST,
                AppConfig.applied_jobs_url, new Response.Listener<String>() {

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

                        getSet_newCall = new GetSetView();
                        Log.d("MYTAG", jsonObj.getString("position"));
                        getSet_newCall.setposition(jsonObj.getString("position"));
                        getSet_newCall.setcompany(jsonObj.getString("company"));
                        getSet_newCall.setposted_date(jsonObj.getString("posted_date"));
                        getSet_newCall.setexperience(jsonObj.getString("experience"));
                        getSet_newCall.setlocation(jsonObj.getString("location"));
                        getSet_newCall.setjob_description(jsonObj.getString("job_description"));
                        getSet_newCall.setskills(jsonObj.getString("skills"));
                        /******** Take Model Object in ArrayList **********/
                        infoLists.add(getSet_newCall);
                        pDialog.dismiss();

                    }

                    list.setAdapter(new ViewAdapter(Activity_ViewAppliedPosted.this,
                            infoLists));


                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                pDialog.hide();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Activity_ViewAppliedPosted.this, "Logout current account and Login your account..", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(Activity_ViewAppliedPosted.this, Activity_Select_Place.class);
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

    // loading the location from json
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
                        Picasso.with(Activity_ViewAppliedPosted.this)
                                .load(image)
                                .into(imageView);
                        //  pDialog.dismiss();


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
                        Toast.makeText(Activity_ViewAppliedPosted.this, "Check your internet connection..!", Toast.LENGTH_SHORT).show();

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (item.getItemId()) {

            case R.id.action_home:
                Intent inte = new Intent(getApplicationContext(), Activity_Select_Place.class);
                startActivity(inte);
                return true;
            case R.id.action_cancel:
                Intent intent = new Intent(Intent.ACTION_MAIN);

                intent.addCategory(Intent.CATEGORY_HOME);

                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(intent);

                finish();

                System.exit(0);
                return true;
        }
        return true;


    }


    @Override
    public void onBackPressed() {

        super.onBackPressed();
        finish();
    }

}

package applicatiom.banyan1.job;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
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

import java.util.HashMap;
import java.util.Map;

import gobal.AppConfig;

public class Activity_JobOpp_Grid extends ActionBarActivity implements View.OnClickListener {

    /*progress bar*/
    private ProgressDialog pDialog;
    /*queue*/
    public static RequestQueue queue;
    private static final String TAG = "";


    public static final String TAG_WANT = "position";
    public static final String TAG_COMPY = "company";
    public static final String TAG_POSTDATE = "posted_date";
    public static final String TAG_EXPERIENCE = "experience";
    public static final String TAG_LOCATION = "location";
    public static final String TAG_JOBDESC = "job_description";
    public static final String TAG_SKILLS = "skills";
    public static final String TAG_CMPYIMG = "img";

    public static final String KEY_ID_LOC = "id";

    public static String view = "view";
    public String se = "Sales Executive/MR";
    public String fl = "First Line Manager";
    public String sl = "Second Line Manager";
    public String zl = "Zonal Level";
    public String pm = "Product management";
    public String trg = "traning";
    public String othr = "others";
    private Toolbar toolbar;

    public static String SalesExecutiveMR, FirstLineManager, SecondLineManager, ZonalLevel, Productmanagement, traning, others;

    public static String location;
    TextView txt_sale, txtl_first, txtl_second, txtl_zonal, txtl_prdmgt, txtl_trainig, txtl_other;
    Button b1;
    TextView t1, t2, t3, t4, t5, t6, t7;
    String str1, str2, str3, str4, str5, str6, str7;
    ImageView imageView;
    String username;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_opp__grid);

        //action bar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Job Position");

        //image

        imageView = (ImageView) findViewById(R.id.image_banner);


        try {
           /* pDialog = new ProgressDialog(Activity_Select_Place.this);
            pDialog.setMessage("Please wait...");
            pDialog.show();
            pDialog.setCancelable(false);*/
            queue = Volley.newRequestQueue(Activity_JobOpp_Grid.this);
            makeJsonObjectRequest_image();
        } catch (Exception e) {
            // TODO: handle exception
        }


        try {
            System.out.println("lololp");
            pDialog = new ProgressDialog(Activity_JobOpp_Grid.this);
            pDialog.setMessage("Please wait...");
            pDialog.show();
            pDialog.setCancelable(false);
            queue = Volley.newRequestQueue(Activity_JobOpp_Grid.this);
            makeJsonObjectRequest_loc();

        } catch (Exception e) {
            // TODO: handle exception
        }


        SharedPreferences pref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        location = pref.getString("location", "text");

        Log.d(TAG, location);

        //location
        TextView txtl = (TextView) findViewById(R.id.text_location);
        System.out.println("gaya" + Activity_Select_Place.text);
        txtl.setText(location);
        //SalesExecutiveMR
        t1 = (TextView) findViewById(R.id.txt_salescnt);
        //FirstLineManager
        t2 = (TextView) findViewById(R.id.txt_frstcnt);

        //SecondLineManager
        t3 = (TextView) findViewById(R.id.txt_secondcnt);

        //ZonalLevel
        t4 = (TextView) findViewById(R.id.txt_zonalcnt);

        //Productmanagement
        t5 = (TextView) findViewById(R.id.txt_pmcnt);

        //traning
        t6 = (TextView) findViewById(R.id.txt_trancnt);

        //others
        t7 = (TextView) findViewById(R.id.txt_othercnt);

        //concept

        txt_sale = (TextView) findViewById(R.id.txt_sales);
        txtl_first = (TextView) findViewById(R.id.txt_first);
        txtl_second = (TextView) findViewById(R.id.txt_second);
        txtl_zonal = (TextView) findViewById(R.id.txt_zonal_lvl);
        txtl_prdmgt = (TextView) findViewById(R.id.txt_prdmgt);
        txtl_trainig = (TextView) findViewById(R.id.txt_training);
        txtl_other = (TextView) findViewById(R.id.txt_other);

        txt_sale.setOnClickListener(this);
        txtl_first.setOnClickListener(this);
        txtl_second.setOnClickListener(this);
        txtl_zonal.setOnClickListener(this);
        txtl_prdmgt.setOnClickListener(this);
        txtl_trainig.setOnClickListener(this);
        txtl_other.setOnClickListener(this);

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
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.txt_sales:
                if (str1.equals("0")) {
                    Toast.makeText(Activity_JobOpp_Grid.this, "No Jobs found..!", Toast.LENGTH_SHORT).show();
                } else {

                    System.out.println("hiihi");

                    SharedPreferences pref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

                    username = pref.getString("username", "");

                    Log.d(TAG, username);


                    if (username.equals("")) {
                        //Toast.makeText(Activity_Joblist.this, "empty" , Toast.LENGTH_LONG).show();
                        Intent in = new Intent(Activity_JobOpp_Grid.this, Activity_login.class);
                        startActivity(in);


                    } else {
                        Intent i = new Intent(Activity_JobOpp_Grid.this, MainActivity.class);
                        i.putExtra("values", se);
                        startActivity(i);


                    }

                }
                break;

            case R.id.txt_first:
                if (str2.equals("0")) {
                    Toast.makeText(Activity_JobOpp_Grid.this, "No Jobs found..!", Toast.LENGTH_SHORT).show();
                } else {

                    System.out.println("hiihi");

                    SharedPreferences pref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

                    username = pref.getString("username", "");

                    Log.d(TAG, username);


                    if (username.equals("")) {
                        //Toast.makeText(Activity_Joblist.this, "empty" , Toast.LENGTH_LONG).show();
                        Intent in = new Intent(Activity_JobOpp_Grid.this, Activity_login.class);
                        startActivity(in);


                    } else {
                        Intent ys = new Intent(Activity_JobOpp_Grid.this, MainActivity.class);
                        ys.putExtra("values", fl);
                        startActivity(ys);


                    }


                }

                break;

            case R.id.txt_second:
                if (str3.equals("0")) {
                    Toast.makeText(Activity_JobOpp_Grid.this, "No Jobs found..!", Toast.LENGTH_SHORT).show();
                } else {
                    System.out.println("hiihi");

                    SharedPreferences pref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

                    username = pref.getString("username", "");

                    Log.d(TAG, username);


                    if (username.equals("")) {
                        //Toast.makeText(Activity_Joblist.this, "empty" , Toast.LENGTH_LONG).show();
                        Intent in = new Intent(Activity_JobOpp_Grid.this, Activity_login.class);
                        startActivity(in);


                    } else {
                        Intent i2 = new Intent(Activity_JobOpp_Grid.this, MainActivity.class);
                        i2.putExtra("values", sl);
                        startActivity(i2);

                    }

                }


                break;

            case R.id.txt_zonal_lvl:
                if (str4.equals("0")) {
                    Toast.makeText(Activity_JobOpp_Grid.this, "No Jobs found..!", Toast.LENGTH_SHORT).show();
                } else {
                    System.out.println("hiihi");

                    SharedPreferences pref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

                    username = pref.getString("username", "");

                    Log.d(TAG, username);


                    if (username.equals("")) {
                        //Toast.makeText(Activity_Joblist.this, "empty" , Toast.LENGTH_LONG).show();
                        Intent in = new Intent(Activity_JobOpp_Grid.this, Activity_login.class);
                        startActivity(in);


                    } else {
                        Intent i3 = new Intent(Activity_JobOpp_Grid.this, MainActivity.class);
                        i3.putExtra("values", zl);
                        startActivity(i3);
                    }

                }


                break;
            case R.id.txt_prdmgt:
                if (str5.equals("0")) {
                    Toast.makeText(Activity_JobOpp_Grid.this, "No Jobs found..!", Toast.LENGTH_SHORT).show();
                } else {
                    System.out.println("hiihi");

                    SharedPreferences pref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

                    username = pref.getString("username", "");

                    Log.d(TAG, username);


                    if (username.equals("")) {
                        //Toast.makeText(Activity_Joblist.this, "empty" , Toast.LENGTH_LONG).show();
                        Intent in = new Intent(Activity_JobOpp_Grid.this, Activity_login.class);
                        startActivity(in);


                    } else {
                        Intent i4 = new Intent(Activity_JobOpp_Grid.this, MainActivity.class);
                        i4.putExtra("values", pm);
                        startActivity(i4);
                    }

                }


                break;
            case R.id.txt_training:
                if (str6.equals("0")) {
                    Toast.makeText(Activity_JobOpp_Grid.this, "No Jobs found..!", Toast.LENGTH_SHORT).show();
                } else {
                    System.out.println("hiihi");

                    SharedPreferences pref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

                    username = pref.getString("username", "");

                    Log.d(TAG, username);


                    if (username.equals("")) {
                        //Toast.makeText(Activity_Joblist.this, "empty" , Toast.LENGTH_LONG).show();
                        Intent in = new Intent(Activity_JobOpp_Grid.this, Activity_login.class);
                        startActivity(in);


                    } else {
                        Intent i5 = new Intent(Activity_JobOpp_Grid.this, MainActivity.class);
                        i5.putExtra("values", trg);
                        startActivity(i5);

                    }

                }


                break;
            case R.id.txt_other:
                if (str7.equals("0")) {
                    Toast.makeText(Activity_JobOpp_Grid.this, "No Jobs found..!", Toast.LENGTH_SHORT).show();
                } else {
                    System.out.println("hiihi");

                    SharedPreferences pref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

                    username = pref.getString("username", "");

                    Log.d(TAG, username);


                    if (username.equals("")) {
                        //Toast.makeText(Activity_Joblist.this, "empty" , Toast.LENGTH_LONG).show();
                        Intent in = new Intent(Activity_JobOpp_Grid.this, Activity_login.class);
                        startActivity(in);


                    } else {
                        Intent i6 = new Intent(Activity_JobOpp_Grid.this, MainActivity.class);
                        i6.putExtra("values", othr);
                        startActivity(i6);

                    }

                }


                break;
        }


    }


    //send the location tyo grid page
    private void makeJsonObjectRequest_loc() {


        System.out.println("method inside");

        StringRequest request = new StringRequest(Request.Method.POST,
                AppConfig.slct_loc_id_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject jsonObject;

                try {
                    jsonObject = new JSONObject(response).getJSONObject("response");
                    System.out.println("json object" + jsonObject);
                    JSONArray arr = jsonObject.getJSONArray("position");
                    System.out.println("json array" + arr);
                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject jsonObj = arr.getJSONObject(i);
                        SalesExecutiveMR = jsonObj.getString("Sales Executive MR");
                        System.out.println("sales" + SalesExecutiveMR);
                        FirstLineManager = jsonObj.getString("First Line Manager");
                        System.out.println("first" + FirstLineManager);
                        SecondLineManager = jsonObj.getString("Second Line Manager");
                        System.out.println("second" + SecondLineManager);
                        ZonalLevel = jsonObj.getString("Zonal Level");
                        System.out.println("zonal" + ZonalLevel);
                        Productmanagement = jsonObj.getString("Product management");
                        System.out.println("product" + Productmanagement);
                        traning = jsonObj.getString("traning");
                        System.out.println("training" + traning);
                        others = jsonObj.getString("others");


                        t1.setText(SalesExecutiveMR);
                        str1 = t1.getText().toString();
                        System.out.println("gaya" + SalesExecutiveMR);
                        System.out.println("cp" + str1);
                        //FirstLineManager
                        System.out.println("gaya" + FirstLineManager);
                        t2.setText(FirstLineManager);
                        str2 = t2.getText().toString();
                        //SecondLineManager
                        System.out.println("gaya" + SecondLineManager);
                        t3.setText(SecondLineManager);
                        str3 = t3.getText().toString();
                        //ZonalLevel

                        System.out.println("gaya" + ZonalLevel);
                        t4.setText(ZonalLevel);
                        str4 = t4.getText().toString();
                        //Productmanagement

                        System.out.println("gaya" + Productmanagement);
                        t5.setText(Productmanagement);
                        str5 = t5.getText().toString();
                        //traning

                        System.out.println("gaya" + traning);
                        t6.setText(traning);
                        str6 = t6.getText().toString();
                        //others

                        System.out.println("gaya" + others);
                        t7.setText(others);
                        str7 = t7.getText().toString();

                        pDialog.dismiss();
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
                        Toast.makeText(Activity_JobOpp_Grid.this, "no internet connection..!", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getApplicationContext(), Activity_Select_Place.class);
                        startActivity(i);
                        pDialog.dismiss();

                    }
                }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                System.out.println("map");
                params.put(KEY_ID_LOC, location);
                System.out.println(params);
                return params;


            }

        };

        // Adding request to request queue
        queue.add(request);

    }


    public void onBackPressed() {

        super.onBackPressed();

        Intent it = new Intent(getApplicationContext(), Activity_Select_Place.class);
        startActivity(it);


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
                        Picasso.with(Activity_JobOpp_Grid.this)
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
                        Toast.makeText(Activity_JobOpp_Grid.this, "Check your internet connection..!", Toast.LENGTH_SHORT).show();

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









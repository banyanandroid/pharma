package applicatiom.banyan1.job;

import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.net.Uri;
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
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import gobal.AppConfig;

import gobal.SessionManagement;


import android.view.View.OnClickListener;

public class Activity_Joblist extends ActionBarActivity{
    /*queue*/
    public static RequestQueue queue;
    /*progress bar*/
    private ProgressDialog pDialog;
    TextView t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, tcreate;
    String str_t1, str_t2, str_t3, str_t4, str_t5, str_t6, str_t7, str_t8, str_t9, str_t10, str_t11, str_t12, str_t13, str_t14, str_t15, str_t16, str_t17;
    public static Button b1;
    private int PICK_REQUEST_CODE;

    String username;
    private static final String TAG = "";
    private Toolbar toolbar;
    ImageView image_create;
    Button btn_cal;
    TextView tt1, tt2, tt3, tt4, tt5, tt6, tt7, tt8, tt9, tt10, tt11, tt12, tt13, tt14, tt15, tt16, tt17, tt18, tt19, tt20;


    TextView vt1, vt2, vt3, vt4, vt5, vt6, vt7;
    ImageView imageView;
    String gmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joblist);


       AppConfig. Item = getIntent().getExtras().getString("id");
        System.out.print("goat"+ AppConfig. Item);




        image_create = (ImageView) findViewById(R.id.image_create);
        btn_cal = (Button) findViewById(R.id.button_call);
        imageView = (ImageView) findViewById(R.id.image_banner);

        tt1 = (TextView) findViewById(R.id.expr_desc_txt);
        tt2 = (TextView) findViewById(R.id.loca_desc_txt);
        vt1 = (TextView) findViewById(R.id.vw1);
        tt3 = (TextView) findViewById(R.id.posted_on_desc_txt);
        tt4 = (TextView) findViewById(R.id.salary_desc_txt);
        tt5 = (TextView) findViewById(R.id.skils_desc_txt);
        vt3 = (TextView) findViewById(R.id.vw3);
        tt6 = (TextView) findViewById(R.id.desc_txt);
        vt4 = (TextView) findViewById(R.id.vw4);
        tt7 = (TextView) findViewById(R.id.emp_detail_txt);
        tt8 = (TextView) findViewById(R.id.cmp_desc_desc_txt);
        vt5 = (TextView) findViewById(R.id.vw22);

        b1 = (Button) findViewById(R.id.apply_des_job);

        t1 = (TextView) findViewById(R.id.txt_exp_des);
        t2 = (TextView) findViewById(R.id.txt_loc_des);
        t3 = (TextView) findViewById(R.id.post_date_desc_txt);
        t4 = (TextView) findViewById(R.id.salary_value_desc_txt);
        /*t5 = (TextView) findViewById(R.id.func_area_value_desc_txt);
        t6 = (TextView) findViewById(R.id.industry_value_desc_txt);*/
        t7 = (TextView) findViewById(R.id.skils_value_desc_txt);
        t8 = (TextView) findViewById(R.id.desc_value_txt);
        t9 = (TextView) findViewById(R.id.cmpy_name_value_desc_txt);
        t10 = (TextView) findViewById(R.id.cmpy_desc_value_desc_txt);
        t11 = (TextView) findViewById(R.id.name_value_desc_txt);
        t12 = (TextView) findViewById(R.id.hr_value_desc_txt);
        t13 = (TextView) findViewById(R.id.ph_value_desc_txt);
        t14 = (TextView) findViewById(R.id.email_value_desc_txt);
        t15 = (TextView) findViewById(R.id.addr_value_desc_txt);
        t16 = (TextView) findViewById(R.id.job_desc_txt_top);
        t17 = (TextView) findViewById(R.id.company_desc_txt_top);
        tcreate = (TextView) findViewById(R.id.txt_create);

        str_t1 = t1.getText().toString().trim();
        str_t2 = t2.getText().toString().trim();
        str_t3 = t3.getText().toString().trim();
        str_t4 = t4.getText().toString().trim();
   /*     str_t5 = t5.getText().toString().trim();
        str_t6 = t6.getText().toString().trim();*/
        str_t7 = t7.getText().toString().trim();
        str_t8 = t8.getText().toString().trim();
        str_t9 = t9.getText().toString().trim();
        str_t10 = t10.getText().toString().trim();
        str_t11 = t11.getText().toString().trim();
        str_t12 = t12.getText().toString().trim();
        str_t13 = t13.getText().toString().trim();
        str_t14 = t14.getText().toString().trim();
        str_t15 = t15.getText().toString().trim();
        str_t16 = t16.getText().toString().trim();
        str_t17 = t17.getText().toString().trim();
        System.out.println("cp");

        b1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                System.out.println("hiihi");

                SharedPreferences pref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

                gmail= pref.getString("username", "email");

                Log.d(TAG, gmail);


                try {
                    pDialog = new ProgressDialog(Activity_Joblist.this);
                    pDialog.setMessage("Please wait...");
                    pDialog.show();
                    pDialog.setCancelable(false);
                    queue = Volley.newRequestQueue(Activity_Joblist.this);
                    System.out.println("clicked");
                    Apply_job();
                } catch (Exception e) {
                    // TODO: handle exception
                }


            }
        });


        image_create.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences pref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

                username = pref.getString("username", "");

                Log.d(TAG, username);


                if (username.equals("")) {
                    //Toast.makeText(Activity_Joblist.this, "empty" , Toast.LENGTH_LONG).show();
                    Intent in = new Intent(Activity_Joblist.this, Activity_login.class);
                    startActivity(in);


                } else {
                    try {
                        Intent in = new Intent(Activity_Joblist.this, Activity_CreateResume.class);
                        startActivity(in);
                    } catch (Exception e) {
                        // TODO: handle exception
                    }


                }


            }
        });

        // add PhoneStateListener
        PhoneCallListener phoneListener = new PhoneCallListener();
        TelephonyManager telephonyManager = (TelephonyManager) this
                .getSystemService(Context.TELEPHONY_SERVICE);
        telephonyManager.listen(phoneListener, PhoneStateListener.LISTEN_CALL_STATE);

        // add button listener
        btn_cal.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                String number = t13.getText().toString().trim();
                System.out.print("eswari_sathya" + number);
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + number));
                startActivity(callIntent);


            }

        });


        // listeners of our two buttons
        View.OnClickListener handler = new View.OnClickListener() {
            public void onClick(View v) {
                switch (v.getId()) {

                    case R.id.forward_des_job:
                        shareTextUrl();
                        break;


                }
            }
        };

        // our buttons
        findViewById(R.id.forward_des_job).setOnClickListener(handler);

        //action bar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Job Details");


        //image


        try {
           /* pDialog = new ProgressDialog(Activity_Select_Place.this);
            pDialog.setMessage("Please wait...");
            pDialog.show();
            pDialog.setCancelable(false);*/
            queue = Volley.newRequestQueue(Activity_Joblist.this);
            makeJsonObjectRequest_image();
        } catch (Exception e) {
            // TODO: handle exception
        }

// create
        tcreate.setPaintFlags(tcreate.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        tcreate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

                username = pref.getString("username", "");

                Log.d(TAG, username);


                if (username.equals("")) {
                    //Toast.makeText(Activity_Joblist.this, "empty" , Toast.LENGTH_LONG).show();
                    Intent in = new Intent(Activity_Joblist.this, Activity_login.class);
                    startActivity(in);


                } else {
                    try {
                        Intent in = new Intent(Activity_Joblist.this, Activity_CreateResume.class);
                        startActivity(in);
                    } catch (Exception e) {
                        // TODO: handle exception
                    }


                }


            }
        });

        t13.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                String number = t13.getText().toString().trim();
                System.out.print("eswari_sathya" + number);
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + number));
                startActivity(callIntent);

            }
        });

        try {
            pDialog = new ProgressDialog(Activity_Joblist.this);
            pDialog.setMessage("Please wait...");
            pDialog.show();
            pDialog.setCancelable(false);
            queue = Volley.newRequestQueue(Activity_Joblist.this);
            makeJsonObjectRequest();
        } catch (Exception e) {
            // TODO: handle exception
        }


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


    //json with volley coding

    private void makeJsonObjectRequest() {


        System.out.println("method inside");

        StringRequest request = new StringRequest(Request.Method.POST,
                AppConfig.view_all_des_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                JSONObject jsonObject;
                try {

                    jsonObject = new JSONObject(response).getJSONObject("response");
                    JSONArray arr = jsonObject.getJSONArray("job_details");
                    System.out.println("hi inside_joblist" + arr);

                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject jsonObj = arr.getJSONObject(i);

                        // int id = Integer.parseInt(jsonObject.optString("id").toString());
                        String position = jsonObj.getString("position");


                        String company = jsonObj.getString("company");

                        String posted_date = jsonObj.getString("posted_date");

                        String salary = jsonObj.getString("salary");

                       /* String functional_area = jsonObj.getString("functional_area");

                        String industry = jsonObj.getString("industry");*/

                        String experience = jsonObj.getString("experience");

                        String location = jsonObj.getString("location");

                        String job_description = jsonObj.getString("job_description");
                        String company_description = jsonObj.getString("company_description");
                        String skills = jsonObj.getString("skills");
                        String contact_person = jsonObj.getString("contact_person");
                        String phone = jsonObj.getString("phone");
                        String email = jsonObj.getString("email");
                        String address = jsonObj.getString("address");

                        System.out.println("eswarichim"+experience);
                        t1.setText(experience);
                        System.out.println("eswarichim"+location);

                        t2.setText(location);
                        System.out.println("eswarichim"+posted_date);
                        t3.setText(posted_date);
                        System.out.println("eswarichim"+salary);
                        t4.setText(salary);
                        System.out.println("eswarichim"+skills);
                     /*   t5.setText(functional_area);
                        t6.setText(industry);*/
                        t7.setText(skills);
                        System.out.println("eswarichim"+job_description);

                        t8.setText(job_description);
                        System.out.println("eswarichim"+company);
                        t9.setText(company);
                        System.out.println("eswarichim"+company_description);
                        t10.setText(company_description);
                        System.out.println("eswarichim"+contact_person);
                        t11.setText(contact_person);
                        System.out.println("eswarichim"+company);
                        t12.setText(company);
                        System.out.println("eswarichim"+phone);
                        t13.setText(phone);
                        System.out.println("eswarichim"+email);
                        t14.setText(email);
                        System.out.println("eswarichim"+address);
                        t15.setText(address);
                        System.out.println("eswarichim"+position);
                        t16.setText(position);
                        System.out.println("eswarichim"+company);
                        t17.setText(company);


                       /* if(t13.equals("")){
                            btn_cal.setVisibility(View.GONE);
                        }*/

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
                        Toast.makeText(Activity_Joblist.this, "no internet connection..!", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getApplicationContext(), Activity_Select_Place.class);
                        startActivity(i);
                        pDialog.dismiss();


                    }
                }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                System.out.println("map");
                params.put("id",AppConfig.Item);
                System.out.println("gugu2_dees" + params);


                return params;

            }

        };

        // Adding request to request queue
        queue.add(request);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }


    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), Activity_JobOpp_Grid.class);
        startActivity(i);

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
                        Picasso.with(Activity_Joblist.this)
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
                        Toast.makeText(Activity_Joblist.this, "Check your internet connection..!", Toast.LENGTH_SHORT).show();

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

    // Method to share either text or URL.
    private void shareTextUrl() {
        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("text/plain");
        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

        // Add data to the intent, the receiving app will decide
        // what to do with it.
        share.putExtra(Intent.EXTRA_SUBJECT, "PharmaJobs-India");
        share.putExtra(Intent.EXTRA_TEXT, "Hi friends i have found new Job Search App to build our career: https://play.google.com/store/apps/details?id=applicatiom.banyan1.job&hl=en");

        startActivity(Intent.createChooser(share, "Share link!"));
    }

    //monitor phone call activities
    private class PhoneCallListener extends PhoneStateListener {

        private boolean isPhoneCalling = false;

        String LOG_TAG = "LOGGING 123";

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {

            if (TelephonyManager.CALL_STATE_RINGING == state) {
                // phone ringing
                Log.i(LOG_TAG, "RINGING, number: " + incomingNumber);
            }

            if (TelephonyManager.CALL_STATE_OFFHOOK == state) {
                // active
                Log.i(LOG_TAG, "OFFHOOK");

                isPhoneCalling = true;
            }

            if (TelephonyManager.CALL_STATE_IDLE == state) {
                // run when class initial and phone call ended,
                // need detect flag from CALL_STATE_OFFHOOK
                Log.i(LOG_TAG, "IDLE");

                if (isPhoneCalling) {

                    Log.i(LOG_TAG, "restart app");

                    // restart app
                    Intent i = getBaseContext().getPackageManager()
                            .getLaunchIntentForPackage(
                                    getBaseContext().getPackageName());
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);

                    isPhoneCalling = false;
                }

            }
        }
    }

    /*private void Apply () {


        System.out.println("inside function");

        StringRequest request = new StringRequest(Request.Method.POST,
                AppConfig.apply_url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                JSONObject jsonObject;
                try {

                    System.out.println("inside try");
                    JSONObject obj = new JSONObject(response).getJSONObject("response");

                    System.out.println("pharamaJobsssssssssss" + obj);


                    String s = obj.getString("Success");
                    String details = obj.getString("Details");

                    if (s.equals("1")) {

                        Toast.makeText(Activity_Joblist.this, details, Toast.LENGTH_SHORT).show();

                        //intent nxt class
                        Intent i = new Intent(getApplicationContext(), Activity_JobOpp_Grid.class);
                        startActivity(i);
                        pDialog.dismiss();


                    }  if (s.equals("0")) {
                        Toast.makeText(Activity_Joblist.this,details, Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getApplicationContext(), Activity_JobOpp_Grid.class);
                        startActivity(i);
                        pDialog.dismiss();
                    }else{
                        Toast.makeText(Activity_Joblist.this, "Apply Failed..!", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(Activity_Joblist.this, "no internet connection..!", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getApplicationContext(), Activity_Select_Place.class);
                        startActivity(i);
                        pDialog.dismiss();

                    }
                }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("job_id", AppConfig.Item);
                params.put("email", gmail);
                System.out.println("inside hashmap:::"+params);
                return params;


            }

        };

        // Adding request to request queue
        queue.add(request);
    }*/


    //...........................................
    private void Apply_job() {

        String tag_json_obj = "json_obj_req";

        StringRequest request = new StringRequest(Request.Method.POST,
                AppConfig.apply_url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());
                try {

                    JSONObject obj = new JSONObject(response).getJSONObject("response");

                    String s = obj.getString("Success");
                    String details = obj.getString("Details");

                    System.out.println("RESPONSE :: " + s);

                    if (s.equals("1")) {

                        Toast.makeText(Activity_Joblist.this, details, Toast.LENGTH_SHORT).show();

                        //intent nxt class
                        Intent i = new Intent(getApplicationContext(), Activity_JobOpp_Grid.class);
                        startActivity(i);
                        pDialog.dismiss();


                    }  if (s.equals("0")) {
                        Toast.makeText(Activity_Joblist.this,"Applied Successfully..!", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getApplicationContext(), Activity_JobOpp_Grid.class);
                        startActivity(i);
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

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("job_id", AppConfig.Item);
                params.put("email", gmail);
                System.out.println("inside hashmap:::"+params);

                return params;
            }

        };

        // Adding request to request queue
        queue.add(request);
    }


}


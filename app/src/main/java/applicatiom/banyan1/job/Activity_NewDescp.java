package applicatiom.banyan1.job;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gobal.AppConfig;
import gobal.GetSet_ans;

public class Activity_NewDescp extends ActionBarActivity {

    ImageView imageView, imageView_disp;
    /*progress bar*/
    private ProgressDialog pDialog;
    /*queue*/
    public static RequestQueue queue;
    private Toolbar toolbar;

    String ans;
    private FloatingActionButton back;
    public static final String KEY_id_new = "id";

    private static final String TAG = "";

    EditText t1;
    EditText edt1;
    String new_id;


    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_desc);

        Bundle args = getIntent().getExtras();
        new_id = args.getString("id");
        System.out.print("lllllll" + new_id);


        imageView = (ImageView) findViewById(R.id.image_banner);
        imageView_disp = (ImageView) findViewById(R.id.image1);
        edt1 = (EditText) findViewById(R.id.edt_new_des_dis);
        t1 = (EditText) findViewById(R.id.txt_title_new_des);
        back = (FloatingActionButton) findViewById(R.id.back_new_des);

        //action bar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("News Details");


        try {
          /*  pDialog = new ProgressDialog(Activity_Select_Place.this);
            pDialog.setMessage("Please wait...");
            pDialog.show();
            pDialog.setCancelable(false);*/
            queue = Volley.newRequestQueue(this);
            makeJsonObjectRequest_image();
        } catch (Exception e) {
            // TODO: handle exception
        }
        try {
            pDialog = new ProgressDialog(Activity_NewDescp.this);
            pDialog.setMessage("Please wait...");
            pDialog.show();
            pDialog.setCancelable(false);
            queue = Volley.newRequestQueue(Activity_NewDescp.this);

            NewDEs_url();
        } catch (Exception e) {
            // TODO: handle exception
        }
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent i = new Intent(getApplicationContext(), Activity_NewList.class);
                startActivity(i);*/
                finish();

            }
        });
    }


    private void NewDEs_url() {

        final StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.new_des_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        JSONObject jsonResponse;
                        try {

                            jsonResponse = new JSONObject(response).getJSONObject("response");
                            JSONArray arr = jsonResponse.getJSONArray("result");
                            System.out.println("hi inside_joblist" + arr);

                            for (int i = 0; i < arr.length(); i++) {
                                JSONObject jsonObj = arr.getJSONObject(i);
                                String image_dis = jsonObj.getString("image");
                                System.out.println("pop" + image_dis);
                                Picasso.with(Activity_NewDescp.this)
                                        .load(image_dis)
                                        .into(imageView_disp);
                                String title = jsonObj.getString("title").toUpperCase();
                                t1.setText(title);
                                String des = jsonObj.getString("description");
                                edt1.setText(des);
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
                        Toast.makeText(Activity_NewDescp.this, "no internet connection..!", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getApplicationContext(), Activity_Select_Place.class);
                        startActivity(i);
                        pDialog.dismiss();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                System.out.print("username_ques" + Activity_login.email);
                map.put(KEY_id_new, new_id);

                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
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
                        Picasso.with(Activity_NewDescp.this)
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
                        Toast.makeText(Activity_NewDescp.this, "Check your internet connection..!", Toast.LENGTH_SHORT).show();

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



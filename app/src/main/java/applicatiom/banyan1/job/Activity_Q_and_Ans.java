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
import android.view.inputmethod.InputMethodManager;
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

import adapter.QansA_Adapter;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import gobal.AppConfig;
import gobal.GetSet_QandA;

/**
 * Created by Parameswari.K on 4/21/2016.
 */
public class Activity_Q_and_Ans extends ActionBarActivity {

    ImageView imageView;
    /*progress bar*/
    private ProgressDialog pDialog;
    /*queue*/
    public static RequestQueue queue;
    private Toolbar toolbar;
    EditText Edt_quest;
    String ques;
    Button b_post;
    String username;
    private static final String TAG = "";


    public static final String KEY_EMAIL = "email";
    public static final String KEY_QUEST = "question";

    static ArrayList<HashMap<String, String>> complaint_list;

    HashMap<String, String> params = new HashMap<String, String>();

    ListView list;
    private GetSet_QandA getSet_newCall;
    private List<GetSet_QandA> infoLists = new ArrayList<>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qanda);
        imageView = (ImageView) findViewById(R.id.image_banner);
        Edt_quest = (EditText) findViewById(R.id.edt_quest_ph);


        b_post = (Button) findViewById(R.id.save_qus_post);

        //action bar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Ask the Experts");

        try {
          /*  pDialog = new ProgressDialog(Activity_Select_Place.this);
            pDialog.setMessage("Please wait...");
            pDialog.show();
            pDialog.setCancelable(false);*/
            queue = Volley.newRequestQueue(Activity_Q_and_Ans.this);
            makeJsonObjectRequest_image();
        } catch (Exception e) {
            // TODO: handle exception
        }

        b_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ques = Edt_quest.getText().toString().trim();

                SharedPreferences pref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

                username = pref.getString("username", "");

                Log.d(TAG, username);


                if (username.equals("")) {
                    //Toast.makeText(Activity_Joblist.this, "empty" , Toast.LENGTH_LONG).show();
                    Intent in = new Intent(Activity_Q_and_Ans.this, Activity_login.class);
                    startActivity(in);


                } else if ((ques.equals(""))) {
                    // Toast.makeText(MainActivity.this, "please enter email", Toast.LENGTH_SHORT)show();
                    Crouton.makeText(Activity_Q_and_Ans.this,
                            "please enter question..!",
                            Style.INFO)
                            .show();
                } else {
                    try {
                        pDialog = new ProgressDialog(Activity_Q_and_Ans.this);
                        pDialog.setMessage("Please wait...");
                        pDialog.show();
                        pDialog.setCancelable(false);
                        queue = Volley.newRequestQueue(Activity_Q_and_Ans.this);

                        QuestJson();
                    } catch (Exception e) {
                        // TODO: handle exception
                    }

                }

            }
        });

        try

        {
            pDialog = new ProgressDialog(Activity_Q_and_Ans.this);
            pDialog.setMessage("Please wait...");
            pDialog.show();
            pDialog.setCancelable(false);
            queue = Volley.newRequestQueue(Activity_Q_and_Ans.this);

            makeJsonRequest();


            System.out.print("fufufu inside try");

        } catch (Exception e) {
            // TODO: handle exception
        }
        complaint_list = new ArrayList<HashMap<String, String>>();

        list = (ListView) findViewById(R.id.listView_ques);


    }

    private void QuestJson() {


        final StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.akept_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        JSONObject jsonResponse;
                        try {

                            jsonResponse = new JSONObject(response).getJSONObject("Response");
                            System.out.println("kokotttt" + jsonResponse);
                            String obj = jsonResponse.getString("message");
                            System.out.println("koko" + obj);

                            if (obj.equals("You have been sucessfully posted ")) {

                                Toast.makeText(Activity_Q_and_Ans.this, obj, Toast.LENGTH_SHORT).show();
                                Edt_quest.setText("");
                                Intent intent = getIntent();
                                finish();
                                startActivity(intent);

                                /*Intent in = new Intent(Activity_Q_and_Ans.this, Activity_Select_Place.class);
                                startActivity(in);*/
                                pDialog.dismiss();


                            } else {

                                Toast.makeText(Activity_Q_and_Ans.this, "else", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(Activity_Q_and_Ans.this, "no internet connection..!", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getApplicationContext(), Activity_Select_Place.class);
                        startActivity(i);
                        pDialog.dismiss();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                System.out.print("username_ques" + username);
                map.put(KEY_EMAIL, username);
                System.out.print("quest_q" + ques);
                map.put(KEY_QUEST, ques);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    private void makeJsonRequest() {
        infoLists.clear();

        StringRequest request = new StringRequest(Request.Method.POST,
                AppConfig.quest_list_url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());
                JSONObject jsonObject;
                try {

                    jsonObject = new JSONObject(response).getJSONObject("response");
                    JSONArray array = jsonObject.getJSONArray("Question");
                    System.out.println("hi inside_newcall" + array);
                    for (int i = 0; i < array.length(); i++) {

                        JSONObject jsonObj = array.getJSONObject(i);
                        //   final GetSet_NewCall sched = new GetSet_NewCall();
                        getSet_newCall = new GetSet_QandA();

                        Log.d("MYTAG", jsonObj.getString("ask_id"));
                        getSet_newCall.setask_id(jsonObj.getString("ask_id"));
                        getSet_newCall.setqustion(jsonObj.getString("qustion"));
                        getSet_newCall.setdate(jsonObj.getString("date"));
                        getSet_newCall.setusername(jsonObj.getString("username"));
                        getSet_newCall.settime(jsonObj.getString("time"));
                        getSet_newCall.setcount(jsonObj.getString("count"));

                        /******** Take Model Object in ArrayList **********/
                        infoLists.add(getSet_newCall);
                        pDialog.dismiss();

                    }

                    list.setAdapter(new QansA_Adapter(Activity_Q_and_Ans.this,
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
                Toast.makeText(Activity_Q_and_Ans.this, "no internet connection..!", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(), Activity_Select_Place.class);
                startActivity(i);
                pDialog.dismiss();

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> map = new HashMap<String, String>();


                return map;
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
                        Picasso.with(Activity_Q_and_Ans.this)
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
                        Toast.makeText(Activity_Q_and_Ans.this, "Check your internet connection..!", Toast.LENGTH_SHORT).show();

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


}

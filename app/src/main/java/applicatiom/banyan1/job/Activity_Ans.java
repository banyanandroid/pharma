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

import adapter.Ans_Adapter;
import adapter.QansA_Adapter;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import gobal.AppConfig;
import gobal.GetSet_ans;


/**
 * Created by Banyan1 on 4/21/2016.
 */
public class Activity_Ans extends ActionBarActivity {

    ImageView imageView;
    /*progress bar*/
    private ProgressDialog pDialog;
    /*queue*/
    public static RequestQueue queue;
    private Toolbar toolbar;
    EditText Edt_ans;
    String ans;
    Button b_send;
    String username, ask_id, ques_dpl;
    private static final String TAG = "";


    // Test Github

    public static final String KEY_EMAIL = "email";
    public static final String KEY_Ans = "answer";
    public static final String KEY_QUEST_id = "question_id";

    static ArrayList<HashMap<String, String>> complaint_list;

    HashMap<String, String> params = new HashMap<String, String>();

    ListView list;
    private GetSet_ans getSet_newCall;
    private List<GetSet_ans> infoLists = new ArrayList<>();

    EditText t1;


    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ans);

        Bundle args = getIntent().getExtras();
        ask_id = args.getString("id");
        ques_dpl = args.getString("question");
        System.out.print("lllllll" + ques_dpl);
        System.out.print("lllllll" + ask_id);


        imageView = (ImageView) findViewById(R.id.image_banner);
        Edt_ans = (EditText) findViewById(R.id.edit_answer);
        t1 = (EditText) findViewById(R.id.edit_answerr);
        System.out.print("jiji_ques" + AppConfig.str_quest);
        t1.setText(ques_dpl);

        b_send = (Button) findViewById(R.id.button_send);

        //action bar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("List of Answer");


        try {
          /*  pDialog = new ProgressDialog(Activity_Select_Place.this);
            pDialog.setMessage("Please wait...");
            pDialog.show();
            pDialog.setCancelable(false);*/
            queue = Volley.newRequestQueue(Activity_Ans.this);
            makeJsonObjectRequest_image();
        } catch (Exception e) {
            // TODO: handle exception
        }

        b_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ans = Edt_ans.getText().toString().trim();

                SharedPreferences pref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

                username = pref.getString("username", "");

                Log.d(TAG, username);


                if (username.equals("")) {
                    //Toast.makeText(Activity_Joblist.this, "empty" , Toast.LENGTH_LONG).show();
                    Intent in = new Intent(Activity_Ans.this, Activity_login.class);
                    startActivity(in);


                } else if ((ans.equals(""))) {
                    // Toast.makeText(MainActivity.this, "please enter email", Toast.LENGTH_SHORT)show();
                    Crouton.makeText(Activity_Ans.this,
                            "please enter answer..!",
                            Style.INFO)
                            .show();
                } else {
                    try {
                        pDialog = new ProgressDialog(Activity_Ans.this);
                        pDialog.setMessage("Please wait...");
                        pDialog.show();
                        pDialog.setCancelable(false);
                        queue = Volley.newRequestQueue(Activity_Ans.this);

                        AnsJson();
                    } catch (Exception e) {
                        // TODO: handle exception
                    }

                }

            }
        });

        try

        {
            pDialog = new ProgressDialog(Activity_Ans.this);
            pDialog.setMessage("Please wait...");
            pDialog.show();
            pDialog.setCancelable(false);
            queue = Volley.newRequestQueue(Activity_Ans.this);

            makeJsonRequest();


            System.out.print("fufufu inside try");

        } catch (Exception e) {
            // TODO: handle exception
        }
        complaint_list = new ArrayList<HashMap<String, String>>();

        list = (ListView) findViewById(R.id.listView_ans);


    }

    private void AnsJson() {


        final StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.ans_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        JSONObject jsonResponse;
                        try {

                            jsonResponse = new JSONObject(response).getJSONObject("Response");
                            System.out.println("ans_url1" + jsonResponse);
                            String obj = jsonResponse.getString("message");
                            System.out.println("ans_url2" + obj);

                            if (obj.equals("You have been sucessfully posted ")) {

                                Toast.makeText(Activity_Ans.this, obj, Toast.LENGTH_SHORT).show();
                                Edt_ans.setText("");
                                Intent intent = getIntent();
                                finish();
                                startActivity(intent);
                                pDialog.dismiss();


                            } else {

                                Toast.makeText(Activity_Ans.this, "else", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(Activity_Ans.this, "no internet connection..!", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getApplicationContext(), Activity_Select_Place.class);
                        startActivity(i);
                        pDialog.dismiss();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();

                System.out.print("username_ques" + Activity_login.email);
                map.put(KEY_EMAIL, username);
                System.out.print("quest_q" + ans);
                map.put(KEY_Ans, ans);
                System.out.print("quest_q" + ask_id);
                map.put(KEY_QUEST_id, ask_id);
                System.out.print("ffffff" + map);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    private void makeJsonRequest() {
        infoLists.clear();

        StringRequest request = new StringRequest(Request.Method.POST,
                AppConfig.ans_list_url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());
                JSONObject jsonObject;
                try {

                    jsonObject = new JSONObject(response).getJSONObject("response");
                    JSONArray array = jsonObject.getJSONArray("Answer");
                    System.out.println("hi inside_newcall" + array);
                    for (int i = 0; i < array.length(); i++) {

                        JSONObject jsonObj = array.getJSONObject(i);
                        //   final GetSet_NewCall sched = new GetSet_NewCall();
                        getSet_newCall = new GetSet_ans();

                        Log.d("MYTAG", jsonObj.getString("answer_id"));
                        getSet_newCall.setanswer_id(jsonObj.getString("answer_id"));
                        getSet_newCall.setanswer(jsonObj.getString("answer"));
                        getSet_newCall.setquestion_id(jsonObj.getString("question_id"));
                        getSet_newCall.setdate(jsonObj.getString("date"));
                        getSet_newCall.setusername(jsonObj.getString("username"));

                        infoLists.add(getSet_newCall);
                        pDialog.dismiss();

                    }

                    list.setAdapter(new Ans_Adapter(Activity_Ans.this,
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
                Toast.makeText(Activity_Ans.this, "no internet connection..!", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(), Activity_Select_Place.class);
                startActivity(i);
                pDialog.dismiss();

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> map = new HashMap<String, String>();
                System.out.print("quest_q" + ask_id);
                map.put(KEY_QUEST_id, ask_id);
                System.out.print("ffffff" + map);
                return map;
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
                        Picasso.with(Activity_Ans.this)
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
                        Toast.makeText(Activity_Ans.this, "Check your internet connection..!", Toast.LENGTH_SHORT).show();

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

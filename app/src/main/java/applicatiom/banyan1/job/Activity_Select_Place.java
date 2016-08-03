package applicatiom.banyan1.job;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;

import android.speech.RecognizerIntent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import gobal.AppConfig;


import android.view.View.OnClickListener;
import android.support.v7.widget.Toolbar;


public class Activity_Select_Place extends ActionBarActivity implements OnClickListener {

    //Declaring an Spinner
    private Spinner s1, s2;
    public static final String KEY_EMAIL = "id";
    public static final String KEY_ZONE = "zone_id";
    public static final String KEY_ZONE_NAME = "zone_name";


    public static String text;
    private Toolbar toolbar;
    private static final String TAG = "";

    /*progress bar*/
    private ProgressDialog pDialog;
    /*queue*/
    public static RequestQueue queue;

    //An ArrayList for Spinner Items
    List<String> list = new ArrayList<String>();
    //An ArrayList for Spinner Items
    List<String> list1 = new ArrayList<String>();
    private ImageButton btnSpeak,btnSpeak1;
    private TextView txtSpeechInput;
    String compareValue;
    public static int count;


    Button b1;
    public static String id;
    String values;
    public static String str_zone_id;

    ImageView imageView;
    String zone = "";

    TextView t1, t2, t3, t4, t5;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    ArrayAdapter<String> dataAdapter1;

    String str_zone_values;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select__place);

        //Initializing Spinner
        s1 = (Spinner) findViewById(R.id.spinner);
        s2 = (Spinner) findViewById(R.id.spinner_zone);
        b1 = (Button) findViewById(R.id.omg);
        imageView = (ImageView) findViewById(R.id.image_banner);
        t1 = (TextView) findViewById(R.id.txt_ask);
        t2 = (TextView) findViewById(R.id.txt_lastest_sp);
        t3 = (TextView) findViewById(R.id.txt_new);
        t4 = (TextView) findViewById(R.id.txt_view_sp);
        t5 = (TextView) findViewById(R.id.txt_post);

        txtSpeechInput = (TextView) findViewById(R.id.txtSpeechInput);

        try {
           /* pDialog = new ProgressDialog(Activity_Select_Place.this);
            pDialog.setMessage("Please wait...");
            pDialog.show();
            pDialog.setCancelable(false);*/
            queue = Volley.newRequestQueue(Activity_Select_Place.this);
            makeJsonObjectRequest_zone();

        } catch (Exception e) {
            // TODO: handle exception
        }

        btnSpeak = (ImageButton) findViewById(R.id.btnSpeak);
        //btnSpeak1 = (ImageButton) findViewById(R.id.btnSpeak1);

        btnSpeak.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                    promptSpeechInput();




            }
        });

       /* btnSpeak1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                promptSpeechInput();

            }
        });*/







        t1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent it = new Intent(getApplicationContext(), Activity_Q_and_Ans.class);
                startActivity(it);

            }
        });

        t2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent itm = new Intent(getApplicationContext(), Activity_NewList.class);
                startActivity(itm);

            }
        });
        t3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itm = new Intent(getApplicationContext(), Activity_training.class);
                startActivity(itm);

            }
        });
        t4.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences pref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                String username = pref.getString("username", "");

                Log.d(TAG, username);


                if (username.equals("")) {
                    //Toast.makeText(MainActivity.this, "if", Toast.LENGTH_LONG).show();
                    Intent in = new Intent(Activity_Select_Place.this, Activity_login.class);
                    startActivity(in);


                } else {
                    //Toast.makeText(MainActivity.this, "else", Toast.LENGTH_LONG).show();
                    Intent itm = new Intent(getApplicationContext(), Activity_ViewAppliedPosted.class);
                    startActivity(itm);
                }

            }
        });

        t5.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itm = new Intent(getApplicationContext(), Activity_Postjob.class);
                startActivity(itm);

            }
        });


        try {
            pDialog = new ProgressDialog(Activity_Select_Place.this);
            pDialog.setMessage("Please wait...");
            pDialog.show();
            pDialog.setCancelable(false);
            queue = Volley.newRequestQueue(Activity_Select_Place.this);
            makeJsonObjectRequest_image();
        } catch (Exception e) {
            // TODO: handle exception
        }


        //action bar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Select Place");

        b1.setOnClickListener(this);

        s2.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(
                            AdapterView<?> parent, View view, int position, long id) {
                        int count = s2.getSelectedItemPosition();
                        System.out.println("huhihi" + count);

                        if (count == 0) {
                            //Toast.makeText(getApplication(), "Please Select something", Toast.LENGTH_LONG).show();
                        } else {
                          //  Toast.makeText(getApplication(), s2.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
                            zone = s2.getSelectedItem().toString();
                            System.out.println("spinner values" + zone);
                            queue = Volley.newRequestQueue(Activity_Select_Place.this);
                            makeJsonObjectRequest();

                        }

                    }

                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

        s1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                //Toast.makeText(Activity_Select_Place.this, "Spinner 2", Toast.LENGTH_LONG).show();
                if (s1.getSelectedItem().equals("Select Place")) {
                    b1.setText("SELECT");

                } else {
                    b1.setText("FIND");
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

    }

    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    txtSpeechInput.setText(result.get(0));
                    compareValue = txtSpeechInput.getText().toString();
                    System.out.println("spinner_values" + compareValue);

                    zone = s2.getSelectedItem().toString();
                    System.out.println("spinner values" + zone);

                    if(zone.equalsIgnoreCase("Select Zone")){
                        for(int j =0 ; j<list1.size(); j++) {

                            if(list1.get(j).toString().equalsIgnoreCase(compareValue)){

                                s2.setSelection(j+1-1);

                            }else {
                                System.out.println("am  cp");

                            }
                        }
                    }else{
                        for(int j =0 ; j<list.size(); j++) {

                            if(list.get(j).toString().equalsIgnoreCase(compareValue)){

                                s1.setSelection(j+1-1);

                            }else {
                                System.out.println("am  cp");

                            }
                        }

                    }



                    txtSpeechInput.setText("");


                }
                break;
            }

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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_cancel) {

            Intent intent = new Intent(Intent.ACTION_MAIN);

            intent.addCategory(Intent.CATEGORY_HOME);

            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            startActivity(intent);

            finish();

            System.exit(0);


            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // loading the location from json_zone

    private void makeJsonObjectRequest_zone() {


        System.out.println("method inside zone");

        StringRequest request = new StringRequest(Request.Method.GET,
                AppConfig.slct_zone_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                JSONObject jsonObject;
                try {

                    jsonObject = new JSONObject(response).getJSONObject("response");
                    JSONArray arr = jsonObject.getJSONArray("place");
                    System.out.println("hi inside" + arr);

                    list1.add("Select Zone");

                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject jsonObj = arr.getJSONObject(i);
                        str_zone_id = jsonObj.getString("zone_id");
                        String zone_name = jsonObj.getString("zone_name");
                        //Adding the name of the place to array list
                        list1.add(zone_name);
                        dataAdapter1 = new ArrayAdapter<String>(Activity_Select_Place.this,
                                android.R.layout.simple_spinner_item, list1);
                        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        s2.setAdapter(dataAdapter1);





                        // pDialog.dismiss();


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
                        Toast.makeText(Activity_Select_Place.this, "no internet connection wait a mint and proceed..!", Toast.LENGTH_SHORT).show();
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
    private void makeJsonObjectRequest() {


        System.out.println("method inside");

        StringRequest request = new StringRequest(Request.Method.POST,
                AppConfig.slct_loc_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                JSONObject jsonObject;
                try {

                    jsonObject = new JSONObject(response).getJSONObject("response");
                    JSONArray arr = jsonObject.getJSONArray("place");
                    System.out.println("hi inside_loc" + arr);
                    list.clear();
                    list.add("Select Place");

                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject jsonObj = arr.getJSONObject(i);
                        String id = jsonObj.getString("id");
                        String location = jsonObj.getString("location");
                        //Adding the name of the place to array list
                        list.add(location);
                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(Activity_Select_Place.this,
                                android.R.layout.simple_spinner_item, list);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        s1.setAdapter(dataAdapter);


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
                        Toast.makeText(Activity_Select_Place.this, "no internet connection wait a mint and proceed..!", Toast.LENGTH_SHORT).show();
                        pDialog.dismiss();

                    }
                }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> map = new HashMap<String, String>();
                System.out.println("CAME");
                map.put("zone_id", zone);
                System.out.println("koko" + zone);
                System.out.println("iiiiiio" + map);
                return map;


            }

        };

        // Adding request to request queue
        queue.add(request);

    }

    //select button event
    public void onClick(View v) {

        text = s1.getSelectedItem().toString();
        System.out.println("spinner values" + text);

        SharedPreferences pref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        // We need an editor object to make changes
        SharedPreferences.Editor edit = pref.edit();

        // Set/Store data
        edit.putString("location", text);


        // Commit the changes
        edit.commit();


        try {
            if (s2.getSelectedItem().toString() == "Select Zone") {


                Toast.makeText(Activity_Select_Place.this, "please select your zone", Toast.LENGTH_SHORT).show();

            } else if (s1.getSelectedItem().toString() == "Select Place") {


                Toast.makeText(Activity_Select_Place.this, "please select your location", Toast.LENGTH_SHORT).show();

            } else {

                Intent it = new Intent(getApplicationContext(), Activity_JobOpp_Grid.class);
                startActivity(it);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }

    }


    //````````````````````````````````````````````````````````````````````````


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
                        Picasso.with(Activity_Select_Place.this)
                                .load(image)
                                .into(imageView);
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
                        Toast.makeText(Activity_Select_Place.this, "Check your internet connection..!", Toast.LENGTH_SHORT).show();

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



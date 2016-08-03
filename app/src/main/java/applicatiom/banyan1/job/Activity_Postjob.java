package applicatiom.banyan1.job;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
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

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import gobal.AppConfig;

import static applicatiom.banyan1.job.CommonUtilities.SENDER_ID;


public class Activity_Postjob extends ActionBarActivity {


    // declare the  items

    private EditText inputName, inputMob, inputCompany, inputLocation, inputExpr;
    private TextInputLayout inputLayoutName, inputLayoutMob, inputLayoutCompany, inputLayoutLocation, inputLayoutExpr;


    private CoordinatorLayout coordinatorLayout;
    private FloatingActionButton fab;


    List<String> list = new ArrayList<String>();
    //core declare
    /*progress bar*/
    private ProgressDialog pDialog;
    /*string declare*/
    private static final String TAG = "";
    static final String TAG_ID = "name";


    String str_mob, str_company, str_loc;
    String str_expr;
    public static String str_name;

    /*queue*/
    public static RequestQueue queue;

    int value = 10;

    private Toolbar toolbar;
    ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postjob);

        //button
        fab = (FloatingActionButton) findViewById(R.id.fab);

        //edit text
        inputName = (EditText) findViewById(R.id.input_name);
        inputMob = (EditText) findViewById(R.id.input_mob_create);
        inputCompany = (EditText) findViewById(R.id.input_company);
        inputLocation = (EditText) findViewById(R.id.input_location);
        inputExpr = (EditText) findViewById(R.id.input_expr);


//image

        imageView = (ImageView) findViewById(R.id.image_banner);


        try {

            queue = Volley.newRequestQueue(Activity_Postjob.this);
            makeJsonObjectRequest_image();
        } catch (Exception e) {
            // TODO: handle exception
        }


        //action bar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Post Job");

        //coordinator
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);

        //textinputlayout
        inputLayoutName = (TextInputLayout) findViewById(R.id.input_layout_name);
        inputLayoutMob = (TextInputLayout) findViewById(R.id.input_layout_mob);
        inputLayoutLocation = (TextInputLayout) findViewById(R.id.input_layout_jloc);
        inputLayoutCompany = (TextInputLayout) findViewById(R.id.input_layout_company);
        inputLayoutExpr = (TextInputLayout) findViewById(R.id.input_layout_expr);



        /*button click*/

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                str_mob = inputMob.getText().toString().trim();
                str_name = inputName.getText().toString().trim();
                str_company = inputCompany.getText().toString().trim();
                str_loc = inputLocation.getText().toString().trim();
                str_expr = inputExpr.getText().toString().trim();

                if (str_name.equals("")) {
                    inputLayoutName.setError("please enter name");
                } else if (str_mob == null || str_mob.length() < 9 || str_mob.length() > 10) {
                    inputLayoutMob.setError("please enter mobile number");
                } else if (str_company.equals("")) {
                    inputLayoutCompany.setError("please enter Company Name");
                } else if (str_loc.equals("")) {
                    inputLayoutLocation.setError("please enter Location");
                } else if (str_expr.equals("")) {
                    inputLayoutExpr.setError("please enter Experience");
                } else {
                    try {
                        pDialog = new ProgressDialog(Activity_Postjob.this);
                        pDialog.setMessage("Please wait...");
                        pDialog.show();
                        pDialog.setCancelable(false);
                        queue = Volley.newRequestQueue(Activity_Postjob.this);
                        System.out.println("no of posting=1");
                        makeJsonObjectRequest();
                    } catch (Exception e) {
                        // TODO: handle exception
                    }
                }

            }


        });


       /* try {
            pDialog = new ProgressDialog(Activity_Postjob.this);
            pDialog.setMessage("Please wait...");
            pDialog.show();
            pDialog.setCancelable(false);
            queue = Volley.newRequestQueue(Activity_Postjob.this);
            makeJsonObjectRequest_autocompany();
        } catch (Exception e) {
            // TODO: handle exception
        }*/
    }

    // validating email id
    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
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


    //json with volley coding register

    private void makeJsonObjectRequest() {


        System.out.println("method inside post1111111111111111");

        StringRequest request = new StringRequest(Request.Method.POST,
                AppConfig.job_post_url, new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                System.out.println("inside res method");
                Log.d(TAG, response.toString());
                try {
                    System.out.println("inside try");
                    JSONObject obj = new JSONObject(response).getJSONObject("response");

                    System.out.println("gaya1" + obj);


                    String s = obj.getString("Success");
                    int success = Integer.parseInt(s);


                    System.out.println("gaya" + success);

                    if (success == 1) {
                        System.out.println("if iniside");
                        //intent nxt class
                        Intent i = new Intent(getApplicationContext(), Activity_Select_Place.class);
                        startActivity(i);
                        Toast.makeText(Activity_Postjob.this, "Job Posted Successfully..!", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(Activity_Postjob.this, "Please Check Internet Connection..!", Toast.LENGTH_SHORT).show();

                    }


                } catch (JSONException e) {

                    // TODO Auto-generated catch block
                    System.out.println("inside catch" + e);
                    e.printStackTrace();
                }
                pDialog.hide();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("kiiikik" + error.toString());
                Toast.makeText(Activity_Postjob.this, "no internet connection..!", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(), Activity_Select_Place.class);
                startActivity(i);
                pDialog.dismiss();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                str_name = inputName.getText().toString().trim();
                str_mob = inputMob.getText().toString().trim();
                str_company = inputCompany.getText().toString().trim();
                str_loc = inputLocation.getText().toString().trim();
                str_expr = inputExpr.getText().toString().trim();

                System.out.println("map");


                params.put("name", str_name);
                System.out.println("name" + str_name);
                params.put("phone", str_mob);
                params.put("location", str_loc);
                params.put("expr", str_expr);
                params.put("company", str_company);
                System.out.println("totall" + params);
                return params;

            }

        };

        // Adding request to request queue
        queue.add(request);
    }

    // loading the location from json
    private void makeJsonObjectRequest_autocompany() {
        System.out.println("method insideddddddd");

        StringRequest request = new StringRequest(Request.Method.GET,
                AppConfig.company_list_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                JSONObject jsonObject;
                try {

                    jsonObject = new JSONObject(response).getJSONObject("response");
                    JSONArray arr = jsonObject.getJSONArray("company_list");
                    System.out.println("hi inside" + arr);
                    list.add("Select Company");
                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject jsonObj = arr.getJSONObject(i);
                        String comapany_id = jsonObj.getString("comapany_id");
                        String company = jsonObj.getString("company");
                        //Adding the name of the place to array list
                        list.add(company);

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
                        Toast.makeText(Activity_Postjob.this, "no internet connection..!", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getApplicationContext(), Activity_Select_Place.class);
                        startActivity(i);
                        pDialog.dismiss();
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
    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
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
                        Picasso.with(Activity_Postjob.this)
                                .load(image)
                                .into(imageView);



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
                        Toast.makeText(Activity_Postjob.this, "Check your internet connection..!", Toast.LENGTH_SHORT).show();

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




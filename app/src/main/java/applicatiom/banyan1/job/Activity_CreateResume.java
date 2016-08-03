package applicatiom.banyan1.job;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
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

import java.util.HashMap;
import java.util.Map;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import gobal.AppConfig;


public class Activity_CreateResume extends ActionBarActivity {
    private FloatingActionButton fab;
    private EditText inputName, inputEmail, inputMob, inputpass, inputcpass, inputadd, inputpincode, inputpresentcom, inputcurrentposition, inputspecficity, inputqual, inputloc, inputexpfrh_create, inputskills;
    private EditText inputexp, inputsal;
    private TextInputLayout inputLayoutName, inputLayoutEmail, inputLayoutpass, inputLayoutcpass, inputLayoutMob;
    private TextInputLayout inputLayoutqual, inputLayoutadd, inputLayoutpincode, inputLayoutcurrentpostion, inputLayoutspecf;
    private TextInputLayout inputLayoutpresentcom, inputLayoutloc;
    /*progress bar*/
    private ProgressDialog pDialog;
    /*queue*/
    public static RequestQueue queue;
    private Toolbar toolbar;
    String username;
    private static final String TAG = "";
    ImageView imageView;

    public static String str_email;
    String str_name, str_mob, str_qual, str_loc;
    String str_sexp, str_ssal, str_exp_yrs;
    String exp_type;

    String str_add, str_precom, str_curpos, str_spec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_resume);


        SharedPreferences pref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        username = pref.getString("username", Activity_login.email);

        Log.d("koooeswari", username);


        //action bar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Create Resume");
        //image

        imageView = (ImageView) findViewById(R.id.image_banner);


        try {
           /* pDialog = new ProgressDialog(Activity_Select_Place.this);
            pDialog.setMessage("Please wait...");
            pDialog.show();
            pDialog.setCancelable(false);*/
            queue = Volley.newRequestQueue(Activity_CreateResume.this);
            makeJsonObjectRequest_image();
        } catch (Exception e) {
            // TODO: handle exception
        }


        //textinputlayout
        inputLayoutName = (TextInputLayout) findViewById(R.id.input_layout_name);
        inputLayoutEmail = (TextInputLayout) findViewById(R.id.input_layout_email);
        inputLayoutMob = (TextInputLayout) findViewById(R.id.input_layout_mob);

        inputLayoutqual = (TextInputLayout) findViewById(R.id.input_layout_qual);
        inputLayoutloc = (TextInputLayout) findViewById(R.id.input_layout_jloc);

        inputLayoutadd = (TextInputLayout) findViewById(R.id.input_layout_addr);

        inputLayoutcurrentpostion = (TextInputLayout) findViewById(R.id.input_layout_currentpos);

        inputLayoutpresentcom = (TextInputLayout) findViewById(R.id.input_layout_precompany);


        try {
            pDialog = new ProgressDialog(Activity_CreateResume.this);
            pDialog.setMessage("Please wait...");
            pDialog.show();
            pDialog.setCancelable(false);
            queue = Volley.newRequestQueue(Activity_CreateResume.this);
            makeJsonObjectRequest();
        } catch (Exception e) {
            // TODO: handle exception
        }
        //button
        fab = (FloatingActionButton) findViewById(R.id.fab);

        //edit text
        inputName = (EditText) findViewById(R.id.input_name_create);
        inputEmail = (EditText) findViewById(R.id.input_email_create);
        inputMob = (EditText) findViewById(R.id.input_mob_create);
        inputqual = (EditText) findViewById(R.id.input_qual_create);
        inputloc = (EditText) findViewById(R.id.input_jloc_create);
        inputadd = (EditText) findViewById(R.id.input_addr_create);
        inputexpfrh_create = (EditText) findViewById(R.id.input_expfrh_create);
        inputpresentcom = (EditText) findViewById(R.id.input_precompany_create);
        inputpresentcom.setVisibility(View.GONE);
        inputexp = (EditText) findViewById(R.id.input_exp_create);
        inputexp.setVisibility(View.GONE);
        inputsal = (EditText) findViewById(R.id.input_sal_create);
        inputsal.setVisibility(View.GONE);
        inputcurrentposition = (EditText) findViewById(R.id.input_currentpos_create);
        inputcurrentposition.setVisibility(View.GONE);
        inputspecficity = (EditText) findViewById(R.id.input_specl_create);
        inputspecficity.setVisibility(View.GONE);







        /*button click*/

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    pDialog = new ProgressDialog(Activity_CreateResume.this);
                    pDialog.setMessage("Please wait...");
                    pDialog.show();
                    pDialog.setCancelable(false);
                    queue = Volley.newRequestQueue(Activity_CreateResume.this);
                    str_name = inputName.getText().toString().trim();
                    System.out.println("kikik" + str_name);
                    str_email = inputEmail.getText().toString().trim();
                    System.out.println("kikik1" + str_email);
                    str_mob = inputMob.getText().toString().trim();
                    System.out.println("kikik2" + str_mob);
                    str_qual = inputqual.getText().toString().trim();
                    System.out.println("kikik3" + str_qual);
                    str_add = inputadd.getText().toString().trim();
                    System.out.println("kikik4" + str_add);
                    str_loc = inputloc.getText().toString().trim();
                    System.out.println("kiki5k" + str_loc);
                    str_sexp = inputexpfrh_create.getText().toString().trim();
                    ;
                    System.out.println("kikik6" + str_sexp);


                    str_precom = inputpresentcom.getText().toString().trim();
                    System.out.println("kikik8" + str_precom);
                    str_exp_yrs = inputexp.getText().toString().trim();
                    System.out.println("kikik9" + str_exp_yrs);
                    str_curpos = inputcurrentposition.getText().toString().trim();
                    System.out.println("kikik10" + str_curpos);
                    str_ssal = inputsal.getText().toString().trim();
                    System.out.println("kikik11" + str_ssal);
                    str_spec = inputspecficity.getText().toString().trim();
                    System.out.println("kikik12" + str_spec);
                    makeJsonObjectRequest_apply_upade();
                } catch (Exception e) {
                    // TODO: handle exception
                }

            }


        });


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


        System.out.println("method inside");

        StringRequest request = new StringRequest(Request.Method.POST,
                AppConfig.createresume_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                JSONObject jsonObject;
                try {

                    jsonObject = new JSONObject(response).getJSONObject("response");
                    JSONArray arr = jsonObject.getJSONArray("user_details");
                    System.out.println("hi inside_joblist" + arr);

                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject jsonObj = arr.getJSONObject(i);

                        // int id = Integer.parseInt(jsonObject.optString("id").toString());
                        String name = jsonObj.getString("name");
                        String email = jsonObj.getString("email");
                        String phone = jsonObj.getString("phone");

                        String qualification = jsonObj.getString("qualification");

                        String address = jsonObj.getString("address");

                        String job_location = jsonObj.getString("job_location");

                        exp_type = jsonObj.getString("exp_type");
                        String present_company = jsonObj.getString("present_company");

                        String exp_year = jsonObj.getString("exp_year");
                        String current_position = jsonObj.getString("current_position");
                        String salary = jsonObj.getString("salary");
                        String speciality = jsonObj.getString("speciality");

                        inputName.setText(name);
                        inputEmail.setText(email);
                        inputMob.setText(phone);
                        inputqual.setText(qualification);
                        inputadd.setText(address);
                        inputloc.setText(job_location);
                        inputexpfrh_create.setText(exp_type);
                        inputpresentcom.setText(present_company);
                        inputexp.setText(exp_year);
                        inputcurrentposition.setText(current_position);
                        inputsal.setText(salary);
                        inputspecficity.setText(speciality);

                        if (exp_type.equals("Experience")) {
                            inputpresentcom.setVisibility(View.VISIBLE);
                            inputexp.setVisibility(View.VISIBLE);
                            inputsal.setVisibility(View.VISIBLE);
                            inputcurrentposition.setVisibility(View.VISIBLE);
                            inputspecficity.setVisibility(View.VISIBLE);
                        } else {
                            Toast.makeText(Activity_CreateResume.this, "You declare your are finisher..!", Toast.LENGTH_LONG).show();

                        }


                        pDialog.dismiss();

                    }


                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("kiiikik" + error.toString());
                Toast.makeText(Activity_CreateResume.this, "no internet connection..!", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(), Activity_Select_Place.class);
                startActivity(i);
                pDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("email", username);

                return map;
            }

        };

        // Adding request to request queue
        queue.add(request);
    }


    //update

    private void makeJsonObjectRequest_apply_upade() {


        System.out.println("method inside");

        StringRequest request = new StringRequest(Request.Method.POST,
                AppConfig.update_apply_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                JSONObject jsonObject;
                try {

                    System.out.println("inside try");
                    JSONObject obj = new JSONObject(response).getJSONObject("response");

                    System.out.println("gaya1" + obj);


                    String s = obj.getString("Success");
                    String details = obj.getString("Details");
                    int success = Integer.parseInt(s);


                    System.out.println("gaya" + success);

                    if (success == 1) {

                        Toast.makeText(Activity_CreateResume.this, details, Toast.LENGTH_LONG).show();

                        //intent nxt class
                        Intent i = new Intent(getApplicationContext(), Activity_JobOpp_Grid.class);
                        startActivity(i);
                        pDialog.dismiss();


                    } else {
                        Toast.makeText(Activity_CreateResume.this, "Data Failed..!", Toast.LENGTH_LONG).show();
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
                        Toast.makeText(Activity_CreateResume.this, "no internet connection..!", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getApplicationContext(), Activity_Select_Place.class);
                        startActivity(i);
                        pDialog.dismiss();

                    }
                }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                System.out.println("map");
                System.out.println("map_eswari" + AppConfig.Item);
                params.put("job_id",AppConfig.Item);
                params.put("name", str_name);
                params.put("email", str_email);
                params.put("phone", str_mob);
                params.put("qualification", str_qual);
                params.put("address", str_add);
                params.put("job_location", str_loc);
                params.put("exp_type", str_sexp);
                params.put("present_company", str_precom);
                params.put("exp_year", str_exp_yrs);
                params.put("current_position", str_curpos);
                params.put("salary", str_ssal);
                params.put("speciality", str_spec);
                System.out.print("put" + params);

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
                        Picasso.with(Activity_CreateResume.this)
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
                        Toast.makeText(Activity_CreateResume.this, "Check your internet connection..!", Toast.LENGTH_LONG).show();

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

package applicatiom.banyan1.job;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import gobal.AppConfig;
import gobal.SessionManagement;


public class Activity_login extends ActionBarActivity implements OnClickListener {


    public static final String KEY_EMAIL = "username";
    public static final String KEY_PASSWORD = "password";

    EditText email_ext, psw_ext;
    private CoordinatorLayout coordinatorLayout;

    private TextInputLayout inputLayoutemail_login, inputLayoutpass_login;

    public static String email;
    private String password;
    Button b1, b2;
    private static final String TAG = "";

    public static String obj;

    /*progress bar*/
    private ProgressDialog pDialog;
    /*queue*/
    public static RequestQueue queue;
    private Toolbar toolbar;
    ImageView imageView;
    private TextInputLayout inputLayoutemail;
    String str_alert_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //image

        imageView = (ImageView) findViewById(R.id.image_banner);


        try {
           /* pDialog = new ProgressDialog(Activity_Select_Place.this);
            pDialog.setMessage("Please wait...");
            pDialog.show();
            pDialog.setCancelable(false);*/
            queue = Volley.newRequestQueue(Activity_login.this);
            makeJsonObjectRequest_image();
        } catch (Exception e) {
            // TODO: handle exception
        }


        //textinputlayout
        inputLayoutemail_login = (TextInputLayout) findViewById(R.id.input_layout_email_login);
        inputLayoutpass_login = (TextInputLayout) findViewById(R.id.input_layout_password_login);
        //action bar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Login/Register");

        //coordinator
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        email_ext = (EditText) findViewById(R.id.input_email);
        psw_ext = (EditText) findViewById(R.id.input_password);
        TextView txt_front = (TextView) findViewById(R.id.txt_front);

        txt_front.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                        showChangeLangDialog();


            }
        });
        //button
        b1 = (Button) findViewById(R.id.login);
        b2 = (Button) findViewById(R.id.sign);
        b1.setOnClickListener(this);


        b2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inta = new Intent(getApplicationContext(), Activity_Registration.class);
                startActivity(inta);
            }
        });

    }

    public void showChangeLangDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.activity_alert_email, null);
        dialogBuilder.setView(dialogView);

        final EditText edt = (EditText) dialogView.findViewById(R.id.emailaddress);
        inputLayoutemail=(TextInputLayout)dialogView.findViewById(R.id.input_layout_alert_emailaddress);

        dialogBuilder.setTitle("Forgot Password !");
        dialogBuilder.setMessage("Please Enter Valid Email");
        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //do something with edt.getText().toString();

                str_alert_email = edt.getText().toString();

                if (str_alert_email.equals("")) {

                    Crouton.makeText(Activity_login.this,
                            "please enter email",
                            Style.INFO)
                            .show();

                } else if (!isValidEmail(str_alert_email)) {
                    Crouton.makeText(Activity_login.this,
                            "please enter valid email",
                            Style.INFO)
                            .show();
                } else {
                    try {
                       //Json
                        try {
                            pDialog = new ProgressDialog(Activity_login.this);
                            pDialog.setMessage("Please wait...");
                            pDialog.show();
                            pDialog.setCancelable(false);
                            queue = Volley.newRequestQueue(Activity_login.this);
                            makeJsonObjectRequest_frgtpswd();
                        }catch (Exception e){

                        }

                    } catch (Exception e) {

                    }
                }

            }
        });

        AlertDialog b = dialogBuilder.create();
        b.show();
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


    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        email = email_ext.getText().toString().trim();
        System.out.print("parameswariiiiiiiiiiiiiiiiiiiii" + email);
        password = psw_ext.getText().toString().trim();

        if (email.equals("")) {
            inputLayoutemail_login.setError("please enter email");
        } else if (password.equals("")) {
            inputLayoutpass_login.setError("please enter password");
        } else {
            try {
                pDialog = new ProgressDialog(Activity_login.this);
                pDialog.setMessage("Please wait...");
                pDialog.show();
                pDialog.setCancelable(false);
                queue = Volley.newRequestQueue(Activity_login.this);
                userLogin();
            } catch (Exception e) {
                // TODO: handle exception
            }
        }


    }


    private void userLogin() {
        email = email_ext.getText().toString().trim();
        password = psw_ext.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        JSONObject jsonResponse;
                        try {

                            jsonResponse = new JSONObject(response).getJSONObject("response");
                            obj = jsonResponse.getString("message");

                            if (obj.equals("Login Success")) {

                                SharedPreferences pref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

                                // We need an editor object to make changes
                                SharedPreferences.Editor edit = pref.edit();

                                // Set/Store data
                                edit.putString("username", email);

                                // Commit the changes
                                edit.commit();

                                Toast.makeText(Activity_login.this, obj, Toast.LENGTH_SHORT).show();

                                Intent i = new Intent(Activity_login.this, Activity_JobOpp_Grid.class);
                                startActivity(i);



                                pDialog.dismiss();


                            } else {

                                Toast.makeText(Activity_login.this, obj, Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(Activity_login.this, "no internet connection..!", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getApplicationContext(), Activity_Select_Place.class);
                        startActivity(i);
                        pDialog.dismiss();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put(KEY_EMAIL, email);
                map.put(KEY_PASSWORD, password);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
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
                        Picasso.with(Activity_login.this)
                                .load(image)
                                .into(imageView);
                        //   pDialog.dismiss();


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
                        Toast.makeText(Activity_login.this, "Check your internet connection..!", Toast.LENGTH_SHORT).show();

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
//forget
    //json with volley coding register

    private void makeJsonObjectRequest_frgtpswd() {


        System.out.println("method inside");

        StringRequest request = new StringRequest(Request.Method.POST,
                AppConfig.forgetpswd_url, new Response.Listener<String>() {


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

                    if (success == 2) {
                        System.out.println("if iniside");

                        Toast.makeText(Activity_login.this, "Not Register Your E - Mail ID", Toast.LENGTH_SHORT).show();


                    } else {
                        Toast.makeText(Activity_login.this, "Password Verification Sent to mail", Toast.LENGTH_SHORT).show();

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
                Toast.makeText(Activity_login.this, "no internet connection..!", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(), Activity_Select_Place.class);
                startActivity(i);
                pDialog.dismiss();

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", str_alert_email);
                System.out.println("forget" + params);
                return params;

            }

        };

        // Adding request to request queue
        queue.add(request);
    }
}





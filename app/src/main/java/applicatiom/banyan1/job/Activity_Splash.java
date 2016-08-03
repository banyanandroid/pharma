package applicatiom.banyan1.job;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.flaviofaria.kenburnsview.RandomTransitionGenerator;
import com.flaviofaria.kenburnsview.Transition;
import com.google.android.gcm.GCMRegistrar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import dmax.dialog.SpotsDialog;
import gobal.AppConfig;

import static applicatiom.banyan1.job.CommonUtilities.SENDER_ID;


public class Activity_Splash extends AppCompatActivity {

    AlertDialog dialog;
    private TextInputLayout inputLayoutemail;

    private KenBurnsView kenBurnsView;
    private boolean isPlay = true;
    ImageView ico;
    TextView txt, txt_count;
    public String totallty;

    /*queue*/
    public static RequestQueue queue;
    /*progress bar*/
    private ProgressDialog pDialog;
    /*Gcm*/
    String regid = "";
    String GcmId = "";
    String str_alert_email;
    String str_alert_email_check;
    String str_user_id = "";
    private static final String TAG = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

/*        SharedPreferences pref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        // We need an editor object to make changes
        SharedPreferences.Editor edit = pref.edit();

        String username = pref.getString("username", "");


        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(Activity_Splash.this);

        str_alert_email_check = sharedPreferences.getString("session_email", "session_email");

        System.out.println("CHECKINGGGGGGG" + str_alert_email_check);



       if (str_alert_email_check.equals("session_email")){

        }else if (str_alert_email_check.equals("")){

       }else{
            try {
                pDialog = new ProgressDialog(Activity_Splash.this);
                pDialog.setCancelable(true);
                pDialog.setMessage("Please wait...");
                pDialog.show();
                queue = Volley.newRequestQueue(Activity_Splash.this);
                Get_GCMID_Verification();

            }catch (Exception e) {

            }

        }


        if (username == null) {
            // Set/Store data
            edit.putString("username", "");
        }

        // Commit the changes
        edit.commit();*/


        try {

            queue = Volley.newRequestQueue(Activity_Splash.this);
            makeJsonObjectRequest_count();
        } catch (Exception e) {
            // TODO: handle exception
        }

        kenBurnsView = (KenBurnsView) findViewById(R.id.image);
        ico = (ImageView) findViewById(R.id.ico);
        txt = (TextView) findViewById(R.id.txt);
        txt_count = (TextView) findViewById(R.id.txt3);


        AccelerateDecelerateInterpolator ACCELERATE_DECELERATE = new AccelerateDecelerateInterpolator();
        RandomTransitionGenerator generator = new RandomTransitionGenerator(5000, ACCELERATE_DECELERATE);
        kenBurnsView.setTransitionGenerator(generator); //set new transition on kenburns view

        kenBurnsView.setTransitionListener(onTransittionListener());

        txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*System.out.println("IDDDD" + str_user_id);*/

                Intent i = new Intent(getApplicationContext(), Activity_Select_Place.class);
                startActivity(i);

                 /*if (str_user_id.equals("")){
                    try {
                        showChangeLangDialog();

                    } catch (Exception e) {

                    }
                }else {

                }*/
            }
        });

    }


/*
    public void showChangeLangDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.activity_alert_email, null);
        dialogBuilder.setView(dialogView);

        final EditText edt = (EditText) dialogView.findViewById(R.id.emailaddress);
        inputLayoutemail=(TextInputLayout)dialogView.findViewById(R.id.input_layout_alert_emailaddress);

        dialogBuilder.setTitle("Pharma Jobs !");
        dialogBuilder.setMessage("Please Enter Valid Email");
        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //do something with edt.getText().toString();

                str_alert_email = edt.getText().toString();

                if (str_alert_email.equals("")) {

                    Crouton.makeText(Activity_Splash.this,
                            "please enter email",
                            Style.INFO)
                            .show();

                } else if (!isValidEmail(str_alert_email)) {
                    Crouton.makeText(Activity_Splash.this,
                            "please enter valid email",
                            Style.INFO)
                            .show();
                } else {
                    try {
                        Get_GCMID();
                    } catch (Exception e) {

                    }
                }

            }
        });

        AlertDialog b = dialogBuilder.create();
        b.show();
    }
*/

    /*// validating email id
    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }*/

    /*//...................................................................................................
    private void Get_GCMID_Verification() {
        GcmId = GCMRegistrar.getRegistrationId(Activity_Splash.this);
        if (GcmId.isEmpty()) {

            GCMRegistrar.register(Activity_Splash.this, SENDER_ID);
            Crouton.makeText(Activity_Splash.this,
                    "Internal ERROR try Again !",
                    Style.INFO)
                    .show();

        } else {
            System.out.println("neeeeeeeeeeeeeeeeeeeeeeeeeeeeewwwwwww" + GcmId);
            try {
                dialog = new SpotsDialog(Activity_Splash.this);
                dialog.show();
                queue = Volley.newRequestQueue(Activity_Splash.this);
                makeJsonemailverification();

            }catch (Exception e) {

            }

            if (GCMRegistrar.isRegisteredOnServer(Activity_Splash.this)) {
                // Skips registration.

            }
        }
    }*/

    //...................................................................................................
   /* private void Get_GCMID() {
        GcmId = GCMRegistrar.getRegistrationId(Activity_Splash.this);
        if (GcmId.isEmpty()) {

            System.out.println("IaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaD Empty");
            GCMRegistrar.register(Activity_Splash.this, SENDER_ID);
            // System.out.println("IaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaD Empty"+GCMIntentService.regid);
            System.out.println("");
            GcmId = GCMRegistrar.getRegistrationId(this);
            System.out.println("IaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaD Empty" + GcmId);
            Crouton.makeText(Activity_Splash.this,
                    "Internal ERROR try Again !",
                    Style.INFO)
                    .show();

        } else {
            //System.out.println("GGGGGGGGGGGGGGGGGGGGGOOOOOOOOOOTTTTTT"+regId);
            System.out.println("neeeeeeeeeeeeeeeeeeeeeeeeeeeeewwwwwww" + GcmId);
            try {
                pDialog = new ProgressDialog(Activity_Splash.this);
                pDialog.setMessage("Please wait...");
                pDialog.show();
                pDialog.setCancelable(true);
                queue = Volley.newRequestQueue(Activity_Splash.this);
                makeJsonObjectRequest();
            } catch (Exception e) {

            }

            if (GCMRegistrar.isRegisteredOnServer(Activity_Splash.this)) {
                // Skips registration.

            }
        }
    }*/
//..............................................................................................

    private KenBurnsView.TransitionListener onTransittionListener() {
        return new KenBurnsView.TransitionListener() {

            @Override
            public void onTransitionStart(Transition transition) {
                //Toast.makeText(MainActivity.this, "start", Toast.LENGTH_SHORT).show();
                ico.setVisibility(View.VISIBLE);
                //txt.setVisibility(View.GONE);
            }

            @Override
            public void onTransitionEnd(Transition transition) {
                //Toast.makeText(Activity_Splash.this, "end", Toast.LENGTH_SHORT).show();
                ico.setVisibility(View.GONE);


            }
        };
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        return true;
    }

    /*****************************
     * VERIFICATION REQ
     ***************/

    /*public void makeJsonemailverification() {

        String tag_json_obj = "json_obj_req";

        System.out.println("CAME DA 1");

        StringRequest request = new StringRequest(Request.Method.POST,
                AppConfig.url_email_verification, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());

                System.out.println("TAGGG" + response.toString());
                try {

                    JSONObject obj = new JSONObject(response);
                    int success = obj.getInt("success");

                    if (success == 1) {

                        JSONArray arr;

                        arr = obj.getJSONArray("chat");

                        for (int i = 0; arr.length() > i; i++) {
                            JSONObject obj1 = arr.getJSONObject(i);

                            str_user_id = obj1.getString("reg_id");


                        }

                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                pDialog.hide();
                pDialog.dismiss();
                hidePDialog();
                dialog.dismiss();
                System.out.println("Shut down");

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                hidePDialog();

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", str_alert_email_check);
                return params;
            }

        };

        // Adding request to request queue
        queue.add(request);
    }*/


    /*******************************
     * Json volley
     ********************************/



    //json with volley coding register
   /* private void makeJsonObjectRequest() {


        System.out.println("method inside");

        StringRequest request = new StringRequest(Request.Method.POST,
                AppConfig.url_email_reg, new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                System.out.println("inside res method");
                Log.d(TAG, response.toString());

                System.out.println("TAGG" + response.toString());
                try {
                    System.out.println("inside try");
                    JSONObject obj = new JSONObject(response);

                    System.out.println("gaya1" + obj);


                    String s = obj.getString("success");
                    int success = Integer.parseInt(s);


                    System.out.println("gaya" + success);

                    if (success == 1) {
                        System.out.println("if iniside");
                        // Toast.makeText(Activity_Registration.this, response, Toast.LENGTH_LONG).show();

                        *//*SharedPreferences pref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

                        // We need an editor object to make changes
                        SharedPreferences.Editor edit = pref.edit();

                        // Set/Store data
                        edit.putString("session_email", str_alert_email);*//*

                        SharedPreferences sharedPreferences = PreferenceManager
                                .getDefaultSharedPreferences(Activity_Splash.this);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("session_email", str_alert_email);
                        editor.commit();


                        //intent nxt class
                        Intent i = new Intent(getApplicationContext(), Activity_Select_Place.class);
                        startActivity(i);
                        Toast.makeText(Activity_Splash.this, "Registed Success..!", Toast.LENGTH_SHORT).show();

                    }


                    else {

                        Crouton.makeText(Activity_Splash.this,
                                "Email id already Registed..!",
                                Style.INFO)
                                .show();
                        Intent i = new Intent(getApplicationContext(), Activity_Select_Place.class);
                        startActivity(i);
                      //  Toast.makeText(Activity_Splash.this, "Email id already Registed..!", Toast.LENGTH_SHORT).show();

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
                Toast.makeText(Activity_Splash.this, "no internet connection..!", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(), Activity_Select_Place.class);
                startActivity(i);
                pDialog.dismiss();

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("gcm_id", GcmId);
                params.put("email", str_alert_email);

                System.out.println("totall" + params);
                return params;

            }

        };

        // Adding request to request queue
        queue.add(request);
    }*/

    //json with volley coding

    private void makeJsonObjectRequest_count() {


        System.out.println("method inside");

        StringRequest request = new StringRequest(Request.Method.GET,
                AppConfig.total_van_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                JSONObject jsonObject;
                try {

                    jsonObject = new JSONObject(response).getJSONObject("response");
                    JSONArray arr = jsonObject.getJSONArray("Vacancy");
                    System.out.println("hi inside_joblist" + arr);

                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject jsonObj = arr.getJSONObject(i);
                        String total = jsonObj.getString("total-jobs");
                        txt_count.setText(total);
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
                        Toast.makeText(Activity_Splash.this, "error res" + error.toString(), Toast.LENGTH_SHORT).show();

                    }
                }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();


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
}


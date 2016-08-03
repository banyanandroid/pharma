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
import android.preference.PreferenceManager;
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
import com.google.android.gcm.GCMRegistrar;
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


public class Activity_Registration extends ActionBarActivity {


    // declare the  items

    private EditText inputName, inputEmail, inputMob, inputpass, inputcpass, inputadd, inputpincode, inputcurrentposition, inputloc;
    private TextInputLayout inputLayoutName, inputLayoutEmail, inputLayoutpass, inputLayoutcpass, inputLayoutMob;
    private TextInputLayout inputLayoutqual, inputLayoutadd, inputLayoutpincode, inputLayoutcurrentpostion, inputLayoutspecf;
    private TextInputLayout inputLayoutpresentcom, inputLayoutloc;
    private CoordinatorLayout coordinatorLayout;
    private FloatingActionButton fab;
    RadioGroup radioSexGroup, radioSexGroup1;
    ImageView i1, i2, i3, i4, i5;

    AutoCompleteTextView autoinputqual, autoinputpresentcom;
    LinearLayout linear1;
    List<String> list = new ArrayList<String>();
    //core declare
    /*progress bar*/
    private ProgressDialog pDialog;
    /*string declare*/
    private static final String TAG = "";
    static final String TAG_ID = "name";
    public static String str_email;
    String str_mob, str_pass, str_cpass, str_qual, str_loc;
    String str_sexp, str_ssal, str_sskill;
    public static String str_name;
    String str_add, str_pincode, str_precom, str_curpos, str_spec;
    /*queue*/
    public static RequestQueue queue;
    String Fresher, Experience;

    //spinner
    Spinner skill, exp, sal, inputspecficity;
    String gender, workvalue;
    RadioButton r1, r2,r3,r4;
    int value = 10;
    private Toolbar toolbar;
    ImageView imageView;

    String regid = "";
    String GcmId= "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cp_registration);

        //auto
        String[] Qualification = {"B.Sc Science", " B.com/B.A", " B.Pharm", " MBA", "Doctorate"};

        //image

        imageView = (ImageView) findViewById(R.id.image_banner);

        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(Activity_Registration.this);

        str_email = sharedPreferences.getString("session_email", "session_email");


        try {
           /* pDialog = new ProgressDialog(Activity_Select_Place.this);
            pDialog.setMessage("Please wait...");
            pDialog.show();
            pDialog.setCancelable(false);*/
            queue = Volley.newRequestQueue(Activity_Registration.this);
            makeJsonObjectRequest_image();
        } catch (Exception e) {
            // TODO: handle exception
        }


        //action bar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Register");

        //coordinator
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);


        //Imageview
        i1 = (ImageView) findViewById(R.id.i1);
        i1.setVisibility(View.GONE);
        i2 = (ImageView) findViewById(R.id.i2);
        i2.setVisibility(View.GONE);
        i3 = (ImageView) findViewById(R.id.i3);
        i3.setVisibility(View.GONE);
        i4 = (ImageView) findViewById(R.id.i4);
        i4.setVisibility(View.GONE);
        i5 = (ImageView) findViewById(R.id.i5);
        i5.setVisibility(View.GONE);


        //textinputlayout
        inputLayoutName = (TextInputLayout) findViewById(R.id.input_layout_name);
        inputLayoutEmail = (TextInputLayout) findViewById(R.id.input_layout_email);
        inputLayoutMob = (TextInputLayout) findViewById(R.id.input_layout_mob);
        inputLayoutpass = (TextInputLayout) findViewById(R.id.input_layout_password);
        inputLayoutcpass = (TextInputLayout) findViewById(R.id.input_layout_cpassword);
        inputLayoutqual = (TextInputLayout) findViewById(R.id.input_layout_qual);
        inputLayoutloc = (TextInputLayout) findViewById(R.id.input_layout_jloc);

        inputLayoutadd = (TextInputLayout) findViewById(R.id.input_layout_addr);
        inputLayoutpincode = (TextInputLayout) findViewById(R.id.input_layout_pincode);
        inputLayoutcurrentpostion = (TextInputLayout) findViewById(R.id.input_layout_currentpos);

        inputLayoutpresentcom = (TextInputLayout) findViewById(R.id.input_layout_precompany);


        //edit text
        inputName = (EditText) findViewById(R.id.input_name);
        inputEmail = (EditText) findViewById(R.id.input_email);
        inputMob = (EditText) findViewById(R.id.input_mob);

        inputpass = (EditText) findViewById(R.id.input_password);
        inputcpass = (EditText) findViewById(R.id.input_cassword);
        //auto
        autoinputqual = (AutoCompleteTextView) findViewById(R.id.input_qual);
        autoinputpresentcom = (AutoCompleteTextView) findViewById(R.id.input_precompany);
        autoinputpresentcom.setVisibility(View.GONE);

        inputloc = (EditText) findViewById(R.id.input_jloc);
        inputadd = (EditText) findViewById(R.id.input_addr);
        inputpincode = (EditText) findViewById(R.id.input_pincode);

        inputcurrentposition = (EditText) findViewById(R.id.input_currentpos);
        inputcurrentposition.setVisibility(View.GONE);
        //spinner
        inputspecficity = (Spinner) findViewById(R.id.input_specl);
        inputspecficity.setVisibility(View.GONE);

        //button
        fab = (FloatingActionButton) findViewById(R.id.fab);

        //radio button
        radioSexGroup = (RadioGroup) findViewById(R.id.radioSex);
        radioSexGroup1 = (RadioGroup) findViewById(R.id.radioSex1);

     /*   inputEmail.setText(str_email);
        inputEmail.setFocusable(false);
*/

        //spinner
        exp = (Spinner) findViewById(R.id.expr);
        exp.setVisibility(View.GONE);
        sal = (Spinner) findViewById(R.id.sal);
        sal.setVisibility(View.GONE);
//        skill = (Spinner) findViewById(R.id.skill);
//        skill.setVisibility(View.GONE);

        //radio exp,fresh
        r1 = (RadioButton) findViewById(R.id.male);
        r2 = (RadioButton) findViewById(R.id.female);

        r3 = (RadioButton) findViewById(R.id.fresher);
        r4 = (RadioButton) findViewById(R.id.exp);

        radioSexGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.male:
                        gender = "Male";
                        break;
                    case R.id.female:
                        gender = "Female";
                        break;

                    default:
                        break;
                }
            }

        });


        radioSexGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.fresher:
                        workvalue = "Fresher";

                        autoinputpresentcom.setVisibility(View.GONE);
                        inputcurrentposition.setVisibility(View.GONE);
                        inputspecficity.setVisibility(View.GONE);
                        exp.setVisibility(View.GONE);
                        sal.setVisibility(View.GONE);
                        i1.setVisibility(View.GONE);
                        i2.setVisibility(View.GONE);
                        i3.setVisibility(View.GONE);
                        i4.setVisibility(View.GONE);
                        i5.setVisibility(View.GONE);

                        break;
                    case R.id.exp:
                        workvalue = "Experience";


                        autoinputpresentcom.setVisibility(View.VISIBLE);
                        inputcurrentposition.setVisibility(View.VISIBLE);
                        inputspecficity.setVisibility(View.VISIBLE);
                        exp.setVisibility(View.VISIBLE);
                        sal.setVisibility(View.VISIBLE);
                        i1.setVisibility(View.VISIBLE);
                        i2.setVisibility(View.VISIBLE);
                        i3.setVisibility(View.VISIBLE);
                        i4.setVisibility(View.VISIBLE);
                        i5.setVisibility(View.VISIBLE);


                        break;

                    default:
                        break;
                }
            }

        });

        /*button click*/

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                str_name = inputName.getText().toString().trim();
                str_email = inputEmail.getText().toString().trim();
                str_mob = inputMob.getText().toString().trim();
                str_pass = inputpass.getText().toString().trim();
                str_cpass = inputcpass.getText().toString().trim();
                str_qual = autoinputqual.getText().toString().trim();
                str_loc = inputloc.getText().toString().trim();
                str_add = inputadd.getText().toString().trim();
                str_pincode = inputpincode.getText().toString().trim();
                str_precom = autoinputpresentcom.getText().toString().trim();
                str_curpos = inputcurrentposition.getText().toString().trim();

                str_spec = inputspecficity.getSelectedItem().toString().trim();
                str_sexp = exp.getSelectedItem().toString();
                str_ssal = sal.getSelectedItem().toString();


                // get selected radio button from radioGroup
                int selectedId = radioSexGroup.getCheckedRadioButtonId();

                // find the radiobutton by returned id
                RadioButton radioButton = (RadioButton) findViewById(selectedId);

                     if (r3.isChecked()) {
                         if (str_name.equals("")) {
                        inputLayoutName.setError("please enter name");
                    } else if (!isValidEmail(str_email)) {
                        inputLayoutEmail.setError("Invalid Email");
                    } else if (str_pass.equals("")) {
                        inputLayoutpass.setError("please enter password");
                    } else if (!str_cpass.equals(str_pass)) {
                        inputLayoutcpass.setError("please enter conform password");
                    } else if (str_mob == null || str_mob.length() < 9 || str_mob.length() > 10) {
                        inputLayoutMob.setError("Invalid length..! please enter correct mobile number");
                    } else if (radioSexGroup.getCheckedRadioButtonId() == -1) {
                        Crouton.makeText(Activity_Registration.this,
                                 "Gender",
                                 Style.INFO)
                                 .show();
                     } else if (str_qual.equals("")) {
                        inputLayoutqual.setError("please enter qualification");
                    } else if (str_add.equals("")) {
                        inputLayoutadd.setError("please enter address");
                    } else if (str_pincode.equals("")) {
                        inputLayoutpincode.setError("please enter pincode");
                    } else if (str_loc.equals("")) {
                        inputLayoutloc.setError("please enter location");
                    } else {
                        try {
                            Get_GCMID();
                        } catch (Exception e) {

                        }
                    }


                } else if(r4.isChecked()) {
                    if (str_name.equals("")) {
                        inputLayoutName.setError("please enter name");
                    } else if (!isValidEmail(str_email)) {
                        inputLayoutEmail.setError("Invalid Email");
                    } else if (str_pass.equals("")) {
                        inputLayoutpass.setError("please enter password");
                    } else if (!str_cpass.equals(str_pass)) {
                        inputLayoutcpass.setError("please enter conform password");
                    } else if (str_mob == null || str_mob.length() < 9 || str_mob.length() > 10) {
                        inputLayoutMob.setError("please enter mobile number");
                    } else if (r1.isChecked()||r2.isChecked()) {
                        Crouton.makeText(Activity_Registration.this,
                                "Gender",
                                Style.INFO)
                                .show();

                    } else if (str_qual.equals("")) {
                        inputLayoutqual.setError("please enter qualification");
                    } else if (str_add.equals("")) {
                        inputLayoutadd.setError("please enter address");
                    } else if (str_pincode.equals("")) {
                        inputLayoutpincode.setError("please enter pincode");
                    } else if (str_loc.equals("")) {
                        inputLayoutloc.setError("please enter location");
                    } else if (str_precom.equals("")) {
                        inputLayoutpresentcom.setError("please enter present company");
                    } else if (str_curpos.equals("")) {
                        inputLayoutcurrentpostion.setError("please enter current position");
                    } else if (str_spec.equals("")) {
                        inputLayoutspecf.setError("please enter specfication");
                    } else if (str_sexp.equals("Select Experience")) {
                        Crouton.makeText(Activity_Registration.this,
                                "please select your experience year",
                                Style.INFO)
                                .show();

                    } else if (str_ssal.equals("Select CTC")) {
                        Crouton.makeText(Activity_Registration.this,
                                "please select your CTC",
                                Style.INFO)
                                .show();

                    }
                    else {

                        try {
                            Get_GCMID();
                        } catch (Exception e) {

                        }

                    }

                }


            }
        });


        //auto_qual
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.select_dialog_item, Qualification);
        autoinputqual.setAdapter(adapter);
        autoinputqual.setThreshold(1);

        try {
            pDialog = new ProgressDialog(Activity_Registration.this);
            pDialog.setMessage("Please wait...");
            pDialog.show();
            pDialog.setCancelable(false);
            queue = Volley.newRequestQueue(Activity_Registration.this);
            makeJsonObjectRequest_autocompany();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    private void Get_GCMID() {
        GcmId = GCMRegistrar.getRegistrationId(Activity_Registration.this);
        if (GcmId.isEmpty()) {

            System.out.println("IaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaD Empty");
            GCMRegistrar.register(Activity_Registration.this, SENDER_ID);
            // System.out.println("IaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaD Empty"+GCMIntentService.regid);
            System.out.println("");
            GcmId = GCMRegistrar.getRegistrationId(this);
            System.out.println("IaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaD Empty"+GcmId);
            try{
                Get_GCMID();

            }catch(Exception e){

            }


        } else {
            Toast.makeText(getApplicationContext(), GcmId, Toast.LENGTH_LONG)
                    .show();
            //System.out.println("GGGGGGGGGGGGGGGGGGGGGOOOOOOOOOOTTTTTT"+regId);
            System.out.println("neeeeeeeeeeeeeeeeeeeeeeeeeeeeewwwwwww"+GcmId);
            try {
                pDialog = new ProgressDialog(Activity_Registration.this);
                pDialog.setMessage("Please wait...");
                pDialog.show();
                pDialog.setCancelable(false);
                queue = Volley.newRequestQueue(Activity_Registration.this);
                makeJsonObjectRequest();
            }catch (Exception e){

            }

            if (GCMRegistrar.isRegisteredOnServer(Activity_Registration.this)) {
                // Skips registration.

            }
        }
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


        System.out.println("method inside");

        StringRequest request = new StringRequest(Request.Method.POST,
                AppConfig.url_reg, new Response.Listener<String>() {


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
                        // Toast.makeText(Activity_Registration.this, response, Toast.LENGTH_LONG).show();

                        SharedPreferences pref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

                        // We need an editor object to make changes
                        SharedPreferences.Editor edit = pref.edit();

                        // Set/Store data
                        edit.putString("email", Activity_Registration.str_email);


                        //intent nxt class
                        Intent i = new Intent(getApplicationContext(), Activity_login.class);
                        startActivity(i);
                        Toast.makeText(Activity_Registration.this, "Registed Success..!", Toast.LENGTH_SHORT).show();

                    } else if (success==0) {
                        Toast.makeText(Activity_Registration.this, "Email id already Registed..!", Toast.LENGTH_SHORT).show();

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
                Toast.makeText(Activity_Registration.this, "no internet connection..!", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(), Activity_Select_Place.class);
                startActivity(i);
                pDialog.dismiss();

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("gcm_id", GcmId);
                params.put("name", str_name);
                params.put("email", str_email);
                params.put("password", str_pass);
                params.put("phone", str_mob);
                params.put("gender", gender);
                params.put("qualification", str_qual);
                params.put("address", str_add);
                params.put("pincode", str_pincode);
                params.put("job_location", str_loc);
                params.put("exp_type", workvalue);
                params.put("present_company", str_precom);
                params.put("current_position", str_curpos);
                params.put("exp_year", str_sexp);
                params.put("salary", str_ssal);
                params.put("speciality", str_spec);

                System.out.println("totall" + params);
                return params;

            }

        };

        // Adding request to request queue
        queue.add(request);
    }

             // loading the location from json
          private void makeJsonObjectRequest_autocompany() {
                System.out.println("method inside");

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

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Activity_Registration.this, android.R.layout.select_dialog_item, list);
                        autoinputpresentcom.setAdapter(adapter);
                        autoinputpresentcom.setThreshold(1);


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
                        Toast.makeText(Activity_Registration.this, "no internet connection..!", Toast.LENGTH_SHORT).show();
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
                        Picasso.with(Activity_Registration.this)
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
                        Toast.makeText(Activity_Registration.this, "Check your internet connection..!", Toast.LENGTH_SHORT).show();

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




package adapter;

/**
 * Created by Parameswari on 6/27/2015.
 */

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import applicatiom.banyan1.job.Activity_Ans;

import applicatiom.banyan1.job.R;
import gobal.AppConfig;
import gobal.GetSetView;


public class ViewAdapter extends ArrayAdapter<GetSetView> {

    private List<GetSetView> _list;
    private final Activity _context;
    private static LayoutInflater _inflater = null;
    GetSetView vidItem;
    /*request volley*/
    private static final String TAG = "";
    private ProgressDialog pDialog;
    public static RequestQueue queue;
    CoordinatorLayout coordinatorLayout;

    private ArrayList<GetSetView> arraylist;

    public ViewAdapter(Activity context, List<GetSetView> lst) {
        super(context, 0, lst);
        this._context = context;
        this._list = lst;
        this.arraylist = new ArrayList<GetSetView>();
        this.arraylist.addAll(_list);
        _inflater = this._context.getLayoutInflater();
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (convertView == null) {
            view = _inflater.inflate(R.layout.activity_viewapp, null);
        }


        vidItem = _list.get(position);
        final TextView txt_job_des_top = (TextView) view.findViewById(R.id.job_txt1);
        txt_job_des_top.setText(vidItem.getposition());
        final TextView txt_company = (TextView) view.findViewById(R.id.company_txt1);
        txt_company.setText(vidItem.getcompany());
        final  TextView txt_time = (TextView) view.findViewById(R.id.time_txt1);
        txt_time.setText(vidItem.getposted_date());
        final TextView txt_expyrs = (TextView) view.findViewById(R.id.years_txt1);
        txt_expyrs.setText(vidItem.getexperience());
        final TextView txt_loc = (TextView) view.findViewById(R.id.location_txt1);
        txt_loc.setText(vidItem.getlocation());
        final TextView txt_desc = (TextView) view.findViewById(R.id.desc_txt1);
        txt_desc.setText(vidItem.getjob_description());
        final TextView txt_skills = (TextView) view.findViewById(R.id.lang_txt1);
        txt_skills.setText(vidItem.getskills());


        return view;
    }

}

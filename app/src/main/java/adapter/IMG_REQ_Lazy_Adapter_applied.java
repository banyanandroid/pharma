package adapter;


import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import com.android.volley.toolbox.ImageLoader;

import applicatiom.banyan1.job.Activity_CreateResume;
import applicatiom.banyan1.job.Activity_Joblist;
import applicatiom.banyan1.job.AppController;
import applicatiom.banyan1.job.FeedImageView;
import applicatiom.banyan1.job.FragmentAppliedJobs;
import applicatiom.banyan1.job.FragmentHome;
import applicatiom.banyan1.job.R;


import android.app.Activity;
import android.content.Context;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;


public class IMG_REQ_Lazy_Adapter_applied extends BaseAdapter {
    private Activity activity;
    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater = null;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    private Object PICK_REQUEST_CODE;
    public static Button b1;


    public IMG_REQ_Lazy_Adapter_applied(Activity a, ArrayList<HashMap<String, String>> d) {
        activity = a;
        data = d;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (convertView == null)
            v = inflater.inflate(R.layout.activity_appliedjobs, null);

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();


        TextView txt_job_des_top = (TextView) v.findViewById(R.id.job_txt1);
        TextView txt_company = (TextView) v.findViewById(R.id.company_txt1);
        TextView txt_time = (TextView) v.findViewById(R.id.time_txt1);
        TextView txt_expyrs = (TextView) v.findViewById(R.id.years_txt1);
        TextView txt_loc = (TextView) v.findViewById(R.id.location_txt1);
        TextView txt_desc = (TextView) v.findViewById(R.id.desc_txt1);
        TextView txt_skills = (TextView) v.findViewById(R.id.lang_txt1);


        HashMap<String, String> result = new HashMap<String, String>();
        result = data.get(position);


        txt_job_des_top.setText(result.get(FragmentAppliedJobs.TAG_WANT));
        System.out.println("bcbc" + FragmentAppliedJobs.TAG_WANT);
        txt_company.setText(result.get(FragmentAppliedJobs.TAG_COMPY));
        System.out.println("bcbc" + FragmentAppliedJobs.TAG_COMPY);
        txt_time.setText(result.get(FragmentAppliedJobs.TAG_POSTDATE));
        System.out.println("bcbc" + FragmentAppliedJobs.TAG_POSTDATE);
        txt_expyrs.setText(result.get(FragmentAppliedJobs.TAG_EXPERIENCE));
        System.out.println("bcbc" + FragmentAppliedJobs.TAG_EXPERIENCE);
        txt_loc.setText(result.get(FragmentAppliedJobs.TAG_LOCATION));
        System.out.println("bcbc" + FragmentAppliedJobs.TAG_LOCATION);
        txt_desc.setText(result.get(FragmentAppliedJobs.TAG_JOBDESC));
        System.out.println("bcbc" + FragmentAppliedJobs.TAG_JOBDESC);
        txt_skills.setText(result.get(FragmentAppliedJobs.TAG_SKILLS));
        System.out.println("bcbc" + FragmentAppliedJobs.TAG_SKILLS);

        return v;

    }


}
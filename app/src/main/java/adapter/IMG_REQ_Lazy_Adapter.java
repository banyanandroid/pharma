package adapter;

/**
 * Created by coolprade on 6/27/2015.
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import applicatiom.banyan1.job.Activity_Joblist;
import applicatiom.banyan1.job.Activity_NewDescp;
import applicatiom.banyan1.job.R;
import gobal.AppConfig;
import gobal.GetSet_job;


public class IMG_REQ_Lazy_Adapter  extends ArrayAdapter<GetSet_job> {

	private List<GetSet_job> _list;
	private final Activity _context;
	private static LayoutInflater _inflater = null;
	GetSet_job vidItem;
	/*request volley*/
	private static final String TAG = "";
	private ProgressDialog pDialog;
	public static RequestQueue queue;
	CoordinatorLayout coordinatorLayout;

	private ArrayList<GetSet_job> arraylist;

	public IMG_REQ_Lazy_Adapter(Activity context, List<GetSet_job> lst) {
		super(context, 0, lst);
		this._context = context;
		this._list = lst;
		this.arraylist = new ArrayList<GetSet_job>();
		this.arraylist.addAll(_list);
		_inflater = this._context.getLayoutInflater();
	}


	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if (convertView == null) {
			view = _inflater.inflate(R.layout.activity_jobtitle, null);
		}


		vidItem = _list.get(position);
		//coordinator
		coordinatorLayout = (CoordinatorLayout) view.findViewById(R.id.coordinatorLayout);

		final TextView txt_job_des_top = (TextView) view.findViewById(R.id.job_txt);
		txt_job_des_top.setText(vidItem.getWANT());
		final TextView txt_company = (TextView) view.findViewById(R.id.company_txt);
		txt_company.setText(vidItem.getCOMPY());
		final TextView txt_time = (TextView) view.findViewById(R.id.time_txt);
		txt_time.setText(vidItem.getPOSTDATE());
		final TextView txt_expyrs = (TextView) view.findViewById(R.id.years_txt);
		txt_expyrs.setText(vidItem.getEXPERIENCE());
		final TextView txt_loc = (TextView) view.findViewById(R.id.location_txt);
		txt_loc.setText(vidItem.getLOCATION());
		final TextView txt_desc = (TextView) view.findViewById(R.id.desc_txt);
		txt_desc.setText(vidItem.getJOBDESC());
		final TextView txt_skills = (TextView) view.findViewById(R.id.lang_txt);
		txt_skills.setText(vidItem.getSKILLS());

		final Button llist = (Button) view.findViewById(R.id.apply_btn);
		llist.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//vidItem = _list.get(position);
				//share.setChecked(true);
				Intent i = new Intent(_context, Activity_Joblist.class);
				i.putExtra("id", arraylist.get(position).getJOBID());
				i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				_context.startActivity(i);

				Log.d("neew_id", "" + arraylist.get(position).getJOBID());

				notifyDataSetChanged();

				// utils.sharetext(_context.getApplicationContext(), vidItem.getLocationName(), vidItem);
			}
		});




		return view;
	}

}

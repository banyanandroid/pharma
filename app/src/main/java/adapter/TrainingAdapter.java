package adapter;

/**
 * Created by Parameswari.K
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

import applicatiom.banyan1.job.Activity_NewDescp;
import applicatiom.banyan1.job.Activity_TrainingDes;
import applicatiom.banyan1.job.R;
import gobal.AppConfig;
import gobal.GetSet_Training;


public class TrainingAdapter extends ArrayAdapter<GetSet_Training> {

    private List<GetSet_Training> _list;
    private final Activity _context;
    private static LayoutInflater _inflater = null;
    GetSet_Training vidItem;
    /*request volley*/
    private static final String TAG = "";
    private ProgressDialog pDialog;
    public static RequestQueue queue;
    CoordinatorLayout coordinatorLayout;

    private ArrayList<GetSet_Training> arraylist;

    public TrainingAdapter(Activity context, List<GetSet_Training> lst) {
        super(context, 0, lst);
        this._context = context;
        this._list = lst;
        this.arraylist = new ArrayList<GetSet_Training>();
        this.arraylist.addAll(_list);
        _inflater = this._context.getLayoutInflater();
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (convertView == null) {
            view = _inflater.inflate(R.layout.activity_train_listitem, null);
        }


        vidItem = _list.get(position);
        //coordinator
        coordinatorLayout = (CoordinatorLayout) view.findViewById(R.id.coordinatorLayout);


        final TextView price = (TextView) view.findViewById(R.id.txt_training_title);

        price.setText(vidItem.gettitle().toUpperCase());
        AppConfig.str_quest = price.getText().toString();

        final LinearLayout lllist = (LinearLayout) view.findViewById(R.id.linear_desc_list);
        lllist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //vidItem = _list.get(position);
                //share.setChecked(true);
                Intent i = new Intent(_context, Activity_TrainingDes.class);
                i.putExtra("id", arraylist.get(position).getid());
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                _context.startActivity(i);

                Log.d("neew_id", "" + arraylist.get(position).getid());

                notifyDataSetChanged();

                // utils.sharetext(_context.getApplicationContext(), vidItem.getLocationName(), vidItem);
            }
        });


        final TextView date = (TextView) view.findViewById(R.id.training_date);
        date.setText(vidItem.getdate());
        final EditText descp = (EditText) view.findViewById(R.id.edt_training_list);
        descp.setText(vidItem.getdescrption());


        return view;
    }

}

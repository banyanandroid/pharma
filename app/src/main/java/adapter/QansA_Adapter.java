package adapter;

/**
 * Created by Parameswari.K
 */

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
import applicatiom.banyan1.job.R;
import gobal.AppConfig;

import gobal.GetSet_QandA;


public class QansA_Adapter extends ArrayAdapter<GetSet_QandA> {

    private List<GetSet_QandA> _list;
    private final Activity _context;
    private static LayoutInflater _inflater = null;
    GetSet_QandA vidItem;
    /*request volley*/
    private static final String TAG = "";
    private ProgressDialog pDialog;
    public static RequestQueue queue;

    private ArrayList<GetSet_QandA> arraylist;

    public QansA_Adapter(Activity context, List<GetSet_QandA> lst) {
        super(context, 0, lst);
        this._context = context;
        this._list = lst;
        this.arraylist = new ArrayList<GetSet_QandA>();
        this.arraylist.addAll(_list);
        _inflater = this._context.getLayoutInflater();
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (convertView == null) {
            view = _inflater.inflate(R.layout.activity_qanda_listitem, null);
        }


        vidItem = _list.get(position);

        final TextView price = (TextView) view.findViewById(R.id.txt_quest);

        price.setText(vidItem.getqustion());
        AppConfig.str_quest = price.getText().toString();


        final LinearLayout llist = (LinearLayout) view.findViewById(R.id.linearLayout);
        llist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //vidItem = _list.get(position);
                //share.setChecked(true);
                Intent i = new Intent(_context, Activity_Ans.class);
                i.putExtra("id", arraylist.get(position).getask_id());
                i.putExtra("question", arraylist.get(position).getqustion());
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                _context.startActivity(i);
//                AppConfig.qus_id=arraylist.get(position).getask_id();
                // AppConfig.str_quest= arraylist.get(position).getqustion();
                Log.d("qus_id", "" + AppConfig.str_quest);
                Log.d("qus_id", "" + arraylist.get(position).getask_id());


                notifyDataSetChanged();

                // utils.sharetext(_context.getApplicationContext(), vidItem.getLocationName(), vidItem);
            }
        });

        final TextView desc = (TextView) view.findViewById(R.id.edt_name_poted_by);
        desc.setText(vidItem.getusername());

        final TextView txt_pty_new = (TextView) view.findViewById(R.id.duration);
        txt_pty_new.setText(vidItem.getdate());

        final TextView txt_pty_new1 = (TextView) view.findViewById(R.id.txt_count);
        txt_pty_new1.setText(vidItem.getcount());


        final TextView txt_pty_new2 = (TextView) view.findViewById(R.id.txt_time);
        txt_pty_new2.setText(vidItem.gettime());


        return view;
    }

}

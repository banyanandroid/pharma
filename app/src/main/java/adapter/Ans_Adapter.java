package adapter;

/**
 * Created by coolprade on 6/27/2015.
 */

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
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


import gobal.GetSet_ans;


public class Ans_Adapter extends ArrayAdapter<GetSet_ans> {

    private List<GetSet_ans> _list;
    private final Activity _context;
    private static LayoutInflater _inflater = null;
    GetSet_ans vidItem;
    /*request volley*/
    private static final String TAG = "";
    private ProgressDialog pDialog;
    public static RequestQueue queue;

    private ArrayList<GetSet_ans> arraylist;

    public Ans_Adapter(Activity context, List<GetSet_ans> lst) {
        super(context, 0, lst);
        this._context = context;
        this._list = lst;
        this.arraylist = new ArrayList<GetSet_ans>();
        this.arraylist.addAll(_list);
        _inflater = this._context.getLayoutInflater();
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (convertView == null) {
            view = _inflater.inflate(R.layout.activity_que_listitem_ans, null);
        }


        vidItem = _list.get(position);

        final TextView price = (TextView) view.findViewById(R.id.txt_ans);

        price.setText(vidItem.getanswer());

        final TextView desc = (TextView) view.findViewById(R.id.txt_send_by);
        desc.setText(vidItem.getusername());

        final TextView txt_pty_new = (TextView) view.findViewById(R.id.txt_date_ans);

        txt_pty_new.setText(vidItem.getdate());

        return view;
    }

}

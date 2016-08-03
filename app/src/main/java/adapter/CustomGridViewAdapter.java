package adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import applicatiom.banyan1.job.Activity_JobOpp_Grid;
import applicatiom.banyan1.job.R;

public class CustomGridViewAdapter extends BaseAdapter {
    String[] result1;
    Context context;
    int[] imageId;
    private static LayoutInflater inflater = null;

    public CustomGridViewAdapter(Activity_JobOpp_Grid mainActivity, int[] prgmImages, String[] prgmNameList1) {
        // TODO Auto-generated constructor stub
        //result=prgmNameList;
        context = mainActivity;
        imageId = prgmImages;
        result1 = prgmNameList1;

        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }


    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return result1.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder {
        TextView tv1;
        ImageView img;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder = new Holder();
        View rowView;

        rowView = inflater.inflate(R.layout.row_grid, null);
//		holder.tv=(TextView) rowView.findViewById(R.id.item_text);
        holder.img = (ImageView) rowView.findViewById(R.id.item_image);

        holder.tv1 = (TextView) rowView.findViewById(R.id.item_text_top);

        holder.tv1.setText(result1[position]);

        holder.img.setImageResource(imageId[position]);







/*
        rowView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub


				Toast.makeText(context, "You Clicked "+result[position], Toast.LENGTH_LONG).show();
			}
		});*/
        return rowView;
    }

}
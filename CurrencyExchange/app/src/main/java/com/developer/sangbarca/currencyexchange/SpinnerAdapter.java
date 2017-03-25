package com.developer.sangbarca.currencyexchange;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by SANG BARCA on 3/24/2017.
 */

public class SpinnerAdapter extends BaseAdapter {
    Context context;
    int layout;
    List<National> listQG;

    public SpinnerAdapter(Context context, int layout, List<National> listQG) {
        this.context = context;
        this.layout = layout;
        this.listQG = listQG;
    }

    @Override
    public int getCount() {
        return listQG.size();
    }

    @Override
    public Object getItem(int position) {
        return listQG.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    private class ViewHolder{
        ImageView imgHinh;
        TextView txtTen;
        TextView txtTyGia;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        ViewHolder holder = new ViewHolder();

        if(rowView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(layout, null);

            holder.imgHinh = (ImageView) rowView.findViewById(R.id.imageViewHinh);
            holder.txtTen = (TextView) rowView.findViewById(R.id.textViewTen);
            holder.txtTyGia = (TextView) rowView.findViewById(R.id.textViewTyGia);

            rowView.setTag(holder);
        }else {
            holder = (ViewHolder) rowView.getTag();
        }
        National national = listQG.get(position);
        holder.imgHinh.setImageResource(national.hinh);
        holder.txtTen.setText(national.ten);

        DecimalFormat dinhDang = new DecimalFormat("###,###");
        holder.txtTyGia.setText("" + dinhDang.format(national.tyGia));
        return rowView;
    }
}

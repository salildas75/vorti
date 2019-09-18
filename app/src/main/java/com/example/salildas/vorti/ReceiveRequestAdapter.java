package com.example.salildas.vorti;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ReceiveRequestAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<ReceiveRequest> receiveRequestArrayList;

    public ReceiveRequestAdapter(Context context, ArrayList<ReceiveRequest> receiveRequestArrayList) {

        this.context = context;
        this.receiveRequestArrayList = receiveRequestArrayList;
    }
    @Override
    public int getViewTypeCount() {
        return getCount();
    }
    @Override
    public int getItemViewType(int position) {

        return position;
    }

    @Override
    public int getCount() {
        return receiveRequestArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return receiveRequestArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ReceiveRequestAdapter.ViewHolder holder;

        if (convertView == null) {
            holder = new ReceiveRequestAdapter.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.receive_request_layout, null, true);

            holder.regularPhone = (TextView) convertView.findViewById(R.id.regularPhone);
            holder.startDate = (TextView) convertView.findViewById(R.id.startDate);
            holder.endDate = (TextView) convertView.findViewById(R.id.endDate);
            holder.totalPrice = (TextView) convertView.findViewById(R.id.totalPrice);
            holder.dayCount = (TextView) convertView.findViewById(R.id.dayCount);
            holder.fullName = (TextView) convertView.findViewById(R.id.fullName);
            holder.requestTime = (TextView) convertView.findViewById(R.id.requestTime);
            holder.btnAccept = (Button) convertView.findViewById(R.id.btnAccept);
            holder.btnCancel = (Button) convertView.findViewById(R.id.btnCancel);

            convertView.setTag(holder);
        }else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (ReceiveRequestAdapter.ViewHolder)convertView.getTag();
        }

        holder.regularPhone.setText(""+receiveRequestArrayList.get(position).getPhone());
        holder.startDate.setText(""+receiveRequestArrayList.get(position).getStartDate());
        holder.endDate.setText(""+receiveRequestArrayList.get(position).getEndDate());
        holder.totalPrice.setText(""+receiveRequestArrayList.get(position).getTotalPrice());
        holder.dayCount.setText(""+receiveRequestArrayList.get(position).getDayCount());
        holder.fullName.setText(""+receiveRequestArrayList.get(position).getRegularFullName());
        holder.requestTime.setText(""+receiveRequestArrayList.get(position).getReqDate());

        return convertView;
    }

    private class ViewHolder {

        protected TextView regularPhone, startDate, endDate, totalPrice, dayCount, fullName, requestTime;
        protected Button btnAccept,btnCancel;

    }
}

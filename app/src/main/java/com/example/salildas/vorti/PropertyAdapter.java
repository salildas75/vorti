package com.example.salildas.vorti;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PropertyAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Property> propertyArrayList;

    public PropertyAdapter(Context context, ArrayList<Property> propertyArrayList) {

        this.context = context;
        this.propertyArrayList = propertyArrayList;
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
        return propertyArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return propertyArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.property_layout, null, true);

            holder.ivImage = (ImageView) convertView.findViewById(R.id.image);
            holder.tvAddress = (TextView) convertView.findViewById(R.id.address);
            holder.tvContact = (TextView) convertView.findViewById(R.id.contactNumber);
            holder.tvRating = (TextView) convertView.findViewById(R.id.rating);
            holder.tvPrice = (TextView) convertView.findViewById(R.id.price);
            holder.tvSeat = (TextView) convertView.findViewById(R.id.seat);
            holder.tvBathroom = (TextView) convertView.findViewById(R.id.bathroom);
            holder.tvDistance = (TextView) convertView.findViewById(R.id.distance);

            convertView.setTag(holder);
        }else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (ViewHolder)convertView.getTag();
        }

        Picasso.get().load(propertyArrayList.get(position).getImage()).into(holder.ivImage);
        holder.tvAddress.setText("Address: "+propertyArrayList.get(position).getStreetNumber()+", "+propertyArrayList.get(position).getStreetName()+", "+propertyArrayList.get(position).getCity()+", "+propertyArrayList.get(position).getState());
        //holder.tvContact.setText("Contact: "+propertyArrayList.get(position).getContact());
        holder.tvRating.setText("Rating: "+propertyArrayList.get(position).getRating());
        holder.tvPrice.setText("Price: "+propertyArrayList.get(position).getPrice());
        holder.tvSeat.setText("Seat: "+propertyArrayList.get(position).getSeats());
        holder.tvBathroom.setText("Bathroom: "+propertyArrayList.get(position).getBathrooms());
        holder.tvDistance.setText("Distance: "+propertyArrayList.get(position).getDistance());

        return convertView;
    }

    private class ViewHolder {

        protected TextView tvAddress, tvContact, tvRating, tvPrice, tvSeat, tvBathroom, tvDistance;
        protected ImageView ivImage;
    }

}

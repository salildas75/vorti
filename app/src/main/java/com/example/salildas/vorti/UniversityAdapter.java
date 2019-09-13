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

public class UniversityAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<University> universityArrayList;

    public UniversityAdapter(Context context, ArrayList<University> universityArrayList) {

        this.context = context;
        this.universityArrayList = universityArrayList;
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
        return universityArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return universityArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        UniversityAdapter.ViewHolder holder;

        if (convertView == null) {
            holder = new UniversityAdapter.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.varsity_layout, null, true);

            holder.ivVarsityImage = (ImageView) convertView.findViewById(R.id.universityImage);
            holder.tvVarsityName = (TextView) convertView.findViewById(R.id.universityName);
            holder.tvVarsityType = (TextView) convertView.findViewById(R.id.universityType);
            holder.tvVarsityDesc = (TextView) convertView.findViewById(R.id.universityDesc);

            convertView.setTag(holder);
        }else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (UniversityAdapter.ViewHolder)convertView.getTag();
        }

        Picasso.get().load(universityArrayList.get(position).getVarsityImage()).into(holder.ivVarsityImage);
        holder.tvVarsityName.setText("Name: "+universityArrayList.get(position).getVarsityName());
        holder.tvVarsityType.setText("Type: "+universityArrayList.get(position).getVarsityType());
        holder.tvVarsityDesc.setText("Description: "+universityArrayList.get(position).getGetVarsityDesc());

        return convertView;
    }

    private class ViewHolder {

        protected TextView tvVarsityName, tvVarsityType, tvVarsityDesc;
        protected ImageView ivVarsityImage;
    }

}

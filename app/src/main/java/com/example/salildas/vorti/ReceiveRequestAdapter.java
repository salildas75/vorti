package com.example.salildas.vorti;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class ReceiveRequestAdapter extends BaseAdapter {

    private static final String KEY_STATUS = "status";
    private static final String KEY_MESSAGE = "message";
    private static final String KEY_REGULAR_PHONE = "regular_phone";
    private static final String KEY_TOTAL_COST = "total_cost";
    private static final String KEY_REGULAR_NANE = "regular_name";

    Constants constants = new Constants();
    public String accept_request_url = constants.baseURL+"member/accept_request.php";
    public String cancel_request_url = constants.baseURL+"member/cancel_request.php";
    public String start_request_url = constants.baseURL+"member/start_request.php";
    public String close_request_url = constants.baseURL+"member/close_request.php";


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
    public View getView(final int position, View convertView, ViewGroup parent) {
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
            holder.requestStatus = (TextView) convertView.findViewById(R.id.requestStatus);
            holder.requestTime = (TextView) convertView.findViewById(R.id.requestTime);

            holder.btnAccept = (Button) convertView.findViewById(R.id.btnAccept);
            holder.btnAccept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {

                    int regularPhone = receiveRequestArrayList.get(position).getPhone();

                    JSONObject request = new JSONObject();
                    try {
                        //Populate the request parameters
                        request.put(KEY_REGULAR_PHONE, regularPhone);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                            (Request.Method.POST, accept_request_url, request, new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        //Check Request

                                        if (response.getInt(KEY_STATUS) == 0) {

                                            Toast.makeText(view.getContext(),
                                                    response.getString(KEY_MESSAGE), Toast.LENGTH_SHORT).show();

                                        } else {
                                            Toast.makeText(view.getContext(),
                                                    response.getString(KEY_MESSAGE), Toast.LENGTH_SHORT).show();

                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }, new Response.ErrorListener() {

                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    //Display error message whenever an error occurs
                                    Toast.makeText(view.getContext(),
                                            error.getMessage(), Toast.LENGTH_SHORT).show();

                                }
                            });

                    // Access the RequestQueue through your singleton class.
                    MySingleton.getInstance(context).addToRequestQueue(jsArrayRequest);

                }
            });


            holder.btnCancel = (Button) convertView.findViewById(R.id.btnCancel);
            holder.btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    int regularPhone = receiveRequestArrayList.get(position).getPhone();

                    JSONObject request = new JSONObject();
                    try {
                        //Populate the request parameters
                        request.put(KEY_REGULAR_PHONE, regularPhone);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                            (Request.Method.POST, cancel_request_url, request, new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        //Check Request

                                        if (response.getInt(KEY_STATUS) == 0) {

                                            Toast.makeText(view.getContext(),
                                                    response.getString(KEY_MESSAGE), Toast.LENGTH_SHORT).show();

                                        } else {
                                            Toast.makeText(view.getContext(),
                                                    response.getString(KEY_MESSAGE), Toast.LENGTH_SHORT).show();

                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }, new Response.ErrorListener() {

                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    //Display error message whenever an error occurs
                                    Toast.makeText(view.getContext(),
                                            error.getMessage(), Toast.LENGTH_SHORT).show();

                                }
                            });

                    // Access the RequestQueue through your singleton class.
                    MySingleton.getInstance(context).addToRequestQueue(jsArrayRequest);
                }
            });


            holder.btnStart = (Button) convertView.findViewById(R.id.btnStart);
            holder.btnStart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {

                    int regularPhone = receiveRequestArrayList.get(position).getPhone();

                    JSONObject request = new JSONObject();
                    try {
                        //Populate the request parameters
                        request.put(KEY_REGULAR_PHONE, regularPhone);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                            (Request.Method.POST, start_request_url, request, new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        //Check Request

                                        if (response.getInt(KEY_STATUS) == 0) {

                                            Toast.makeText(view.getContext(),
                                                    response.getString(KEY_MESSAGE), Toast.LENGTH_SHORT).show();

                                        } else {
                                            Toast.makeText(view.getContext(),
                                                    response.getString(KEY_MESSAGE), Toast.LENGTH_SHORT).show();

                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }, new Response.ErrorListener() {

                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    //Display error message whenever an error occurs
                                    Toast.makeText(view.getContext(),
                                            error.getMessage(), Toast.LENGTH_SHORT).show();

                                }
                            });

                    // Access the RequestQueue through your singleton class.
                    MySingleton.getInstance(context).addToRequestQueue(jsArrayRequest);



                }
            });


            holder.btnClose = (Button) convertView.findViewById(R.id.btnClose);
            holder.btnClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {

                    int regularPhone = receiveRequestArrayList.get(position).getPhone();
                    String reqStatus = receiveRequestArrayList.get(position).getReqStatus();
                    double totalCost = receiveRequestArrayList.get(position).getTotalPrice();
                    String regularName = receiveRequestArrayList.get(position).getRegularFullName();

                    if (reqStatus.equals("started")) {

                    JSONObject request = new JSONObject();
                    try {
                        //Populate the request parameters
                        request.put(KEY_REGULAR_PHONE, regularPhone);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                            (Request.Method.POST, close_request_url, request, new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        //Check Request

                                        if (response.getInt(KEY_STATUS) == 0) {

                                            Toast.makeText(view.getContext(),
                                                    response.getString(KEY_MESSAGE), Toast.LENGTH_SHORT).show();

                                        } else {
                                            Toast.makeText(view.getContext(),
                                                    response.getString(KEY_MESSAGE), Toast.LENGTH_SHORT).show();

                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }, new Response.ErrorListener() {

                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    //Display error message whenever an error occurs
                                    Toast.makeText(view.getContext(),
                                            error.getMessage(), Toast.LENGTH_SHORT).show();

                                }
                            });

                    // Access the RequestQueue through your singleton class.
                    MySingleton.getInstance(context).addToRequestQueue(jsArrayRequest);


                    Intent intent = new Intent(context.getApplicationContext(), PaymentActivity.class);

                    intent.putExtra(KEY_TOTAL_COST,totalCost);
                    intent.putExtra(KEY_REGULAR_PHONE,regularPhone);
                    intent.putExtra(KEY_REGULAR_NANE,regularName);

                    intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                    context.getApplicationContext().startActivity(intent);
                    }else {
                        Toast.makeText(view.getContext(),
                               "Firstly you have to start this", Toast.LENGTH_SHORT).show();
                    }
                }
            });


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
        holder.requestStatus.setText(""+receiveRequestArrayList.get(position).getReqStatus());
        holder.requestTime.setText(""+receiveRequestArrayList.get(position).getReqDate());

        return convertView;
    }

    private class ViewHolder {

        protected TextView regularPhone, startDate, endDate, totalPrice, dayCount, fullName, requestTime,requestStatus;
        protected Button btnAccept, btnCancel, btnStart, btnClose;

    }



}

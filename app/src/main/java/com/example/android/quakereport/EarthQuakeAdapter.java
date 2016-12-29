package com.example.android.quakereport;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by sboo on 2016-12-24.
 */
public class EarthQuakeAdapter extends ArrayAdapter<EarthQuakeInfo> {
    private static final String LOCATION_DELIMETER = "of";

    public EarthQuakeAdapter(Context context, int resource, List<EarthQuakeInfo> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rootView;
        if(convertView != null){
            rootView = convertView;
        }else{
            rootView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        TextView textMagnitude = (TextView)rootView.findViewById(R.id.text_magnitude);
        TextView textLocationOffset = (TextView)rootView.findViewById(R.id.text_location_offset);
        TextView textLocation = (TextView)rootView.findViewById(R.id.text_location);
        TextView textDate = (TextView)rootView.findViewById(R.id.text_date);
        TextView textTime = (TextView)rootView.findViewById(R.id.text_time);

        EarthQuakeInfo earthQuakeInfo = getItem(position);

        GradientDrawable magBackground = (GradientDrawable)textMagnitude.getBackground();
        int backgroundColor = getMagnitudeColor(earthQuakeInfo.getMagnitude());
        magBackground.setColor(ContextCompat.getColor(getContext(), backgroundColor));
        textMagnitude.setText(formatMagnitude(earthQuakeInfo.getMagnitude()));

        String locationOffset = formatLocationOffset(earthQuakeInfo.getLocation());
        String location = formatPrimaryLocation(earthQuakeInfo.getLocation());
        textLocationOffset.setText(locationOffset);
        textLocation.setText(location);

        Date date = new Date(earthQuakeInfo.getDate());
        String dateStr = formatDate(date);
        String timeStr = formatTime(date);
        textDate.setText(dateStr);
        textTime.setText(timeStr);

        return rootView;
    }

    private String formatMagnitude(double mag){
        DecimalFormat format = new DecimalFormat("0.0");
        String result = format.format(mag);
        return result;
    }

    private String formatDate(Date date){
        SimpleDateFormat format = new SimpleDateFormat("LLL dd , yyyy");
        return format.format(date);
    }

    private String formatTime(Date date){
        SimpleDateFormat format = new SimpleDateFormat("h:mm a");
        return format.format(date);
    }

    private String formatLocationOffset(String originStr){
        String result = "";
        if(originStr.contains(LOCATION_DELIMETER)){
            result = originStr.split(LOCATION_DELIMETER)[0] + LOCATION_DELIMETER;
        }else{
            result = getContext().getResources().getString(R.string.near_the);
        }

        return result;
    }

    private String formatPrimaryLocation(String originStr){
        String result = "";
        String[] splitArr = originStr.split(LOCATION_DELIMETER);
        if(originStr.contains(LOCATION_DELIMETER)){
            result = splitArr[1];
        }else{
            result = splitArr[0];
        }

        return result;
    }

    private int getMagnitudeColor(double magnitude){
        int simpleMag = (int)magnitude;
        int backgroundColor;
        switch (simpleMag){
            case 0:
            case 1:
                backgroundColor = R.color.magnitude1;
                break;
            case 2:
                backgroundColor = R.color.magnitude2;
                break;
            case 3:
                backgroundColor = R.color.magnitude3;
                break;
            case 4:
                backgroundColor = R.color.magnitude4;
                break;
            case 5:
                backgroundColor = R.color.magnitude5;
                break;
            case 6:
                backgroundColor = R.color.magnitude6;
                break;
            case 7:
                backgroundColor = R.color.magnitude7;
                break;
            case 8:
                backgroundColor = R.color.magnitude8;
                break;
            case 9:
                backgroundColor = R.color.magnitude9;
                break;
            default:
                backgroundColor = R.color.magnitude10plus;
                break;
        }

        return backgroundColor;
    }

}

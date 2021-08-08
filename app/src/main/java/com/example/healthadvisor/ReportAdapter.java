package com.example.healthadvisor;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import java.util.ArrayList;

public class ReportAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final ArrayList<String> metrics;
    private final ArrayList<String> value;

    public ReportAdapter(@NonNull Activity context, ArrayList<String> metrics,ArrayList<String> value) {
        super(context, R.layout.report_list_item,metrics);
        this.context = context;
        this.metrics = metrics;
        this.value = value;
    }

    public View getView(int position , View view, ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();
        @SuppressLint({"ViewHolder", "InflateParams"}) View rowView = inflater.inflate(R.layout.report_list_item,null,true);

        TextView metricsTextView = rowView.findViewById(R.id.metrics);
        TextView valueTextView = rowView.findViewById(R.id.value);

        metricsTextView.setText(metrics.get(position));
        valueTextView.setText((value.get(position)));

        return rowView;
    }
}

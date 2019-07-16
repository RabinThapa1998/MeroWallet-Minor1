package com.example.merowalletv11;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class StatisticsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView( LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistics, container, false);

        final Button btnStat = (Button) view.findViewById(R.id.stat);
        //int nos=0;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                btnStat.performClick();
            }
        },0);

        btnStat.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity(), StatisticsActivity.class);
                //in.putExtra("some", "some data");
                startActivity(in);
            }
        });
        return view;
    }
}

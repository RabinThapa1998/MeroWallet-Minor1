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

public class ReportsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reports, container, false);
        final Button btnRep = (Button) view.findViewById(R.id.rep);
        //int nos=0;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                btnRep.performClick();
            }
        },0);

        btnRep.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity(), ReportsActivity.class);
                //in.putExtra("some", "some data");
                startActivity(in);
            }
        });
        return view;
    }
}

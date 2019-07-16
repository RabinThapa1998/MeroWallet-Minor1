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
import android.widget.ListView;

public class RecordsFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_records, container, false);
        final Button btnRec = (Button) view.findViewById(R.id.rec);
        //int nos=0;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                btnRec.performClick();
            }
        },0);

        btnRec.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity(), RecordsActivity.class);
                //in.putExtra("some", "some data");
                startActivity(in);
            }
        });
        return view;


    }
}

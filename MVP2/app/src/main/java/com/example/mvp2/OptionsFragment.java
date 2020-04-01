package com.example.mvp2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;


public class OptionsFragment extends Fragment {

    ImageView grad_b, grad_p, grad_y;

    public OptionsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onStart() {
        super.onStart();

        grad_b = (ImageView) getView().findViewById(R.id.grad_b);
        grad_p = (ImageView) getView().findViewById(R.id.grad_p);
        grad_y = (ImageView) getView().findViewById(R.id.grad_y);

        grad_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).setTheme((int)0);
            }
        });

        grad_p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).setTheme((int)1);
            }
        });

        grad_y.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).setTheme((int)2);

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_options, container, false);
    }
}

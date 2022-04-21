package com.example.smartmirror;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

public class ItemFragment extends Fragment {

    ListView itemList;
    ListItem listItem;
    ListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_item, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        itemList = view.findViewById(R.id.itemList);
        listItem = new ListItem();
        adapter = new ListAdapter(getContext());

        itemList.setAdapter(adapter);

        Button button1 = view.findViewById(R.id.btn_top);
        Button button2 = view.findViewById(R.id.btn_bottom);
        Button button3 = view.findViewById(R.id.btn_outer);
        Button button4 = view.findViewById(R.id.btn_etc);


        //////////////////////////////////////////
        //////////////////////////////////////////

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });



    }
}

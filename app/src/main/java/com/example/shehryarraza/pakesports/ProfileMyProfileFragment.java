package com.example.shehryarraza.pakesports;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import static android.app.Activity.RESULT_OK;

public class ProfileMyProfileFragment extends Fragment {
    private Button button;
    private ImageView imageView;
    private static final int SELECT_PICTURE = 2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


       View view = inflater.inflate(R.layout.fragment_myprofile_profile, container, false);

       button = view.findViewById(R.id.edit_profile);

       imageView = view.findViewById(R.id.imageView4);

       button.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent = new Intent(getActivity(), EditProfileActivity.class);
               startActivityForResult(intent, SELECT_PICTURE);
           }
       });
       return view;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            if(requestCode == SELECT_PICTURE) {
                String result = data.getStringExtra("result");



                Uri myURI = Uri.parse(result);
                Picasso.with(getContext()).load(myURI).transform(new CircleTransform()).into(imageView);



                //result is the code of the picked image
                //code to change profile picture goes here
            }
        }

    }


    @Override
    public void onResume() {

        super.onResume();

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("My Profile");


    }
}

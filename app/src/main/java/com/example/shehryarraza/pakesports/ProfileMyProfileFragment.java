package com.example.shehryarraza.pakesports;



import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.icu.text.IDNA;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

public class ProfileMyProfileFragment extends Fragment {
    private Button button;
    private ImageView imageView;
    private TextView tv;
    public String result;
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
       tv = view.findViewById(R.id.user_name);


       return view;

    }


    @Override
    public void onResume() {


        // Always load back the image when fragment is resumed
        Bitmap bitmap = new ImageSaver(getContext()).
                setFileName("myImage.png").
                setDirectoryName("images").
                load();


        SharedPreferences prefs = getContext().getSharedPreferences("myRef", MODE_PRIVATE);
        final String loadedString = prefs.getString("correctname", null);
        if (loadedString != null) {
            tv.setText(loadedString);
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), EditProfileActivity.class).putExtra("loadmystring", loadedString);
                startActivityForResult(intent, SELECT_PICTURE);
            }
        });

        getImageUri getImageUri = new getImageUri();

        Uri uri = getImageUri.getImageUri(getContext(),bitmap);

        Picasso.with(getActivity()).load(uri).transform(new CircleTransform()).into(imageView);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("My Profile");

        super.onResume();

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == SELECT_PICTURE) {
            if(resultCode == RESULT_OK) {
                String strEditText = data.getStringExtra("fullname");
                result = strEditText;

                tv.setText(strEditText);//set string over textview

                SharedPreferences.Editor editor = getContext().getSharedPreferences("myRef", MODE_PRIVATE).edit();
                editor.putString("correctname", strEditText);
                editor.apply();


            }
        }
    }

}

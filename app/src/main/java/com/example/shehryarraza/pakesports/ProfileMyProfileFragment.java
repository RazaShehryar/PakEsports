package com.example.shehryarraza.pakesports;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

public class ProfileMyProfileFragment extends Fragment {
    private Button button;
    private ImageView imageView;
    private static final int SELECT_PICTURE = 2;

    @Override
    public void onCreate(Bundle savedInstanceState) {

       /* Activity act = getActivity();
        if (act instanceof EditProfileActivity) {
            Uri uri = ((EditProfileActivity) act).getImageUri(getContext(), bitmap);
            Picasso.with(getActivity()).load(uri).transform(new CircleTransform()).into(imageView);
            Toast.makeText(act, "HIIII", Toast.LENGTH_SHORT).show();
        }*/
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
                Picasso.with(getActivity()).load(myURI).transform(new CircleTransform()).into(imageView); //show the image



                // Saving the image in internal memory

                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), myURI);
                    new ImageSaver(getActivity()).setFileName("myImage.png").setDirectoryName("images").save(bitmap);

                } catch (IOException e) {
                    Toast.makeText(getContext(), "Error converting to Bitmap", Toast.LENGTH_SHORT).show();
                }


            }
        }

    }


    @Override
    public void onResume() {

        // Always load back the image when fragment is resumed

        Bitmap bitmap = new ImageSaver(getContext()).
                setFileName("myImage.png").
                setDirectoryName("images").
                load();

        getImageUri getImageUri = new getImageUri();

        Uri uri = getImageUri.getImageUri(getContext(),bitmap);

        Picasso.with(getActivity()).load(uri).transform(new CircleTransform()).into(imageView);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("My Profile");

        super.onResume();

    }



}

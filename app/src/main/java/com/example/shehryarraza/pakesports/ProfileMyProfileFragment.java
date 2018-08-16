package com.example.shehryarraza.pakesports;



import android.content.Intent;
import android.graphics.Bitmap;
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

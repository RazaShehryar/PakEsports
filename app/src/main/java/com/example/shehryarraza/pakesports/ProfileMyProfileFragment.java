package com.example.shehryarraza.pakesports;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class ProfileMyProfileFragment extends Fragment {

    private Button button;
    private ImageView imageView;
    public static ProfileMyProfileFragment newInstance() {
        ProfileMyProfileFragment fragment = new ProfileMyProfileFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        View view = inflater.inflate(R.layout.fragment_myprofile_profile,container, false);



        imageView = view.findViewById(R.id.imageView4);

        button = view.findViewById(R.id.edit_profile);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), EditProfileActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
    @Override
    public void onResume() {

        super.onResume();

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("My Profile");

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if( requestCode == 1 ) {
            String strtext = getArguments().getString("userphoto");
            Bitmap bitmap = ((EditProfileActivity)getActivity()).StringToBitMap(strtext);

            imageView.setImageBitmap(bitmap);
        }
    }

}

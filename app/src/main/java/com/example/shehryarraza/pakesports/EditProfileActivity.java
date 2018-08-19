package com.example.shehryarraza.pakesports;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.squareup.picasso.Picasso;
import java.io.File;
import java.io.IOException;
import id.zelory.compressor.Compressor;


public class EditProfileActivity extends AppCompatActivity {


    private EditText Description;
    private EditText Fullname;
    private static ImageButton imageButton;
    private static final int SELECT_PICTURE = 2;
    private Button button;
    private Uri selectedImageURI = null;
    public String send_image;
    public String full_name;
    private TextView textView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        setContentView(R.layout.activity_edit_profile);



        Description = findViewById(R.id.description);
        Fullname = findViewById(R.id.fullname);
        imageButton = findViewById(R.id.imageButton);
        button = findViewById(R.id.button3);
        textView = findViewById(R.id.textView4);
        progressBar = findViewById(R.id.progressBar3);
        Description.append("");




        Bitmap bitmap = new ImageSaver(EditProfileActivity.this).
                setFileName("myImage.png").
                setDirectoryName("images").
                load();


        if (bitmap != null) {
            getImageUri getImageUri = new getImageUri();
            Uri uri = getImageUri.getImageUri(EditProfileActivity.this, bitmap);
            Picasso.with(EditProfileActivity.this).load(uri).transform(new CircleTransform()).into(imageButton);
        }

        else {

        }


        View.OnClickListener l = new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
            }
        };

        imageButton.setOnClickListener(l);
        textView.setOnClickListener(l);



    }

   /* public String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String temp=Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    public Bitmap StringToBitMap(String encodedString){
        try {
            byte [] encodeByte=Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }*/





    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {

                selectedImageURI = data.getData();
                String alpha = selectedImageURI.toString();

                SharedPreferences.Editor editor = getApplicationContext().getSharedPreferences("myRef", MODE_PRIVATE).edit();
                editor.putString("image", alpha);
                editor.apply();

                new AsyncTaskRunnerProfile().execute();

            }

        }
        else{
            imageButton.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }

    }

    class AsyncTaskRunnerProfile extends AsyncTask<Object, Void, Void> {


        @Override
        protected Void doInBackground(Object... params) {
            ConvertImage convertImage = new ConvertImage(EditProfileActivity.this);

            String convertedImageFile = convertImage.getPathFromGooglePhotosUri(selectedImageURI);

            File file = new File(convertedImageFile);
            Bitmap compressedImageBitmap;
            try {
                compressedImageBitmap = new Compressor(getBaseContext()).compressToBitmap(file);
                new ImageSaver(EditProfileActivity.this).setFileName("myImage.png").setDirectoryName("images").save(compressedImageBitmap);

            } catch (IOException e) {
                Toast.makeText(EditProfileActivity.this, "Image size too big to upload", Toast.LENGTH_SHORT).show();
            }


            return null;
        }


        @Override
        protected void onPostExecute (Void myUri){
            // execution of result of Long time consuming operation
            Picasso.with(EditProfileActivity.this).load(selectedImageURI).transform(new CircleTransform()).into(imageButton);
        }

    }


    @Override
    protected void onResume() {
        SharedPreferences prefs = getApplicationContext().getSharedPreferences("myRef", MODE_PRIVATE);
        String loadedString = prefs.getString("image", null);
        if (loadedString != null) {
            Description.setText(loadedString);
        }

        full_name = Fullname.getText().toString();


        String s= getIntent().getStringExtra("loadmystring");
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
        Fullname.setText("");
        Fullname.append(s);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent returnIntent = new Intent();
                Bundle extras = new Bundle();
                full_name = Fullname.getText().toString();

                if (full_name.matches("")) {
                    Toast.makeText(EditProfileActivity.this, "You did not enter a username", Toast.LENGTH_SHORT).show();
                    return;
                }
                extras.putString("fullname",full_name);
                extras.putString("result",send_image);
                returnIntent.putExtras(extras);
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }
        });

        super.onResume();
    }

}
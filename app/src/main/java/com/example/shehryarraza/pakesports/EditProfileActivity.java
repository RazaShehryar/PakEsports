package com.example.shehryarraza.pakesports;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import com.squareup.picasso.Picasso;
import java.io.File;
import java.io.IOException;
import id.zelory.compressor.Compressor;


public class EditProfileActivity extends AppCompatActivity {


    private EditText editText;
    private static ImageButton imageButton;
    private static final int SELECT_PICTURE = 2;
    private Button button;
    private Uri selectedImageURI = null;
    public String send_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        setContentView(R.layout.activity_edit_profile);



        editText = findViewById(R.id.description);
        imageButton = findViewById(R.id.imageButton);
        button = findViewById(R.id.button3);
        editText.append("");


        Bitmap bitmap = new ImageSaver(EditProfileActivity.this).
                setFileName("myImage.png").
                setDirectoryName("images").
                load();

        getImageUri getImageUri = new getImageUri();

        Uri uri = getImageUri.getImageUri(EditProfileActivity.this,bitmap);

        Picasso.with(EditProfileActivity.this).load(uri).transform(new CircleTransform()).into(imageButton);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
            }
        });



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



                Picasso.with(this).load(selectedImageURI).transform(new CircleTransform()).into(imageButton);

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

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
                        send_image = selectedImageURI.toString();
                        Intent returnIntent = new Intent();
                        returnIntent.putExtra("result",send_image);
                        setResult(Activity.RESULT_OK,returnIntent);
                        finish();
                    }
                });

            }

        }

    }

}
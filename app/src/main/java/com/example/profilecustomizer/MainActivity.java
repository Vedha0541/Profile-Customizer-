package com.example.profilecustomizer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private EditText etName;
    private ImageView ivChooseImg;
    private Button btnChooseImg;
    private Button btnSubmit;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        etName = findViewById(R.id.etName);
        ivChooseImg = findViewById(R.id.ivChooseImg);
        btnChooseImg = findViewById(R.id.btnChooseImg);
        btnSubmit = findViewById(R.id.btnSubmit);

        btnChooseImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImageChooser();
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveProfile();
            }
        });
    }
    private void openImageChooser(){
        Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,PICK_IMAGE_REQUEST);
    }
    // Handle the result of the image picker
    @SuppressLint("MissingSuperCall")
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==PICK_IMAGE_REQUEST && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            imageUri=data.getData();
            try
            {
              Bitmap bitmap=MediaStore.Images.Media.getBitmap(getContentResolver(),imageUri);
              ivChooseImg.setImageBitmap(bitmap);
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }

        }
    }
    private void saveProfile(){
        String name=etName.getText().toString().trim();
        if(name.isEmpty()){
            Toast.makeText(this,"Please Enter your name",Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this,"Your Profile Saved: "+name,Toast.LENGTH_LONG).show();
        }

    }
}
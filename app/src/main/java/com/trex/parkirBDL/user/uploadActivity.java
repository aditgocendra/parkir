package com.trex.parkirBDL.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.trex.parkirbandarlampung.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class uploadActivity extends AppCompatActivity {
    private ImageView back_btn;

    private Uri imageUri;



    private String nama_area,alamat,ruangparkir, saveCurrentDate,categoryname,hargajam, saveCurrentTime,nama,name2;

    private Button AddNewProductButton;

    private ImageView InputProductImage;

    private EditText InputNamaArea, InputAlamat,hargaperjam, InputRuangParkir ;

    private static final int GalleryPick = 1;

    private Uri ImageUri;

    private String productRandomKey, downloadImageUrl;

    private StorageReference ProductImagesRef;

    private DatabaseReference ProductsRef;

 //   private ProgressDialog loadingBar;

    FirebaseAuth mAuth;

    FirebaseUser currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        //pid = currentUser.getProviderId();

//        categoryname = getIntent().getExtras().get("kategori").toString();
        ProductImagesRef = FirebaseStorage.getInstance().getReference().child("Gambar Products");
        ProductsRef = FirebaseDatabase.getInstance().getReference().child("Parkir");

        AddNewProductButton = findViewById(R.id.add_new_product);
        InputProductImage = (ImageView) findViewById(R.id.select_product_image);
        InputNamaArea = findViewById(R.id.nama);
        InputAlamat = findViewById(R.id.alamat);
        InputRuangParkir = findViewById(R.id.ruang_lingkup);
        hargaperjam = findViewById(R.id.harga_perjam);

        nama = getIntent().getExtras().get("profilename").toString();
        InputProductImage.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View view)
            {
                OpenGallery();
            }
        });

        AddNewProductButton.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View view)

            {
                ValidateProductData();
            }

        });

    
    }

    private void OpenGallery() {


        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, GalleryPick);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==GalleryPick  &&  resultCode==RESULT_OK  &&  data!=null)
        {
            ImageUri = data.getData();
            InputProductImage.setImageURI(ImageUri);
        }
    }

    private void ValidateProductData() {

        nama_area = InputNamaArea.getText().toString();
        alamat = InputAlamat.getText().toString();
        ruangparkir = InputRuangParkir.getText().toString();

        if (ImageUri == null)
        { Toast.makeText(this, "image is mandatory...", Toast.LENGTH_SHORT).show();

        }

        else if (nama_area.isEmpty())
        {
            Toast.makeText(this, "silahkan isi nama area parkir", Toast.LENGTH_SHORT).show();
        }

        else if (alamat.isEmpty())
        {
            Toast.makeText(this, "silahkan Tulis alamat...", Toast.LENGTH_SHORT).show();
        }
        else if (ruangparkir.isEmpty())
        {
            Toast.makeText(this, "silahkan isi maksimal ruang parkir...", Toast.LENGTH_SHORT).show();
        }
        else
        {
            StoreProductInformation();

        }
    }

    private void StoreProductInformation() {

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        productRandomKey = saveCurrentDate + saveCurrentTime + "parkir" + nama_area;


        final StorageReference filePath = ProductImagesRef.child(ImageUri.getLastPathSegment() + productRandomKey + ".jpg");

        final UploadTask uploadTask = filePath.putFile(ImageUri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e)
            {
                String message = e.toString();
                Toast.makeText(uploadActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
            {
                Toast.makeText(uploadActivity.this, "sukses unggah barang", Toast.LENGTH_SHORT).show();

                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception
                    {
                        if (!task.isSuccessful())
                        {
                            throw task.getException();
                        }

                        downloadImageUrl = filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task)
                    {
                        if (task.isSuccessful())
                        {
                            downloadImageUrl = task.getResult().toString();

                            Toast.makeText(uploadActivity.this, "berhasil ", Toast.LENGTH_SHORT).show();

                            SaveProductInfoToDatabase();
                        }
                    }
                });
            }
        });

    }

    private void SaveProductInfoToDatabase() {

        HashMap<String, Object> productMap = new HashMap<>();
        productMap.put("pid", productRandomKey);
        productMap.put("date", saveCurrentDate);
        productMap.put("time", saveCurrentTime);
        productMap.put("nama_area", nama_area);
        productMap.put("image", downloadImageUrl);
        productMap.put("harga", hargajam);
        productMap.put("profilename", nama);
        productMap.put("ruang_lingkup", ruangparkir);
        productMap.put("alamat", alamat);

        ProductsRef.child(productRandomKey).updateChildren(productMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if (task.isSuccessful())
                        {
                            Intent intent = new Intent(uploadActivity.this, mitraActivity.class);
                            startActivity(intent);


                            Toast.makeText(uploadActivity.this, "sukses ", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {

                            String message = task.getException().toString();
                            Toast.makeText(uploadActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }


}




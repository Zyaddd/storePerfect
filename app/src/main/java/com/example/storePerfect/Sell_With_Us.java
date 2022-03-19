package com.example.storePerfect;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import io.paperdb.Paper;

public class Sell_With_Us extends Activity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private ImageView inputProductImage;
    private Button addProductBtn;
    private TextView textViewShowUploads;
    private EditText inputProductName, inputProductDescription, inputProductPrice, inputProductStock;
    private String categoryName, description, price, Pname, stock, saveCurrentDate, saveCurrentTime;
    private ProgressBar progressBar;

    private String productRandomKey, downloadImageUrl;

    private Uri imageUri;

    private StorageReference productImageRef;
    private DatabaseReference prodcutsRef;

    private StorageTask mUploadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sell_with_us);

        //for the Navigation Menu
        final DrawerLayout drawerLayout = findViewById(R.id.drawerlayout);
        findViewById(R.id.menuIcon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        inputProductImage = findViewById(R.id.imageView_choose_image);
        inputProductName = findViewById(R.id.editText_product_name);
        inputProductPrice = findViewById(R.id.editText_product_price);
        inputProductDescription = findViewById(R.id.editText_product_description);
        inputProductStock = findViewById(R.id.editText_product_stock);

        addProductBtn = findViewById(R.id.button_add_product);
        progressBar = findViewById(R.id.progressBar_upload);

        productImageRef = FirebaseStorage.getInstance().getReference().child("Product Images");
        prodcutsRef = FirebaseDatabase.getInstance().getReference().child("Products");

        inputProductImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });


        addProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mUploadTask != null && mUploadTask.isInProgress()) {
                    Toast.makeText(getApplicationContext(), "Upload in Progress", Toast.LENGTH_SHORT).show();
                } else {
//                    StoreProductInformation();
                    validateProductData();
                }
            }
        });


    }//onCreate


    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            imageUri = data.getData();
//          with no independencies
            inputProductImage.setImageURI(imageUri);

            // Picasso.get().load(imageUri).into(addImage);
        }
    }

    //get file extension from the image
//    private String getFileExtension(Uri uri) {
//        ContentResolver cR = getContentResolver();
//        MimeTypeMap mime = MimeTypeMap.getSingleton();
//        return mime.getExtensionFromMimeType(cR.getType(uri));
//    }


    public void validateProductData() {
        description = inputProductDescription.getText().toString();
        price = inputProductPrice.getText().toString();
        Pname = inputProductName.getText().toString();
        stock = inputProductStock.getText().toString();

        if (imageUri == null) {
            Toast.makeText(this, "Product Image is mandatory", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(Pname)) {
            inputProductName.setError("Product name is required.");
        } else if (TextUtils.isEmpty(price)) {
            inputProductPrice.setError("Product price is required.");
        } else if (TextUtils.isEmpty(stock)) {
            inputProductStock.setError("Product stock size is required.");
        } else if (TextUtils.isEmpty(description)) {
            inputProductDescription.setError("Product description size is required.");
        } else {
            StoreProductInformation();
        }

    }

    private void StoreProductInformation() {

        progressBar.setVisibility(View.VISIBLE);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        description = inputProductDescription.getText().toString();
        price = inputProductPrice.getText().toString();
        Pname = inputProductName.getText().toString();
        stock = inputProductStock.getText().toString();

        productRandomKey = saveCurrentDate + saveCurrentTime;

        final StorageReference filePath = productImageRef.child(imageUri.getLastPathSegment() + productRandomKey + ".jpg");

        final UploadTask uploadTask = filePath.putFile(imageUri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String message = e.toString();
                Toast.makeText(Sell_With_Us.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(getApplicationContext(), "Product Uploaded Successfully!", Toast.LENGTH_SHORT).show();
                Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }
                        downloadImageUrl = filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            downloadImageUrl = task.getResult().toString();
                            Toast.makeText(Sell_With_Us.this, "Product image Url uploaded successfully!", Toast.LENGTH_SHORT).show();
                            SaveProductInfoToDatabase();
                        }
                    }
                });
            }
        });
    }

    private void SaveProductInfoToDatabase() {
        HashMap<String, Object> productMap = new HashMap<>();
        productMap.put("pname", Pname);
        productMap.put("image", downloadImageUrl);
        productMap.put("price", price);
        productMap.put("description", description);
        productMap.put("stock", stock);
        productMap.put("pid", productRandomKey);
        productMap.put("date", saveCurrentDate);
        productMap.put("time", saveCurrentTime);
        productMap.put("category", categoryName);

        prodcutsRef.child(productRandomKey).updateChildren(productMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            startActivity(new Intent(getApplicationContext(), My_Product_List.class));
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(Sell_With_Us.this, "Product uploaded successfully!", Toast.LENGTH_SHORT).show();
                        } else {
                            progressBar.setVisibility(View.INVISIBLE);
                            String message = task.getException().toString();
                            Toast.makeText(Sell_With_Us.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {

            case R.id.menuLogin:
                startActivity(new Intent(getApplicationContext(), Login_Activity.class));
                break;

            case R.id.menuHome:
                startActivity(new Intent(getApplicationContext(), Store_Activity.class));
                break;

            case R.id.menuSellWithUs:
                startActivity(new Intent(getApplicationContext(), Sell_With_Us.class));
                break;
            case R.id.menuLogout:
                Paper.book().destroy();
                startActivity(new Intent(getApplicationContext(), Store_Activity.class));
                break;
        }
        return true;
    }

}

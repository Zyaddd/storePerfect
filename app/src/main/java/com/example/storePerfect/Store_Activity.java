package com.example.storePerfect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;

import io.paperdb.Paper;

public class Store_Activity extends AppCompatActivity {

    private ProgressDialog loadingBar;

    android.widget.HorizontalScrollView brandScrollFirst;
    android.widget.HorizontalScrollView brandScrollSecond;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_activity);

        Paper.init(this);

        loadingBar = new ProgressDialog(this);

        ImageView cartIcon = (ImageView) findViewById(R.id.cartIcon);
        ImageView menuIcon = (ImageView) findViewById(R.id.menuIcon);

        //open Cart_Activity
        cartIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Cart_Activity.class));
            }
        });

        //open login_Activity
        menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Login_Activity.class));
            }
        });

        brandScrollFirst = findViewById(R.id.brandScrollFirst);
        brandScrollSecond = findViewById(R.id.brandScrollSecond);

        mHandler.sendMessageDelayed(new Message(), 1000);


        //for the Navigation Menu
        final DrawerLayout drawerLayout = findViewById(R.id.drawerlayout);
        findViewById(R.id.menuIcon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

//        String userPhoneKey = Paper.book().read(Prevalent.userPhoneKey);
//        String userPasswordKey = Paper.book().read(Prevalent.userPasswordKey);

//        if (userPhoneKey != "" && userPasswordKey != "") {
//            if (!TextUtils.isEmpty(userPhoneKey) && !TextUtils.isEmpty(userPasswordKey)) {
//                AllowAccess(userPhoneKey, userPasswordKey);
//                loadingBar.setTitle("Already Logged in");
//                loadingBar.setMessage("Please wait...");
//                loadingBar.setCanceledOnTouchOutside(false);
//                loadingBar.show();
//
//            }
//        }
    }

//    private void AllowAccess(final String phone, final String password) {
//
//        final DatabaseReference rootRef;
//        rootRef = FirebaseDatabase.getInstance().getReference();
//
//        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if (dataSnapshot.child("Users").child(phone).exists()) {
//                    Users usersData = dataSnapshot.child("Users").child(phone).getValue(Users.class);
//
//                    if (usersData.getPhone().equals(phone)) {
//                        if (usersData.getPassword().equals(password)) {
//                            Toast.makeText(getApplicationContext(), "You already logged in", Toast.LENGTH_SHORT).show();
//                            loadingBar.dismiss();
//                            startActivity(new Intent(getApplicationContext(), My_Product_List.class));
//                        } else {
//                            loadingBar.dismiss();
//                            Toast.makeText(getApplicationContext(), "Password is Incorrect", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                } else {
//                    loadingBar.dismiss();
//                    Toast.makeText(getApplicationContext(), "Account with this " + phone + " number do not exits", Toast.LENGTH_SHORT).show();
//                }
//
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }


    //linked to adidas
    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), Login_Activity.class));
        finish();
    }


    public void goToProductList(View view) {
        startActivity(new Intent(getApplicationContext(), ProductsMenu_Activity.class));
    }

    //Auto Scroll the HorizontalScrollView SLOWLY
    Handler mHandler = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {

            brandScrollFirst.smoothScrollBy(5, 0);
            brandScrollSecond.smoothScrollBy(5, 0);
            mHandler.sendMessageDelayed(new Message(), 10);

            return false;
        }
    });


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

            case R.id.menuProfile:
                startActivity(new Intent(getApplicationContext(), My_Product_List.class));
                break;

            case R.id.menuLogout:
                Paper.book().destroy();
                startActivity(new Intent(getApplicationContext(), My_Product_List.class));

                break;

            case R.id.menuLocation:
                Paper.book().destroy();
                startActivity(new Intent(getApplicationContext(), Address.class));
                break;

            case R.id.menuPayment:
                Paper.book().destroy();
                startActivity(new Intent(getApplicationContext(), Payment_Activity.class));
                break;

        }
        return true;
    }


}


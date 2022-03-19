package com.example.storePerfect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import io.paperdb.Paper;

public class ProductsMenu_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.products_menu_activity);

        //for the Navigation Menu
        final DrawerLayout drawerLayout = findViewById(R.id.drawerlayout);
        findViewById(R.id.menuIcon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
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

            case R.id.menuProfile:
                startActivity(new Intent(getApplicationContext(), My_Product_List.class));
                break;

            case R.id.menuLogout:
                Paper.book().destroy();
                startActivity(new Intent(getApplicationContext(), Login_Activity.class));

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

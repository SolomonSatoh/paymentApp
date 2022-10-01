package com.example.paymentapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.Iterator;
import java.util.Set;

public class ViewCart extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cart);

        Cart cart = MainActivity.p_cart;

        LinearLayout cartLayout = (LinearLayout) findViewById(R.id.cart);

        Set<Product> products = cart.getProducts();

        Iterator iterator = products.iterator();
        while(iterator.hasNext())
        {
            Product product = (Product) iterator.next();
            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            TextView name = new TextView(this);
            TextView quantity = new TextView(this);

            name.setText(product.getP_name());
            quantity.setText(Integer.toString(cart.getQuantity(product)));

            name.setTextSize(20);
            quantity.setTextSize(20);

            linearLayout.addView(name);
            linearLayout.addView(quantity);

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                    , 150, Gravity.CENTER);
            layoutParams.setMargins(20, 30,20, 30);
            linearLayout.setLayoutParams(layoutParams);

            name.setLayoutParams(new TableLayout.LayoutParams(0, ActionBar.LayoutParams.WRAP_CONTENT, 1));
            quantity.setLayoutParams(new TableLayout.LayoutParams(0, ActionBar.LayoutParams.WRAP_CONTENT, 1));

            name.setGravity(Gravity.CENTER);
            quantity.setGravity(Gravity.CENTER);

            cartLayout.addView(linearLayout);

        }

    }
}
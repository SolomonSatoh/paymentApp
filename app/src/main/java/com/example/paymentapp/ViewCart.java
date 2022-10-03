package com.example.paymentapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Set;

public class ViewCart extends AppCompatActivity {

    TextView p_response;
    Cart cart = MainActivity.p_cart;
    PayPalConfiguration n_config = MainActivity.p_configuration;
    int new_Code = MainActivity.p_paypalRequestCode;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cart);

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

    public void resetCard(View view)
    {
        p_response.setText("Total value = 0 Mwk");
        cart.empty();
    }

    public void pay(View view)
    {
        PayPalPayment payment = new PayPalPayment(new BigDecimal(cart.getValue()), "USD", "Cart Pay",
                PayPalPayment.PAYMENT_INTENT_SALE);

        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, n_config);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);
        startActivityForResult(intent, new_Code);
    }
}
package com.example.paymentapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalService;

import static com.paypal.android.sdk.payments.PayPalConfiguration.ENVIRONMENT_SANDBOX;

public class MainActivity extends AppCompatActivity
{
    TextView p_response;
    Cart p_cart;

    PayPalConfiguration p_configuration;
    String p_paypalClientId = "AWQXPmu-G34y5gxyflWA9AwBWaEjq5M_NM-d4mh0KC6zvwUFCqQvhmJ6jAww8WxpxC0XsPZ6xNz4WzP_";
    Intent P_service;
    int p_paypalRequestCode = 999;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        p_response = (TextView) findViewById(R.id.response);

        LinearLayout list = (LinearLayout) findViewById(R.id.list);

        p_configuration = new PayPalConfiguration()
                .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
                .clientId(p_paypalClientId);

        P_service = new Intent(this, PayPalService.class);
        P_service.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, p_configuration);
        startService(P_service);

        p_cart = new Cart();

        Product products[] =
                {
                        new Product("Mango", 30.99),
                        new Product("Tomato", 20.99),
                        new Product("Chinangwa", 23.99),
                        new Product("Mbatata", 40.99),
                        new Product("Nthochi", 50.99),
                        new Product("Mawungu", 15.99),

                };

        for (int i = 0; i < products.length; i++)
        {
            Button button = new Button(this);
            button.setText(products[i].getP_name() + " --- " + products[i].getP_value() + " MMk");
            button.setTag(products[i]);

            // the display
            button.setTextSize(20);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    150, Gravity.CENTER);
            layoutParams.setMargins(20, 20, 20, 20);
            button.setLayoutParams(layoutParams);

            button.setOnClickListener(new View.OnClickListener()
            {

                public void onClick(View view)
                {
                    Button button1 = (Button) view;
                    Product product = (Product) button1.getTag();

                    p_cart.addToCart(product);
                    p_response.setText("Total cart value = " + String.format("%.2f", p_cart.getValue()) + " MWK");
                }
            });
            list.addView(button);
        }

    }
}
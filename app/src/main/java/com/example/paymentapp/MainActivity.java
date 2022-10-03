 package com.example.paymentapp;

 import android.app.Activity;
 import android.content.Intent;
 import android.media.audiofx.DynamicsProcessing;
 import android.os.Build;
 import android.os.Bundle;
 import android.view.Gravity;
 import android.view.View;
 import android.view.ViewGroup;
 import android.widget.Button;
 import android.widget.LinearLayout;
 import android.widget.TextView;

 import androidx.annotation.RequiresApi;
 import androidx.appcompat.app.AppCompatActivity;

 import com.paypal.android.sdk.payments.PayPalConfiguration;
 import com.paypal.android.sdk.payments.PayPalPayment;
 import com.paypal.android.sdk.payments.PayPalService;
 import com.paypal.android.sdk.payments.PaymentActivity;
 import com.paypal.android.sdk.payments.PaymentConfirmation;
 import com.paypal.checkout.PayPalCheckout;
 import com.paypal.checkout.config.CheckoutConfig;
 import com.paypal.checkout.config.Environment;
 import com.paypal.checkout.createorder.CurrencyCode;
 import com.paypal.checkout.createorder.UserAction;

 import java.math.BigDecimal;

public class MainActivity extends AppCompatActivity
{
    TextView p_response;
    static Cart p_cart;

    static  PayPalConfiguration p_configuration;
    String p_paypalClientId ="AWQXPmu-G34y5gxyflWA9AwBWaEjq5M_NM-d4mh0KC6zvwUFCqQvhmJ6jAww8WxpxC0XsPZ6xNz4WzP_";
    Intent P_service;
    static int p_paypalRequestCode = 333;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        p_response = (TextView) findViewById(R.id.response);

        LinearLayout list = (LinearLayout) findViewById(R.id.list);

        /*PayPalCheckout.setConfig(new CheckoutConfig(
                getApplication(),
                p_paypalClientId,
                Environment.SANDBOX,
                "com.example.paymentapp" + "://paypalpay",
                CurrencyCode.USD,
                UserAction.PAY_NOW
        ));*/

        //configuring the paypal sdk for this application
        p_configuration = new PayPalConfiguration()
                .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
                .clientId(p_paypalClientId);

        P_service = new Intent(this, PayPalService.class);
        P_service.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, p_configuration);
        startService(P_service);

        p_cart = new Cart();

        // static items on the landing activity/page
        Product products[] =
                {
                        new Product("Mango", 30.99),
                        new Product("Tomato", 20.99),
                        new Product("Chinangwa", 23.99),
                        new Product("Mbatata", 40.99),


                };

        //listing the items on the main page and adding an onclick to each and every item
        for (int i = 0; i < products.length; i++)
        {
            Button button = new Button(this);
            button.setText(products[i].getP_name() + " --- " + products[i].getP_value() + " Mwk");
            button.setTag(products[i]);

            // the display
            button.setTextSize(20);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    150, Gravity.CENTER);
            layoutParams.setMargins(20, 20, 20, 20);
            button.setLayoutParams(layoutParams);

            //allow users to click on the listed items
            button.setOnClickListener(new View.OnClickListener()
            {
                public void onClick(View view)
                {
                    Button button1 = (Button) view;
                    Product product = (Product) button1.getTag();
                    p_cart.addToCart(product);
                    p_response.setText("Total value = " + String.format("%.2f", p_cart.getValue()) + " Mwk");
                }
            });
            list.addView(button);
        }
    }

    /*public void pay(View view)
    {
        PayPalPayment payment = new PayPalPayment(new BigDecimal(p_cart.getValue()), "USD", "Cart Pay",
                PayPalPayment.PAYMENT_INTENT_SALE);

        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, p_configuration);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);
        startActivityForResult(intent, p_paypalRequestCode);
    }*/
    
   //Allow user to view selected items via the cart  
    public void viewCart(View view)
    {
        Intent intent = new Intent (this, ViewCart.class);
        p_cart = p_cart;
        startActivity(intent);
    }
    
    //allow users to clear selected items
    public void resetCard(View view)
    {
        p_response.setText("Total value = 0 Mwk");
        p_cart.empty();
    }

    //display the paypal paymment page if the ammou nt is greater than zero else the the page does not show
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == p_paypalRequestCode)
        {
            if(resultCode == Activity.RESULT_OK)
            {
                PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);

                if(confirm != null)
                {
                    String state = confirm.getProofOfPayment().getState();
                    if(state.equals("approved"))
                        p_response.setText("Payment approved");
                    else
                        p_response.setText("Error in the Payment");
                }
                else
                    p_response.setText("The confirmation is null");
            }
        }
    }

}
package com.example.paymentapp;

public class Product
{
    String p_name;
    double p_value;

    Product (String name, double value)
    {
        p_name = name;
        p_value = value;
    }

    public String getP_name() {
        return p_name;
    }

    public double getP_value() {
        return p_value;
    }


}

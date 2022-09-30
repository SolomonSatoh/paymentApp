package com.example.paymentapp;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class Cart
{
    Map<Product, Integer> p_cart;
    double p_value;

    Cart()
    {
      p_cart = new LinkedHashMap<>();
    }

    void addToCart(Product product)
    {
       if (p_cart.containsKey(product))
           p_cart.put(product, p_cart.get(product) + 1);
       else
           p_cart.put(product, 1);
       p_value += product.getP_value();
    }

    int getQuantity(Product product)
    {
        return p_cart.get(product);
    }

    Set getProducts()
    {
        return p_cart.keySet();
    }

    void empty()
    {
        p_cart.clear();
        p_value = 0;
    }

    double getValue()
    {
        return p_value;
    }

    int getSize()
    {
        return  p_cart.size();
    }

}

package com.hotel.utils;

import java.text.DecimalFormat;

public class PriceUtils {
    
    
    public static String formatPrice(double price) {
        DecimalFormat formatter = new DecimalFormat("#,###.00");
        return formatter.format(price) + " KZT";
    }

    
    public static double applyDiscount(double price, double discountPercentage) {
        if (discountPercentage <= 0) return price;
        return price - (price * (discountPercentage / 100));
    }
}
# JustJava
This is a small App that can be used to order coffee. It is very basic, you can order only one type of coffee which costs 5 euros per cup.
It implements a quantity picker and an order button that calculates the total price of the order.
This was made for the Google Developer Challenge 2017-2018 as an assignment to practice with Java.

This is what the UI looks like on my device (Lenovo tablet Lollipop API22)

![Udacity JustJava App Portrait](https://github.com/elisavetTriant/JustJava/blob/master/screenshots/Screenshot_JustJava_portrait.png  "Udacity JustJava App Portrait")

And here is what the layout xml code looks like (file app/src/main/res/layout/activity_main.xml, or https://github.com/elisavetTriant/JustJava/blob/master/app/src/main/res/layout/activity_main.xml):
```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:layout_margin="16dp"
    tools:context="com.example.android.justjava.MainActivity">

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Quantity"
        android:textAllCaps="true"
        android:textSize="16sp"
        android:layout_marginBottom="16dp"

        />
    <LinearLayout
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:orientation="horizontal">
        
        <Button
            android:id="@+id/quantity_decrement_button"
            android:layout_width="48dp"
            android:layout_height="48dp"
            android:text="-"
            android:onClick="decrement"
            />

        <TextView
            android:id="@+id/quantity_text_view"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="2"
            android:paddingLeft="8dp"
            android:paddingRight="8dp"
            android:textColor="@android:color/black"
            />

        <Button
            android:id="@+id/quantity_increment_button"
            android:layout_width="48dp"
            android:layout_height="48dp"
            android:text="+"
            android:onClick="increment"
            />

    </LinearLayout>
    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Price"
        android:textAllCaps="true"
        android:textSize="16sp"
        android:layout_marginTop="16dp"
        android:layout_marginBottom="16dp"

        />
    <TextView
        android:id="@+id/price_text_view"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="$10"
        android:textColor="@android:color/black"
        />
    <Button
        android:id="@+id/order_button"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Order"
        android:padding="16dp"
        android:layout_marginTop="16dp"
        android:onClick="submitOrder"
        />

</LinearLayout>
```
The java code looks like this (app/src/main/java/com/example/android/justjava) or https://github.com/elisavetTriant/JustJava/blob/master/app/src/main/java/com/example/android/justjava/MainActivity.java
```java
/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.android.justjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 2;
    int price = 5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        int totalPrice = quantity * price;
        //String priceMessage1 = "Amount due " + NumberFormat.getCurrencyInstance().format(totalPrice);
        //String priceMessage2 = "That would be " + NumberFormat.getCurrencyInstance().format(totalPrice) + " please.";
        // String priceMessage3 = "You owe " + totalPrice + " bucks, dude!";
        // String priceMessage4 = totalPrice + " dollars for " + quantity + " cups of coffee. Pay up.";
        String priceMessage5 = "Total: " + NumberFormat.getCurrencyInstance().format(totalPrice);
        priceMessage5 = priceMessage5 +"\nThank you!";
        displayMessage(priceMessage5);
    }

    /**
     * This method is called when the quantity increment button is clicked.
     */
    public void increment(View view) {
        quantity = quantity + 1 ;
        display(quantity);
    }

    /**
     * This method is called when the quantity decrement button is clicked.
     */
    public void decrement(View view) {

         // You can order one or more coffees!

        if (quantity > 1) {
            quantity = quantity - 1;
            display(quantity);
        }
    }
    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given price on the screen.
     */
    private void displayPrice(int number) {
        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
        priceTextView.setText(message);
    }
}
```

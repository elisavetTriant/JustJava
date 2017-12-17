# JustJava
This is a small App that can be used to order coffee. It is very basic, you can order only one type of coffee which costs 5 euros per cup.
It implements a quantity picker and an order button that calculates the total price of the order.
This was made for the Google Developer Challenge 2017-2018 as an assignment to practice with Java.

>
>Branch notes:
>- The App's TextViews text is now dynamically populated through Java code, overriding the hardcoded value in the layout xml.
  This is  also used in the initialization of the text values according to the global variable's initial value
>- The guantity picker can choose one or more coffees. No sense in ordering 0 or negative values coffee cups! 
  A Toast is also implemented in the event of pressing the minus button and the quantity is already 1 cups of coffee
>- Saves global vars between activity states, using onSaveInstanceState and onRestoreInstanceState
>- The layout xml is not touched in this branch. This is intentional. I didn't want the UI to be altered by any means except Java. 
>- This branch is dedicated to all my fellow Udacians, mentors and students alike, who took and will take the time to make helpful suggestions to make this App helpful to others.
>

This is what the UI looks like on my device (Lenovo tablet Lollipop API22)

![Udacity JustJava App Portrait](https://github.com/elisavetTriant/JustJava/blob/master/screenshots/Screenshot_JustJava_portrait.png.png  "Udacity JustJava App Portrait")

And here is what the layout xml code looks like (file app/src/main/res/layout/activity_main.xml, or https://github.com/elisavetTriant/JustJava/blob/Suggestions/app/src/main/res/layout/activity_main.xml

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
The java code looks like this (app/src/main/java/com/example/android/justjava) or https://github.com/elisavetTriant/JustJava/blob/Suggestions/app/src/main/java/com/example/android/justjava/MainActivity.java
```java
/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.android.justjava;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    //Declare and Initialise Global Vars
    int quantity = 1;
    int price = 5;
    String priceMessage = "";
    //https://www.android-examples.com/change-textview-text-programmatically-in-android/
    TextView quantityTextView;
    TextView priceTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //https://www.android-examples.com/change-textview-text-programmatically-in-android/
        quantityTextView = findViewById(R.id.quantity_text_view);
        priceTextView = findViewById(R.id.price_text_view);

        //https://www.android-examples.com/change-textview-text-programmatically-in-android/
        updateTextViewsTextThroughJava();
    }

    // https://stackoverflow.com/questions/151777/saving-android-activity-state-using-save-instance-state
    //prevent the application from restarting when changing orientation. Saving global vars between activity states.
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        // Save UI state changes to the savedInstanceState.
        // This bundle will be passed to onCreate if the process is
        // killed and restarted.
        savedInstanceState.putInt("quantity", quantity);
        savedInstanceState.putString("priceMessage", priceMessage );
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // Restore UI state from the savedInstanceState.
        // This bundle has also been passed to onCreate.
        quantity = savedInstanceState.getInt("quantity");
        priceMessage = savedInstanceState.getString("priceMessage");

        //https://www.android-examples.com/change-textview-text-programmatically-in-android/
        updateTextViewsTextThroughJava();
    }

    /**
     * This method is called when the quantity increment button is clicked.
     */
    public void increment(View view) {
        quantity = quantity + 1 ;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the quantity decrement button is clicked.
     */
    public void decrement(View view) {
         // You can order one or more coffees!
        if (quantity > 1) {
            quantity = quantity - 1;
            displayQuantity(quantity);
        } else {
            displayToastMessage("You can't order less than one coffee! Thank you!");
        }
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        preparePriceMessage();
        displayOrderMessage(priceMessage);
    }

    /**
     * This method displays the given price on the screen. DEPRECATED
    *
    private void displayPrice(int number) {
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    }
     *
     */


    /**
     * This method displays the given quantity value on the screen.
     */
    public void displayQuantity(int number) {
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given text on the screen.
     */
    public void displayOrderMessage(String message) {
        priceTextView.setText(message);
    }

    /**
     * This method when called prepares the global priceMessage variable
     * I decided to move this task to a separate public function because
     * a) The function is called in more than one places
     * b) it gives more versatility on the message to be displayed
     */
    public void preparePriceMessage() {
        int totalPrice = quantity * price;
        priceMessage = "Amount due " + NumberFormat.getCurrencyInstance().format(totalPrice) + "\nThank you!";
        //String priceMessage2 = "That would be " + NumberFormat.getCurrencyInstance().format(totalPrice) + " please.";
        //String priceMessage3 = "You owe " + totalPrice + " bucks, dude!";
        //String priceMessage4 = totalPrice + " dollars for " + quantity + " cups of coffee. Pay up.";
        //priceMessage = "Total: " + NumberFormat.getCurrencyInstance().format(totalPrice);
        //priceMessage = priceMessage + "\nThank you!";
    }

    /**
     * This method, when called, updates all the TextViews dynamically with the current global vars values
     */
    public void updateTextViewsTextThroughJava() {
        displayQuantity(quantity);
        preparePriceMessage();
        displayOrderMessage(priceMessage);
    }
    /**
     * This method displays a Toast Message when called.
     */
    //https://developer.android.com/guide/topics/ui/notifiers/toasts.html
    private void displayToastMessage(String message){
        Context context = getApplicationContext();
        String text = message;
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

}
```

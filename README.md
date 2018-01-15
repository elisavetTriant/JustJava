# JustJava
This is a small App that can be used to order coffee. It is very basic, you can order only one type of coffee which costs 5 euros per cup.
It implements a quantity picker and an order button that calculates the total price of the order. The user then is prompted to send the order via email.

A Greek translation of the App is provided.

This was made for the Google Developer Challenge 2017-2018 as an assignment to practice with Java.

This is how the App behaves:(short video on Youtube)

[![Just Java App](http://img.youtube.com/vi/FYLJY3dYU6w/0.jpg)](https://youtu.be/FYLJY3dYU6w "Just Java App")

This is what the UI looks like on my device (Lenovo tablet Lollipop API22)

Portrait
![Udacity JustJava App Portrait](https://github.com/elisavetTriant/JustJava/blob/master/screenshots/Screenshot_JustJava_revised_english_portrait.png.png "Udacity JustJava App Portrait")

Landscape
![Udacity JustJava App Landscape](https://github.com/elisavetTriant/JustJava/blob/master/screenshots/Screenshot_JustJava_revised_landscape.png.png "Udacity JustJava App Landscape")

And here is what the layout xml code looks like (file app/src/main/res/layout/activity_main.xml, or https://github.com/elisavetTriant/JustJava/blob/master/app/src/main/res/layout/activity_main.xml):
```xml
<?xml version="1.0" encoding="utf-8"?>
<ScrollView xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:fillViewport="true"
    tools:context="com.example.android.justjava.MainActivity">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_margin="@dimen/elements_margin"
        android:orientation="vertical">

        <EditText
            android:id="@+id/name_edit_text"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginBottom="@dimen/elements_margin"
            android:hint="@string/name"
            android:textSize="@dimen/elements_text_size" />

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginBottom="@dimen/elements_margin"
            android:text="@string/toppings"
            android:textAllCaps="true"
            android:textSize="@dimen/elements_text_size" />

        <CheckBox
            android:id="@+id/whipped_cream_checkbox"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginBottom="@dimen/elements_margin"
            android:paddingLeft="@dimen/checkboxes_padding"
            android:text="@string/whipped_cream"
            android:textSize="@dimen/elements_text_size" />

        <CheckBox
            android:id="@+id/chocolate_checkbox"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginBottom="@dimen/elements_margin"
            android:paddingLeft="@dimen/checkboxes_padding"
            android:text="@string/chocolate"
            android:textSize="@dimen/elements_text_size" />

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginBottom="@dimen/elements_margin"
            android:text="@string/quantity"
            android:textAllCaps="true"
            android:textSize="@dimen/elements_text_size" />

        <LinearLayout
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:orientation="horizontal">

            <Button
                android:id="@+id/quantity_decrement_button"
                android:layout_width="@dimen/quantity_buttons_size"
                android:layout_height="@dimen/quantity_buttons_size"
                android:onClick="decrement"
                android:text="@string/minus"
                style="@style/buttonsStyle"/>

            <TextView
                android:id="@+id/quantity_text_view"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:paddingLeft="@dimen/quantity_buttons_padding"
                android:paddingRight="@dimen/quantity_buttons_padding"
                android:textColor="@android:color/black" />

            <Button
                android:id="@+id/quantity_increment_button"
                android:layout_width="@dimen/quantity_buttons_size"
                android:layout_height="@dimen/quantity_buttons_size"
                android:onClick="increment"
                android:text="@string/plus"
                style="@style/buttonsStyle"
                />

        </LinearLayout>

        <Button
            android:id="@+id/order_button"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginTop="@dimen/elements_margin"
            android:onClick="submitOrder"
            android:padding="@dimen/order_button_padding"
            android:text="@string/order"
            style="@style/buttonsStyle"/>
    </LinearLayout>
</ScrollView>
```
Don't forget to take a look at the resources folder ( /app/res/values ) and take a look at the code there also. For instance the styles.xml code looks like this:
```xml
<resources>

    <!-- Base application theme. -->
    <style name="AppTheme" parent="Theme.AppCompat.Light.DarkActionBar">
        <!-- Customize your theme here. -->
        <item name="colorPrimary">@color/colorPrimary</item>
        <item name="colorPrimaryDark">@color/colorPrimaryDark</item>
        <item name="colorAccent">@color/colorAccent</item>
    </style>

    <style name="buttonsStyle">
        <item name="android:background">@color/colorPrimary</item>
        <item name="android:textColor">@android:color/white</item>
    </style>

</resources>
```
The java code looks like this (app/src/main/java/com/example/android/justjava) or https://github.com/elisavetTriant/JustJava/blob/master/app/src/main/java/com/example/android/justjava/MainActivity.java
```java
/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match the package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.android.justjava;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    //Declare and Initialise Global Vars
    public int quantity = 1;
    //Declare and Initialise Constants
    static final int PRICE_PER_CUP = 5;
    static final int CHOCOLATE_PRICE = 2;
    static final int WHIPPED_CREAM_PRICE = 1;
    //String constants for keys
    static final String QUANTITY_KEY = "quantity";
    //UI object vars
    TextView quantityTextView;
    CheckBox whippedCreamCheckBox;
    CheckBox chocolateCheckBox;
    EditText nameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Get the view objects using the view IDs
        quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        nameEditText = (EditText) findViewById(R.id.name_edit_text);

        updateUIValues();
    }

    // https://stackoverflow.com/questions/151777/saving-android-activity-state-using-save-instance-state
    //prevent the application from restarting when changing orientation. Saving global vars between activity states.
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        // Save UI state changes to the savedInstanceState.
        // This bundle will be passed to onCreate if the process is
        // killed and restarted.
        savedInstanceState.putInt(QUANTITY_KEY, quantity);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // Restore UI state from the savedInstanceState.
        // This bundle has also been passed to onCreate.
        quantity = savedInstanceState.getInt(QUANTITY_KEY);
        updateUIValues();
    }

    /**
     * This method is called when the quantity increment button is clicked.
     */
    public void increment(View view) {
        if (quantity < 100) {
            quantity = quantity + 1;
            displayQuantity(quantity);
        } else {
            displayToastMessage(getString(R.string.max_order));
        }
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
            displayToastMessage(getString(R.string.min_order));
        }
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        String orderMessage = prepareOrderMessage();
        String subjectLine = getString(R.string.order_summary_email_subject,  nameEditText.getText().toString());
        composeEmail(subjectLine, orderMessage);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    public void displayQuantity(int number) {
        quantityTextView.setText(String.valueOf(number));
    }


    /**
     * This method, when called, updates all the TextViews dynamically with the current global vars values
     */
    public void updateUIValues() {
        displayQuantity(quantity);
    }

    /**
     * This method starts an Activity to send email with the order
     * More on https://developer.android.com/guide/components/intents-common.html#Email
     * @param subject
     * @param emailBody
     */
    private void composeEmail(String subject, String emailBody) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, emailBody);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * Create summary of the order.
     *
     * @param name
     * @param totalPrice
     * @param hasWhippedCream is whether or not the user wants whipped cream topping
     * @param hasChocolate is whether or not the user wants chocolate topping
     * @return text summary
     */
    private String createOrderSummary(String name, int totalPrice, boolean hasWhippedCream, boolean hasChocolate) {
        String priceMessage = getString(R.string.order_summary_name, name);
        priceMessage += "\n" + getString(R.string.order_summary_whipped_cream, hasWhippedCream);
        priceMessage += "\n" + getString(R.string.order_summary_chocolate,hasChocolate);
        priceMessage += "\n" + getString(R.string.order_summary_quantity, quantity) ;
        priceMessage += "\n" + getString(R.string.order_summary_price,  NumberFormat.getCurrencyInstance().format(totalPrice));
        priceMessage += "\n" + getString(R.string.thank_you);
        return priceMessage;
    }


    /**
     * Calculates the price of the order.
     *
     * @return total price of the order
     */
    private int calculatePrice() {
        int totalPricePerCup = PRICE_PER_CUP;

        if (whippedCreamCheckBox.isChecked()){
            totalPricePerCup +=  WHIPPED_CREAM_PRICE;
        }

        if (chocolateCheckBox.isChecked()){
            totalPricePerCup += CHOCOLATE_PRICE;
        }

        return quantity * totalPricePerCup;
    }

    /**
     * Gets values and calls createOrderSummary method.
     *
     * @return Order Summary String
     */
    private String prepareOrderMessage(){
        int totalPrice = calculatePrice();
        String customerName = nameEditText.getText().toString();
        boolean isWhippedCreamChecked = whippedCreamCheckBox.isChecked();
        boolean isChocolateChecked = chocolateCheckBox.isChecked();

        return createOrderSummary(customerName, totalPrice, isWhippedCreamChecked, isChocolateChecked);
    }

    /**
     * This method displays a Toast Message when called.
     *
     * @param message, the message to be displayed
     *
     */
    //https://developer.android.com/guide/topics/ui/notifiers/toasts.html
    private void displayToastMessage(String message){
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, message, duration);
        toast.show();
    }

}
```

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
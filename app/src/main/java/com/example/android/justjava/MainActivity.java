/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match the package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.android.justjava;

import android.content.Context;
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
    public String orderMessage = "";
    //Declare and Initialise Constants
    static final int PRICE_PER_CUP = 5;
    static final int CHOCOLATE_PRICE = 2;
    static final int WHIPPED_CREAM_PRICE = 1;
    //String constants for keys
    static final String QUANTITY_KEY = "quantity";
    static final String ORDER_MESSAGE_KEY = "orderMessage";
    //UI object vars
    TextView quantityTextView;
    TextView orderSummaryTextView;
    CheckBox whippedCreamCheckBox;
    CheckBox chocolateCheckBox;
    EditText nameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Get the view objects using the view IDs
        quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
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
        savedInstanceState.putString(ORDER_MESSAGE_KEY, orderMessage );
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // Restore UI state from the savedInstanceState.
        // This bundle has also been passed to onCreate.
        quantity = savedInstanceState.getInt(QUANTITY_KEY);
        orderMessage = savedInstanceState.getString(ORDER_MESSAGE_KEY);

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
            displayToastMessage("You can't order more than 100 coffees! Thank you!");
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
            displayToastMessage("You can't order less than one coffee! Thank you!");
        }
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        orderMessage = prepareOrderMessage();
        displayOrderMessage(orderMessage);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    public void displayQuantity(int number) {
        quantityTextView.setText(String.valueOf(number));
    }

    /**
     * This method displays the given orderSummary on the screen.
     */
    public void displayOrderMessage(String message) {
        orderSummaryTextView.setText(message);
    }

    /**
     * This method, when called, updates all the TextViews dynamically with the current global vars values
     */
    public void updateUIValues() {
        displayQuantity(quantity);
        orderMessage = prepareOrderMessage();
        displayOrderMessage(orderMessage);
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
        String priceMessage = "Name: " + name;
        priceMessage += "\nAdd Whipped Cream? " + hasWhippedCream;
        priceMessage += "\nAdd Chocolate? " + hasChocolate;
        priceMessage += "\nQuantity: " + quantity ;
        priceMessage += "\nTotal: " + NumberFormat.getCurrencyInstance().format(totalPrice);
        priceMessage += "\nThank you!";
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
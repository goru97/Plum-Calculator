package com.yuga.calculator;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Stack;

/**
 * Calculator Activity Class
 * @author gauravbajaj
 * @version 1.0
 */

public class CalcActivity extends ActionBarActivity {

    Stack<Integer> operands = new Stack<Integer>(); //Stack to store operands
    Stack<String> operators = new Stack<String>(); //Stack to store operations
    String lastVal = null; //To check the previous value on button press
    TextView text_view; // To display the result

    /**
     *
     * @param view
     * @author Gaurav Bajaj
     * Function to clear the stacks
     */

    public void clear(View view) {
        operands.clear();
        operators.clear();
        lastVal = null;
        text_view.setText("0");
    }

    Boolean negation = false; // to check initial negative integer

    public void evaluate(View view) {
        Button b = (Button) view;  // Current button pressed


        // Code for operator starts
        if ((view.getId() == R.id.bPlus || view.getId() == R.id.bMinus) || view.getId() == R.id.bEquals) {
            String buttonVal = b.getText().toString().trim();

            String operator = null;
            if (operators.size() != 0)
                operator = operators.pop();

            if (operands.size() == 2) {  // To perform calculations when ready

                int a = operands.pop();
                double val = 0;
                if (operator.equals("+"))  // Addition
                    val = operands.pop() + a;
                if (operator.equals("-"))  // Subtraction
                    val = operands.pop() - a;
                String result = String.format("%.0f", val);

                if (result.length() <= 7) {  //Set the max length to 7
                    operands.push((int) val);
                    text_view.setText(Integer.toString((int) val));

                } else {
                    clear(null);

                    Toast.makeText(this, R.string.large_result, Toast.LENGTH_SHORT)
                            .show();
                }

                if (view.getId() != R.id.bEquals) {
                    operators.push(buttonVal);
                    lastVal = buttonVal;
                } else if (!result.equals("0") && result.length() <= 7)
                    lastVal = Integer.toString((int) val);
                else
                    clear(null);


            } else {   //Code for operands start
                if (view.getId() != R.id.bEquals) {

                    if (operands.size() == 1) {
                        operators.clear();
                        operators.push(buttonVal);
                        lastVal = buttonVal;
                    } else {

                        if (view.getId() == R.id.bMinus) {
                            negation = true;

                        }
                    }
                }
            }

        } else {

            if (lastVal == null) {

                String operand = b.getText().toString();

                if (negation) {  // To handle negative values

                    operand = "-" + operand;
                    negation = false;
                }
                if (!operand.equals("0")) {
                    operands.push(Integer.parseInt(operand));
                    lastVal = operand;
                    text_view.setText(lastVal);
                }

            } else {
                if (!lastVal.equals("+") && !lastVal.equals("-")) {

                    if (lastVal.length() != 7)
                        lastVal = lastVal + b.getText();
                    else {
                        //  clear(null);
                        Toast.makeText(this, R.string.max_length, Toast.LENGTH_SHORT)
                                .show();
                    }

                    operands.pop();
                    operands.push(Integer.parseInt(lastVal));
                    text_view.setText(lastVal);
                } else {
                    lastVal = b.getText().toString();
                    operands.push(Integer.parseInt(lastVal));
                    text_view.setText(lastVal);

                }

            }

        }


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc);
        text_view = (TextView) findViewById(R.id.text_view);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_calc, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

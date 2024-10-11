package com.at.calc;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    TextView resultField;
    EditText NumberField;
    TextView operationField;
    Double operand = null;
    String lastOperation = "=";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        resultField = findViewById(R.id.resultField);
        NumberField = findViewById(R.id.NumberField);
        operationField = findViewById(R.id.operationField);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    @Override
    protected void onSaveInstanceState(Bundle outState){
        outState.putString("OPERATION",lastOperation);
        if(operand!=null)
            outState.putDouble("OPERAND",operand);
        super.onSaveInstanceState(outState);
    }
    @SuppressLint("SetTextI18n")
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        lastOperation = savedInstanceState.getString("OPERATION");
        operand = savedInstanceState.getDouble("OPERAND");
        resultField.setText(operand.toString());
        operationField.setText(lastOperation);
    }

    public void onNumberClick(View view) {
        Button button = (Button)view;
        NumberField.append(button.getText());
        if(lastOperation.equals("=")&&operand!=null){
            operand=null;
        }
    }

    public void onOperationClick(View view) {

        Button button = (Button)view;
        String op= button.getText().toString();
        String number = NumberField.getText().toString();
        lastOperation = op;
        if(!number.isEmpty()){
            number=number.replace(',','.');
            try {
                performOperation(Double.valueOf(number),op);
            }catch (NumberFormatException ex){
                NumberField.setText("");
            }
        }

        operationField.setText(lastOperation);
    }

    @SuppressLint("SetTextI18n")
    private void performOperation(Double number, String operation){

        if(operand == null){
            operand = number;
        }
        else{
            if(lastOperation.equals("=")){
                lastOperation = operation;
            }
            switch (lastOperation){
                case "=":
                    operand = number;
                    break;
                case "/":
                    if (number == 0) {
                        operand = 0.0;
                    }
                    else{
                        operand /= number;
                    }
                    break;
                case "*":
                    operand *=number;
                break;
                case "+":
                    operand +=number;
                    break;
                case "-":
                    operand -=number;
                break;
            }
            resultField.setText(operand.toString().replace('.','.'));
            NumberField.setText("");
        }

    }
}

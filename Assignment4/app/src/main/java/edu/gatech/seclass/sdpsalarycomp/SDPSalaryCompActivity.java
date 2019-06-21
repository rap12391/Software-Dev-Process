package edu.gatech.seclass.sdpsalarycomp;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class SDPSalaryCompActivity extends AppCompatActivity implements OnItemSelectedListener{

    private EditText baseSalary;
    private Spinner currentCity;
    private Spinner newCity;
    private TextView targetSalary;
    private ArrayAdapter<CharSequence> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sdpsalary_comp);

        setupUIViews();

        Resources res = getResources();
        adapter = ArrayAdapter.createFromResource(this,R.array.cities,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        currentCity.setAdapter(adapter);
        currentCity.setSelection(0);
        currentCity.setOnItemSelectedListener(this);

        newCity.setAdapter(adapter);
        newCity.setSelection(0);
        newCity.setOnItemSelectedListener(this);

        baseSalary.setText("0");
        baseSalary.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                compute();

            }
        });
    }
    private void setupUIViews() {
        baseSalary = (EditText) findViewById(R.id.baseSalary);
        currentCity = (Spinner) findViewById(R.id.currentCity);
        newCity = (Spinner) findViewById(R.id.newCity);
        targetSalary = (TextView) findViewById(R.id.targetSalary);

    }

    private void compute(){
        String[] index_val_array = getResources().getStringArray(R.array.index_val);

        int index_val1 = currentCity.getSelectedItemPosition();
        int index_val2 = newCity.getSelectedItemPosition();

        int int_val1 = Integer.parseInt(index_val_array[index_val1]);
        int int_val2 = Integer.parseInt(index_val_array[index_val2]);

        double val1 = (double) int_val1;
        double val2 = (double) int_val2;

        String sTextfromBaseSal = baseSalary.getText().toString();
        if (sTextfromBaseSal.matches("[0-9]+")){
            int base_sal_int = Integer.parseInt(sTextfromBaseSal);

            double base_sal = (double) base_sal_int;

            double coL = base_sal*val2/val1;
            int rounded = (int) Math.round(coL);

            targetSalary.setText(String.valueOf(rounded));
        } else {
            baseSalary.setError("Invalid salary");
            targetSalary.setText("");
        }



}

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        compute();


    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}

package com.example.newproject;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    // 声明
    //菜单
    private Spinner spinnerConversionType, spinnerSourceUnit, spinnerDestinationUnit;
    //输入
    private EditText editTextValue;
    //结果
    private TextView textViewResult;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化
        spinnerConversionType = findViewById(R.id.spinnerConversionType);
        spinnerSourceUnit = findViewById(R.id.spinnerSourceUnit);
        spinnerDestinationUnit = findViewById(R.id.spinnerDestinationUnit);
        editTextValue = findViewById(R.id.editTextValue);
        textViewResult = findViewById(R.id.textViewResult);

        //按钮
        Button buttonConvert = findViewById(R.id.buttonConvert);

        // 适配器——类型选择菜单
        ArrayAdapter<CharSequence> conversionTypeAdapter = ArrayAdapter.createFromResource(this,
                R.array.conversion_types_array, android.R.layout.simple_spinner_item);
        conversionTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerConversionType.setAdapter(conversionTypeAdapter);

        // 设置源单位下拉菜单的适配器（默认显示长度单位）
        updateUnitSpinners(0); // 这里调用了一个方法来设置源单位的适配器，我们需要确保这个方法正确设置了适配器

        // 适配器——源单位
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.length_units_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSourceUnit.setAdapter(adapter);

        //适配器——目标单位
        ArrayAdapter<CharSequence> DestinationTypeAdapter = ArrayAdapter.createFromResource(this,
                R.array.length_units_array, android.R.layout.simple_spinner_item);
        DestinationTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDestinationUnit.setAdapter(DestinationTypeAdapter);

        // 添加转换按钮监听器
        buttonConvert.setOnClickListener(v -> convertValue());

        // 转换类型菜单监听器
        spinnerConversionType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //根据选项更新
                updateUnitSpinners(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //为空时
            }
        });
        //监听器
        buttonConvert.setOnClickListener(v -> {

            convertValue();//转换函数
        });

    }

    private void updateUnitSpinners(int conversionType) {
        ArrayAdapter<CharSequence> adapter;
        // 根据选择的转换类型，更新源单位和目标单位的下拉菜单
        switch (conversionType) {
            case 1: // 重量转换
                adapter = ArrayAdapter.createFromResource(this,
                        R.array.weight_units_array, android.R.layout.simple_spinner_item);
                break;
            case 2: // 温度转换
                adapter = ArrayAdapter.createFromResource(this,
                        R.array.temperature_units_array, android.R.layout.simple_spinner_item);
                break;
            default:
                adapter = ArrayAdapter.createFromResource(this,
                        R.array.length_units_array, android.R.layout.simple_spinner_item);
                break;
        }
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSourceUnit.setAdapter(adapter);
        spinnerDestinationUnit.setAdapter(adapter);
    }

    //转换函数
    private void convertValue() {
        // 获取输入值
        String valueStr = editTextValue.getText().toString();
        if (valueStr.isEmpty()) {
            textViewResult.setText(getString(R.string.result_default));
            return;
        }

        // 获取选择类型
        String sourceUnit = spinnerSourceUnit.getSelectedItem().toString();
        String destinationUnit = spinnerDestinationUnit.getSelectedItem().toString();
        int conversionType = spinnerConversionType.getSelectedItemPosition();

        // 单位转换
        double inputValue = Double.parseDouble(valueStr);
        double result = 0;

        switch (conversionType) {
            case 0: // 长度转换
                result = performLengthConversion(inputValue, sourceUnit, destinationUnit);
                break;
            case 1: // 重量转换
                result = performWeightConversion(inputValue, sourceUnit, destinationUnit);
                break;
            case 2: // 温度转换
                result = performTemperatureConversion(inputValue, sourceUnit, destinationUnit);
                break;
        }

        // 转换后的值
        textViewResult.setText(String.valueOf(result));
    }

    // 长度转换
    private double performLengthConversion(double inputValue, String sourceUnit, String destinationUnit) {
        double result = 0;
        switch (sourceUnit) {
            case "Inch":
                switch (destinationUnit) {
                    case "Inch":
                        result = inputValue;
                        break;
                    case "Centimeter":
                        result = inputValue * 2.54;
                        break;
                    case "Foot":
                        result = inputValue / 12;
                        break;
                    case "Yard":
                        result = inputValue / 36;
                        break;
                    case "Mile":
                        result = inputValue / 63360;
                        break;
                }
                break;
            case "Centimeter":
                switch (destinationUnit) {
                    case "Inch":
                        result = inputValue / 2.54;
                        break;
                    case "Centimeter":
                        result = inputValue;
                        break;
                    case "Foot":
                        result = inputValue / 30.48;
                        break;
                    case "Yard":
                        result = inputValue / 91.44;
                        break;
                    case "Mile":
                        result = inputValue / 160934;
                        break;
                }
                break;
            case "Foot":
                switch (destinationUnit) {
                    case "Inch":
                        result = inputValue * 12;
                        break;
                    case "Centimeter":
                        result = inputValue * 30.48;
                        break;
                    case "Foot":
                        result = inputValue;
                        break;
                    case "Yard":
                        result = inputValue / 3;
                        break;
                    case "Mile":
                        result = inputValue / 5280;
                        break;
                }
                break;
            case "Yard":
                switch (destinationUnit) {
                    case "Inch":
                        result = inputValue * 36;
                        break;
                    case "Centimeter":
                        result = inputValue * 91.44;
                        break;
                    case "Foot":
                        result = inputValue * 3;
                        break;
                    case "Yard":
                        result = inputValue;
                        break;
                    case "Mile":
                        result = inputValue / 1760;
                        break;
                }
                break;
            case "Mile":
                switch (destinationUnit) {
                    case "Inch":
                        result = inputValue * 63360;
                        break;
                    case "Centimeter":
                        result = inputValue * 160934;
                        break;
                    case "Foot":
                        result = inputValue * 5280;
                        break;
                    case "Yard":
                        result = inputValue * 1760;
                        break;
                    case "Mile":
                        result = inputValue;
                        break;
                }
                break;
        }
        return result;

    }

    // 重量转换
    private double performWeightConversion(double inputValue, String sourceUnit, String destinationUnit) {
        double result = 0;
        switch (sourceUnit) {
            case "Pound":
                switch (destinationUnit) {
                    case "Pound":
                        result = inputValue;
                        break;
                    case "Ounce":
                        result = inputValue * 16;
                        break;
                    case "Kilogram":
                        result = inputValue * 0.453592;
                        break;
                    case "Ton":
                        result = inputValue * 0.0005;
                        break;
                }
                break;
            case "Ounce":
                switch (destinationUnit) {
                    case "Pound":
                        result = inputValue * 0.0625;
                        break;
                    case "Ounce":
                        result = inputValue;
                        break;
                    case "Kilogram":
                        result = inputValue * 0.0283495;
                        break;
                    case "Ton":
                        result = inputValue * 0.0000283495;
                        break;
                }
                break;
            case "Kilogram":
                switch (destinationUnit) {
                    case "Pound":
                        result = inputValue * 2.20462;
                        break;
                    case "Ounce":
                        result = inputValue * 35.274;
                        break;
                    case "Kilogram":
                        result = inputValue;
                        break;
                    case "Ton":
                        result = inputValue * 0.00110231;
                        break;
                }
                break;
            case "Ton":
                switch (destinationUnit) {
                    case "Pound":
                        result = inputValue * 2204.62;
                        break;
                    case "Ounce":
                        result = inputValue * 35274;
                        break;
                    case "Kilogram":
                        result = inputValue * 907.185;
                        break;
                    case "Ton":
                        result = inputValue;
                        break;
                }
                break;
        }
        return result;
    }

    // 温度转换
    private double performTemperatureConversion(double inputValue, String sourceUnit, String destinationUnit) {
        double result = 0;
        switch (sourceUnit) {
            case "Celsius":
                switch (destinationUnit) {
                    case "Celsius":
                        result = inputValue;
                        break;
                    case "Fahrenheit":
                        result = (inputValue * 9 / 5) + 32;
                        break;
                    case "Kelvin":
                        result = inputValue + 273.15;
                        break;
                }
                break;
            case "Fahrenheit":
                switch (destinationUnit) {
                    case "Celsius":
                        result = (inputValue - 32) * 5 / 9;
                        break;
                    case "Fahrenheit":
                        result = inputValue;
                        break;
                    case "Kelvin":
                        result = (inputValue + 459.67) * 5 / 9;
                        break;
                }
                break;
            case "Kelvin":
                switch (destinationUnit) {
                    case "Celsius":
                        result = inputValue - 273.15;
                        break;
                    case "Fahrenheit":
                        result = (inputValue * 9 / 5) - 459.67;
                        break;
                    case "Kelvin":
                        result = inputValue;
                        break;
                }
                break;
        }
        return result;
    }
    }


package com.example.admin.linear_regression_android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.util.Log;

import org.tensorflow.contrib.android.TensorFlowInferenceInterface;

public class MainActivity extends AppCompatActivity {
    // Tensorflow work
    static {
        System.loadLibrary("tensorflow_inference");
    }
    private static final String MODEL_NAME = "file:///android_asset/optimized_frozen_linear_regression.pb";
    private static final String INPUT_NODE = "x";
    private static final String OUTPUT_NODE = "y_output";
    private static final int[] INPUT_SHAPE = {1, 1};
    private TensorFlowInferenceInterface inferenceInterface;
    //.................................................................




    EditText editText;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.edit_text);
        textView = (TextView) findViewById(R.id.text_view);

        //Tensorflow
        inferenceInterface = new TensorFlowInferenceInterface();
        inferenceInterface.initializeTensorFlow(getAssets(),MODEL_NAME);
    }



    public void pressButton(View view){
        float input = Float.parseFloat(editText.getText().toString());
        Log.d("MainActivity", String.valueOf(input));

        String results = performInference(input);
        textView.setText(results);
    }

    private String performInference(float input){
        float[] floatArray = {input};
        inferenceInterface.fillNodeFloat(INPUT_NODE, INPUT_SHAPE, floatArray);
        inferenceInterface.runInference(new String[]{OUTPUT_NODE});
        float [] results = {0.0f};
        inferenceInterface.readNodeFloat(OUTPUT_NODE, results);
        String final_result = String.valueOf(results[0]);
        return final_result;
    }
}

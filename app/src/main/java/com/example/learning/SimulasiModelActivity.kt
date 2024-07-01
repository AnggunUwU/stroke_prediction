package com.example.learning

import android.annotation.SuppressLint
import android.content.res.AssetManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import org.tensorflow.lite.Interpreter
import java.io.FileInputStream
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel

class SimulasiModelActivity : AppCompatActivity() {

    private lateinit var interpreter: Interpreter
    private val mModelPath = "stroke.tflite"

    private lateinit var resultText: TextView
    private lateinit var gender: EditText
    private lateinit var age: EditText
    private lateinit var hypertension: EditText
    private lateinit var heart_disease: EditText
    private lateinit var ever_married : EditText
    private lateinit var work_type: EditText
    private lateinit var Residence_type: EditText
    private lateinit var avg_glucose_level: EditText
    private lateinit var bmi: EditText
    private lateinit var smoking_status: EditText
    private lateinit var checkButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simulasi_model)

        resultText = findViewById(R.id.tvHasil)
        gender = findViewById(R.id.etgender)
        age = findViewById(R.id.etAge)
        hypertension = findViewById(R.id.ethypertension)
        heart_disease = findViewById(R.id.heart_disease)
        ever_married = findViewById(R.id.ever_married)
        work_type = findViewById(R.id.work_type)
        Residence_type = findViewById(R.id.Residence_type)
        avg_glucose_level = findViewById(R.id.avg_glucose_level)
        bmi = findViewById(R.id.bmi)
        smoking_status = findViewById(R.id.smoking_status)
        checkButton = findViewById(R.id.btnPredict)

        checkButton.setOnClickListener {
            var result = doInference(
                gender.text.toString(),
                age.text.toString(),
                hypertension.text.toString(),
                heart_disease.text.toString(),
                ever_married.text.toString(),
                work_type.text.toString(),
                Residence_type.text.toString(),
                avg_glucose_level.text.toString(),
                bmi.text.toString(),
                smoking_status.text.toString())
            runOnUiThread {
                if (result == 0) {
                    resultText.text = "tidak stroke"
                }else if (result == 1){
                    resultText.text = "anda terjangkit stroke"
                }
            }
        }
        initInterpreter()
    }

    private fun initInterpreter() {
        val options = org.tensorflow.lite.Interpreter.Options()
        options.setNumThreads(10)
        options.setUseNNAPI(true)
        interpreter = org.tensorflow.lite.Interpreter(loadModelFile(assets, mModelPath), options)
    }

    private fun doInference(input1: String, input2: String, input3: String, input4: String, input5: String, input6: String, input7: String, input8: String, input9: String, input10: String): Int {
        val inputVal = FloatArray(10)
        try {
            inputVal[0] = input1.toFloat()
            inputVal[1] = input2.toFloat()
            inputVal[2] = input3.toFloat()
            inputVal[3] = input4.toFloat()
            inputVal[4] = input5.toFloat()
            inputVal[5] = input6.toFloat()
            inputVal[6] = input7.toFloat()
            inputVal[7] = input8.toFloat()
            inputVal[8] = input9.toFloat()
            inputVal[9] = input10.toFloat()
        } catch (e: NumberFormatException) {
            Log.e("doInference", "Failed to convert input to float", e)
            return -1
        }

        // Buat tensor input dengan dimensi [1, 10]
        val inputTensor = arrayOf(inputVal)

        // Check input values
        Log.d("doInference", "Input values: ${inputVal.joinToString(", ")}")

        val output = Array(1) { FloatArray(1) }
        interpreter.run(inputTensor, output)

        // Check output values
        Log.d("doInference", "Output values: ${output[0].joinToString(", ")}")

        // Ensure the output array has the expected length
        return if (output.isNotEmpty() && output[0].isNotEmpty()) {
            if (output[0][0] > 0.15f) 1 else 0
        } else {
            Log.e("doInference", "Output array is empty or invalid")
            -1
        }
    }


    private fun loadModelFile(assetManager: AssetManager, modelPath: String): MappedByteBuffer{
        val fileDescriptor = assetManager.openFd(modelPath)
        val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
        val fileChannel = inputStream.channel
        val startOffset = fileDescriptor.startOffset
        val declaredLength = fileDescriptor.declaredLength
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
    }
}
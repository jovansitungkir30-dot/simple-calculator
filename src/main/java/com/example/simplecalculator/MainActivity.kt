package com.example.simplecalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var resultTextView: TextView
    private var currentNumber: String = ""
    private var currentOperator: String = ""
    private var firstOperand: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        resultTextView = findViewById(R.id.resultTextView)

        // Temukan semua tombol angka dan set listener
        val numButtons = arrayOf(
            findViewById<Button>(R.id.btn_0), findViewById<Button>(R.id.btn_1),
            findViewById<Button>(R.id.btn_2), findViewById<Button>(R.id.btn_3),
            findViewById<Button>(R.id.btn_4), findViewById<Button>(R.id.btn_5),
            findViewById<Button>(R.id.btn_6), findViewById<Button>(R.id.btn_7),
            findViewById<Button>(R.id.btn_8), findViewById<Button>(R.id.btn_9)
        )

        for (button in numButtons) {
            button.setOnClickListener {
                onNumberClick(button.text.toString())
            }
        }

        // Temukan semua tombol operasi dan set listener
        val opButtons = arrayOf(
            findViewById<Button>(R.id.btn_plus), findViewById<Button>(R.id.btn_minus),
            findViewById<Button>(R.id.btn_multiply), findViewById<Button>(R.id.btn_divide)
        )

        for (button in opButtons) {
            button.setOnClickListener {
                onOperatorClick(button.text.toString())
            }
        }

        // Listener untuk tombol-tombol lain
        findViewById<Button>(R.id.btn_equals).setOnClickListener {
            onEqualsClick()
        }

        findViewById<Button>(R.id.btn_clear).setOnClickListener {
            onClearClick()
        }

        findViewById<Button>(R.id.btn_dot).setOnClickListener {
            onDecimalClick()
        }

        findViewById<Button>(R.id.btn_plus_minus).setOnClickListener {
            onPlusMinusClick()
        }

        findViewById<Button>(R.id.btn_percent).setOnClickListener {
            onPercentClick()
        }
    }

    // Fungsi-fungsi logika kalkulator
    private fun onNumberClick(number: String) {
        if (currentNumber.length < 9) { // Batasi panjang angka
            currentNumber += number
            resultTextView.text = currentNumber
        }
    }

    private fun onOperatorClick(operator: String) {
        if (currentNumber.isNotEmpty()) {
            firstOperand = currentNumber.toDouble()
            currentOperator = operator
            currentNumber = ""
        }
    }

    private fun onEqualsClick() {
        if (currentNumber.isNotEmpty() && currentOperator.isNotEmpty()) {
            val secondOperand = currentNumber.toDouble()
            var result: Double? = null

            when (currentOperator) {
                "+" -> result = firstOperand + secondOperand
                "-" -> result = firstOperand - secondOperand
                "×" -> result = firstOperand * secondOperand
                "÷" -> {
                    if (secondOperand != 0.0) {
                        result = firstOperand / secondOperand
                    } else {
                        resultTextView.text = "Error"
                        return
                    }
                }
            }

            if (result != null) {
                resultTextView.text = formatResult(result)
                currentNumber = result.toString()
                currentOperator = ""
            }
        }
    }

    private fun onClearClick() {
        currentNumber = ""
        currentOperator = ""
        firstOperand = 0.0
        resultTextView.text = "0"
    }

    private fun onDecimalClick() {
        if (!currentNumber.contains(".")) {
            if (currentNumber.isEmpty()) {
                currentNumber = "0."
            } else {
                currentNumber += "."
            }
            resultTextView.text = currentNumber
        }
    }

    private fun onPlusMinusClick() {
        if (currentNumber.isNotEmpty()) {
            val value = currentNumber.toDouble()
            currentNumber = (-value).toString()
            resultTextView.text = currentNumber
        }
    }

    private fun onPercentClick() {
        if (currentNumber.isNotEmpty()) {
            val value = currentNumber.toDouble()
            val result = value / 100
            currentNumber = result.toString()
            resultTextView.text = formatResult(result)
        }
    }

    private fun formatResult(value: Double): String {
        return if (value % 1 == 0.0) {
            // Jika hasilnya bilangan bulat, ubah jadi Int
            value.toInt().toString()
        } else {
            // Jika desimal, tampilkan dengan format
            String.format("%.2f", value)
        }
    }
}
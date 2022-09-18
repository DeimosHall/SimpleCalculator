package com.example.simplecalculator

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ContextThemeWrapper
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi

class MainActivity : AppCompatActivity() {

    var operation: String? = null

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etNumber1: EditText = findViewById(R.id.et_number_1)
        val etNumber2: EditText = findViewById(R.id.et_number_2)
        val btnAdd: Button = findViewById(R.id.btn_add)
        val btnSubtract: Button = findViewById(R.id.btn_subtract)
        val btnMultiply: Button = findViewById(R.id.btn_multiply)
        val btnDivide: Button = findViewById(R.id.btn_divide)
        val btnEqual: Button = findViewById(R.id.btn_equal)
        val btnClear: Button = findViewById(R.id.btn_clear)
        val tvResult: TextView = findViewById(R.id.tv_result)

        btnAdd.setOnClickListener {
            operation = "add"
            highlight(btnAdd, btnSubtract, btnMultiply, btnDivide)
        }
        btnSubtract.setOnClickListener {
            operation = "subtract"
            highlight(btnAdd, btnSubtract, btnMultiply, btnDivide)
        }
        btnMultiply.setOnClickListener {
            operation = "multiply"
            highlight(btnAdd, btnSubtract, btnMultiply, btnDivide)
        }
        btnDivide.setOnClickListener {
            operation = "divide"
            highlight(btnAdd, btnSubtract, btnMultiply, btnDivide)
        }

        btnEqual.setOnClickListener {
            val (num1, num2) = getNumbers(applicationContext, etNumber1, etNumber2)

            if (num1 != null && num2 != null) {
                when (operation) {
                    "add" -> {
                        setResult(tvResult, num1 + num2)
                    }
                    "subtract" -> {
                        setResult(tvResult, num1 - num2)
                    }
                    "multiply" -> {
                        setResult(tvResult, num1 * num2)
                    }
                    "divide" -> {
                        if (num2 == 0.0) {
                            // TODO: try to use generics to fix this line
                            tvResult.text = "${getString(R.string.result)} ${getString(R.string.undefined)}"
                            Toast.makeText(applicationContext, R.string.divided_by_zero, Toast.LENGTH_SHORT).show()
                        } else {
                            setResult(tvResult, num1 / num2)
                        }
                    }
                    else -> {
                        // TODO: show animation that highlights operation buttons
                    }
                }
            }
        }
    }

    @SuppressLint("NewApi")
    @RequiresApi(Build.VERSION_CODES.M)
    private fun highlight(btnAdd: Button, btnSubtract: Button, btnMultiply: Button, btnDivide: Button) {
        Log.d("check", "Darkmode: ${isDarkThemeOn()}")

        when (isDarkThemeOn()) {
            false -> {
                btnAdd.setBackgroundColor(getColor(R.color.primary))
                btnSubtract.setBackgroundColor(getColor(R.color.primary))
                btnMultiply.setBackgroundColor(getColor(R.color.primary))
                btnDivide.setBackgroundColor(getColor(R.color.primary))

                when (operation) {
                    "add" -> btnAdd.setBackgroundColor(getColor(R.color.secondary))
                    "subtract" -> btnSubtract.setBackgroundColor(getColor(R.color.secondary))
                    "multiply" -> btnMultiply.setBackgroundColor(getColor(R.color.secondary))
                    "divide" -> btnDivide.setBackgroundColor(getColor(R.color.secondary))
                }
            }
            true -> {
                btnAdd.setBackgroundColor(getColor(R.color.primary_dark))
                btnSubtract.setBackgroundColor(getColor(R.color.primary_dark))
                btnMultiply.setBackgroundColor(getColor(R.color.primary_dark))
                btnDivide.setBackgroundColor(getColor(R.color.primary_dark))

                when (operation) {
                    "add" -> btnAdd.setBackgroundColor(getColor(R.color.secondary_dark))
                    "subtract" -> btnSubtract.setBackgroundColor(getColor(R.color.secondary_dark))
                    "multiply" -> btnMultiply.setBackgroundColor(getColor(R.color.secondary_dark))
                    "divide" -> btnDivide.setBackgroundColor(getColor(R.color.secondary_dark))
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.R)
    fun isDarkThemeOn(): Boolean {
        return resources.configuration.isNightModeActive
    }

    private fun getNumbers(context: Context, number1: EditText, number2: EditText): Pair<Double?, Double?> {
        var num1: Double? = null
        var num2: Double? = null

        try {
            num1 = number1.text.toString().toDouble()
        } catch (e: Exception) {
            Toast.makeText(context, R.string.missing_number1, Toast.LENGTH_SHORT).show()
        }

        try {
            num2 = number2.text.toString().toDouble()
        } catch (e: Exception) {
            Toast.makeText(context, R.string.missing_number2, Toast.LENGTH_SHORT).show()
        }

        return Pair(num1, num2)
    }

    private fun setResult(textView: TextView, result: Double) {
        textView.text = getString(R.string.result) + " $result"
    }
}
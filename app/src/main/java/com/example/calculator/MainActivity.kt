package com.example.calculator

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var resultTV: TextView
    private var currentInput: String = ""
    private var operator: String? = null
    private var operand1: Double? = null
    private var operand2: Double? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Reference the TextView for displaying the result
        resultTV = findViewById(R.id.resultTV)

        // Set up number buttons
        setNumberButtonClickListeners()

        // Set up operator buttons (+, -, /, *)
        setOperatorButtonClickListeners()

        // Set up special function buttons (CE, C, =, +/-)
        setSpecialFunctionButtonClickListeners()
    }

    private fun setNumberButtonClickListeners() {
        val buttons = listOf(
            R.id.button0, R.id.button1, R.id.button2, R.id.button3, R.id.button4,
            R.id.button5, R.id.button6, R.id.button7, R.id.button8, R.id.button9
        )

        for (id in buttons) {
            val button: Button = findViewById(id)
            button.setOnClickListener {
                appendToInput(button.text.toString())
            }
        }
    }

    private fun setOperatorButtonClickListeners() {
        val operators = mapOf(
            R.id.buttonPlus to "+",
            R.id.buttonMinus to "-",
            R.id.buttonMultiply to "*",
            R.id.buttonDivide to "/"
        )

        for ((id, op) in operators) {
            val button: Button = findViewById(id)
            button.setOnClickListener {
                if (currentInput.isNotEmpty()) {
                    operand1 = currentInput.toDoubleOrNull()
                    operator = op
                    currentInput = ""
                    updateResultDisplay()
                }
            }
        }
    }

    private fun setSpecialFunctionButtonClickListeners() {
        findViewById<Button>(R.id.buttonCE).setOnClickListener {
            clearAll()
        }
        findViewById<Button>(R.id.buttonC).setOnClickListener {
            clearInput()
        }

        findViewById<Button>(R.id.buttonEqual).setOnClickListener {
            calculateResult()
        }

        findViewById<Button>(R.id.buttonPlusMinus).setOnClickListener {
            toggleSign()
        }

        findViewById<Button>(R.id.buttonDot).setOnClickListener {
            if (!currentInput.contains(".")) {
                appendToInput(".")
            }
        }

        findViewById<Button>(R.id.buttonBS).setOnClickListener {
            if (currentInput.isNotEmpty()) {
                currentInput = currentInput.dropLast(1)
                updateResultDisplay()
            }
        }
    }

    private fun clearInput() {
        currentInput =""
        updateResultDisplay()
    }

    private fun appendToInput(value: String) {
        currentInput += value
        updateResultDisplay()
    }

    private fun updateResultDisplay() {
        resultTV.text = currentInput
    }

    private fun clearAll() {
        currentInput = ""
        operator = null
        operand1 = null
        operand2 = null
        updateResultDisplay()
    }

    private fun toggleSign() {
        if (currentInput.isNotEmpty() && currentInput != "0") {
            currentInput = if (currentInput.startsWith("-")) {
                currentInput.removePrefix("-")
            } else {
                "-$currentInput"
            }
            updateResultDisplay()
        }
    }

    private fun calculateResult() {
        operand2 = currentInput.toDoubleOrNull()

        if (operand1 != null && operand2 != null && operator != null) {
            val result = when (operator) {
                "+" -> operand1!! + operand2!!
                "-" -> operand1!! - operand2!!
                "*" -> operand1!! * operand2!!
                "/" -> if (operand2 != 0.0) operand1!! / operand2!! else "Error"
                else -> "Error"
            }
            resultTV.text = result.toString()
            operand1 = null
            operand2 = null
            currentInput = ""
            operator = null
        }
        else if(operand1 != null  && operator != null) {
            resultTV.text = operand1.toString()
        }
    }
}

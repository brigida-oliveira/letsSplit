package com.tiptime.letssplit

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.tiptime.letssplit.databinding.ActivityMainBinding
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        // Make sure this is before calling super.onCreate
        setTheme(R.style.Theme_LetsSplit)

        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.calculateButton.setOnClickListener { calculateTip() }

        binding.costOfServiceEditText.setOnKeyListener { view, keyCode, _ -> handleKeyEvent(view, keyCode) }
    }

    private fun calculateTip() {
        val stringInTextField = binding.costOfServiceEditText.text.toString()
        val stringInSecondTextField = binding.splitNumberEditText.text.toString()
        val peopleNumber = stringInSecondTextField.toDoubleOrNull()
        val cost = stringInTextField.toDoubleOrNull()
        if (cost == null || peopleNumber == null) {
            binding.tipResult.text = ""
            return
        }

        val tipPercentage = when (binding.tipOptions.checkedRadioButtonId) {
            R.id.option_twenty_percent -> 0.20
            R.id.option_eighteen_percent -> 0.18
            R.id.option_fifteen_percent -> 0.15
            else -> 0.10
        }

        var tip = tipPercentage * cost
        var tipByPerson = (tipPercentage * cost) / peopleNumber
        var valueByPeople = cost / peopleNumber
        var totalValue = valueByPeople + tipByPerson

        val formattedTip = NumberFormat.getCurrencyInstance().format(tip)
        binding.tipResult.text = getString(R.string.tip_amount, formattedTip)

        val formattedTipByPerson = NumberFormat.getCurrencyInstance().format(tipByPerson)
        binding.tipByPerson.text = getString(R.string.tip_by_person, formattedTipByPerson)

        val formattedCostByPerson = NumberFormat.getCurrencyInstance().format(valueByPeople)
        binding.costByPersonResult.text = getString(R.string.person_cost, formattedCostByPerson)

        val formattedResult = NumberFormat.getCurrencyInstance().format(totalValue)
        binding.totalByPersonResult.text = getString(R.string.total_amount, formattedResult)
    }



    private fun handleKeyEvent(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            // Hide the keyboard
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }
}
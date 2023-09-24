package com.example.rates_conversion

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.isVisible
import com.example.rates_conversion.databinding.ActivityMainBinding
import com.example.rates_conversion.fxdatas.Rates
import com.example.rates_conversion.fxdatas.RetrofitInstance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainActivity : AppCompatActivity() {
    private var MainActivity = "MainActivity"
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnConvert.setOnClickListener {

            makeRequest()
        }


    }


    @SuppressLint("SetTextI18n")
    private fun makeRequest() {
        CoroutineScope(Dispatchers.IO).launch {
            try {


                val fromCurrency = binding.spFromCurrency.selectedItem.toString()
                val toCurrency = binding.spToCurrency.selectedItem.toString()
                val amount = binding.etFrom.text.toString().toDoubleOrNull()

                if (amount != null) {
                    val response = RetrofitInstance.api.getRates("EUR")

                    if (response.isSuccessful) {
                        binding.progressBar.isVisible = false
                        binding.tvResult.setTextColor(Color.BLACK)

                        val rates = response.body()?.rates

                        if (rates != null) {

                            val fromRate = getRateForCurrency(fromCurrency, rates)
                            val toRate = getRateForCurrency(toCurrency, rates)

                            if (fromRate != null && toRate != null) {
                                val convertedAmount = amount * (toRate / fromRate)
                                withContext(Dispatchers.Main) {
                                    binding.tvResult.text = convertedAmount.toString()
                                }
                            } else {
                                binding.tvResult.text = "Currency rates not found."
                                binding.progressBar.isVisible = false
                                binding.tvResult.setTextColor(Color.RED)
                            }
                        }
                    }
                } else {
                    binding.tvResult.text = "Please enter a valid amount."
                    binding.progressBar.isVisible = false
                    binding.tvResult.setTextColor(Color.RED)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Log.d(MainActivity, "Request failed: ${e.message}")
                withContext(Dispatchers.Main) {
                    binding.tvResult.text = "Request failed:   ${e.message}"
                }
            }
        }
    }


    private fun getRateForCurrency(currency: String, rates: Rates) = when (currency) {
        "EUR" -> rates.EUR
        "USD" -> rates.USD
        "AUD" -> rates.AUD
        "GBP" -> rates.GBP
        "JPY" -> rates.JPY
        "CAD" -> rates.CAD
        "MXN" -> rates.MXN
        "PLN" -> rates.PLN
        else -> null
    }
}










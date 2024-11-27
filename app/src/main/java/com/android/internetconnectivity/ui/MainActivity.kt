package com.android.internetconnectivity.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.android.internetconnectivity.data.ConnectivityObserverImpl
import com.android.internetconnectivity.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var connectivityViewModel: ConnectivityViewModel
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val connectivityObserver = ConnectivityObserverImpl(applicationContext)
        connectivityViewModel = ConnectivityViewModel(connectivityObserver)

        setObserver()
    }

    private fun setObserver() {
        lifecycleScope.launch {
            connectivityViewModel.isConnected.collect { isConnected ->
                // Hide the progress bar once the connection state is received
                binding.progressBar.visibility = View.GONE
                if (isConnected) {
                    // Update the TextView for connected state
                    binding.statusTextView.text = "Network Connected"
                    binding.statusTextView.setTextColor(
                        ContextCompat.getColor(
                            this@MainActivity,
                            android.R.color.holo_green_dark
                        )
                    )
                    binding.retryButton.visibility = View.GONE
                } else {
                    // Update the TextView for disconnected state
                    binding.statusTextView.text = "Network Disconnected"
                    binding.statusTextView.setTextColor(
                        ContextCompat.getColor(
                            this@MainActivity,
                            android.R.color.holo_red_dark
                        )
                    )

                    // Optionally, show the retry button if the user needs to try reconnecting
                    binding.retryButton.visibility = View.VISIBLE
                    binding.retryButton.setOnClickListener {
                        binding.statusTextView.text = "Checking connectivity..."
                        binding.progressBar.visibility = View.VISIBLE
                        binding.retryButton.visibility = View.GONE
                    }
                }
            }
        }
    }
}
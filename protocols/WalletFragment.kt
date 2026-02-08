package com.escargot.android.ui.wallet

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.escargot.android.R

/**
 * WalletFragment: Affiche les DUCO gagnés en survivant.
 * Offline-first: Cache les balances locales.
 */
class WalletFragment : Fragment(R.layout.fragment_wallet) {

    private var cachedBalance: Double = 0.0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        // Load offline balance
        loadCachedBalance()
        
        // Update UI
        // view.balanceText.text = "$cachedBalance DUCO"
        
        // Sync button (Try to reach a node with internet bridge)
        // view.syncButton.setOnClickListener { triggerMeshSync() }
    }

    private fun loadCachedBalance() {
        // TODO: Read from encrypted SharedPreferences
        // Survive EMP: Data stored locally
        cachedBalance = 12.50 // Stub
    }
}
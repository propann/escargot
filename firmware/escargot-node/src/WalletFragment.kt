package com.propann.escargot.ui.wallet

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment

// Survive horde: UI Stub for Wallet V2
// v2 foufou: à fond les zombies !

class WalletFragment : Fragment() {

    private var balance: Double = 42.0 // Stub riche

    fun showBalance() {
        // TODO: Lire le solde depuis le stockage local sécurisé (offline first)
        // textViewBalance.text = "$balance DUCO 🐌"
    }

    fun postEarn() {
        // Déclenche le minage sur le noeud via Bluetooth/USB
        // NodeService.sendCommand("CMD_MINE_START")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showBalance()
        // Survive EMP: offline opti
    }
}
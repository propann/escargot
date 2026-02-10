package com.propann.escargot.ui.wallet

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment

// Survive horde: UI Stub for Wallet
class WalletFragment : Fragment() {

    private var balance: Double = 0.0

    fun showBalance() {
        // TODO: Lire le solde depuis le stockage local sécurisé
        // textViewBalance.text = "$balance DUCO"
    }

    fun postEarn() {
        // TODO: Déclencher une tâche de minage en arrière-plan si connecté au noeud
        // NodeService.startMining()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showBalance()
    }
}
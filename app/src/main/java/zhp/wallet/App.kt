package zhp.wallet

import android.app.Application

class App: Application() {

    init {
        System.loadLibrary("TrustWalletCore")
    }

}
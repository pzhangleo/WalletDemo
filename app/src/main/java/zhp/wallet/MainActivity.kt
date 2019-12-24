package zhp.wallet

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import wallet.core.jni.BitcoinAddress
import wallet.core.jni.CoinType
import wallet.core.jni.HDWallet

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var content = ""
        button.setOnClickListener {
            val wallet = HDWallet(
                "shoot island position soft burden budget tooth cruel issue economy destroy above",
                ""
            )
            content = wallet.mnemonic()
            textView.text = content.plus("\n")
                .plus(
                    BitcoinAddress(
                        wallet.getKey("m/49'/0'/0'/0/0").getPublicKeySecp256k1(true), CoinType.BITCOIN.p2shPrefix()
                    ).description()
                )
        }

    }
}

package zhp.wallet

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.trustwallet.core.app.utils.Numeric
import com.trustwallet.core.app.utils.toHexByteArray
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import wallet.core.jni.*

@RunWith(AndroidJUnit4::class)//mhA2dTKenJboiZCdeGThZqJnhkFnsfuH8E
class BitcoinTest {//cUtktjkMRnsYr6Zbv818TmLQRFvGcV8JTForrofyyciTpiQwwmzN

    init {
        System.loadLibrary("TrustWalletCore")
    }

    val word = "shoot island position soft burden budget tooth cruel issue economy destroy above"
    val wallet = HDWallet(word, "")

    @Test
    fun testBip44Address() {
        val value = BitcoinAddress(
            wallet.getKey("m/44'/0'/0'/0/0").getPublicKeySecp256k1(true),
            CoinType.BITCOIN.staticPrefix()
        ).description()
        assertEquals("1BejyChZRiwwE2zYtbj8HRqq1RYScA8uAp", value)
    }

    @Test
    fun testBip44TestNetAddress() {
        val privateKey =
            PrivateKey("DA2682C7BC5EA24303CD595D83381AD5BF488B0C6A42BB18CE380DE9E6039CCE".toHexByteArray())
        val publicKey =
            PublicKey(
                "021F7054BC803D15279DA7549C3754630045B13FD8B3708CB956716DEAFCE916AD".toHexByteArray(),
                PublicKeyType.SECP256K1
            )
        val value = BitcoinAddress(privateKey.getPublicKeySecp256k1(true), 0x0)
//        assertEquals("mhA2dTKenJboiZCdeGThZqJnhkFnsfuH8E", value.description())
        assertEquals(privateKey.getPublicKeySecp256k1(true).description(), publicKey.description())
    }

    @Test
    fun testBip49Address() {
        val value = BitcoinAddress(
            wallet.getKey("m/49'/0'/0'/0/0").getPublicKeySecp256k1(true),
            CoinType.BITCOIN.p2shPrefix()
        ).description()
        assertEquals("3QeWZLebxSAxswzSZNMa6bTurQxBXkPirJ", value)
    }

    @Test
    fun testBip84Address() {
        val value = SegwitAddress(
            CoinType.BITCOIN.hrp(),
            wallet.getKey("m/84'/0'/0'/0/0").getPublicKeySecp256k1(true)
        ).description()
        assertEquals("bc1quvuarfksewfeuevuc6tn0kfyptgjvwsvrprk9d", value)
    }

    @Test
    fun testBip84Address2() {
        val value = CoinType.BITCOIN.deriveAddress(wallet.getKeyForCoin(CoinType.BITCOIN))
        assertEquals("bc1quvuarfksewfeuevuc6tn0kfyptgjvwsvrprk9d", value)
    }

}
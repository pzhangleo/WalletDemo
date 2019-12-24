package zhp.wallet

import androidx.test.ext.junit.runners.AndroidJUnit4
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
            wallet.getKey("m/44'/0'/0'/0/0").getPublicKeySecp256k1(true), CoinType.BITCOIN.staticPrefix()
        ).description()
        assertEquals("1BejyChZRiwwE2zYtbj8HRqq1RYScA8uAp", value)
    }

    @Test
    fun testBip44TestNetAddress() {
        val pk = PrivateKey("cUtktjkMRnsYr6Zbv818TmLQRFvGcV8JTForrofyyciTpiQwwmzN".toByteArray())
        val value = BitcoinAddress(
           pk.getPublicKeySecp256k1(true), CoinType.BITCOIN.staticPrefix()
        ).description()
        assertEquals("1BejyChZRiwwE2zYtbj8HRqq1RYScA8uAp", value)
    }

    @Test
    fun testBip49Address() {
        val value = BitcoinAddress(
            wallet.getKey("m/49'/0'/0'/0/0").getPublicKeySecp256k1(true), CoinType.BITCOIN.p2shPrefix()
        ).description()
        assertEquals("3QeWZLebxSAxswzSZNMa6bTurQxBXkPirJ", value)
    }

    @Test
    fun testBip84Address() {
        val value = SegwitAddress(CoinType.BITCOIN.hrp(),
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
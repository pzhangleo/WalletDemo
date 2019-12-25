package zhp.wallet

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.trustwallet.core.app.utils.toHex
import com.trustwallet.core.app.utils.toHexByteArray
import com.trustwallet.core.app.utils.toHexNoPrefix
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
        val value = BitcoinAddress(privateKey.getPublicKeySecp256k1(true), 111.toByte())
        assertEquals("mhA2dTKenJboiZCdeGThZqJnhkFnsfuH8E", value.description())
        assertEquals(privateKey.getPublicKeySecp256k1(true).description(), publicKey.description())
    }

    @Test
    fun testBip49Address() {//key都没有问题，主要是生成地址的算法
        val privateKey = wallet.getKey("m/49'/0'/0'/0/0")
        val value = BitcoinAddress(
            privateKey.getPublicKeySecp256k1(true),
            CoinType.BITCOIN.p2shPrefix()
        ).description()
        assertEquals(
            "0x33fcdf642437f6351f78249ab99c6477e299ac135d4cae334ca0e768ec884429",
            privateKey.data().toHex()
        )

        val publicKeyHash = Hash.sha256RIPEMD(privateKey.getPublicKeySecp256k1(true).data())
        val hash160 = Hash.sha256RIPEMD(("0x0014" + publicKeyHash.toHexNoPrefix()).toHexByteArray())
        // A stringified buffer is:
        //   1 byte version + data bytes + 4 bytes check code (a truncated hash)
        val version = 5.toByte()
        val addressBytes = ByteArray(1 + hash160.size + 4)
        addressBytes[0] = version
        System.arraycopy(hash160, 0, addressBytes, 1, hash160.size)
        val checksum: ByteArray = Hash.sha256SHA256(addressBytes.dropLast(4).toByteArray())
        System.arraycopy(checksum, 0, addressBytes, hash160.size + 1, 4)
        val address = Base58.encodeNoCheck(addressBytes)
        assertEquals("3QeWZLebxSAxswzSZNMa6bTurQxBXkPirJ", address)
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
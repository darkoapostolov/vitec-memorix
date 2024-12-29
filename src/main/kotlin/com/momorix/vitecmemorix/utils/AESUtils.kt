package com.momorix.vitecmemorix.utils

import org.springframework.stereotype.Component
import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

@Component
object AESUtils {
    private const val ALGORITHM = "AES"

    @Throws(Exception::class)
    fun encrypt(value: String, keyString: String?): String {
        val secretKey = SecretKeySpec(keyString!!.toByteArray(), ALGORITHM)
        val cipher = Cipher.getInstance(ALGORITHM)
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)
        return Base64.getEncoder().encodeToString(cipher.doFinal(value.toByteArray()))
    }
}
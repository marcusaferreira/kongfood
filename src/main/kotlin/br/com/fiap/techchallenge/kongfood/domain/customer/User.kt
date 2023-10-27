package br.com.fiap.techchallenge.kongfood.domain.customer

import java.security.SecureRandom
import java.security.spec.KeySpec
import java.util.*
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec


class User(
    val id: UUID,
    val username: String,
    var password: String,
    var userSalt: String?
) {

    init {
        val tempPassword = password
        val random = SecureRandom()
        val salt = ByteArray(16)
        random.nextBytes(salt)
        val spec: KeySpec = PBEKeySpec(tempPassword.toCharArray(), salt, 65536, 128)
        val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
        val hash = factory.generateSecret(spec).encoded
        password = String(hash)
        userSalt = String(salt)
    }

}

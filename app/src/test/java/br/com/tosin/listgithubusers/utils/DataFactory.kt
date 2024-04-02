package br.com.tosin.listgithubusers.utils

import java.util.Calendar
import java.util.Date
import java.util.Random
import java.util.UUID
import java.util.concurrent.ThreadLocalRandom

object DataFactory {

    private const val SALT_CHARS = "abcdefghijklmnopqrstuvwxyz1234567890"
    private const val SALT_CHARS_ALPHABET = "abcdefghijklmnopqrstuvwxyz"

    fun randomString(): String = randomUUID()

    fun randomInt(): Int = ThreadLocalRandom.current().nextInt(1, 1000 + 1)

    fun randomLong(): Long = randomInt().toLong()

    fun randomDouble(): Double = ThreadLocalRandom.current().nextDouble(1.0, 1000 + 1.0)

    fun randomBoolean(): Boolean = Math.random() < 0.5

    fun randomDate(): Date = randomCalendar().time

    fun randomUUID(): String = UUID.randomUUID().toString()

    fun randomCalendar(addOneMinute: Boolean = false): Calendar {
        val aux = Calendar.getInstance()
        if (addOneMinute) {
            aux.add(Calendar.MINUTE, 1)
        }
        return aux
    }

    fun randomEmail(): String {
        val salt = StringBuilder()
        val rnd = Random()
        while (salt.length < 10) { // length of the random string.
            val index = (rnd.nextFloat() * SALT_CHARS.length).toInt()
            salt.append(SALT_CHARS[index])
        }
        return "$salt@churros.com"
    }

    fun randomAlphabet(): String {
        val salt = StringBuilder()
        val rnd = Random()
        while (salt.length < 10) { // length of the random string.
            val index = (rnd.nextFloat() * SALT_CHARS_ALPHABET.length).toInt()
            salt.append(SALT_CHARS_ALPHABET[index])
        }
        return salt.toString()
    }

    fun randomPhone(): String {
        val country = ThreadLocalRandom.current().nextInt(1, 999 + 1)
        val DDD = ThreadLocalRandom.current().nextInt(10, 99 + 1)
        val pre = ThreadLocalRandom.current().nextInt(1000, 99999 + 1)
        val pos = ThreadLocalRandom.current().nextInt(1000, 9999 + 1)
        return "+$country($DDD)$pre-$pos"
    }
}

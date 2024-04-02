package br.com.tosin.listgithubusers.ui.utils

private const val DEFAULT_DISPLAY_STRING = "-"

fun String?.defaultDisplayOfStringEmpty(): String {
    return this?.ifEmpty {
        DEFAULT_DISPLAY_STRING
    } ?: DEFAULT_DISPLAY_STRING
}

package dev.xnative.cleanrmapi.common

fun <T> tryOrNull(block: () -> T) = try {
    block()
} catch (_: Exception) {
    null
}
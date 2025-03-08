package org.mathieu.cleanrmapi.common.extensions

fun <T: Any> T.toList(): List<T> = listOf(this)
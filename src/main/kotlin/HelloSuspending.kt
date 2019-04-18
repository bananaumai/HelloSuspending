package dev.bananaumai.practices.suspending

import kotlinx.coroutines.*

fun main() = runBlocking<Unit> {
    println(delayedHello())
}

suspend fun delayedHello(): String {
    delay(100)
    return "Hello World"
}

package dev.bananaumai.practices.suspending.delay

import kotlinx.coroutines.*
import java.time.LocalDateTime

fun main() {
    runBlocking {
        launch(CoroutineName("launch1")) {
            println("$coroutineContext , ${Thread.currentThread().name}")
            delay(1)
            for (i in 1..100) {
                if (i > 0 && i % 10 == 0) {
                    println()
                    delay(10)
                } else {
                    print("-")
                }
            }
        }

        launch(CoroutineName("launch2")) {
            println("$coroutineContext , ${Thread.currentThread().name}")
            delay(10)
            for (i in 1..100) {
                if (i > 0 && i % 10 == 0) {
                    println()
                    delay(1)
                } else {
                    print("+")
                }
            }
        }
    }

    runBlocking(Dispatchers.Default) {
        launch(CoroutineName("launch3")) {
            for (i in 1..100) {
                if (i > 0 && i % 10 == 0) {
                    delay(1)
                } else {
                    println("$coroutineContext , ${Thread.currentThread().name}")
                }
            }
        }

        launch(CoroutineName("launch4")) {
            for (i in 1..50) {
                if (i > 0 && i % 10 == 0) {
                    delay(1)
                } else {
                    println("$coroutineContext , ${Thread.currentThread().name}")
                }
            }
        }
    }
}


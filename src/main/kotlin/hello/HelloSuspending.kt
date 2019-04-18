package dev.bananaumai.practices.suspending.hello

import kotlinx.coroutines.*
import java.time.LocalDateTime
import kotlin.system.measureNanoTime
import kotlin.system.measureTimeMillis

fun main() {
    runBlocking(CoroutineName("without dispatcher")) { test() }
    runBlocking(CoroutineName("with default dispatcher") + Dispatchers.Default) { test() }
    println("This code is not executed until all launched process is done")
}

fun CoroutineScope.test() = launch {
    println("Test is started. $coroutineContext, ${Thread.currentThread().name}")

    val time = measureTimeMillis {
        println("before launch heavy")
        val job1 = launch { heavy(this) }
        println("after launched heavy")

        println("before launch suspendableHeavy1")
        val job2 = launch { suspendableHeavy1(this) }
        println("after launched suspendableHeavy1")

        println("before launch suspendableHeavy2")
        val job3 = launch { suspendableHeavy2(this) }
        println("after launched suspendableHeavy2")

        println("before launch suspendableHeavy3")
        val job4 = launch { suspendableHeavy3(this) }
        println("after launched suspendableHeavy3")

        joinAll(job1, job2, job3, job4)
    }

    println("Test was finished in $time/ms. $coroutineContext, ${Thread.currentThread().name}")
}

val LIMIT = 2_000_000_000L

fun heavy(scope: CoroutineScope) {
    val label = "heavy"
    startLog(label, scope)
    var cnt = 0; while(cnt < LIMIT) { cnt++ }
    endLog(label, scope)
}

suspend fun suspendableHeavy1(scope: CoroutineScope) {
    val label = "suspendableHeavy1"
    startLog(label, scope)
    delay(3000)
    endLog(label, scope)
}

suspend fun suspendableHeavy2(scope: CoroutineScope) = withContext(Dispatchers.Default) {
    val label = "suspendableHeavy2"
    startLog(label, scope)
    var cnt = 0; while(cnt < LIMIT) { cnt++ }
    endLog(label, scope)
}

suspend fun suspendableHeavy3(scope: CoroutineScope) = coroutineScope {
    val label = "suspendableHeavy3"
    startLog(label, scope)
    var cnt = 0; while(cnt < LIMIT) { cnt++ }
    endLog(label, scope)
}

fun startLog(label: String, scope: CoroutineScope? = null) {
    println("${LocalDateTime.now()} [$label] Start $scope: ${Thread.currentThread().name}")
}

fun endLog(label: String, scope: CoroutineScope? = null) {
    println("${LocalDateTime.now()} [$label] End $scope: ${Thread.currentThread().name}")
}

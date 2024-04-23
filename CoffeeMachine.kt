package machine

import kotlin.system.exitProcess

object CoffeeMachine {
    var water = 400
    var milk = 540
    var beans = 120
    var cups = 9
    var money = 550
    fun transact(coffee: Coffee): String {
        val enough = when {
            water < coffee.water -> "water"
            milk < coffee.milk -> "milk"
            beans < coffee.beans -> "beans"
            cups < 1 -> "cups"
            else -> "enough"
        }
        return if (enough == "enough") {
            water -= coffee.water
            milk -= coffee.milk
            beans -= coffee.beans
            cups--
            money += coffee.money
            "I have enough resources, making you a coffee!"
        } else {
            "Sorry, not enough $enough!"
        }
    }
    fun fill() {
        println("Write how many ml of water you want to add:")
        water += readln().toInt()
        println("Write how many ml of milk you want to add:")
        milk += readln().toInt()
        println("Write how many grams of coffee beans you want to add:")
        beans += readln().toInt()
        println("Write how many disposable cups you want to add:")
        cups += readln().toInt()
    }

    fun take() {
        println("I gave you \$$money")
        money = 0
    }

    fun remaining() {
        val stateTemplate = """
            The coffee machine has:
            $water ml of water
            $milk ml of milk
            $beans g of coffee beans
            $cups disposable cups
            ${'$'}$money of money
        """.trimIndent()
        println(stateTemplate)
    }
}

data class Coffee(val water: Int, val milk: Int, val beans: Int, val money: Int)
val espresso = Coffee(water = 250, milk = 0, beans = 16, money = 4)
val latte = Coffee(water = 350, milk = 75, beans = 20, money = 7)
val cappuccino = Coffee(water = 200, milk = 100, beans = 12, money = 6)

fun main() {
    while (true) {
        println("Write action (buy, fill, take, remaining, exit):")
        when (readln()) {
            "buy" -> buy()
            "fill" -> CoffeeMachine.fill()
            "take" -> CoffeeMachine.take()
            "remaining" -> CoffeeMachine.remaining()
            "exit" -> exitProcess(0)
        }
        println()
    }
}

fun buy() {
    println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:")
    when (readln()) {
        "1" -> println(CoffeeMachine.transact(espresso))
        "2" -> println(CoffeeMachine.transact(latte))
        "3" -> println(CoffeeMachine.transact(cappuccino))
        "back" -> {}
    }
}
package machine

class CoffeeMachine(var water: Int, var milk: Int, var beans: Int, var cups: Int, var money: Int) {
    var state = State.MENU

    enum class State {
        OFF,
        MENU,
        BUY,
        FILL
    }

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

    fun interact(s1: String, s2: String = "", s3: String = "", s4: String = "") {
        when (state) {
            State.BUY -> when (s1) {
                "1" -> println(transact(Coffee.ESPRESSO))
                "2" -> println(transact(Coffee.LATTE))
                "3" -> println(transact(Coffee.CAPPUCCINO))
                "back" -> {}
            }
            State.FILL -> {
                water += s1.toInt()
                milk += s2.toInt()
                beans += s3.toInt()
                cups += s4.toInt()
            }
        }
        state = State.MENU
    }
}

enum class Coffee(val water: Int, val milk: Int, val beans: Int, val money: Int) {
    ESPRESSO(water = 250, milk = 0, beans = 16, money = 4),
    LATTE(water = 350, milk = 75, beans = 20, money = 7),
    CAPPUCCINO(water = 200, milk = 100, beans = 12, money = 6)
}

fun main() {
    val coffeeMachine = CoffeeMachine(water = 400, milk = 540, beans = 120, cups = 9, money = 550)

    while (coffeeMachine.state == CoffeeMachine.State.MENU) {
        println("Write action (buy, fill, take, remaining, exit):")
        when (readln()) {
            "buy" -> coffeeMachine.state = CoffeeMachine.State.BUY
            "fill" -> coffeeMachine.state = CoffeeMachine.State.FILL
            "take" -> coffeeMachine.take()
            "remaining" -> coffeeMachine.remaining()
            "exit" -> coffeeMachine.state = CoffeeMachine.State.OFF
        }

        if (coffeeMachine.state == CoffeeMachine.State.BUY) {
            println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:")
            coffeeMachine.interact(readln())
        } else if (coffeeMachine.state == CoffeeMachine.State.FILL) {
            println("Write how many ml of water you want to add:")
            val water = readln()
            println("Write how many ml of milk you want to add:")
            val milk = readln()
            println("Write how many grams of coffee beans you want to add:")
            val beans = readln()
            println("Write how many disposable cups you want to add:")
            val cups = readln()
            coffeeMachine.interact(water, milk, beans, cups)
        }
        println()
    }
}
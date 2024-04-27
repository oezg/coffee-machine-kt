package machine

class CoffeeMachine(var water: Int, var milk: Int, var beans: Int, var cups: Int, var money: Int) {
    var state = State.OFF

    enum class State {
        OFF,
        MENU,
        SELL,
        FILL_WATER,
        FILL_MILK,
        FILL_BEANS,
        FILL_CUPS
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

    fun interact(input: String) {
        when (state) {
            State.MENU -> {
                when (input) {
                    "buy" -> state = State.SELL
                    "fill" -> state = State.FILL_WATER
                    "take" -> take()
                    "remaining" -> remaining()
                    "exit" -> state = State.OFF
                }

            }
            State.SELL -> {
                when (input) {
                    "1" -> println(transact(Coffee.ESPRESSO))
                    "2" -> println(transact(Coffee.LATTE))
                    "3" -> println(transact(Coffee.CAPPUCCINO))
                    "back" -> {}
                }
                state = State.MENU
            }
            State.FILL_WATER -> {
                water += input.toInt()
                state = State.FILL_MILK
            }
            State.FILL_MILK -> {
                milk += input.toInt()
                state = State.FILL_BEANS
            }
            State.FILL_BEANS -> {
                beans += input.toInt()
                state = State.FILL_CUPS
            }
            State.FILL_CUPS -> {
                cups += input.toInt()
                state = State.MENU
            }
            else -> {}
        }
    }
}

enum class Coffee(val water: Int, val milk: Int, val beans: Int, val money: Int) {
    ESPRESSO(water = 250, milk = 0, beans = 16, money = 4),
    LATTE(water = 350, milk = 75, beans = 20, money = 7),
    CAPPUCCINO(water = 200, milk = 100, beans = 12, money = 6)
}
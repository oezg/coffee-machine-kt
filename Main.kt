package machine

fun main() {
    val coffeeMachine = CoffeeMachine(water = 400, milk = 540, beans = 120, cups = 9, money = 550)
    coffeeMachine.state = CoffeeMachine.State.MENU

    while (coffeeMachine.state != CoffeeMachine.State.OFF) {
        println(when (coffeeMachine.state) {
            CoffeeMachine.State.MENU -> "Write action (buy, fill, take, remaining, exit):"
            CoffeeMachine.State.SELL -> "What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:"
            CoffeeMachine.State.FILL_WATER -> "Write how many ml of water you want to add:"
            CoffeeMachine.State.FILL_MILK -> "Write how many ml of milk you want to add:"
            CoffeeMachine.State.FILL_BEANS -> "Write how many grams of coffee beans you want to add:"
            CoffeeMachine.State.FILL_CUPS -> "Write how many disposable cups you want to add:"
            else -> break
        })

        coffeeMachine.interact(readln())

        if (coffeeMachine.state == CoffeeMachine.State.MENU) {
            println()
        }
    }
}
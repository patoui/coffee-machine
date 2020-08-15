package machine;

import java.util.Scanner;


enum MachineState {
    START("start"),
    BUY("buy"),
    FILL("fill"),
    TAKE("take"),
    REMAINING("remaining"),
    EXIT("exit"),
    ESPRESSO("1"),
    LATTE("2"),
    CAPPUCCINO("3"),
    BACK("back");

    private String state;

    MachineState(String state) {
        this.state = state;
    }

    public static MachineState findByState(String state) {
        for (MachineState value : values()) {
            if (state.equals(value.state)) {
                return value;
            }
        }
        return null;
    }
}

public class CoffeeMachine {
    public int water; // ml
    public int milk; // ml
    public int coffee; // g
    public int cups;
    public int money;

    public int refillStep = 0;

    public MachineState state;

    public CoffeeMachine(
            MachineState initialState,
            int initialWater,
            int initialMilk,
            int initialCoffee,
            int initialCups,
            int initialMoney
    ) {
        this.state = initialState;
        this.water = initialWater;
        this.milk = initialMilk;
        this.coffee = initialCoffee;
        this.cups = initialCups;
        this.money = initialMoney;
    }

    public void processInput(String input) {
        this.state = this.refillStep > 0 ? MachineState.FILL : MachineState.findByState(input);
    }

    private void processRemainingAction() {
        System.out.println(
                "The coffee machine has:"
                        + "\n" + water + " of water"
                        + "\n" + milk + " of milk"
                        + "\n" + coffee + " of coffee beans"
                        + "\n" + cups + " of disposable cups"
                        + "\n$" + money + " of money"
        );
    }

    private boolean makeCoffee(int waterChange, int milkChange, int coffeeChange, int moneyChange) {
        boolean hasEnoughWater = (this.water + waterChange) >= 0;
        boolean hasEnoughMilk = (this.milk + milkChange) >= 0;
        boolean hasEnoughCoffee = (this.coffee + coffeeChange) >= 0;
        boolean hasEnoughCups = this.cups > 0;

        if (!hasEnoughWater) {
            System.out.println("Sorry, not enough water!");
            return false;
        } else if (!hasEnoughMilk) {
            System.out.println("Sorry, not enough milk!");
            return false;
        } else if (!hasEnoughCoffee) {
            System.out.println("Sorry, not enough coffee!");
            return false;
        } else if (!hasEnoughCups) {
            System.out.println("Sorry, not enough water!");
            return false;
        }

        System.out.println("I have enough resources, making you a coffee!");
        this.water += waterChange;
        this.milk += milkChange;
        this.coffee += coffeeChange;
        this.money += moneyChange;
        this.cups--;

        return true;
    }

    private void makeEspresso() {
        this.makeCoffee(-250, 0, -16, 4);
    }

    private void makeLatte() {
        this.makeCoffee(-350, -75, -20, 7);
    }

    private void makeCappuccino() {
        this.makeCoffee(-200, -100, -12, 6);
    }

    private void actionPrompt() {
        System.out.println("Write action (buy, fill, take, remaining, exit):");
    }

    private void processFillAction(String input) {
        this.refillStep++;
        int thingToAdd = 0;

        if (refillStep > 1) {
            thingToAdd = Integer.parseInt(input);
        }

        switch (this.refillStep) {
            case 1:
                System.out.println("Write how many ml of water do you want to add:");
                break;
            case 2:
                this.water += thingToAdd;
                System.out.println("Write how many ml of milk do you want to add:");
                break;
            case 3:
                this.milk += thingToAdd;
                System.out.println("Write how many grams of coffee beans do you want to add:");
                break;
            case 4:
                this.coffee += thingToAdd;
                System.out.println("Write how many disposable cups of coffee do you want to add:");
                break;
            case 5:
                this.cups += thingToAdd;
                this.refillStep = 0;
                break;
        }
    }

    private void processTakeAction() {
        System.out.println("I gave you $" + money);
        money = 0;
    }

    public static void main(String[] args) {
        CoffeeMachine machine = new CoffeeMachine(
                MachineState.START,
                400,
                540,
                120,
                9,
                550
        );
        Scanner scanner = new Scanner(System.in);
        String input = null;

        while (true) {
            switch (machine.state) {
                case BUY:
                    System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:");
                    break;
                case ESPRESSO:
                    machine.makeEspresso();
                    machine.actionPrompt();
                    break;
                case LATTE:
                    machine.makeLatte();
                    machine.actionPrompt();
                    break;
                case CAPPUCCINO:
                    machine.makeCappuccino();
                    machine.actionPrompt();
                    break;
                case FILL:
                    machine.processFillAction(input);
                    if (machine.refillStep == 0) {
                        machine.actionPrompt();
                    }
                    break;
                case TAKE:
                    machine.processTakeAction();
                    machine.actionPrompt();
                    break;
                case REMAINING:
                    machine.processRemainingAction();
                    machine.actionPrompt();
                    break;
                case EXIT:
                    System.exit(0);
                    break;
                case BACK:
                case START:
                default:
                    machine.actionPrompt();
                    break;
            }
            input = scanner.nextLine();
            machine.processInput(input);
        }
    }
}

package htw.berlin.prog2.ha1;

public class Calculator {

    private String screen = "0";
    private double latestValue;
    private String latestOperation = "";
    private boolean newInputExpected = false;

    public String readScreen() {
        return screen;
    }

    public void pressDigitKey(int digit) {
        if(digit > 9 || digit < 0) throw new IllegalArgumentException();

        if(screen.equals("0") || latestValue == Double.parseDouble(screen) || newInputExpected) {
            screen = "";
            newInputExpected = false;
        }

        screen = screen + digit;
    }

    public void pressClearKey() {
        screen = "0";
        latestOperation = "";
        latestValue = 0.0;
        newInputExpected = false;
    }

    public void pressBinaryOperationKey(String operation) {
        if (!latestOperation.isEmpty()) {
            pressEqualsKey();
        }
        latestValue = Double.parseDouble(screen);
        latestOperation = operation;
        newInputExpected = true;
    }

    public void pressUnaryOperationKey(String operation) {
        latestValue = Double.parseDouble(screen);
        latestOperation = operation;
        var result = switch(operation) {
            case "√" -> Math.sqrt(Double.parseDouble(screen));
            case "%" -> Double.parseDouble(screen) / 100;
            case "1/x" -> 1 / Double.parseDouble(screen);
            default -> throw new IllegalArgumentException();
        };
        screen = Double.toString(result);
        if(screen.equals("NaN")) screen = "Error";
        if(screen.contains(".") && screen.length() > 11) screen = screen.substring(0, 10);
    }

    public void pressDotKey() {
        if(!screen.contains(".")) screen = screen + ".";
    }

    public void pressNegativeKey() {
        screen = screen.startsWith("-") ? screen.substring(1) : "-" + screen;
    }

    public void pressEqualsKey() {
        if (latestOperation.isEmpty()) return;

        double currentValue = Double.parseDouble(screen);
        var result = switch(latestOperation) {
            case "+" -> latestValue + currentValue;
            case "-" -> latestValue - currentValue;
            case "x" -> latestValue * currentValue;
            case "/" -> latestValue / currentValue;
            default -> throw new IllegalArgumentException();
        };
        screen = Double.toString(result);
        if(screen.equals("Infinity")) screen = "Error";
        if(screen.endsWith(".0")) screen = screen.substring(0,screen.length()-2);
        if(screen.contains(".") && screen.length() > 11) screen = screen.substring(0, 10);

        latestValue = result;
        newInputExpected = true;
    }
}
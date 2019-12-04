
package monopoly;

class payFeeCard implements Card {
    private int amount;

    public AddMoney(double amount, String text) {
        super(text);
        this.amount = amount;
    }

    public void effect(Players ps, int result) {
        ps.current().deductMoney(amount);
    }
}

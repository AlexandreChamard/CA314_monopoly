
package monopoly;

class PayPlayerCard implements Card {
    public int amount;

    public AddMoney(double amount, String text) {
        super(text);
        this.amount = amount;
    }

    void effect(Players ps, int result) {
        ps.current().addMoney(amount);
    }
}

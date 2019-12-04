
package monopoly;

class PayPlayerCard implements Card {
    public int amount;

    public PayPlayerCard(int amount, String text) {
        // super(text);
        this.amount = amount;
    }

    public void doEffect(Player p) {
        // ps.current().addMoney(amount);
    }
}

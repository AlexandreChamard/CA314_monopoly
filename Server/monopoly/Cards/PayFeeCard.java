
package monopoly;

class payFeeCard implements Card {
    private int amount;

    public payFeeCard(int amount, String text) {
        // super(text);
        this.amount = amount;
    }

    public void doEffect(Player p) {
        // ps.current().deductMoney(amount);
    }
}


package monopoly;

class GoToLocationCard implements Card {
    private int destination;

    public GoToLocationCard(int destination, String text) {
        // super(text);
        this.destination = destination;
    }

    public void doEffect(Player p) {
        // ps.current().moveTo(destination);
        // ps.current().getSlot().action(ps, result);
    }
}


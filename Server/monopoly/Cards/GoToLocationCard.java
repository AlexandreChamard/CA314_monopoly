
package monopoly;

class GoToLocationCard implements Card {
    
    private int destination;

    public GoToLocation(int destination, String text) {
        super(text);
        this.destination = destination;
    }

    public void effect(Players ps, int result) {
        ps.current().moveTo(destination);
        ps.current().getSlot().action(ps, result);
    }
}


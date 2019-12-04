
package monopoly;

class GoToJailCard implements Card {

    public GoToJailCard(int destination, String text) {
        super(destination, text);
    }

    public void effect(Players ps, int result) {
        super.effect(ps, result);
        ps.current().imprison();
    }
}


package monopoly;

interface Card {
    /* Depending on the description of the card does different thing, depending on what kind of cards we want to implement we’ll need functions for each card eg. Being sent to jail. */
    public void doEffect(Player p) throws ErrorState;
}

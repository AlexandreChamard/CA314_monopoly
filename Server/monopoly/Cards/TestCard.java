
package monopoly;

class TestCard implements Card {
    public void doEffect(Gameplate game, Player p) throws ErrorState {
        Master.getInstance().getAPI().send(p, new ErrorState(101, "test"));
    }
};
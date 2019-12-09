
package monopoly;

class TurnRule implements Rule {
    public void apply(Gameplate game, Player p) {
        PlayerInfos infos = game.infos.get(p);

        if (infos.remainingJailTurn == 0) { // not in prison
            int[] dice = game.dice.roll();
            /** @notify player must roll dice */
            game.advance(p, dice[0] + dice[1]);
            game.applySlotRule(p);
        } else {
            game.applyRule("prison", p);
        }
    }
}

class PrisonRule implements Rule {
    public void apply(Gameplate game, Player p) {
        PlayerInfos infos = game.infos.get(p);
        /** @notify player must chose pay or roll */
        int[] dice = game.dice.roll();
        switch (dice.length) {
            case 0:
                infos.remainingJailTurn = 0;
                game.broadcast(new ErrorState(108, Integer.toString(infos.id)));
                game.applyRule("turn", p);
                break;
            case 2:
                if (dice[0] == dice[1]) {
                    infos.remainingJailTurn = 0;
                    game.broadcast(new ErrorState(108, Integer.toString(infos.id)));
                } else {
                    --infos.remainingJailTurn;
                }
                break;
            default:
                /** interal error */
                break;
        }
    }
}
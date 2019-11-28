
package monopoly;

import java.util.Random;

class Dice
{
    Random rand = new Random();

    public int rollDice()
    {
        return rand.nextInt(6) + 1;
    }
}

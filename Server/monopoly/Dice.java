
package monopoly;

import java.util.Random;

class Dice
{
    Random rand = new Random();

    public int[] roll()
    {
        int[] dice = new int[2];
        dice[0] = rand.nextInt(6) + 1;
        dice[1] = rand.nextInt(6) + 1;
        return dice;
    }
}

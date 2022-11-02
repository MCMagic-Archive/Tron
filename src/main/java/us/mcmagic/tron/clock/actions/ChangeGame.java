package us.mcmagic.tron.clock.actions;

import us.mcmagic.tron.game.TronGame;

/**
 * Created by Marc on 1/31/16
 */
public class ChangeGame extends GameAction {
    private final TronGame game;

    public ChangeGame(long time, TronGame game) {
        super(time);
        this.game = game;
    }

    @Override
    public void playAction() {
        game.start();
    }
}
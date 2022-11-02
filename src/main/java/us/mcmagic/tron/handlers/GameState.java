package us.mcmagic.tron.handlers;

/**
 * Created by Marc on 12/26/15
 */
public enum GameState {
    SERVER_STARTING, IN_LOBBY, LASERTAG, BETWEEN, DISCWARS, LIGHTCYCLES, POSTGAME;

    private static GameState currentState = SERVER_STARTING;

    public static void setState(GameState state) {
        currentState = state;
    }

    public static boolean isState(GameState state) {
        return currentState.equals(state);
    }

    public static GameState getState() {
        return currentState;
    }
}
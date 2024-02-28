package view;

import model.GameStateLogic;

import javax.swing.*;

public class TicTacViewParent extends JFrame {

    protected GameStateLogic gameState;
    protected ScoreKeeper scoreTracker;

    /**
     * Restore the deserialized elements of the current game.
     *
     * @param gameState    The deserialized game state.
     * @param scoreTracker The deserialized score tracker.
     */
    public void deSerializeGame(GameStateLogic gameState, ScoreKeeper scoreTracker) {
        this.gameState = gameState;
        this.scoreTracker = scoreTracker;
    }

    /**
     * Get the game state.
     *
     * @return The game state.
     */
    public GameStateLogic getGameState() {
        return gameState;
    }

    /**
     * Get the score tracker.
     *
     * @return The score tracker.
     */
    public ScoreKeeper getScoreTracker() {
        return scoreTracker;
    }
}
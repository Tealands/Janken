package com.example.janken;

import org.junit.Test;
import static org.junit.Assert.*;

import com.example.janken.model.JankenGame;

public class JankenGameTest {

    @Test
    public void testJudgeWin() {
        JankenGame game = new JankenGame();
        assertEquals(JankenGame.Result.WIN, game.judge(JankenGame.Hand.ROCK, JankenGame.Hand.SCISSORS));
        assertEquals(JankenGame.Result.WIN, game.judge(JankenGame.Hand.SCISSORS, JankenGame.Hand.PAPER));
        assertEquals(JankenGame.Result.WIN, game.judge(JankenGame.Hand.PAPER, JankenGame.Hand.ROCK));
    }

    @Test
    public void testJudgeLose() {
        JankenGame game = new JankenGame();
        assertEquals(JankenGame.Result.LOSE, game.judge(JankenGame.Hand.SCISSORS, JankenGame.Hand.ROCK));
        assertEquals(JankenGame.Result.LOSE, game.judge(JankenGame.Hand.PAPER, JankenGame.Hand.SCISSORS));
        assertEquals(JankenGame.Result.LOSE, game.judge(JankenGame.Hand.ROCK, JankenGame.Hand.PAPER));
    }

    @Test
    public void testJudgeDraw() {
        JankenGame game = new JankenGame();
        assertEquals(JankenGame.Result.DRAW, game.judge(JankenGame.Hand.ROCK, JankenGame.Hand.ROCK));
        assertEquals(JankenGame.Result.DRAW, game.judge(JankenGame.Hand.SCISSORS, JankenGame.Hand.SCISSORS));
        assertEquals(JankenGame.Result.DRAW, game.judge(JankenGame.Hand.PAPER, JankenGame.Hand.PAPER));
    }

    @Test
    public void testScoreAndGameOver() {
        JankenGame game = new JankenGame();
        assertFalse(game.isGameOver());
        game.applyResult(JankenGame.Result.WIN);
        game.applyResult(JankenGame.Result.WIN);
        game.applyResult(JankenGame.Result.WIN);
        assertTrue(game.isGameOver());
        assertTrue(game.isPlayerWinner());
    }

    @Test
    public void testReset() {
        JankenGame game = new JankenGame();
        game.applyResult(JankenGame.Result.WIN);
        game.reset();
        assertEquals(0, game.getPlayerScore());
        assertEquals(0, game.getCpuScore());
    }
}

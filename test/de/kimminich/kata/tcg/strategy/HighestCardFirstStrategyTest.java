package de.kimminich.kata.tcg.strategy;

import de.kimminich.kata.tcg.Card;
import de.kimminich.kata.tcg.syntactic.CardSugar;
import org.junit.Test;

import java.util.Optional;

import static de.kimminich.kata.tcg.syntactic.CardSugar.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class HighestCardFirstStrategyTest {

    Strategy strategy;

    @Test
    public void shouldPlayCardsInOrderFromHighToLow() {
        strategy = new HighestCardFirstStrategy();

        assertThat(strategy.nextCard(10, Card.list(0, 2, 3, 8, 9)), is(card(9)));
        assertThat(strategy.nextCard(1, Card.list(0, 2, 3, 8)), is(card(0)));
    }

    @Test
    public void strategyShouldReturnNoCardIfInsufficientManaForAnyHandCard() {
        strategy = new HighestCardFirstStrategy();

        assertThat(strategy.nextCard(1, Card.list(2, 3, 8)), is(noCard()));
    }

}

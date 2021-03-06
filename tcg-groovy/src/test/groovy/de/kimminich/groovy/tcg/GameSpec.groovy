package de.kimminich.groovy.tcg

import spock.lang.Specification
import spock.lang.Unroll

class GameSpec extends Specification {

    def Game game;

    def "game should have two players"() {
        setup:
        game = new Game()

        expect:
        game.activePlayer != null
        game.opponentPlayer != null
    }

    def "starting player hold 3 cards and opponent holds 4 cards in his starting hand"() {
        given:
        game = new Game()

        when:
        game.start()

        then:
        game.activePlayer.hand.size() == 3
        game.activePlayer.deck.size() == 17
        and:
        game.opponentPlayer.hand.size() == 4
        game.opponentPlayer.deck.size() == 16
    }

    def "every turn the active player changes"() {
        given:
        game = new Game()
        Player previouslyInactivePlayer = game.opponentPlayer

        when:
        game.beginTurn()

        then:
        game.activePlayer == previouslyInactivePlayer
    }

    @Unroll("receiving +1 max. mana to #currentMaxMana current max. mana gives the player #expectedMaxMana max. mana and replenishes mana also to #expectedMaxMana")
    def "every turn the active player receives 1 max. mana and his mana is fully replenished"() {
        given:
        game = new Game()
        game.opponentPlayer = new Player(mana: 0, maxMana: currentMaxMana)

        when:
        game.beginTurn()

        then:
        game.activePlayer.maxMana == expectedMaxMana
        game.activePlayer.mana == expectedMaxMana

        where:
        currentMaxMana | expectedMaxMana
        0              | 1
        1              | 2
        2              | 3
        3              | 4
        4              | 5
        5              | 6
        6              | 7
        7              | 8
        8              | 9
        9              | 10
    }

    def "max. mana is capped at a value of 10"() {
        given:
        game = new Game()
        game.opponentPlayer = new Player(maxMana: 10)

        when:
        game.beginTurn()

        then:
        game.activePlayer.maxMana == 10
    }

    def "every turn the active player draws a card from his deck"() {
        given:
        game = new Game()
        game.opponentPlayer = new Player(deck: [3], hand: [1, 2])

        when:
        game.beginTurn()

        then:
        game.activePlayer.deck == []
        game.activePlayer.hand == [1, 2, 3]
    }
}

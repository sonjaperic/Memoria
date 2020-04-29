import javax.swing.*;
import java.util.Arrays;

public class GameController {
    private BoardGUI boardGUI;
    private LogInGUI logInGUI;
    private MenuGUI menuGUI;
    private User[] multiPlayer = new User[2];

    private Card selectedCard;
    private Card[] pairOfCards = new Card[2];
    private String player2;

    public GameController() {
        logInGUI = new LogInGUI(this, "Player One ");
    }

    /**
     * Vänder upp valt kort.
     * TODO: Snygga till och eventuellt skapa en par-klass.
     */
    public void doTurn(Card card) {
        selectedCard = card;
        if (pairOfCards[0] == null) {
            pairOfCards[0] = selectedCard;
            pairOfCards[0].revealSymbol();
        }

        if ((pairOfCards[0] != null) && (pairOfCards[0] != selectedCard) && (pairOfCards[1] == null)) {
            pairOfCards[1] = selectedCard;
            pairOfCards[1].revealSymbol();
            boardGUI.getTimer().start();
        }
    }

    /**
     * Kontrollera om paret matchar.
     * Visa matchning och ta korten ur spel, eller vänd tillbaks kort.
     * TODO: Snygga till och eventuellt par-klass. Snygga till villkoren.
     * TODO: Ta bort andra vilkoret i if-satsen
     * TODO: Vad ska hända efter isGamewon?
     */
    public void checkCards() {
        if ((pairOfCards[0].getPathSymbol().substring(0, 9).equals(pairOfCards[1].getPathSymbol().substring(0, 9))) &&
                !(pairOfCards[0].getPathSymbol().equals(pairOfCards[1].getPathSymbol()))) {
            // Matcha paret.
            pairOfCards[0].setEnabled(false); //disables the button
            pairOfCards[1].setEnabled(false);
            pairOfCards[0].setMatched(true); //flags the button as having been matched
            pairOfCards[1].setMatched(true);
            if (isGameWon()) {
                JOptionPane.showMessageDialog(boardGUI, "You win!");
                boardGUI.dispose();
                boardGUI = new BoardGUI(this);
            }
        } else {
            // Dölj paret
            for (Card card : pairOfCards) {
                card.hideSymbol();
            }
        }
        Arrays.fill(pairOfCards, null);     // Töm paret av kort.
    }

    /**
     * Kontrollerar om spelet är slut.
     * TODO: Lös det här snyggare.
     */
    private boolean isGameWon() {
        for (Card card : boardGUI.getCards()) {
            if (!card.isMatched()) {
                return false;
            }
        }
        return true;
    }

    LogInGUI logInPlayer2;
    /**
     * Go game view.
     */
    public void switchGUI() {
        logInPlayer2 = new LogInGUI(this, "Player Two");

//        boardGUI = new BoardGUI(this);

//        boardGUI.getPnlPlayer1().setBorder(BorderFactory.createTitledBorder(multiPlayer[0].getUserName()));
//        boardGUI.getPnlPlayer2().setBorder(BorderFactory.createTitledBorder(multiPlayer[1].getUserName()));
    }

//    public void loginPlayer() {
//        String name = logInGUI.getTxtUsername().getText();
//        multiPlayer[0] = new User(name);
//        JOptionPane.showMessageDialog(null, "Välkommen spelare1: " + name);
//        new MenuGUI(this);
//    }

    public void createUser() {
        if (multiPlayer[0] == null) {
            String name = logInGUI.getTxtUsername().getText();
            multiPlayer[0] = new User(name);
            JOptionPane.showMessageDialog(null, "Välkommen spelare1: " + name);
            new MenuGUI(this);
        }
        else {
            String name = logInPlayer2.getTxtUsername().getText();
            multiPlayer[1] = new User(name);
            JOptionPane.showMessageDialog(null, "Välkommen spelare 2: " + name);
            boardGUI = new BoardGUI(this);
            boardGUI.getPnlPlayer1().setBorder(BorderFactory.createTitledBorder(multiPlayer[0].getUserName()));
            boardGUI.getPnlPlayer2().setBorder(BorderFactory.createTitledBorder(multiPlayer[1].getUserName()));
//            player2 = JOptionPane.showInputDialog("Ange spelare 2's namn");
//            multiPlayer[1] = new User(player2);
        }
    }
}

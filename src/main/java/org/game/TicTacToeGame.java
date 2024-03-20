package org.game;

import org.game.engine.*;


public class TicTacToeGame extends Game {

    private final int[][] model = new int[3][3];
    private boolean isGameStopped;
    private int currentPlayer;

    @Override
    public void initialize() {
        setScreenSize(3, 3);
        startGame();
        updateView();
    }

    public void startGame() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                model[i][j] = 0;
            }
        }
        isGameStopped = false;
        currentPlayer = 1;
    }

    public void updateCellView(int x, int y, int value) {
        if (value == 0) {
            setCellValue(x, y, " ");
        } else if (value == 1) {
            setCellValueEx(x, y, Color.WHITE, "X", Color.RED);
        } else setCellValueEx(x, y, Color.WHITE, "O", Color.BLUE);
    }

    public void updateView() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                updateCellView(i, j, model[i][j]);
            }
        }
    }

    public void onKeyPress(Key key) {
        if (key == Key.SPACE && isGameStopped) {
            startGame();
            updateView();
        } else if (key == Key.ESCAPE) {
            startGame();
            updateView();
        }
    }

    public void onMouseLeftClick(int x, int y) {
        if (model[x][y] != 0 || isGameStopped) return;
        setSignAndCheck(x, y);
        currentPlayer = 3 - currentPlayer;
        if (isGameStopped) return;
        computerTurn();
        currentPlayer = 3 - currentPlayer;
    }

    public void setSignAndCheck(int x, int y) {
        model[x][y] = currentPlayer;
        updateView();
        if (checkWin(x, y, currentPlayer)) {
            isGameStopped = true;
            if (currentPlayer == 1) {
                showMessageDialog(Color.NONE, "You Win!", Color.GREEN, 75);
            } else showMessageDialog(Color.NONE, "Game Over", Color.RED, 75);
        }
        if (!hasEmptyCell()) {
            isGameStopped = true;
            showMessageDialog(Color.NONE, "Draw!", Color.BLACK, 75);
        }
    }

    public void computerTurn() {
        if (model[1][1] == 0) {
            setSignAndCheck(1, 1);
            return;
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (model[i][j] == 0 && checkFutureWin(i, j, currentPlayer)) {
                    setSignAndCheck(i, j);
                    return;
                }
            }
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (model[i][j] == 0 && checkFutureWin(i, j, 3-currentPlayer)) {
                    setSignAndCheck(i, j);
                    return;
                }
            }
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (model[i][j] == 0) {
                    setSignAndCheck(i, j);
                    return;
                }
            }
        }
    }

    public boolean checkFutureWin(int x, int y, int n) {
        int tmp = model[x][y];
        model[x][y] = n;
        if (checkWin(x, y, n)) {
            model[x][y] = tmp;
            return true;
        } else {
            model[x][y] = tmp;
            return false;
        }
    }

    public boolean hasEmptyCell() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (model[i][j] == 0) return true;
            }
        }
        return false;
    }

    public boolean checkWin(int x, int y, int n) {
//        for (int i = 0; i < 3; i++) {
//            int vertically = 0;
//            int horizontally = 0;
//            for (int j = 0; j < 3; j++) {
//                if (model[i][j] == n) {
//                    vertically++;
//                }
//                if (model[j][i] == n) {
//                    horizontally++;
//                }
//            }
//            if (vertically == 3 || horizontally == 3) return true;
//        }
//        return (model[0][0] == n && model[1][1] == n && model[2][2] == n) ||
//                (model[2][0] == n && model[1][1] == n && model[0][2] == n);

        if (model[x][0] == n && model[x][1] == n && model[x][2] == n)
            return true;
        if (model[0][y] == n && model[1][y] == n && model[2][y] == n)
            return true;
        if (model[0][0] == n && model[1][1] == n && model[2][2] == n)
            return true;
        return model[0][2] == n && model[1][1] == n && model[2][0] == n;
    }
}

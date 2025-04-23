import java.util.*;

public class PuzzleGame {
    private String[][] grid = new String[3][3];
    private int moveCount = 0;

    public PuzzleGame() {
        shuffleTiles();
    }

    public void shuffleTiles() {
        // Start from the solved state
        String[][] solved = {
            {"1", "2", "3"},
            {"4", "5", "6"},
            {"7", "8", " "}
        };
    
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                grid[i][j] = solved[i][j];
    
        // Apply N random valid moves from the goal state
        Random rand = new Random();
        int emptyI = 2, emptyJ = 2; // Empty tile starts at bottom right
        int moves = 30; // You can reduce this to make it even easier
    
        do {
            for (int i = 0; i < 3; i++)
                for (int j = 0; j < 3; j++)
                    grid[i][j] = solved[i][j];
    
            int movesLeft = moves;
            while (movesLeft-- > 0) {
                int[][] directions = {{0,1}, {1,0}, {0,-1}, {-1,0}};
                List<int[]> validMoves = new ArrayList<>();
        
                for (int[] dir : directions) {
                    int ni = emptyI + dir[0];
                    int nj = emptyJ + dir[1];
                    if (ni >= 0 && ni < 3 && nj >= 0 && nj < 3)
                        validMoves.add(new int[]{ni, nj});
                }
        
                int[] chosen = validMoves.get(rand.nextInt(validMoves.size()));
                int ni = chosen[0], nj = chosen[1];
        
                // Swap empty with chosen
                grid[emptyI][emptyJ] = grid[ni][nj];
                grid[ni][nj] = " ";
        
                // Update empty position
                emptyI = ni;
                emptyJ = nj;
            }
        } while (!isSolvable(Arrays.asList(grid[0][0], grid[0][1], grid[0][2], grid[1][0], grid[1][1], grid[1][2], grid[2][0], grid[2][1], grid[2][2])));
    
        moveCount = 0;
    }
    
    public boolean moveTile(int i, int j) {
        int[][] directions = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        for (int[] d : directions) {
            int ni = i + d[0], nj = j + d[1];
            if (ni >= 0 && ni < 3 && nj >= 0 && nj < 3 && grid[ni][nj].equals(" ")) {
                grid[ni][nj] = grid[i][j];
                grid[i][j] = " ";
                moveCount++;
                return true;
            }
        }
        return false;
    }

    public boolean checkWin() {
        String[] win = {"1", "2", "3", "4", "5", "6", "7", "8", " "};
        int idx = 0;
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (!grid[i][j].equals(win[idx++])) return false;
        return true;
    }

    public String[][] getGrid() {
        return grid;
    }

    public int getMoveCount() {
        return moveCount;
    }

    private boolean isSolvable(List<String> tiles) {
        int invCount = 0;
        for (int i = 0; i < tiles.size() - 1; i++) {
            for (int j = i + 1; j < tiles.size(); j++) {
                String a = tiles.get(i);
                String b = tiles.get(j);
                if (!a.equals(" ") && !b.equals(" ") && Integer.parseInt(a) > Integer.parseInt(b)) {
                    invCount++;
                }
            }
        }
        return invCount % 2 == 0;
    }
}

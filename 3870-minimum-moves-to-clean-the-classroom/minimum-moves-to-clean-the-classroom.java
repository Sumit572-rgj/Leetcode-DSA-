import java.util.*;

class Solution {

    static class State {
        int r, c;
        int mask;
        int energy;
        int moves;

        State(int r, int c, int mask, int energy, int moves) {
            this.r = r;
            this.c = c;
            this.mask = mask;
            this.energy = energy;
            this.moves = moves;
        }
    }

    public int minMoves(String[] classroom, int energy) {

        int m = classroom.length;
        int n = classroom[0].length();

        int startR = -1;
        int startC = -1;
        int[][] litterIndex = new int[m][n];

        for (int[] row : litterIndex) {
            Arrays.fill(row, -1);
        }

        int litterCount = 0;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {

                char ch = classroom[i].charAt(j);

                if (ch == 'S') {
                    startR = i;
                    startC = j;
                }

                if (ch == 'L') {
                    litterIndex[i][j] = litterCount++;
                }
            }
        }

        if (litterCount == 0) {
            return 0;
        }

        int targetMask = (1 << litterCount) - 1;
        int totalCells = m * n;

        BitSet[] visited = new BitSet[(1 << litterCount) * totalCells];

        Queue<State> queue = new ArrayDeque<>();

        int startPos = startR * n + startC;
        int startIndex = startPos;

        visited[startIndex] = new BitSet(energy + 1);
        visited[startIndex].set(energy);

        queue.offer(
            new State(startR, startC, 0, energy, 0)
        );

        int[][] dirs = {
            {1, 0},
            {-1, 0},
            {0, 1},
            {0, -1}
        };

        while (!queue.isEmpty()) {

            State cur = queue.poll();

         
            if (cur.mask == targetMask) {
                return cur.moves;
            }
            if (cur.energy == 0 &&
                classroom[cur.r].charAt(cur.c) != 'R') {
                continue;
            }

          
            int currentEnergy = cur.energy;

            if (classroom[cur.r].charAt(cur.c) == 'R') {
                currentEnergy = energy;
            }

            for (int[] dir : dirs) {

                int nr = cur.r + dir[0];
                int nc = cur.c + dir[1];

                if (nr < 0 || nr >= m ||
                    nc < 0 || nc >= n) {
                    continue;
                }

           
                if (classroom[nr].charAt(nc) == 'X') {
                    continue;
                }

               
                if (currentEnergy <= 0) {
                    continue;
                }

                int newEnergy = currentEnergy - 1;

                int newMask = cur.mask;
                if (classroom[nr].charAt(nc) == 'L') {

                    int index = litterIndex[nr][nc];

                    newMask |= (1 << index);
                }
                if (classroom[nr].charAt(nc) == 'R') {
                    newEnergy = energy;
                }

                int position = nr * n + nc;

                int stateIndex =
                    newMask * totalCells + position;

                if (visited[stateIndex] == null) {
                    visited[stateIndex] =
                        new BitSet(energy + 1);
                }

                if (visited[stateIndex].get(newEnergy)) {
                    continue;
                }

                visited[stateIndex].set(newEnergy);

                queue.offer(
                    new State(
                        nr,
                        nc,
                        newMask,
                        newEnergy,
                        cur.moves + 1
                    )
                );
            }
        }

        return -1;
    }
}
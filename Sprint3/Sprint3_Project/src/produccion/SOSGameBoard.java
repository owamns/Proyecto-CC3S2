package produccion;

public class SOSGameBoard {
        public enum contentBoxes {EMPTY, LETTER_S, LETTER_O}
        private int squaresPerSide;
        private Player[] players = new Player[2];
        private char turn;
        private contentBoxes[][] grid;
        public SOSGameBoard(){
            squaresPerSide = 3;
            grid = new contentBoxes[squaresPerSide][squaresPerSide];
            initBoard();
        }
        public SOSGameBoard(int squaresPerSide){
            this.squaresPerSide = squaresPerSide;
            grid = new contentBoxes[squaresPerSide][squaresPerSide];
            initBoard();
        }
        public void initBoard(){
            for (int row = 0; row < squaresPerSide; row++) {
                for (int column = 0; column < squaresPerSide; column++) {
                    grid[row][column] = contentBoxes.EMPTY;
                }
            }
            turn = 'B';
            players[0] = new Player("Blue player");
            players[1] = new Player("Red player");
        }
        public int getSquaresPerSide() { return squaresPerSide; };
        public contentBoxes getCell(int row, int column){
            if ( row >= 0 && row < squaresPerSide && column >= 0 && column < squaresPerSide ){
                return grid[row][column];
            }
            return null;
        }
        public char getTurn(){
            return turn;
        }
        public void makePlay(int row, int column, contentBoxes chosen){
            if ( row >= 0 && row < squaresPerSide && column >= 0 && column < squaresPerSide ){
                grid[row][column] = chosen;
            }
            else {
                throw new IllegalArgumentException("El argumento debe estar dentro de los limites del tablero");
            }
            turn = (getTurn()=='B')? 'R':'B';
        }

        public int[] positionSOS(int row, int column, contentBoxes chosen) {
            int[][] around = new int[][]{{-1, -1}, {0, -1}, {1, -1}, {-1, 0}, {1, 0}, {-1, 1}, {0, 1}, {1, 1}};
            int[] positions = new int[4];

            if (chosen == contentBoxes.LETTER_S) {
                for (int i = 0; i < 8; i++) {
                    if (getCell(row + around[i][0], column + around[i][1]) == contentBoxes.LETTER_O && getCell(row + 2 * around[i][0], column + 2 * around[i][1]) == contentBoxes.LETTER_S) {
                        positions[0] = row;
                        positions[1] = column;
                        positions[2] = row + 2 * around[i][0];
                        positions[3] = column + 2 * around[i][1];
                    }
                }
            } else if (chosen == contentBoxes.LETTER_O) {
                for (int i = 0; i < 4; i++) {
                    if (getCell(row + around[i][0], column + around[i][1]) == contentBoxes.LETTER_S && getCell(row - around[i][0], column - around[i][1]) == contentBoxes.LETTER_S) {
                        positions[0] = row - around[i][0];
                        positions[1] = column - around[i][1];
                        positions[2] = row + around[i][0];
                        positions[3] = column + around[i][1];
                    }
                }
            }
            return positions;
        }
        public Player[] getPlayers() {
            return players;
        }
        public Player getWinner(){
            if(players[0].getScore()>players[1].getScore()){
                return players[0];
            } else if (players[0].getScore() < players[1].getScore()) {
                return players[1];
            }
            return null;
        }
        public void setTurn(char turn) {
            this.turn = turn;
        }
        public boolean isBoardFull(){
            for (int row = 0; row < squaresPerSide; row++){
                for (int column = 0; column < squaresPerSide; column++){
                    if (getCell(row, column) == contentBoxes.EMPTY) return false;
                }
            }
            return true;
        }
        public int howManySOS(int row, int column, contentBoxes chosen){
            int[][] around = new int[][]{ {-1,-1} , {0,-1} , {1,-1} , {-1,0} , {1,0} , {-1,1} , {0,1} , {1,1} } ;
            int points = 0;
            if ( chosen == contentBoxes.LETTER_S ){
                for( int i = 0; i < 8; i++ ){
                    if( getCell(row + around[i][0],column + around[i][1]) == contentBoxes.LETTER_O && getCell(row + 2*around[i][0],column + 2*around[i][1]) == contentBoxes.LETTER_S ){
                        points++;
                    }
                }
            }
            else if ( chosen == contentBoxes.LETTER_O ){
                for( int i = 0; i < 4; i++ ){
                    if( getCell(row + around[i][0],column + around[i][1]) == contentBoxes.LETTER_S && getCell(row - around[i][0],column - around[i][1]) == contentBoxes.LETTER_S ){
                        points++;
                    }
                }
            }
            return points;
        }
        class Player{
            private String name;
            private int score;
            private char letter;
            public Player(String name){
                this.name = name;
                score = 0;
                letter = 'S';
            }
            public void setLetter(char letter) {
                this.letter = letter;
            }
            public char getLetter() {
                return letter;
            }
            public int getScore(){
                return score;
            }
            public void increaseScore(int points){
                score+=points;
            }
            public String getName() {
                return name;
            }
            public void setScore(int score) {
                this.score = score;
            }
        }
    }

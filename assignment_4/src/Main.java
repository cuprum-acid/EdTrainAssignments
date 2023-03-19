/*
 * This program is the chess game with custom rules.
 * User inserts the board size and the number of pieces.
 * The program calculates the number of possible moves and captures for each piece.
 * The program follows the UML class diagram.
 * By: Evgeny Bobkunov
 */

// Importing the necessary libraries
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.util.Scanner;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * The main class, which contains the main methods and runs the program.
 * @author Evgeny Bobkunov
 */
public final class Main {
    private Main() { }
    /**
     *  Creating a new chessBoard object
     *  of class Board, which is a chess board for playing chess.
     */
    private static Board chessBoard;
    /**
     * A constant showing the minimum allowed size of the chessboard.
     */
    public static final int MIN_BOARD_SIZE = 3;
    /**
     * A constant showing the maximum allowed size of the chessboard.
     */
    public static final int MAX_BOARD_SIZE = 1000;
    /**
     * A constant showing the index of coordinate of Y-axis.
     */
    public static final int INDEX_OF_THE_Y_COORDINATE = 3;


    /**
     * The main method of the program,
     * which starts the execution of the program.
     * @param args The command line arguments. Standard for main method.
     * @throws IOException If an input or output exception occurred.
     */
    public static void main(String[] args) throws IOException {
        // Creating a new main object of class Main.
        Main main = new Main();
        // Creating a file reader object to read input text file.
        FileReader fr = new FileReader("src\\input.txt");
        // Reading the input text file for the second time in the different variable.
        FileReader fr2 = new FileReader("src\\input.txt");
        // Creating a buffered reader 1 object to read the input text file.
        BufferedReader reader = new BufferedReader(fr);
        BufferedReader br = new BufferedReader(fr2);
        // Creating a new scanner 1 object to read the input text file.
        Scanner scan = new Scanner(fr);
        Scanner sc = new Scanner(fr2);
        // Creating file writer object to write the output text file.
        FileWriter writer = new FileWriter("src\\output.txt", false);
        // Read line by line into String line.
        String line = reader.readLine();
        String checkForLines = br.readLine();
        // Use try-catch block to catch the exceptions.
        try {
            // Creating a new chessBoard object of class Board with the size, which is the first number in the file.
            chessBoard = main.new Board(Integer.parseInt(line));
            // Check if the board size is in the allowed range.
            if (chessBoard.size < MIN_BOARD_SIZE || chessBoard.size > MAX_BOARD_SIZE) {
                // If the board size is not in the allowed range, throw an exception.
                throw new InvalidBoardSizeException();
            }
            chessBoard.positionToPieces = new HashMap<>();
            // Get the number of pieces from the second line in the file.
            int piecesCount = Integer.parseInt(reader.readLine());
            // Check if the number of pieces is in the allowed range.
            if (piecesCount < 2 || piecesCount > chessBoard.size * chessBoard.size) {
                // If the number of pieces is not in the allowed range, throw an exception.
                throw new InvalidNumberOfPiecesException();
            }
            // Creating a new array of pieces.
            String[][] pieceDataParts = new String[0][0];
            ChessPiece chessPiece;
            // Number of lines in the file. -1 because the first line is the board size.
            int numberOfLines = -1; // And the second line is the number of pieces.
            // Count the number of lines in the file.
            while ((checkForLines = br.readLine()) != null) {
                numberOfLines++;
            }
            // Check if the number of lines in the file is equal to the number of pieces.
                if (numberOfLines != piecesCount) {
                // If the number of lines in the file is not equal to the number of pieces, throw an exception.
                throw new InvalidNumberOfPiecesException();
            }
            for (int i = 0; i < piecesCount; i++) {
                String pieceData = reader.readLine(); // Read the next line.
                pieceDataParts = Arrays.copyOf(pieceDataParts, pieceDataParts.length + 1);
                pieceDataParts[pieceDataParts.length - 1] = pieceData.split(" "); // Split the line into parts.
                pieceDataParts[i][0] = pieceData.split(" ")[0]; // Get the piece type.
                if (!pieceDataParts[i][0].equals("King") && !pieceDataParts[i][0].equals("Queen")
                        && !pieceDataParts[i][0].equals("Rook")
                        && !pieceDataParts[i][0].equals("Bishop")
                        && !pieceDataParts[i][0].equals("Knight")
                        && !pieceDataParts[i][0].equals("Pawn")) {
                    throw new InvalidPieceNameException(); // If the piece type is not valid, throw an exception.
                }
                pieceDataParts[i][1] = pieceData.split(" ")[1]; // Get the piece color.
                if (!pieceDataParts[i][1].equals("White") && !pieceDataParts[i][1].equals("Black")) {
                    throw new InvalidPieceColorException(); // If the piece color is not valid, throw an exception.
                }
                pieceDataParts[i][2] = pieceData.split(" ")[2]; // Get the piece X coordinate.
                if (Integer.parseInt(pieceDataParts[i][2]) < 1 // It must be in the allowed range.
                        || Integer.parseInt(pieceDataParts[i][2]) > chessBoard.size) {
                    throw new InvalidPiecePositionException();
                }
                pieceDataParts[i][INDEX_OF_THE_Y_COORDINATE] = pieceData.split(" ")[INDEX_OF_THE_Y_COORDINATE]; //getY
                if (Integer.parseInt(pieceDataParts[i][INDEX_OF_THE_Y_COORDINATE]) < 1
                        || Integer.parseInt(pieceDataParts[i][INDEX_OF_THE_Y_COORDINATE]) > chessBoard.size) {
                    throw new InvalidPiecePositionException();
                }
                PiecePosition piecePosition = main.new PiecePosition(Integer.parseInt(pieceDataParts[i][2]),
                        Integer.parseInt(pieceDataParts[i][INDEX_OF_THE_Y_COORDINATE]));
                PieceColor pieceColor = PieceColor.parse((pieceDataParts[i][1]));
                switch (pieceDataParts[i][0]) { // Depending on the piece type, create a new piece.
                    case "King": // If the piece type is King.
                        chessPiece = main.new King(piecePosition, pieceColor); // Create a new King piece.
                        chessBoard.addPiece(chessPiece); // Add the piece to the board.
                        break; // Break the switch.
                    case "Queen": // If the piece type is Queen.
                        chessPiece = main.new Queen(piecePosition, pieceColor); // Create a new Queen piece.
                        chessBoard.addPiece(chessPiece); // Add the piece to the board.
                        break; // Break the switch.
                    case "Rook": // If the piece type is Rook.
                        chessPiece = main.new Rook(piecePosition, pieceColor); // Create a new Rook piece.
                        chessBoard.addPiece(chessPiece); // Add the piece to the board.
                        break; // Break the switch.
                    case "Bishop": // If the piece type is Bishop.
                        chessPiece = main.new Bishop(piecePosition, pieceColor); // Create a new Bishop piece.
                        chessBoard.addPiece(chessPiece); // Add the piece to the board.
                        break; // Break the switch.
                    case "Knight": // If the piece type is Knight.
                        chessPiece = main.new Knight(piecePosition, pieceColor); // Create a new Knight piece.
                        chessBoard.addPiece(chessPiece); // Add the piece to the board.
                        break; // Break the switch.
                    case "Pawn": // If the piece type is Pawn.
                        chessPiece = main.new Pawn(piecePosition, pieceColor); // Create a new Pawn piece.
                        chessBoard.addPiece(chessPiece); // Add the piece to the board.
                        break; // Break the switch.
                    default: // If the piece type is not valid.
                        break; // Break the switch.
                }
            }
            for (int i = 0; i < piecesCount; i++) { // Check if the pieces are in the same position.
                for (int j = i + 1; j < piecesCount; j++) {
                    if (pieceDataParts[i][2].equals(pieceDataParts[j][2])
                            && pieceDataParts[i][INDEX_OF_THE_Y_COORDINATE].equals(
                                    pieceDataParts[j][INDEX_OF_THE_Y_COORDINATE])
                    ) { // If the pieces are in the same position, throw an exception.
                        throw new InvalidPiecePositionException();
                    }
                }
            }
            int whiteKingCount = 0; // Count the number of white kings.
            int blackKingCount = 0; // Count the number of black kings.
            for (ChessPiece piece : chessBoard.positionToPieces.values()) {
                if (piece instanceof King) {
                    if (piece.color == PieceColor.WHITE) {
                        whiteKingCount++;
                    } else if (piece.color == PieceColor.BLACK) {
                        blackKingCount++;
                    }
                }
            } // Check if there is one white king and one black king.
            if (whiteKingCount != 1 || blackKingCount != 1) {
                throw new InvalidGivenKingsException();
            }
            for (int i = 0; i < piecesCount; i++) { // Write the result to the output file.
                chessPiece = chessBoard.positionToPieces.get(Integer.parseInt(pieceDataParts[i][2])
                        + " " + Integer.parseInt(pieceDataParts[i][INDEX_OF_THE_Y_COORDINATE]));
                writer.write(chessPiece.getMovesCount(chessBoard.positionToPieces, chessBoard.size)
                        + " " + chessPiece.getCapturesCount(chessBoard.positionToPieces, chessBoard.size) + "\n");
                writer.flush();
                System.out.println(chessPiece.getMovesCount(chessBoard.positionToPieces, chessBoard.size)
                        + " " + chessPiece.getCapturesCount(chessBoard.positionToPieces, chessBoard.size));

            }
        } catch (InvalidBoardSizeException | InvalidNumberOfPiecesException // Catch the exceptions.
                 | InvalidPieceNameException | InvalidPieceColorException
                 | InvalidPiecePositionException | InvalidGivenKingsException e) {
            writer.write(e.getMessage());
            writer.flush();
            System.out.println(e.getMessage());
            System.exit(0);
        }
    }
    /**
     * This class represents a chess board.
     * It contains the size of the board and a map of the pieces on the board.
     */
    class Board {
        /**
         * Map, that contains all the pieces on the board.
         * Key is the position of the piece (It's coordinate) in the format "x y".
         * Value is the position and color of the piece.
         */
        private Map<String, ChessPiece> positionToPieces;

        /**
         * Size of the board.
         */
        private int size;
        /**
         * Constructor for the Board class.
         * @param boardSize Size of the board.
         */
        Board(int boardSize) {
            this.size = boardSize;
            this.positionToPieces = new HashMap<>();
        }
        /**
         * Get possible moves for a piece.
         * @param piece Piece to get the moves for.
         * @return Number of possible moves.
         */
        public int getPiecePossibleMovesCount(ChessPiece piece) {
                return 0;
        }
        /**
         * Get possible captures for a piece.
         * @param piece Piece to get the captures for.
         * @return Number of possible captures.
         */
        public int getPiecePossibleCapturesCount(ChessPiece piece) {
                return 0;
        }
        /**
         * Add a piece to the board.
         * @param piece Piece to add to the board.
         */
        public void addPiece(ChessPiece piece) {
            positionToPieces.put(piece.position.toString(), piece);

        }
        /**
         * Get a piece by its coordinates.
         * @param position coordinates of the piece.
         * @return Piece at the given coordinates.
         */
        public ChessPiece getPiece(PiecePosition position) {
                return null;
        }
    }
    /**
     * This abstract class represents an abstract chess piece.
     * It contains the position and color of the piece.
     */
    abstract class ChessPiece {
        /**
         * Position of the piece on the board.
         * It's coordinate in the format "x y".
         */
        protected PiecePosition position;
        /**
         * Color of the piece.
         * It can be either White or Black.
         */
        protected PieceColor color;
        /**
         * Constructor for the ChessPiece class.
         * @param piecePosition Position of the piece on the board.
         * @param pieceColor Color of the piece.
         */
        ChessPiece(PiecePosition piecePosition, PieceColor pieceColor) {
            this.position = piecePosition;
            this.color = pieceColor;
        }

        /**
         * Getter for the position of the piece.
         * @return Position of the piece.
         */
        public PiecePosition getPosition() {
            return position;
        }
        /**
         * Getter for the color of the piece.
         * @return Color of the piece.
         */
        public PieceColor getColor() {
            return color;
        }
        /**
         * Abstract method of getting possible moves for a piece.
         * @param positions Map of the pieces on the board.
         * @param boardSize Size of the board.
         * @return Number of possible moves.
         */
        public abstract int getMovesCount(Map<String, ChessPiece> positions, int boardSize);
        /**
         * Abstract method of getting possible captures for a piece.
         * @param positions Map of the pieces on the board.
         * @param boardSize Size of the board.
         * @return Number of possible captures.
         */
        public abstract int getCapturesCount(Map<String, ChessPiece> positions, int boardSize);
    }

    /**
     * Enumeration of the colors of the pieces.
     */
    enum PieceColor {
        /**
         * White color of the piece.
         */
        WHITE,
        /**
         * Black color of the piece.
         */
        BLACK;
        /**
         * Get the color of the piece from the input line.
         * @param line String line, that should contain the color of the piece.
         * @return The color of the piece in PieceColor Type.
         */
        public static PieceColor parse(String line) {
                if (line.equals("White")) {
                    return PieceColor.WHITE;
                } else if (line.equals("Black")) {
                    return PieceColor.BLACK;
                } else {
                    return null;
                }
        }
    }
    /**
     * This class represents a position of a piece on the board.
     * It contains the x and y coordinates of the piece.
     */
    public class PiecePosition {
        /**
         * X coordinate of the piece on the chess board.
         */
        private int x;
        /**
         * Y coordinate of the piece on the chess board.
         */
        private int y;
        /**
         * Constructor of the PiecePosition class.
         * @param onX X coordinate of the piece on the chess board.
         * @param onY Y coordinate of the piece on the chess board.
         */
        public PiecePosition(int onX, int onY) {
            this.x = onX;
            this.y = onY;
        }
        /**
         * Getter for the X coordinate of the piece on the chess board.
         * @return X coordinate of the piece on the chess board.
         */
        public int getX() {
            return x;
        }
        /**
         * Getter for the Y coordinate of the piece on the chess board.
         * @return Y coordinate of the piece on the chess board.
         */
        public int getY() {
            return y;
        }
        /**
         * Method that returns the String type position of the piece on the chess board in the format "x y".
         * @return String position of the piece in the format "x y".
         */
        @Override
        public String toString() {
            return x + " " + y;
        }
    }

    /**
     * This class represents an interface for the walking diagonally pieces.
     * It contains the method for getting the possible moves and captures for the pieces that can walk diagonally.
     */
    public interface BishopMovement {
        /**
         * Method that returns the possible diagonal moves.
         * The piece can move diagonally.
         * @param position Position of the piece on the board.
         * @param color Color of the piece.
         * @param positions Map of the positions of the pieces on the board.
         * @param boardSize Size of the board.
         * @return Number of the possible moves of the piece.
         */
        int getDiagonalMovesCount(PiecePosition position, PieceColor color,
                                         Map<String, ChessPiece> positions, int boardSize);
        /**
         * Method that returns the possible diagonal captures.
         * The piece can capture diagonally.
         * @param position Position of the piece on the board.
         * @param color Color of the piece.
         * @param positions Map of the positions of the pieces on the board.
         * @param boardSize Size of the board.
         * @return Number of the possible captures of the piece.
         */
        int getDiagonalCapturesCount(PiecePosition position, PieceColor color,
                                            Map<String, ChessPiece> positions, int boardSize);
    }
    public interface RookMovement {
        /**
         * Method that returns the possible orthogonal moves.
         * The piece can move horizontally and vertically.
         * @param position Position of the piece on the board.
         * @param color Color of the piece.
         * @param positions Map of the positions of the pieces on the board.
         * @param boardSize Size of the board.
         * @return Number of the possible moves of the piece.
         */
        int getOrthogonalMovesCount(PiecePosition position, PieceColor color,
                                           Map<String, ChessPiece> positions, int boardSize);

        /**
         * Method that returns the possible orthogonal captures.
         * The piece can move horizontally and vertically.
         * @param position Position of the piece on the board.
         * @param color Color of the piece.
         * @param positions Map of the positions of the pieces on the board.
         * @param boardSize Size of the board.
         * @return Number of the possible captures of the piece.
         */
        int getOrthogonalCapturesCount(PiecePosition position, PieceColor color,
                                              Map<String, ChessPiece> positions, int boardSize);
    }
    /**
     * This class represents a Knight piece on the chess board.
     * It contains the methods for getting the possible moves and captures for the Knight piece.
     */
    public class Knight extends ChessPiece {
        /**
         * Constructor of the Knight class.
         * @param position Position of the knight on the board.
         * @param color Color of the knight.
         */
        public Knight(PiecePosition position, PieceColor color) {
            super(position, color);
        }
        /**
         * Method that returns the possible moves of the Knight.
         * The Knight can move in an L shape.
         * The Knight can jump over other pieces.
         * @param positions Map of the positions of the pieces on the board.
         * @param boardSize Size of the board.
         * @return Number of the possible moves of the Knight.
         */
        @Override
        public int getMovesCount(Map<String, ChessPiece> positions, int boardSize) {
            // Count moves for the Knight.
            int movesCount = 0;
            // Get the initial position of the Knight.
            int x = position.getX();
            int y = position.getY();
            // Check if the Knight can move to the "L" position.
            if (x + 2 <= boardSize && y + 1 <= boardSize) {
                if (positions.get((x + 2) + " " + (y + 1)) == null) {
                    movesCount++;
                }
                if (positions.get((x + 2) + " " + (y + 1)) != null
                        && positions.get((x + 2) + " " + (y + 1)).getColor() != color) {
                    movesCount++;
                }
            }
            if (x + 2 <= boardSize && y - 1 >= 1) {
                if (positions.get((x + 2) + " " + (y - 1)) == null) {
                    movesCount++;
                }
                if (positions.get((x + 2) + " " + (y - 1)) != null
                        && positions.get((x + 2) + " " + (y - 1)).getColor() != color) {
                    movesCount++;
                }
            }
            if (x - 2 >= 1 && y + 1 <= boardSize) {
                if (positions.get((x - 2) + " " + (y + 1)) == null) {
                    movesCount++;
                }
                if (positions.get((x - 2) + " " + (y + 1)) != null
                        && positions.get((x - 2) + " " + (y + 1)).getColor() != color) {
                    movesCount++;
                }
            }
            if (x - 2 >= 1 && y - 1 >= 1) {
                if (positions.get((x - 2) + " " + (y - 1)) == null) {
                    movesCount++;
                }
                if (positions.get((x - 2) + " " + (y - 1)) != null
                        && positions.get((x - 2) + " " + (y - 1)).getColor() != color) {
                    movesCount++;
                }
            }
            if (x + 1 <= boardSize && y + 2 <= boardSize) {
                if (positions.get((x + 1) + " " + (y + 2)) == null) {
                    movesCount++;
                }
                if (positions.get((x + 1) + " " + (y + 2)) != null
                        && positions.get((x + 1) + " " + (y + 2)).getColor() != color) {
                    movesCount++;
                }
            }
            if (x + 1 <= boardSize && y - 2 >= 1) {
                if (positions.get((x + 1) + " " + (y - 2)) == null) {
                    movesCount++;
                }
                if (positions.get((x + 1) + " " + (y - 2)) != null
                        && positions.get((x + 1) + " " + (y - 2)).getColor() != color) {
                    movesCount++;
                }
            }
            if (x - 1 >= 1 && y + 2 <= boardSize) {
                if (positions.get((x - 1) + " " + (y + 2)) == null) {
                    movesCount++;
                }
                if (positions.get((x - 1) + " " + (y + 2)) != null
                        && positions.get((x - 1) + " " + (y + 2)).getColor() != color) {
                    movesCount++;
                }
            }
            if (x - 1 >= 1 && y - 2 >= 1) {
                if (positions.get((x - 1) + " " + (y - 2)) == null) {
                    movesCount++;
                }
                if (positions.get((x - 1) + " " + (y - 2)) != null
                        && positions.get((x - 1) + " " + (y - 2)).getColor() != color) {
                    movesCount++;
                }
            }
            return movesCount;
        }
        /**
         * Method that returns the possible captures of the Knight.
         * The Knight can move in an L shape.
         * The Knight can jump over other pieces.
         * @param positions Map of the positions of the pieces on the board.
         * @param boardSize Size of the board.
         * @return Number of the possible captures of the Knight.
         */
        @Override
        public int getCapturesCount(Map<String, ChessPiece> positions, int boardSize) {
            int capturesCount = 0;
            int x = position.getX();
            int y = position.getY();
            if (x + 2 <= boardSize && y + 1 <= boardSize) {
                if (positions.get((x + 2) + " " + (y + 1)) != null
                        && positions.get((x + 2) + " " + (y + 1)).getColor() != color) {
                    capturesCount++;
                }
            }
            if (x + 2 <= boardSize && y - 1 >= 1) {
                if (positions.get((x + 2) + " " + (y - 1)) != null
                        && positions.get((x + 2) + " " + (y - 1)).getColor() != color) {
                    capturesCount++;
                }
            }
            if (x - 2 >= 1 && y + 1 <= boardSize) {
                if (positions.get((x - 2) + " " + (y + 1)) != null
                        && positions.get((x - 2) + " " + (y + 1)).getColor() != color) {
                    capturesCount++;
                }
            }
            if (x - 2 >= 1 && y - 1 >= 1) {
                if (positions.get((x - 2) + " " + (y - 1)) != null
                        && positions.get((x - 2) + " " + (y - 1)).getColor() != color) {
                    capturesCount++;
                }
            }
            if (x + 1 <= boardSize && y + 2 <= boardSize) {
                if (positions.get((x + 1) + " " + (y + 2)) != null
                        && positions.get((x + 1) + " " + (y + 2)).getColor() != color) {
                    capturesCount++;
                }
            }
            if (x + 1 <= boardSize && y - 2 >= 1) {
                if (positions.get((x + 1) + " " + (y - 2)) != null
                        && positions.get((x + 1) + " " + (y - 2)).getColor() != color) {
                    capturesCount++;
                }
            }
            if (x - 1 >= 1 && y + 2 <= boardSize) {
                if (positions.get((x - 1) + " " + (y + 2)) != null
                        && positions.get((x - 1) + " " + (y + 2)).getColor() != color) {
                    capturesCount++;
                }
            }
            if (x - 1 >= 1 && y - 2 >= 1) {
                if (positions.get((x - 1) + " " + (y - 2)) != null
                        && positions.get((x - 1) + " " + (y - 2)).getColor() != color) {
                    capturesCount++;
                }
            }
            return capturesCount;
        }
    }
    /**
     * Class that represents the King.
     * There is only one King per color.
     */
    public class King extends ChessPiece {
        /**
         * Constructor for King.
         * @param position position of the piece.
         * @param color color of the piece.
         */
        public King(PiecePosition position, PieceColor color) {
            super(position, color);
        }

        /**
         * Method that returns the possible moves of the King.
         * The King can move in any direction, but only one square at a time.
         * @param positions Map of the positions of the pieces on the board.
         * @param boardSize Size of the board.
         * @return Number of the possible moves of the King.
         */
        @Override
        public int getMovesCount(Map<String, ChessPiece> positions, int boardSize) {
            int movesCount = 0;
            int x = position.getX();
            int y = position.getY();
            // Check all the possible moves of the King. (Squares around the King)
            if (x + 1 <= boardSize && y + 1 <= boardSize) {
                if (positions.get((x + 1) + " " + (y + 1)) == null) {
                    movesCount++;
                } else if (positions.get((x + 1) + " " + (y + 1)).getColor() != color) {
                    movesCount++;
                }
            }
            if (x + 1 <= boardSize && y - 1 > 0) {
                if (positions.get((x + 1) + " " + (y - 1)) == null) {
                    movesCount++;
                } else if (positions.get((x + 1) + " " + (y - 1)).getColor() != color) {
                    movesCount++;
                }
            }
            if (x - 1 > 0 && y + 1 <= boardSize) {
                if (positions.get((x - 1) + " " + (y + 1)) == null) {
                    movesCount++;
                } else if (positions.get((x - 1) + " " + (y + 1)).getColor() != color) {
                    movesCount++;
                }
            }
            if (x - 1 > 0 && y - 1 > 0) {
                if (positions.get((x - 1) + " " + (y - 1)) == null) {
                    movesCount++;
                } else if (positions.get((x - 1) + " " + (y - 1)).getColor() != color) {
                    movesCount++;
                }
            }
            if (x + 1 <= boardSize) {
                if (positions.get((x + 1) + " " + y) == null) {
                    movesCount++;
                } else if (positions.get((x + 1) + " " + y).getColor() != color) {
                    movesCount++;
                }
            }
            if (x - 1 > 0) {
                if (positions.get((x - 1) + " " + y) == null) {
                    movesCount++;
                } else if (positions.get((x - 1) + " " + y).getColor() != color) {
                    movesCount++;
                }
            }
            if (y + 1 <= boardSize) {
                if (positions.get(x + " " + (y + 1)) == null) {
                    movesCount++;
                } else if (positions.get(x + " " + (y + 1)).getColor() != color) {
                    movesCount++;
                }
            }
            if (y - 1 > 0) {
                if (positions.get(x + " " + (y - 1)) == null) {
                    movesCount++;
                } else if (positions.get(x + " " + (y - 1)).getColor() != color) {
                    movesCount++;
                }
            }
            return movesCount;
        }

        /**
         * Method that returns the possible captures of the King.
         * The King can capture in any direction, but only one square at a time.
         * @param positions Map of the positions of the pieces on the board.
         * @param boardSize Size of the board.
         * @return Number of the possible captures of the King.
         */
        @Override
        public int getCapturesCount(Map<String, ChessPiece> positions, int boardSize) {
            int capturesCount = 0;
            int x = position.getX();
            int y = position.getY();
            if (x + 1 <= boardSize && y + 1 <= boardSize) {
                if (positions.get((x + 1) + " " + (y + 1)) != null
                        && positions.get((x + 1) + " " + (y + 1)).getColor() != color) {
                    capturesCount++;
                }
            }
            if (x + 1 <= boardSize && y - 1 > 0) {
                if (positions.get((x + 1) + " " + (y - 1)) != null
                        && positions.get((x + 1) + " " + (y - 1)).getColor() != color) {
                    capturesCount++;
                }
            }
            if (x - 1 > 0 && y + 1 <= boardSize) {
                if (positions.get((x - 1) + " " + (y + 1)) != null
                        && positions.get((x - 1) + " " + (y + 1)).getColor() != color) {
                    capturesCount++;
                }
            }
            if (x - 1 > 0 && y - 1 > 0) {
                if (positions.get((x - 1) + " " + (y - 1)) != null
                        && positions.get((x - 1) + " " + (y - 1)).getColor() != color) {
                    capturesCount++;
                }
            }
            if (x + 1 <= boardSize) {
                if (positions.get((x + 1) + " " + y) != null
                        && positions.get((x + 1) + " " + y).getColor() != color) {
                    capturesCount++;
                }
            }
            if (x - 1 > 0) {
                if (positions.get((x - 1) + " " + y) != null
                        && positions.get((x - 1) + " " + y).getColor() != color) {
                    capturesCount++;
                }
            }
            if (y + 1 <= boardSize) {
                if (positions.get(x + " " + (y + 1)) != null
                        && positions.get(x + " " + (y + 1)).getColor() != color) {
                    capturesCount++;
                }

            }
            if (y - 1 > 0) {
                if (positions.get(x + " " + (y - 1)) != null
                        && positions.get(x + " " + (y - 1)).getColor() != color) {
                    capturesCount++;
                }
            }
            return capturesCount;
        }
    }
    /**
     * Class that represents the Pawn chess piece.
     * The Pawn can move only one square forward, but can capture diagonally if the color of this piece is different.
     */
    public class Pawn extends ChessPiece {
        /**
         * Constructor for Pawn.
         * @param position position of the piece.
         * @param color color of the piece.
         */
        public Pawn(PiecePosition position, PieceColor color) {
            super(position, color);
        }
        /**
         * Method that returns the possible moves of the Pawn.
         * The Pawn can move only one square forward, but can capture diagonally.
         * @param positions Map of the positions of the pieces on the board.
         * @param boardSize Size of the board.
         * @return Number of the possible moves of the Pawn.
         */
        @Override
        public int getMovesCount(Map<String, ChessPiece> positions, int boardSize) {
            int movesCount = 0;
            int x = position.getX();
            int y = position.getY();
            // If the color of the piece is white, the pawn can move only up.
            if (color == PieceColor.WHITE) {
                if (y + 1 <= boardSize) {
                       if (positions.get(x + " " + (y + 1)) == null) {
                            movesCount++;
                        }
                }
                if (y + 1 <= boardSize && x + 1 <= boardSize) {
                    if (positions.get((x + 1) + " " + (y + 1)) != null
                            && positions.get((x + 1) + " " + (y + 1)).getColor() != color) {
                        movesCount++;
                    }
                }
                if (y + 1 <= boardSize && x - 1 > 0) {
                    if (positions.get((x - 1) + " " + (y + 1)) != null
                            && positions.get((x - 1) + " " + (y + 1)).getColor() != color) {
                        movesCount++;
                    }
                }
                // If the color of the piece is black, the pawn can move only down.
            } else if (color == PieceColor.BLACK) {
                if (y - 1 > 0) {
                    if (positions.get(x + " " + (y - 1)) == null) {
                        movesCount++;
                    }
                }
                if (y - 1 > 0 && x + 1 <= boardSize) {
                    if (positions.get((x + 1) + " " + (y - 1)) != null
                            && positions.get((x + 1) + " " + (y - 1)).getColor() != color) {
                        movesCount++;
                    }
                }
                if (y - 1 > 0 && x - 1 > 0) {
                    if (positions.get((x - 1) + " " + (y - 1)) != null
                            && positions.get((x - 1) + " " + (y - 1)).getColor() != color) {
                        movesCount++;
                    }
                }
            }
            return movesCount;
        }
        /**
         * Method that returns the possible captures of the Pawn.
         * The Pawn can capture only diagonally and only pieces of other color.
         * In this method we do not take into account the piece facing the pawn, because pawn can not capture it.
         * @param positions Map of the positions of the pieces on the board.
         * @param boardSize Size of the board.
         * @return Number of the possible captures of the Pawn.
         */
        @Override
        public int getCapturesCount(Map<String, ChessPiece> positions, int boardSize) {
            int capturesCount = 0;
            int x = position.getX();
            int y = position.getY();
            if (color == PieceColor.WHITE) {
                if (y + 1 <= boardSize && x + 1 <= boardSize) {
                    if (positions.get((x + 1) + " " + (y + 1)) != null
                            && positions.get((x + 1) + " " + (y + 1)).getColor() != color) {
                        capturesCount++;
                    }
                }
                if (y + 1 <= boardSize && x - 1 > 0) {
                    if (positions.get((x - 1) + " " + (y + 1)) != null
                            && positions.get((x - 1) + " " + (y + 1)).getColor() != color) {
                        capturesCount++;
                    }
                }
            } else if (color == PieceColor.BLACK) {
                if (y - 1 > 0 && x + 1 <= boardSize) {
                    if (positions.get((x + 1) + " " + (y - 1)) != null
                            && positions.get((x + 1) + " " + (y - 1)).getColor() != color) {
                        capturesCount++;
                    }
                }
                if (y - 1 > 0 && x - 1 > 0) {
                    if (positions.get((x - 1) + " " + (y - 1)) != null
                            && positions.get((x - 1) + " " + (y - 1)).getColor() != color) {
                        capturesCount++;
                    }
                }
            }
            return capturesCount;
        }
    }

    /**
     * Class that represents the Bishop chess piece.
     */
    public class Bishop extends ChessPiece implements BishopMovement {
        /**
         * Constructor for Bishop.
         * @param position position of the piece.
         * @param color color of the piece.
         */
        public Bishop(PiecePosition position, PieceColor color) {
            super(position, color);
        }
        /**
         * Method that returns the possible moves of the Bishop.
         * The Bishop can move diagonally.
         * @param positions Map of the positions of the pieces on the board.
         * @param boardSize Size of the board.
         * @return Number of the possible moves of the Bishop.
         */
        @Override
        public int getMovesCount(Map<String, ChessPiece> positions, int boardSize) {
            int movesCount = 0;
            int x = position.getX();
            int y = position.getY();
            int i = 1;
            /* Check  Bishop's diagonals.
             Move across the board diagonally until we find a piece, or we reach the end of the board.
             If the color is the same, we can not move there. We rest against the figure.
             If the color is different, we can capture the piece, and stand on its position. */
            while (x + i <= boardSize && y + i <= boardSize) {
                if (positions.get((x + i) + " " + (y + i)) == null) {
                    movesCount++;
                } else {
                    if (positions.get((x + i) + " " + (y + i)).getColor() != color) {
                        movesCount++;
                    }
                    break;
                }
                i++;
            }
            i = 1;
            while (x - i > 0 && y - i > 0) {
                if (positions.get((x - i) + " " + (y - i)) == null) {
                    movesCount++;
                } else {
                    if (positions.get((x - i) + " " + (y - i)).getColor() != color) {
                        movesCount++;
                    }
                    break;
                }
                i++;
            }
            i = 1;
            while (x + i <= boardSize && y - i > 0) {
                if (positions.get((x + i) + " " + (y - i)) == null) {
                    movesCount++;
                } else {
                    if (positions.get((x + i) + " " + (y - i)).getColor() != color) {
                        movesCount++;
                    }
                    break;
                }
                i++;
            }
            i = 1;
            while (x - i > 0 && y + i <= boardSize) {
                if (positions.get((x - i) + " " + (y + i)) == null) {
                    movesCount++;
                } else {
                    if (positions.get((x - i) + " " + (y + i)).getColor() != color) {
                        movesCount++;
                    }
                    break;
                }
                i++;
            }
            return movesCount;
        }
        /**
         * Method that returns the possible captures of the Bishop.
         * The Bishop can capture diagonally and only pieces of other color.
         * @param positions Map of the positions of the pieces on the board.
         * @param boardSize Size of the board.
         * @return Number of the possible captures of the Bishop.
         */
        @Override
        public int getCapturesCount(Map<String, ChessPiece> positions, int boardSize) {
            int capturesCount = 0;
            int x = position.getX();
            int y = position.getY();
            int i = 1;
            while (x + i <= boardSize && y + i <= boardSize) {
                if (positions.get((x + i) + " " + (y + i)) == null) {
                    i++;
                } else {
                    if (positions.get((x + i) + " " + (y + i)).getColor() != color) {
                        capturesCount++;
                    }
                    break;
                }
            }
            i = 1;
            while (x - i > 0 && y - i > 0) {
                if (positions.get((x - i) + " " + (y - i)) == null) {
                    i++;
                } else {
                    if (positions.get((x - i) + " " + (y - i)).getColor() != color) {
                        capturesCount++;
                    }
                    break;
                }
            }
            i = 1;
            while (x + i <= boardSize && y - i > 0) {
                if (positions.get((x + i) + " " + (y - i)) == null) {
                    i++;
                } else {
                    if (positions.get((x + i) + " " + (y - i)).getColor() != color) {
                        capturesCount++;
                    }
                    break;
                }
            }
            i = 1;
            while (x - i > 0 && y + i <= boardSize) {
                if (positions.get((x - i) + " " + (y + i)) == null) {
                    i++;
                } else {
                    if (positions.get((x - i) + " " + (y + i)).getColor() != color) {
                        capturesCount++;
                    }
                    break;
                }
            }
            return capturesCount;
        }
        /**
         * Method that returns diagonal moves.
         * @param positions Map of the positions of the pieces on the board.
         * @param boardSize Size of the board.
         * @return Number of the possible diagonal moves.
         */
        @Override
        public int getDiagonalMovesCount(PiecePosition position, PieceColor color,
                                         Map<String, ChessPiece> positions, int boardSize) {
            return 0;
        }
        /**
         * Method that returns diagonal captures.
         * @param positions Map of the positions of the pieces on the board.
         * @param boardSize Size of the board.
         * @return Number of the possible diagonal captures.
         */
        @Override
        public int getDiagonalCapturesCount(PiecePosition position, PieceColor color,
                                            Map<String, ChessPiece> positions, int boardSize) {
            return 0;
        }
    }
    /**
     * Class that represents the Rook chess piece.
     * The Rook can move horizontally and vertically.
     */
    public class Rook extends ChessPiece implements RookMovement {
        /**
         * Constructor for Rook.
         * @param position position of the piece.
         * @param color color of the piece.
         */
        public Rook(PiecePosition position, PieceColor color) {
            super(position, color);
        }
        /**
         * Method that returns the possible moves of the Rook.
         * The Rook can move horizontally and vertically.
         * @param positions Map of the positions of the pieces on the board.
         * @param boardSize Size of the board.
         * @return Number of the possible moves of the Rook.
         */
        @Override
        public int getMovesCount(Map<String, ChessPiece> positions, int boardSize) {
            int movesCount = 0;
            int x = position.getX();
            int y = position.getY();
            int i = 1;
            while (x + i <= boardSize) {
                if (positions.get((x + i) + " " + y) == null) {
                    movesCount++;
                } else {
                    if (positions.get((x + i) + " " + y).getColor() != color) {
                        movesCount++;
                    }
                    break;
                }
                i++;
            }
            i = 1;
            while (x - i > 0) {
                if (positions.get((x - i) + " " + y) == null) {
                    movesCount++;
                } else {
                    if (positions.get((x - i) + " " + y).getColor() != color) {
                        movesCount++;
                    }
                    break;
                }
                i++;
            }
            i = 1;
            while (y + i <= boardSize) {
                if (positions.get(x + " " + (y + i)) == null) {
                    movesCount++;
                } else {
                    if (positions.get(x + " " + (y + i)).getColor() != color) {
                        movesCount++;
                    }
                    break;
                }
                i++;
            }
            i = 1;
            while (y - i > 0) {
                if (positions.get(x + " " + (y - i)) == null) {
                    movesCount++;
                } else {
                    if (positions.get(x + " " + (y - i)).getColor() != color) {
                        movesCount++;
                    }
                    break;
                }
                i++;
            }
            return movesCount;
        }
        /**
         * Method that returns the possible captures of the Rook.
         * The Rook can capture horizontally and vertically and only pieces of other color.
         * @param positions Map of the positions of the pieces on the board.
         * @param boardSize Size of the board.
         * @return Number of the possible captures of the Rook.
         */
        @Override
        public int getCapturesCount(Map<String, ChessPiece> positions, int boardSize) {
            int capturesCount = 0;
            int x = position.getX();
            int y = position.getY();
            int i = 1;
            while (x + i <= boardSize) {
                if (positions.get((x + i) + " " + y) == null) {
                    i++;
                } else {
                    if (positions.get((x + i) + " " + y).getColor() != color) {
                        capturesCount++;
                    }
                    break;
                }
            }
            i = 1;
            while (x - i > 0) {
                if (positions.get((x - i) + " " + y) == null) {
                    i++;
                } else {
                    if (positions.get((x - i) + " " + y).getColor() != color) {
                        capturesCount++;
                    }
                    break;
                }
            }
            i = 1;
            while (y + i <= boardSize) {
                if (positions.get(x + " " + (y + i)) == null) {
                    i++;
                } else {
                    if (positions.get(x + " " + (y + i)).getColor() != color) {
                        capturesCount++;
                    }
                    break;
                }
            }
            i = 1;
            while (y - i > 0) {
                if (positions.get(x + " " + (y - i)) == null) {
                    i++;
                } else {
                    if (positions.get(x + " " + (y - i)).getColor() != color) {
                        capturesCount++;
                    }
                    break;
                }
            }
            return capturesCount;
        }
        /**
         * Method that returns orthogonal moves: horizontal and vertical.
         * @param positions Map of the positions of the pieces on the board.
         * @param boardSize Size of the board.
         * @return Number of the possible orthogonal moves.
         */
        @Override
        public int getOrthogonalMovesCount(PiecePosition position, PieceColor color,
                                           Map<String, ChessPiece> positions, int boardSize) {
            return 0;
        }
        /**
         * Method that returns orthogonal captures: horizontal and vertical.
         * @param positions Map of the positions of the pieces on the board.
         * @param boardSize Size of the board.
         * @return Number of the possible orthogonal captures.
         */
        @Override
        public int getOrthogonalCapturesCount(PiecePosition position, PieceColor color,
                                              Map<String, ChessPiece> positions, int boardSize) {
            return 0;
        }
    }
    /**
     * Class that represents the Queen chess piece.
     * The Queen can move horizontally, vertically and diagonally.
     */
    public class Queen extends ChessPiece implements BishopMovement, RookMovement {
        /**
         * Constructor for Queen.
         * @param position position of the piece.
         * @param color color of the piece.
         */
        public Queen(PiecePosition position, PieceColor color) {
            super(position, color);
        }
        /**
         * Method that returns the possible moves of the Queen.
         * The Queen can move horizontally, vertically and diagonally.
         * @param positions Map of the positions of the pieces on the board.
         * @param boardSize Size of the board.
         * @return Number of the possible moves of the Queen.
         */
        @Override
        public int getMovesCount(Map<String, ChessPiece> positions, int boardSize) {
            int movesCount = 0;
            int x = position.getX();
            int y = position.getY();
            int i = 1;
            while (x + i <= boardSize) {
                if (positions.get((x + i) + " " + y) == null) {
                    movesCount++;
                } else {
                    if (positions.get((x + i) + " " + y).getColor() != color) {
                        movesCount++;
                    }
                    break;
                }
                i++;
            }
            i = 1;
            while (x - i > 0) {
                if (positions.get((x - i) + " " + y) == null) {
                    movesCount++;
                } else {
                    if (positions.get((x - i) + " " + y).getColor() != color) {
                        movesCount++;
                    }
                    break;
                }
                i++;
            }
            i = 1;
            while (y + i <= boardSize) {
                if (positions.get(x + " " + (y + i)) == null) {
                    movesCount++;
                } else {
                    if (positions.get(x + " " + (y + i)).getColor() != color) {
                        movesCount++;
                    }
                    break;
                }
                i++;
            }
            i = 1;
            while (y - i > 0) {
                if (positions.get(x + " " + (y - i)) == null) {
                    movesCount++;
                } else {
                    if (positions.get(x + " " + (y - i)).getColor() != color) {
                        movesCount++;
                    }
                    break;
                }
                i++;
            }
            i = 1;
            while (x + i <= boardSize && y + i <= boardSize) {
                if (positions.get((x + i) + " " + (y + i)) == null) {
                    movesCount++;
                } else {
                    if (positions.get((x + i) + " " + (y + i)).getColor() != color) {
                        movesCount++;
                    }
                    break;
                }
                i++;
            }
            i = 1;
            while (x - i > 0 && y - i > 0) {
                if (positions.get((x - i) + " " + (y - i)) == null) {
                    movesCount++;
                } else {
                    if (positions.get((x - i) + " " + (y - i)).getColor() != color) {
                        movesCount++;
                    }
                    break;
                }
                i++;
            }
            i = 1;
            while (x + i <= boardSize && y - i > 0) {
                if (positions.get((x + i) + " " + (y - i)) == null) {
                    movesCount++;
                } else {
                    if (positions.get((x + i) + " " + (y - i)).getColor() != color) {
                        movesCount++;
                    }
                    break;
                }
                i++;
            }
            i = 1;
            while (x - i > 0 && y + i <= boardSize) {
                if (positions.get((x - i) + " " + (y + i)) == null) {
                    movesCount++;
                } else {
                    if (positions.get((x - i) + " " + (y + i)).getColor() != color) {
                        movesCount++;
                    }
                    break;
                }
                i++;
            }
            return movesCount;
        }
        /**
         * Method that returns the possible captures of the Queen.
         * The Queen can capture horizontally, vertically and diagonally.
         * @param positions Map of the positions of the pieces on the board.
         * @param boardSize Size of the board.
         * @return Number of the possible captures of the Queen.
         */
        @Override
        public int getCapturesCount(Map<String, ChessPiece> positions, int boardSize) {
            int capturesCount = 0;
            int x = position.getX();
            int y = position.getY();
            int i = 1;
            while (x + i <= boardSize) {
                if (positions.get((x + i) + " " + y) == null) {
                    i++;
                } else {
                    if (positions.get((x + i) + " " + y).getColor() != color) {
                        capturesCount++;
                    }
                    break;
                }
            }
            i = 1;
            while (x - i > 0) {
                if (positions.get((x - i) + " " + y) == null) {
                    i++;
                } else {
                    if (positions.get((x - i) + " " + y).getColor() != color) {
                        capturesCount++;
                    }
                    break;
                }
            }
            i = 1;
            while (y + i <= boardSize) {
                if (positions.get(x + " " + (y + i)) == null) {
                    i++;
                } else {
                    if (positions.get(x + " " + (y + i)).getColor() != color) {
                        capturesCount++;
                    }
                    break;
                }
            }
            i = 1;
            while (y - i > 0) {
                if (positions.get(x + " " + (y - i)) == null) {
                    i++;
                } else {
                    if (positions.get(x + " " + (y - i)).getColor() != color) {
                        capturesCount++;
                    }
                    break;
                }
            }
            i = 1;
            while (x + i <= boardSize && y + i <= boardSize) {
                if (positions.get((x + i) + " " + (y + i)) == null) {
                    i++;
                } else {
                    if (positions.get((x + i) + " " + (y + i)).getColor() != color) {
                        capturesCount++;
                    }
                    break;
                }
            }
            i = 1;
            while (x - i > 0 && y - i > 0) {
                if (positions.get((x - i) + " " + (y - i)) == null) {
                    i++;
                } else {
                    if (positions.get((x - i) + " " + (y - i)).getColor() != color) {
                        capturesCount++;
                    }
                    break;
                }
            }
            i = 1;
            while (x + i <= boardSize && y - i > 0) {
                if (positions.get((x + i) + " " + (y - i)) == null) {
                    i++;
                } else {
                    if (positions.get((x + i) + " " + (y - i)).getColor() != color) {
                        capturesCount++;
                    }
                    break;
                }
            }
            i = 1;
            while (x - i > 0 && y + i <= boardSize) {
                if (positions.get((x - i) + " " + (y + i)) == null) {
                    i++;
                } else {
                    if (positions.get((x - i) + " " + (y + i)).getColor() != color) {
                        capturesCount++;
                    }
                    break;
                }
            }
            return capturesCount;
        }

        /**
         * Method for getting diagonals moves.
         * @param position Position of the piece on the board.
         * @param color Color of the piece.
         * @param positions Map of the positions of the pieces on the board.
         * @param boardSize Size of the board.
         * @return Number of the possible moves of the piece.
         */
        @Override
        public int getDiagonalMovesCount(PiecePosition position, PieceColor color,
                                         Map<String, ChessPiece> positions, int boardSize) {
            return 0;
        }
        /**
         * Method for getting diagonals captures.
         * @param position Position of the piece on the board.
         * @param color Color of the piece.
         * @param positions Map of the positions of the pieces on the board.
         * @param boardSize Size of the board.
         * @return Number of the possible captures of the piece.
         */
        @Override
        public int getDiagonalCapturesCount(PiecePosition position, PieceColor color,
                                            Map<String, ChessPiece> positions, int boardSize) {
            return 0;
        }
        /**
         * Method for getting orthogonal moves.
         * @param position Position of the piece on the board.
         * @param color Color of the piece.
         * @param positions Map of the positions of the pieces on the board.
         * @param boardSize Size of the board.
         * @return Number of the possible moves of the piece.
         */
        @Override
        public int getOrthogonalMovesCount(PiecePosition position, PieceColor color,
                                           Map<String, ChessPiece> positions, int boardSize) {
            return 0;
        }
        /**
         * Method for getting orthogonal captures.
         * @param position Position of the piece on the board.
         * @param color Color of the piece.
         * @param positions Map of the positions of the pieces on the board.
         * @param boardSize Size of the board.
         * @return Number of the possible captures of the piece.
         */
        @Override
        public int getOrthogonalCapturesCount(PiecePosition position, PieceColor color,
                                              Map<String, ChessPiece> positions, int boardSize) {
            return 0;
        }
    }


}
/**
 * Class for exceptions.
 * Exceptions are thrown in the following priority order:
 * 1. Invalid board size.
 * 2. Invalid number of pieces.
 * 3. Invalid piece name.
 * 4. Invalid piece color.
 * 5. Invalid piece position.
 * 6. Invalid number of Kings.
 * 7. Invalid input.
 */
class Exception extends Throwable {
    public String getMessage() {
        return null;
    }
}
/**
 * Exception for invalid board size.
 * The board size must be a positive integer number between 3 and 1000.
 */
class InvalidBoardSizeException extends Exception {
    /**
     * Method for getting the message of the exception.
     * @return Message of the exception.
     */
    @Override
    public String getMessage() {
        return "Invalid board size";
    }
}

/**
 * Exception for invalid number of pieces.
 * The number of pieces must be a positive integer number between 2 and board size squared.
 */
class InvalidNumberOfPiecesException extends Exception {
    @Override
    public String getMessage() {
        return "Invalid number of pieces";
    }
}

/**
 * Exception for invalid piece name.
 * The piece name must be a string with the name of the piece.
 * The name of the piece must be one of the following: King, Queen, Rook, Bishop, Knight, Pawn.
 */
class InvalidPieceNameException extends Exception {
    @Override
    public String getMessage() {
        return "Invalid piece name";
    }
}

/**
 * Exception for invalid piece color.
 * The piece color must be a string with the color of the piece.
 * The color of the piece must be one of the following: White, Black.
 */
class InvalidPieceColorException extends Exception {
    @Override
    public String getMessage() {
        return "Invalid piece color";
    }
}

/**
 * Exception for invalid piece position.
 * The piece position must be a string with the position of the piece.
 * The position of the piece must be a string with two integers separated by a space.
 * The first integer must be between 1 and board size.
 * The second integer must be between 1 and board size.
 */
class InvalidPiecePositionException extends Exception {
    @Override
    public String getMessage() {
        return "Invalid piece position";
    }
}

/**
 * Exception for invalid kings number.
 * There should always be one and only one king per player (color) for a game to be valid.
 */
class InvalidGivenKingsException extends Exception {
    @Override
    public String getMessage() {
        return "Invalid given Kings";
    }
}
/**
 * Exception for invalid input.
 * If something is wrong with the input, this exception is thrown.
 */
class InvalidInputException extends Exception {
    @Override
    public String getMessage() {
        return "Invalid input";
    }
}

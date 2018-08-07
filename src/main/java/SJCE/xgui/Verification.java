package SJCE.xgui;
import SJCE.xgui.JPanel.BoardUI;
import java.util.HashMap;

public class Verification {
/*
========================================================================
 SCAN MOVE PATH AND ATTACK PATH
========================================================================
*/
	public static final byte RANK_2 = 1;
	public static final byte RANK_7 = 6;
	private static final int OFF = BoardUI.OFF_BOARD;
	private static final int[] toGeneralBoard = {
	   OFF, OFF, OFF, OFF, OFF, OFF, OFF, OFF, OFF, OFF, OFF, OFF,
	   OFF, OFF, OFF, OFF, OFF, OFF, OFF, OFF, OFF, OFF, OFF, OFF,
	   OFF, OFF,   0,   1,   2,   3,   4,   5,   6,   7, OFF, OFF,
	   OFF, OFF,   8,   9,  10,  11,  12,  13,  14,  15, OFF, OFF,
	   OFF, OFF,  16,  17,  18,  19,  20,  21,  22,  23, OFF, OFF,
	   OFF, OFF,  24,  25,  26,  27,  28,  29,  30,  31, OFF, OFF,
	   OFF, OFF,  32,  33,  34,  35,  36,  37,  38,  39, OFF, OFF,
	   OFF, OFF,  40,  41,  42,  43,  44,  45,  46,  47, OFF, OFF,
	   OFF, OFF,  48,  49,  50,  51,  52,  53,  54,  55, OFF, OFF,
	   OFF, OFF,  56,  57,  58,  59,  60,  61,  62,  63, OFF, OFF,
	   OFF, OFF, OFF, OFF, OFF, OFF, OFF, OFF, OFF, OFF, OFF, OFF,
	   OFF, OFF, OFF, OFF, OFF, OFF, OFF, OFF, OFF, OFF, OFF, OFF,
	};

	private static final int[] toTestBoard = {
	   26,   27,   28,   29,   30,   31,   32,   33,
	   38,   39,   40,   41,   42,   43,   44,   45,
	   50,   51,   52,   53,   54,   55,   56,   57,
	   62,   63,   64,   65,   66,   67,   68,   69,
	   74,   75,   76,   77,   78,   79,   80,   81,
	   86,   87,   88,   89,   90,   91,   92,   93,
	   98,   99,  100,  101,  102,  103,  104,  105,
	   110, 111,  112,  113,  114,  115,  116,  117,
	};

	public static final int OFF_BOARD = BoardUI.OFF_BOARD;
	public static final int PIECE_BLOCKADE = -50;
	public static final int LIMIT_EXCEED = -51;
	public static final int INVALID_DIRECTION = -52;
	public static final int SUCCESSFUL = 10;
	public int scanSquare;
	public int scanPiece;

	public int scanMove(int source, int destination, int direction, int limit) {
		return scanPath(source, destination, direction, limit);
	}
	public int scanMove(int source, int destination, int direction) {
		return scanPath(source, destination, direction, Integer.MAX_VALUE);
	}
	public int scanAttack(int source, int direction, int limit) {
		int result = scanPath(source, Integer.MAX_VALUE, direction, limit);
		if(Utility.equalOr(result, PIECE_BLOCKADE, OFF_BOARD, LIMIT_EXCEED))
			return SUCCESSFUL;
		return result;
	}
	public int scanAttack(int source, int direction) {
		return scanAttack(source, direction, Integer.MAX_VALUE);
	}
	public int scanPath(int source, int destination, int direction, int limit) {
		scanSquare = source;
		scanPiece = board[source];
		if(direction == 0) return INVALID_DIRECTION;
		source = toTestBoard[source];
		destination = (destination >= BoardUI.SQUARE_COUNT)?
				Integer.MAX_VALUE : toTestBoard[destination];
		for(int i = source + direction, l = 0; true; i += direction, l++) {
			scanSquare = toGeneralBoard[i];
			scanPiece = (toGeneralBoard[i] == OFF_BOARD)? PiecesUI.NO_PIECE
					: board[toGeneralBoard[i]];

			if(toGeneralBoard[i] == OFF_BOARD) return OFF_BOARD;
			else if(i == destination) break;
			else if(board[toGeneralBoard[i]] != PiecesUI.NO_PIECE)
				return PIECE_BLOCKADE;
			else if(l >= limit - 1) return LIMIT_EXCEED;
		}
		return SUCCESSFUL;
	}
/*
=====================================================================================
 PERFORM INITIAL VERIFY
=====================================================================================
*/
	public static final int INVALID_MOVE = -1000;
	public static final int INVALID_BLOCK = -1001;
	public static final int VALID_MOVE = 1000;
	private int[] board;
	private HashMap<Integer, String> messageMap = new HashMap<Integer, String>(10);
	private static final int EMPTY_DESTINATION = 100;

	public Verification(int[] board) {
		this.board = board;
		messageMap.put(INVALID_MOVE, "Move is not Valid");
	}

	public int initialVerify(Move move, int type) {
		int source = move.getSource();
		int destination = move.getDestination();
		int piece = move.getPiece();

		int fileDisplacement = BoardUI.getFileDistance(destination, source);
		int rankDisplacement = BoardUI.getRankDistance(destination, source);

		if(PiecesUI.isPiece(PiecesUI.PAWN, piece)) {
		    if(Math.abs(rankDisplacement) == 2) {
if((PiecesUI.getColor(piece) == 
        PiecesUI.COLOR_WHITE) ? (BoardUI.getRank(source) != RANK_2)
: (BoardUI.getRank(source) != RANK_7)) return INVALID_MOVE;
				int direction = compare(fileDisplacement, rankDisplacement,
							0, 0, 12, 0, -0, -0, -12, -0, 0);
				return combineResult(scanMove(source, destination, direction, 2),
						EMPTY_DESTINATION);
			}
			if((PiecesUI.getColor(piece) == PiecesUI.COLOR_WHITE) ? (rankDisplacement != +1)
					: (rankDisplacement != -1)) return INVALID_MOVE;

			int direction = compare(fileDisplacement, rankDisplacement,
						0, 13, 12, 11, -0, -13, -12, -11, 0);

			int result = scanMove(source, destination, direction, 1);
			if(fileDisplacement == 0 && scanPiece != PiecesUI.NO_PIECE)
				return INVALID_MOVE;
			else if(Math.abs(fileDisplacement) == 1 && scanPiece == PiecesUI.NO_PIECE)
				return INVALID_MOVE;

			return combineResult(result, piece);
		}
		else if(PiecesUI.isPiece(PiecesUI.KNIGHT, piece)) {
			int direction = (Math.abs(fileDisplacement) > Math.abs(rankDisplacement)) ?
					  compare(fileDisplacement, rankDisplacement,
							  	0, 14, 0, 10, 0, -14, 0, -10, 0)
					: compare(fileDisplacement, rankDisplacement,
								0, 25, 0, 23, 0, -25, 0, -23, 0);
			return combineResult(scanMove(source, destination, direction, 1), piece);
		}
		else if(PiecesUI.isPiece(PiecesUI.BISHOP, piece)) {
			int direction = compare(fileDisplacement, rankDisplacement,
						0, 13, 0, 11, -0, -13, -0, -11, 0);

			return combineResult(scanMove(source, destination, direction), piece);
		}
		else if(PiecesUI.isPiece(PiecesUI.ROOK, piece)) {
			int direction = compare(fileDisplacement, rankDisplacement,
						1, 0, 12, 0, -1, -0, -12, -0, 0);

			return combineResult(scanMove(source, destination, direction), piece);
		}
		else if(PiecesUI.isPiece(PiecesUI.QUEEN, piece)) {
			int direction = compare(fileDisplacement, rankDisplacement,
						1, 13, 12, 11, -1, -13, -12, -11, 0);

			return combineResult(scanMove(source, destination, direction), piece);
		}
		else if(PiecesUI.isPiece(PiecesUI.KING, piece)) {
			if(type == Move.CASTLE_MOVE) {
				int direction = compare(fileDisplacement, rankDisplacement,
						1, 0, 0, 0, -1, -0, -0, -0, 0);

				int result = (fileDisplacement < 0)? scanMove(source, destination - 1,
						direction, 3) : scanMove(source, destination, direction, 2);
				return combineResult(result, EMPTY_DESTINATION);
			}
			int direction = compare(fileDisplacement, rankDisplacement,
						1, 13, 12, 11, -1, -13, -12, -11, 0);

			return combineResult(scanMove(source, destination, direction, 1), piece);
		}
		return VALID_MOVE;
	}
	private int combineResult(int scanResult, int flag) {
		if(scanResult < 0) return INVALID_MOVE;
		if(scanPiece != PiecesUI.NO_PIECE) {
			if(flag == EMPTY_DESTINATION) return INVALID_MOVE;
			else if(PiecesUI.sameColor(flag, scanPiece)) return INVALID_MOVE;
		}
		return VALID_MOVE;
	}
	private int compare(int fileDisplacement, int rankDisplacement,
							int fprz, int fprp, int fzrp,
							int fnrp, int fnrz, int fnrn,
							int fzrn, int fprn, int fzrz) {
		return Utility.compare(fileDisplacement,
					Utility.compare(rankDisplacement, fprp, fprz, fprn),
					Utility.compare(rankDisplacement, fzrp, fzrz, fzrn),
					Utility.compare(rankDisplacement, fnrp, fnrz, fnrn));
	}
/*
=====================================================================================
 PERFORM FINAL VERIFY
=====================================================================================
*/
	public int finalVerify(Move move) {
		int color = PiecesUI.getColor(move.getPiece());
		if(findAttacker(PiecesUI.indexOf(board, PiecesUI.KING[color]),
				PiecesUI.switchColor(color)) != PiecesUI.NO_PIECE) return INVALID_MOVE;
		return VALID_MOVE;
	}
	private int findAttacker(int square, int color) {
		int[] directionList;
		directionList = new int[] {-13, -11, 11, 13};
		for(int direction : directionList) {
			if(scanAttack(square, direction) < 0) return PiecesUI.NO_PIECE;
			if(scanPiece == PiecesUI.BISHOP[color] || scanPiece == PiecesUI.QUEEN[color])
				return scanPiece;
		}
		for(int direction : directionList) {
			if(scanAttack(square, direction, 1) < 0) return PiecesUI.NO_PIECE;
			if(scanPiece == PiecesUI.KING[color]) return scanPiece;
			if(scanPiece == PiecesUI.PAWN[color]) {
				if((color == PiecesUI.COLOR_WHITE)? (direction == -11 || direction == -13)
						: (direction == 11 || direction == 13)) return scanPiece;
			}
		}
		directionList = new int[] {-12, -1, 1, 12};
		for(int direction : directionList) {
			if(scanAttack(square, direction) < 0) return PiecesUI.NO_PIECE;
			if(scanPiece == PiecesUI.ROOK[color] || scanPiece == PiecesUI.QUEEN[color])
				return scanPiece;
		}
		for(int direction : directionList) {
			if(scanAttack(square, direction, 1) < 0) return PiecesUI.NO_PIECE;
			if(scanPiece == PiecesUI.KING[color]) return scanPiece;
		}
		directionList = new int[] {-25, -23, -14, -10, 10, 14, 23, 25 };
		for(int direction : directionList) {
			if(scanAttack(square, direction, 1) < 0) return PiecesUI.NO_PIECE;
			if(scanPiece == PiecesUI.KNIGHT[color]) return scanPiece;
		}
		return PiecesUI.NO_PIECE;
	}
	public String getMessage(int result) { return messageMap.get(result); }
}

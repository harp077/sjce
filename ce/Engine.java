/*
 * Engine.java
 *
 * Created on 14 of July 2002, 22:15
 */

import java.io.*;
import java.util.*;
import java.text.*;

/**
 * The main engine class.
 *
 * @author Tomasz Michniewski
 * @version 1.0
 */
public class Engine extends java.lang.Object
{
	/** A constant representing the maximal number of items in one command. */
	private static final int MAX_ITEMS = 100;

	/** A constant representing the search margin used in aspiration search. */
	private static final int SEARCH_MARGIN = 10;

	/** This field stores the mode (true == winboard, false = textmode). */
	private boolean xboard;

	/** This field stores information about the color the machine plays. */
	private boolean computerWhite;

	/** This field stores information about the side to play the next move. */
	private boolean moveOfWhite;

	/** This field stores an indicator whether to think on oponents time. */
	private boolean ponder;

	/** This field stores the current position. */
	private Position p;

	/** This field stores the status of the go command. */
	private boolean wasGo;

	/** This field stores indicator whether to post the best line or not. */
	private boolean post;

	/** This field stores the number of moves to the next time control. */
	private int levelMoves;

	/** This field stores the time to the next time control. */
	private int levelTime;

	/** This field stores the increment added after each move. */
	private int levelIncrement;

	/** This field stores the number of moves played before the next time control. */
	private int numberOfMoves;

	/** This field stores the current move number. */
	private int moveNumber;

	/** This field stores the time in seconds till the next time control. */
	private int timeLeft;

	/** This field stores the time in seconds per one move for the operator. */
	private int operatorTime;

	/** This field stores the number of processors availaible on the machine. */
	private int numberOfProcessors;

	/** This field stores the number of threads */
	private int numberOfThreads;

	/** This field stores the first searching thread. */
	private Search s1;

	/** This field stores the second searching thread. */
	private Search s2;

	/** This field stores the game written in PGN format. */
	private String gamePGN;

	/** This field stores the opening moves in Atak format. */
	private String openingMoves;

	/** This field stores the log used to store every message exchanged. */
	private PrintWriter log;

	/**
	 * Initialization of members.
	 * @throws IOException It might be thrown by the FileWriter constructor.
	 */
	public Engine()
	throws IOException
	{
		super();

		xboard = false;
		computerWhite = false;
		moveOfWhite = true;
		p = new Position();
		wasGo = false;
		numberOfThreads = 0;
		gamePGN = "";
		openingMoves = "";
		log = new PrintWriter(new BufferedWriter(new FileWriter("JChess.log")));

		ponderCmd("off");
		postCmd();
		levelCmd(0, 5, 0);
		operatorCmd(0); // seconds per move

		numberOfProcessors =
			Integer.parseInt(System.getProperty("number_of_processors", "1"));
		threadsCmd(1);
/*
		if (numberOfProcessors == 1)
			threadsCmd(3);
		else
			threadsCmd(4);
*/
	}

	/**
	 * The program starts here.
	 * @throws IOException It might be thrown by the getCommand method.
	 * @throws InterruptedException It might be thrown by the join method.
	 */
	public void start()
	throws IOException, InterruptedException
	{
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);
		StreamTokenizer st = new StreamTokenizer(br);
		st.eolIsSignificant(true);
		st.commentChar('#');
		st.wordChars('/', '/');

		String[] command = new String[MAX_ITEMS]; // first command, then parameters
		int numberOfItems = getCommand(st, command);

		xboard = (command[0].equals("xboard") && numberOfItems == 1);
		if (xboard)
		{
			System.out.println("feature setboard=1"); // use the setboard instead of edit command
			log.println("Output:");
			log.println("feature setboard=1");
			numberOfItems = getCommand(st, command);
		}

		if (!xboard)
		{
			System.out.println("JChess ver. 1.0, Copyright (c) 2001-2002 Tomasz Michniewski");
			log.println("Output:");
			log.println("JChess ver. 1.0, Copyright (c) 2001-2002 Tomasz Michniewski");
		}

		while (!command[0].equals("quit"))
		{
			if (command[0].equals("black") && numberOfItems == 1)
				// it is move of black
				blackCmd();
			else if (command[0].equals("display") && numberOfItems == 1)
				// display the current chessboard
				displayCmd();
			else if (command[0].equals("force") && numberOfItems == 1)
				// stop playing
				forceCmd();
			else if (command[0].equals("go") && numberOfItems == 1)
				// play with the color on move
				goCmd();
			else if (command[0].equals("level") && numberOfItems == 4)
				// set clocks
				levelCmd(Integer.parseInt(command[1]), Integer.parseInt(command[2]),
					Integer.parseInt(command[3]));
			else if (command[0].equals("new") && numberOfItems == 1)
				// start a new game
				newCmd();
			else if (command[0].equals("nopost") && numberOfItems == 1)
				// stop displaying thinking output
				nopostCmd();
			else if (command[0].equals("operator") && numberOfItems == 2)
				// some time for the operator
				operatorCmd(Integer.parseInt(command[1]));
			else if (command[0].equals("ponder") && numberOfItems == 2)
				// change ponder indicator
				ponderCmd(command[1]);
			else if (command[0].equals("post") && numberOfItems == 1)
				// start displaying thinking output
				postCmd();
			else if (command[0].equals("savegame") && numberOfItems == 3)
				// save the current game
				savegameCmd(Integer.parseInt(command[1]), command[2]);
			else if (command[0].equals("setboard") &&
				(numberOfItems == 7 || numberOfItems == 8))
				// set the position by reading the given fen
				// for now ignore the 50 moves rule
				if (numberOfItems == 7)
					setboardCmd(command[1], command[2], command[3], command[4],
						Integer.parseInt(command[6]));
				else // numberOfItems == 8
					setboardCmd(command[1] + command[2], command[3], command[4], command[5],
						Integer.parseInt(command[7]));
			else if (command[0].equals("time") && numberOfItems == 2)
				// how much time the engine has to the next time control
				timeCmd(Integer.parseInt(command[1]));
			else if (command[0].equals("time") && numberOfItems == 3)
				// how much time the engine has to the next time control
				timeCmd(Integer.parseInt(command[1]), Integer.parseInt(command[2]));
			else if (command[0].equals("threads") && numberOfItems == 2)
				// set number of threads
				threadsCmd(Integer.parseInt(command[1]));
			else if (command[0].equals("white") && numberOfItems == 1)
				// it is move of white
				whiteCmd();
			else if (isMove(command[0]) && numberOfItems == 1)
				// it is a move
				moveCmd(command[0]);
			else if (!xboard)
			{
				if (numberOfItems > 0)
				{
					System.out.print("Bad command: " + command[0]);
					log.println("Output:");
					log.print("Bad command: " + command[0]);
					for (int i = 1; i < numberOfItems; i++)
					{
						System.out.print(' ' + command[i]);
						log.print(' ' + command[i]);
					}
					System.out.println();
					log.println();
				}
			}

			numberOfItems = getCommand(st, command);
		}

		stopThinking();
		log.close();
	}

	/**
	 * Reads the next command and splits it into items.
	 * @param st The StreamTokenizer object connected to the System.in stream.
	 * @param command The return variable.
	 * command[0] will store the command name,
	 * the rest will store command parameters.
	 * @return Returns the number of items in the array.
	 * @throws IOException It might be thrown by the nextToken method.
	 */
	private int getCommand(StreamTokenizer st, String[] command)
	throws IOException
	{
		int numberOfItems = 0;
		while (st.nextToken() != StreamTokenizer.TT_EOF)
		{
			switch (st.ttype)
			{
				case StreamTokenizer.TT_EOL :
					log.println("Input:");
					log.print(command[0]);
					for (int i = 1; i < numberOfItems; i++)
						log.print(' ' + command[i]);
					log.println();
					return numberOfItems;
				case StreamTokenizer.TT_NUMBER :
					command[numberOfItems] = Integer.toString((int)st.nval);
					break;
				case StreamTokenizer.TT_WORD :
					command[numberOfItems] = st.sval;
					break;
				default :
					command[numberOfItems] = String.valueOf((char)st.ttype);
					break;
			}
			numberOfItems++;
		}

		log.println("Input:");
		log.print(command[0]);
		for (int i = 1; i < numberOfItems; i++)
			log.print(' ' + command[i]);
		log.println();

		return numberOfItems;
	}

	/**
	 * Checks whether the input command represents the valid move.
	 * @param command The command to check.
	 * @return Returns true if the command represents the valid move.
	 */
	private static boolean isMove(String command)
	{
		if (command.length() != 4 && command.length() != 5)
			return false;
		if (command.charAt(0) < 'a' || command.charAt(0) > 'h')
			return false;
		if (command.charAt(1) < '1' || command.charAt(1) > '8')
			return false;
		if (command.charAt(2) < 'a' || command.charAt(2) > 'h')
			return false;
		if (command.charAt(3) < '1' || command.charAt(3) > '8')
			return false;
		if (command.length() == 5 &&
			command.charAt(4) != 'q' && command.charAt(4) != 'r' &&
			command.charAt(4) != 'b' && command.charAt(4) != 'n')
			return false;

		return true;
	}

	/**
	 * Realizes the black command.
	 */
	private void blackCmd()
	{
		moveOfWhite = false;
		wasGo = false;
	}

	/**
	 * Realizes the display command.
	 */
	private void displayCmd()
	{
		// this command might not be called in the xboard mode.
		if (computerWhite)
		{
			System.out.print(p.toStringInverted());
			log.println("Output:");
			log.print(p.toStringInverted());
		}
		else
		{
			System.out.print(p.toString());
			log.println("Output:");
			log.print(p.toString());
		}

		System.out.print("Eval: " + Integer.toString(p.evaluatePosition()));
		log.print("Eval: " + Integer.toString(p.evaluatePosition()));
		System.out.print(", phase: ");
		log.print(", phase: ");
		int gamePhase = p.calculateGamePhase();
		if (gamePhase == Position.OPENING)
		{
			System.out.println("opening.");
			log.println("opening.");
		}
		else if (gamePhase == Position.MIDDLE_GAME)
		{
			System.out.println("middle game.");
			log.println("middle game.");
		}
		else if (gamePhase == Position.ENDING)
		{
			System.out.println("ending.");
			log.println("ending.");
		}
		else
		{
			System.out.println("mating.");
			log.println("mating.");
		}
	}

	/**
	 * Realizes the force command.
	 */
	private void forceCmd()
	{
		wasGo = false;
	}

	/**
	 * Realizes the go command.
	 * @throws InterruptedException It might be thrown by the play method.
	 */
	private void goCmd()
	throws InterruptedException
	{
		if (moveOfWhite)
			computerWhite = true;
		else
			computerWhite = false;

		wasGo = true;
		play();
	}

	/**
	 * Realizes the level command.
	 */
	private void levelCmd(int levelMoves, int levelTime, int levelIncrement)
	{
		this.levelMoves = levelMoves;
		this.levelTime = 60 * levelTime;
		this.levelIncrement = levelIncrement;

		numberOfMoves = 0;
		timeLeft = 60 * levelTime;
	}

	/**
	 * Realizes the new command.
	 */
	private void newCmd()
	{
		computerWhite = false;
		moveOfWhite = true;
		numberOfMoves = 0;
		moveNumber = 1;
		timeLeft = levelTime;
		gamePGN = "";
		openingMoves = "";

		p.initPosition();
		Search.resetNumberOfElements();
		wasGo = true;
	}

	/**
	 * Realizes the nopost command.
	 */
	private void nopostCmd()
	{
		post = false;
	}

	/**
	 * Realizes the operator command.
	 * @param operatorTime The time in seconds left for operating the program.
	 */
	private void operatorCmd(int operatorTime)
	{
		this.operatorTime = operatorTime;
	}

	/**
	 * Realizes the ponder command.
	 * @param onOrOff One of two possible values: "on" or "off".
	 */
	private void ponderCmd(String onOrOff)
	{
		ponder = onOrOff.equals("on");
		if (numberOfThreads > 0) // not the first call from the constructor
		{
			if (!ponder)
			{
				if (numberOfThreads % 2 == 1)
				{
					s1.stopIt();
				}
				else
				{
					s1.stopIt();
					s2.stopIt();
				}
			}
		}
	}

	/**
	 * Realizes the post command.
	 */
	private void postCmd()
	{
		post = true;
	}

	/**
	 * Realizes the savegame command.
	 * @param round The number of round.
	 * @param result A string holding the result (1-0, 1/2-1/2, 0-1).
	 * @throws IOException It might be thrown by the FileOutputStream constructor.
	 */
	private void savegameCmd(int round, String result)
	throws IOException
	{
		if (result.equals("whitewon"))
			result = "1-0";
		else if (result.equals("draw"))
			result = "1/2-1/2";
		else if (result.equals("blackwon"))
			result = "0-1";
		else
			result = "*";

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");
		Date d = new Date();
		String today = formatter.format(d);

		PrintWriter out = new PrintWriter(
			new BufferedWriter(
				new FileWriter("game" + Integer.toString(round) + ".pgn")));
		out.println("[Event \"Computer chess game\"]");
		out.println("[Site \"?\"]");
		out.println("[Date \"" + today + "\"]");
		out.println("[Round \"" + Integer.toString(round) + "\"]");
		if (computerWhite)
		{
			out.println("[White \"JChess\"]");
			out.println("[Black \"Opponent\"]");
		}
		else
		{
			out.println("[White \"Opponent\"]");
			out.println("[Black \"JChess\"]");
		}
		out.println("[Result \"" + result + "\"]");
		out.print(gamePGN);
		if (!gamePGN.equals(""))
			out.print(' ');
		out.println(result);
		out.close();
	}

	/**
	 * Realizes the setboard command.
	 * @param position The input position.
	 * @param sideToMove Whose turn is it.
	 * @param castles The satus of castles.
	 * @param pawnToCapture The pawn which might be captured en passant.
	 * @param moveNumber The current move number.
	 */
	private void setboardCmd(String position, String sideToMove,
		String castles, String pawnToCapture, int moveNumber)
	{
		boolean whiteShortCastle = (castles.indexOf('K') != -1);
		boolean whiteLongCastle = (castles.indexOf('Q') != -1);
		boolean blackShortCastle = (castles.indexOf('k') != -1);
		boolean blackLongCastle = (castles.indexOf('q') != -1);
		int pawnToCaptureSquare = -1;
		if (!pawnToCapture.equals("-"))
		{
			pawnToCaptureSquare = (pawnToCapture.charAt(1) - '1');
			pawnToCaptureSquare = ((pawnToCaptureSquare << 3) + (pawnToCapture.charAt(0) - 'a'));
			if (pawnToCaptureSquare <= 31) // white
				pawnToCaptureSquare += 8;
			else // black
				pawnToCaptureSquare -= 8;
		}
		p.setBoard(whiteShortCastle, whiteLongCastle,
			blackShortCastle, blackLongCastle, pawnToCaptureSquare);

		numberOfMoves = moveNumber - 1;
		if (levelMoves != 0)
			numberOfMoves %= levelMoves;
		this.moveNumber = moveNumber;
		openingMoves = "?"; // to skip opening moves

		if (sideToMove.equals("w"))
			whiteCmd();
		else
			blackCmd();
		Search.resetNumberOfElements();
		wasGo = false;

		int index = 0;
		int square = 56;
		while (square >= 0)
		{
			switch (position.charAt(index))
			{
				case 'P' :
					p.setPiece(square, Position.WHITE_PAWN);
					index++;
					square++;
					if (square % 8 == 0)
						square -= 16; // e.g. after 63 is 64 and the next comes 48 - line by line
					break;
				case 'N' :
					p.setPiece(square, Position.WHITE_KNIGHT);
					index++;
					square++;
					if (square % 8 == 0)
						square -= 16;
					break;
				case 'B' :
					p.setPiece(square, Position.WHITE_BISHOP);
					index++;
					square++;
					if (square % 8 == 0)
						square -= 16;
					break;
				case 'R' :
					p.setPiece(square, Position.WHITE_ROOK);
					index++;
					square++;
					if (square % 8 == 0)
						square -= 16;
					break;
				case 'Q' :
					p.setPiece(square, Position.WHITE_QUEEN);
					index++;
					square++;
					if (square % 8 == 0)
						square -= 16;
					break;
				case 'K' :
					p.setPiece(square, Position.WHITE_KING);
					index++;
					square++;
					if (square % 8 == 0)
						square -= 16;
					break;
				case 'p' :
					p.setPiece(square, Position.BLACK_PAWN);
					index++;
					square++;
					if (square % 8 == 0)
						square -= 16;
					break;
				case 'n' :
					p.setPiece(square, Position.BLACK_KNIGHT);
					index++;
					square++;
					if (square % 8 == 0)
						square -= 16;
					break;
				case 'b' :
					p.setPiece(square, Position.BLACK_BISHOP);
					index++;
					square++;
					if (square % 8 == 0)
						square -= 16;
					break;
				case 'r' :
					p.setPiece(square, Position.BLACK_ROOK);
					index++;
					square++;
					if (square % 8 == 0)
						square -= 16;
					break;
				case 'q' :
					p.setPiece(square, Position.BLACK_QUEEN);
					index++;
					square++;
					if (square % 8 == 0)
						square -= 16;
					break;
				case 'k' :
					p.setPiece(square, Position.BLACK_KING);
					index++;
					square++;
					if (square % 8 == 0)
						square -= 16;
					break;
				case '1' :
					index++;
					square++;
					if (square % 8 == 0)
						square -= 16;
					break;
				case '2' :
					index++;
					square += 2;
					if (square % 8 == 0)
						square -= 16;
					break;
				case '3' :
					index++;
					square += 3;
					if (square % 8 == 0)
						square -= 16;
					break;
				case '4' :
					index++;
					square += 4;
					if (square % 8 == 0)
						square -= 16;
					break;
				case '5' :
					index++;
					square += 5;
					if (square % 8 == 0)
						square -= 16;
					break;
				case '6' :
					index++;
					square += 6;
					if (square % 8 == 0)
						square -= 16;
					break;
				case '7' :
					index++;
					square += 7;
					if (square % 8 == 0)
						square -= 16;
					break;
				case '8' :
					index++;
					square += 8;
					if (square % 8 == 0)
						square -= 16;
					break;
				case '/' :
					index++;
					break;
			}
		}

		if (!xboard)
			displayCmd();
	}

	/**
	 * Realizes the time command.
	 * @param time The time left to the next time control.
	 */
	private void timeCmd(int time)
	{
		timeLeft = time / 100;
	}

	/**
	 * Realizes the second version of time command.
	 * @param minutes The time left to the next time control.
	 * @param seconds The time left to the next time control.
	 */
	private void timeCmd(int minutes, int seconds)
	{
		timeLeft = 60 * minutes + seconds;
	}

	/**
	 * Realizes the threads command.
	 * @param time The time left to the next time control.
	 */
	private void threadsCmd(int numberOfThreads)
	{
		if (this.numberOfThreads > 0 && !xboard) // if not called from the constructor
		{
			switch (numberOfThreads)
			{
				case 1 :
					System.out.println("One thread, standard search");
					log.println("Output:");
					log.println("One thread, standard search");
					break;
				case 2 :
					System.out.println("Two threads, normal search");
					log.println("Output:");
					log.println("Two threads, normal search");
					break;
				case 3 :
					System.out.println("One thread, aspiration search");
					log.println("Output:");
					log.println("One thread, aspiration search");
					break;
				case 4 :
					System.out.println("Two threads, parallel aspiration search");
					log.println("Output:");
					log.println("Two threads with aspiration search");
					break;
				case 5 :
					System.out.println("One thread, binary alphabeta");
					log.println("Output:");
					log.println("One thread, binary alphabeta");
					break;
				case 6 :
					System.out.println("Two threads, triple alphabeta");
					log.println("Output:");
					log.println("Two threads, triple alphabeta");
					break;
				case 7 :
					System.out.println("One thread, sequencial search");
					log.println("Output:");
					log.println("One thread, sequencial search");
					break;
				case 8 :
					System.out.println("Two threads, standard search with parallel window search");
					log.println("Output:");
					log.println("Two threads, standard search with parallel window search");
					break;
			}
		}

		this.numberOfThreads = numberOfThreads;
	}

	/**
	 * Realizes the white command.
	 */
	private void whiteCmd()
	{
		moveOfWhite = true;
		wasGo = false;
	}

	/**
	 * Processes the next move.
	 * @param stringMove The move from the opponent in human format.
	 * @throws InterruptedException It might be thrown by the play method.
	 */
	private void moveCmd(String stringMove)
	throws InterruptedException
	{
		short move = 0;
		if (computerWhite) // convert blacks' move to white notation
			move = (short)(7 - (stringMove.charAt(1) - '1'));
		else
			move = (short)(stringMove.charAt(1) - '1');

		move = (short)((move << 3) + (stringMove.charAt(0) - 'a'));

		if (computerWhite)
			move = (short)((move << 3) + 7 - (stringMove.charAt(3) - '1'));
		else
			move = (short)((move << 3) + (stringMove.charAt(3) - '1'));

		move = (short)((move << 3) + (stringMove.charAt(2) - 'a'));

		byte promotedPiece = Position.NO_PROMOTION;
		if (stringMove.length() == 5)
		{
			switch (stringMove.charAt(4))
			{
				case 'q' :
					promotedPiece = Position.WHITE_QUEEN;
					break;
				case 'r' :
					promotedPiece = Position.WHITE_ROOK;
					break;
				case 'b' :
					promotedPiece = Position.WHITE_BISHOP;
					break;
				case 'n' :
					promotedPiece = Position.WHITE_KNIGHT;
					break;
				default :
					// never happens
					break;
			}
		}

		if (computerWhite) // blacks' move is converted to whites' notation
			p.invertPosition();
		p.makeMove(move, promotedPiece);
		if (computerWhite)
			p.invertPosition();

		moveOfWhite = !moveOfWhite;

		if (!xboard)
			displayCmd();

		if (computerWhite)
		{
			gamePGN += ' ' + stringMove;
			moveNumber++;
			openingMoves += stringMove;
		}
		else
		{
			if (moveNumber > 1)
				gamePGN += ' ';
			gamePGN += Integer.toString(moveNumber) + ". " + stringMove;
			openingMoves += stringMove;
		}

		if (wasGo)
			play();
	}

	/**
	 * Makes the next move using iterative deepening.
	 * @throws InterruptedException It might be thrown by the Thread.join method.
	 */
	private void play()
	throws InterruptedException
	{
		stopThinking();

		if (!computerWhite)
			p.invertPosition();

		long startTime = System.currentTimeMillis();

		int moveTime; // time per one move in ms
		if (levelMoves == 0)
			moveTime = Math.min(1000 * timeLeft / 32, 1000 * levelTime / 50);
		else
			moveTime = 1000 * Math.min(levelTime / levelMoves,
				timeLeft / (levelMoves - numberOfMoves));
		if (moveTime > 2 * 1000 * operatorTime) // some time for the operator
			moveTime -= 1000 * operatorTime;

		short bestMove = 0;
		int maxDepth = 1;
		int e = p.evaluatePosition();

		// first check if there is some hint from the opening book
		// if so just play it, otherwise calculate normally
		String stringMove = Book.getBestMove(openingMoves);
		if (!stringMove.equals("")) // something has been found
		{
			if (!computerWhite) // convert blacks' move to white notation
				bestMove = (short)(7 - (stringMove.charAt(1) - '1'));
			else
				bestMove = (short)(stringMove.charAt(1) - '1');

			bestMove = (short)((bestMove << 3) + (stringMove.charAt(0) - 'a'));

			if (!computerWhite)
				bestMove = (short)((bestMove << 3) + 7 - (stringMove.charAt(3) - '1'));
			else
				bestMove = (short)((bestMove << 3) + (stringMove.charAt(3) - '1'));

			bestMove = (short)((bestMove << 3) + (stringMove.charAt(2) - 'a'));
		}
		else // start thinking
		{
			int alpha, beta, gamma, delta;
	
			if (numberOfThreads == 1) // one thread, standard search
			{
				while (3 * moveTime / 4 - (System.currentTimeMillis() - startTime) > 0 ||
					maxDepth == 1)
				// do not start the next iteration if there is not enough time
				{
					alpha = -Search.INFINITY;
					beta = Search.INFINITY;
					s1 = new Search(p, alpha, beta, maxDepth, "1",
						startTime, xboard, post, computerWhite);
					s1.start();
					if (maxDepth > 1) // the first loop must be always finished
					{
						s1.join(Math.max(moveTime - (System.currentTimeMillis() - startTime), 1));
						// max to be sure that the wait time is always positive
						s1.stopIt();
					}
					s1.join();
	
					if (!s1.getLastRunFinished()) // not enough time
						break;
	
					bestMove = s1.getBestMove();
					e = s1.getBestValue();
	
					if (e >= Search.CHECKMATE_VALUE)
						break;
	
					maxDepth++;
				}
			}
			else if (numberOfThreads == 2) // two threads, normal search
			{
				while (3 * moveTime / 4 - (System.currentTimeMillis() - startTime) > 0 ||
					maxDepth == 1)
				{
					alpha = -Search.INFINITY;
					beta = e;
					gamma = Search.INFINITY;
					s1 = new Search(p, alpha, beta, maxDepth, "1",
						startTime, xboard, post, computerWhite);
					s2 = new Search(p, beta, gamma, maxDepth, "2",
						startTime, xboard, post, computerWhite);
					s1.start();
					s2.start();
					if (maxDepth > 1) // the first loop must be always finished
					{
						s1.join(Math.max(moveTime - (System.currentTimeMillis() - startTime), 1));
						s2.join(Math.max(moveTime - (System.currentTimeMillis() - startTime), 1));
						// max to be sure that the wait time is always positive
						s1.stopIt();
						s2.stopIt();
					}
					s1.join();
					s2.join();
	
					if (s1.getLastRunFinished() && !s2.getLastRunFinished())
					{
						if (s1.getBestValue() > alpha && s1.getBestValue() < beta)
						{
							bestMove = s1.getBestMove();
							e = s1.getBestValue();
						}
						else
							break;
					}
					else if (!s1.getLastRunFinished() && s2.getLastRunFinished())
					{
						if (s2.getBestValue() > beta && s2.getBestValue() < gamma)
						{
							bestMove = s1.getBestMove();
							e = s1.getBestValue();
						}
						else
							break;
					}
					else if (!s1.getLastRunFinished() && !s2.getLastRunFinished())
					{
						break;
					}
					else if (s1.getBestValue() > alpha && s1.getBestValue() < beta)
					// s1.getLastRunFinished() && s2.getLastRunFinished()
					{
						bestMove = s1.getBestMove();
						e = s1.getBestValue();
					}
					else if (s2.getBestValue() > beta && s2.getBestValue() < gamma)
					{
						bestMove = s2.getBestMove();
						e = s2.getBestValue();
					}
					else if (s1.getBestValue() >= beta && s2.getBestValue() <= beta)
					{
						// s1.getBestValue() == beta == s2.getBestValue()
						bestMove = s1.getBestMove();
						e = s1.getBestValue();
					}
					else
					{
						System.out.println("Should not have happend (threads 2)!");
						log.println("Output:");
						log.println("Should not have happend (threads 2)!");
					}
	
					if (e >= Search.CHECKMATE_VALUE)
						break;
	
					maxDepth++;
				}
			}
			else if (numberOfThreads == 3) // one thread, aspiration search
			{
				while (3 * moveTime / 4 - (System.currentTimeMillis() - startTime) > 0 ||
					maxDepth == 1)
				{
					alpha = e - SEARCH_MARGIN;
					beta = e + SEARCH_MARGIN;
					s1 = new Search(p, alpha, beta, maxDepth, "1",
						startTime, xboard, post, computerWhite);
					s1.start();
					if (maxDepth > 1) // the first loop must be always finished
					{
						s1.join(Math.max(moveTime - (System.currentTimeMillis() - startTime), 1));
						// max to be sure that the wait time is always positive
						s1.stopIt();
					}
					s1.join();
	
					if (!s1.getLastRunFinished()) // not enough time
						break;
	
					if (s1.getBestValue() > alpha && s1.getBestValue() < beta)
					{
						bestMove = s1.getBestMove();
						e = s1.getBestValue();
					}
					else if (s1.getBestValue() <= alpha) // research needed
					{
						alpha = -Search.INFINITY;
						beta = s1.getBestValue();
						s1 = new Search(p, alpha, beta, maxDepth, "1\'",
							startTime, xboard, post, computerWhite);
						s1.start();
						if (maxDepth > 1) // the first loop must be always finished
						{
							s1.join(Math.max(moveTime - (System.currentTimeMillis() - startTime), 1));
							// max to be sure that the wait time is always positive
							s1.stopIt();
						}
						s1.join();
	
						if (!s1.getLastRunFinished()) // not enough time
							break;
	
						bestMove = s1.getBestMove();
						e = s1.getBestValue();
					}
					else if (s1.getBestValue() >= beta) // research needed
					{
						alpha = s1.getBestValue();
						beta = Search.INFINITY;
						s1 = new Search(p, alpha, beta, maxDepth, "1\'",
							startTime, xboard, post, computerWhite);
						s1.start();
						if (maxDepth > 1) // the first loop must be always finished
						{
							s1.join(Math.max(moveTime - (System.currentTimeMillis() - startTime), 1));
							// max to be sure that the wait time is always positive
							s1.stopIt();
						}
						s1.join();
	
						if (!s1.getLastRunFinished()) // not enough time
							break;
	
						bestMove = s1.getBestMove();
						e = s1.getBestValue();
					}
	
					if (e >= Search.CHECKMATE_VALUE)
						break;
	
					maxDepth++;
				}
			}
			else if (numberOfThreads == 4) // two threads, parallel aspiration search

			{
				while (3 * moveTime / 4 - (System.currentTimeMillis() - startTime) > 0 ||
					maxDepth == 1)
				{
					alpha = e - 2 * SEARCH_MARGIN;
					beta = e;
					gamma = e + 2 * SEARCH_MARGIN;
					s1 = new Search(p, alpha, beta, maxDepth, "1",
						startTime, xboard, post, computerWhite);
					s2 = new Search(p, beta, gamma, maxDepth, "2",
					 startTime, xboard, post, computerWhite);
					s1.start();
					s2.start();
					if (maxDepth > 1) // the first loop must be always finished
					{
						s1.join(Math.max(moveTime - (System.currentTimeMillis() - startTime), 1));
						s2.join(Math.max(moveTime - (System.currentTimeMillis() - startTime), 1));
						// max to be sure that the wait time is always positive
						s1.stopIt();
						s2.stopIt();
					}
					s1.join();
					s2.join();
	
					if (s1.getLastRunFinished() && !s2.getLastRunFinished())
					{
						if (s1.getBestValue() > alpha && s1.getBestValue() < beta)
						{
							bestMove = s1.getBestMove();
							e = s1.getBestValue();
						}
						else
							break;
					}
					else if (!s1.getLastRunFinished() && s2.getLastRunFinished())
					{
						if (s2.getBestValue() > beta && s2.getBestValue() < gamma)
						{
							bestMove = s1.getBestMove();
							e = s1.getBestValue();
						}
						else
							break;
					}
					else if (!s1.getLastRunFinished() && !s2.getLastRunFinished())
					{
						break;
					}
					else if (s1.getBestValue() > alpha && s1.getBestValue() < beta)
					// s1.getLastRunFinished() && s2.getLastRunFinished()
					{
						bestMove = s1.getBestMove();
						e = s1.getBestValue();
					}
					else if (s2.getBestValue() > beta && s2.getBestValue() < gamma)
					{
						bestMove = s2.getBestMove();
						e = s2.getBestValue();
					}
					else if (s1.getBestValue() >= beta && s2.getBestValue() <= beta)
					{
						// s1.getBestValue() == beta == s2.getBestValue()
						bestMove = s1.getBestMove();
						e = s1.getBestValue();
					}
					else if (s1.getBestValue() <= alpha) // research needed
					{
						alpha = -Search.INFINITY;
						beta = s1.getBestValue() - 2 * SEARCH_MARGIN;
						gamma = s1.getBestValue();
						s1 = new Search(p, alpha, beta, maxDepth, "1\'",
							startTime, xboard, post, computerWhite);
						s2 = new Search(p, beta, gamma, maxDepth, "2\'",
							startTime, xboard, post, computerWhite);
						s1.start();
						s2.start();
						if (maxDepth > 1) // the first loop must be always finished
						{
							s1.join(Math.max(moveTime - (System.currentTimeMillis() - startTime), 1));
							s2.join(Math.max(moveTime - (System.currentTimeMillis() - startTime), 1));
							// max to be sure that the wait time is always positive
							s1.stopIt();
							s2.stopIt();
						}
						s1.join();
						s2.join();
	
						if (s1.getLastRunFinished() && !s2.getLastRunFinished())
						{
							if (s1.getBestValue() < beta)
							{
								bestMove = s1.getBestMove();
								e = s1.getBestValue();
							}
							else
								break;
						}
						else if (!s1.getLastRunFinished() && s2.getLastRunFinished())
						{
							if (s2.getBestValue() > beta && s2.getBestValue() <= gamma) // <= !
							{
								bestMove = s2.getBestMove();
								e = s2.getBestValue();
							}
							else
								break;
						}
						else if (!s1.getLastRunFinished() && !s2.getLastRunFinished())
						{
							break;
						}
						else if (s1.getBestValue() < beta)
						// s1.getLastRunFinished() && s2.getLastRunFinished()
						{
							bestMove = s1.getBestMove();
							e = s1.getBestValue();
						}
						else if (s2.getBestValue() > beta)
						{
							bestMove = s2.getBestMove();
							e = s2.getBestValue();
						}
						else if (s1.getBestValue() >= beta && s2.getBestValue() <= beta)
						{
							// s1.getBestValue() == beta == s2.getBestValue()
							bestMove = s1.getBestMove();
							e = s1.getBestValue();
						}
						else
						{
							System.out.println("Should not have happend (threads 4, 1)!");
							log.println("Output:");
							log.println("Should not have happend (threads 4, 1)!");
						}
					}
					else if (s2.getBestValue() >= gamma) // research needed
					{
						alpha = s2.getBestValue();
						beta = s2.getBestValue() + 2 * SEARCH_MARGIN;
						gamma = Search.INFINITY;
						s1 = new Search(p, alpha, beta, maxDepth, "1\'",
							startTime, xboard, post, computerWhite);
						s2 = new Search(p, beta, gamma, maxDepth, "2\'",
							startTime, xboard, post, computerWhite);
						s1.start();
						s2.start();
						if (maxDepth > 1) // the first loop must be always finished
						{
							s1.join(Math.max(moveTime - (System.currentTimeMillis() - startTime), 1));
							s2.join(Math.max(moveTime - (System.currentTimeMillis() - startTime), 1));
							// max to be sure that the wait time is always positive
							s1.stopIt();
							s2.stopIt();
						}
						s1.join();
						s2.join();
	
						if (s1.getLastRunFinished() && !s2.getLastRunFinished())
						{
							if (s1.getBestValue() >= alpha && s1.getBestValue() < beta) // >= !
							{
								bestMove = s1.getBestMove();
								e = s1.getBestValue();
							}
							else
								break;
						}
						else if (!s1.getLastRunFinished() && s2.getLastRunFinished())
						{
							if (s2.getBestValue() > beta)
							{
								bestMove = s2.getBestMove();
								e = s2.getBestValue();
							}
							else
								break;
						}
						else if (!s1.getLastRunFinished() && !s2.getLastRunFinished())
						{
							break;
						}
						else if (s1.getBestValue() < beta)
						// s1.getLastRunFinished() && s2.getLastRunFinished()
						{
							bestMove = s1.getBestMove();
							e = s1.getBestValue();
						}
						else if (s2.getBestValue() > beta)
						{
							bestMove = s2.getBestMove();
							e = s2.getBestValue();
						}
						else if (s1.getBestValue() >= beta && s2.getBestValue() <= beta)
						{
							// s1.getBestValue() == beta == s2.getBestValue()
							bestMove = s1.getBestMove();
							e = s1.getBestValue();
						}
						else
						{
							System.out.println("Should not have happend (threads 4, 2)!");
							log.println("Output:");
							log.println("Should not have happend (threads 4, 2)!");
						}
					}
					else
					{
						System.out.println("Should not have happend (threads 4, 3)!");
						log.println("Output:");
						log.println("Should not have happend (threads 4, 3)!");
					}
	
					if (e >= Search.CHECKMATE_VALUE)
						break;
	
					maxDepth++;
				}
			}
			else if (numberOfThreads == 5) // one thread, binary alphabeta
			{
				while (3 * moveTime / 4 - (System.currentTimeMillis() - startTime) > 0 ||
					maxDepth == 1)
				{
					alpha = -Search.INFINITY;
					beta = Search.INFINITY;
					int middle = e;
					while (3 * moveTime / 4 - (System.currentTimeMillis() - startTime) > 0)
					{
						s1 = new Search(p, middle - 1, middle + 1, maxDepth, "1",
							startTime, xboard, post, computerWhite);
						s1.start();
						if (maxDepth > 1) // the first loop must be always finished
						{
							s1.join(Math.max(moveTime - (System.currentTimeMillis() - startTime), 1));
							// max to be sure that the wait time is always positive
							s1.stopIt();
						}
						s1.join();
	
						if (!s1.getLastRunFinished()) // not enough time
							break;
	
						if (s1.getBestValue() == middle)
						{
							bestMove = s1.getBestMove();
							e = s1.getBestValue();
							break;
						}
						else if (s1.getBestValue() <= middle - 1)
						{
							beta = s1.getBestValue();
							middle = (alpha + beta) / 2;
						}
						else if (s1.getBestValue() >= middle + 1)
						{
							alpha = s1.getBestValue();
							middle = (alpha + beta) / 2;
						}
					}
	
					if (e >= Search.CHECKMATE_VALUE)
						break;
	
					maxDepth++;
				}
			}
			else if (numberOfThreads == 6) // two threads, triple alphabeta
			{
				while (3 * moveTime / 4 - (System.currentTimeMillis() - startTime) > 0 ||
					maxDepth == 1)
				{
					alpha = -Search.INFINITY;
					beta = Search.INFINITY;
					int middle1 = e - SEARCH_MARGIN;
					int middle2 = e + SEARCH_MARGIN;
					while (3 * moveTime / 4 - (System.currentTimeMillis() - startTime) > 0)
					{
						s1 = new Search(p, middle1 - 1, middle1 + 1, maxDepth, "1",
							startTime, xboard, post, computerWhite);
						s2 = new Search(p, middle2 - 1, middle2 + 1, maxDepth, "2",
							startTime, xboard, post, computerWhite);
						s1.start();
						s2.start();
						if (maxDepth > 1) // the first loop must be always finished
						{
							s1.join(Math.max(moveTime - (System.currentTimeMillis() - startTime), 1));
							s2.join(Math.max(moveTime - (System.currentTimeMillis() - startTime), 1));
							// max to be sure that the wait time is always positive
							s1.stopIt();
							s2.stopIt();
						}
						s1.join();
						s2.join();
	
						if (s1.getLastRunFinished() && !s2.getLastRunFinished())
						{
							if (s1.getBestValue() == middle1)
							{
								bestMove = s1.getBestMove();
								e = s1.getBestValue();
								break;
							}
							else
								break;
						}
						else if (!s1.getLastRunFinished() && s2.getLastRunFinished())
						{
							if (s2.getBestValue() == middle2)
							{
								bestMove = s2.getBestMove();
								e = s2.getBestValue();
								break;
							}
							else
								break;
						}
						else if (!s1.getLastRunFinished() && !s2.getLastRunFinished())
						{
							break;
						}
						else if (s1.getBestValue() == middle1)
						// s1.getLastRunFinished() && s2.getLastRunFinished()
						{
							bestMove = s1.getBestMove();
							e = s1.getBestValue();
							break;
						}
						else if (s2.getBestValue() == middle2)
						{
							bestMove = s2.getBestMove();
							e = s2.getBestValue();
							break;
						}
						else if (s1.getBestValue() <= middle1 - 1)
						{
							beta = s1.getBestValue();
							middle1 = alpha + (beta - alpha) / 3;
							middle2 = alpha + 2 * (beta - alpha) / 3;
						}
						else if (s2.getBestValue() >= middle2 + 1)
						{
							alpha = s2.getBestValue();
							middle1 = alpha + (beta - alpha) / 3;
							middle2 = alpha + 2 * (beta - alpha) / 3;
						}
						else if (s1.getBestValue() >= middle1 + 1 && s2.getBestValue() <= middle2 - 1)
						{
							alpha = s1.getBestValue();
							beta = s2.getBestValue();
							middle1 = alpha + (beta - alpha) / 3;
							middle2 = alpha + 2 * (beta - alpha) / 3;
						}
						else
						{
							System.out.println("Should not have happend (threads 6)!");
							log.println("Output:");
							log.println("Should not have happend (threads 6)!");
						}
					}
	
					if (e >= Search.CHECKMATE_VALUE)
						break;
	
					maxDepth++;
				}
			}
			else if (numberOfThreads == 7) // one thread, sequencial search
			{
				while (3 * moveTime / 4 - (System.currentTimeMillis() - startTime) > 0 ||
					maxDepth == 1)
				{
					int direction = 0;
					while (3 * moveTime / 4 - (System.currentTimeMillis() - startTime) > 0)
					{
						s1 = new Search(p, e - 1, e + 1, maxDepth, "1",
							startTime, xboard, post, computerWhite);
						s1.start();
						if (maxDepth > 1) // the first loop must be always finished
						{
							s1.join(Math.max(moveTime - (System.currentTimeMillis() - startTime), 1));
							// max to be sure that the wait time is always positive
							s1.stopIt();
						}
						s1.join();
	
						if (!s1.getLastRunFinished()) // not enough time
							break;
	
						if (s1.getBestValue() == e)
						{
							bestMove = s1.getBestMove();
							e = s1.getBestValue();
							break;
						}
						else if (s1.getBestValue() == e - 1 && direction == 1)
						{
							bestMove = s1.getBestMove();
							e = s1.getBestValue();
							break;
						}
						else if (s1.getBestValue() == e + 1 && direction == -1)
						{
							bestMove = s1.getBestMove();
							e = s1.getBestValue();
							break;
						}
						else if (s1.getBestValue() <= e - 1)
						{
							direction = -1;
							e = s1.getBestValue() - 1;
						}
						else if (s1.getBestValue() >= e + 1)
						{
							direction = 1;
							e = s1.getBestValue() + 1;
						}
						else
						{
							System.out.println("Should not have happend (threads 7)!");
							log.println("Output:");
							log.println("Should not have happend (threads 7)!");
						}
					}
	
					if (e >= Search.CHECKMATE_VALUE)
						break;
	
					maxDepth++;
				}
			}
			else if (numberOfThreads == 8) // two threads, standard search with parallel window search
			{
				while (3 * moveTime / 4 - (System.currentTimeMillis() - startTime) > 0 ||
					maxDepth == 1)
				{
					alpha = e - SEARCH_MARGIN;
					beta = e + SEARCH_MARGIN;
					gamma = -Search.INFINITY;
					delta = Search.INFINITY;
					s1 = new Search(p, alpha, beta, maxDepth, "1",
						startTime, xboard, post, computerWhite);
					s2 = new Search(p, gamma, delta, maxDepth, "2",
						startTime, xboard, post, computerWhite);
					s1.start();
					s2.start();
					if (maxDepth > 1) // the first loop must be always finished
					{
						s1.join(Math.max(moveTime - (System.currentTimeMillis() - startTime), 1));
						// max to be sure that the wait time is always positive
						s1.stopIt();
					}
					s1.join();
	
					if (!s1.getLastRunFinished()) // not enough time
					{
						s2.stopIt();
						if (s2.getLastRunFinished()) // s2 faster than s1 - very rare
						{
							bestMove = s2.getBestMove();
							e = s2.getBestValue();
						}
						break;
					}
					else if (s1.getBestValue() > alpha && s1.getBestValue() < beta) // found
					{
						s2.stopIt();
						bestMove = s1.getBestMove();
						e = s1.getBestValue();
					}
					else // wait for the second thread
					{
						if (maxDepth > 1) // the first loop must be always finished
						{
							s2.join(Math.max(moveTime - (System.currentTimeMillis() - startTime), 1));
							// max to be sure that the wait time is always positive
							s2.stopIt();
						}
						s2.join();
	
						if (!s2.getLastRunFinished()) // not enough time
							break;
	
						bestMove = s2.getBestMove();
						e = s2.getBestValue();
					}
	
					if (e >= Search.CHECKMATE_VALUE)
						break;
	
					maxDepth++;
				}
			}
		}

		numberOfMoves++;
		if (levelMoves != 0 && numberOfMoves == levelMoves)
		{
			numberOfMoves = 0;
			timeLeft = 60 * levelTime;
		}
		else
			timeLeft -= (moveTime + 500) / 1000;

		if (bestMove > 0)
		{
			System.out.println("move " + Position.moveToString(bestMove, computerWhite));
			log.println("Output:");
			log.println("move " + Position.moveToString(bestMove, computerWhite));
		}
		else if (bestMove == Search.CHECKMATE)
		{
			if (!xboard)
			{
				System.out.println("Checkmate");
				log.println("Output:");
				log.println("Checkmate");
			}
		}
		else if (bestMove == Search.STALEMATE)
		{
			if (!xboard)
			{
				System.out.println("Stalemate");
				log.println("Output:");
				log.println("Stalemate");
			}
		}

		long calculationTime = System.currentTimeMillis() - startTime;
		if (!xboard)
		{
			System.out.println("Elapsed time: " + Long.toString(calculationTime) + "ms");
			log.println("Output:");
			log.println("Elapsed time: " + Long.toString(calculationTime) + "ms");
		}

		if (bestMove > 0)
		{
			p.makeMove(bestMove, Position.WHITE_QUEEN);
			// if it is a promotion, then it must be a queen
			Search.storeHashCode(p.getHashCode());

			moveOfWhite = !moveOfWhite;
		}

		Search.clearTT();

		if (ponder)
		{
			Position pTmp = p.duplicatePosition();
			pTmp.invertPosition();
			if (numberOfThreads % 2 == 1)
			{
				s1 = new Search(pTmp, -Search.INFINITY, Search.INFINITY, maxDepth + 1, "1\"",
					startTime, xboard, post, !computerWhite);
				s1.start();
			}
			else
			{
				s1 = new Search(pTmp, -Search.INFINITY, e, maxDepth + 1, "1\"",
					startTime, xboard, post, !computerWhite);
				s2 = new Search(pTmp, e, Search.INFINITY, maxDepth + 1, "2\"",
					startTime, xboard, post, !computerWhite);
				s1.start();
				s2.start();
			}
		}

		if (!computerWhite)
			p.invertPosition();

		if (!xboard)
			displayCmd();

		stringMove = Position.moveToString(bestMove, computerWhite);
		if (computerWhite)
		{
			gamePGN += ' ' + Integer.toString(moveNumber) + ". " + stringMove;
			openingMoves += stringMove;
		}
		else
		{
			gamePGN += ' ' + stringMove;
			moveNumber++;
			openingMoves += stringMove;
		}

		if (!xboard)
		{
			System.out.println("On my clock is: " +
				Integer.toString(timeLeft / 60) + "min. " +
				Integer.toString(timeLeft - 60 * (timeLeft / 60)) + "s.");
			System.out.println("If there is a difference use the time command" +
				" to correct the remaining time.");
			log.println("Output:");
			log.println("On my clock is: " +
				Integer.toString(timeLeft / 60) + "min. " +
				Integer.toString(timeLeft - 60 * (timeLeft / 60)) + "s.");
			log.println("If there is a difference use the time command" +
				" to correct the remaining time.");
		}
	}

	/**
	 * Stops thinking on oponents time (if any).
	 */
	public void stopThinking()
	{
		if (ponder)
		{
			if (numberOfThreads % 2 == 1)
			{
				if (s1 != null)
					s1.stopIt();
			}
			else
			{
				if (s1 != null)
					s1.stopIt();
				if (s2 != null)
					s2.stopIt();
			}
		}
	}
}

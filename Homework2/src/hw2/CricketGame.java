package hw2;

import api.Outcome;

/**
 * Rithwik Gokhale 
 * ID: 831106304 
 * ComS 227 
 * Homework Assignment 2
 * 
 * @author rithw
 *
 */
public class CricketGame {

	// This is an int variable which decides which side is batting. side 0 or 1 and
	// changes the integer value accordingly
	private int side;
	// this integer variable stores the runs for team0
	private int runs0;
	// this variable stores the runs for team 1
	private int runs1;
	// this int value is used to decide which side will be batting
	private int currentside;
	// this variable stores the number of players who got out in that particular
	// innings only
	private int outplayers;
	// this variable stores the constructor value of bowls per over from the user
	private int bowlsperover;
	// this variable stores the number of bowls which have been bowled in the over
	// or the innings
	private int numbowls;
	// this variable stores the number of overs in the innings. the value is taken
	// from the constructor user input
	private int overinnings;
	// this variable stores the number of overs which have been bowled in that
	// particular innings
	private int numover;
	// this boolean variable checks if the game has ended or not
	private boolean endgame;
	// this boolean variable checks if the game is currently in play or not. Note
	// that this is different than the game being ended
	private boolean inplay;
	// this value stores the total number of innings which will be present in the
	// game. this is taken from the constructor value.
	private int totinnings;
	// this stores the number of innings which have been played so far
	private int numinnings;
	// this boolean checks if the player is currently running or not
	private boolean isrunning;
	// this stores the number of players who are playing for each team. the value is
	// acquired from the constructor input.
	private int numplayers;

	/**
	 * This is the empty constructor which uses the public default values
	 */
	public CricketGame() {
	}

	/**
	 * This constructor creates a cricket game instance which takes in the user
	 * input and sets the parameters accordingly. The parameters can be seen below.
	 * 
	 * @param givenBowlsPerOver
	 * @param givenOversPerInnings
	 * @param givenTotalInnings
	 * @param givenNumPlayers
	 */
	public CricketGame(int givenBowlsPerOver, int givenOversPerInnings, int givenTotalInnings, int givenNumPlayers) {
		totinnings = givenTotalInnings;
		bowlsperover = givenBowlsPerOver;
		overinnings = givenOversPerInnings;
		if (totinnings % 2 == 1) {
			totinnings++;
		}
		numplayers = givenNumPlayers;
	}

	/**
	 * This is the biggest method of this class which stores the logic for most of
	 * the methods of this class. The main part of this method refers to the outcome
	 * object present in a different class. There are a number of different outcomes
	 * and each of the outcomes will have a different impact on the runs, the
	 * players who got out and the number of balls and overs and innings in the
	 * game.
	 * 
	 * Bowls the ball and updates the game state depending on the given outcome.
	 * Note that after a call to this method with Outcome.HIT, the ball will be in
	 * play (isInPlay() returns true).
	 * 
	 * NOTE THAT WHILE THE LOGIC FOR MANY OTHER METHODS EXISTS IN THIS METHOD, IT
	 * WILL BE EXPLAINED IN THE APPROPRIATE JAVADOC
	 * 
	 * @param outcome
	 */
	public void bowl(Outcome outcome) {
		int temprun = 0;
		if (outcome == Outcome.WICKET) {
			outplayers++;
			numbowls++;
			if (inplay == true) {
				outplayers = outplayers - 1;
			}
		} else if (outcome == Outcome.LBW) {
			numbowls++;
			outplayers++;
		} else if (outcome == Outcome.CAUGHT_FLY) {
			numbowls++;
			outplayers++;
		} else if (outcome == Outcome.WIDE) {
			temprun++;
		} else if (outcome == Outcome.NO_BALL) {
			temprun++;
		} else if (outcome == Outcome.BOUNDARY_SIX) {
			temprun += 6;
			numbowls++;
			if (endgame == true) {
				temprun = temprun - 6;
			}
			if (inplay == true) {
				temprun = temprun - 6;
			}
		} else if (outcome == Outcome.BOUNDARY_FOUR) {
			temprun += 4;
			numbowls++;
		} else if (outcome == Outcome.HIT) {
			numbowls++;
			inplay = true;
		}

		if (currentside == 0) {
			runs0 = runs0 + temprun;
		} else {
			runs1 = runs1 + temprun;
		}
		if (bowlsperover == numbowls) {
			numover++;
			numbowls = 0;
		}
		if (numover == overinnings) {
			numinnings++;
			numover++;
			outplayers = 0;
			numover = 0;
		}
		if (outplayers == numplayers - 1) {
			numinnings++;
			outplayers = 0;
			numover = 0;
			numbowls = 0;
		}
		if (numinnings == totinnings) {
			endgame = true;
		} else {
			endgame = false;
		}
		side = numinnings % 2;
		if (side == 0) {
			currentside = 0;
		} else {
			currentside = 1;
		}
		if (numinnings >= 3) {
			if (runs0 > runs1) {
				endgame = true;
			}
			if (runs1 > runs0) {
				endgame = true;
			}
		}

	}

	/**
	 * this method checks which side is batting and returns the int value
	 * accordingly. 0 for team 0 and 1 for team 1. this is decided by performing
	 * modulus division on the number of innings in this game. If there is no
	 * remainder then 0 is batting and vice versa.
	 * 
	 * @return currentside
	 */
	public int whichSideIsBatting() {

		return currentside;

	}

	/**
	 * this method returns true if the game has ended. this is checked on two
	 * different parameters. The first one is whether one team has a higher score
	 * than the other. The other and the parameter which is checked first is whether
	 * the number of innings which have been players so far is equal to the total
	 * number of innings in this game. this is the value taken from the constructor
	 * 
	 * 
	 * @return endgame
	 */
	public boolean isGameEnded() {
		return endgame;
	}

	/**
	 * this is a simple method which returns the number of balls which has been
	 * bowled at that exact moment in the game. note that this value is reset at the
	 * over count or at the innings count or at a new game
	 * 
	 * @return numbowls
	 */
	public int getBowlCount() {
		return numbowls;

	}

	/**
	 * this method returns the number of innings which have been completed in the
	 * game so far. this value is decided by looking at the number of balls per over
	 * and the number of balls which have been bowled so far and decided
	 * accordingly. every time the over per innings count reaches the limit or if
	 * all the players have been eliminated then the number of innings will change
	 * 
	 * @return numinnings
	 */
	public int getCompletedInnings() {
		return numinnings;

	}

	/**
	 * this method returns the number of players who have gotten out in this
	 * particular innings. this value is added over the course of this class. and
	 * the value also gets reset when the innings end or the game ends and the other
	 * team is to bat next. The value gets a +1 whenever the batting team loses a
	 * wicket, gets and LBW or has a mid air catch
	 * 
	 * @return outplayers
	 */
	public int getOuts() {
		return outplayers;

	}

	/**
	 * this method simply returns the number of completed overs per innings. the
	 * value is reset at the end of the innings or the game.
	 * 
	 * @return numover
	 */
	public int getOverCount() {
		return numover;

	}

	/**
	 * this method takes the boolean parameter of if batting side is true to
	 * determine which team is batting and then returns their score. the runs value
	 * is added over in the bowl method whenever the player runs, hits a 4, 6 or if
	 * there is a wide or no ball
	 * 
	 * Returns the score for one of the two sides.
	 * 
	 * @param battingSide
	 * @return runs
	 */
	public int getScore(boolean battingSide) {
		if (battingSide == true) {
			if (currentside == 0) {
				return runs0;
			} else {
				return runs1;
			}
		} else {
			if (currentside == 1) {
				return runs0;
			} else {
				return runs1;
			}
		}

	}

	/**
	 * This is a void method where the player who is currently trying to run across
	 * the pitch gets run out. This can only happen when the player is running, the
	 * game has not ended and is in play. And when the player gets run out, the game
	 * is no longer in play and the out player variable will get an addition
	 */
	public void runOut() {
		if (endgame == false || inplay == true) {
			if (isrunning == true) {
				outplayers++;
				isrunning = false;
				inplay = false;
			} else {
				outplayers += 0;
			}
		}
	}

	/**
	 * this is another void method where the player has completed his run across the
	 * pitch and is now back in the safe zone. this can only happen when the game is
	 * still going on and the player is running at that moment when the method is
	 * called. When this method is completed the game is not in play, the player is
	 * no longer running and a run gets added to the batting side
	 * 
	 */
	public void safe() {

		if (endgame == false || inplay == true) {
			if (isrunning == true) {
				runs0++;
				runs1++;
				isrunning = false;
				inplay = false;
			} else {
				isrunning = false;
				inplay = false;
			}
		}
	}

	/**
	 * This is the try run method where the player attempts to run across the pitch.
	 * this method can only be called when the game is going on. if the player is
	 * already running then the run is completed and a run is added to the team
	 * score. if the bats man was not already running then he will attempt to run
	 * when the method is called
	 * 
	 */
	public void tryRun() {
		int tempruns = 0;
		if (endgame == false && inplay == true) {
			if (isrunning == true) {
				tempruns += 1;
			} else {
				isrunning = true;
			}
			if (currentside == 0) {
				runs0 = runs0 + tempruns;
			} else {
				runs1 = runs1 + tempruns;
			}
		} else {
			isrunning = false;
		}

	}

	/**
	 * this is a boolean method which checks if the game is currently in play. the
	 * game is only in play if the end game method has not returned a true value.
	 * Returns true if the ball is currently in play. The ball is in play directly
	 * following a call to bowl(Outcome.HIT) and is taken out of play by a
	 * subsequent call to safe or runOut.
	 * 
	 * @return inplay
	 */
	public boolean isInPlay() {
		return inplay;

	}

	/**
	 * Returns true if bats men are currently running. Bats men are running directly
	 * following a call to tryRun and remain in a running state until a subsequent
	 * call to safe or runOut.
	 * 
	 * @return isrunning
	 */
	public boolean isRunning() {
		return isrunning;

	}

}

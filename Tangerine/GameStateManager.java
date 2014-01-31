//import Audio.JukeBox;
//import GameState.Levels.*;

public class GameStateManager {
	
	private GameState[] gameStates;
	private int currentState;
	
	public int score = 0;

	public int currentLevel = 1;
	
	public static final int NUMGAMESTATES = 20;
	public static final int LEVEL1STATE = 0;
	public static final int LOADINGSTATE = 19;
	
	
	public GameStateManager(){
		//JukeBox.init();
		
		gameStates = new GameState[NUMGAMESTATES];
		
		currentState = LOADINGSTATE;
		loadState(currentState);
		
	}
	
	private void loadState(int state){
		if(state == LOADINGSTATE){
			gameStates[state] = new StateLoadingState(this);
		}if(state == LEVEL1STATE){
			gameStates[state] = new StateRunningState(this);
		}
		
	}
	
	private void unloadState(int state){
		gameStates[state] = null;
	}
	
	public int getCurrentLevel(){return currentLevel;}
	public void nextLevel(){currentLevel++;}
	public void setCurrentLevel(int i){currentLevel = i;}
	
	public void setState(int state){
		unloadState(currentState);
		currentState = state;
		loadState(currentState);
	}

	public int getScore(){
		return score;
	}
	public void addScore(int s){
		score += s;
	}
	public void setScore(int s){
		score = s;
	}

	public void update(){
		try{
		gameStates[currentState].update();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public void draw(java.awt.Graphics2D g){
		try{
		gameStates[currentState].draw(g);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}

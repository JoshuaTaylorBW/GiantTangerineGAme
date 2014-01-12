package GameState;

//import Audio.JukeBox;
//import GameState.Levels.*;

public class GameStateManager {
	
	private GameState[] gameStates;
	private int currentState;
	
	public int currentLevel = 41;
	
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
			gameStates[state] = new LoadingState(this);
		}if(state == LEVEL1STATE){
			gameStates[state] = new RunningState(this);
		}
		
	}
	
	private void unloadState(int state){
		gameStates[state] = null;
	}
	
	public int getCurrentLevel(){return currentLevel;}
	
	public void setState(int state){
		unloadState(currentState);
		currentState = state;
		loadState(currentState);
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

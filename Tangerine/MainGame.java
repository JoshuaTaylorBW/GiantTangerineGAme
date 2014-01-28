import javax.swing.JFrame;

public class MainGame {
	
	public static void main(String[] args) {
		
		JFrame window = new JFrame("Amodo");
		window.setContentPane(new MainGamePanel());
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.pack();
		window.setVisible(true);
		
	}
	
}

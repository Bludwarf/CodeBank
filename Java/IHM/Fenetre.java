package IHM;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Fenetre extends JPanel {

	private static final long serialVersionUID = 1L;
	
	/**
	 * This is the default constructor
	 */
	public Fenetre() {
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		
	}

	/**
	 * Affichage de la fenêtre
	 */
	public static void main(String[] args) {
		// Lancement un processus qui gère la fenêtre
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Fenetre fen = new Fenetre();
				fen.setVisible(true);				
			}
		});
	}
}

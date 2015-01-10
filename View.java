import java.awt.*;

import javax.swing.*;

import java.awt.event.*;
import java.io.File;
import java.io.RandomAccessFile;

@SuppressWarnings("serial")
public class View extends JFrame implements ActionListener, KeyListener {
	
	Model game;
	//Color[][][] rubiksCubeView = new Color[6][3][3];
	int faceAvant = 0;   // 0 : blanc
	int faceDroite = 1;  // 1 : bleu
	int faceHaut = 2;    // 2 : orange
	int faceArriere = 3; // 3 : jaune
	int faceGauche = 4;  // 4 : vert 
	int faceBas = 5;     // 5 : rouge
	
	JPanel panelGeneral;
	JPanel[][] jp;
	
	File fileChosen = null;
	
	public View(int largeur, int hauteur){ //on cree la fenetre
		super("Rubik's Cube");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(largeur, hauteur);
		setLocationRelativeTo(null); // La fenêtre apparai au centre de l'écran

		creerMenus();  
		creerInterface();
		
		setVisible(true);		//show();
	}
	
	private void creerMenus() 
	{
		// Menu Fichier
        JMenu menuFichier = new JMenu("Fichier");
        menuFichier.setMnemonic(KeyEvent.VK_F);
        
        ActionListener a1 = new ActionListener(){
            public void actionPerformed(ActionEvent e){
            	for(int i=0;i<6;i++)
    			{
    				for(int j=0;j<9;j++)
    				{
    					game.InitialisationGrille();
    				}
    			}
            	actualiserFaceAvant();
            	fileChosen = null;
            }
        };
        ajouterItem("Nouveau", menuFichier, a1, KeyEvent.VK_N); // Nouveau reset la grille
        
        ActionListener a2 = new ActionListener(){
            public void actionPerformed(ActionEvent e){
            	chargerFichier("./Rubik's Cube Database");
            }
        };
        ajouterItem("Charger", menuFichier, a2, KeyEvent.VK_L);
        
        ActionListener a3 = new ActionListener(){
            public void actionPerformed(ActionEvent e){
            	enregistrerFichier();
            }
        };
        ajouterItem("Enregistrer", menuFichier, a3, KeyEvent.VK_S);
        
        ActionListener a4 = new ActionListener(){
            public void actionPerformed(ActionEvent e){
            	enregistrerSousFichier();
            }
        };
        ajouterItem("Enregistrer sous", menuFichier, a4);
        
        ActionListener a5 = new ActionListener(){
            public void actionPerformed(ActionEvent e){
            	System.exit(0);
            }
        };
        ajouterItem("Quitter", menuFichier, a5, KeyEvent.VK_Q);

        // Menu Action
        JMenu menuResoudre = new JMenu("Résoudre");
        menuResoudre.setMnemonic(KeyEvent.VK_R);
        
        ActionListener a10 = new ActionListener(){
            public void actionPerformed(ActionEvent e){
            	//chargerFichier("./Sudoku Database/Facile");
            }
        };
        ajouterItem("Résoudre complètement", menuResoudre, a10);
        
        ActionListener a11 = new ActionListener(){
            public void actionPerformed(ActionEvent e){
            	//chargerFichier("./Sudoku Database/Moyen");
            }
        };
        ajouterItem("Faire une face", menuResoudre, a11);
        
         // Relier les menus à la fenêtre
		JMenuBar barreDeMenu = new JMenuBar();
		barreDeMenu.add(menuFichier);
		barreDeMenu.add(menuResoudre);
		this.setJMenuBar(barreDeMenu);
	}
	
	private void ajouterItem(String intitule, JMenu menu, ActionListener a)
	{
        JMenuItem item = new JMenuItem(intitule);
        menu.add(item);
        item.addActionListener(a);
	}
	
	private void ajouterItem(String intitule, JMenu menu, ActionListener a, int key)
	{
        JMenuItem item = new JMenuItem(intitule);
        menu.add(item);
        item.addActionListener(a);
        item.setAccelerator(KeyStroke.getKeyStroke(key, ActionEvent.CTRL_MASK));
	}
	
	private void chargerFichier(String dossier)
	{
		// Utiliser jnlp si possible : FileOpenService fos;
		
		// Dossier d'ouverture de l'explorateur
    	JFileChooser dialogue = new JFileChooser(new File(dossier)); 
    	
    	if (dialogue.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
    		fileChosen = dialogue.getSelectedFile();
    		
    		reporterFichierDansCube(fileChosen); // Charger IHM
    	}	
	}		
	
	private void enregistrerFichier()
	{
		if (fileChosen != null)
		{
			reporterCubeDansFichier(fileChosen.toString());
		}
		else
			enregistrerSousFichier();
	}
	
	private void enregistrerSousFichier()
	{
		// Utiliser jnlp si possible : FileOpenService fos;
		
		// Dossier d'ouverture de l'explorateur
    	JFileChooser dialogue = new JFileChooser(new File("./Rubik's Cube Database")); 
    	dialogue.setSelectedFile(new File("exemple.txt"));
    	
    	if (dialogue.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
    		fileChosen = dialogue.getSelectedFile();
    		reporterCubeDansFichier(fileChosen.toString());
    	}
	}
	
	private void creerInterface()
	{
		Container c = getContentPane();
		
		game = new Model();
		Color[][] face = game.getFace(0);
		
		// On crée la face de devant
		panelGeneral = new JPanel();
		panelGeneral.setLayout(new GridLayout(3,3)); 
		
		// On crée les couleurs de la face de devant
		jp = new JPanel[3][3];
		for(int i=0;i<3;i++)
		{
			for(int j=0;j<3;j++)
			{
				jp[i][j] = new JPanel();
				jp[i][j].setBorder(BorderFactory.createEtchedBorder());
				jp[i][j].setBackground(face[i][j]);
				//jp[i][j].addActionListener(this);
				jp[i][j].setFocusable(true); // Nécessaire pour addKeyListener
				jp[i][j].addKeyListener(this);
				panelGeneral.add(jp[i][j]);
			}
		}
		
		c.add(panelGeneral);
	}
	
	public void reporterFichierDansCube(File fileChosen) {

		try {
			RandomAccessFile raf = new RandomAccessFile(fileChosen, "r");
			String ligne;
			
			for (int i = 0; (ligne = raf.readLine()) != null; ++i) 
			{
				if (i >= 6) break;
				for (int j = 0; j < ligne.length(); ++j)
				{
					if (j >= 9) break;
					char c = ligne.charAt(j);
					
					// On ne remplit pas si le char n'est pas un chiffre entre 0 et 5
					if (c == '0') 
						game.rubiksCube[i][j/3][j%3] = Color.white;
					else if (c == '1') 
						game.rubiksCube[i][j/3][j%3] = Color.blue;
					else if (c == '2') 
						game.rubiksCube[i][j/3][j%3] = Color.orange;
					else if (c == '3') 
						game.rubiksCube[i][j/3][j%3] = Color.yellow;
					else if (c == '4') 
						game.rubiksCube[i][j/3][j%3] = Color.green;
					else if (c == '5') 
						game.rubiksCube[i][j/3][j%3] = Color.red;
				}
			}
			raf.close();
			
		}
		catch (Exception e) {
			System.out.print(fileChosen + " not found: " + e.toString() + "\n");
		}
		
		actualiserFaceAvant();
	}
	
	public String reporterCubeDansFichier(String fileName) {

		try {
			
			RandomAccessFile raf = new RandomAccessFile(fileName, "rw");
			
			for(int i=0;i<6;i++)
			{
				for(int j=0;j<9;j++)
				{
					Color c = game.rubiksCube[i][j/3][j%3];
					
					if (c.equals(Color.white))
						raf.writeBytes("0");
					else if (c.equals(Color.blue)) 
						raf.writeBytes("1");
					else if (c.equals(Color.orange))
						raf.writeBytes("2");
					else if (c.equals(Color.yellow)) 
						raf.writeBytes("3");
					else if (c.equals(Color.green)) 
						raf.writeBytes("4");
					else if (c.equals(Color.red))
						raf.writeBytes("5");
					else
						raf.writeBytes("?");
				}
				if (i != 5)
					raf.writeBytes("\n");
			}
			raf.close();
		}
		catch (Exception e) {
			System.out.println(fileName + " not found: " + e.toString());
		}
		
		return fileName;
	}
	
	private void actualiserFaceAvant()
	{
		// On récupère la face avant
		Color[][] face = game.getFace(faceAvant);
		
		// On l'affiche
		for(int i=0;i<3;i++)
		{
			for(int j=0;j<3;j++)
			{
				jp[i][j].setBackground(face[i][j]);
			}
		}
	}
	
	public void actionPerformed (ActionEvent e) {
	}
	
	public void keyPressed(KeyEvent evt) { 
		
		if (evt.getKeyCode() == KeyEvent.VK_LEFT || evt.getKeyCode() == KeyEvent.VK_KP_LEFT) 
		{	
			// Les faces changent de place
			int temp = faceAvant;
			faceAvant = faceGauche;
			faceGauche = faceArriere;
			faceArriere = faceDroite;
			faceDroite = temp;
			
			// Les faces changent d'orientation
			game.setFace(faceHaut, game.turnFace(game.getFace(faceHaut)));
			game.setFace(faceHaut, game.turnFace(game.getFace(faceHaut)));
			game.setFace(faceHaut, game.turnFace(game.getFace(faceHaut)));

			game.setFace(faceBas, game.turnFace(game.getFace(faceBas)));
			
			actualiserFaceAvant();
		}
		if (evt.getKeyCode() == KeyEvent.VK_RIGHT || evt.getKeyCode() == KeyEvent.VK_KP_RIGHT) 
		{
			// Les faces changent de place
			int temp = faceAvant;
			faceAvant = faceDroite;
			faceDroite = faceArriere;
			faceArriere = faceGauche;
			faceGauche = temp;

			// Les faces changent d'orientation
			game.setFace(faceHaut, game.turnFace(game.getFace(faceHaut)));
			
			game.setFace(faceBas, game.turnFace(game.getFace(faceBas)));
			game.setFace(faceBas, game.turnFace(game.getFace(faceBas)));
			game.setFace(faceBas, game.turnFace(game.getFace(faceBas)));
			
			actualiserFaceAvant();
		}
		if (evt.getKeyCode() == KeyEvent.VK_UP || evt.getKeyCode() == KeyEvent.VK_KP_UP) 
		{
			// Les faces changent de place
			int temp = faceAvant;
			faceAvant = faceHaut;
			faceHaut = faceArriere;
			faceArriere = faceBas;
			faceBas = temp;

			// Les faces changent d'orientation
			game.setFace(faceHaut, game.turnFace(game.getFace(faceHaut)));
			game.setFace(faceHaut, game.turnFace(game.getFace(faceHaut)));
			
			game.setFace(faceArriere, game.turnFace(game.getFace(faceArriere)));
			game.setFace(faceArriere, game.turnFace(game.getFace(faceArriere)));
			
			game.setFace(faceGauche, game.turnFace(game.getFace(faceGauche)));
			
			game.setFace(faceDroite, game.turnFace(game.getFace(faceDroite)));
			game.setFace(faceDroite, game.turnFace(game.getFace(faceDroite)));
			game.setFace(faceDroite, game.turnFace(game.getFace(faceDroite)));
			
			actualiserFaceAvant();
		}
		if (evt.getKeyCode() == KeyEvent.VK_DOWN || evt.getKeyCode() == KeyEvent.VK_KP_DOWN) 
		{
			// Les faces changent de place
			int temp = faceAvant;
			faceAvant = faceBas;
			faceBas = faceArriere;
			faceArriere = faceHaut;
			faceHaut = temp;

			// Les faces changent d'orientation
			game.setFace(faceBas, game.turnFace(game.getFace(faceBas)));
			game.setFace(faceBas, game.turnFace(game.getFace(faceBas)));
			
			game.setFace(faceArriere, game.turnFace(game.getFace(faceArriere)));
			game.setFace(faceArriere, game.turnFace(game.getFace(faceArriere)));

			game.setFace(faceGauche, game.turnFace(game.getFace(faceGauche)));
			game.setFace(faceGauche, game.turnFace(game.getFace(faceGauche)));
			game.setFace(faceGauche, game.turnFace(game.getFace(faceGauche)));
			
			game.setFace(faceDroite, game.turnFace(game.getFace(faceDroite)));
			
			actualiserFaceAvant();
		}

	}
	
	public void keyReleased(KeyEvent evt) { // méthode non implémentée mais
		// necessaire pour compiler

	}
	
	public void keyTyped(KeyEvent evt) { // méthode non implémentée mais
		// necessaire pour compiler

	}
	
	public static void main(String[] args)
	{
        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
            	new View(800, 800);
            }
        });
	}
}

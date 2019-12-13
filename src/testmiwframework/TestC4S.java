package testmiwframework;
import algorithms.IMIW;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.awt.event.*; 
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import pacsinterface.SecurityAPIGateway;
public class TestC4S extends JFrame implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	BufferedImage hostImage,stegoImage,imageWatermark;
	String textWatermark,disease,imageModality="Xray",imageFileLink;
	JComboBox diseaseDropDown;
	boolean[] services = new boolean[4];// for the four main services 
	SecurityAPIGateway securitygateway;
	JLabel labelwatermarkImage;
	
	//Select Security services to request for
	Checkbox integrityChk,authenticationChk, copyrightChk, privacyChk;
	public TestC4S() throws IOException {
		setSize(750, 650);
		setTitle("Using C4S to Test MIW Framework");
		
		 //Creating the panel at bottom and adding components
        JPanel panel = new JPanel(); 
        JButton getHost = new JButton("New Host Image");
        getHost.addActionListener(this);
        
        JButton newImageWmkBtn = new JButton("New Image Watermark"); // Get image watermark to be used
        newImageWmkBtn.addActionListener(this);
        JButton newTextWmkBtn = new JButton("New Text Watermark");
        newTextWmkBtn.addActionListener(this);
        JButton embedBtn = new JButton("Embed");//Start Performing Algorithm Selection and actual Watermarking
        embedBtn.addActionListener(this);
        panel.add(getHost); // Components Added using Flow Layout
        panel.add(newImageWmkBtn); // Components Added using Flow Layout
        panel.add(newImageWmkBtn);
        panel.add(newTextWmkBtn);
        panel.add(embedBtn);
        
        //Radiologist to select type of security service from MIW
        integrityChk = new Checkbox("Integrity",true);  
        authenticationChk = new Checkbox("Authentication"); 
        copyrightChk = new Checkbox("Copyright"); 
        privacyChk = new Checkbox("Privacy"); 
        
      //Create a list of diseases that could be diagnosed automatically using image biomarkers.
      String[] diseaseList = { "Pneumonia", "Melanoma", "Alzheimer", "Glaucoma", "Fracture" };
      diseaseDropDown = new JComboBox(diseaseList);
      diseaseDropDown.setSelectedIndex(0);
      diseaseDropDown.addActionListener(this);
        
          
                
      //Creating the Panels at Top, Centre and adding components
        JPanel panelContainerNorth = new JPanel(); // North Panel
        JPanel panelContainerCentral = new JPanel(); //Central Panel
        JPanel panelNorth1 = new JPanel(); //Contains host image
        panelNorth1.setLayout(new BoxLayout(panelNorth1, BoxLayout.Y_AXIS));
        //addCheckBox("Integrity", panelNorth1);
        //addAButton("Button 2", panelNorth1);
        //addAButton("Button 3", panelNorth1);
        //addAButton("Long-Named Button 4", panelNorth1);
        panelNorth1.add(integrityChk);  
        panelNorth1.add(authenticationChk,BorderLayout.NORTH);
        panelNorth1.add(copyrightChk);  
        panelNorth1.add(privacyChk);
        panelNorth1.add(diseaseDropDown);
        
        JPanel panelNorth2 = new JPanel();
        
        JPanel panelNorth3 = new JPanel();
        JPanel panelWatermarked = new JPanel();
        JPanel panelQualityParameters = new JPanel();
        JLabel labelStegoImage = new JLabel("Stego Image will be found here after watermarking");
        JLabel labelQParameters = new JLabel("Quality Paramters Displayed Here");
        
     // reads input image
        String PicURL = "C:\\Users\\peze\\Desktop\\Presentation Pix\\Glaucoma.jpg";
        // extracts extension of output file
        String formatName =PicURL.substring(PicURL
                .lastIndexOf(".") + 1); // Get file format or type of file
        
       hostImage = this.readImage(PicURL);
       
        // Resize hostImage to 1/4 of its original size
        Image newImage = hostImage.getScaledInstance(hostImage.getWidth()/4, getHeight()/4, Image.SCALE_DEFAULT);
        //display resized version as imageicon on a label
        ImageIcon image = new ImageIcon(newImage);
        JLabel labelHostImage = new JLabel("Host Image");
        labelHostImage.setIcon(image);
        
        labelwatermarkImage = new JLabel("Image Watermark");
        //Read Text Watermark
        String textFilePath="C:\\Users\\peze\\Documents\\MATLAB\\emr.txt";
        JTextArea watermarkText = new JTextArea();
        textWatermark = readTextFile(textFilePath);
        JLabel wmkLabel = new JLabel("Text Watermark");
        watermarkText.setRows(10);watermarkText.setColumns(20);
        watermarkText.setLineWrap(true);
        watermarkText.setFont(new Font("Serif", Font.ITALIC, 10));
        
        watermarkText.setWrapStyleWord(true);
        watermarkText.setText(textWatermark);//Display the text watermark in the Text Area of the UI
        JScrollPane scroll = new JScrollPane(watermarkText);
		
	   // setResizable(false);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	   
	    
	  
        panelNorth1.add(labelHostImage);
        panelNorth2.add(labelwatermarkImage);
        panelNorth3.add(watermarkText);
        panelNorth3.add(wmkLabel);
        panelWatermarked.add(labelStegoImage);
        panelQualityParameters.add(labelQParameters); 
        panelNorth1.setSize(50,50);
        panelNorth1.setOpaque(true);
        panelNorth1.setBackground(Color.CYAN);
        panelNorth3.setBackground(Color.CYAN);
        panelWatermarked.setBackground(Color.CYAN);
        panel.setBackground(Color.ORANGE);
        panelQualityParameters.setBackground(Color.CYAN);
        
        panelContainerNorth.add(panelNorth1);
        panelContainerNorth.add(panelNorth2);
        panelContainerNorth.add(panelNorth3);
        panelContainerCentral.add(panelWatermarked);
        panelContainerCentral.add(panelQualityParameters);
        panelContainerNorth.setOpaque(true);
        panelContainerNorth.setBackground(Color.WHITE);
        
      //Adding Components to the frame.
        this.getContentPane().add( java.awt.BorderLayout.CENTER,scroll);
        this.getContentPane().add(java.awt.BorderLayout.CENTER,panelContainerCentral);
        this.getContentPane().add(BorderLayout.SOUTH, panel);
        this.getContentPane().add(BorderLayout.NORTH, panelContainerNorth);
        
        this.setVisible(true);
	    
		
	}
	
	BufferedImage readImage(String PicURL) throws IOException {
		File inputFile = new File(PicURL);
        //Read host image File
         BufferedImage image;
         image = ImageIO.read(inputFile);
         return image;
	}
	
	String readTextFile(String textFilePath) throws IOException {
		File file = new File(textFilePath);
		BufferedReader br = new BufferedReader(new FileReader(file)); 
		  
		  String strText="",st; 
		  while ((st = br.readLine()) != null) 
			  strText=strText + st;
		  return strText;
		  } 
	private static void addCheckBox(String text, Container container) {
		Checkbox check = new Checkbox(text);
		//check.setAlignmentX(Component.CENTER_ALIGNMENT);
        container.add(check);
    }
	private static void addAButton(String text, Container container) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        container.add(button);
    }
		
	
	
	public static void main(String[] args) throws IOException {
		// Execute the written test here
		TestC4S tc4s = new TestC4S();

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// Handle events in the GUI
		Object o = e.getSource();
		o.getClass();
		if (e.getActionCommand().equals("New Host Image")) {
			//Open dialog box to read new host image to be used
			imageFileLink = this.getFilenameFromDialog();
	        File img=new File(imageFileLink);
			
			System.out.println("Selected File:" + this.imageFileLink);
		}
		else if(e.getActionCommand().equals("Embed")){
			//Get the requested Security Services, images and watermarks 
			//and send them to PACS SecurityAPI Gateway. This class in 
			//PACS interface will add disease being diagnosed, modality 
			//of the image and other information required to make a request
			services[0] = integrityChk.getState();
			services[1] = authenticationChk.getState();
			services[2] = copyrightChk.getState();
			services[3] = privacyChk.getState();
			
			disease = diseaseDropDown.getSelectedItem().toString();
			//Create Object of SecurityAPIGateway in PACSInterface using 
			// these retrieved data as parameters to the constructor.
			
			securitygateway = new SecurityAPIGateway(hostImage, imageWatermark, textWatermark,  disease, services,imageModality);
			
			System.out.println("Disease:" + disease);		
		}
		else if(e.getActionCommand().equals("New Image Watermark")) {
			//Call file chooser method
			imageFileLink = this.getFilenameFromDialog();
	        File img=new File(imageFileLink);
			
			if(img!=null)
			{
			try {
				this.imageWatermark=ImageIO.read(img);//Read actual image from image file
			} catch (IOException d) {
				// Catch thrown error
				d.printStackTrace();
				}
			}
			BufferedImage image1=scale(this.imageWatermark,this.imageWatermark.getType(),125,125,0.25,0.25);
			ImageIcon image = new ImageIcon(image1);
			labelwatermarkImage.setIcon(image);
	        this.repaint();
	        
		}
		else if(e.getActionCommand().equals("New Text Watermark")) {
			//Open dialog box to read new host image to be used
			String textFileLink = this.getFilenameFromDialog();
	        
			
		}
		
		
	}
	/**
	 * Get the name of image or text file to be read as cover or watermark
	 * @return
	 */
	String getFilenameFromDialog() {
		String fileLink=null;
		JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setAcceptAllFileFilterUsed(true);
        int rVal = fileChooser.showOpenDialog(null);
        if (rVal == JFileChooser.APPROVE_OPTION) {
        	fileLink=fileChooser.getSelectedFile().toString();
          //System.out.println("Image file Link:" + fileLink);
        }
        return fileLink;
	}
	/**
	 * 
	 * @param sbi
	 * @param imageType
	 * @param dWidth
	 * @param dHeight
	 * @param fWidth
	 * @param fHeight
	 * @return
	 */
	public static BufferedImage scale(BufferedImage sbi, int imageType, int dWidth, int dHeight, double fWidth, double fHeight) {
	    BufferedImage dbi = null;
	    if(sbi != null) {
	        dbi = new BufferedImage(dWidth, dHeight, imageType);
	        Graphics2D g = dbi.createGraphics();
	        //AffineTransform at = AffineTransform.getScaleInstance(fWidth, fHeight);
	        g.drawImage(sbi, 0,0,dWidth,dHeight,null);
	        g.dispose();
	        g.setComposite(AlphaComposite.Src);
	        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	        g.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
	        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
	    }
	    return dbi;
	}

}

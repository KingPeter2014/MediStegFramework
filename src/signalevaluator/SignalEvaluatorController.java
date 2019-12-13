/**
 * 
 */
package signalevaluator;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.UnsupportedEncodingException;
import java.util.Base64;

import algorithms.C4S;
import algorithms.IMIW;

/**
 * /**
 * @author peze
 *  This is a Context Class that enables us select the best MIW algorithm strategy
 *  It creates an array of the Interface MIW and stores the object of each algorithm
 *  strategy and then iteratively use the algorithms to fulfill the request from the 
 *  external system subject to the constraints imposed by the system.
 *  
 * @author peze
 *
 */
public class SignalEvaluatorController {

	/**
	 * 
	 */
	enum Servicename{Integrity,Authentication,Privacy,Copyright};
	IMIW[] thisMIWAlgorithm = new IMIW[10];// = new C4S(hostImage,textWatermark);//Initialise and run the C4S algorithm
	int maxImageWatermarkSize = 64;
	BufferedImage hostImage,stegoImage,watermarkImage;
	String textWatermark, diseaseName,imageModality;
	boolean[] services;
	byte[] encodingKey=null,binaryTextWatermark=null,binaryImageWatermark=null;
	
	
	public SignalEvaluatorController(BufferedImage hostImage,BufferedImage watermarkImage, String textWatermark, String diseaseName,boolean[] services,String imageModality) {
		// TODO Auto-generated constructor stub
		this.hostImage = hostImage;
		this.watermarkImage = watermarkImage;
		this.textWatermark = textWatermark;
		this.diseaseName = diseaseName;
		this.imageModality = imageModality;
		this.services = services;
		this.MIWAlgorithmFactory();
		System.out.println("Textwatermark in SignalEvaluator:" + this.textWatermark);
	}
	/**
	 * Create array of known Medical image watermarking algorithms in the framework
	 * 
	 */
	void MIWAlgorithmFactory() {
		//First convert image and text watermarks to binary
		if(this.textWatermark!=null)
			this.binaryTextWatermark = this.convertTextWatermarkToBinary(this.textWatermark);
		if(this.watermarkImage!=null)
			this.binaryImageWatermark = this.convertImageToBinary(this.watermarkImage);
		
		thisMIWAlgorithm[0] = new C4S();
	}
	
	IMIW selectBestAlgorithm() {
		//run pre-test on different MIW algorithm and select the one that best suits the application
		//stegoImage = c4s.encoder(hostImage, watermark, encodingkey)
		return thisMIWAlgorithm[0];
	}
	
	void secureImage(IMIW selectedMIWAlgorithm){
		//selectedMIWAlgorithm.encoder(this.hostImage,(Byte)this.textWatermark, watermark, encodingkey)
	}
	/**
	 * Get the embedding or encoding key from the selected algorithm
	 * @return
	 */
	byte[] getEncodingKey() {
		
		return this.encodingKey;
	}
	/**
	 * 
	 * @param imageWatermark
	 * @return
	 */
	byte[] convertImageToBinary(BufferedImage imageWatermark) {
		byte[] binaryImage = null;
		int rows = imageWatermark.getHeight();
		int cols = imageWatermark.getWidth();
		//Resize image if it is too large to server as watermark image
		if(rows > maxImageWatermarkSize | cols > maxImageWatermarkSize ) {
			imageWatermark = SignalEvaluatorController.scale(imageWatermark, imageWatermark.getType(), maxImageWatermarkSize, maxImageWatermarkSize, cols, rows);
			System.out.println("Finished resizing image to:" + imageWatermark.getHeight() + " x" + imageWatermark.getWidth());
		}
			WritableRaster raster = imageWatermark.getRaster();
		DataBufferByte rsDatabuffer = (DataBufferByte) raster.getDataBuffer();
		binaryImage = rsDatabuffer.getData();
		String imageBinaryString = this.stringifyByteArray(binaryImage);
		System.out.println("Binaryimage:"+ imageBinaryString);
		return binaryImage;
	}
	/**
	 * Convert text watermark to binary
	 * @param textWatermark
	 * @return
	 */
	
	byte[] convertTextWatermarkToBinary(String textWatermark) {
		byte[] byteArray=null;
		String stringByte = "", binaryString;
		byteArray = textWatermark.getBytes(); //Convert Text to byte array 
		//binaryText = Base64.getDecoder().decode(textWatermark);
		stringByte = this.stringifyByteArray(byteArray);
		
		System.out.println("Text Watermark String  bits :" + stringByte);
		return byteArray;
	}
	/**
	 * Convert array of bytes into String characters so that each bit can be accessed
	 * rather than the whole byte.
	 * @param byteArray
	 * @return
	 */
	String stringifyByteArray(byte[] byteArray) {
		String stringByte = "", binaryString;
		//binaryText = Base64.getDecoder().decode(textWatermark);
		int lengthofbytes = byteArray.length; // Number of bytes in EMR
		for(int i=0; i <lengthofbytes; i++) {
			binaryString = String.format("%8s", Integer.toBinaryString(byteArray[i] & 0xFF)).replace(' ', '0');	
			//System.out.println(" Character number " + i + ":"+ binaryString);
			stringByte = stringByte + binaryString; //Bits in string format not binary bytes
		}
		return stringByte;
		
	}
	
	
	/**
	 * Used after extracting EMR data from watermark.Takes returned bytearray
	 * and converts it to readable Electronic Medical record (EMR) 
	 * @param binarydata
	 * @return stringText. 
	 */
	String convertByteToString(byte[] binarydata) {
		String stringText=null;
		stringText = Base64.getEncoder().encodeToString(binarydata);
		
		System.out.println("Text Watermark: "+ stringText);
		return stringText;
	}
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

/**
 * 
 */
package algorithms;
import java.awt.image.*;
import javax.imageio.*;

/**
 * @author peze
 * IMIW - Medical Image Watermarking Interface
 * seed -  seed to generate a single key
 * seed1 and seed2 - Specific to Goldcode Preffered pairs or other implementations that required two seeds to generate a keyset
 * hostImage - Original medical image slice or volume without watermark
 * stegoImage - Image after watermark has been added
 * Keys - uses 'keyType' and the seeds to generate a key.
 * keyType - enum with values: GENERATIONKEY, EMBEDDINGKEY, DECODINGKEY,EXTRACTIONKEY or  REVERSALKEY
 */
public interface IMIW {
	
	
	long seed =0,seed1=0,seed2=0;
	//Major Operations
	BufferedImage encoder(BufferedImage hostImage, Byte[] watermark, Byte[] encodingkey);
	Byte[] decoder(BufferedImage stegoImage, Byte[] decodingkey);
	Byte[] Keys(long seed,long seed1,long seed2,String keyType);
	float[] distortionFunction(BufferedImage hostImage,BufferedImage stegoImage);
	float[] attackFunction(BufferedImage stegoImage, float mean, float sd,String mode); //mode = type of attack to be performed.
	Byte[] extraction(Byte[] estimatedWatermark, Byte[] extractionKey, String type);//type = image or text watermark
	BufferedImage removeWatermark(BufferedImage hostImage,  Byte[] reversalkey);	// Should return original image after removing watermark
	//For Blind methods, hostImage is not provided for integrity and authentication of images
	String integrityChecker(BufferedImage stegoImage,BufferedImage hostImage,String sideInformation);//sideInformation = specific information relevant to determine integrity
	String imageAuthentication(BufferedImage stegoImage,BufferedImage hostImage,String sideInformation);//sideInformation = specific information to determine authentic image)
	
	//Utility Operations
	BufferedImage imageWatermarkExtraction(Byte[] estimatedWatermark);// Turn binary bits into image
	String textWatermarkExtraction(Byte[] estimatedWatermark);// turn binary bits into ASCII coded text
	

}

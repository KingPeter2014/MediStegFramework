/**
 * 
 */
package bioevaluator;
import java.awt.image.BufferedImage;

/**
 * @author peze
 *
 */
public class Classifiers {

	/**
	 * 
	 */
	private Diseases disease;
	
	public Classifiers(Diseases disease, BufferedImage image) {
		// set the disease to be classified after watermarking 
		this.disease = disease;
		
	}
	
}

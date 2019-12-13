/**
 * 
 */
package bioevaluator;

import java.awt.image.BufferedImage;

/**
 * @author peze
 *
 */
public class Diseases {
	private String diseasename;
	private BufferedImage originalImageScan;
	enum ImageBiomarkers{Homogeneity,Entropy,Contrast};
	
	public Diseases() {
		
	}
	float computeImageBiomarker() {
		
		return 0;
	}
	
}

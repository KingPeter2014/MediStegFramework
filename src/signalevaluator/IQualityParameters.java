/**
 * 
 */
package signalevaluator;

import java.awt.image.BufferedImage;

/**
 * @author peze
 * To allow implementation of standard signal processing quality parameters and
 * their possible extension in future
 */
public interface IQualityParameters {
	public static float capacity=0;
	float psnr(BufferedImage hostImage,BufferedImage stegoImage);
	float ssim(BufferedImage hostImage,BufferedImage stegoImage);
	float mse(BufferedImage hostImage,BufferedImage stegoImage);
	float computationSpeed();//Time taken to embed the watermark

}

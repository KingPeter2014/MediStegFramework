/**
 * 
 */
package algorithms;

import java.awt.image.BufferedImage;

/**
 * @author peze
 *
 */
public class C4S implements IMIW {
	private BufferedImage hostImage,stegoImage,imagewatermark;
	private String textWatermark;

	/**
	 * Both image and string watermark provided for embedding
	 */
	public C4S(BufferedImage hostImage,String watermark,BufferedImage imageWatermark) {
		// 
		this.hostImage = hostImage;
	}
	public C4S(BufferedImage hostImage,String watermark) {
		// 
		this.hostImage = hostImage;
		this.textWatermark = watermark;
	}
	/**
	 * Default Constructor
	 */
	public C4S() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public BufferedImage encoder(BufferedImage hostImage, Byte[] watermark, Byte[] encodingkey) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Byte[] decoder(BufferedImage stegoImage, Byte[] decodingkey) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Byte[] Keys(long seed, long seed1, long seed2, String keyType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public float[] distortionFunction(BufferedImage hostImage, BufferedImage stegoImage) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public float[] attackFunction(BufferedImage stegoImage, float mean, float sd, String mode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Byte[] extraction(Byte[] estimatedWatermark, Byte[] extractionKey, String type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BufferedImage removeWatermark(BufferedImage hostImage, Byte[] reversalkey) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String integrityChecker(BufferedImage stegoImage, BufferedImage hostImage, String sideInformation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String imageAuthentication(BufferedImage stegoImage, BufferedImage hostImage, String sideInformation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BufferedImage imageWatermarkExtraction(Byte[] estimatedWatermark) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String textWatermarkExtraction(Byte[] estimatedWatermark) {
		// TODO Auto-generated method stub
		return null;
	}

	

}

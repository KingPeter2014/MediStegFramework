/**
 * 
 */
package pacsinterface;
import java.awt.image.BufferedImage;
import bioevaluator.BioEvaluatorController;
import signalevaluator.SignalEvaluatorController;

/**
 * @author peze
 * This class is the Security gateway of an external system that need access to the 
 * biomedical and expert evaluation framework. It defines the security service needed 
 * at the moment subject to some ethical and diagnostic constraints define in a JSON file.
 * 
 */
public class SecurityAPIGateway {

	/**
	 * 
	 */
	private BufferedImage originalScanImage,watermarkImage; 
	String diseaseName,imageModality,textWatermark, ethicsConstraints;
	enum Servicename{Integrity,Authentication,Privacy,Copyright};
	Servicename service= Servicename.Integrity;
	EthicsAndPolicies ethicpolicy; //Contains constraints for diseases, modalities and images
	BioEvaluatorController bioEvaluator;
	SignalEvaluatorController signalEvaluator;
	boolean[] services;
	// constructor
	public SecurityAPIGateway(BufferedImage originalScanImage,BufferedImage watermarkImage, String textWatermark, String diseaseName,boolean[] services,String imageModality) {
		
		this.originalScanImage = originalScanImage;
		this.watermarkImage = watermarkImage;
		this.diseaseName = diseaseName;
		this.imageModality = imageModality;
		this.textWatermark = textWatermark;
		this.services = services;
		this.createCollaboratingObjects();
		this.ethicsConstraints = ethicpolicy.getAllConstraints();
		System.out.println("In SecurityGateway:" + this.ethicsConstraints);
	}
	
	void requestService(Servicename service) {
		
		
	}
	void createCollaboratingObjects() {
		ethicpolicy = new EthicsAndPolicies();
		signalEvaluator =  new SignalEvaluatorController(originalScanImage,watermarkImage,textWatermark,diseaseName,services,imageModality);
		bioEvaluator = new BioEvaluatorController();
		
	}
}

/**
 * 
 */
package pacsinterface;

/**
 * @author peze
 *
 */
public class EthicsAndPolicies {

	/**
	 * Creates or retrieves ethics, policies and constraints in form of JSON. This will be sent to the SignalEvaluator
	 * as well as the BioEvaluator.
	 * A policy.json file is a text file in JSON (Javascript Object Notation) format. 
	 * Each policy is defined by a one-line statement in the form "<target>" : "<rule>".
	 */
	String diseasename,diseaseConstraints,generalConstraints,allConstraints;
	public EthicsAndPolicies() {
		// TODO Auto-generated constructor stub
	}
	private String getGeneralEthics() {
		generalConstraints = "HIPPA";
		return generalConstraints;
	}
	/**
	 * KB = ask BioEvaluator Knowledge Base(KB"). If not, provide a JSON list of
	 * Constraint/value pair for the specific disease
	 * @param diseaseName
	 * @param constraints
	 * @return
	 */
	public String diseaseSpecificConstraints(String diseaseName) {
		
		 diseaseConstraints = "KB"; //KB= Knowledge base of disease constraint
		 return diseaseConstraints;
	}
	/**
	 * get and format all constraints in form that can be sent for further processing
	 * JSON format is proposed for use here.
	 * @return
	 */
	public String getAllConstraints() {
		allConstraints = this.getGeneralEthics() + "," + this.diseaseSpecificConstraints(this.diseasename);
		return allConstraints;
	}

}

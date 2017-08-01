/**
 * 
 */
package com.vzw.booking.ms.template.api;

/**
 * <h1>TemplateRequest</h1> TemplateRequest defines the input request. This input JSON
 * is mapped to this object.
 * <p>
 */
public class TemplateRequest {
	
	public enum OPERATION {
		byJobName,
		byJobNameLike,
		byJobNameUsingSP
	}
	
	/**
	 * Defines the fields in the JSON document.
	 */
	private OPERATION operation;
	private String operand;
	/**
	 * @param operation
	 * @param operand
	 */
	public TemplateRequest(OPERATION operation, String operand) {
		this.operation = operation;
		this.operand = operand;
	}
	
	public TemplateRequest() {
	}

	/**
	 * @return the operation
	 */
	public OPERATION getOperation() {
		return operation;
	}

	/**
	 * @param operation the operation to set
	 */
	public void setOperation(OPERATION operation) {
		this.operation = operation;
	}

	/**
	 * @return the operand
	 */
	public String getOperand() {
		return operand;
	}

	/**
	 * @param operand the operand to set
	 */
	public void setOperand(String operand) {
		this.operand = operand;
	}
	
	

}

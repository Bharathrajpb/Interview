package com.c4l.rewardservice.model;

import lombok.Getter;

@Getter
public class VerificationResponse {
	private String pseudoCard;
	public Status status;

	private VerificationResponse(String pseudoCard, Status status) {
		this.pseudoCard = pseudoCard;
		this.status = status;
	}

	public VerificationResponse() {

	}

	public static VerificationResponse passed(String pseudoCard) {
		return new VerificationResponse(pseudoCard, Status.VERIFICATION_PASSED);
	}

	public static VerificationResponse failed(String pseudoCard) {
		return new VerificationResponse(pseudoCard, Status.VERIFICATION_FAILED);
	}

	

	public enum Status {
		VERIFICATION_PASSED,
		VERIFICATION_FAILED
	}



	@Override
	public String toString() {
		return "VerificationResponse [pseudoCard=" + pseudoCard + ", status=" + status + "]";
	}
	
	
}

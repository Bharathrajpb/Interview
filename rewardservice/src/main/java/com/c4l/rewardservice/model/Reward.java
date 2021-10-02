package com.c4l.rewardservice.model;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

@Data
public class Reward implements Serializable{

	 private static final long serialVersionUID = 1L;
	 
	 
	 private String pseudoCard;
	 private String cardType;
	 private String cifid;
	 private String tranAmount;
	 private String binNo;
	 private boolean isPrimary;
	 private BigDecimal  amount;
	
	 
	 @Override
	public String toString() {
		return "Reward [pseudoCard=" + "***" + ", cardType=" + cardType + ", cifid=" + "***" + ", tranAmount="
				+ tranAmount + ", binNo=" + "****" + ", isPrimary=" + isPrimary + ", amount=" + amount + "]";
	}
	 
	 
	 
}

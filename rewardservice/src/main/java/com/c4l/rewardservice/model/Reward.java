package com.c4l.rewardservice.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class Reward implements Serializable{

	 private static final long serialVersionUID = 1L;
	 @NotNull
	 @Size(min = 15, max = 15)
	 private String pseudoCard;
	 private String cardType;
	 @NotNull
	 @Size(min = 8, max = 8)
	 private String cifid;
	 @NotNull
	 private String ponits;
	 @NotNull
	 @Size(min = 8, max = 8)
	 private String binNo;
	 private boolean isPrimary;
	 @NotNull
	 private BigDecimal  amount;
	
	 
	 @Override
	public String toString() {
		return "Reward [pseudoCard=" + "***" + ", cardType=" + cardType + ", cifid=" + "***" + ", tranAmount="
				+ ponits + ", binNo=" + "****" + ", isPrimary=" + isPrimary + ", amount=" + amount + "]";
	}
	 
	 
	 
}

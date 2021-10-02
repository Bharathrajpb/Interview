package com.c4l.rewardservice.entity;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
	
@Document
@Data
public class RewardsFailedReport {

	private Date createdOn=new Date();
	private String pseudoCard;
	private String cardType;
	private String cifid;
	private String tranAmount;
	private String binNo;
	private boolean isPrimary;
	private BigDecimal  amount;	
	private String desc;
	
	
	@Override
	public String toString() {
		return "RewardsFailedReport [createdOn=" + createdOn + ", pseudoCard=" + "****" + ", cardType=" + cardType
				+ ", cifid=" + "***" + ", tranAmount=" + tranAmount + ", binNo=" + "****" + ", isPrimary=" + isPrimary
				+ ", amount=" + amount + ", desc=" + desc + "]";
	}
	
	
	
}

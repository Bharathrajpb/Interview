package com.c4l.rewardservice.entity;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.ToString;

@Document
@Data

public class Rewards {

	
	private Date createdOn=new Date();
	private String pseudoCard;
	private String cardType;
	private String cifid;
	private String tranAmount;
	private String binNo;
	private boolean isPrimary;
	private BigDecimal  amount;
	
	
	@Override
	public String toString() {
		return "Rewards [createdOn=" + createdOn + ", pseudoCard=" + "********" + ", cardType=" + cardType + ", cifid="
				+ "****" + ", tranAmount=" + tranAmount + ", binNo=" + "****" + ", isPrimary=" + isPrimary + ", amount="
				+ amount + "]";
	}

	
	
}

package com.c4l.rewardservice.entity;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
	
@Document
@Data
public class RewardsFailedReport {

	@Id
	private String id;
	private Date createdOn=new Date();
	private String record;
	private String desc;
	
	
	@Override
	public String toString() {
		return "RewardsFailedReport [createdOn=" + createdOn + ", record=" + record + ", desc=" + desc + "]";
	}
	
	/*
	 * private String cardType; private String cifid; private String tranAmount;
	 * private String binNo; private boolean isPrimary; private BigDecimal amount;
	 */
	
	
	
	
	
}

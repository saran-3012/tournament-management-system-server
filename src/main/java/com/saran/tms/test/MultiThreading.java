package com.saran.tms.test;

import java.util.Arrays;
import java.util.List;

import com.saran.tms.dao.Dao;
import com.saran.tms.enums.Operators;
import com.saran.tms.enums.TableNames;
import com.saran.tms.models.Model;
import com.saran.tms.models.OrganizationModel;
import com.saran.tms.models.UserModel;
import com.saran.tms.pojo.ConditionEntry;
import com.saran.tms.pojo.TableColumnEntry;
import com.saran.tms.pojo.TableConditionEntry;

public class MultiThreading extends Thread {
	public void run() {
		
		Dao udao = new Dao(UserModel.class);
		
		
		
		try {
			
			List<Model> users = udao.findAll(
										Arrays.asList("*"), 
										Arrays.asList(new ConditionEntry(null, "user_name", Arrays.asList(Operators.ILIKE), "%saran%"))
									);
			
			System.out.println("Users" + users);
			
		}
		catch(Exception err) {
			System.out.println(err.getMessage());
			err.printStackTrace();
		}
	}
}

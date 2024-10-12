package com.saran.tms.test;

import com.saran.tms.models.Model;
import com.saran.tms.models.OrganizationModel;
import com.saran.tms.models.UserModel;
import com.saran.tms.pojo.ConditionEntry;
import com.saran.tms.pojo.GroupEntry;
import com.saran.tms.pojo.JoinEntry;
import com.saran.tms.pojo.TableColumnEntry;
import com.saran.tms.pojo.TableConditionEntry;
import com.saran.tms.postgresql.PostgresDataBase;
import com.saran.tms.services.UserService;
import com.saran.tms.validators.PasswordBCrypter;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.saran.tms.adapters.MapModelParser;
import com.saran.tms.adapters.ModelMapParser;
import com.saran.tms.config.DataBaseConfig;
import com.saran.tms.config.TableConfig;
import com.saran.tms.connections.ConnectionManager;
import com.saran.tms.connections.ConnectionPool;
import com.saran.tms.enums.JoinTypes;
import com.saran.tms.enums.Operators;
import com.saran.tms.enums.TableNames;
import com.saran.tms.exceptions.ResponseException;


public class Main {
	

	public static void main(String[] args) {
		
//		for(Map.Entry<String, TableConfig> entry : DataBaseConfig.getTableConfigEntries()) {
//			System.out.println(entry);
//		}

////	Object to Map
//		
//
//		UserModel user = new UserModel();
//		
//		user.setId(3000000L);
//		user.setName("Saran");
//		user.setDateOfBirth(12314134134L);
//		user.setPhoneNumber("8220171421");
//		user.setEmail("sarankumar@gmail.com");
//		user.setPassword("12345676543");
//		user.setOrganizationId(2173912L);		
//		user.setBloodGroup("O+ve");
//		user.setGender(1);
//		user.setRole(0);
//		user.setAddress("Chennai - 6");
//		
//		
//		Map<String, Object> map = null;
//		
//		try {
//			map = ModelMapParser.convertToMap(user);
//			System.out.println(map);
//		} 
//		catch (Exception e) {
//			e.printStackTrace();
//		}
//		
////	Map to Object	
//		
//		Model obj = null;
//		
//
//		try {
//			obj = MapModelParser.convertToObject("UserModel", map);
//			System.out.println(obj);
//		} 
//		catch (Exception e) {
//				e.printStackTrace();
//		}
		
		
//		Test update users
		
//		UserModel user = new UserModel();
//		
//		user.setName("Saran Kumar" );
//		user.setAge(21);
//		user.setPhoneNumber("9597723076");
//		user.setGender(1);
//		user.setRole(10);
//
//		TableConditionEntry conditionEntry = new TableConditionEntry();
//		conditionEntry.setTableName(TableNames.USERS);
//		conditionEntry.setColumnContitions(
//			Arrays.asList(
//				new ConditionEntry(null, "role", Arrays.asList(Operators.EQUAL), 10)
//			)
//		);
//		
//		PostgresDataBase pdb = new PostgresDataBase();
//		
//		try {
//			pdb.delete(conditionEntry);;
//		}
//		catch (Exception ex) {
//			ex.printStackTrace();
//		}
		
		
//		Test join user and org
//		
//		TableColumnEntry columnEntry1 = new TableColumnEntry();
//		columnEntry1.setTableName(TableNames.USERS);
//		columnEntry1.setColumnNames(Arrays.asList("*"));
//		
//		TableColumnEntry columnEntry2 = new TableColumnEntry();
//		columnEntry2.setTableName(TableNames.ORGANIZATIONS);
//		columnEntry2.setColumnNames(Arrays.asList("*"));
//		
//		TableColumnEntry columnEntry3 = new TableColumnEntry();
//		columnEntry3.setTableName(TableNames.TOURNAMENTS);
//		columnEntry3.setColumnNames(Arrays.asList("*"));
//		
//		List<TableColumnEntry> requiredTableFields = Arrays.asList(columnEntry1, columnEntry2, columnEntry3);
//		
//		List<JoinEntry> joinEntries = Arrays.asList(
//			new JoinEntry(TableNames.USERS, TableNames.ORGANIZATIONS, "organization_id", "id", JoinTypes.JOIN),
//			new JoinEntry(TableNames.ORGANIZATIONS, TableNames.TOURNAMENTS, "id", "organization_id", JoinTypes.JOIN)
//		);
//
//		TableConditionEntry conditionEntry1 = new TableConditionEntry();
//		conditionEntry1.setTableName(TableNames.USERS);
//		conditionEntry1.setColumnContitions(
//			Arrays.asList(
//				new ConditionEntry(null, "name", Arrays.asList(Operators.ILIKE), "%saran%"),
//				new ConditionEntry(Arrays.asList(Operators.AND), "role", Arrays.asList(Operators.EQUAL), 0)
//			)
//		);
//		
//		TableConditionEntry conditionEntry2 = new TableConditionEntry();
//		conditionEntry2.setTableName(TableNames.ORGANIZATIONS);
//		conditionEntry2.setColumnContitions(
//			Arrays.asList(
//				new ConditionEntry(Arrays.asList(Operators.AND), "id", Arrays.asList(Operators.EQUAL), 2173912L)
//			)
//		);
//		
//		List<TableConditionEntry> requiredTableConditions = Arrays.asList(conditionEntry1, conditionEntry2);
//		
//		
//		PostgresDataBase pdb = new PostgresDataBase();
//		
//		try {
//			pdb.findUserWithJoin(requiredTableFields, joinEntries, requiredTableConditions);
//		}
//		catch (Exception ex) {
//			ex.printStackTrace();
//		}
		
//		Test find user
		
		
//		TableColumnEntry columnEntry = new TableColumnEntry();
//		columnEntry.setTableName(TableNames.USERS);
//		columnEntry.setColumnNames(Arrays.asList("id", "name", "organization_id", "role"));
//
//		TableConditionEntry conditionEntry = new TableConditionEntry();
//		
//		conditionEntry.setTableName(TableNames.USERS);
//		
//		conditionEntry.setColumnContitions(
//			Arrays.asList(
//				new ConditionEntry(null, "name", Arrays.asList(Operators.ILIKE), "%saran%"),
//				new ConditionEntry(Arrays.asList(Operators.AND), "role", Arrays.asList(Operators.EQUAL), 0)
//			)
//		);
//		
//		PostgresDataBase pdb = new PostgresDataBase();
//		
//		try {
//			pdb.findAll(columnEntry, conditionEntry, 20, 0);;
//		}
//		catch (Exception ex) {
//			ex.printStackTrace();
//		}

		
				
//      Test create users
		
//		List<Object> users = new ArrayList<>();
//		
//		for(int i=0; i<1000; i++) {
//			
//			UserModel user = new UserModel();
//			
//			user.setId(3000000L);
//			user.setName("Saran" + (i*i) );
//			user.setAge(20);
//			user.setPhoneNumber("8220171421");
//			user.setEmail((10000000 + (int)(Math.random() * ((100000000)))) + "sarankumar." + (10000000 + (int)(Math.random() * ((100000000)))) + "@gmail.com");
//			user.setPassword(String.valueOf(1000000 + (int)(Math.random() * ((1000000)))));
//			user.setOrganizationId(2173912L);		
//			user.setBloodGroup("O+ve");
//			user.setGender(1);
//			user.setRole(0);
//			user.setAddress("Chennai - " + i);
//			
//			users.add(user);
//		}
//		
//		System.out.println(users);
//		
//		PostgresDataBase pdb = new PostgresDataBase();
//		
//		try {
//			pdb.saveAll(users);
//		}
//		catch (Exception ex) {
//			ex.printStackTrace();
//		}
//		
//		to map
		
//		Map<String, Object> mp = null;
//		
//		try {
//			mp = ObjectMapParser.convertToMap(user);
//			System.out.println(mp);
//		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
//				| SecurityException e) {
//			e.printStackTrace();
//		}
//
////		to object
//		
//		
//		
//		Object obj = null;
//		
//
//		try {
//			obj = MapObjectParser.convertToObject("UserModel", mp);
//			System.out.println(obj);
//		} 
//		catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | ClassNotFoundException e) {
//				e.printStackTrace();
//			}
		
		
		
//		
//		try {
//			
//			Connection con =  ConnectionPool.getConnection();
//			StatementFactory sf = new StatementFactory();
//			
//			PreparedStatement pst = sf.prepareInsertStatement(con, user, "users");
//			
//			int affectedRows = pst.executeUpdate();
//			
//			System.out.println(affectedRows + " rows affected.");
//			
//			pst.close();
//			ConnectionPool.addExistingConnection(con);
//			
//			ConnectionPool.closeAllConnections();
//			
//		} 
//		catch (Exception e) {
//			e.printStackTrace();
//		}
		
	
//		UserDao dao = null;
//		
//		try {
//			UserDao dao = new UserDao();
//			
//			UserModel user = dao.findOneById(3000000l);
//			if(isSaved) 
//				System.out.println("User created successfully");
//			else
//				System.out.println("Something went worng!");
//			System.out.println(user);
//		}
//		catch(Exception err) {
//			System.out.println(err.getMessage());
//		}
		
//		try {
//			dao = new UserDao();
//			
//			boolean isUpdated = dao.updateOneById(user);
//			if(isUpdated) 
//				System.out.println("User Updated successfully");
//			else
//				System.out.println("Something went worng!");
//		}
//		catch(Exception err) {
//			err.printStackTrace();
//		}
		
		
//		OrganizationModel org = new OrganizationModel();
//		
//		org.setName("Zoho Corp");
//		org.setAddress("Karaikudi");
//		org.setYear("2005");
//		
//		try {
//			OrganizationDao dao = new OrganizationDao();
//			
//			Long organizationId = dao.save(org);
//			System.out.println("Org Id = " + organizationId);
//			
//		}
//		catch(Exception err) {
//			System.out.println(err.getMessage());
//			err.printStackTrace();
//		}
//		
//		try {
//			ConnectionPool cp = ConnectionPool.getConnectionPool(8);
//			cp.closeAllConnections();
//		}
//		catch(Exception err) {}
//		
		
//		Date currentDate = new Date();
//        long milliSeconds = currentDate.getTime();
//        System.out.println("Start : "+milliSeconds);
		
//		currentDate = new Date();
//      milliSeconds = currentDate.getTime();
//      System.out.println("End : "+milliSeconds);
		
		// Connection Pool Test Begin //	
        
//        ArrayList<MultiThreading> thrds = new ArrayList<>();
//        
//        int n = 5000;
//        
//		for(int i=0; i<n; i++) {
//			MultiThreading mt = new MultiThreading();
//			thrds.add(mt);
//			mt.start();
//			
//		}
//		
//		for(int i=0; i<n; i++) {
//			try {
//				thrds.get(i).join();
//			} 
//			catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		}
//		
//		System.out.println("All run completed");
		

		// Connection Pool Test End //		

//      RETURNING CHECK  
		
//        Connection con;
//        PreparedStatement pst;
//        ResultSet rs;
//        
//        try {
//        	con = ConnectionManager.getConnection();
//        	pst = con.prepareStatement("insert into organizations (name, address, year) values ('aedaef','adfad','adfad') returning id");
//        	rs = pst.executeQuery();
//        	if(rs.next()) {
//        		System.out.print(rs.getLong("id"));
//        	}
//        	else {
//        		System.out.println("Something went wrong!");
//        	}
//        	rs.close();
//        	pst.close();
//        	con.close();
//        }
//        catch (Exception ex) {
//        	ex.printStackTrace();
//        }

	
	}

}

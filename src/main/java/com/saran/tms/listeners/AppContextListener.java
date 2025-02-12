package com.saran.tms.listeners;


import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.instrument.Instrumentation;
import java.lang.management.ManagementFactory;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.logging.Level;

import javax.management.MBeanServer;
import javax.management.MBeanServerInvocationHandler;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.saran.monitor.InstrumentationRegistryMBean;
import com.saran.monitor.JvmManagementBean;
import com.saran.tms.concurrency.ConcurrencyLimiterFactory;
import com.saran.tms.config.ColumnConfig;
import com.saran.tms.config.DataBaseConfig;
import com.saran.tms.config.TableConfig;
import com.saran.tms.connections.ConnectionPool;
import com.saran.tms.controllers.UserController;
import com.saran.tms.enums.Constraints;
import com.saran.tms.logger.ApplicationLogger;
import com.saran.tms.models.UserModel;
import com.saran.tms.routers.Router;
import com.saran.tms.services.MonitoringService;
import com.saran.tms.test.Main;

import redis.clients.jedis.UnifiedJedis;

/**
 * Application Lifecycle Listener implementation class AppContextListener
 *
 */

public class AppContextListener implements ServletContextListener {

	Thread cleanupSchedulerThread;
	Thread appStatsThread;
    
	public AppContextListener() {}

    @SuppressWarnings("unchecked")
	public void contextInitialized(ServletContextEvent sce)  { 
    	
    	MonitoringService.initializeJvmBeam();
    	
    	ServletContext context = sce.getServletContext();

    	// Logger initialization 
    	
    	String LOGGER_FOLDER_PATH = context.getInitParameter("LogsFolder");
    	
    	ApplicationLogger.initializeLogger(LOGGER_FOLDER_PATH);
    	
    	// Database configuration initialization
    	
    	String DB_CONFIG_FILE_PATH = context.getInitParameter("DataBaseConfig");
    	if(DB_CONFIG_FILE_PATH == null || DB_CONFIG_FILE_PATH.isEmpty()) {
    		System.out.println("DB Config File path is not set");
    		ApplicationLogger.log(Level.SEVERE, "DB Config File path is not set");
    		return;
    	}
    	
        Yaml yaml = new Yaml();
        Map<String, Object> configMap = null;
        InputStream inputStream = null;
        
        try {
        	inputStream = new FileInputStream(DB_CONFIG_FILE_PATH);

            configMap = yaml.load(inputStream);
            inputStream.close();
        } 
        catch (IOException e) {
            e.printStackTrace();
    		ApplicationLogger.log(Level.SEVERE, "Error while loading the db-config.yaml file", e);
            return;
        }
        
        Map<String, Object> dataBaseConfigMap = (Map<String, Object>) configMap.get("database");
        
        String dbUrl = (String) dataBaseConfigMap.get("url");
        String dbUser = (String) dataBaseConfigMap.get("user");
        String dbPassword = (String) dataBaseConfigMap.get("password");
        String modelPackageName = (String) dataBaseConfigMap.get("modelPackage");
        
        Map<String, TableConfig> tableNameConfigMap = new HashMap<>();
        Map<String, TableConfig> modelNameConfigMap = new HashMap<>();
        
        List<Map<String, Object>> tableConfigMapList = (List<Map<String, Object>>) configMap.get("tables");
        
        for(Map<String, Object> tableConfigMap : tableConfigMapList) {
        	
        	String tableName = (String) tableConfigMap.get("tableName");
        	String modelName = (String) tableConfigMap.get("modelName");
        	Map<String, ColumnConfig> columnMapping = new HashMap<>();
        	Map<String, ColumnConfig> fieldMapping = new HashMap<>();
        	
        	List<Map<String, Object>> columnConfigMapList = (List<Map<String, Object>>) tableConfigMap.get("columns");
        	
        	for(Map<String, Object> columnConfigMap : columnConfigMapList) {
        		
        		String columnName = (String) columnConfigMap.get("columnName");
        		String fieldName = (String) columnConfigMap.get("fieldName");
        		String dataType = (String) columnConfigMap.get("dataType");
        		Set<Constraints> constraintSet = new HashSet<>();
        		
        		
        		List<Object> constraints = (List<Object>) columnConfigMap.get("constraints");
        		
        		
        		for(Object constraint : constraints) {
        			try {
        				constraintSet.add(Constraints.getConstraint((String) constraint));
        			}
        			catch(IllegalArgumentException ex) {
        				System.out.println(ex.getMessage());
        	    		ApplicationLogger.log(Level.WARNING, ex.getMessage(), ex);
        			}
        		}
        		
        		ColumnConfig columnConfig = null;
        		
				try {
					columnConfig = new ColumnConfig(columnName, fieldName, Class.forName("java.lang." + dataType), constraintSet);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
		            ApplicationLogger.log(Level.SEVERE, "Data type is not configured properly", e);
		            return;
				}
        		
        		columnMapping.put(columnName, columnConfig);
        		fieldMapping.put(fieldName, columnConfig);
        		
        	}
        	
        	TableConfig tableConfig = new TableConfig(tableName, modelName, columnMapping, fieldMapping);
        	
        	tableNameConfigMap.put(tableName, tableConfig);
        	modelNameConfigMap.put(modelName, tableConfig);
        	
        }
        
        DataBaseConfig.initializeConfig(dbUrl, dbUser, dbPassword, modelPackageName, tableNameConfigMap, modelNameConfigMap);
        ApplicationLogger.log(Level.CONFIG, "Data base configuration initialized successfully!");

        
        // Custom connection pool implementation
        
        try {
			ConnectionPool.initializeConnectionPool(10, 20);
	        ApplicationLogger.log(Level.CONFIG, "Connection Pool initialized successfully!");
		} 
        catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			ApplicationLogger.log(Level.SEVERE, "Connection Pool initialization failed", e);
			return;
		}
        
        try {
        	Router.initailizeRouter("com.saran.tms.controllers");
            ApplicationLogger.log(Level.CONFIG, "Router setup initialized successfully!");
        }
        catch(Exception e) {
        	e.printStackTrace();
			ApplicationLogger.log(Level.SEVERE, "Router initialization failed", e);
			return;
        }
        
        // Concurrency limiter cofiguration initialization
        
        ConcurrencyLimiterFactory.initializeConcurrencyLimiterPool("TournamentRegistration:Participants");
        ConcurrencyLimiterFactory.initializeConcurrencyLimiterPool("TournamentRegistration:Teams");
        ConcurrencyLimiterFactory.initializeConcurrencyLimiterPool("TournamentRegistration:TeamMembers");

        final Runnable cleanupScheduler = () -> {
        	while(true) {
        		try {
					Thread.sleep(1000 * 60 * 60);
				} catch (InterruptedException e) {
					e.printStackTrace();
					ApplicationLogger.log(Level.SEVERE, "Scheduler execution failed", e);
				}
        		ConcurrencyLimiterFactory.cleanUp();
        	}
        };
        
        cleanupSchedulerThread = new Thread(cleanupScheduler);
        cleanupSchedulerThread.setDaemon(true);
        cleanupSchedulerThread.start();
        
        
        // App stats monitoring
        final Runnable appStatsPersister = () -> {
    		
    		while(true) {
    			MonitoringService.persistLiveAppStats();
//    			MonitoringService.cleanupLiveAppStats(3 * 60 * 60 * 1000);
    			MonitoringService.cleanupLiveAppStats(1 * 60 * 60 * 1000);
    			try {
//    				Thread.sleep(1000 * 60 * 5);
    				Thread.sleep(1000 * 10);
    			} catch (InterruptedException e) {
    				e.printStackTrace();
    				ApplicationLogger.log(Level.SEVERE, "Scheduler execution failed", e);
    			}
    		}
    		
    	};
    	
    	
    	
    	MonitoringService.registerObject("tableNameConfigMap", tableNameConfigMap);
    	MonitoringService.registerObject("modelNameConfigMap", modelNameConfigMap);
    	
    	System.out.println(tableNameConfigMap);
    	
        
    	MonitoringService.persistStaticAppStats();
        appStatsThread = new Thread(appStatsPersister);
        appStatsThread.setDaemon(true);
        appStatsThread.start();
        
        
//        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
//        ObjectName objectName = null;
//		try {
//			objectName = new ObjectName("com.saran:type=InstrumentationHolder");
//		} catch (MalformedObjectNameException e) {
//			e.printStackTrace();
//		}
//        InstrumentationRegistryMBean mBean = MBeanServerInvocationHandler.newProxyInstance(
//            mBeanServer, objectName, InstrumentationRegistryMBean.class, false
//        );
//        Instrumentation instrumentation = mBean.getInstrumentation();
//        
//        JvmManagementBean jmb = JvmManagementBean.getInstance(instrumentation);
        
        
       
        
//        System.out.println("App Context listener : " + instrumentation);
//        
//        System.out.println("App Context listener, tableNameConfigMap : " + jmb.getObjectSize(tableNameConfigMap) / (1024.0 * 1024.0) + "mb");
//        System.out.println("App Context listener, modelNameConfigMap : " + jmb.getObjectSize(modelNameConfigMap) / (1024.0 * 1024.0) + "mb");
      
         	
//    	Main.main(null); // TESTING
    }

    
    
	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    
    public void contextDestroyed(ServletContextEvent sce)  { 
    	// Custom connection pool implementation
        try {
			ConnectionPool.closeAllConnections();
			ApplicationLogger.log(Level.CONFIG, "All the Connection Pool connections closed");
		} 
         catch (SQLException e) {
 			ApplicationLogger.log(Level.WARNING, "Connections are not closed properly", e);
        	e.printStackTrace();
		}
        
        // Stop scheduler thread
        try {        	
        	cleanupSchedulerThread.stop();
        	ApplicationLogger.log(Level.CONFIG, "Scheduler thread stopped");
        }
        catch(Exception e) {
        	ApplicationLogger.log(Level.WARNING, "Scheduler thread not stopped properly", e);
        	e.printStackTrace();
        }
        
        // app stats scheduler thread
        try {        	
        	appStatsThread.stop();
        	ApplicationLogger.log(Level.CONFIG, "App stats thread stopped");
        }
        catch(Exception e) {
        	ApplicationLogger.log(Level.WARNING, "Scheduler thread not stopped properly", e);
        	e.printStackTrace();
        }
        
        
        
        // Close logger
        ApplicationLogger.log(Level.CONFIG, "Closing application logger");
        ApplicationLogger.closeLogger();
    }
	
}

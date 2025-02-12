package com.saran.tms.services;

import java.lang.instrument.Instrumentation;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryUsage;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import javax.management.MBeanServer;
import javax.management.MBeanServerInvocationHandler;
import javax.management.MalformedObjectNameException;
import javax.management.Notification;
import javax.management.ObjectName;
import javax.management.openmbean.CompositeData;

import org.json.JSONArray;
import org.json.JSONObject;

import com.saran.monitor.InstrumentationRegistryMBean;
import com.saran.monitor.JvmManagementBean;
import com.saran.monitor.JvmManagementException;
import com.saran.tms.logger.ApplicationLogger;
import com.saran.tms.persistance.DataStoreConfig;
import com.saran.tms.persistance.Persistance;
import com.saran.tms.persistance.fs.FileConfig;
import com.saran.tms.persistance.redis.RedisConfig;
import com.saran.tms.persistance.redis.RedisStrategies;
import com.saran.tms.routers.QueryParams;
import com.sun.management.GarbageCollectionNotificationInfo;
import com.sun.management.GcInfo;

import redis.clients.jedis.resps.Tuple;

import redis.clients.*;

public class MonitoringService {

	private static JvmManagementBean jvmBean;
	
	private final static String REDIS_HOST = "localhost";
	private final static int REDIS_PORT    = 6379;	
	
	private final static DataStoreConfig redisConfig = new RedisConfig(REDIS_HOST, REDIS_PORT);
	
	private final static Persistance redisCache     = Persistance.getPersistance(Persistance.DataStores.REDIS, redisConfig);
	private final static Persistance redisSortedSet = Persistance.getPersistance(Persistance.DataStores.REDIS, redisConfig)
																 .setStrategy(RedisStrategies.sortedSetStrategy);
	
	private final static String staticAppStatsKey = "staticAppStats";
	private final static String liveAppStatsKey   = "liveAppStats";
	private final static String gcNotificationKey = "gcNotification";
	

	private final static String FILE_STORAGE_FOLDER_PATH = "/Users/saran-pt7697/FileStorage/";
	
	private final static DataStoreConfig fileConfig = new FileConfig(FILE_STORAGE_FOLDER_PATH);
	
	private final static Persistance fileSystem = Persistance.getPersistance(Persistance.DataStores.FS, fileConfig);
	

	public static void initializeJvmBeam() {
		
			MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
	        
	        ObjectName diagnosticCommandMBeanName = null;
			try {
				diagnosticCommandMBeanName = new ObjectName("com.sun.management:type=DiagnosticCommand");
			} catch (MalformedObjectNameException e) {

				e.printStackTrace();
			}
			



//			
//			 try {
//				MBeanInfo mBeanInfo = mbs.getMBeanInfo(diagnosticCommandMBeanName);
//				System.out.println("MBean Class: " + mBeanInfo.getClassName());
//	            System.out.println("Description: " + mBeanInfo.getDescription());
//	            System.out.println("Attributes:");
//	            Arrays.stream(mBeanInfo.getAttributes()).forEach(attr -> {
//	                System.out.println("\t" + attr.getName() + " (" + attr.getType() + ")");
//	            });
//	            System.out.println("Operations:");
//	            Arrays.stream(mBeanInfo.getOperations()).forEach(op -> {
//	                System.out.println("\t" + op.getName() + " (" + op.getReturnType() + ")");
//	            });
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//	        
//
//	        String[] commands = {};
//	        try {
//	            Object attribute = mbs.getAttribute(diagnosticCommandMBeanName, "DiagnosticCommandNames");
//	            if (attribute instanceof String[]) {
//	                commands = (String[]) attribute;
//	            } else {
//	                System.out.println("DiagnosticCommandNames is not of type String[]");
//	            }
//	        } catch (Exception e) {
//	            System.out.println(e.getMessage());
//	        }
//	        
//	        System.out.println("Available Diagnostic Commands:");
//	        for (String command : commands) {
//	            System.out.println(command);
//	        }
	        

//	        String[] gcClassHistogramArgs = new String[0];  
//	        String result = null;
//			try {
//				result = (String) mbs.invoke(diagnosticCommandMBeanName, "gcClassHistogram", new Object[]{gcClassHistogramArgs}, new String[]{String[].class.getName()});
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//	        System.out.println("Class Histogram:\n" + result.substring(0, 100));
	        
	        
//		try {
//            // Get the platform MBeanServer
//            MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
//
//            // ObjectName for the DiagnosticCommandMBean
//            ObjectName diagnosticCommandMBeanName = new ObjectName("com.sun.management:type=DiagnosticCommand");
//
//            // List of operations that return statistics
//            Map<String, String[]> operations = new HashMap<>();
//            operations.put("compilerCodeHeapAnalytics", new String[0]); //
//            operations.put("compilerCodecache", new String[0]); //
//            operations.put("compilerCodelist", new String[0]); //
//            operations.put("compilerQueue", new String[0]); //
//            operations.put("gcClassHistogram", new String[0]); //
//            operations.put("gcClassStats", new String[0]); //
//            operations.put("gcHeapInfo", new String[0]); //
//            operations.put("gcFinalizerInfo", new String[0]); //
//            operations.put("help", new String[0]);
//            operations.put("threadPrint", new String[0]); //
//            operations.put("vmCheckCommercialFeatures", new String[0]); --
//            operations.put("vmClassHierarchy", new String[0]); //
//            operations.put("vmClassloaderStats", new String[0]); //
//            operations.put("vmClassloaders", new String[0]); //
//            operations.put("vmCommandLine", new String[0]); //
//            operations.put("vmDynlibs", new String[0]); // 
//            operations.put("vmFlags", new String[0]); //
//            operations.put("vmInfo", new String[0]); //
//            operations.put("vmLog", new String[0]); //
//            operations.put("vmMetaspace", new String[0]); //
//            operations.put("vmNativeMemory", new String[0]); //
//            operations.put("vmPrintTouchedMethods", new String[0]); //
//            operations.put("vmStringtable", new String[0]); //
//            operations.put("vmSymboltable", new String[0]); //
//            operations.put("vmSystemProperties", new String[0]); // 
//            operations.put("vmSystemdictionary", new String[0]); //
//            operations.put("vmUptime", new String[0]); //
//            operations.put("vmVersion", new String[0]); //
//
//            System.out.println("Invoking Diagnostic Commands for Stats...");
//            for (Map.Entry<String, String[]> entry : operations.entrySet()) {
//                try (OutputStream os = new FileOutputStream("/Users/saran-pt7697/DignosticBeanTest/" + entry.getKey() + ".txt")){
//                    String operationName = entry.getKey();
//                    String[] params = entry.getValue();
//                    String[] signature = new String[]{String[].class.getName()};
//
//                    // Invoke the MBean operation
//                    String result = (String) mbs.invoke(diagnosticCommandMBeanName, operationName, new Object[] {params}, signature);                
//                    os.write(result.getBytes());
//                    
//                } catch (Exception e) {
//                    System.out.println("Error invoking " + entry.getKey() + ": " + e.getMessage());
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//	        
//	        
//	        
//	        
		
		ObjectName objectName = null;
		try {
			objectName = new ObjectName("com.saran:type=InstrumentationRegistry");
		} catch (MalformedObjectNameException e) {
			e.printStackTrace();
		}
		
		
		InstrumentationRegistryMBean instrumentationRegistry = MBeanServerInvocationHandler.newProxyInstance(mBeanServer, objectName,
				InstrumentationRegistryMBean.class, false);
		Instrumentation instrumentation = instrumentationRegistry.getInstrumentation();

		jvmBean = JvmManagementBean.getInstance(instrumentation);
		
		jvmBean.addGcListener((Notification notification, Object handback) -> {
			if (GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION.equals(notification.getType())) {
				
				long currentMillisValue = Instant.now().toEpochMilli();
                GarbageCollectionNotificationInfo gcNotificationInfo = GarbageCollectionNotificationInfo.from((CompositeData) notification.getUserData());
                GcInfo gcInfo = gcNotificationInfo.getGcInfo();

                JSONObject gcData = new JSONObject();
                
                gcData.put("gcName", gcNotificationInfo.getGcName());
                gcData.put("gcAction", gcNotificationInfo.getGcAction());
                gcData.put("gcCause", gcNotificationInfo.getGcCause());
                gcData.put("gcStartTime", gcInfo.getStartTime());
                gcData.put("gcEndTime", gcInfo.getEndTime());
                gcData.put("gcDuration", gcInfo.getDuration());
                
                JSONObject memoryBeforeGc = new JSONObject();
                for(final Map.Entry<String, MemoryUsage> gcMemoryUsage : gcInfo.getMemoryUsageBeforeGc().entrySet()){
                	JSONObject memoryUsageData = new JSONObject();
                	MemoryUsage memUsage = gcMemoryUsage.getValue();
                	
                	memoryUsageData.put("allocatedMemory", memUsage.getCommitted());
                	memoryUsageData.put("usedMemory", memUsage.getUsed());
                	
                	memoryBeforeGc.put(gcMemoryUsage.getKey(), memoryUsageData);
				}
                
                
                JSONObject memoryAfterGc = new JSONObject();
                for(final Map.Entry<String, MemoryUsage> gcMemoryUsage : gcInfo.getMemoryUsageAfterGc().entrySet()){
                	JSONObject memoryUsageData = new JSONObject();
                	MemoryUsage memUsage = gcMemoryUsage.getValue();
                	
                	memoryUsageData.put("allocatedMemory", memUsage.getCommitted());
                	memoryUsageData.put("usedMemory", memUsage.getUsed());
                	
                	memoryAfterGc.put(gcMemoryUsage.getKey(), memoryUsageData);
				}
                
                gcData.put("memoryBeforeGc", memoryBeforeGc);
                gcData.put("memoryAfterGc", memoryAfterGc);
                
                
                redisSortedSet.write(gcNotificationKey, (double) currentMillisValue, gcData.toString()); 
            
            }
			
		});
		
		
		try {
		
			String vmTouchedMethods = jvmBean.getVmTouchedMethods();
			((FileConfig) fileConfig).setFileName("vmTouchedMethods.txt");
			fileSystem.write(vmTouchedMethods);
			
			
			((FileConfig) fileConfig).setFileName("class-histogram.txt");
			String classHistogram = jvmBean.getClassHistogram();
			fileSystem.write(classHistogram);
			String preSubstring = classHistogram.substring(0, 200);
			fileSystem.update("*.", "REDIS");

			
			
			
		} catch (JvmManagementException e) {
			e.printStackTrace();
		}

	}

	public static void registerObject(String refKey, Object objRef) {
		jvmBean.registerObjectReference(refKey, objRef);
	}

	@SuppressWarnings("unchecked")
	public static JSONObject retriveAppStats(QueryParams queryParams) {
		double startDate = queryParams.getLong("start_date");
		double endDate = queryParams.getLong("end_date");
		double startTime = queryParams.getLong("start_time");
		double endTime = queryParams.getLong("end_time");
		
		String staticAppStatsString = (String) redisCache.read(staticAppStatsKey);
		List<Tuple> rangeLiveAppStats = (List<Tuple>) redisSortedSet.read(liveAppStatsKey, startDate + startTime, endDate + endTime);
		

		JSONArray liveAppStats = new JSONArray();

		final int n = rangeLiveAppStats.size();

		for (int i = n - 1; i >= 0; i--) {

			JSONObject liveAppStat = new JSONObject(rangeLiveAppStats.get(i).getElement());
			liveAppStat.put("timestamp", (long) rangeLiveAppStats.get(i).getScore());

			liveAppStats.put(liveAppStat);
		}

		JSONObject staticAppStats = new JSONObject(staticAppStatsString);

		JSONObject appStatsData = new JSONObject();

		appStatsData.put("static", staticAppStats);
		appStatsData.put("live", liveAppStats);

		return appStatsData;
	}
	
	public static JSONArray getAvailableObjectReferences() {
		
		JSONArray availableRefs = new JSONArray(jvmBean.getAllReferenceKeys());
		
		return availableRefs;
	}

	public static JSONObject retriveObjectsSize(QueryParams queryParams) {
		String[] selectedObjects = queryParams.getAll("selected_object");
		JSONObject refObjSizes = new JSONObject();
		for (final String objRefKey : selectedObjects) {
			try {
				refObjSizes.put(objRefKey, (jvmBean.getReferenceObjectSize(objRefKey) / (1024.0 * 1024.0)) + "mb");
			} catch (Exception e) {
				ApplicationLogger.log(Level.SEVERE, e.getMessage(), e);
			}
		}
		return refObjSizes;
	}

	public static void persistStaticAppStats() {
		JSONObject staticAppStats = null;
		try {
			staticAppStats = jvmBean.getStaticJvmStats();
		} catch (JvmManagementException e) {
			staticAppStats = new JSONObject();
			e.printStackTrace();
		}

		
		redisCache.write(staticAppStatsKey, staticAppStats.toString());
	}

	public static void persistLiveAppStats() {
		JSONObject liveAppStats = null;
		try {
			liveAppStats = jvmBean.getDynamicJvmStats();
		} catch (JvmManagementException e) {
			liveAppStats = new JSONObject();
			e.printStackTrace();
		}


		double currentMillisValue = Instant.now().toEpochMilli();
		
		redisSortedSet.write(liveAppStatsKey, currentMillisValue, liveAppStats.toString());

	}

	public static void cleanupLiveAppStats(long millis) {

		double currentMillisValue = Instant.now().toEpochMilli();
		double endMillisValue = currentMillisValue - millis;
		
		
		redisSortedSet.delete(liveAppStatsKey, 0.0, endMillisValue);
	}

}

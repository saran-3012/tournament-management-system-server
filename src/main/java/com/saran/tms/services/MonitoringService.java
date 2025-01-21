package com.saran.tms.services;

import java.lang.management.ClassLoadingMXBean;
import java.lang.management.CompilationMXBean;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.LockInfo;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.MemoryUsage;
import java.lang.management.MonitorInfo;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.RuntimeMXBean;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.Instant;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.saran.tms.routers.QueryParams;
import com.saran.tms.utils.Utilities;

import redis.clients.jedis.UnifiedJedis;
import redis.clients.jedis.resps.Tuple;

public class MonitoringService {
	
	
	public static JSONObject retriveAppStats(QueryParams queryParams) {
		long startDate = queryParams.getLong("start_date");
		long endDate = queryParams.getLong("end_date");
		long startTime = queryParams.getLong("start_time");
		long endTime = queryParams.getLong("end_time");
		

		
        String staticAppStatsKey = "staticAppStats";
        String liveAppStatsKey = "liveAppStats";
        
        UnifiedJedis jedis = new UnifiedJedis("redis://localhost:6379");
        
		String staticAppStatsString = jedis.get(staticAppStatsKey);
		List<Tuple> rangeLiveAppStats = jedis.zrangeByScoreWithScores(liveAppStatsKey, startDate + startTime, endDate + endTime);
		
		jedis.close();
		
		JSONArray liveAppStats = new JSONArray();
		
		final int n = rangeLiveAppStats.size();
		
		for(int i=n-1; i>=0; i--) {
			
			JSONObject liveAppStat = new JSONObject(rangeLiveAppStats.get(i).getElement());
			liveAppStat.put("timestamp", new Timestamp((long) rangeLiveAppStats.get(i).getScore()).toString());
			
			liveAppStats.put(liveAppStat);
		}
		
		JSONObject staticAppStats = new JSONObject(staticAppStatsString);
		
		JSONObject appStatsData = new JSONObject();
		
		appStatsData.put("static", staticAppStats);
		appStatsData.put("live", liveAppStats);

		return appStatsData;
	}
	
	
	@Deprecated
	public static void persistAppStats() {
		
		JSONObject appStats = new JSONObject();
		
		appStats.put("memoryStats", extractMemoryStats());
		appStats.put("threadStats", extractThreadStats());
		appStats.put("gcStats", extractGarbageCollectorStats());
		appStats.put("compilationStats", extractCompilationStats());
		appStats.put("runtimeStats", extractRuntimeStats());
		appStats.put("osStats", extractOperatingSystemStats());
		appStats.put("classLoadingStats", extractClassLoadingStats());
		
		
		String appStatsKey = "appstats";
		long currentMillisValue = Instant.now().toEpochMilli();
		
		
		UnifiedJedis jedis = new UnifiedJedis("redis://localhost:6379");
		jedis.zadd(appStatsKey, currentMillisValue, appStats.toString());
		jedis.close();
	}
	
	public static void persistStaticAppStats() {
		JSONObject staticAppStats = extractStaticAppStats();
		
		String staticAppStatsKey = "staticAppStats";
		
		UnifiedJedis jedis = new UnifiedJedis("redis://localhost:6379");
		jedis.set(staticAppStatsKey, staticAppStats.toString());
		jedis.close();
	}
	
	public static void persistLiveAppStats() {
		JSONObject liveAppStats = extractLiveAppStats();
		
		String liveAppStatsKey = "liveAppStats";
		long currentMillisValue = Instant.now().toEpochMilli();
		
		
		UnifiedJedis jedis = new UnifiedJedis("redis://localhost:6379");
		jedis.zadd(liveAppStatsKey, currentMillisValue, liveAppStats.toString());
		jedis.close();
	}
	
	@Deprecated
	public static void cleanupAppStats() {
		String appStatsKey = "appstats";
		long currentMillisValue = Instant.now().toEpochMilli();
		
		long endMillisValue = currentMillisValue - (3 * 60 * 60 * 1000);
		
		UnifiedJedis jedis = new UnifiedJedis("redis://localhost:6379");
		jedis.zremrangeByScore(appStatsKey, 0, endMillisValue);
		jedis.close();
	}
	
	public static void cleanupLiveAppStats(long millis) {
		String liveAppStatsKey = "liveAppStats";
		long currentMillisValue = Instant.now().toEpochMilli();
		
		long endMillisValue = currentMillisValue - millis;
		
		UnifiedJedis jedis = new UnifiedJedis("redis://localhost:6379");
		jedis.zremrangeByScore(liveAppStatsKey, 0, endMillisValue);
		jedis.close();
	}
	
	// ------------------- Isolating static and dynamic data ---------------------  //	
	
	public static JSONObject extractStaticAppStats() {
		
		MemoryMXBean memoryMxBean = ManagementFactory.getMemoryMXBean();
		
        MemoryUsage heapMemoryUsage = memoryMxBean.getHeapMemoryUsage();
		JSONObject heapMemoryData = new JSONObject();
		try {			
			heapMemoryData.put("initMemory", Utilities.convertByteUnit(heapMemoryUsage.getInit())); // Static
			heapMemoryData.put("maxMemory", Utilities.convertByteUnit(heapMemoryUsage.getMax())); // Static
		} 
		catch(Exception e) {}
		
        MemoryUsage nonHeapMemoryUsage = memoryMxBean.getNonHeapMemoryUsage();
        JSONObject nonHeapMemoryData = new JSONObject();
		try {			
			nonHeapMemoryData.put("initMemory", Utilities.convertByteUnit(nonHeapMemoryUsage.getInit())); // Static
			nonHeapMemoryData.put("maxMemory", Utilities.convertByteUnit(nonHeapMemoryUsage.getMax())); // Static
		} 
		catch(Exception e) {}
		
		List<MemoryPoolMXBean> memoryPoolMxBeans = ManagementFactory.getMemoryPoolMXBeans();
		JSONObject memorySpacesData = new JSONObject();
		
		for (MemoryPoolMXBean memoryPoolMxBean : memoryPoolMxBeans) {
			JSONObject memoryPoolData = new JSONObject();
			
			MemoryUsage memoryUsage = memoryPoolMxBean.getUsage();
			
			memoryPoolData.put("initMemory", Utilities.convertByteUnit(memoryUsage.getInit()));
			memoryPoolData.put("maxMemory", Utilities.convertByteUnit(memoryUsage.getMax()));

			memoryPoolData.put("memoryManagerNames", new JSONArray(memoryPoolMxBean.getMemoryManagerNames()));
			memoryPoolData.put("memoryType", memoryPoolMxBean.getType().toString());
			
			memorySpacesData.put(memoryPoolMxBean.getName(), memoryPoolData);
		}
		
		JSONObject memoryData = new JSONObject();
		memoryData.put("heapMemory", heapMemoryData);
		memoryData.put("nonHeapMemory", nonHeapMemoryData);
		memoryData.put("memorySpaces", memorySpacesData);
		

		
		List<GarbageCollectorMXBean> gcMXBeans = ManagementFactory.getGarbageCollectorMXBeans();
		JSONObject garbageCollectorStatsData = new JSONObject();

        for (GarbageCollectorMXBean gcMXBean : gcMXBeans) {
        	garbageCollectorStatsData.put(gcMXBean.getName(), new JSONArray(gcMXBean.getMemoryPoolNames()));	
        }
        
        
        
        
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
		Runtime runtime = Runtime.getRuntime();
		JSONObject runtimeData = new JSONObject();
			
		runtimeData.put("jvmProcessId", runtimeMXBean.getPid()); // Static
		runtimeData.put("jvmStartTime", new Timestamp(runtimeMXBean.getStartTime()).toString()); // Static
		runtimeData.put("systemClassLoaderClassPath", runtimeMXBean.getClassPath()); // Static
		runtimeData.put("libraryPath", runtimeMXBean.getLibraryPath()); // Static
		runtimeData.put("managementSpecVersion", runtimeMXBean.getManagementSpecVersion()); // Static
		runtimeData.put("jvmName", runtimeMXBean.getName()); // Static
		runtimeData.put("jvmSpecName", runtimeMXBean.getSpecName()); // Static
		runtimeData.put("jvmSpceVendor", runtimeMXBean.getSpecVendor()); // Static
		runtimeData.put("jvmSpecVersion", runtimeMXBean.getSpecVersion()); // Static
		runtimeData.put("jvmVmName", runtimeMXBean.getVmName()); // Static
		runtimeData.put("jvmVmVendor", runtimeMXBean.getVmVendor()); // Static
		runtimeData.put("jvmVmVersion", runtimeMXBean.getVmVersion()); // Static
		runtimeData.put("jvmMaxMemory", Utilities.convertByteUnit(runtime.maxMemory())); // Static

		
		
		
		OperatingSystemMXBean osMXBean = ManagementFactory.getOperatingSystemMXBean();
		
		JSONObject osData = new JSONObject();

		try {			
			osData.put("osName", osMXBean.getName()); // Static
			osData.put("osVersion", osMXBean.getVersion()); // Static
			osData.put("osArchitecture", osMXBean.getArch()); // Static
			osData.put("availableProcessor", osMXBean.getAvailableProcessors()); // Static
		} catch(Exception e) {}
		
        if (osMXBean instanceof com.sun.management.OperatingSystemMXBean) {
            com.sun.management.OperatingSystemMXBean sunOsMXBean = (com.sun.management.OperatingSystemMXBean) osMXBean;
            try {    			
            	osData.put("totalPhysicalMemorySize", Utilities.convertByteUnit(sunOsMXBean.getTotalPhysicalMemorySize())); // Static
            	osData.put("totalSwapSpace", Utilities.convertByteUnit(sunOsMXBean.getTotalSwapSpaceSize())); // Static
    		} catch(Exception e) {}
        }
        
        
        
        JSONObject staticAppStats = new JSONObject();
        
        staticAppStats.put("memory", memoryData);
        staticAppStats.put("gc", garbageCollectorStatsData);
        staticAppStats.put("runtime", runtimeData);
        staticAppStats.put("os", osData);
		
		return staticAppStats;
	}
	
	
	public static JSONObject extractLiveAppStats() {

		MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
		
        MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();
		JSONObject heapMemoryData = new JSONObject();
		try {			
			heapMemoryData.put("usedMemory", Utilities.convertByteUnit(heapMemoryUsage.getUsed()));
			heapMemoryData.put("allocatedMemory", Utilities.convertByteUnit(heapMemoryUsage.getCommitted()));
		} finally {}
		
        MemoryUsage nonHeapMemoryUsage = memoryMXBean.getNonHeapMemoryUsage();
        JSONObject nonHeapMemoryData = new JSONObject();
		try {			
			nonHeapMemoryData.put("usedMemory", Utilities.convertByteUnit(nonHeapMemoryUsage.getUsed()));
			nonHeapMemoryData.put("allocatedMemory", Utilities.convertByteUnit(nonHeapMemoryUsage.getCommitted()));
		} finally {}
		
		List<MemoryPoolMXBean> memoryPoolMXBeans = ManagementFactory.getMemoryPoolMXBeans();
		JSONObject memorySpacesData = new JSONObject();
		
		for (MemoryPoolMXBean memoryPoolMxBean : memoryPoolMXBeans) {
			JSONObject memoryPoolData = new JSONObject();
			try {				
				
				if(memoryPoolMxBean.isCollectionUsageThresholdSupported()) {
					memoryPoolData.put("collectionUsageThreshold", Utilities.convertByteUnit(memoryPoolMxBean.getCollectionUsageThreshold()));
					memoryPoolData.put("collectionUsageThresholdCount", memoryPoolMxBean.getCollectionUsageThresholdCount());
				}
				
				if(memoryPoolMxBean.isUsageThresholdSupported()) {
					memoryPoolData.put("usageThreshold", Utilities.convertByteUnit(memoryPoolMxBean.getUsageThreshold()));
					memoryPoolData.put("usageThresholdCount", memoryPoolMxBean.getUsageThresholdCount());
				}
				
				try {
					MemoryUsage cmu = memoryPoolMxBean.getCollectionUsage();
					memoryPoolData.put("collectionAllocatedMemory", Utilities.convertByteUnit(cmu.getCommitted()));
					memoryPoolData.put("collectionUsedMemory", Utilities.convertByteUnit(cmu.getUsed()));
				} catch(Exception e) {}
				
				MemoryUsage pmu = memoryPoolMxBean.getPeakUsage();
				memoryPoolData.put("peakAllocatedMemory", Utilities.convertByteUnit(pmu.getCommitted()));
				memoryPoolData.put("peakUsedMemory", Utilities.convertByteUnit(pmu.getUsed()));
				
				MemoryUsage mu = memoryPoolMxBean.getUsage();
				memoryPoolData.put("allocatedMemory", Utilities.convertByteUnit(mu.getCommitted()));
				memoryPoolData.put("usedMemory", Utilities.convertByteUnit(mu.getUsed()));
				
			} 
			catch(Exception e) {
				e.printStackTrace();
			}
			memorySpacesData.put(memoryPoolMxBean.getName(), memoryPoolData);
		}
        
		JSONObject memoryData = new JSONObject();
		memoryData.put("heapMemory", heapMemoryData);
		memoryData.put("nonHeapMemory", nonHeapMemoryData);
		memoryData.put("memorySpaces", memorySpacesData);
		
		
		
		ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();

		JSONObject threadUsageData = new JSONObject();
		try {			
			threadUsageData.put("activeThreadCount", threadMXBean.getThreadCount());
			threadUsageData.put("peakThreadCount", threadMXBean.getPeakThreadCount());
			threadUsageData.put("daemonThreadCount", threadMXBean.getDaemonThreadCount());
			threadUsageData.put("totalThreadCount", threadMXBean.getTotalStartedThreadCount());
			
			long[] activeThreads = threadMXBean.getAllThreadIds();
			threadUsageData.put("activeThreads", new JSONArray((activeThreads == null)? new Object[]{} : activeThreads));
			
			long[] deadlockedThreads = threadMXBean.findDeadlockedThreads();
			threadUsageData.put("deadlockThreads", new JSONArray((deadlockedThreads == null)? new Object[]{} : deadlockedThreads));
			
			JSONObject threadInfosData = new JSONObject();
			ThreadInfo[] threadInfos = threadMXBean.dumpAllThreads(true, true);
			for(final ThreadInfo threadInfo : threadInfos) {
				JSONObject threadInfoData = new JSONObject();
				
				threadInfoData.put("blockedTime", threadInfo.getBlockedTime() == -1? "-" : threadInfo.getBlockedTime() + " ms");
				threadInfoData.put("blockedCount", threadInfo.getBlockedCount());
				threadInfoData.put("lockName", threadInfo.getLockName());
				threadInfoData.put("lockOwnerId", threadInfo.getLockOwnerId());
				threadInfoData.put("threadPriority", threadInfo.getPriority());
				threadInfoData.put("threadName", threadInfo.getThreadName());
				threadInfoData.put("threadState", threadInfo.getThreadState());
				threadInfoData.put("threadCpuTime", Utilities.upliftTimeUnit( threadMXBean.getThreadCpuTime(threadInfo.getThreadId()) ));
				threadInfoData.put("threadUserTime", Utilities.upliftTimeUnit( threadMXBean.getThreadUserTime(threadInfo.getThreadId()) ));

				StackTraceElement[] stkTraces = threadInfo.getStackTrace();
				JSONArray stkTraceArray = new JSONArray();
				for(final StackTraceElement stkTrace : stkTraces) {
					stkTraceArray.put(stkTrace.toString());
				}
				threadInfoData.put("stackTrace", stkTraceArray);

				// MAY BE ADDED LATER //
//				MonitorInfo[] lckMonts = threadInfo.getLockedMonitors();
//				LockInfo[] lckInfos = threadInfo.getLockedSynchronizers();
//				LockInfo lckInfo = threadInfo.getLockInfo();
				// MAY BE ADDED LATER //
				
				
				
				threadInfosData.put(((Long) threadInfo.getThreadId()).toString(), threadInfoData);
				
				
//				---------------------
//				
//				long blkTime = threadInfo.getBlockedTime();
//				long blkCount = threadInfo.getBlockedCount();
				
				// MAY BE ADDED LATER //
//				MonitorInfo[] lckMonts = threadInfo.getLockedMonitors();
//				LockInfo[] lckInfos = threadInfo.getLockedSynchronizers();
//				LockInfo lckInfo = threadInfo.getLockInfo();
//				String lckName = threadInfo.getLockName();
				// MAY BE ADDED LATER //
				
//				long lckOwnerId = threadInfo.getLockOwnerId();
//				int threadPriority = threadInfo.getPriority();
//				StackTraceElement[] stkkTraces = threadInfo.getStackTrace();
//				long threadId = threadInfo.getThreadId();
//				String threadName = threadInfo.getThreadName();
//				Thread.State threadState = threadInfo.getThreadState();
//				
//				long threadCpuTime = threadMXBean.getThreadCpuTime(threadInfo.getThreadId());
//				long threadUserTime = threadMXBean.getThreadUserTime(threadInfo.getThreadId());
			}
			
			threadUsageData.put("threadDump", threadInfosData);
		} finally {}

		
		
		List<GarbageCollectorMXBean> gcMXBeans = ManagementFactory.getGarbageCollectorMXBeans();
		JSONObject garbageCollectorStatsData = new JSONObject();

        for (GarbageCollectorMXBean gcMXBean : gcMXBeans) {

        	JSONObject gcStatsData = new JSONObject();
        	try {
      
        		gcStatsData.put("gcCount", gcMXBean.getCollectionCount());
        		gcStatsData.put("gcTime", gcMXBean.getCollectionTime());
    			
    		} finally {}
        	
        	garbageCollectorStatsData.put(gcMXBean.getName(), gcStatsData);
        }
        
        CompilationMXBean compilationMXBean = ManagementFactory.getCompilationMXBean();
		 
		JSONObject compilationData = new JSONObject();
			
		if(compilationMXBean.isCompilationTimeMonitoringSupported()) {
			compilationData.put("compilerName", compilationMXBean.getName()); // Static
			compilationData.put("compilationTime", compilationMXBean.getTotalCompilationTime());
		}
		
		
		
		RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
		Runtime runtime = Runtime.getRuntime();
		
		JSONObject runtimeData = new JSONObject();
	
		try {			
			runtimeData.put("jvmUpTime", runtimeMXBean.getUptime());
			runtimeData.put("jvmTotalMemory", Utilities.convertByteUnit(runtime.totalMemory()));
			runtimeData.put("jvmFreeMemory", Utilities.convertByteUnit(runtime.freeMemory()));
		} finally {}
		
		
		
		
		
		OperatingSystemMXBean osMXBean = ManagementFactory.getOperatingSystemMXBean();
		
		JSONObject osData = new JSONObject();

		try {			
			osData.put("systemLoadAverage", osMXBean.getSystemLoadAverage());
		} finally {}
		
        if (osMXBean instanceof com.sun.management.OperatingSystemMXBean) {
            com.sun.management.OperatingSystemMXBean sunOsMXBean = (com.sun.management.OperatingSystemMXBean) osMXBean;
            try {    			
            	NumberFormat formatter = new DecimalFormat("#0.00%"); 
            	osData.put("systemCpuLoad", formatter.format(sunOsMXBean.getSystemCpuLoad()));
            	osData.put("processCpuLoad", formatter.format(sunOsMXBean.getProcessCpuLoad()));
            	osData.put("freePhysicalMemorySize", Utilities.convertByteUnit(sunOsMXBean.getFreePhysicalMemorySize()));
            	osData.put("freeSwapSpace", Utilities.convertByteUnit(sunOsMXBean.getFreeSwapSpaceSize()));
    		} finally {}
        }
        
        
        ClassLoadingMXBean classLoadingMXBean = ManagementFactory.getClassLoadingMXBean();
		
		JSONObject classLoadingData = new JSONObject();
		
		try {			
			classLoadingData.put("loadedClassCount", classLoadingMXBean.getLoadedClassCount());
			classLoadingData.put("totalLoadedClassCount", classLoadingMXBean.getTotalLoadedClassCount());
			classLoadingData.put("unloadedClassCount", classLoadingMXBean.getUnloadedClassCount());
		} finally {}
		
		
		JSONObject liveAppStats = new JSONObject();
		
		liveAppStats.put("memory", memoryData);
		liveAppStats.put("thread", threadUsageData);
        liveAppStats.put("gc", garbageCollectorStatsData);
        liveAppStats.put("compilation", compilationData);
        liveAppStats.put("runtime", runtimeData);
        liveAppStats.put("os", osData);
        liveAppStats.put("classLoading", classLoadingData);
		
		return liveAppStats;
	}
	
	//	------------------------------------------------------------------  //
	
	public static JSONObject extractMemoryStats() {

		MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
		
        MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();
		JSONObject heapMemoryData = new JSONObject();
		try {			
			heapMemoryData.put("initMemory", heapMemoryUsage.getInit()); // Static
			heapMemoryData.put("usedMemory", heapMemoryUsage.getUsed());
			heapMemoryData.put("allocatedMemory", heapMemoryUsage.getCommitted());
			heapMemoryData.put("maxMemory", heapMemoryUsage.getMax()); // Static
		} finally {}
		
        MemoryUsage nonHeapMemoryUsage = memoryMXBean.getNonHeapMemoryUsage();
        JSONObject nonHeapMemoryData = new JSONObject();
		try {			
			nonHeapMemoryData.put("initMemory", nonHeapMemoryUsage.getInit()); // Static
			nonHeapMemoryData.put("usedMemory", nonHeapMemoryUsage.getUsed());
			nonHeapMemoryData.put("allocatedMemory", nonHeapMemoryUsage.getCommitted());
			nonHeapMemoryData.put("maxMemory", nonHeapMemoryUsage.getMax()); // Static
		} finally {}
		
		List<MemoryPoolMXBean> memoryPoolMXBeans = ManagementFactory.getMemoryPoolMXBeans();
		JSONArray memorySpacesData = new JSONArray();
		for (MemoryPoolMXBean memoryPoolMXBean : memoryPoolMXBeans) {
			JSONObject memorySpaceData = new JSONObject();
			MemoryUsage memorySpaceUsage = memoryPoolMXBean.getUsage();
			try {				
				memorySpaceData.put("memorySpaceName", memoryPoolMXBean.getName()); // Static
				memorySpaceData.put("memorySpaceType", memoryPoolMXBean.getType()); // Static
				memorySpaceData.put("initMemory", memorySpaceUsage.getInit()); // Static
				memorySpaceData.put("usedMemory", memorySpaceUsage.getUsed());
				memorySpaceData.put("allocatedMemory", memorySpaceUsage.getCommitted());
				memorySpaceData.put("maxMemory", memorySpaceUsage.getMax()); // Static
			} 
			catch(Exception e) {
				e.printStackTrace();
			}
			memorySpacesData.put(memorySpaceData);
		}
        
		JSONObject memoryUsageData = new JSONObject();
		memoryUsageData.put("heapMemoryData", heapMemoryData);
		memoryUsageData.put("nonHeapMemoryData", nonHeapMemoryData);
		memoryUsageData.put("memorySpacesData", memorySpacesData);
		
		return memoryUsageData;
	}
	
	public static JSONObject extractThreadStats() {
		
		ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();

		JSONObject threadUsageData = new JSONObject();
		try {			
			threadUsageData.put("activeThreadCount", threadMXBean.getThreadCount());
			threadUsageData.put("peakThreadCount", threadMXBean.getPeakThreadCount());
			threadUsageData.put("daemonThreadCount", threadMXBean.getDaemonThreadCount());
			threadUsageData.put("totalThreadCount", threadMXBean.getTotalStartedThreadCount());
			
			long[] threadIds = threadMXBean.getAllThreadIds();
			ThreadInfo[] threadInfos = threadMXBean.getThreadInfo(threadIds);
			for(final ThreadInfo threadInfo : threadInfos) {
				threadInfo.getBlockedTime();
				threadInfo.getBlockedCount();
				threadInfo.getLockedMonitors();
				threadInfo.getLockedSynchronizers();
				threadInfo.getLockInfo();
				threadInfo.getLockName();
				threadInfo.getLockOwnerId();
				threadInfo.getPriority();
				threadInfo.getStackTrace();
				threadInfo.getThreadId();
				threadInfo.getThreadName();
				threadInfo.getThreadState();
				
				threadMXBean.getThreadCpuTime(threadInfo.getThreadId());
				threadMXBean.getThreadUserTime(threadInfo.getThreadId());
			}
			
			long[] deadlockedThreads = threadMXBean.findDeadlockedThreads();
			threadUsageData.put("deadlockThreadCount", (deadlockedThreads == null)? 0 : deadlockedThreads.length);
		} finally {}

        
        return threadUsageData;
	}
	
	public static JSONArray extractGarbageCollectorStats() {
		
		List<GarbageCollectorMXBean> gcMXBeans = ManagementFactory.getGarbageCollectorMXBeans();
		JSONArray garbageCollectorStatsData = new JSONArray();

        for (GarbageCollectorMXBean gcMXBean : gcMXBeans) {

        	JSONObject gcStatsData = new JSONObject();
        	try {
        		
        		
        		gcStatsData.put("gcName", gcMXBean.getName()); // Static
        		gcStatsData.put("gcCount", gcMXBean.getCollectionCount());
        		gcStatsData.put("gcTime", gcMXBean.getCollectionTime());
        		gcStatsData.put("gcManagedMemoryPools", new JSONArray(gcMXBean.getMemoryPoolNames())); // Static
    			
    		} finally {}
        	
        	garbageCollectorStatsData.put(gcStatsData);
        }
        
        return garbageCollectorStatsData;
	}
	
	public static JSONObject extractCompilationStats() {
		 CompilationMXBean compilationMXBean = ManagementFactory.getCompilationMXBean();
		 
		 JSONObject compilationData = new JSONObject();
			
		 if(compilationMXBean.isCompilationTimeMonitoringSupported()) {

			 compilationData.put("compilerName", compilationMXBean.getName()); // Static
			 compilationData.put("compilationTime", compilationMXBean.getTotalCompilationTime());
		 }
		
		 return compilationData;
	}
	
	public static JSONObject extractRuntimeStats() {
		RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
		Runtime runtime = Runtime.getRuntime();
		
		JSONObject runtimeData = new JSONObject();
	
		try {			
			runtimeData.put("jvmProcessId", runtimeMXBean.getPid()); // Static
			runtimeData.put("jvmStartTime", runtimeMXBean.getStartTime()); // Static
			runtimeData.put("jvmUpTime", runtimeMXBean.getUptime());
			runtimeData.put("systemClassLoaderClassPath", runtimeMXBean.getClassPath()); // Static
			runtimeData.put("libraryPath", runtimeMXBean.getLibraryPath()); // Static
			runtimeData.put("managementSpecVersion", runtimeMXBean.getManagementSpecVersion()); // Static
			runtimeData.put("jvmName", runtimeMXBean.getName()); // Static
			runtimeData.put("jvmSpecName", runtimeMXBean.getSpecName()); // Static
			runtimeData.put("jvmSpceVendor", runtimeMXBean.getSpecVendor()); // Static
			runtimeData.put("jvmSpecVersion", runtimeMXBean.getSpecVersion()); // Static
			runtimeData.put("jvmVmName", runtimeMXBean.getVmName()); // Static
			runtimeData.put("jvmVmVendor", runtimeMXBean.getVmVendor()); // Static
			runtimeData.put("jvmVmVersion", runtimeMXBean.getVmVersion()); // Static
			runtimeData.put("jvmTotalMemory", runtime.totalMemory());
			runtimeData.put("jvmFreeMemory", runtime.freeMemory());
			runtimeData.put("jvmMaxMemory", runtime.maxMemory()); // Static
		} finally {}
		
		return runtimeData;
	}
	
	public static JSONObject extractOperatingSystemStats() {
		
		OperatingSystemMXBean osMXBean = ManagementFactory.getOperatingSystemMXBean();
		
		JSONObject osData = new JSONObject();

		try {			
			osData.put("osName", osMXBean.getName()); // Static
			osData.put("osVersion", osMXBean.getVersion()); // Static
			osData.put("osArchitecture", osMXBean.getArch()); // Static
			osData.put("availableProcessor", osMXBean.getAvailableProcessors()); // Static
			osData.put("systemLoadAverage", osMXBean.getSystemLoadAverage());
		} finally {}
		
        if (osMXBean instanceof com.sun.management.OperatingSystemMXBean) {
            com.sun.management.OperatingSystemMXBean sunOsMXBean = (com.sun.management.OperatingSystemMXBean) osMXBean;
            try {    			
            	osData.put("systemCpuLoad", sunOsMXBean.getSystemCpuLoad());
            	osData.put("processCpuLoad", sunOsMXBean.getProcessCpuLoad());
            	osData.put("totalPhysicalMemorySize", sunOsMXBean.getTotalPhysicalMemorySize()); // Static
            	osData.put("freePhysicalMemorySize", sunOsMXBean.getFreePhysicalMemorySize());
            	osData.put("totalSwapSpace", sunOsMXBean.getTotalSwapSpaceSize()); // Static
            	osData.put("freeSwapSpace", sunOsMXBean.getFreeSwapSpaceSize());
    		} finally {}
        }
        
        return osData;
	}
	
	public static JSONObject extractClassLoadingStats() {
		ClassLoadingMXBean classLoadingMXBean = ManagementFactory.getClassLoadingMXBean();
		
		JSONObject classLoadingData = new JSONObject();
		
		try {			
			classLoadingData.put("loadedClassCount", classLoadingMXBean.getLoadedClassCount());
			classLoadingData.put("totalLoadedClassCount", classLoadingMXBean.getTotalLoadedClassCount());
			classLoadingData.put("unloadedClassCount", classLoadingMXBean.getUnloadedClassCount());
		} finally {}

		return classLoadingData;
	}
	
}

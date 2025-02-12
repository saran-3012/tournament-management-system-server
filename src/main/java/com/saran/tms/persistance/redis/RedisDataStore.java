package com.saran.tms.persistance.redis;



import com.saran.tms.persistance.DataStore;
import com.saran.tms.persistance.DataStoreConfig;
import com.saran.tms.persistance.StorageStrategy;

import redis.clients.jedis.Jedis;

public class RedisDataStore implements DataStore {

	private RedisConfig redisConfig;
	private StorageStrategy redisStrategy;
	
	public RedisDataStore(DataStoreConfig dsConfig) {
		this.redisConfig = (RedisConfig) dsConfig;
		this.redisStrategy = RedisStrategies.cacheStrategy;
	}
	
	private Jedis getRedisConnection() {
		return new Jedis(redisConfig.getHost(), redisConfig.getPort());
	}
	
	@Override
	public void setStrategy(StorageStrategy rStrategy) {
		if(rStrategy instanceof RedisStrategy) {			
			this.redisStrategy = rStrategy;
		}
	}

	@Override
	public Object write(Object ...args) {
		Jedis redis = getRedisConnection();
		Object[] arguments = prependObjects(new Object[]{redis}, args);
		Object res = redisStrategy.write(arguments);
		redis.close();
		return res;
	}

	@Override
	public Object read(Object ...args) {
		Jedis redis = getRedisConnection();
		Object[] arguments = prependObjects(new Object[]{redis}, args);
		Object res = redisStrategy.read(arguments);
		redis.close();
		return res;
	}

	@Override
	public Object update(Object ...args) {
		Jedis redis = getRedisConnection();
		Object[] arguments = prependObjects(new Object[]{redis}, args);
		Object res = redisStrategy.update(arguments);
		redis.close();
		return res;
	}

	@Override
	public Object delete(Object ...args) {
		Jedis redis = getRedisConnection();
		Object[] arguments = prependObjects(new Object[]{redis}, args);
		Object res = redisStrategy.delete(arguments);
		redis.close();
		return res;
	}

}

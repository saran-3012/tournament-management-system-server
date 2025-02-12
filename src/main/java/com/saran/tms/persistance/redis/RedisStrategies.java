package com.saran.tms.persistance.redis;

import java.util.Arrays;
import redis.clients.jedis.Jedis;

public class RedisStrategies {
	
	final public static RedisStrategy cacheStrategy = new RedisStrategy() {

		@Override
		public Object write(Object... args) {
			Jedis redis = (Jedis) args[0];
			redis.set(args[1].toString(), args[2].toString());
			return null;
		}

		@Override
		public Object read(Object... args) {
			Jedis redis = (Jedis) args[0];
			return redis.get(args[1].toString());
		}

		@Override
		public Object update(Object... args) {
			Jedis redis = (Jedis) args[0];
			redis.set(args[1].toString(), args[2].toString());
			return null;
		}

		@Override
		public Object delete(Object... args) {
			Jedis redis = (Jedis) args[0];
			redis.del((String[]) Arrays.copyOfRange(args, 1, args.length));
			return null;
		}
		
	};
	
	final public static RedisStrategy sortedSetStrategy = new RedisStrategy() {

		@Override
		public Object write(Object... args) {
			Jedis redis = (Jedis) args[0];
			redis.zadd(args[1].toString(), (double) args[2], args[3].toString());
			return null;
		}

		@Override
		public Object read(Object... args) {
			Jedis redis = (Jedis) args[0];
			return redis.zrangeByScoreWithScores(args[1].toString(), (double) args[2], (double) args[3]);
		}

		@Override
		public Object update(Object... args) {
			Jedis redis = (Jedis) args[0];
			redis.zadd(args[1].toString(), (double) args[2], args[3].toString());
			return null;
		}

		@Override
		public Object delete(Object... args) {
			Jedis redis = (Jedis) args[0];
			redis.zremrangeByScore(args[1].toString(), (double) args[2], (double) args[3]);
			return null;
		}
		
	};
	
	
}

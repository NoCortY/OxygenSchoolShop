package cn.objectspace.schoolshop.cache;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 强指定redis的JedisPool接口构造函数，这样才能在centos成功创建jedispool
 * 
 * @author xiangze
 *
 */
public class JedisPoolWriper {
	private JedisPool jedisPool;

	public JedisPoolWriper(final JedisPoolConfig poolConfig, final String host,
			final int port,String password) {
		try {
			jedisPool = new JedisPool(poolConfig, host, port,60000,password);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public JedisPool getJedisPool() {
		return jedisPool;
	}

	public void setJedisPool(JedisPool jedisPool) {
		this.jedisPool = jedisPool;
	}

}

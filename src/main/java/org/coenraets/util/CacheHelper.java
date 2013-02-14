package org.coenraets.util;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.config.CacheWriterConfiguration;
import net.sf.ehcache.config.CacheWriterConfiguration.WriteMode;
import net.sf.ehcache.config.Configuration.Monitoring;
import net.sf.ehcache.config.Configuration;
import net.sf.ehcache.config.ManagementRESTServiceConfiguration;

public class CacheHelper {

	public enum CacheName {
		WRITE_THOUGH,
		WRITE_BEHIND,
		WITHOUT_WRITER
	}
	
	private static final CacheManager mgr;
	
	static {
		
		// Configuration du cache manager
		Configuration configuration = new Configuration();
		
		// configuration du cache write though
		CacheConfiguration cacheWriteThough = new CacheConfiguration(CacheName.WRITE_THOUGH.name(), 10000)
					.cacheWriter(new CacheWriterConfiguration());
					
		// configuration du cache write behind
		CacheConfiguration cacheWriteBehind = new CacheConfiguration(CacheName.WRITE_BEHIND.name(), 10000)
					.cacheWriter(new CacheWriterConfiguration().writeMode(WriteMode.WRITE_BEHIND));
		
		// configuration du cache sans writer avec activation des stats
		CacheConfiguration cacheWithStats = new CacheConfiguration(CacheName.WITHOUT_WRITER.name(),10000)
											.maxEntriesLocalHeap(10000)
											.maxEntriesLocalDisk(100000);
		
		cacheWithStats.statistics(true);
					
		ManagementRESTServiceConfiguration rconf = new ManagementRESTServiceConfiguration();
		rconf.setBind("localhost:9888");
		rconf.setEnabled(true);
		configuration.monitoring(Monitoring.ON);
		configuration.managementRESTService(rconf);

		configuration.addCache(cacheWriteThough);
		configuration.addCache(cacheWriteBehind);
		configuration.addCache(cacheWithStats);
		
		mgr = CacheManager.create(configuration);
	}
	
	public static Cache getCache(CacheName cachename) {
		return mgr.getCache(cachename.name());
	}
	
	public static Cache getCacheWriteThough() {
		return getCache(CacheName.WRITE_THOUGH);
	}
	public static Cache getCacheBehind() {
		return getCache(CacheName.WRITE_BEHIND);
	}
	public static Cache getCacheWithoutWriter() {
		return getCache(CacheName.WITHOUT_WRITER);
	}
}

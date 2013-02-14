package org.coenraets.service;

import java.util.List;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.constructs.blocking.CacheEntryFactory;
import net.sf.ehcache.constructs.blocking.SelfPopulatingCache;

import org.coenraets.model.Wine;
import org.coenraets.util.CacheHelper;

/**
 * @author Christophe Coenraets
 */
public class Exercise2 implements WineService {
	WineMysql mysql;
	private Ehcache selfPopulatingCache;

	public Exercise2() {
		mysql = new WineMysql();
		CacheEntryFactory factory = new CacheEntryFactory() {

			@Override
			public Object createEntry(Object arg0) throws Exception {

				return mysql.findById((Long) arg0);
			}
		};

		selfPopulatingCache = new SelfPopulatingCache(CacheHelper.getCacheWriteThough(),
				factory);
	}

	@Override
	public List<Wine> findAll() {
		throw new RuntimeException("not implemented");
	}

	@Override
	public List<Wine> findByName(String name) {
		return mysql.findByName(name);
	}

	@Override
	/**
	 * Exercise 2A. Modifier cette méthode. Le système de données est le cache => aucun appel à mysql dans cette méthode.
	 * C'est au cache qu'il faut indiquer comment se mettre à jour en cas d'objet non présent dans le cache
	 */
	public Wine findById(long id) {
		Element o = selfPopulatingCache.get(id);
		return (o == null) ? null : (Wine) o.getObjectValue();
	}

	@Override
	public Wine save(Wine wine) {
		throw new RuntimeException("not implemented");
	}

	@Override
	public Wine create(Wine wine) {
		throw new RuntimeException("not implemented");
	}

	@Override
	public Wine update(Wine wine) {
		throw new RuntimeException("not implemented");
	}

	@Override
	public boolean remove(long id) {
		throw new RuntimeException("not implemented");
	}

	@Override
	public void clear() {
		selfPopulatingCache.removeAll();
	}

	@Override
	public void init() {
	}

	public Ehcache getCache() {
		return selfPopulatingCache;
	}

	public WineMysql getMysql() {
		return mysql;
	}

	public void setMysql(final WineMysql mysql) {
		this.mysql = mysql;
	}

	public void setCache(final Ehcache selfPopulatingCache) {
		this.selfPopulatingCache = selfPopulatingCache;
	}
}

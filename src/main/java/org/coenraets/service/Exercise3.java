package org.coenraets.service;

import java.util.List;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import org.coenraets.model.Wine;
import org.coenraets.util.CacheHelper;

/**
 * @author Christophe Coenraets
 */
public class Exercise3 implements WineService {
	private WineMysql mysql;
	private Cache wineCache;

	public Exercise3() {
		mysql = new WineMysql();
		wineCache = CacheHelper.getCacheWriteThough();
		wineCache.registerCacheWriter(new MyCacheWriter());

	}

	@Override
	public List<Wine> findAll() {
		throw new RuntimeException("not implemented");
	}

	@Override
	public List<Wine> findByName(String name) {
		throw new RuntimeException("not implemented");
	}

	@Override
	public Wine findById(long id) {
		Element o = wineCache.get(id);
		return (o == null) ? null : (Wine) o.getObjectValue();
	}

	@Override
	public Wine save(Wine wine) {
		throw new RuntimeException("not implemented");
	}

	@Override
	public Wine create(Wine wine) {
		// put an entry in cache and in database
		wineCache.putWithWriter(new Element(wine.getId(), wine));
		return null;
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
		wineCache.removeAll();
	}

	@Override
	public void init() {
	}

	public void setMysql(final WineMysql mysql) {
		this.mysql = mysql;
	}

	public void setCache(final Cache wineCache) {
		this.wineCache = wineCache;
	}
}

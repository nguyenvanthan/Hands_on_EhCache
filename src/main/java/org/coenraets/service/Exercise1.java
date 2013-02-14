package org.coenraets.service;

import java.util.List;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.config.Configuration;

import org.coenraets.model.Wine;
import org.coenraets.util.CacheHelper;
import org.coenraets.util.ConnectionHelper;

/**
 * Des indices pour créer le cache sont dans le fichier tips1.txt dans ce meme répertoire !
 * Saurez vous vous en passer ? :)
 *
 */
public class Exercise1 implements WineService {
  private WineMysql mysql;
  private Cache wineCache;

  public Exercise1() {
	  // initialize cache and dao
	  wineCache = CacheHelper.getCacheWriteThough();
	  mysql = new WineMysql();
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
    // cherche dans le cache
	Element e =  wineCache.get(id);
	Wine result = null;
	if (e != null) {
		result = (Wine)e.getObjectValue();
	} else {
		result = mysql.findById(id);
		wineCache.put(new Element(id, result));
	}
    return result;
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
    wineCache.removeAll();
  }

  @Override
  public void init() {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  public Cache getCache() {
    return wineCache;
  }

  public void setMysql(final WineMysql mysql) {
    this.mysql = mysql;
  }

  public void setWineCache(final Cache wineCache) {
    this.wineCache = wineCache;
  }
}

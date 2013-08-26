package com.smart.mis.datastore;

//import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Transaction;
public class Util {

  private static final Logger logger = Logger.getLogger(Util.class.getCanonicalName());
  
  private static DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

	/**
	 * Add the entity to cache and also to the datastore
	 * @param entity
	 *          : entity to be persisted
	 */
  public static boolean persistEntity(Entity entity) {
	logger.log(Level.INFO, "Saving entity " + entity.getKey());
    Transaction txn = datastore.beginTransaction();
    try {
      datastore.put(entity);
	  txn.commit();
    } catch (Exception e){
    	return false; // catch exception
    } finally {
	  if (txn.isActive()) {
	    txn.rollback();
	    return false; // fail and rollback transaction
	  }
    }
    return true; // Success
  }

	/**
	 * Delete the entity from persistent store represented by the key
	 * Also delete it from cache
	 * 
	 * @param key
	 *          : key to delete the entity from the persistent store
	 */
  public static void deleteEntity(Key key) {
	logger.log(Level.INFO, "Deleting entity " + key.toString());
	Transaction txn = datastore.beginTransaction();
	try {
	  datastore.delete(key);
	  txn.commit();
	} finally {
	  if (txn.isActive()) {
	    txn.rollback();
	  }
    }
  }

	/**
	 * Delete the entities represented by a list of keys
	 * Delete the entitites from cache also
	 * 
	 * @param keys : keys for the entities that are to be deleted
	 */
  public static void deleteEntity(final List<Key> keys) {
    datastore.delete(new Iterable<Key>() {
    	public Iterator<Key> iterator() {
		  return keys.iterator();
	  }
	});
  }

	/**
	 * Search and return the entity from the cache . If absent , search the
	 * datastore.
	 * 
	 * @param key
	 *          : key to find the entity
	 * @return entity
	 */
  public static Entity findEntity(Key key) {
	logger.log(Level.INFO, "Search the entity " + key.toString());
	try {
	  return datastore.get(key);
	} catch (EntityNotFoundException e) {
	  return null;
	}
  }

	/***
	 * Search entities based on search criteria
	 * 
	 * @param kind
	 * @param searchBy
	 *          : Searching Criteria (Property)
	 * @param searchFor
	 *          : Searching Value (Property Value)
	 * @return List all entities of a kind from the cache or datastore (if not in
	 *         cache) with the specified properties
	 */
  public static Iterable<Entity> listEntities(String kind, String searchBy, String searchFor) {
	logger.log(Level.INFO, "Search entities by " + searchBy + " for value '" + searchFor +"' in " + kind);
	Query query = new Query(kind);
    if (searchFor != null && !"".equals(searchFor)) {
      Filter filter = new Query.FilterPredicate(searchBy, FilterOperator.EQUAL, searchFor);
      query.setFilter(filter);
    }
    PreparedQuery pq = datastore.prepare(query);
    return pq.asIterable();
  }
  
	/***
	 * Search entities based on search criteria
	 * 
	 * @param kind
	 * @param filter
	 *          : Searching Filter - new Query.FilterPredicate
	 * @return List all entities of a kind from the cache or datastore (if not in
	 *         cache) with the specified properties
	 */
  public static Iterable<Entity> listEntities(String kind, Filter filter) {
	logger.log(Level.INFO, "Search entities based on Filter");
	Query query = new Query(kind);
	if (filter != null) query.setFilter(filter);
    PreparedQuery pq = datastore.prepare(query);
    return pq.asIterable();
  }

	/**
	 * Get the list of children from a parent key in the entity group
	 *  
	 * @param kind : the entity kind of the children that is to be searched for 
	 * @param ancestor : the parent key of the entity group where we need to search
	 * @return iterable with all children of the parent of the specified kind
	 */
  public static Iterable<Entity> listChildren(String kind, Key ancestor) {
	logger.log(Level.INFO, "Search entities based on parent");
	Query query = new Query(kind);
    query.setAncestor(ancestor);
    //query.addFilter(Entity.KEY_RESERVED_PROPERTY, FilterOperator.GREATER_THAN, ancestor);
    query.setFilter(new Query.FilterPredicate(Entity.KEY_RESERVED_PROPERTY, FilterOperator.GREATER_THAN, ancestor));
    PreparedQuery pq = datastore.prepare(query);
    return pq.asIterable();
  }

	/**
	 * Get the list of keys of all children for a given entity kind in a given entity group 
	 * represented by the parent key
	 * @param kind : Entity kind of the children that needs to be searched for
	 * @param ancestor : Parent key of the entity group that needs to be searched for
	 * @return an iterable with keys of children
	 */
  public static Iterable<Entity> listChildKeys(String kind, Key ancestor) {
	logger.log(Level.INFO, "Search entities based on parent");
	Query query = new Query(kind);
    query.setAncestor(ancestor).setKeysOnly();
    //query.addFilter(Entity.KEY_RESERVED_PROPERTY, FilterOperator.GREATER_THAN,ancestor);
    query.setFilter(new Query.FilterPredicate(Entity.KEY_RESERVED_PROPERTY, FilterOperator.GREATER_THAN, ancestor));
    PreparedQuery pq = datastore.prepare(query);
    return pq.asIterable();
  }
  
	/**
	 * List the entities in JSON format
	 * 
	 * @param entities  entities to return as JSON strings
	 */
	public static String writeJSON(Iterable<Entity> entities) {
	  logger.log(Level.INFO, "creating JSON format object");
		StringBuilder sb = new StringBuilder();
		
		int i = 0;
		sb.append("{\"data\": [");
		for (Entity result : entities) {
		  Map<String, Object> properties = result.getProperties();
		  sb.append("{");
		  if (result.getKey().getName() == null)
			sb.append("\"name\" : \"" + result.getKey().getId() + "\",");
		  else
			sb.append("\"name\" : \"" + result.getKey().getName() + "\",");
	
		  for (String key : properties.keySet()) {
			sb.append("\"" + key + "\" : \"" + properties.get(key) + "\",");
		  }
		  sb.deleteCharAt(sb.lastIndexOf(","));
		  sb.append("},");
		  i++;
		}
		if (i > 0) {
		  sb.deleteCharAt(sb.lastIndexOf(","));
		}  
		sb.append("]}");
		return sb.toString();
	}

	  /**
	   * get DatastoreService instance
	   * @return DatastoreService instance
	   */
	  public static DatastoreService getDatastoreServiceInstance(){
		  return datastore;
	  }
  
	public static Key getKey(String kind){
		return datastore.allocateIds(kind, 1).getStart();
	}
	
	public static void debug(String kind) {
		Iterable<Entity> entities = listEntities(kind, null, null);
		int counter = 0;
		for (Entity result : entities) {
			Map<String,Object> properies = result.getProperties();
			logger.log(Level.INFO, "fetch :" + ++counter
					+"	kind :" + result.getKind()
					+"	key :" + result.getKey()
					+"	properites :"+ properies.toString());
		}
	}
}
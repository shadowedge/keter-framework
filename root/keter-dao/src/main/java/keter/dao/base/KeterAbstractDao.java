package keter.dao.base;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import keter.util.ArrayUtil;
import keter.util.ClazzUtil;
//import keter.util.ClazzUtil;

@Transactional
public abstract class KeterAbstractDao<E> implements KeterDao<E> {
	
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(KeterAbstractDao.class);

	private final Class<E> entityClass;

	@SuppressWarnings("unchecked")
	protected KeterAbstractDao() {
		this.entityClass = ClazzUtil.getSuperClassGenricType(this.getClass());
	}

	@Autowired
	private SessionFactory sessionFactory;
	
	public Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public E findById(Long id) {
		return (E) getCurrentSession().get(entityClass, id);
	}

	@Override
	public void save(E e) {
		getCurrentSession().save(e);
	}
	
	@Override
	public void saveOrUpdate(E e) {
		getCurrentSession().saveOrUpdate(e);
	}

	@Override
	public E saveEntity(E e) {
		getCurrentSession().saveOrUpdate(e);
		return e;
	}

	@Override
	public void delete(E e) {
		getCurrentSession().delete(e);
	}

	@Override
	public void deleteById(Long id) {
		// TODO 根据ID删除记录
	}

	/**
	 * <p>
	 * Method ：findAll
	 * <p>
	 * Description : 功能描述
	 * 
	 * @return
	 * @see keter.dao.base.KeterDao#findAll()
	 */
	@Override
	public List<E> findAll() {
		return getCurrentSession().createCriteria(entityClass).list();
	}
	
	protected <T> T[] wrap(T... objects) {
		 return ArrayUtil.wrap(objects);
    }
	
	
	/**
	 * Executes query with given parameters and returns a list results.
	 * Parameters are passed in order of their posiion in parameters array.
	 * 
	 * @param sqlQuery
	 *            query to execute
	 * @param parameters
	 *            parameters to pass to the query
	 * @return single result from query execution
	 */
	@SuppressWarnings("unchecked")
	protected List<E> getResultList(String sqlQuery, String[] names, Object[] parameters) {
		Query query = getCurrentSession().createQuery(sqlQuery);
		setQueryParameters(query, names, parameters);
		return (List<E>) query.list();
	}
	
	/**
	 * Executes query with given parameters and returns a single result.
	 * Parameters are passed along with given names.
	 * 
	 * @param sqlQuery
	 *            query to execute
	 * @param parameters
	 *            parameters to pass to the query
	 * @return single result from query execution
	 * 
	 */
	@SuppressWarnings("unchecked")
	protected E getSingleResult(String sqlQuery, String[] names, Object[] parameters) {
		Query query = getCurrentSession().createQuery(sqlQuery);
		query.setMaxResults(2);// avoid OOME if the list is huge
		setQueryParameters(query, names, parameters);
		List<E> result = (List<E>) query.list();
		return result.size() == 0 ? null : result.get(0);
	}

	/**
	 * Sets the parameters of a query. If names array is null, parameters are
	 * passed as positional parameters. Otherwise parameters are passed as named
	 * parameters, and position of parameter in array defines its name.
	 * 
	 * @param query
	 *            query instance
	 * @param names
	 *            names for corresponding parameters
	 * @param parameters
	 *            parameters passed to query
	 */
	protected void setQueryParameters(Query query, String[] names, Object[] parameters) {
		if (parameters != null) {
			if (names != null) {
				if (names.length != parameters.length) {
					throw new IllegalArgumentException(
							"numer of names is different from number of parameters");
				}

				for (int i = 0; i < parameters.length; i++) {
					query.setParameter(names[i], parameters[i]);
				}

			} else {
				for (int i = 0; i < parameters.length; i++) {
					query.setParameter(i, parameters[i]);
				}
			}
		}
	}
}

	// @Override
	// public List<E> findByCriteria(Criterion criterion) {
	// Criteria criteria = getCurrentSession().createCriteria(entityClass);
	// criteria.add(criterion);
	// return criteria.list();
	// }

//
// @Override
// public void remove(Entity entity) {
// // session = getSessionFactory().getCurrentSession();
// session.delete(entity);
// session.flush();//hibernate4 need
// }
//
// @SuppressWarnings("unchecked")
// @Override
// public Entity merge(Entity entity) {
// Entity mergedEntity = null;
// mergedEntity = (Entity) session.merge(entity);
// return mergedEntity;
// }
//
// @SuppressWarnings("unchecked")
// @Override
// public Entity findById(Object key) {
// Entity foundEntity = null;
// foundEntity = (Entity) session.get(entityClass, (Serializable) key);
// return foundEntity;
// }
//
// @SuppressWarnings("unchecked")
// @Override
// public void delete(Serializable... entityids) {
// for (Object id : entityids) {
// remove((Entity) session.load(this.entityClass, (Serializable) id));
// }
// }
//
// /**
// * Returns list of query results with no parameters.
// *
// * @param query
// * query to execute
// * @return list of query results
// */
// protected List<Entity> getResult(String query) {
// @SuppressWarnings("unchecked")
// List<Entity> entities = (List<Entity>) session.createQuery(query)
// .list();
// return entities;
// }
//
// /**
// * Returns list of all query results.
// *
// * @param query
// * query to execute
// * @return list of query results
// */
// @SuppressWarnings("unchecked")
// public List<Entity> findAll() {
// return session.createCriteria(entityClass).list();
// }
//

//
 
//

//
// for (int i = 0; i < parameters.length; i++) {
// query.setParameter(names[i], parameters[i]);
// }
//
// } else {
// for (int i = 0; i < parameters.length; i++) {
// query.setParameter(i, parameters[i]);
// }
// }
// }
// }
//

//
// /**
// * Returns list of query results with positional parameters.
// *
// * @param query
// * query to execute
// * @param parameters
// * parameters to pass to query
// * @return list of query results
// */
// protected List<Entity> getResultList(String query, Object... parameters) {
// return getResultList(query, null, parameters);
// }
//
// /**
// * <p>
// * Method ：getResultList
// * <p>
// * Description : 无条件分页查询
// *
// * @param hqlQuery
// * @param pageNo
// * 页号（从1开始）
// * @param pageSize
// * 每页记录数
// * @return
// * @author 顾力行-gulixing@msn.com
// */
// protected List<Entity> getResultList(String hqlQuery, Pagination p) {
// if (p.getPageNo() < 1) {
//			logger.info("[pageNo]不能小于1，系统将pageNo自动设置成1"); //$NON-NLS-1$
// p.setPageNo(1);
// }
// p.setRecordTotal(generateCntSql(hqlQuery, null, null));
// Query query = createQuery(hqlQuery, null, null);
// int start = (p.getPageNo() - 1) * p.getPageSize();
// query.setFirstResult(start);// 设置查询结果的开始记录数
// query.setMaxResults(p.getPageSize());// 设查询结果的结束记录数
// return query.list();
// }
//
// protected Query createQuery(String sqlQuery, String[] names,
// Object[] parameters) {
// Query query = session.createQuery(sqlQuery);
// setQueryParameters(query, names, parameters);
// return query;
// }
//
// private Long generateCntSql(String hsql, String[] names, Object[] parameters)
// {
// String aliaName = null;
// if (hsql.toUpperCase().indexOf("SELECT") >= 0) {// 存在SELECT和别名
// int select_end = hsql.toUpperCase().indexOf("SELECT")
// + "SELECT".length();
// int from_start = hsql.toUpperCase().indexOf("FROM");
// aliaName = hsql.substring(select_end, from_start);
// } else {
// logger.error(hsql + ":此种情况暂未处理！");
// }
// int from_end = hsql.toUpperCase().indexOf("FROM") + "FROM".length();
// String sqlBeginWithFrom = hsql.substring(from_end).trim()
// .replace("`", "");
// String tableName = sqlBeginWithFrom.substring(0,
// sqlBeginWithFrom.indexOf(" "));
// String suffix = hsql.substring(hsql.indexOf(tableName)
// + tableName.length());
// String cntHql = "SELECT count(" + aliaName + ") from " + tableName
// + " " + suffix;
// logger.info(cntHql);
// Query queryCnt = (Query) session.createQuery(cntHql);
// setQueryParameters(queryCnt, names, parameters);
// // FIXME;
// return 1L;
// // return (Long) queryCnt.getSingleResult();
// }
//
// /**
// * <p>
// * Method ：getResultList
// * <p>
// * Description : 条件分页查询
// *
// * @param hqlQuery
// * @param pageNo
// * 页号（从1开始）
// * @param pageSize
// * 每页记录数
// * @return
// * @author 顾力行-gulixing@msn.com
// */
// protected List<Entity> getResultList(String sqlQuery, String[] names,
// Object[] parameters, Pagination p) {
// if (p.getPageNo() < 1) {
//			logger.info("[pageNo]不能小于1，系统将pageNo自动设置成1"); //$NON-NLS-1$
// p.setPageNo(1);
// }
// Query query = createQuery(sqlQuery, names, parameters);
// p.setRecordTotal(generateCntSql(sqlQuery, names, parameters));
// int start = (p.getPageNo() - 1) * p.getPageSize();
// query.setFirstResult(start);// 设置查询结果的开始记录数
// query.setMaxResults(p.getPageSize());// 设查询结果的结束记录数
// return query.list();
// }
// }
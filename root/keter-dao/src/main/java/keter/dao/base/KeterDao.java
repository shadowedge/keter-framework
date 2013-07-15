package keter.dao.base;

import java.util.List;

public abstract interface KeterDao<Entity> {

	public abstract void save(Entity entity);

	public abstract Entity saveEntity(Entity entity);

	public abstract void saveOrUpdate(Entity entity);

	public abstract void delete(Entity entity);

	public abstract void deleteById(Long id);

	public abstract Entity findById(Long id);

	public abstract List<Entity> findAll();

}

// package keter.dao.base;
//
// import java.io.Serializable;
// import java.util.List;
//
// public abstract interface KeterDao<Entity> {
//
// public abstract void persist(Entity paramEntity);
//
// public abstract Entity persistEntity(Entity paramEntity);
//
// public abstract void delete(Serializable... id);
//
// public abstract Entity findById(Object paramObject);
//
// public abstract List<Entity> findAll();
//
// public abstract void remove(Entity paramEntity);
//
// public abstract Entity merge(Entity paramEntity);
//
// }
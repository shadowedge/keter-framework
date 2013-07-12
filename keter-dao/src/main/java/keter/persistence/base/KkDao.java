package keter.persistence.base;

import java.io.Serializable;
import java.util.List;

public abstract interface KkDao<Entity> {

	public abstract void persist(Entity paramEntity);

	public abstract Entity persistEntity(Entity paramEntity);

	public abstract void delete(Serializable... id);

	public abstract Entity findById(Object paramObject);

	public abstract List<Entity> findAll();

	public abstract void remove(Entity paramEntity);

	public abstract Entity merge(Entity paramEntity);

}
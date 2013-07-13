package keter.web.util;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.BeanInstantiationException;
import org.springframework.beans.BeanUtils;

/**
 * Auxiliary for mapping definition between form parameters and model
 * properties. Extremely useful when form should change only a subset of model's
 * properties.
 * 
 * This class uses concept of white listing properties to store - user should
 * provide an array of property names to be storeable (transferable to other
 * model's instance). Returning null causes to store all attributes.
 * 
 * @author Dawid Fatyga
 * 
 * @param <Model>
 *            Model class to operate on
 */
abstract public class Mapping<Model> {
	/**
	 * Model in which actual property setting will be done.
	 */
	@Valid
	private Model model;

	/**
	 * Cached property list to store.
	 */
	private Set<String> properties;

	/**
	 * Human readable way of telling that all parameters are storable.
	 * 
	 * @return null
	 */
	protected String[] all() {
		return null;
	}

	/**
	 * Return model instance. If model was not injected (set by setModel()), new
	 * model instance is created. Model class should have public default
	 * constructor.
	 * 
	 * @return model instance
	 * @throws BeanInstantiationException
	 *             when no model was set and Model class does not provide public
	 *             default constructor
	 */
	public Model getModel() throws BeanInstantiationException {
		if (model == null) {
			setModel(BeanUtils.instantiate(getModelClass()));
		}
		return model;
	}

	/**
	 * Convenient shortcut for getModel(), allows to save some keystrokes when
	 * using mapping in forms.
	 * 
	 * @return model instance
	 */
	public Model get$() {
		return getModel();
	}

	/**
	 * Simply sets the model.
	 * 
	 * @param model
	 *            model instance with initial data
	 */
	public void setModel(Model model) {
		this.model = model;
	}

	/**
	 * Should return array of property names to be storable (transferable by
	 * copyProperties()). Returning null causes all properties to be storable.
	 * 
	 * By default returns null which means this mapping instance will copy all
	 * attributes.
	 * 
	 * @return array of property names
	 */
	public String[] getAccessibleProperties() {
		return all();
	}

	/**
	 * Should return model class to operate on. Class should be taken from the
	 * bottom of the inheritance hierarchy in order to cover all possible
	 * attributes.
	 * 
	 * @return model class
	 */
	public abstract Class<? extends Model> getModelClass();

	/**
	 * Allows to transfer stored attributes inside target object. Only
	 * properties marked as storable by getAccessibleProperties() will be
	 * transfered if possible.
	 * 
	 * @param target
	 *            object to which properties will be transfered
	 */
	public void copyProperties(Model target) {
		Set<String> propertiesToStore = getPropertySet();
		PropertyDescriptor[] allModelProperties = BeanUtils
				.getPropertyDescriptors(getModelClass());

		List<String> ignoredProperties = new ArrayList<String>();
		if (propertiesToStore != null) {
			/* Model properties "not to store" are simply ignored */
			for (PropertyDescriptor property : allModelProperties) {
				String name = property.getName();
				if (!propertiesToStore.contains(name)) {
					ignoredProperties.add(name);
				}
			}
		}

		BeanUtils.copyProperties(getModel(), target,
				ignoredProperties.toArray(new String[] {}));
	}

	/**
	 * Convenient shortcut for: copy properties and return target.
	 * 
	 * @param target
	 *            object to which properties will be transfered
	 * @return target
	 */
	public <ModelLike extends Model> ModelLike populate(ModelLike target) {
		copyProperties(target);
		return target;
	}


	/**
	 * Convenient shortcut for common pattern: create mapping, set the model and
	 * return (use) mapping somewhere - this can be simplified to one line.
	 * 
	 * @param model
	 *            model to set with initial data
	 * @return this
	 */
	public Mapping<Model> withPrototype(Model model) {
		setModel(model);
		return this;
	}

	/**
	 * Creates property names set from property names array returned from
	 * getAccessibleProperties()
	 * 
	 * @return set of property names
	 */
	protected Set<String> getPropertySet() {
		if (properties == null) {
			String[] accessibleProperties = getAccessibleProperties();
			if (accessibleProperties != null) {
				properties = new HashSet<String>();
				Collections.addAll(properties, accessibleProperties);
			}
		}
		return properties;
	}

	/**
	 * Convenient shortcut for new String[]{ args }
	 * 
	 * @param args
	 *            strings to wrap in array.
	 * @return an array of strings given as parameters
	 */
	protected String[] wrap(String... args) {
		return args;
	}
}
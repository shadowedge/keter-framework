package keter.web.util;

import java.beans.PropertyEditorSupport;

import keter.persistence.base.KeterDao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.beans.SimpleTypeConverter;




/**
 * Converts Domain classes to string values (ids) and ids to Domain classes,
 * based on generic parameters.
 * 
 * @author Dawid Fatyga
 * 
 * @param <Model>
 *            model class to convert
 * @param <Dao>
 *            dao class from which model will be fetched
 */
public class DaoPropertyEditor<Model, Dao extends KeterDao<Model>> extends
PropertyEditorSupport {
	private Logger logger = LoggerFactory.getLogger(DaoPropertyEditor.class);

	private Dao dao;
	private SimpleTypeConverter typeConverter = new SimpleTypeConverter();

	/**
	 * PropertyEditor must be proviede with dao object instance.
	 * 
	 * @param dao
	 *            dao instance from entities will be fetched
	 */
	public DaoPropertyEditor(Dao dao) {
		this.dao = dao;
	}

	@Override
	public void setAsText(String text) {
		Model model = null;
		try {
			model = dao.findById(Long.parseLong(text));
		} catch (NumberFormatException nfe) {
			logger.warn("Malformed number given '" + text + "'");
		}
		setValue(model);
	}

	@Override
	public String getAsText() {
		Object obj = getValue();
		if (obj == null) {
			return null;
		}
		BeanWrapper bean = PropertyAccessorFactory.forBeanPropertyAccess(obj);
		return typeConverter.convertIfNecessary(bean.getPropertyValue("id"),
				String.class);
	}
};
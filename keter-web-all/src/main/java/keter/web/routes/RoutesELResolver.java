package keter.web.routes;

import java.beans.FeatureDescriptor;
import java.util.Iterator;

import javax.el.ELContext;
import javax.el.ELResolver;
import javax.el.PropertyNotWritableException;


/**
 * ELResolver that allows to format Routes in array-like syntax.
 * 
 * @author Dawid Fatyga
 * 
 */
public class RoutesELResolver extends ELResolver {

	@Override
	public Class<?> getCommonPropertyType(ELContext context, Object base) {
		if (base != null && base instanceof Route) {
			context.setPropertyResolved(true);
			return Object.class;
		}
		return null;
	}

	@Override
	public Iterator<FeatureDescriptor> getFeatureDescriptors(ELContext arg0,
			Object arg1) {
		return null;
	}

	@Override
	public Class<?> getType(ELContext arg0, Object arg1, Object arg2) {
		return null;
	}

	@Override
	public Object getValue(ELContext context, Object base, Object property) {
		if (context == null) {
			throw new NullPointerException();
		}

		if (base != null && base instanceof Route) {
			context.setPropertyResolved(true);
			Route route = (Route) base;
			return route.append(property);
		}
		// exception?
		return null;
	}

	@Override
	public boolean isReadOnly(ELContext arg0, Object arg1, Object arg2) {
		return true;
	}

	@Override
	public void setValue(ELContext arg0, Object arg1, Object arg2, Object arg3) {
		throw new PropertyNotWritableException();
	}
}

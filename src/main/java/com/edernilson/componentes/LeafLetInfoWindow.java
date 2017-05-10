package com.edernilson.componentes;

import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;
import javax.el.ValueExpression;
import java.util.List;
import java.util.ArrayList;
import javax.faces.component.FacesComponent;

/**
 *
 * @author eder.nilson
 */
@FacesComponent(value = LeafLetInfoWindow.COMPONENT_TYPE)
//@ResourceDependencies({
//
//})
public class LeafLetInfoWindow extends UIComponentBase {


	public static final String COMPONENT_TYPE = "com.edernilson.componentes.LeafLetInfoWindow";
	public static final String COMPONENT_FAMILY = "com.edernilson.componentes";
	private static final String OPTIMIZED_PACKAGE = "com.edernilson.componentes.";

	protected enum PropertyKeys {

		maxWidth;

		String toString;

		PropertyKeys(String toString) {
			this.toString = toString;
		}

		PropertyKeys() {}

		public String toString() {
			return ((this.toString != null) ? this.toString : super.toString());
}
	}

	public LeafLetInfoWindow() {
		setRendererType(null);
	}

	public String getFamily() {
		return COMPONENT_FAMILY;
	}

	public int getMaxWidth() {
		return (java.lang.Integer) getStateHelper().eval(PropertyKeys.maxWidth, java.lang.Integer.MIN_VALUE);
	}
	public void setMaxWidth(int _maxWidth) {
		getStateHelper().put(PropertyKeys.maxWidth, _maxWidth);
		handleAttribute("maxWidth", _maxWidth);
	}


	protected FacesContext getFacesContext() {
		return FacesContext.getCurrentInstance();
	}

	public void handleAttribute(String name, Object value) {
		List<String> setAttributes = (List<String>) this.getAttributes().get("javax.faces.component.UIComponentBase.attributesThatAreSet");
		if(setAttributes == null) {
			String cname = this.getClass().getName();
			if(cname != null && cname.startsWith(OPTIMIZED_PACKAGE)) {
				setAttributes = new ArrayList<String>(6);
				this.getAttributes().put("javax.faces.component.UIComponentBase.attributesThatAreSet", setAttributes);
			}
		}
		if(setAttributes != null) {
			if(value == null) {
				ValueExpression ve = getValueExpression(name);
				if(ve == null) {
					setAttributes.remove(name);
				} else if(!setAttributes.contains(name)) {
					setAttributes.add(name);
				}
			}
		}
	}
}
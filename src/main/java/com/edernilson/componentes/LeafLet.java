package com.edernilson.componentes;

import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;
import javax.faces.component.UINamingContainer;
import javax.el.ValueExpression;
import javax.faces.application.ResourceDependencies;
import javax.faces.application.ResourceDependency;
import java.util.List;
import java.util.ArrayList;
import com.edernilson.componentes.event.leaflet.OverlaySelectEvent;
import com.edernilson.componentes.event.leaflet.StateChangeEvent;
import com.edernilson.componentes.event.leaflet.PointSelectEvent;
import com.edernilson.componentes.event.leaflet.MarkerDragEvent;
import com.edernilson.componentes.model.leaflet.LatLngBounds;
import com.edernilson.componentes.model.leaflet.LatLng;
import com.edernilson.componentes.model.leaflet.Marker;
import java.util.Map;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponent;
import javax.faces.event.FacesEvent;
import javax.faces.event.AjaxBehaviorEvent;
import org.primefaces.util.Constants;
import org.primefaces.context.RequestContext;

/**
 *
 * @author eder.nilson
 */
@FacesComponent(value = LeafLet.COMPONENT_TYPE)
@ResourceDependencies({
    @ResourceDependency(library = "primefaces", name = "jquery/jquery.js"),
    @ResourceDependency(library = "primefaces", name = "primefaces.js"),
    @ResourceDependency(library = "leaflet", name = "css/leaflet.css"),
    @ResourceDependency(library = "leaflet", name = "leaflet.js"),
    @ResourceDependency(library = "leaflet", name = "leaflet-primefaces-extension.js")
})
public class LeafLet extends UIComponentBase implements org.primefaces.component.api.Widget, javax.faces.component.behavior.ClientBehaviorHolder {

    public static final String COMPONENT_TYPE = "com.edernilson.componentes.LeafLet";
    public static final String COMPONENT_FAMILY = "com.edernilson.componentes";
    private static final String DEFAULT_RENDERER = "com.edernilson.componentes.LeafLetRenderer";
    private static final String OPTIMIZED_PACKAGE = "com.edernilson.componentes.";

    protected enum PropertyKeys {

        widgetVar, model, style, styleClass, type, center, zoom, streetView, disableDefaultUI, navigationControl, mapTypeControl, draggable, disableDoubleClickZoom, onPointClick, fitBounds;

        String toString;

        PropertyKeys(String toString) {
            this.toString = toString;
        }

        PropertyKeys() {
        }

        public String toString() {
            return ((this.toString != null) ? this.toString : super.toString());
        }
    }

    public LeafLet() {
        setRendererType(DEFAULT_RENDERER);
    }

    public String getFamily() {
        return COMPONENT_FAMILY;
    }

    public java.lang.String getWidgetVar() {
        return (java.lang.String) getStateHelper().eval(PropertyKeys.widgetVar, null);
    }

    public void setWidgetVar(java.lang.String _widgetVar) {
        getStateHelper().put(PropertyKeys.widgetVar, _widgetVar);
        handleAttribute("widgetVar", _widgetVar);
    }

    public com.edernilson.componentes.model.leaflet.MapModel getModel() {
        return (com.edernilson.componentes.model.leaflet.MapModel) getStateHelper().eval(PropertyKeys.model, null);
    }

    public void setModel(com.edernilson.componentes.model.leaflet.MapModel _model) {
        getStateHelper().put(PropertyKeys.model, _model);
        handleAttribute("model", _model);
    }

    public java.lang.String getStyle() {
        return (java.lang.String) getStateHelper().eval(PropertyKeys.style, null);
    }

    public void setStyle(java.lang.String _style) {
        getStateHelper().put(PropertyKeys.style, _style);
        handleAttribute("style", _style);
    }

    public java.lang.String getStyleClass() {
        return (java.lang.String) getStateHelper().eval(PropertyKeys.styleClass, null);
    }

    public void setStyleClass(java.lang.String _styleClass) {
        getStateHelper().put(PropertyKeys.styleClass, _styleClass);
        handleAttribute("styleClass", _styleClass);
    }

    public java.lang.String getType() {
        return (java.lang.String) getStateHelper().eval(PropertyKeys.type, null);
    }

    public void setType(java.lang.String _type) {
        getStateHelper().put(PropertyKeys.type, _type);
        handleAttribute("type", _type);
    }

    public java.lang.String getCenter() {
        return (java.lang.String) getStateHelper().eval(PropertyKeys.center, null);
    }

    public void setCenter(java.lang.String _center) {
        getStateHelper().put(PropertyKeys.center, _center);
        handleAttribute("center", _center);
    }

    public int getZoom() {
        return (java.lang.Integer) getStateHelper().eval(PropertyKeys.zoom, 8);
    }

    public void setZoom(int _zoom) {
        getStateHelper().put(PropertyKeys.zoom, _zoom);
        handleAttribute("zoom", _zoom);
    }

    public boolean isStreetView() {
        return (java.lang.Boolean) getStateHelper().eval(PropertyKeys.streetView, false);
    }

    public void setStreetView(boolean _streetView) {
        getStateHelper().put(PropertyKeys.streetView, _streetView);
        handleAttribute("streetView", _streetView);
    }

    public boolean isDisableDefaultUI() {
        return (java.lang.Boolean) getStateHelper().eval(PropertyKeys.disableDefaultUI, false);
    }

    public void setDisableDefaultUI(boolean _disableDefaultUI) {
        getStateHelper().put(PropertyKeys.disableDefaultUI, _disableDefaultUI);
        handleAttribute("disableDefaultUI", _disableDefaultUI);
    }

    public boolean isNavigationControl() {
        return (java.lang.Boolean) getStateHelper().eval(PropertyKeys.navigationControl, true);
    }

    public void setNavigationControl(boolean _navigationControl) {
        getStateHelper().put(PropertyKeys.navigationControl, _navigationControl);
        handleAttribute("navigationControl", _navigationControl);
    }

    public boolean isMapTypeControl() {
        return (java.lang.Boolean) getStateHelper().eval(PropertyKeys.mapTypeControl, true);
    }

    public void setMapTypeControl(boolean _mapTypeControl) {
        getStateHelper().put(PropertyKeys.mapTypeControl, _mapTypeControl);
        handleAttribute("mapTypeControl", _mapTypeControl);
    }

    public boolean isDraggable() {
        return (java.lang.Boolean) getStateHelper().eval(PropertyKeys.draggable, true);
    }

    public void setDraggable(boolean _draggable) {
        getStateHelper().put(PropertyKeys.draggable, _draggable);
        handleAttribute("draggable", _draggable);
    }

    public boolean isDisableDoubleClickZoom() {
        return (java.lang.Boolean) getStateHelper().eval(PropertyKeys.disableDoubleClickZoom, false);
    }

    public void setDisableDoubleClickZoom(boolean _disableDoubleClickZoom) {
        getStateHelper().put(PropertyKeys.disableDoubleClickZoom, _disableDoubleClickZoom);
        handleAttribute("disableDoubleClickZoom", _disableDoubleClickZoom);
    }

    public java.lang.String getOnPointClick() {
        return (java.lang.String) getStateHelper().eval(PropertyKeys.onPointClick, null);
    }

    public void setOnPointClick(java.lang.String _onPointClick) {
        getStateHelper().put(PropertyKeys.onPointClick, _onPointClick);
        handleAttribute("onPointClick", _onPointClick);
    }

    public boolean isFitBounds() {
        return (java.lang.Boolean) getStateHelper().eval(PropertyKeys.fitBounds, false);
    }

    public void setFitBounds(boolean _fitBounds) {
        getStateHelper().put(PropertyKeys.fitBounds, _fitBounds);
        handleAttribute("fitBounds", _fitBounds);
    }

    private static final Collection<String> EVENT_NAMES = Collections.unmodifiableCollection(Arrays.asList("overlaySelect", "stateChange", "pointSelect", "markerDrag"));

    @Override
    public void queueEvent(FacesEvent event) {
        FacesContext context = getFacesContext();
        Map<String, String> params = context.getExternalContext().getRequestParameterMap();
        String eventName = params.get(Constants.PARTIAL_BEHAVIOR_EVENT_PARAM);
        String clientId = this.getClientId(context);

        if (isSelfRequest(context)) {

            AjaxBehaviorEvent behaviorEvent = (AjaxBehaviorEvent) event;
            FacesEvent wrapperEvent = null;

            if (eventName.equals("overlaySelect")) {
                wrapperEvent = new OverlaySelectEvent(this, behaviorEvent.getBehavior(), this.getModel().findOverlay(params.get(clientId + "_overlayId")));

                //if there is info window, update and show it
                LeafLetInfoWindow infoWindow = getInfoWindow();
                if (infoWindow != null) {
                    RequestContext.getCurrentInstance().update(infoWindow.getClientId(context));
                }
            } else if (eventName.equals("stateChange")) {
                String[] centerLoc = params.get(clientId + "_center").split(",");
                String[] northeastLoc = params.get(clientId + "_northeast").split(",");
                String[] southwestLoc = params.get(clientId + "_southwest").split(",");
                int zoomLevel = Integer.valueOf(params.get(clientId + "_zoom"));

                LatLng center = new LatLng(Double.valueOf(centerLoc[0]), Double.valueOf(centerLoc[1]));
                LatLng northeast = new LatLng(Double.valueOf(northeastLoc[0]), Double.valueOf(northeastLoc[1]));
                LatLng southwest = new LatLng(Double.valueOf(southwestLoc[0]), Double.valueOf(southwestLoc[1]));

                wrapperEvent = new StateChangeEvent(this, behaviorEvent.getBehavior(), new LatLngBounds(northeast, southwest), zoomLevel, center);
            } else if (eventName.equals("pointSelect")) {
                String[] latlng = params.get(clientId + "_pointLatLng").split(",");
                LatLng position = new LatLng(Double.valueOf(latlng[0]), Double.valueOf(latlng[1]));

                wrapperEvent = new PointSelectEvent(this, behaviorEvent.getBehavior(), position);
            } else if (eventName.equals("markerDrag")) {
                Marker marker = (Marker) this.getModel().findOverlay(params.get(clientId + "_markerId"));
                double lat = Double.valueOf(params.get(clientId + "_lat"));
                double lng = Double.valueOf(params.get(clientId + "_lng"));
                marker.setLatlng(new LatLng(lat, lng));

                wrapperEvent = new MarkerDragEvent(this, behaviorEvent.getBehavior(), marker);
            }

            wrapperEvent.setPhaseId(behaviorEvent.getPhaseId());

            super.queueEvent(wrapperEvent);
        } else {
            super.queueEvent(event);
        }
    }

    public LeafLetInfoWindow getInfoWindow() {
        for (UIComponent kid : getChildren()) {
            if (kid instanceof LeafLetInfoWindow) {
                return (LeafLetInfoWindow) kid;
            }
        }

        return null;
    }

    private boolean isSelfRequest(FacesContext context) {
        return this.getClientId(context).equals(context.getExternalContext().getRequestParameterMap().get(Constants.PARTIAL_SOURCE_PARAM));
    }

    @Override
    public Collection<String> getEventNames() {
        return EVENT_NAMES;
    }

//    protected FacesContext getFacesContext() {
//        return FacesContext.getCurrentInstance();
//    }

    public String resolveWidgetVar() {
//        FacesContext context = FacesContext.getCurrentInstance();
        FacesContext context = getFacesContext();
        String userWidgetVar = (String) getAttributes().get("widgetVar");

        if (userWidgetVar != null) {
            return userWidgetVar;
        } else {
            return "widget_" + getClientId(context).replaceAll("-|" + UINamingContainer.getSeparatorChar(context), "_");
        }
    }

    public void handleAttribute(String name, Object value) {
        List<String> setAttributes = (List<String>) this.getAttributes().get("javax.faces.component.UIComponentBase.attributesThatAreSet");
        if (setAttributes == null) {
            String cname = this.getClass().getName();
            if (cname != null && cname.startsWith(OPTIMIZED_PACKAGE)) {
                setAttributes = new ArrayList<String>(6);
                this.getAttributes().put("javax.faces.component.UIComponentBase.attributesThatAreSet", setAttributes);
            }
        }
        if (setAttributes != null) {
            if (value == null) {
                ValueExpression ve = getValueExpression(name);
                if (ve == null) {
                    setAttributes.remove(name);
                } else if (!setAttributes.contains(name)) {
                    setAttributes.add(name);
                }
            }
        }
    }
}

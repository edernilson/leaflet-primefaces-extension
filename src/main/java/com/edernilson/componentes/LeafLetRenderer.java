package com.edernilson.componentes;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.component.behavior.ClientBehavior;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import org.primefaces.component.behavior.ajax.AjaxBehavior;

import com.edernilson.componentes.model.leaflet.Circle;
import com.edernilson.componentes.model.leaflet.LatLng;
import com.edernilson.componentes.model.leaflet.MapModel;
import com.edernilson.componentes.model.leaflet.Marker;
import com.edernilson.componentes.model.leaflet.Polygon;
import com.edernilson.componentes.model.leaflet.Polyline;
import com.edernilson.componentes.model.leaflet.Rectangle;
import javax.faces.render.FacesRenderer;
import org.primefaces.renderkit.CoreRenderer;

/**
 *
 * @author eder.nilson
 */
@FacesRenderer(componentFamily = LeafLet.COMPONENT_FAMILY, rendererType = LeafLetRenderer.RENDERER_TYPE)
public class LeafLetRenderer extends CoreRenderer {

    public static final String RENDERER_TYPE = "com.edernilson.componentes.LeafLetRenderer";

    @Override
    public void decode(FacesContext context, UIComponent component) {
        decodeBehaviors(context, component);
    }

    @Override
    public void encodeEnd(FacesContext facesContext, UIComponent component) throws IOException {
        LeafLet map = (LeafLet) component;

        encodeMarkup(facesContext, map);
        encodeScript(facesContext, map);
    }

    protected void encodeMarkup(FacesContext facesContext, LeafLet map) throws IOException {
        ResponseWriter writer = facesContext.getResponseWriter();
        String clientId = map.getClientId();

        writer.startElement("div", map);
        writer.writeAttribute("id", clientId, null);
        if (map.getStyle() != null) {
            writer.writeAttribute("style", map.getStyle(), null);
        }
        if (map.getStyleClass() != null) {
            writer.writeAttribute("class", map.getStyleClass(), null);
        }

        writer.endElement("div");
    }

    protected void encodeScript(FacesContext context, LeafLet map) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        String clientId = map.getClientId();

//        System.out.println("ClientId:" + clientId);

        String widgetVar = map.resolveWidgetVar();
        LeafLetInfoWindow infoWindow = map.getInfoWindow();

        startScript(writer, clientId);

        writer.write("$(function() {");

        writer.write("PrimeFaces.cw('LeafLet','" + widgetVar + "',{");
        writer.write("id:'" + clientId + "'");

        //Required configuration
        writer.write(",mapTypeId:'" + map.getType().toUpperCase() + "'");
        writer.write(",center: new L.LatLng(" + map.getCenter() + ")");
        writer.write(",zoom:" + map.getZoom());

        if (!map.isFitBounds()) {
            writer.write(",fitBounds:false");
        }

        //Overlays
        encodeOverlays(context, map);
        //Controls
        if (map.isDisableDefaultUI()) {
            writer.write(",disableDefaultUI:true");
        }
        if (!map.isNavigationControl()) {
            writer.write(",navigationControl:false");
        }
        if (!map.isMapTypeControl()) {
            writer.write(",mapTypeControl:false");
        }
        if (map.isStreetView()) {
            writer.write(",streetViewControl:true");
        }

        //Options
        if (!map.isDraggable()) {
            writer.write(",draggable:false");
        }
        if (map.isDisableDoubleClickZoom()) {
            writer.write(",disableDoubleClickZoom:true");
        }

        //Client events
        if (map.getOnPointClick() != null) {
            writer.write(",onPointClick:function(event) {" + map.getOnPointClick() + ";}");
        }

        /*
         * Behaviors
         * - Adds hook to show info window if one defined
         * - Encodes behaviors
         */
        if (infoWindow != null) {
            Map<String, List<ClientBehavior>> behaviorEvents = map.getClientBehaviors();
            List<ClientBehavior> overlaySelectBehaviors = behaviorEvents.get("overlaySelect");
            for (ClientBehavior clientBehavior : overlaySelectBehaviors) {
                ((AjaxBehavior) clientBehavior).setOnsuccess(widgetVar + ".openWindow(data)");
            }
        }

        encodeClientBehaviors(context, map);

        writer.write("});});");

        endScript(writer);
    }

    protected void encodeOverlays(FacesContext context, LeafLet map) throws IOException {
        MapModel model = map.getModel();
        ResponseWriter writer = context.getResponseWriter();

        //Overlays
        if (model != null) {
            if (!model.getMarkers().isEmpty()) {
                encodeMarkers(context, map);
            }
            if (!model.getPolylines().isEmpty()) {
                encodePolylines(context, map);
            }
            if (!model.getPolygons().isEmpty()) {
                encodePolygons(context, map);
            }

            if (!model.getCircles().isEmpty()) {
                encodeCircles(context, map);
            }
            if (!model.getRectangles().isEmpty()) {
                encodeRectangles(context, map);
            }
        }

        LeafLetInfoWindow infoWindow = map.getInfoWindow();

        if (infoWindow != null) {
            writer.write(",infoWindow: L.popup({");
            writer.write("id:'" + infoWindow.getClientId(context) + "'");
            if (infoWindow.getMaxWidth() != java.lang.Integer.MIN_VALUE)
                writer.write(", maxWidth:'" + infoWindow.getMaxWidth() + "'");
            writer.write("})");
        }
    }

    protected void encodeMarkers(FacesContext context, LeafLet map) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        MapModel model = map.getModel();

        writer.write(",markers:[");

        for (Iterator<Marker> iterator = model.getMarkers().iterator(); iterator.hasNext();) {
            Marker marker = (Marker) iterator.next();
            encodeMarker(context, marker);

            if (iterator.hasNext()) {
                writer.write(",");
            }
        }
        writer.write("]");
    }

    protected void encodeMarker(FacesContext context, Marker marker) throws IOException {
        ResponseWriter writer = context.getResponseWriter();

        writer.write("L.marker(");
        writer.write("new L.LatLng(" + marker.getLatlng().getLat() + ", " + marker.getLatlng().getLng() + "), {");

        writer.write("id:'" + marker.getId() + "'");
        if (marker.getTitle() != null) {
            writer.write(",title:\"" + escapeText(marker.getTitle()) + "\"");
        }
        String shadow = "";
        if (marker.getShadow() != null) {
            writer.write(",shadowUrl:'" + marker.getShadow() + "'");
        }

        if (marker.getIcon() != null) {
            writer.write(",icon: L.icon({iconUrl:'" + marker.getIcon() + "'" + shadow + ", popupAnchor: new L.point(15, 5)})");
//            writer.write(",icon: L.icon({iconUrl:'" + marker.getIcon() + "'" + shadow + "})");
        }

        if (marker.getCursor() != null) {
            writer.write(",cursor:'" + marker.getCursor() + "'");
        }
        if (marker.isDraggable()) {
            writer.write(",draggable: true");
        }
        if (!marker.isVisible()) {
            writer.write(",visible: false");
        }
        if (marker.isFlat()) {
            writer.write(",flat: true");
        }
        if (marker.getZindex() > Integer.MIN_VALUE) {
            writer.write(",zIndexOffset:" + marker.getZindex());
        }

        writer.write("})");
    }

    protected void encodePolylines(FacesContext context, LeafLet map) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        MapModel model = map.getModel();

        writer.write(",polylines:[");

        for (Iterator<Polyline> lines = model.getPolylines().iterator(); lines.hasNext();) {
            Polyline polyline = (Polyline) lines.next();

            writer.write("new L.polyline(");
            encodePaths(context, polyline.getPaths());

            writer.write(", {");
            writer.write("id:'" + polyline.getId() + "'");

            writer.write(",opacity:" + polyline.getStrokeOpacity());
            writer.write(",weight:" + polyline.getStrokeWeight());

            if (polyline.getStrokeColor() != null) {
                writer.write(",color:'" + polyline.getStrokeColor() + "'");
            }
            if (polyline.getZindex() > Integer.MIN_VALUE) {
                writer.write(",zIndex:" + polyline.getZindex());
            }

            writer.write("})");

            if (lines.hasNext()) {
                writer.write(",");
            }
        }

        writer.write("]");
    }

    protected void encodePolygons(FacesContext context, LeafLet map) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        MapModel model = map.getModel();

        writer.write(",polygons:[");

        for (Iterator<Polygon> polygons = model.getPolygons().iterator(); polygons.hasNext();) {
            Polygon polygon = (Polygon) polygons.next();

            writer.write("new L.polygon(");
            encodePaths(context, polygon.getPaths());

            writer.write(", {");
            writer.write("id:'" + polygon.getId() + "'");

//            writer.write(",strokeOpacity:" + polygon.getStrokeOpacity());
            writer.write(",opacity:" + polygon.getStrokeOpacity());
//            writer.write(",strokeWeight:" + polygon.getStrokeWeight());
            writer.write(",weight:" + polygon.getStrokeWeight());
            writer.write(",fillOpacity:" + polygon.getFillOpacity());

            if (polygon.getStrokeColor() != null) {
//                writer.write(",strokeColor:'" + polygon.getStrokeColor() + "'");
                writer.write(",color:'" + polygon.getStrokeColor() + "'");
            }
            if (polygon.getFillColor() != null) {
                writer.write(",fillColor:'" + polygon.getFillColor() + "'");
            }
            if (polygon.getZindex() > Integer.MIN_VALUE) {
                writer.write(",zIndex:" + polygon.getZindex());
            }

            writer.write("})");

            if (polygons.hasNext()) {
                writer.write(",");
            }
        }

        writer.write("]");
    }

    protected void encodeCircles(FacesContext context, LeafLet map) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        MapModel model = map.getModel();

        writer.write(",circles:[");

        for (Iterator<Circle> circles = model.getCircles().iterator(); circles.hasNext();) {
            Circle circle = (Circle) circles.next();

            writer.write("new L.circle(");
            writer.write("new L.LatLng(" + circle.getCenter().getLat() + ", " + circle.getCenter().getLng() + ")");

            writer.write(", {");
            writer.write("id:'" + circle.getId() + "'");

            writer.write(",radius:" + circle.getRadius());

            writer.write(",opacity:" + circle.getStrokeOpacity());
            writer.write(",weight:" + circle.getStrokeWeight());
            writer.write(",fillOpacity:" + circle.getFillOpacity());

            if (circle.getStrokeColor() != null) {
                writer.write(",color:'" + circle.getStrokeColor() + "'");
            }
            if (circle.getFillColor() != null) {
                writer.write(",fillColor:'" + circle.getFillColor() + "'");
            }
            if (circle.getZindex() > Integer.MIN_VALUE) {
                writer.write(",zIndex:" + circle.getZindex());
            }

            writer.write("})");

            if (circles.hasNext()) {
                writer.write(",");
            }
        }

        writer.write("]");
    }

    protected void encodeRectangles(FacesContext context, LeafLet map) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        MapModel model = map.getModel();

        writer.write(",rectangles:[");

        for (Iterator<Rectangle> rectangles = model.getRectangles().iterator(); rectangles.hasNext();) {
            Rectangle rectangle = (Rectangle) rectangles.next();

            writer.write("new L.rectangle(");

            LatLng ne = rectangle.getBounds().getNorthEast();
            LatLng sw = rectangle.getBounds().getSouthWest();

            writer.write("new L.latLngBounds(new L.LatLng(" + ne.getLat() + "," + ne.getLng() + "), new L.LatLng(" + sw.getLat() + "," + sw.getLng() + "))");

            writer.write(", {");
            writer.write("id:'" + rectangle.getId() + "'");

            writer.write(",opacity:" + rectangle.getStrokeOpacity());
            writer.write(",weight:" + rectangle.getStrokeWeight());
            writer.write(",fillOpacity:" + rectangle.getFillOpacity());

            if (rectangle.getStrokeColor() != null) {
                writer.write(",color:'" + rectangle.getStrokeColor() + "'");
            }
            if (rectangle.getFillColor() != null) {
                writer.write(",fillColor:'" + rectangle.getFillColor() + "'");
            }
            if (rectangle.getZindex() > Integer.MIN_VALUE) {
                writer.write(",zIndex:" + rectangle.getZindex());
            }

            writer.write("})");

            if (rectangles.hasNext()) {
                writer.write(",");
            }
        }

        writer.write("]");
    }

    protected void encodePaths(FacesContext context, List<LatLng> paths) throws IOException {
        ResponseWriter writer = context.getResponseWriter();

        writer.write("[");
        for (Iterator<LatLng> coords = paths.iterator(); coords.hasNext();) {
            LatLng coord = coords.next();

            writer.write("new L.LatLng(" + coord.getLat() + ", " + coord.getLng() + ")");

            if (coords.hasNext()) {
                writer.write(",");
            }

        }
        writer.write("]");
    }

    @Override
    public void encodeChildren(FacesContext context, UIComponent component) throws IOException {
        //Do Nothing
    }

    @Override
    public boolean getRendersChildren() {
        return true;
    }
}

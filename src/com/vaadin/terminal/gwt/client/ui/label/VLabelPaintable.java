/*
@VaadinApache2LicenseForJavaFiles@
 */
package com.vaadin.terminal.gwt.client.ui.label;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.PreElement;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.terminal.gwt.client.ApplicationConnection;
import com.vaadin.terminal.gwt.client.UIDL;
import com.vaadin.terminal.gwt.client.Util;
import com.vaadin.terminal.gwt.client.ui.VAbstractPaintableWidget;

public class VLabelPaintable extends VAbstractPaintableWidget {
    public VLabelPaintable() {
    }

    public void updateFromUIDL(UIDL uidl, ApplicationConnection client) {
        if (client.updateComponent(this, uidl, true)) {
            return;
        }

        boolean sinkOnloads = false;

        final String mode = uidl.getStringAttribute("mode");
        if (mode == null || "text".equals(mode)) {
            getWidgetForPaintable().setText(uidl.getChildString(0));
        } else if ("pre".equals(mode)) {
            PreElement preElement = Document.get().createPreElement();
            preElement.setInnerText(uidl.getChildUIDL(0).getChildString(0));
            // clear existing content
            getWidgetForPaintable().setHTML("");
            // add preformatted text to dom
            getWidgetForPaintable().getElement().appendChild(preElement);
        } else if ("uidl".equals(mode)) {
            getWidgetForPaintable().setHTML(uidl.getChildrenAsXML());
        } else if ("xhtml".equals(mode)) {
            UIDL content = uidl.getChildUIDL(0).getChildUIDL(0);
            if (content.getChildCount() > 0) {
                getWidgetForPaintable().setHTML(content.getChildString(0));
            } else {
                getWidgetForPaintable().setHTML("");
            }
            sinkOnloads = true;
        } else if ("xml".equals(mode)) {
            getWidgetForPaintable().setHTML(
                    uidl.getChildUIDL(0).getChildString(0));
        } else if ("raw".equals(mode)) {
            getWidgetForPaintable().setHTML(
                    uidl.getChildUIDL(0).getChildString(0));
            sinkOnloads = true;
        } else {
            getWidgetForPaintable().setText("");
        }
        if (sinkOnloads) {
            Util.sinkOnloadForImages(getWidgetForPaintable().getElement());
        }
    }

    @Override
    protected Widget createWidget() {
        return GWT.create(VLabel.class);
    }

    @Override
    public VLabel getWidgetForPaintable() {
        return (VLabel) super.getWidgetForPaintable();
    }

}

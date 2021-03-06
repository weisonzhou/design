/*
 * Copyright(c) 2001-2010, FineReport Inc, All Rights Reserved.
 */
package com.fr.design.designer.creator;

import java.awt.Color;
import java.awt.Dimension;
import java.beans.IntrospectionException;

import javax.swing.BorderFactory;
import javax.swing.border.Border;

import com.fr.design.mainframe.widget.editors.InChangeBooleanEditor;
import com.fr.form.ui.FieldEditor;
import com.fr.design.form.util.XCreatorConstants;
import com.fr.form.ui.TextEditor;
import com.fr.form.ui.reg.RegExp;
import com.fr.general.Inter;
import com.fr.stable.ArrayUtils;
import com.fr.stable.StringUtils;

/**
 * @author richer
 * @since 6.5.3
 */
public abstract class XFieldEditor extends XWidgetCreator {

    protected static final Border FIELDBORDER = BorderFactory.createLineBorder(new Color(128, 152, 186), 1);

    public XFieldEditor(FieldEditor widget, Dimension initSize) {
        super(widget, initSize);
    }

    @Override
    public CRPropertyDescriptor[] supportedDescriptor() throws IntrospectionException {
        return (CRPropertyDescriptor[]) ArrayUtils.addAll(
                super.supportedDescriptor(), getCRPropertyDescriptor()
        );
    }

    private CRPropertyDescriptor[] getCRPropertyDescriptor() throws IntrospectionException {
        CRPropertyDescriptor allowBlank = new CRPropertyDescriptor("allowBlank", this.data.getClass()).setI18NName(
                Inter.getLocText("FR-Designer_Allow-Blank")).setEditorClass(InChangeBooleanEditor.class).putKeyValue(
                XCreatorConstants.PROPERTY_VALIDATE, "FR-Designer_Validate");
        CRPropertyDescriptor blankErrorMsg = new CRPropertyDescriptor("errorMessage", this.data.getClass()).setI18NName(
                Inter.getLocText("FR-Engine_Verify-Message"))
                .putKeyValue(XCreatorConstants.PROPERTY_VALIDATE, "FR-Designer_Validate");
        CRPropertyDescriptor fontSize = new CRPropertyDescriptor("fontSize", this.data.getClass(), "getFontSize", "setFontSize")
                .setI18NName(Inter.getLocText("FR-Designer_Font-Size"))
                .putKeyValue(XCreatorConstants.PROPERTY_CATEGORY, "FR-Designer_Advanced");
        return !((FieldEditor) toData()).isAllowBlank() ?
                new CRPropertyDescriptor[]{allowBlank, blankErrorMsg, fontSize}
                : new CRPropertyDescriptor[]{allowBlank, fontSize};
    }

    public boolean isDisplayRegField(boolean displayRegField) {
        RegExp reg = ((TextEditor) toData()).getRegex();
        if (reg == null || !StringUtils.isNotEmpty(reg.toRegText())) {

            displayRegField = false;
        }
        return displayRegField;
    }
}
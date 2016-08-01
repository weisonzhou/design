/*
 * Copyright(c) 2001-2010, FineReport Inc, All Rights Reserved.
 */
package com.fr.design.designer.creator;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.beans.IntrospectionException;

import javax.swing.JComponent;

import com.fr.base.BaseUtils;
import com.fr.base.ScreenResolution;
import com.fr.base.Style;
import com.fr.design.form.util.XCreatorConstants;
import com.fr.design.mainframe.widget.editors.RegexEditor;
import com.fr.design.mainframe.widget.editors.WidgetValueEditor;
import com.fr.design.mainframe.widget.renderer.RegexCellRencerer;
import com.fr.form.ui.TextEditor;
import com.fr.form.ui.reg.NoneReg;
import com.fr.form.ui.reg.RegExp;
import com.fr.general.FRFont;
import com.fr.general.Inter;
import com.fr.stable.ArrayUtils;
import com.fr.stable.Constants;
import com.fr.stable.StringUtils;
import jdk.nashorn.internal.runtime.regexp.joni.Regex;

/**
 * @author richer
 * @since 6.5.3
 */
public class XTextEditor extends XWrapperedFieldEditor {

    public XTextEditor(TextEditor widget, Dimension initSize) {
        super(widget, initSize);
    }

    /**
     * 控件的属性列表
     *
     * @return 此控件所用的属性列表
     * @throws IntrospectionException 异常
     */
    @Override
    public CRPropertyDescriptor[] supportedDescriptor() throws IntrospectionException {
        CRPropertyDescriptor widgetValue = new CRPropertyDescriptor("widgetValue", this.data.getClass()).setI18NName(
                Inter.getLocText(new String[]{"FR-Designer_Widget", "Value"})).setEditorClass(
                WidgetValueEditor.class).putKeyValue(XCreatorConstants.PROPERTY_CATEGORY, "Advanced");
        CRPropertyDescriptor regex = new CRPropertyDescriptor("regex", this.data.getClass()).setI18NName(
                Inter.getLocText("FR-Designer_Input_Rule")).setEditorClass(RegexEditor.class).putKeyValue(
                "renderer", RegexCellRencerer.class).putKeyValue(XCreatorConstants.PROPERTY_VALIDATE, "FR-Designer_Validate");
        CRPropertyDescriptor regErrorMessage = new CRPropertyDescriptor("regErrorMessage", this.data.getClass()).setI18NName(
                Inter.getLocText("Verify-Message")).putKeyValue(XCreatorConstants.PROPERTY_VALIDATE, "FR-Designer_Validate");
        CRPropertyDescriptor waterMark = new CRPropertyDescriptor("waterMark", this.data.getClass()).setI18NName(
                Inter.getLocText("FR-Designer_WaterMark")).putKeyValue(XCreatorConstants.PROPERTY_CATEGORY,
                "Advanced");
        CRPropertyDescriptor[] sup = (CRPropertyDescriptor[]) ArrayUtils.addAll(new CRPropertyDescriptor[]{widgetValue}, super.supportedDescriptor());
        Boolean displayRegField = true;
        RegExp reg = ((TextEditor) toData()).getRegex();
        if (reg == null || !StringUtils.isNotEmpty(reg.toRegText())) {

            displayRegField = false;
        }
        return displayRegField ? (CRPropertyDescriptor[]) ArrayUtils.addAll(sup,
                new CRPropertyDescriptor[]{regex, regErrorMessage, waterMark}) :
                (CRPropertyDescriptor[]) ArrayUtils.addAll(sup, new CRPropertyDescriptor[]{regex, waterMark});
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        TextEditor area = (TextEditor) data;
        if (area.getWidgetValue() != null) {
            Graphics2D g2d = (Graphics2D) g.create();
            BaseUtils.drawStringStyleInRotation(g2d, this.getWidth(), this.getHeight(), area.getWidgetValue()
                    .toString(), Style.getInstance(FRFont.getInstance()).deriveHorizontalAlignment(Constants.LEFT)
                    .deriveTextStyle(Style.TEXTSTYLE_SINGLELINE), ScreenResolution.getScreenResolution());
        }
    }

    @Override
    protected JComponent initEditor() {
        setBorder(FIELDBORDER);
        return this;
    }

    @Override
    protected String getIconName() {
        return "text_field_16.png";
    }

}
package com.fr.design.designer.creator;import com.fr.design.form.util.XCreatorConstants;import com.fr.form.ui.WriteAbleRepeatEditor;import com.fr.general.Inter;import com.fr.stable.ArrayUtils;import java.awt.*;import java.beans.IntrospectionException;/** * Author : Shockway * Date: 13-9-22 * Time: 上午10:40 */public abstract class XCustomWriteAbleRepeatEditor extends XWriteAbleRepeatEditor {	public XCustomWriteAbleRepeatEditor(WriteAbleRepeatEditor widget, Dimension initSize) {		super(widget, initSize);	}	public CRPropertyDescriptor[] supportedDescriptor() throws IntrospectionException {		return (CRPropertyDescriptor[]) ArrayUtils.addAll(super.supportedDescriptor(),				new CRPropertyDescriptor[] { new CRPropertyDescriptor("customData",						this.data.getClass()).setI18NName(Inter.getLocText("Form-Allow_CustomData"))						.putKeyValue(XCreatorConstants.PROPERTY_VALIDATE, "FR-Designer_Validate")});	}}
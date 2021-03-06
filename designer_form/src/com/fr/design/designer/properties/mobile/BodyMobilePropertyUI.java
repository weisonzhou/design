package com.fr.design.designer.properties.mobile;

import com.fr.design.designer.creator.XCreator;
import com.fr.design.designer.creator.XWAbsoluteBodyLayout;
import com.fr.design.designer.creator.XWFitLayout;
import com.fr.design.fun.impl.AbstractWidgetPropertyUIProvider;
import com.fr.design.gui.itable.AbstractPropertyTable;
import com.fr.general.Inter;

/**
 * Created by Administrator on 2016/5/16/0016.
 */
public class BodyMobilePropertyUI extends AbstractWidgetPropertyUIProvider {

    private XCreator xCreator;

    public BodyMobilePropertyUI(XWFitLayout xwFitLayout) {
        this.xCreator = xwFitLayout;
    }

    public BodyMobilePropertyUI(XWAbsoluteBodyLayout xwAbsoluteBodyLayout) {
        this.xCreator = xwAbsoluteBodyLayout;
    }

    @Override
    public AbstractPropertyTable createWidgetAttrTable() {
        return new BodyAppRelayoutTable(xCreator);
    }

    @Override
    public String tableTitle() {
        return Inter.getLocText("FR-Designer_Mobile-Attr");
    }
}

package com.fr.design.mainframe.templateinfo;

import com.fr.base.parameter.ParameterUI;
import com.fr.main.impl.WorkBook;
import com.fr.report.cellcase.CellCase;
import com.fr.report.poly.PolyWorkSheet;
import com.fr.report.worksheet.WorkSheet;

import java.util.Iterator;

/**
 * Created by plough on 2017/3/17.
 */
public class JWorkBookProcessInfo extends TemplateProcessInfo<WorkBook> {

    public JWorkBookProcessInfo(WorkBook wb) {
        super(wb);
    }

    // 获取模板类型
    public int getReportType() {
        return template.isElementCaseBook() ? 0 : 1;
    }

    // 获取模板格子数
    public int getCellCount() {
        int cellCount = 0;
        if (template.isElementCaseBook()) {  // 如果是普通报表
            for (int i = 0; i < template.getReportCount(); i++) {
                WorkSheet r = (WorkSheet) template.getReport(i);
                CellCase cc = r.getBlock().getCellCase();
                for (int j = 0; j < cc.getRowCount(); j++) {
                    Iterator iter = cc.getRow(j);
                    while (iter.hasNext()) {
                        cellCount ++;
                        iter.next();
                    }
                }
            }
        }
        return cellCount;
    }
    // 获取模板悬浮元素个数
    public int getFloatCount() {
        int chartCount = 0;
        if (template.isElementCaseBook()) {  // 如果是普通报表
            for (int i = 0; i < template.getReportCount(); i++) {
                WorkSheet r = (WorkSheet) template.getReport(i);
                Iterator fiter = r.getBlock().floatIterator();
                while (fiter.hasNext()) {
                    chartCount ++;
                    fiter.next();
                }
            }
        }
        return chartCount;
    }
    // 获取模板聚合块个数
    public int getBlockCount() {
        int blockCount = 0;
        if (!template.isElementCaseBook()) {  // 如果是聚合报表
            for (int i = 0; i < template.getReportCount(); i++) {
                PolyWorkSheet r = (PolyWorkSheet) template.getReport(i);
                blockCount += r.getBlockCount();
            }
        }
        return blockCount;
    }
    // 获取模板控件数
    public int getWidgetCount() {
        ParameterUI pui = template.getReportParameterAttr().getParameterUI();
        return pui == null ? 0 : (pui.getAllWidgets().length - 1);
    }
}

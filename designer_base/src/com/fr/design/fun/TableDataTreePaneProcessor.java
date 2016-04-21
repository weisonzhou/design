package com.fr.design.fun;

import com.fr.design.data.datapane.TableDataTreePane;
import com.fr.stable.fun.Level;

/**
 * 自定义的模板（服务器）数据集的树样式接口  el:分组样式
 * Coder: zack
 * Date: 2016/4/18
 * Time: 9:04
 */
public interface TableDataTreePaneProcessor extends Level {
    String XML_TAG = "TableDataTreePaneProcessor";

    int CURRENT_LEVEL = 1;
    /**
     * 创建数据集面板
     * @return 数据集面板
     */
    TableDataTreePane createTableDataTreePane();

}

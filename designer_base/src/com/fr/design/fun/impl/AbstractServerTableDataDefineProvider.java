package com.fr.design.fun.impl;

import com.fr.design.data.datapane.TableDataNameObjectCreator;
import com.fr.design.data.tabledata.wrapper.TableDataFactory;
import com.fr.design.fun.ServerTableDataDefineProvider;
import com.fr.stable.fun.impl.AbstractProvider;
import com.fr.stable.fun.mark.API;

/**
 * @author richie
 * @date 2015-05-13
 * @since 8.0
 */
@API(level = ServerTableDataDefineProvider.CURRENT_LEVEL)
public abstract class AbstractServerTableDataDefineProvider extends AbstractProvider implements ServerTableDataDefineProvider {

    @Override
    public int currentAPILevel() {
        return CURRENT_LEVEL;
    }

    @Override
    public String mark4Provider() {
        return getClass().getName();
    }

    @Override
    public void process() {
        TableDataNameObjectCreator creator = new TableDataNameObjectCreator(
                nameForTableData(),
                prefixForTableData(),
                iconPathForTableData(),
                classForTableData(),
                classForInitTableData(),
                appearanceForTableData()
        );
        TableDataFactory.register(classForTableData(), creator);
    }
}
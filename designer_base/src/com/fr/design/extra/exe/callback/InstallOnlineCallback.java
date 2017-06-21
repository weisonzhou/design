package com.fr.design.extra.exe.callback;

import com.fr.design.extra.PluginOperateUtils;
import com.fr.general.FRLogger;
import com.fr.general.Inter;
import com.fr.plugin.context.PluginMarker;
import com.fr.plugin.error.PluginErrorCode;
import com.fr.plugin.manage.control.PluginTask;
import com.fr.plugin.manage.control.PluginTaskResult;

import javax.swing.*;
import java.util.List;

/**
 * Created by ibm on 2017/5/26.
 */
public class InstallOnlineCallback extends AbstractPluginTaskCallback {
    protected JSCallback jsCallback;
    private static int HUNDRED_PERCENT = 100;

    public InstallOnlineCallback(PluginMarker pluginMarker, JSCallback jsCallback){
        this.pluginMarker = pluginMarker;
        this.jsCallback = jsCallback;
    }

    @Override
    public void updateProgress(String description, double aProgress) {
        jsCallback.execute(String.valueOf(aProgress * HUNDRED_PERCENT + "%"));
    }


    @Override
    public void done(PluginTaskResult result) {
        if (result.isSuccess()) {
            jsCallback.execute("success");
            FRLogger.getLogger().info(Inter.getLocText("FR-Designer-Plugin_Install_Success"));
            JOptionPane.showMessageDialog(null, Inter.getLocText("FR-Designer-Plugin_Install_Success"));
        } else if (result.errorCode() == PluginErrorCode.NeedDealWithPluginDependency) {
            int rv = JOptionPane.showOptionDialog(
                    null,
                    Inter.getLocText(Inter.getLocText("FR-Designer-Plugin_Install_Dependence")),
                    Inter.getLocText("FR-Designer-Plugin_Warning"),
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    null,
                    null
            );
            if (rv == JOptionPane.CANCEL_OPTION || rv == JOptionPane.CLOSED_OPTION) {
                return;
            }
            List<PluginTask> pluginTasks = result.getPreTasks();
            for(PluginTask pluginTask : pluginTasks){
                PluginMarker marker = pluginTask.getMarker();
                PluginOperateUtils.installPluginDependence(marker, jsCallback );
            }
            //执行JS回调
            PluginOperateUtils.installPluginOnline(pluginMarker, jsCallback);
        } else if(result.errorCode() == PluginErrorCode.HasLowerPluginWhenInstall){
            int rv = JOptionPane.showOptionDialog(
                    null,
                    Inter.getLocText("FR-Designer-Plugin_Has_Install_Lower"),
                    Inter.getLocText("FR-Designer-Plugin_Warning"),
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    null,
                    null
            );
            if (rv == JOptionPane.CANCEL_OPTION || rv == JOptionPane.CLOSED_OPTION) {
                return;
            }
            PluginOperateUtils.updatePluginOnline(pluginMarker, jsCallback);
        }else {
            jsCallback.execute("failed");
            FRLogger.getLogger().info(Inter.getLocText("FR-Designer-Plugin_Install_Failed"));
            JOptionPane.showMessageDialog(null, result.getMessage(), Inter.getLocText("FR-Designer-Plugin_Warning"), JOptionPane.ERROR_MESSAGE);
        }
    }
}
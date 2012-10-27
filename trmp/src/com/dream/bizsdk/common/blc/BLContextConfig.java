/**
 *all rights reserved,@copyright 2003
 */
package com.dream.bizsdk.common.blc;

import java.io.File;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-9-27
 * Time: 21:23:44
 */
public class BLContextConfig {
    protected File contextConfig;
    protected File taskConfig;
    protected File log4jConfig;
    protected File eventConfig;

    public BLContextConfig() {

    }

    /**
     * @param contextConfig
     * @param taskConfig
     * @param eventConfig
     */
    public BLContextConfig(File contextConfig, File taskConfig, File eventConfig) {
        this.contextConfig = contextConfig;
        this.taskConfig = taskConfig;
        this.log4jConfig = eventConfig;
    }

    /**
     * @param contextConfig
     */
    public void setContextConfig(File contextConfig) {
        this.contextConfig = contextConfig;
    }

    /**
     * @return
     */
    public File getContextConfig() {
        return contextConfig;
    }

    /**
     * @param taskConfig
     */
    public void setTaskConfig(File taskConfig) {
        this.taskConfig = taskConfig;
    }

    /**
     * @return
     */
    public File getTaskConfig() {
        return taskConfig;
    }

    /**
     * @param log4jConfig
     */
    public void setLog4jConfig(File log4jConfig) {
        this.log4jConfig = log4jConfig;
    }

    /**
     * @return
     */
    public File getLog4jConfig() {
        return log4jConfig;
    }

    /**
     * @return
     */
    public File getEventConfig() {
        return eventConfig;
    }

    /**
     * @param eventConfig
     */
    public void setEventConfig(File eventConfig) {
        this.eventConfig = eventConfig;
    }
}

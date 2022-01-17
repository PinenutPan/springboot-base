package com.sinosoft.cms.base.enums;

public enum DataSourceType {
    /**
     * 主库
     */
    MASTER,

    /**
     * 从库
     */
    SLAVE,

    /**
     * shardingSphereDataSource
     */
    SHARDING_SPHERE
}

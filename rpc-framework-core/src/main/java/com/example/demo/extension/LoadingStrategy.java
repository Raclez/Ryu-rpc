package com.example.demo.extension;

/**
 * @author RL475
 * 加载策略
 */

public interface LoadingStrategy extends Prioritize{
    /**
     * @return String
     */
    String directory();
    default String[] includedPackages() {
        // default match all
        return null;
    }

    default String getName() {
        return this.getClass().getSimpleName();
    }


    /**
     * @return boolean
     * 是否重写
     */
    default boolean overridden() {
        return false;
    }
}

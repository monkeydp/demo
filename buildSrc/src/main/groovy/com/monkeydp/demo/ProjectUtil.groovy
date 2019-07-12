package com.monkeydp.demo

import org.gradle.api.Project

import static com.monkeydp.demo.Symbol.DOT

/**
 * @author iPotato* @date 2019/7/12
 */
class ProjectUtil {

    /**
     * 构建组
     * @param project
     * @return
     */
    static String buildGroup(Project project) {
        def parent = project.parent
        if (null == parent) {
            return project.group
        }
        return parent.group + DOT + parent.name
    }
}

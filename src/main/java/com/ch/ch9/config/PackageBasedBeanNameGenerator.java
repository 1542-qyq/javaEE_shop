package com.ch.ch9.config;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.util.StringUtils;

public class PackageBasedBeanNameGenerator extends AnnotationBeanNameGenerator {

    @Override
    protected String buildDefaultBeanName(BeanDefinition definition) {
        String className = definition.getBeanClassName();

        // 处理mapper
        if (className != null) {
            if (className.contains("mapper.admin")) {
                return "admin" + getSimpleName(className);
            } else if (className.contains("mapper.before")) {
                return "before" + getSimpleName(className);
            } else if (className.contains("controller.admin")) {
                return "admin" + getSimpleName(className);
            } else if (className.contains("controller.before")) {
                return "before" + getSimpleName(className);
            } else if (className.contains("service.admin")) {
                return "admin" + getSimpleName(className);
            } else if (className.contains("service.before")) {
                return "before" + getSimpleName(className);
            }
        }

        return super.buildDefaultBeanName(definition);
    }

    private String getSimpleName(String className) {
        String simpleName = className.substring(className.lastIndexOf('.') + 1);
        // 首字母小写
        return StringUtils.uncapitalize(simpleName);
    }
}
package net.watcher.domain.monitoring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * BeanCreationMonitorPostProcessor functionality
 * monitoring for created beans
 *
 * @author Kostia
 *
 */
@Component
public class BeanCreationMonitorPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("Target bean is: " + beanName);
        return bean;
    }
}

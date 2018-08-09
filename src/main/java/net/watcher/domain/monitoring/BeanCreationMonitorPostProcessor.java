package net.watcher.domain.monitoring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(BeanCreationMonitorPostProcessor.class);
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        LOGGER.info("Target bean is: " + beanName);
        return bean;
    }
}

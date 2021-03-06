package com.u2ware.springfield.config.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;

import com.u2ware.springfield.config.ModulesConfig;

/**
 * 
 * @deprecated
 *
 */
public class BaseMessageDefinition {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected void registerBeanDefinition(BeanDefinitionRegistry registry, String beanName, BeanDefinition beanDefinition){
		registry.registerBeanDefinition(beanName, beanDefinition);
		logger.debug(beanName+" = "+beanDefinition.getBeanClassName());
	}
	
	protected void registerBeanDefinition(BeanDefinitionRegistry registry, String beanName){
		logger.debug(beanName+" = <<bean name used in registry >>");
	}

	public void addSpringfieldMessageSourceBasenames(BeanDefinitionRegistry registry, ModulesConfig modulesConfig) {
		String beanName = "springfieldMessageSourceBasenames";
		String[] packagesToScan = new String[]{modulesConfig.getBasePackage()};

		if(registry.isBeanNameInUse(beanName)) {
			BeanDefinition savedBean = registry.getBeanDefinition(beanName);
			String[] savedValue = (String[])savedBean.getPropertyValues().getPropertyValue("packagesToScan").getValue();
			if(savedValue != null){
				String[] newValue = new String[savedValue.length+packagesToScan.length];
				for(int i = 0; i < savedValue.length; i++){
					newValue[i] = savedValue[i];
				}
				for(int i = 0; i < packagesToScan.length; i++){
					newValue[i + packagesToScan.length] = packagesToScan[i];
				}
				packagesToScan = newValue;
			}
			registry.removeBeanDefinition(beanName);
		}
		
		BeanDefinition beanDefinition = BeanDefinitionBuilder
				.rootBeanDefinition("com.u2ware.springfield.support.resource.ResourcePatternResolverBean")
				.addPropertyValue("packagesToScan", packagesToScan)
				.addPropertyValue("resourcePatterns", new String[]{"/**/messages.xml"})
				.getRawBeanDefinition();
				
		this.registerBeanDefinition(registry, beanName, beanDefinition);
	}
	
	public void addSpringfieldMessageSource(BeanDefinitionRegistry registry, ModulesConfig modulesConfig) {
		String beanName = "springfieldMessageSource";
		if(registry.isBeanNameInUse(beanName)) {registerBeanDefinition(registry, beanName); return;}

		BeanDefinition beanDefinition = BeanDefinitionBuilder
				.rootBeanDefinition("org.springframework.context.support.ReloadableResourceBundleMessageSource")
				.addPropertyValue("basenames", "#{springfieldMessageSourceBasenames.filenames}")
				.getRawBeanDefinition();
		
		this.registerBeanDefinition(registry, beanName, beanDefinition);
	}
	
	public void addSpringfieldMessageSourceAccessor(BeanDefinitionRegistry registry, ModulesConfig modulesConfig) {
		
		String beanName = "springfieldMessageSourceAccessor";
		if(registry.isBeanNameInUse(beanName)) {registerBeanDefinition(registry, beanName); return;}

		BeanDefinition beanDefinition = BeanDefinitionBuilder
				.rootBeanDefinition("org.springframework.context.support.MessageSourceAccessor")
				.addConstructorArgReference("springfieldMessageSource")
				.getRawBeanDefinition();
		
		this.registerBeanDefinition(registry, beanName, beanDefinition);
	}


}

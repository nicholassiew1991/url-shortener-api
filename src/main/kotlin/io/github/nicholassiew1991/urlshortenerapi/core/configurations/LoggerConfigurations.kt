package io.github.nicholassiew1991.urlshortenerapi.core.configurations

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.InjectionPoint
import org.springframework.beans.factory.config.BeanDefinition
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Scope

@Configuration
class LoggerConfigurations {

  @Bean
  @Scope(BeanDefinition.SCOPE_PROTOTYPE)
  fun logger(injectionPoint: InjectionPoint): Logger {
    if (injectionPoint.methodParameter != null) {
      return LoggerFactory.getLogger(injectionPoint.methodParameter!!.containingClass)
    } else if (injectionPoint.field != null) {
      return LoggerFactory.getLogger(injectionPoint.field!!.declaringClass)
    }
    throw IllegalArgumentException()
  }

}
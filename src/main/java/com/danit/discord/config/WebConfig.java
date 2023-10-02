package com.danit.discord.config;

import com.danit.discord.annotations.PrefixMapping;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.web.method.HandlerTypePredicate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.lang.annotation.Annotation;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
                .addMapping("/*")
                .allowedOrigins("*")
                .allowCredentials(false)
                .allowedHeaders("*")
                .exposedHeaders("*")
                .maxAge(60 * 30)
                .allowedMethods("*");
//        registry.addMapping("/**");
    }

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false) {
            @Override
            protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
                return true;
            }
        };
        provider.addIncludeFilter(new AnnotationTypeFilter(PrefixMapping.class));

        String pkgName = "com.danit.discord.annotations";
        provider.findCandidateComponents(pkgName).forEach(bean -> {
            try {
                String className = bean.getBeanClassName();
                Class<? extends Annotation> clz = (Class<? extends Annotation>) Class.forName(className);

                String prefix = clz.getDeclaredAnnotation(PrefixMapping.class).value();
                configurer.addPathPrefix(prefix, HandlerTypePredicate.forAnnotation(clz));
            } catch (ClassNotFoundException | ClassCastException e) {
                e.printStackTrace(System.err);
            }
        });
    }
}

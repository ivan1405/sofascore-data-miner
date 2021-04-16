package com.sports.data.mapper;

import ma.glasnost.orika.OrikaSystemProperties;
import ma.glasnost.orika.impl.ConfigurableMapper;
import ma.glasnost.orika.impl.DefaultMapperFactory;

public class SportMapper extends ConfigurableMapper {

    @Override
    protected void configureFactoryBuilder(DefaultMapperFactory.Builder factoryBuilder) {
        super.configureFactoryBuilder(factoryBuilder);
        System.setProperty(OrikaSystemProperties.WRITE_SOURCE_FILES, "true");
        System.setProperty(OrikaSystemProperties.WRITE_CLASS_FILES, "true");
    }
}

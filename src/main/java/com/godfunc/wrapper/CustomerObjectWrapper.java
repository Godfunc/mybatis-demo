package com.godfunc.wrapper;

import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.reflection.wrapper.BeanWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomerObjectWrapper extends BeanWrapper {
    private Logger logger = LoggerFactory.getLogger(CustomerObjectWrapper.class);
    public CustomerObjectWrapper(MetaObject metaObject, Object object) {
        super(metaObject, object);
    }
    @Override
    public void set(PropertyTokenizer prop, Object value) {
        super.set(prop, value);
        logger.info("set {} value {}", prop.getIndexedName(), value);
    }
}

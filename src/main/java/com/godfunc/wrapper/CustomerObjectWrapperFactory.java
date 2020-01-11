package com.godfunc.wrapper;

import com.godfunc.entity.User;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.wrapper.ObjectWrapper;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;

public class CustomerObjectWrapperFactory implements ObjectWrapperFactory {
    public boolean hasWrapperFor(Object object) {
        if(object instanceof User) {
            return true;
        } else {
            return false;
        }
    }
    public ObjectWrapper getWrapperFor(MetaObject metaObject, Object object) {
        return new CustomerObjectWrapper(metaObject, object);
    }
}

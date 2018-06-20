package com.sandman.download.utils;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.shiro.codec.Base64;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * Created by shusesshou on 2017/9/22.
 */
public class SerializeUtils extends SerializationUtils {
    public static String serializaToString(Serializable obj){
        try{
            byte[] value = serialize(obj);
            return Base64.encodeToString(value);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static <T> T deserializeFromString(String base64){
        try{
            byte[] objectData = Base64.decode(base64);
            return deserialize(objectData);
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static <T> Collection<T> deserializeFromStrings(Collection<String> base64s){
        try{
            List<T> list = Lists.newLinkedList();
            for(String base64 : base64s){
                byte[] objectData = Base64.decode(base64);
                T t = deserialize(objectData);
                list.add(t);
            }
            return list;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}

package com.ks.tool;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * 把对象转化为byte数组，或把byte数组转化为对象
 * @author xushoushan
 *
 */
public class SerializeUtils {
	public static byte[] serialize(Object o) {  
        ByteArrayOutputStream out = new ByteArrayOutputStream();  
        try {  
            ObjectOutputStream outo = new ObjectOutputStream(out);  
            outo.writeObject(o);  
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
  
        return out.toByteArray();  
    }  
  
    public static Object deserialize(byte[] b) {  
        ObjectInputStream oin;  
        try {  
            oin = new ObjectInputStream(new ByteArrayInputStream(b));  
            try {  
                return oin.readObject();  
            } catch (ClassNotFoundException e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
                return null;  
            }  
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
            return null;  
        }  
  
    }
}

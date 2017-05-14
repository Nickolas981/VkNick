package com.example.nickolas.vknick;

import java.lang.reflect.Field;

/**
 * Created by Nickolas on 14.05.2017.
 */

public class GetResourceIdByString {
    public static int getResId(String resName, Class<?> c) {

        try {
            Field idField = c.getDeclaredField(resName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}

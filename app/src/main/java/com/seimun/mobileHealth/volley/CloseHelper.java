package com.seimun.mobileHealth.volley;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by Administrator on 2016/3/8
 */
public class CloseHelper {

    /**
     *
     * @param closeable 闭合
     */
    public static final void close(Closeable closeable) {
        try {
            closeable.close();
            closeable = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
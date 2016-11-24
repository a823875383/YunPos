package com.jsqix.yunpos.app.utils.hb;

import com.jsqix.yunpos.app.base.MyApplication;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ScriptUtil {

    /**
     * 和包加密公用方法
     *
     * @param pwd
     * @param rdm
     * @return
     */
    public static String makePayPwdByRhino(String pwd, String rdm) {
        String pwdInfo = "";
        try {
            String jsFileName = "pwd-encryption.js"; // js文件

            Object[] params = new Object[]{pwd, rdm};
            Context rhino = Context.enter();
            rhino.setOptimizationLevel(-1);
            Scriptable scope = rhino.initStandardObjects();
            InputStream is = MyApplication.getInstance().getAssets().open(jsFileName); // 字节流
            InputStreamReader isr = new InputStreamReader(is);// 字符流
            BufferedReader br = new BufferedReader(isr);// 缓冲流
            rhino.evaluateReader(scope, br, "JavaScript", 1, null);
            Object obj = scope.get("doEncrypt", scope);
            if (obj instanceof Function) {
                Function jsFunction = (Function) obj;
                Object jsResult = jsFunction.call(rhino, scope, scope, params);
                pwdInfo = Context.toString(jsResult);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            Context.exit();
        }
        return pwdInfo;
    }

}
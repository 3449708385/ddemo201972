package com.mgp.ddemo.commons.token;

import com.auth0.jwt.JWTSigner;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.internal.com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author mgp
 * @version 创建时间 ： 2018/1/22.
 */
public class JwtUtils {
    /**
     * 密钥
     */
    private static final String SECRET = "xxxx";
    /**
     * 默认字段key:exp
     */
    private static final String EXP = "exp";
    /**
     * 默认字段key:payload
     */
    private static final String TOKENPREFIX = "token_prefix";

    /**
     * 加密
     *
     * @param object  加密数据,token
     * @param maxTime 有效期（毫秒数）
     * @param <T>
     * @return
     */
    public static <T> String encode(T object, long maxTime) {

        try {

            final JWTSigner signer = new JWTSigner(SECRET);

            final Map<String, Object> data = new HashMap<>(10);

            ObjectMapper objectMapper = new ObjectMapper();

            String jsonString = objectMapper.writeValueAsString(object);

            data.put(TOKENPREFIX, jsonString);

            data.put(EXP, System.currentTimeMillis() + maxTime);

            return signer.sign(data);

        } catch (IOException e) {

            e.printStackTrace();

            return null;

        }
    }

    /**
     * 数据解密
     *
     * @param jwt    解密数据
     * @param tClass 解密类型
     * @param <T>
     * @return
     */
    public static <T> T decode(String jwt, Class<T> tClass) {

        try {

            final JWTVerifier jwtVerifier = new JWTVerifier(SECRET);

            final Map<String, Object> data = jwtVerifier.verify(jwt);

            //判断数据是否超时或者符合标准
            if (data.containsKey(EXP) && data.containsKey(TOKENPREFIX)) {

                long exp = (long) data.get(EXP);
                long currentTimeMillis = System.currentTimeMillis();

                if (exp > currentTimeMillis) {

                    String json = (String) data.get(TOKENPREFIX);

                    ObjectMapper objectMapper = new ObjectMapper();

                    return objectMapper.readValue(json, tClass);
                }
            }
            return null;

        } catch (Exception e) {

            e.printStackTrace();

            return null;
        }
    }
}

package com.cdtu.util;


import java.util.HashMap;
import java.util.Map;

import com.auth0.jwt.JWTSigner;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.internal.com.fasterxml.jackson.databind.ObjectMapper;
/**
 * 
 * ClassName:token加密/解密
 *
 * @author wencheng
 *
 */
public class Jwt {
    private static final String SECRET = "XX#$%()(#*!()!KL<><MQLMNQNQJQK sdfkjsdrow32234545fdf>?N<:{LWPW";

    private static final String EXP = "exp";

    private static final String PAYLOAD = "payload";

    //加密，传入一个对象和有效期
    public static <T> String sign(T object, long maxAge) {
        try {
            final JWTSigner signer = new JWTSigner(SECRET);
            final Map<String, Object> claims = new HashMap<String, Object>();
            ObjectMapper mapper = new ObjectMapper();
            String jsonString = mapper.writeValueAsString(object);
            claims.put(PAYLOAD, jsonString);
            claims.put(EXP, System.currentTimeMillis() + maxAge);
            return signer.sign(claims);
        } catch(Exception e) {
            return null;
        }
    }

    //解密，传入一个加密后的token字符串和解密后的类型
    public static<T> T unsign(String jwt, Class<T> classT) {
        final JWTVerifier verifier = new JWTVerifier(SECRET);
        try {
            final Map<String,Object> claims= verifier.verify(jwt);
            if (claims.containsKey(EXP) && claims.containsKey(PAYLOAD)) {
                long exp = (Long)claims.get(EXP);
                long currentTimeMillis = System.currentTimeMillis();
                if (exp > currentTimeMillis) {
                    String json = (String)claims.get(PAYLOAD);
                    ObjectMapper objectMapper = new ObjectMapper();
                    return objectMapper.readValue(json, classT);
                }
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }
//    public static void main(String[] args) {
//		Role role = new Role();
////		role.setUsername("1");
////		role.setPassword("1");
////		role.setRole("1");
////		System.out.println(role.toString());
////		System.out.println("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE1NDY4NzAxNzQyNTEsInBheWxvYWQiOiJ7XCJ1c2VybmFtZVwiOlwiMVwiLFwibmFtZVwiOm51bGwsXCJwYXNzd29yZFwiOlwiMVwiLFwicm9sZVwiOlwic3R1ZGVudFwiLFwiY2FwdGNoYVwiOlwiYmg1blwiLFwib2xkUGFzc3dvcmRcIjpudWxsLFwibmV3UGFzc3dvcmRcIjpudWxsLFwiZGV0ZXJtaU5lbmV3UGFzc3dvcmRcIjpudWxsLFwicmVtZW1iZXJNZVwiOnRydWUsXCJtc2dcIjpudWxsfSJ9.9BDs4Wu_w9ga206KdrmBg2ztFEOGkjo8KvI0-WohyEs");
//		String string = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE1NDY4NzUxMDA1NDAsInBheWxvYWQiOiJ7XCJ1c2VybmFtZVwiOlwiMVwiLFwibmFtZVwiOm51bGwsXCJwYXNzd29yZFwiOlwiMVwiLFwicm9sZVwiOlwic3R1ZGVudFwiLFwiY2FwdGNoYVwiOlwiejczalwiLFwib2xkUGFzc3dvcmRcIjpudWxsLFwibmV3UGFzc3dvcmRcIjpudWxsLFwiZGV0ZXJtaU5lbmV3UGFzc3dvcmRcIjpudWxsLFwicmVtZW1iZXJNZVwiOnRydWUsXCJtc2dcIjpudWxsfSJ9.g-P4FRWQWVMW4I9vtFkYGbsTqc1WKcRtZhNG36njiZo";
//		Role unsign = unsign(string, role.getClass());
//		System.out.println(unsign.toString());
//		String string1 = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE1NDY4NzQ5OTc0MjgsInBheWxvYWQiOiJ7XCJ1c2VybmFtZVwiOlwiMVwiLFwibmFtZVwiOm51bGwsXCJwYXNzd29yZFwiOlwiMVwiLFwicm9sZVwiOlwic3R1ZGVudFwiLFwiY2FwdGNoYVwiOlwibHBzcFwiLFwib2xkUGFzc3dvcmRcIjpudWxsLFwibmV3UGFzc3dvcmRcIjpudWxsLFwiZGV0ZXJtaU5lbmV3UGFzc3dvcmRcIjpudWxsLFwicmVtZW1iZXJNZVwiOnRydWUsXCJtc2dcIjpudWxsfSJ9.2XihkLK2MveFe-WhNwGX63wiYb_kEUkYr5iaCKshZyU";
//		Role unsign1 = unsign(string1, role.getClass());
//		System.out.println(unsign1.toString());
//	}
}
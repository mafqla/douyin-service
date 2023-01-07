package com.yali.vilivili.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.yali.vilivili.model.vo.TokenInfoVO;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * @Description
 * @Date 2023/1/6 17:18
 * @Author pq
 */
public class JwtUtils {

    private static final String SECRET = "!Q@W#E$R^Y&U";
    //token签发者
    private static final String ISSUSRE = "HZSTYGZPT";
    //token过期时间
    public static final Long EXPIRE_DATE = 1000 * 60L;

    /**
     * @return java.lang.String
     * @Author pq
     * @Description 根据tokenInfoVO生成token
     * @Date `17:54` 2023/1/6
     * @Param [tokenInfoVo]
     **/
    public static String getToken(TokenInfoVO tokenInfoVO) {
        //当前时间
        Date now = new Date();
        //创建过期时间
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.DATE, 7);  //7天过期
        //1. header
        Algorithm algorithm = Algorithm.HMAC256(SECRET);
        return JWT.create()
                .withIssuer(ISSUSRE)
                .withIssuedAt(now)
                .withExpiresAt(new Date(now.getTime() + EXPIRE_DATE))
                .withClaim("userId", tokenInfoVO.getUserId())
                .withClaim("loginUUID", tokenInfoVO.getLoginUUID())
                .sign(algorithm);
    }

   /**
    * @Author pq
    * @Description 根据map生成token
    * @Date 0:32 2023/1/8
    * @Param [map]
    * @return java.lang.String
    **/
    public static String getTokenByData(Map<String, String> map) {
        //创建过期时间
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.DATE, 7);  //7天过期

        //创建builder对象
        JWTCreator.Builder builder = JWT.create();
        //遍历map
        map.forEach(builder::withClaim);
        return builder.withExpiresAt(instance.getTime()).sign(Algorithm.HMAC256(SECRET));
    }

    /**
     * @Author pq
     * @Description 解析token
     * @Date 0:34 2023/1/8
     * @Param [token]
     * @return com.yali.vilivili.model.vo.TokenInfoVO
     **/
    public static TokenInfoVO decodeJwt(String token){
        Algorithm algorithm = Algorithm.HMAC256(SECRET);
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT jwt = verifier.verify(token);
        int userId = jwt.getClaim("userId").asInt();
        String loginUUID=jwt.getClaim("loginUUID").asString();
        TokenInfoVO tokenInfoVO=new TokenInfoVO();
        tokenInfoVO.setUserId(userId);
        tokenInfoVO.setLoginUUID(loginUUID);
        return tokenInfoVO;
    }
}

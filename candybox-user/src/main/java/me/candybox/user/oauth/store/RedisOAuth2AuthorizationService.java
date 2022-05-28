package me.candybox.user.oauth.store;

import com.alibaba.fastjson.JSON;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.security.oauth2.core.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;

import lombok.extern.slf4j.Slf4j;
import me.candybox.core.config.ConstantConfig;



@Slf4j
public class RedisOAuth2AuthorizationService extends JdbcOAuth2AuthorizationService{
	// private final Map<String, OAuth2Authorization> authorizations = new ConcurrentHashMap<>();

	// public RedisOAuth2AuthorizationService(OAuth2Authorization... authorizations) {
	// 	this(Arrays.asList(authorizations));
	// }

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    public RedisOAuth2AuthorizationService(JdbcOperations jdbcOperations,
            RegisteredClientRepository registeredClientRepository) {
        super(jdbcOperations, registeredClientRepository);
    }

    @Override
    public OAuth2Authorization findById(String id) {
        log.debug("id={}",id);
        return super.findById(id);
        // return JSON.parseObject(redisTemplate.opsForValue().get(ACCESS_TOKEN_ID+id),new TypeReference<OAuth2Authorization>() {});
    }

    @Override
    public OAuth2Authorization findByToken(String token, OAuth2TokenType tokenType) {
        log.debug("token={},tokenType={}",token,tokenType);
        return super.findByToken(token, tokenType);
        // return JSON.parseObject(redisTemplate.opsForValue().get(ACCESS_TOKEN_VALUE+token),new TypeReference<OAuth2Authorization>() {});
    }

    @Override
    public void remove(OAuth2Authorization authorization) {
        log.debug("authorization={}",authorization);
        super.remove(authorization);
        String tokenId=authorization.getId();
        String tokenValue=authorization.getAccessToken().getToken().getTokenValue();
        redisTemplate.delete(ConstantConfig.ACCESS_TOKEN_ID+tokenId);
        redisTemplate.delete(ConstantConfig.ACCESS_TOKEN_VALUE+tokenValue);
    }

    @Override
    public void save(OAuth2Authorization authorization) {
        log.debug("authorization={}",JSON.toJSONString(authorization));
        super.save(authorization);
        String tokenId=authorization.getId();
        String tokenValue=authorization.getAccessToken().getToken().getTokenValue();
        String value=JSON.toJSONString(authorization);
        redisTemplate.opsForValue().set(ConstantConfig.ACCESS_TOKEN_ID+tokenId,value);
        redisTemplate.expireAt(ConstantConfig.ACCESS_TOKEN_ID+tokenId, authorization.getAccessToken().getToken().getExpiresAt());
        redisTemplate.opsForValue().set(ConstantConfig.ACCESS_TOKEN_VALUE+tokenValue,value);
        redisTemplate.expireAt(ConstantConfig.ACCESS_TOKEN_VALUE+tokenValue, authorization.getAccessToken().getToken().getExpiresAt());
    }
    
}

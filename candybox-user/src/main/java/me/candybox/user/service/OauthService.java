package me.candybox.user.service;



import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.hutool.core.util.IdUtil;
import me.candybox.core.config.TokenInfoThreadLocal;
import me.candybox.user.mapper.Oauth2RegisteredClientMapper;
import me.candybox.user.model.Oauth2RegisteredClient;

/**
 * oauth认证业务类
 */
@Service
public class OauthService {

    @Autowired
    private Oauth2RegisteredClientMapper oauth2RegisteredClientMapper;

    /**
     * 认证保存
     * @param oauth2RegisteredClient
     * @return
     */
    public int save(Oauth2RegisteredClient oauth2RegisteredClient){
        oauth2RegisteredClient.setClientId(IdUtil.simpleUUID());
        oauth2RegisteredClient.setClientSecret("{noop}"+IdUtil.simpleUUID());
        oauth2RegisteredClient.setClientIdIssuedAt(new Date());
        oauth2RegisteredClient.setClientAuthenticationMethods("client_secret_post");
        oauth2RegisteredClient.setAuthorizationGrantTypes("client_credentials");
        oauth2RegisteredClient.setClientSettings("{\"@class\":\"java.util.Collections$UnmodifiableMap\",\"settings.client.require-proof-key\":false,\"settings.client.require-authorization-consent\":true}");
        oauth2RegisteredClient.setTokenSettings("{\"@class\":\"java.util.Collections$UnmodifiableMap\",\"settings.token.reuse-refresh-tokens\":true,\"settings.token.id-token-signature-algorithm\":[\"org.springframework.security.oauth2.jose.jws.SignatureAlgorithm\",\"RS256\"],\"settings.token.access-token-time-to-live\":[\"java.time.Duration\",7200.000000000],\"settings.token.access-token-format\":{\"@class\":\"org.springframework.security.oauth2.core.OAuth2TokenFormat\",\"value\":\"self-contained\"},\"settings.token.refresh-token-time-to-live\":[\"java.time.Duration\",3600.000000000]}");

        oauth2RegisteredClient.setCreateTime(new Date());
        oauth2RegisteredClient.setCreateUserId(TokenInfoThreadLocal.getTokenInfo().getUserId());
        oauth2RegisteredClient.setCreateUserName(TokenInfoThreadLocal.getTokenInfo().getUserName());
        oauth2RegisteredClient.setStatus(1);
        return oauth2RegisteredClientMapper.insert(oauth2RegisteredClient);
       
    }
}

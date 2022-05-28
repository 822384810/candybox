package me.candybox.user.model;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import me.candybox.core.annotation.CbDataName;
import me.candybox.core.model.CbBaseModel;

@Getter
@Setter
@CbDataName(name="oauth_client")
public class Oauth2RegisteredClient extends CbBaseModel<Oauth2RegisteredClient>{
    @TableId(type=IdType.ASSIGN_UUID)
    @ApiModelProperty(value = "id")
    private String id;

    private String clientId;
    private Date clientIdIssuedAt;
    private String clientSecret;
    private Date clientSecretExpiresAt;
    private String clientName;
    private String clientAuthenticationMethods;
    private String authorizationGrantTypes;
    private String redirectUris;
    private String scopes;
    private String clientSettings;
    private String tokenSettings;

}

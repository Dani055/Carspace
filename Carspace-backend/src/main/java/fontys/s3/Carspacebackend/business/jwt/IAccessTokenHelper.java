package fontys.s3.Carspacebackend.business.jwt;

import fontys.s3.Carspacebackend.domain.AccessToken;
import fontys.s3.Carspacebackend.domain.User;


public interface IAccessTokenHelper {
    String generateAccessToken(User user);

    AccessToken decode(String accessTokenEncoded);
}

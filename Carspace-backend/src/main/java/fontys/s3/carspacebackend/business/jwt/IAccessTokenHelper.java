package fontys.s3.carspacebackend.business.jwt;

import fontys.s3.carspacebackend.domain.AccessToken;
import fontys.s3.carspacebackend.domain.User;


public interface IAccessTokenHelper {
    String generateAccessToken(User user);

    AccessToken decode(String accessTokenEncoded);
}

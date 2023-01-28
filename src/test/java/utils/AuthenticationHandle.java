package utils;

import org.apache.commons.codec.binary.Base64;

public class AuthenticationHandle {

    public static String getEncodedCredStr ( String email, String apiToken ) {
        if ( email == null || apiToken == null ) {
            throw new IllegalArgumentException("[ERR] email or api token can't be null");
        }
        String cred = email.concat(":").concat(apiToken);
        byte[] encodedCred = Base64.encodeBase64(cred.getBytes());
        return new String(encodedCred);
    }
}

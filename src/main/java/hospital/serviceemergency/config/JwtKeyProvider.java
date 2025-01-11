package hospital.serviceemergency.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class JwtKeyProvider {
    private RSAPublicKey publicKey;

    @Value("classpath:public.pem")
    private Resource publicKeyResource;

    @PostConstruct
    public void init() throws Exception {
        String publicKeyPEM = new String(publicKeyResource.getInputStream().readAllBytes())
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s", "");

        byte[] encoded = Base64.getDecoder().decode(publicKeyPEM);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(encoded);
        publicKey = (RSAPublicKey) keyFactory.generatePublic(keySpec);
    }

    public RSAPublicKey getPublicKey() {
        return publicKey;
    }
}

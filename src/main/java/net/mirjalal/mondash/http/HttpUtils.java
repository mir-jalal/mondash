package net.mirjalal.mondash.http;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.http.HttpClient;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.springframework.http.client.reactive.JdkClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;

import net.mirjalal.mondash.encryption.Base64Encryptor;

public class HttpUtils {

    public static SSLContext createSslContext(String caCertString) throws CertificateException, KeyStoreException, NoSuchAlgorithmException, IOException, KeyManagementException {
        Base64Encryptor encryptor = new Base64Encryptor();
        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
        ByteArrayInputStream inputStream = new ByteArrayInputStream(encryptor.decrypt(caCertString).getBytes(StandardCharsets.UTF_8));
        Certificate caCert = certificateFactory.generateCertificate(inputStream);

        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        keyStore.load(null, null);
        keyStore.setCertificateEntry("ca", caCert);
        
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(keyStore);

        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, trustManagerFactory.getTrustManagers(), null);
        
        return sslContext;
    }

    public static CredentialsProvider createCredentialsProvider(String username, String password) {
        CredentialsProvider provider = new BasicCredentialsProvider();
        provider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username, password));
        return provider;
    }

    public static WebClient createWebClient(String caCertString) throws KeyManagementException, CertificateException, KeyStoreException, NoSuchAlgorithmException, IOException {
        return createWebClient(createSslContext(caCertString));
    }

    public static WebClient createWebClient(SSLContext sslContext) {
        HttpClient httpClient = HttpClient.newBuilder().sslContext(sslContext).build();
        return WebClient.builder().clientConnector(new JdkClientHttpConnector(httpClient)).build();
    }
}

package com.accenture.product.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApplicationConfiguration {

    @Value("${trust.store}")
    private Resource trustStore;

    @Value("${trust.store.password}")
    private String trustStorePassword;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

   /* @Bean
    public RestTemplate restTemplate() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException, CertificateException, IOException {
        // Option 1: Trust all certificates (for development/testing only, not recommended for production)
//        SSLContext sslContext = SSLContextBuilder.create()
//                .loadTrustMaterial(null, (X509Certificate[] chain, String authType) -> true)
//                .build();

        // Option 2: Load specific truststore (recommended for production)
        SSLContext sslContext = new SSLContextBuilder()
                .loadTrustMaterial(trustStore.getURL(), trustStorePassword.toCharArray()).build();

        SSLConnectionSocketFactory sslConFactory = new SSLConnectionSocketFactory(sslContext);
        HttpClientConnectionManager cm = PoolingHttpClientConnectionManagerBuilder.create()
                .setSSLSocketFactory(sslConFactory)
                .build();
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm).build();
        ClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
        return new RestTemplate(requestFactory);
    }*/
}
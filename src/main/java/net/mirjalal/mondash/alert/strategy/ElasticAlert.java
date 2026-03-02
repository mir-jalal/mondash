package net.mirjalal.mondash.alert.strategy;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpHost;
import org.apache.http.client.CredentialsProvider;
import org.elasticsearch.client.RestClient;

import com.fasterxml.jackson.databind.node.ObjectNode;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.ElasticsearchException;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import net.mirjalal.mondash.http.HttpUtils;
import net.mirjalal.mondash.model.Alert;
import net.mirjalal.mondash.model.AlertSource;
import jakarta.annotation.PreDestroy;

public class ElasticAlert implements AlertStrategy {

    public static String USERNAME = "username";
    public static String PASSWORD = "password";
    public static String URL = "url";
    public static String CA_CERT = "caCert";
    public static String INDEX_NAME = "indexName";

    private final ElasticsearchClient client;
    private final String timestampField = "@timestamp";
    private String indexName;

    @PreDestroy
    public void preDestroy() {
        try {
            this.client.close();
        } catch (IOException e) {}
    }

    public ElasticAlert(AlertSource alertSource) throws KeyManagementException, CertificateException, KeyStoreException, NoSuchAlgorithmException, IOException {
        String username = alertSource.getParameterValue(USERNAME);
        String password = alertSource.getParameterValue(PASSWORD);
        String hostname = alertSource.getParameterValue(URL);
        String caCert = alertSource.getParameterValue(CA_CERT);
        this.indexName = alertSource.getParameterValue(INDEX_NAME);

        RestClient restClient = createRestClient(hostname, username, password, caCert);

        ElasticsearchTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());
        
        this.client = new ElasticsearchClient(transport);
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    private SearchRequest createSearchSearchRequestTimestampAfter(String timestamp) {
        return SearchRequest.of(s -> s
            .index(this.indexName)
            .query(q -> q
                .range( r -> r
                    .date(v -> v
                        .field(this.timestampField)
                        .gte(timestamp)
                    )
                )
            )
        );
    }

    private SearchResponse<ObjectNode> searchAfterTimestamp(String timestamp) throws ElasticsearchException, IOException {
        SearchRequest request = this.createSearchSearchRequestTimestampAfter(timestamp);

        return this.client.search(request, ObjectNode.class);
    }

    private List<Alert> getAlertsByTimestamp(String timestamp) throws IOException {

        List<Alert> newAlerts = new ArrayList<>();
        
        this.searchAfterTimestamp(timestamp).hits().hits().forEach(hit -> {
            newAlerts.add(Alert.createAlert(hit.source()));
        });
        return newAlerts;
    }

    private RestClient createRestClient(String hostname, String username, String password, String caCert) throws CertificateException, KeyStoreException, NoSuchAlgorithmException, IOException, KeyManagementException {
        CredentialsProvider provider = HttpUtils.createCredentialsProvider(username, password);
        SSLContext sslContext = HttpUtils.createSslContext(caCert);
        
        return RestClient.builder(HttpHost.create(hostname))
            .setHttpClientConfigCallback(cb -> {
                cb.setDefaultCredentialsProvider(provider);
                cb.setSSLContext(sslContext);
                return cb;
            }).build();
    }

    @Override
    public Iterable<Alert> getActiveAlerts(String timestamp) {
        try {
            return this.getAlertsByTimestamp(timestamp);
        } catch (IOException exception) {
            return new ArrayList<>();
        }
    }

    @Override
    public Iterable<Alert> getAlerts() {
        return null;
    }
}

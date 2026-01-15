package net.mirjalal.mondash.alert;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;

import com.fasterxml.jackson.databind.node.ObjectNode;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import jakarta.annotation.PreDestroy;
import net.mirjalal.mondash.ssl.SslUtil;


public class ElasticAlert implements AlertStrategy {

    private final ElasticsearchClient client;
    private String indexName;
    private final String timestampField = "@timestamp";

    @PreDestroy
    public void preDestroy() {
        try {
            this.client.close();
        } catch (IOException e) {}
    }

    public ElasticAlert(
        String username,
        String password,
        String hostname,
        String caCert,
        String indexName
    ) throws KeyManagementException, CertificateException, KeyStoreException, NoSuchAlgorithmException, IOException {

        CredentialsProvider provider = new BasicCredentialsProvider();
        provider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username, password));

        SSLContext sslContext = SslUtil.createSslContext(caCert);

        RestClient restClient = RestClient.builder(HttpHost.create(hostname))
            .setHttpClientConfigCallback(cb -> {
                cb.setDefaultCredentialsProvider(provider);
                cb.setSSLContext(sslContext);
                return cb;
            }).build();

        ElasticsearchTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());
        this.indexName = indexName;
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

    private List<Alert> getAlertsByTimestamp(String timestamp) throws IOException {
        SearchRequest request = this.createSearchSearchRequestTimestampAfter(timestamp);

        SearchResponse<ObjectNode> response = this.client.search(request, ObjectNode.class);

        List<Alert> newAlerts = new ArrayList<>();
        
        response.hits().hits().forEach(hit -> {
            newAlerts.add(Alert.createAlert(hit.source()));
        });
        return newAlerts;
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

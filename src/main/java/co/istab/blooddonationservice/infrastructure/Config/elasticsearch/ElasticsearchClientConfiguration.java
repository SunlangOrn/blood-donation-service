package co.istab.blooddonationservice.infrastructure.Config.elasticsearch;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import lombok.Data;

import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.message.BasicHeader;
import org.apache.http.ssl.SSLContextBuilder;
import org.elasticsearch.client.RestClient;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.SSLContext;


@Configuration
@Data
public class ElasticsearchClientConfiguration {

    @Bean
    public RestClient restClient() throws Exception {
        SSLContext sslContext = SSLContextBuilder.create()
                .loadTrustMaterial(null, (chain, authType) -> true) // trust all for dev
                .build();

        Header[] defaultHeaders = new Header[]{
                new BasicHeader("Authorization", "ApiKey VE1oMnJac0JFOE5aeVYySFA1Y286ckVWeEpSU29SUnV2ODhSX0RJNEFsZw==")
        };

        return RestClient.builder(HttpHost.create("https://localhost:9200"))
                .setHttpClientConfigCallback(hc -> hc
                        .setSSLContext(sslContext)
                        .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE))
                .setDefaultHeaders(defaultHeaders)
                .build();
    }

    @Bean
    public ElasticsearchClient elasticsearchClient(RestClient restClient) {
        RestClientTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());
        return new ElasticsearchClient(transport);
    }
}

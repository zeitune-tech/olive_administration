package sn.zeitune.olive_insurance_administration.app.clients.impl;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import sn.zeitune.olive_insurance_administration.app.clients.CoverageClient;
import sn.zeitune.olive_insurance_administration.app.dto.external.CoverageReferenceResponse;
import sn.zeitune.olive_insurance_administration.app.dto.external.CoverageRequest;

import java.util.List;
import java.util.UUID;

@Component
@Slf4j
@Transactional
public class CoverageClientImpl implements CoverageClient {

    private final WebClient userWebClient;

    public CoverageClientImpl(WebClient.Builder webClientBuilder) {
        this.userWebClient = webClientBuilder.baseUrl("http://localhost:8030/api/v1").build();
    }

    @Override
    public void createCoverages(CoverageRequest request) {
        userWebClient.post()
                .uri("/inter-services/coverages")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    @Override
    public List<CoverageReferenceResponse> getCoverageReferences(UUID managementEntity) {
        return userWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/inter-services/coverages/references")
                        .queryParam("managementEntity", managementEntity)
                        .build())
                .retrieve()
                .bodyToFlux(CoverageReferenceResponse.class)
                .collectList()
                .block();
    }

    @Override
    public List<CoverageReferenceResponse> initCoverageReferences(UUID managementEntity) {
        return userWebClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/inter-services/coverages/init/" + managementEntity)
                        .build())
                .retrieve()
                .bodyToFlux(CoverageReferenceResponse.class)
                .collectList()
                .block();
    }
}

package com.e451.rest.gateways.impl;

import com.e451.rest.domains.assessment.Assessment;
import com.e451.rest.domains.assessment.AssessmentResponse;
import com.e451.rest.gateways.AssessmentServiceGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Created by j747951 on 6/15/2017.
 */
@Service
public class AssessmentServiceGatewayImpl implements AssessmentServiceGateway {

    private final String assessmentServiceUri;
    private final RestTemplateBuilder restTemplateBuilder;

    @Autowired
    public AssessmentServiceGatewayImpl(@Value("${service-uri}") String assessmentServiceUri,
                                        RestTemplateBuilder restTemplateBuilder) {
        this.assessmentServiceUri = assessmentServiceUri + "/assessments";
        this.restTemplateBuilder = restTemplateBuilder;
    }

    @Override
    public ResponseEntity<AssessmentResponse> getAssessments() {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(assessmentServiceUri);
        RestTemplate template = restTemplateBuilder.build();
        ResponseEntity response;

        try {
            response = template.getForEntity(builder.build().toUriString(), AssessmentResponse.class);
            return ResponseEntity.status(HttpStatus.OK).body((AssessmentResponse) response.getBody());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<AssessmentResponse> getAssessmentByGuid(String guid) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(assessmentServiceUri).pathSegment(guid);
        RestTemplate template = restTemplateBuilder.build();
        ResponseEntity response;

        try {
            response = template.getForEntity(builder.build().toUriString(), AssessmentResponse.class);
            return ResponseEntity.status(HttpStatus.OK).body((AssessmentResponse) response.getBody());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<AssessmentResponse> createAssessment(Assessment assessment) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(assessmentServiceUri);
        RestTemplate template = restTemplateBuilder.build();
        ResponseEntity response;

        try {
            response = template.postForEntity(builder.build().toUriString(), assessment, AssessmentResponse.class);
            return ResponseEntity.status(HttpStatus.CREATED).body((AssessmentResponse) response.getBody());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<AssessmentResponse> updateAssessment(Assessment assessment) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(assessmentServiceUri);
        RestTemplate template = restTemplateBuilder.build();
        HttpEntity<Assessment> requestEntity = new HttpEntity<>(assessment, null);
        ResponseEntity response;

        try {
            response = template.exchange(builder.build().toUri(), HttpMethod.PUT, requestEntity, AssessmentResponse.class);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body((AssessmentResponse) response.getBody());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
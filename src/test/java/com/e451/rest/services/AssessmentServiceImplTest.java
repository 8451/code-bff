package com.e451.rest.services;

import com.e451.rest.domains.assessment.Assessment;
import com.e451.rest.domains.assessment.AssessmentResponse;
import com.e451.rest.gateways.AssessmentServiceGateway;
import com.e451.rest.services.impl.AssessmentServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

/**
 * Created by j747951 on 6/15/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class AssessmentServiceImplTest {

    private AssessmentService assessmentService;

    @Mock
    private AssessmentServiceGateway assessmentServiceGateway;

    private List<Assessment> assessments;

    @Before
    public void setup() {
        this.assessmentService = new AssessmentServiceImpl(assessmentServiceGateway);

        assessments = Arrays.asList(
                new Assessment("1", "fn1", "ln1", "test1@test.com"),
                new Assessment("2", "fn2", "ln2", "test2@test.com"),
                new Assessment("3", "fn3", "ln3", "test3@test.com")
        );
    }

    @Test
    public void whenGetAssessments_returnsListOfAssessments() {
        AssessmentResponse assessmentResponse = new AssessmentResponse();
        assessmentResponse.setAssessments(this.assessments);

        ResponseEntity<AssessmentResponse> gatewayResponse = ResponseEntity.ok(assessmentResponse);

        when(assessmentServiceGateway.getAssessments()).thenReturn(gatewayResponse);

        ResponseEntity<AssessmentResponse> response = assessmentService.getAssessments();

        Assert.assertEquals(3, response.getBody().getAssessments().size());
    }
    
    @Test
    public void whenGetAssessmentByGuid_returnsListOfSingleAssessment() {
        AssessmentResponse assessmentResponse = new AssessmentResponse();
        assessmentResponse.setAssessments(Arrays.asList(this.assessments.get(0)));

        ResponseEntity<AssessmentResponse> gatewayResponse = new ResponseEntity<>(assessmentResponse, HttpStatus.OK);

        when(assessmentServiceGateway.getAssessmentByGuid("1")).thenReturn(gatewayResponse);

        ResponseEntity<AssessmentResponse> response = assessmentService.getAssessmentByGuid("1");

        Assert.assertTrue(response.getBody().getAssessments().size() == 1);
    }

    @Test
    public void whenCreateAssessment_returnNewAssessment() {
        AssessmentResponse assessmentResponse = new AssessmentResponse();
        Assessment assessment = new Assessment("4", "fn4", "ln4", "test4@test.com");
        assessmentResponse.setAssessments(Arrays.asList(assessment));

        ResponseEntity<AssessmentResponse> gatewayRespone =
                ResponseEntity.status(HttpStatus.CREATED).body(assessmentResponse);

        when(assessmentServiceGateway.createAssessment(assessment)).thenReturn(gatewayRespone);

        ResponseEntity<AssessmentResponse> response = assessmentService.createAssessment(assessment);

        Assert.assertEquals(1, response.getBody().getAssessments().size());
    }

    @Test
    public void whenUpdateAssessment_returnUpdatedAssessment() {
        AssessmentResponse assessmentResponse = new AssessmentResponse();

        Assessment assessment = new Assessment("4", "fn4", "ln4", "test4@test.com");
        assessmentResponse.setAssessments(Arrays.asList(assessment));

        ResponseEntity<AssessmentResponse> gatewayResponse = new ResponseEntity<AssessmentResponse>(assessmentResponse, HttpStatus.ACCEPTED);

        when(assessmentServiceGateway.updateAssessment(assessment)).thenReturn(gatewayResponse);

        ResponseEntity<AssessmentResponse> response = assessmentService.updateAssessment(assessment);

        Assert.assertEquals(1, response.getBody().getAssessments().size());
        Assert.assertEquals(assessment, response.getBody().getAssessments().get(0));
    }
}
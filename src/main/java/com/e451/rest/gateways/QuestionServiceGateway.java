package com.e451.rest.gateways;

import com.e451.rest.domains.language.LanguageResponse;
import com.e451.rest.domains.question.Question;
import com.e451.rest.domains.question.QuestionResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;


/**
 * Created by e384873 on 6/9/2017.
 */
public interface QuestionServiceGateway {
    ResponseEntity<QuestionResponse> getQuestions();
    ResponseEntity<QuestionResponse> getQuestions(int page, int size, String property);
    ResponseEntity<QuestionResponse> searchQuestions(int page, int size, String property, String searchString);
    ResponseEntity<QuestionResponse> getQuestion(String id);
    ResponseEntity<QuestionResponse> createQuestion(Question question);
    ResponseEntity<QuestionResponse> updateQuestion(Question question);
    ResponseEntity deleteQuestion(String id);
    ResponseEntity<LanguageResponse> getLanguages();
}

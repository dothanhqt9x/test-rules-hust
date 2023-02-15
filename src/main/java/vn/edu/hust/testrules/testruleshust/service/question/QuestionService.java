package vn.edu.hust.testrules.testruleshust.service.question;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.json.JSONException;
import org.springframework.stereotype.Service;
import vn.edu.hust.testrules.testruleshust.api.admin.apiresponse.AllQuestionApiResponse;
import vn.edu.hust.testrules.testruleshust.api.question.apiresponse.QuestionGetAllApiResponse;
import vn.edu.hust.testrules.testruleshust.api.question.apirequest.SubmitQuestionApiRequest;
import vn.edu.hust.testrules.testruleshust.api.question.apiresponse.HistoryApiResponse;
import vn.edu.hust.testrules.testruleshust.api.question.apiresponse.UserMaxScoreApiResponse;
import vn.edu.hust.testrules.testruleshust.exception.ServiceException;
import vn.edu.hust.testrules.testruleshust.service.question.json.HistoryJson;
import vn.edu.hust.testrules.testruleshust.service.question.servicerequest.QuestionServiceRequest;
import vn.edu.hust.testrules.testruleshust.service.question.serviceresponse.QuestionServiceResponse;

import java.io.IOException;
import java.util.List;

@Service
public interface QuestionService {

    QuestionServiceResponse getOneQuestion() throws JsonProcessingException;
    void insertNewQuestion(QuestionServiceRequest request) throws JsonProcessingException, ServiceException;
    List<QuestionGetAllApiResponse> getAllQuestion(Integer size);
    void insertAllQuestion() throws IOException;
    void submitQuestion(List<SubmitQuestionApiRequest> requests, String email) throws JsonProcessingException;
    List<HistoryApiResponse> getListHistory(String email);
    List<HistoryJson> getHistoryDetails(Integer historyId) throws JsonProcessingException, JSONException, ServiceException;
    List<AllQuestionApiResponse> getListQuestion(int pageNo, int pageSize);
    List<UserMaxScoreApiResponse> submitQuestionForApp(String email, Integer score);
}

package vn.edu.hust.testrules.testruleshust.service.question.json;

import lombok.Builder;
import lombok.Getter;
import vn.edu.hust.testrules.testruleshust.api.question.post.apirequest.SubmitQuestionApiRequest;

import java.util.List;

@Getter
@Builder
public class HistoryJson {

    private final List<SubmitQuestionApiRequest> historyJson;
}

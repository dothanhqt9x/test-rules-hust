package vn.edu.hust.testrules.testruleshust.lcs;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class Question {

    String question;
    ArrayList<String> answer;
    ArrayList<Integer> key;
}

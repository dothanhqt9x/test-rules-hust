package vn.edu.hust.testrules.testruleshust.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ServiceException extends Exception{

    private final String causeId;
}

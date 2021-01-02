package Rest.utils;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
public class CustomerRequestAlreadyExistsException extends Exception{
    public CustomerRequestAlreadyExistsException(Long id){
        super("Customer request already exist " + id);
    }
}

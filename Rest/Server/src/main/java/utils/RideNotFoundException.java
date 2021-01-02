package utils;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
// This annotation tell Spring to convert this exception to HttpStatus.NOT_FOUND
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class RideNotFoundException extends RuntimeException{
    public RideNotFoundException(Long id){
        super("Could not find ride " + id);
    }
}

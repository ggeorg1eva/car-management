package pu.fmi.carmanagement.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

public class UtilMethods {
    /**
     * Helper method which checks if the source is found in the database. If it isn't found, a ResponseStatusException is thrown.
     * @param inDB - the source to check
     * @param errMsg - the message we want to throw
     * @param <T> - the type of the resource (e.g Car/Garage/MaintenanceRequest)
     */
    public static  <T> void isSourceInDB(Optional<T> inDB, String errMsg) {
        if (inDB.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, errMsg);
        }
    }
}

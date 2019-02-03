package si.recek.wealthbuild.util;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(Long id) {
        super("Resource with id: " + id + "could not be found.");
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }
}

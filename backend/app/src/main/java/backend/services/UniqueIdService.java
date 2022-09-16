package backend.services;

import java.util.UUID;

public class UniqueIdService {
    public String uuidv4() {
        return UUID.randomUUID().toString();
    }
}

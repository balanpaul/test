package rest.client;

import agentie.model.Zbor;
import agentie.services.rest.ServiceException;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Callable;

public class ZborClient {
    public static final String URL = "http://localhost:8080/agentie/zbor";

    private RestTemplate restTemplate = new RestTemplate();


    private <T> T execute(Callable<T> callable) {
        try {
            return callable.call();
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    public Zbor[] getAll() {
        System.out.println("getAll ZborClient");

        return execute(() -> restTemplate.getForObject( URL, Zbor[].class));
    }
    public Zbor create(Zbor user) {
        return execute(() -> restTemplate.postForObject(URL, user, Zbor.class));
    }

    public void update(Zbor user) {
        execute(() -> {
            restTemplate.put(String.format("%s/%s", URL, user.getId()), user);
            return null;
        });
    }

    public void delete(Integer id) {
        execute(() -> {
            restTemplate.delete(String.format("%s/%s", URL, id));
            return null;
        });
    }
}

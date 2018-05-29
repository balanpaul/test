package start;

import agentie.model.Zbor;
import agentie.services.rest.ServiceException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import rest.client.ZborClient;

public class StartRestClient {

    private final static ZborClient pClient=new ZborClient();

    public static void main(String[] args) {
        RestTemplate restTemplate=new RestTemplate();
        try{
            show(()->{
                Zbor[] res=pClient.getAll();
                for(Zbor p:res){
                    System.out.println(p.getDestinatie()+", "+p.getAeroport()+", "+p.getNrLocuri());
                }
            });
        } catch (RestClientException ex){
            System.out.println("Exception ... "+ex.getMessage());
        }
    }

    private static void show(Runnable task) {
        try {
            task.run();
        } catch (ServiceException e) {
            System.out.println("Service exception"+ e);
        }
    }
}

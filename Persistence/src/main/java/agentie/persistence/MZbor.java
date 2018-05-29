package agentie.persistence;

import agentie.model.Zbor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.TreeMap;

@Component
public class MZbor {
   private Map<Integer,Zbor> allZbor;

    public MZbor() {
        allZbor=new TreeMap<Integer, Zbor>();
        allZbor.put(1,new Zbor(1,"Str","sda","dsa",1));
    }

    public Zbor[] getZbors(){
        return allZbor.values().toArray(new Zbor[allZbor.size()]);
    }

    public void save(Zbor zbor){
        allZbor.put(zbor.getId(),zbor);
    }

    public void delete(Integer integer){
        if(allZbor.containsKey(integer))
            allZbor.remove(integer);
    }

    public void update(Integer integer,Zbor zbor){
        if(allZbor.containsKey(integer))
            if(integer==zbor.getId()){
            allZbor.put(integer,zbor);
            return;
            }
    }
}

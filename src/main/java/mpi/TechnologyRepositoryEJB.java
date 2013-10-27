package mpi;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

@Stateless
public class TechnologyRepositoryEJB implements TechnologyRepository {

    private List<Technology> technologies = new ArrayList<Technology>();
    
    @Override
    public void store(Technology technology){
        technologies.add(technology);
    }
    
    @Override
    public List<Technology> list(){
        return technologies;
    }
    
}

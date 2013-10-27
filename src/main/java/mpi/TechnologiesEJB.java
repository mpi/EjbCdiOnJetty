package mpi;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class TechnologiesEJB implements TechnologiesBean {

    @EJB
    private TechnologyRepository repository;
    
    @Override
    public void save(Technology technology) {
        repository.store(technology);
    }

    @Override
    public List<Technology> list() {
        return repository.list();
    }

    
}

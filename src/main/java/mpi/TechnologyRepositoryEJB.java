package mpi;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class TechnologyRepositoryEJB implements TechnologyRepository {

    @PersistenceContext(unitName="TechnologiesPU")
    private EntityManager entityManager;
    
    @Override
    public void store(Technology technology){
        entityManager.persist(technology);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<Technology> list(){
        return entityManager.createQuery("select t from Technology t").getResultList();
    }
    
}

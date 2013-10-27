package mpi;

import java.util.List;

public interface TechnologyRepository {

    public void store(Technology technology);

    public List<Technology> list();

}
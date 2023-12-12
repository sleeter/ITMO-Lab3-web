package db;

import entity.DotEntity;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceContext;

import java.io.Serializable;
import java.util.List;

@Named(value = "dotDao")
@SessionScoped
public class DotDao implements Serializable {
    @PersistenceContext(unitName = "dots")
    private EntityManager entityManager;
    private EntityManagerFactory entityManagerFactory;

    public void createEntityManager() {
        entityManagerFactory = Persistence.createEntityManagerFactory("dots");
        entityManager = entityManagerFactory.createEntityManager();
    }

    public void addDotToDB(DotEntity dot) {
        entityManager.getTransaction().begin();
        try {
            entityManager.persist(dot);
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
        }
        entityManager.getTransaction().commit();
    }

    public List<DotEntity> getDotsFromDB() {
        return entityManager.createQuery("select d from DotEntity d", DotEntity.class).getResultList();
    }

    public void clearDotsInDB() {
        entityManager.getTransaction().begin();
        try {
            entityManager.createQuery("delete from DotEntity").executeUpdate();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
        }
        entityManager.getTransaction().commit();
    }
}

package pl.coderslab.project.dao;

import org.springframework.stereotype.Repository;
import pl.coderslab.project.entities.Exercise;
import pl.coderslab.project.entities.TrainingPlan;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class TrainingPlanDao {

    @PersistenceContext
    private EntityManager entityManager;

    public void createTrainingPlan(TrainingPlan trainingPlan) {entityManager.persist(trainingPlan);}

    public TrainingPlan readTrainingPlan(int id) { return entityManager.find(TrainingPlan.class,id);}

    public void updateTrainingPlan(TrainingPlan trainingPlan) { entityManager.merge(trainingPlan);}

    public void deleteTrainingPlan(TrainingPlan trainingPlan ) {
        entityManager.remove(entityManager.contains(trainingPlan) ? trainingPlan : entityManager.merge(trainingPlan));}

    public List<TrainingPlan> findAllTrainingPlans() {
        return entityManager.createQuery("SELECT t FROM TrainingPlan t").getResultList();
    }
}

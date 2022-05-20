package pl.coderslab.project.dao;

import org.springframework.stereotype.Repository;
import pl.coderslab.project.entities.Exercise;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class ExercisesDao {

    @PersistenceContext
    private EntityManager entityManager;

    public void createExercise(Exercise exercise) {entityManager.persist(exercise);}

    public Exercise readExercise(int id) { return entityManager.find(Exercise.class,id);}

    public void updateExercise(Exercise exercise) { entityManager.merge(exercise);}

    public void deleteExercise(Exercise exercise) {
        entityManager.remove(entityManager.contains(exercise) ? exercise : entityManager.merge(exercise));}

    public List<Exercise> findAllExercises() {
        return entityManager.createQuery("SELECT e FROM Exercise e").getResultList();
    }
}

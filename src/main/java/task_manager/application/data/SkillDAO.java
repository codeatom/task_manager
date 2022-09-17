package task_manager.application.data;

import task_manager.application.model.Skill;

import java.util.List;
import java.util.Optional;

public interface SkillDAO {

    Skill save(Skill skill);

    Optional<Skill> findById(Integer id);

    List<Skill> findAll();

    Skill update(Skill skill);

    boolean delete(Skill skill);
}

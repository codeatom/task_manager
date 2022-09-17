package task_manager.application.data;

import task_manager.application.model.Language;

import java.util.List;
import java.util.Optional;

public interface LanguageDAO {

    Language save(Language language);

    Optional<Language> findById(Integer id);

    List<Language> findAll();

    Language update(Language language);

    boolean delete(Language language);
}


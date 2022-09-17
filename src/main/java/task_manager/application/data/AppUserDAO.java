package task_manager.application.data;

import task_manager.application.model.AppUser;

import java.util.List;
import java.util.Optional;

public interface AppUserDAO {

    AppUser save(AppUser appUser);

    Optional<AppUser> findById(Integer id);

    List<AppUser> findAll();

    AppUser update(AppUser appUser);

    boolean delete(AppUser appUser);
}
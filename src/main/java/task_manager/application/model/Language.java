package task_manager.application.model;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "Language")
public class Language {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "languageName")
    private String languageName;

    @Column(name = "description")
    private String description;

    @Column(name = "languageBranch")
    private String languageBranch;

    @ManyToMany(mappedBy = "languageList")
    private final Set<AppUser> appUserList = new HashSet<>();

    public Language() {
    }

    public Language(String languageName, String description, String languageBranch) {
        this.languageName = languageName;
        this.description = description;
        this.languageBranch = languageBranch;
    }


    public Integer getId() {
        return id;
    }

    public String getLanguageName() {
        return languageName;
    }

    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLanguageBranch() {
        return languageBranch;
    }

    public void setLanguageBranch(String languageBranch) {
        this.languageBranch = languageBranch;
    }

    public Set<AppUser> getAppUserList() {
        return appUserList;
    }

    public void addAppUser(AppUser appUser) {
        if (appUser == null) throw new IllegalArgumentException("appUser is null");

        appUserList.add(appUser);
        appUser.getLanguageList().add(this);
    }

    public void removeAppUser(AppUser appUser) {
        if (appUser == null) throw new IllegalArgumentException("appUser is null");

        appUser.getLanguageList().remove(this);
        appUserList.remove(appUser);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Language language = (Language) o;
        return Objects.equals(id, language.id) && Objects.equals(languageName, language.languageName) && Objects.equals(description, language.description) && Objects.equals(languageBranch, language.languageBranch);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, languageName, description, languageBranch);
    }

    @Override
    public String toString() {
        return "Language{" +
                "id=" + id +
                ", languageName='" + languageName + '\'' +
                ", description='" + description + '\'' +
                ", languageBranch='" + languageBranch + '\'' +
                ", appUserList=" + appUserList +
                '}';
    }
}

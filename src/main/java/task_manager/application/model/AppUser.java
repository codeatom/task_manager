package task_manager.application.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "AppUser")
public class AppUser {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Integer userId;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "birthDate")
    private LocalDate birthDate;

    @Column(name = "active")
    private boolean active = true;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "contactInfo_id")
    private ContactInfo contactInfo;

    @ManyToMany
    @JoinTable(name = "appuser_skill", joinColumns = @JoinColumn(name = "appuser_id"), inverseJoinColumns = @JoinColumn(name = "skill_id"))
    private final Set<Skill> skillList = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "appuser_language", joinColumns = @JoinColumn(name = "appuser_id"), inverseJoinColumns = @JoinColumn(name = "language_id"))
    private final Set<Language> languageList = new HashSet<>();


    public AppUser() {
    }

    public AppUser(String username, String firstName, String lastName, LocalDate birthDate, String password) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.password = password;
    }


    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public boolean isActive() {
        return active;
    }

    public void toggleActive() {
        this.active = !active;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ContactInfo getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(ContactInfo contactInfo) {
        this.contactInfo = contactInfo;
    }

    public Set<Skill> getSkillList() {
        return skillList;
    }

    public Set<Language> getLanguageList() {
        return languageList;
    }

    public void addSkill(Skill skill) {
        if (skill == null) throw new IllegalArgumentException("skill is null");

        skillList.add(skill);
    }

    public void removeSkill(Skill skill) {
        if (skill == null) throw new IllegalArgumentException("skill is null");

        skillList.remove(skill);
    }

    public void addLanguage(Language language) {
        if (language == null) throw new IllegalArgumentException("language is null");

        languageList.add(language);
    }

    public void removeLanguage(Language language) {
        if (language == null) throw new IllegalArgumentException("language is null");

        languageList.remove(language);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppUser appUser = (AppUser) o;
        return active == appUser.active && Objects.equals(userId, appUser.userId) && Objects.equals(username, appUser.username) && Objects.equals(password, appUser.password) && Objects.equals(firstName, appUser.firstName) && Objects.equals(lastName, appUser.lastName) && Objects.equals(birthDate, appUser.birthDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, username, password, firstName, lastName, birthDate, active);
    }

    @Override
    public String toString() {
        return "AppUser{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthDate=" + birthDate +
                ", active=" + active +
                '}';
    }

    // @PreRemove
    // private void removeLanguageFromAppUsers() {
    //    //May be useful for bidirectional ManyToMany
    // }

}

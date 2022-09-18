package task_manager.application.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "SKILL")
public class Skill {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "skillname")
    private String skillName;

    @Column(name = "description")
    private String description;

    public Skill() {
    }

    public Skill(String skillName, String description) {
        this.skillName = skillName;
        this.description = description;
    }


    public Integer getId() {
        return id;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Skill skill = (Skill) o;
        return Objects.equals(id, skill.id) && Objects.equals(skillName, skill.skillName) && Objects.equals(description, skill.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, skillName, description);
    }

    @Override
    public String toString() {
        return "Skill{" +
                "id=" + id +
                ", skillName='" + skillName + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

}


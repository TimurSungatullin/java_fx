package sample;


public class Model {

    private String name;
    private Integer age;
    private Boolean active;
    private String gender;

    public Model() {
    }

    public Model(String name, Integer age, Boolean active, String gender) {
        this.name = name;
        this.age = age;
        this.active = active;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}

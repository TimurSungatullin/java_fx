package sample.model;


public class Model {

    private Long id;
    private String name;
    private Long age;
    private Boolean active;
    private String gender;

    public Model() {
    }

    public Model(Long id, String name, Long age, Boolean active, String gender) {
        this.id = id;
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

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":" + id + "," +
                "\"name\":\"" + name + "\"," +
                "\"age\":" + age + "," +
                "\"active\":" + active + "," +
                "\"gender\":\"" + gender + "\"" +
                '}';
    }
}

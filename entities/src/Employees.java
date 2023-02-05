public class Employees {

    private String name;
    private String email;
    private Double salary;

    public Employees(String name, String email, Double salary) {
        this.name = name;
        this.email = email;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Double getSalary() {
        return salary;
    }

    @Override
    public String toString() {
        return "Nome: "
                + this.getName()
                + ", "
                + "Email: "
                + this.getEmail()
                + ", "
                + String.format("%.2f", this.getSalary());
    }

}

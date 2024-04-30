package pt.ipp.isep.dei.esoft.project.domain.employee;

import java.util.Objects;

public class Employee {
    private final String email;
    private String name;
    private String position;
    private String phone;

    public Employee(String name, String position, String phone, String email) {
        this.name = name;
        this.position = position;
        this.phone = phone;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Employee)) {
            return false;
        }
        Employee employee = (Employee) o;
        return email.equals(employee.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    public boolean hasEmail(String email) {
        return this.email.equals(email);
    }


    /**
     * Clone method.
     *
     * @return A clone of the current instance.
     */
    public Employee clone() {
        return new Employee(this.name,this.position,this.phone,this.email);
    }
}
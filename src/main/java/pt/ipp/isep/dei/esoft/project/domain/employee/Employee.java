package pt.ipp.isep.dei.esoft.project.domain.employee;

import java.util.Objects;

public class Employee {
    private String email;
    private String name;
    private String position;
    private String phone;

    public Employee(String name, String position, String phone, String email) {
        setName(name);
        setPosition(position);
        setPhone(phone);
        setEmail(email);
    }

    public void setName(String name) {
        if (name.split(" ").length<=6){
            this.name=name;
        }else {
            throw new IllegalArgumentException("Name must contain max of 6 characters");
        }
    }

    public void setPhone(String phone) {
        //if (verifyPhone(phone)){
            this.phone=phone;
        /*}else {
            throw new IllegalArgumentException("Phone number is invalid please try again");
        }*/
    }

    private boolean verifyPhone(String phone) {
        return phone.matches("^\\+(?:[0-9] ?){6,14}[0-9]$");
    }

    public void setPosition(String position) {
        this.position = position;
    }
    public void setEmail(String email) {
        if (verifyCollaboratorEmail(email)){
            this.email=email;
        }else {
            throw new IllegalArgumentException("Invalid email");
        }

    }

    private boolean verifyCollaboratorEmail(String email) {
        String[] emailDiv = email.split("@");
        if (emailDiv[1].split("\\.").length ==2 && email.split("@").length==2){
            return true;
        }
        return false;
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
package ar.com.bank.services.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


/***
 * Sample entity 
 */
@Entity
public class Solicitud {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;
    private String lastName;
    private String age;
    private String dni;

    /**
     * DO NOT USE, ONLY FOR HIBERNATE
     * 
     * @deprecated
     */
    public Solicitud() {

    }

    public Solicitud(String name, String lastName, String age, String dni) {
        super();
        this.name = name;
        this.lastName = lastName;
        this.age = age;
        this.dni = dni;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAge() {
        return age;
    }

    public String getDni() {
        return dni;
    }

    public Integer getId() {
        return id;
    }

}

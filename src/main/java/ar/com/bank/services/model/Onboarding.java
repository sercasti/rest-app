package ar.com.bank.services.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Onboarding {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_persona", referencedColumnName = "id")
    private Persona persona;

    /**
     * DO NOT USE, ONLY FOR HIBERNATE
     */
    public Onboarding() {
        super();
    }

    /**
     * @param id
     * @param persona
     */
    public Onboarding(Long id, Persona persona) {
        super();
        this.id = id;
        this.persona = persona;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the persona
     */
    public Persona getPersona() {
        return persona;
    }

    /**
     * @param persona the persona to set
     */
    public void setPersona(Persona persona) {
        this.persona = persona;
    }

}

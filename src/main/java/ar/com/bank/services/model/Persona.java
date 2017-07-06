package ar.com.bank.services.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@ApiModel
public class Persona {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(unique = true)
	private String dni;

	private String firstName;

	private String lastName;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date dateOfBirth;

	private String sexo;

	private String age;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date issuedDateDni;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date expirationDateDni;

	private String pathPicture;

	@ElementCollection
	@CollectionTable(name = "pathsDNI", joinColumns = @JoinColumn(name = "persona_id"))
	@Column(name = "pathDNI")
	private List<String> pathsDNI = new ArrayList<String>();;

	/**
	 * DO NOT USE, ONLY FOR HIBERNATE
	 */
	public Persona() {
		super();
	}

	/**
	 * @param id
	 * @param dni
	 * @param firstName
	 * @param lastName
	 * @param dateOfBirth
	 * @param sexo
	 * @param expirationDateDni
	 * @param pathPicture
	 */
	public Persona(String dni, String firstName, String lastName, Date dateOfBirth, String sexo, Date expirationDateDni,
			String pathPicture) {
		super();
		this.dni = dni;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
		this.sexo = sexo;
		this.expirationDateDni = expirationDateDni;
		this.pathPicture = pathPicture;
	}

	@ApiModelProperty(hidden = true)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ApiModelProperty(position = 1, required = true, value = "DNI")
	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	@ApiModelProperty(position = 2, required = true, value = "firstName")
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@ApiModelProperty(position = 3, required = true, value = "lastName")
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@ApiModelProperty(position = 4, required = true, value = "dateOfBirth")
	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	@ApiModelProperty(position = 5, required = true, value = "sexo")
	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	@ApiModelProperty(position = 6, required = true, value = "expirationDateDni")
	public Date getExpirationDateDni() {
		return expirationDateDni;
	}

	public void setExpirationDateDni(Date expirationDateDni) {
		this.expirationDateDni = expirationDateDni;
	}

	@ApiModelProperty(hidden = true)
	public String getPathPicture() {
		return pathPicture;
	}

	public void setPathPicture(String pathPicture) {
		this.pathPicture = pathPicture;
	}

	@ApiModelProperty(position = 7, required = true, value = "issuedDateDni")
	public Date getIssuedDateDni() {
		return issuedDateDni;
	}

	public void setIssuedDateDni(Date issuedDateDni) {
		this.issuedDateDni = issuedDateDni;
	}

	@ApiModelProperty(position = 8, required = true, value = "age")
	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	@ApiModelProperty(hidden = true)
	public List<String> getPathsDNI() {
		return pathsDNI;
	}

	public void setPathsDNI(List<String> pathsDNI) {
		this.pathsDNI = pathsDNI;
	}

}

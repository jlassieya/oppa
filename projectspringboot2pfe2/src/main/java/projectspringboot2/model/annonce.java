package projectspringboot2.model;



import jakarta.persistence.Entity;

import java.util.Date;

import jakarta.persistence.*;

@Entity
@Table(name="annonce")

public class annonce {
@Id
@GeneratedValue(strategy=GenerationType.IDENTITY)
private int id;
private int id_user;
private String adresse_depart;

private String adresse_destination;
private Date datelimite;
private String imageFileName;



@Column(columnDefinition="TEXT")
private String description;



public String getImageFileName() {
	return imageFileName;
}



public void setImageFileName(String imageFileName) {
	this.imageFileName = imageFileName;
}



public int getId() {
	return id;
}



public void setId(int id) {
	this.id = id;
}



public int getId_user() {
	return id_user;
}



public void setId_user(int id_user) {
	this.id_user = id_user;
}



public String getAdresse_depart() {
	return adresse_depart;
}



public void setAdresse_depart(String adresse_depart) {
	this.adresse_depart = adresse_depart;
}



public String getAdresse_destination() {
	return adresse_destination;
}



public void setAdresse_destination(String adresse_destination) {
	this.adresse_destination = adresse_destination;
}



public Date getDatelimite() {
	return datelimite;
}



public void setDatelimite(Date datelimite) {
	this.datelimite = datelimite;
}



public String getDescription() {
	return description;
}



public void setDescription(String description) {
	this.description = description;
}

}
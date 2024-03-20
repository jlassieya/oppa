package projectspringboot2.model;



import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.*;
public class annonceDto {

   @NotEmpty(message="The adress  depart is required")
    private String adresse_depart ;

    @NotEmpty(message="The adress destination is required")
     private String adresse_destination ;

     @NotEmpty(message="The date limite is required")
      private Date datelimite ;
     

      
         @Size(min=10,message="The description should be at least 10 characters")
      @Size(max=2000, message ="The description cannot exceed 2000 characters")
      private String description ;
         private MultipartFile imageFile;


		public MultipartFile getImageFile() {
			return imageFile;
		}



		public void setImageFile(MultipartFile imageFile) {
			this.imageFile = imageFile;
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
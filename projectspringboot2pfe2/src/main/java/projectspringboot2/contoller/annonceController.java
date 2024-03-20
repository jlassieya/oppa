package projectspringboot2.contoller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import projectspringboot2.model.annonceDto;
import projectspringboot2.model.annonce;
import projectspringboot2.service.annonceRepository;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/annonce")
public class annonceController {

	@Autowired
	private annonceRepository repo;

	@GetMapping({"", "/"})
	public String showProductList(Model model) {
		List<annonce> annonces = repo.findAll(Sort.by(Sort.Direction.DESC, "id"));
		model.addAttribute("annonce", annonces);
		return "annonce/index";
	}

	@PostMapping("/create")
	public String createannonce(@Valid @ModelAttribute annonceDto annonceDto, BindingResult result) {

		if (annonceDto.getImageFile() == null || annonceDto.getImageFile().isEmpty()) {
			result.addError(new FieldError("annonceDto", "imageFile", "The image file is required"));
		}
		if (result.hasErrors()) {
			return "annonce/Createannonce";
		}

		// save image file
		MultipartFile image = annonceDto.getImageFile();
		Date createdAt = new Date();
		String storageFileName = createdAt.getTime() + "_" + image.getOriginalFilename();
		try {
			String uploadDir = "public/images/";
			Path uploadPath = Paths.get(uploadDir);
			if (!Files.exists(uploadPath)) {
				Files.createDirectories(uploadPath);
			}
			try (InputStream inputStream = image.getInputStream()) {
				Files.copy(inputStream, Paths.get(uploadDir + storageFileName),
						StandardCopyOption.REPLACE_EXISTING);
			}
		} catch (IOException ex) {
			System.out.println("Exception:" + ex.getMessage());
		}

		annonce annonce = new annonce();
		annonce.setAdresse_depart(annonceDto.getAdresse_depart());
		annonce.setAdresse_destination(annonceDto.getAdresse_destination());
		annonce.setDatelimite(annonceDto.getDatelimite());
		annonce.setDescription(annonceDto.getDescription());
		annonce.setImageFileName(storageFileName);

		repo.save(annonce);

		return "redirect:/annonce";
	}

	@GetMapping("/edit")
	public String showEditPage(Model model, @RequestParam int id) {
		try {
			annonce annonce = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid annonce Id:" + id));
			model.addAttribute("annonce", annonce);

			annonceDto annonceDto = new annonceDto();
			annonceDto.setDescription(annonce.getDescription());
			annonceDto.setAdresse_depart(annonce.getAdresse_depart());
			annonceDto.setAdresse_destination(annonce.getAdresse_destination());
			annonceDto.setDatelimite(annonce.getDatelimite());
			model.addAttribute("annonceDto", annonceDto);
		} catch (Exception ex) {
			System.out.println("Exception:" + ex.getMessage());
		}

		return "annonce/Editannonce";
	}

	@PostMapping("/edit")
	public String updateannonce(Model model, @RequestParam int id, @Valid @ModelAttribute annonceDto annonceDto, BindingResult result) {
		try {
			if (result.hasErrors()) {
				return "annonce/Editannonce";
			}
			annonce existingAnnonce = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid annonce Id:" + id));
			existingAnnonce.setDescription(annonceDto.getDescription());
			existingAnnonce.setAdresse_depart(annonceDto.getAdresse_depart());
			existingAnnonce.setAdresse_destination(annonceDto.getAdresse_destination());
			existingAnnonce.setDatelimite(annonceDto.getDatelimite());
			repo.save(existingAnnonce);
		} catch (Exception ex) {
			System.out.println("Exception:" + ex.getMessage());
		}
		return "redirect:/annonce";
	}

	@GetMapping("/delete")
	public String deleteannonce(@RequestParam int id) {
		try {
			annonce annonce = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid annonce Id:" + id));
			repo.delete(annonce);
		} catch (Exception ex) {
			System.out.println("Exception:" + ex.getMessage());
		}
		return "redirect:/annonce";
	}
}

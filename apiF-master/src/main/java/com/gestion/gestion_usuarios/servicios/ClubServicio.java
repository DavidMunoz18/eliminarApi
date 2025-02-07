package com.gestion.gestion_usuarios.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.gestion.gestion_usuarios.daos.ClubDao;
import com.gestion.gestion_usuarios.dtos.RegistroClubDto;
import com.gestion.gestion_usuarios.repositorios.ClubRepository;

@Service
public class ClubServicio {

	@Autowired
	private ClubRepository clubRepository; // Inyectamos el repositorio
	@Autowired
	private PasswordEncoder passwordEncoder;

	// Metodo que valida las credenciales del club
	public ResponseEntity<String> validarCredenciales(String emailClub, String passwordClub) {
		ClubDao club = clubRepository.findByEmailClub(emailClub);

		if (club == null) {
			System.out.println("Club no encontrado para el email: " + emailClub);
			return ResponseEntity.status(401).body("Credenciales incorrectas");
		}

		// Comparar la contraseña ingresada (texto plano) con la almacenada (encriptada)
        if (!passwordEncoder.matches(passwordClub, club.getPasswordClub())) {
            System.out.println("Contraseña incorrecta");
            return ResponseEntity.status(401).body("Credenciales incorrectas");
        }

		return ResponseEntity.ok("club");
	}

	// Metodo que se asegura si el correo ya se encuentra registrado
	public boolean emailExistsClub(String emailCLub) {
		return clubRepository.existsByEmailClub(emailCLub);
	}

	// Método para registrar un nuevo club
	public void registroClub(RegistroClubDto clubDto) {
		ClubDao club = new ClubDao();
		club.setNombreClub(clubDto.getNombreClub());
		club.setEmailClub(clubDto.getEmailClub());
		club.setSedeClub(clubDto.getSedeClub());
		club.setPasswordClub(passwordEncoder.encode(clubDto.getPasswordClub()));// Encripta la contraseña antes de
																				// guardarla

		clubRepository.save(club); // Guardar el nuevo club en la base de datos
	}
	public boolean borrarClub(Long idClub) {
        try {
            if (clubRepository.existsById(idClub)) {
                clubRepository.deleteById(idClub); // Elimina el club por ID
                return true;
            } else {
                return false; // Si no existe el club, retorna false
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar el club: " + e.getMessage());
        }
    }

}
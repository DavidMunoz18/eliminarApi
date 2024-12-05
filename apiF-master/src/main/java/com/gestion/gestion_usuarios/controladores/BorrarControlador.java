package com.gestion.gestion_usuarios.controladores;

import com.gestion.gestion_usuarios.servicios.UsuarioServicio;
import com.gestion.gestion_usuarios.servicios.ClubServicio; // Asumiendo que existe un servicio de Club
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/gestionEliminar")
public class BorrarControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Autowired
    private ClubServicio clubServicio; // Asumimos que existe un servicio para manejar clubes

    // Eliminar Usuario
    @DeleteMapping("/usuario/{id}")
    public ResponseEntity<String> borrarUsuario(@PathVariable("id") Long idUsuario) {
        try {
            boolean eliminado = usuarioServicio.borrarUsuario(idUsuario);
            if (eliminado) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Usuario eliminado exitosamente.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar el usuario: " + e.getMessage());
        }
    }

    // Eliminar Club
    @DeleteMapping("/club/{id}")
    public ResponseEntity<String> borrarClub(@PathVariable("id") Long idClub) {
        try {
            boolean eliminado = clubServicio.borrarClub(idClub); // Se asume que tienes este método en ClubServicio
            if (eliminado) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Club eliminado exitosamente.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Club no encontrado.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar el club: " + e.getMessage());
        }
    }
}

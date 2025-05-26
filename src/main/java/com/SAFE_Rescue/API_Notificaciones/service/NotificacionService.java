package com.SAFE_Rescue.API_Notificaciones.service;

import com.SAFE_Rescue.API_Notificaciones.modelo.Notificacion;
import com.SAFE_Rescue.API_Notificaciones.repository.NotificacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class NotificacionService {

    private final NotificacionRepository notificacionRepository;

    @Autowired
    public NotificacionService(NotificacionRepository notificacionRepository) {
        this.notificacionRepository = notificacionRepository;
    }

    @Transactional
    public Notificacion crearNotificacion(Notificacion notificacion) {
        if (notificacion.getReceptores() == null || notificacion.getReceptores().isEmpty()) {
            throw new IllegalArgumentException("La lista de receptores no puede estar vacía al crear.");
        }
        notificacion.setFechaNotificacion(LocalDateTime.now());
        notificacion.setEstadoNotificacion(true);
        return notificacionRepository.save(notificacion);
    }

    public List<Notificacion> obtenerTodasLasNotificaciones() {
        return notificacionRepository.findAll();
    }

    public Optional<Notificacion> obtenerNotificacionPorId(int id) {
        return notificacionRepository.findById(id);
    }

    @Transactional
    public Notificacion actualizarNotificacion(int idNotificacion, Notificacion datosActualizados) {
        Notificacion notificacionExistente = notificacionRepository.findById(idNotificacion)
                .orElseThrow(() -> new RuntimeException("Notificación no encontrada con ID: " + idNotificacion));

        if (datosActualizados.getTituloNotificacion() != null) {
            if (datosActualizados.getTituloNotificacion().isBlank()) {
                throw new IllegalArgumentException("El título no puede estar en blanco.");
            }
            if (datosActualizados.getTituloNotificacion().length() > 50) {
                throw new IllegalArgumentException("El título debe tener entre 1 y 50 caracteres.");
            }
            notificacionExistente.setTituloNotificacion(datosActualizados.getTituloNotificacion());
        }

        if (datosActualizados.getContenidoNotificacion() != null) {
            if (datosActualizados.getContenidoNotificacion().isBlank()) {
                throw new IllegalArgumentException("El contenido no puede estar en blanco.");
            }
            if (datosActualizados.getContenidoNotificacion().length() > 500) {
                throw new IllegalArgumentException("El contenido debe tener entre 1 y 500 caracteres.");
            }
            notificacionExistente.setContenidoNotificacion(datosActualizados.getContenidoNotificacion());
        }

        if (datosActualizados.getEstadoNotificacion() != null) {
            notificacionExistente.setEstadoNotificacion(datosActualizados.getEstadoNotificacion());
        }

        if (datosActualizados.getReceptores() != null && !datosActualizados.getReceptores().isEmpty()) {
            notificacionExistente.setReceptores(datosActualizados.getReceptores());
        }
        else if (datosActualizados.getReceptores() != null && datosActualizados.getReceptores().isEmpty()) {
            throw new IllegalArgumentException("La lista de receptores no puede quedar vacía al actualizar.");
        }

        return notificacionRepository.save(notificacionExistente);
    }

    @Transactional
    public void eliminarNotificacion(int id) {
        if (!notificacionRepository.existsById(id)) {
            throw new RuntimeException("Notificación no encontrada con ID: " + id + " para eliminar.");
        }
        notificacionRepository.deleteById(id);
    }
}
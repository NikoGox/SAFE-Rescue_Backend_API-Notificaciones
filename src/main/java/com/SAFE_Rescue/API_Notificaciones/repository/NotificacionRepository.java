package com.SAFE_Rescue.API_Notificaciones.repository;

import com.SAFE_Rescue.API_Notificaciones.modelo.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificacionRepository extends JpaRepository<Notificacion, Integer> {
}
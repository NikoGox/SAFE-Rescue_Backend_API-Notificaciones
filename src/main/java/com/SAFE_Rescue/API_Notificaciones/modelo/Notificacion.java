package com.SAFE_Rescue.API_Notificaciones.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "notificacion_emergencia")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_notificacion")
    private int idNotificacion;

    @NotNull(message = "El ID del emisor no puede ser nulo")
    @Column(name = "id_emisor", nullable = false)
    private Integer idEmisor;

    @NotBlank(message = "El título no puede estar en blanco")
    @Size(min = 1, max = 50, message = "El título debe tener entre 1 y 50 caracteres")
    @Column(name = "titulo_notificacion", length = 50, nullable = false)
    private String tituloNotificacion;

    @NotBlank(message = "El contenido no puede estar en blanco")
    @Size(min = 1, max = 500, message = "El contenido debe tener entre 1 y 500 caracteres")
    @Column(name = "contenido_notificacion", length = 500, nullable = false)
    private String contenidoNotificacion;

    @Column(name = "fecha_notificacion", nullable = false)
    private LocalDateTime fechaNotificacion;

    @Column(name = "estado_notificacion", nullable = false)
    private Boolean estadoNotificacion;

    @NotEmpty(message = "La lista de receptores no puede estar vacía")
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "notificacion_receptores", joinColumns = @JoinColumn(name = "id_notificacion"))
    @Column(name = "id_receptor")
    private List<Integer> receptores;

    public Notificacion(Integer idEmisor, String tituloNotificacion, String contenidoNotificacion, List<Integer> receptores) {
        this.idEmisor = idEmisor;
        this.tituloNotificacion = tituloNotificacion;
        this.contenidoNotificacion = contenidoNotificacion;
        this.fechaNotificacion = LocalDateTime.now();
        this.estadoNotificacion = true;
        this.receptores = receptores;
    }


}
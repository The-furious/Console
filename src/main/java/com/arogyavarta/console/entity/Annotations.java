package com.arogyavarta.console.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "Annotations")
public class Annotations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "annotation_id")
    private Long annotationId;

    @ManyToOne
    @JoinColumn(name = "radiologist_id")
    private Radiologist radiologist;

    @ManyToOne
    @JoinColumn(name = "image_id")
    private Images image;

    @Column(name = "impression_text")
    private String impressionText;

    @Column(name = "annotation_date")
    private LocalDateTime annotationDate;

    @Column(name = "edited_image_url")
    private String editedImageUrl;
}
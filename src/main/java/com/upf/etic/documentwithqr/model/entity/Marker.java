package com.upf.etic.documentwithqr.model.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="markers")
public class Marker implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "descripcion", length = 500)
    private String descripcion;

    @Column(name = "link")
    private String link;

    @Column(name = "tipo")
    private String tipo;

    @Column(name = "latitud")
    private float latitud;

    @Column(name = "longitud")
    private float longitud;

    @Column(name = "image_path")
    private String imagePath;

    @Column(name = "creation_date")
    @Temporal(TemporalType.DATE)
    private Date creationDate;

    public Long getId(){
        return id;
    }

    public String getTitulo(){
        return titulo;
    }

    public String getDescripcion(){
        return descripcion;
    }

    public String getLink(){
        return link;
    }

    public String getTipo(){
        return tipo;
    }

    public float getLatitud(){
        return latitud;
    }

    public float getLongitud(){
        return longitud;
    }

    public String getImagePath(){
        return imagePath;
    }

    public Date getCreationDate(){
        return creationDate;
    }

    public void setId(Long id){
        this.id = id;
    }

    public void setTitulo(String titulo){
        this.titulo = titulo;
    }

    public void setDescripcion(String descripcion){
        this.descripcion = descripcion;
    }

    public void setLink(String link){
        this.link = link;
    }

    public void setTipo(String tipo){
        this.tipo = tipo;
    }

    public void setLatitud(float latitud){
        this.latitud = latitud;
    }

    public void setLongitud(float longitud){
        this.longitud = longitud;
    }

    public void setImagePath(String imagePath){
        this.imagePath = imagePath;
    }

    public void setCreationDate(Date creationDate){
        this.creationDate = creationDate;
    }

}
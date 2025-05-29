package com.ucam.flexicoche.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.ucam.flexicoche.model.Estado;

@Converter(autoApply = true)
public class EstadoConverter implements AttributeConverter<Estado, String> {
 
    @Override
    public String convertToDatabaseColumn(Estado estado) {
        return estado.getNombre();
    }
 
    @Override
    public Estado convertToEntityAttribute(String dbData) {
        return Estado.fromNombre(dbData);
    }
 
}
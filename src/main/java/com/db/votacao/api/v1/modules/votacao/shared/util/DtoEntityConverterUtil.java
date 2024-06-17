package com.db.votacao.api.v1.modules.votacao.shared.util;


import lombok.experimental.UtilityClass;
import org.springframework.beans.BeanUtils;

@UtilityClass
public class DtoEntityConverterUtil {

    public static <D, E> E convertToEntity(D dto, Class<E> entityClass) throws Exception {
        E convertedEntity = null;
        try {
            convertedEntity = entityClass.getDeclaredConstructor().newInstance();
            BeanUtils.copyProperties(dto, convertedEntity);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return convertedEntity;
    }

    public static <D, E> D convertToDto(E entity, Class<D> dtoClass) throws Exception {
        D convertedDto = null;
        try {
            convertedDto = dtoClass.getDeclaredConstructor().newInstance();
            BeanUtils.copyProperties(entity, convertedDto);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return convertedDto;
    }
}
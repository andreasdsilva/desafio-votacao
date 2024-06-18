package com.db.votacao.api.v1.modules.votacao.shared.util;


import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

@Slf4j
@UtilityClass
public class DtoEntityConverterUtil {

    public static <D, E> E convertToEntity(D dto, Class<E> entityClass) throws Exception {
        E convertedEntity = null;
        try {
            convertedEntity = entityClass.getDeclaredConstructor().newInstance();
            BeanUtils.copyProperties(dto, convertedEntity);
        } catch (Exception e) {
            log.error("Falha ao converter dto para entidade!", e);
            throw new Exception("Falha ao converter dto para entidade!", e);
        }
        return convertedEntity;
    }

    public static <D, E> D convertToDto(E entity, Class<D> dtoClass) throws Exception {
        D convertedDto = null;
        try {
            convertedDto = dtoClass.getDeclaredConstructor().newInstance();
            BeanUtils.copyProperties(entity, convertedDto);
        } catch (Exception e) {
            log.error("Falha ao converter entidade para dto!", e);
            throw new Exception("Falha ao entidade para dto!", e);
        }
        return convertedDto;
    }
}
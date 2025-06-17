package com.dw.credito.mapper;


import com.dw.credito.dto.CreditoDTO;
import com.dw.credito.dto.CreditoRequestDTO;
import com.dw.credito.dto.CreditoResponseDTO;
import com.dw.credito.model.Credito;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring")
public interface CreditoMapper {

    CreditoMapper INSTANCE = Mappers.getMapper(CreditoMapper.class);

    CreditoDTO toDTO(Credito credito);

    @Mapping(target = "id", ignore = true)
    Credito toEntity(CreditoDTO creditoDTO);

    @Mapping(target = "id", ignore = true)
    Credito toEntity(CreditoRequestDTO creditoRequestDTO);

    @Mapping(target = "simplesNacional", source = "simplesNacional", qualifiedByName = "mapSimplesNacional")
    @Mapping(target = "percentualDeducao", expression = "java(credito.getValorDeducao().divide(credito.getValorFaturado(), 4, java.math.RoundingMode.HALF_UP).multiply(new java.math.BigDecimal(\"100\")))")
    @Mapping(target = "valorLiquido", expression = "java(credito.getValorFaturado().subtract(credito.getValorDeducao()))")
    CreditoResponseDTO toResponseDTO(Credito credito);

    @Named("mapSimplesNacional")
    default String mapSimplesNacional(boolean simplesNacional) {
        return simplesNacional ? "Sim" : "Não";
    }
}
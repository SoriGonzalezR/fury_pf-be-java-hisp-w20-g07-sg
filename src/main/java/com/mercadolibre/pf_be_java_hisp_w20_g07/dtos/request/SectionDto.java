package com.mercadolibre.pf_be_java_hisp_w20_g07.dtos.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class SectionDto {

    private int sectionCode;
    private int warehouseCode;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SectionDto that = (SectionDto) o;
        return sectionCode == that.sectionCode && warehouseCode == that.warehouseCode;
    }

    @Override
    public int hashCode() {
        return Objects.hash(sectionCode, warehouseCode);
    }
}


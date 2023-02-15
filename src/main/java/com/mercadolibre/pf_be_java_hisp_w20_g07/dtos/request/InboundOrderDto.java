package com.mercadolibre.pf_be_java_hisp_w20_g07.dtos.request;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.mercadolibre.pf_be_java_hisp_w20_g07.dtos.BatchDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class InboundOrderDto {

    @NotNull(message = "id cannot be null")
    @JsonAlias("order_number")
    @Min(value = 1,message = "id must be greater than 0")
    private Integer id;

    @NotNull(message = "order_date cannot be null")
    @JsonAlias("order_date")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate date;

    @NotNull(message = "section must not be null")
    @Valid
    private SectionDto section;

    @NotEmpty(message = "batch_stock cannot be empty")
    private List<@Valid BatchDto> batchStock;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InboundOrderDto that = (InboundOrderDto) o;
        return id == that.id && Objects.equals(date, that.date) && Objects.equals(section, that.section) && Objects.equals(batchStock, that.batchStock);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, section, batchStock);
    }
}






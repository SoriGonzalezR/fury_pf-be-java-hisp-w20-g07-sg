package com.mercadolibre.pf_be_java_hisp_w20_g07.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@ToString
@EqualsAndHashCode
public class BatchDto {



    private Integer batchNumber;

    private int productId;

    private double currentTemperature;

    private double minimumTemperature;
    private int initialQuantity;
    private int currentQuantity;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate manufacturingDate;

    //@Temporal(TemporalType.TIME)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    //@DateTimeFormat(style = "dd-MM-yyyy HH:mm:ss")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy HH:mm:ss")
    private LocalDateTime manufacturingTime;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate dueDate;



    //private Section section;
    //private InboundOrder inboundOrder;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BatchDto batchDto = (BatchDto) o;
        return productId == batchDto.productId && Double.compare(batchDto.currentTemperature, currentTemperature) == 0 && Double.compare(batchDto.minimumTemperature, minimumTemperature) == 0 && initialQuantity == batchDto.initialQuantity && currentQuantity == batchDto.currentQuantity && Objects.equals(batchNumber, batchDto.batchNumber) && Objects.equals(manufacturingDate, batchDto.manufacturingDate) && Objects.equals(manufacturingTime, batchDto.manufacturingTime) && Objects.equals(dueDate, batchDto.dueDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(batchNumber, productId, currentTemperature, minimumTemperature, initialQuantity, currentQuantity, manufacturingDate, manufacturingTime, dueDate);
    }

    @Override
    public String toString() {
        return "BatchDto{" +
                "batchNumber=" + batchNumber +
                ", productId=" + productId +
                ", currentTemperature=" + currentTemperature +
                ", minimumTemperature=" + minimumTemperature +
                ", initialQuantity=" + initialQuantity +
                ", currentQuantity=" + currentQuantity +
                ", manufacturingDate=" + manufacturingDate +
                ", manufacturingTime=" + manufacturingTime +
                ", dueDate=" + dueDate +
                '}';
    }

}

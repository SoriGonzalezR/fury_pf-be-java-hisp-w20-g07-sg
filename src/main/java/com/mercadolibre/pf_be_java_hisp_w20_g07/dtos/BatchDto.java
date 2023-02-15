package com.mercadolibre.pf_be_java_hisp_w20_g07.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@ToString
@EqualsAndHashCode
public class BatchDto {


    @NotNull(message = "batch_number cannot be null")
    @Min(value = 1, message = "batch_number cannot be lower than 1")
    private Integer batchNumber;

    @NotNull(message = "product_id cannot be null")
    @Min(value = 1 , message = "product_id cannot be lower than 1")
    private Integer productId;

    @NotNull(message = "current_temperature cannot be null")
    private Double currentTemperature;

    @NotNull(message = "minimum_temperature cannot be null")
    private Double minimumTemperature;

    @NotNull(message = "initial_quantity cannot be null")
    @Min(value = 1,message = "initial_quantity cannot be lower than 1")
    private Integer initialQuantity;
    @NotNull(message = "current_quantity cannot be null")
    @Min(value = 0 , message = "current_quantity cannot be lower than 0")
    private Integer currentQuantity;

    @NotNull(message = "manufacturing_date cannot be null")
    @Past(message = "manufacturing_date must be a past date")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate manufacturingDate;

    @NotNull(message = "manufacturing_time cannot be null")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy HH:mm:ss")
    private LocalDateTime manufacturingTime;

    @NotNull(message = "due_date cannot be null")
    @Future(message = "due_date must be a future date")
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
        return Objects.equals(batchNumber, batchDto.batchNumber) && Objects.equals(productId, batchDto.productId) && Objects.equals(currentTemperature, batchDto.currentTemperature) && Objects.equals(minimumTemperature, batchDto.minimumTemperature) && Objects.equals(initialQuantity, batchDto.initialQuantity) && Objects.equals(currentQuantity, batchDto.currentQuantity) && Objects.equals(manufacturingDate, batchDto.manufacturingDate) && Objects.equals(manufacturingTime, batchDto.manufacturingTime) && Objects.equals(dueDate, batchDto.dueDate);
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

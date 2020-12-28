package com.movies.rest.dto;

import com.movies.rest.validations.annotation.NullOrNotBlank;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "Create movie Request", description = "Payload to add a movie")
public class CreateMovieRequest {

    @NullOrNotBlank
    @ApiModelProperty(value = "A valid title", allowableValues = "NonEmpty String", allowEmptyValue = false)
    private String title;

    @NullOrNotBlank
    @ApiModelProperty(value = "A valid description", allowableValues = "NonEmpty String", allowEmptyValue = false)
    private String description;

    @Min(0)
    @ApiModelProperty(value = "A valid stock", allowableValues = "Number greater or equal 0")
    private Long stock;

    @DecimalMin("0.00")
    @ApiModelProperty(value = "A valid stock", allowableValues = "Number greater or equal 0")
    private BigDecimal rentalPrice;

    @DecimalMin("0.00")
    @ApiModelProperty(value = "A valid stock", allowableValues = "Number greater or equal 0")
    private BigDecimal salePrice;

    private boolean availability;
}

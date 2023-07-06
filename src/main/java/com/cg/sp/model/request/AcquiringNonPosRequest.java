package com.cg.sp.model.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AcquiringNonPosRequest {
    @NotNull(message = "CrNumber can't be Null")
    @Min(value = 1000000000L,message = "crNumber must be equal to 10 digits")
    @Max(value = 9999999999L,message = "crNumber must be equal to 10 digits")
    private Long crNumber;

    @Column(nullable = false)
    @NotNull(message = "startDate can't be null")
    @NotBlank(message = "startDate can't be blank")
    private String startDate;

    //    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(nullable = false)
    @NotNull(message = "endDate can't be null")
    @NotBlank(message = "endDate can't be blank")
    private String endDate;

    @NotNull(message = "User Profile can't be null")
    private UserProfile userProfile;

}

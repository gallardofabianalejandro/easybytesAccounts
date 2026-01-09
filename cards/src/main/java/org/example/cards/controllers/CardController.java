package org.example.cards.controllers;

import com.openapi.autoconfigure.openapi.ApiAnnotations;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.example.cards.constants.CardsConstants;
import org.example.cards.dto.CardsDto;
import org.example.cards.dto.ResponseDto;
import org.example.cards.services.ICardsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static org.example.cards.constants.CardsConstants.MESSAGE_201;
import static org.example.cards.constants.CardsConstants.STATUS_201;

@RestController
@RequestMapping(path = "/api/cards", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Validated
@Tag(
    name = "Cards Management",
    description = "Complete card management APIs for creating, retrieving, updating and deleting customer cards. " +
                  "Supports credit, debit, prepaid and business cards with comprehensive financial tracking and limits."
)
public class CardController {

    private final ICardsService iCardsService;

    @Operation(
        summary = "Create a new customer card",
        description = "Creates a new card for an existing customer identified by their mobile number. " +
                     "The system will generate a unique card number and set up the card with default limits.",
        operationId = "createCard"
    )
    @ApiResponse(
        responseCode = "201",
        description = "Card created successfully",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = ResponseDto.class),
            examples = {
                @io.swagger.v3.oas.annotations.media.ExampleObject(
                    name = "Card Created",
                    summary = "Successful card creation",
                    value = """
                    {
                      "statusCode": "201",
                      "statusMsg": "Card created successfully"
                    }
                    """
                )
            }
        )
    )
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createCard(
        @ApiAnnotations.MobileNumberParam
        @RequestParam
        @Pattern(regexp = "^\\d{10}$", message = "Mobile number must be exactly 10 digits")
        String mobileNumber
    ) {
        iCardsService.createCard(mobileNumber);
        return ResponseEntity.status(HttpStatus.CREATED)
                           .body(new ResponseDto(STATUS_201, MESSAGE_201));
    }

    @Operation(
        summary = "Get customer card details",
        description = "Retrieves complete card information for a customer using their mobile number. " +
                     "Returns card details including limits, usage and available balance.",
        operationId = "getCardDetails"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Card details retrieved successfully",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = CardsDto.class),
            examples = {
                @io.swagger.v3.oas.annotations.media.ExampleObject(
                    name = "Card Details",
                    summary = "Complete card information",
                    value = """
                    {
                      "mobileNumber": "1234567890",
                      "cardNumber": "4532015112830366",
                      "cardType": "CREDIT",
                      "totalLimit": 50000,
                      "amountUsed": 15000,
                      "availableAmount": 35000
                    }
                    """
                )
            }
        )
    )
    @ApiAnnotations.StandardApiResponses
    @GetMapping("/fetch")
    public ResponseEntity<CardsDto> fetchCardDetails(
        @ApiAnnotations.MobileNumberParam
        @RequestParam
        @Pattern(regexp = "^\\d{10}$", message = "Mobile number must be exactly 10 digits")
        String mobileNumber
    ) {
        CardsDto cardsDto = iCardsService.fetchCard(mobileNumber);
        return ResponseEntity.ok(cardsDto);
    }

    @Operation(
        summary = "Update customer card details",
        description = "Updates existing card information including limits and status. " +
                     "Allows modification of credit limits and card parameters.",
        operationId = "updateCard"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Card updated successfully",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = ResponseDto.class),
            examples = {
                @io.swagger.v3.oas.annotations.media.ExampleObject(
                    name = "Card Updated",
                    summary = "Successful card update",
                    value = """
                    {
                      "statusCode": "200",
                      "statusMsg": "Card updated successfully"
                    }
                    """
                )
            }
        )
    )
    @ApiResponse(
        responseCode = "417",
        description = "Card update failed",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = ResponseDto.class),
            examples = {
                @io.swagger.v3.oas.annotations.media.ExampleObject(
                    name = "Update Failed",
                    summary = "Card update operation failed",
                    value = """
                    {
                      "statusCode": "417",
                      "statusMsg": "Card update failed"
                    }
                    """
                )
            }
        )
    )
    @ApiAnnotations.UpdateApiResponses
    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateCardDetails(
        @io.swagger.v3.oas.annotations.Parameter(
            description = "Complete card information to update",
            required = true,
            schema = @Schema(implementation = CardsDto.class)
        )
        @Valid @RequestBody CardsDto cardsDto
    ) {
        boolean isUpdated = iCardsService.updateCard(cardsDto);
        if (isUpdated) {
            return ResponseEntity.ok()
                               .body(new ResponseDto(CardsConstants.STATUS_200, CardsConstants.MESSAGE_200));
        } else {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                               .body(new ResponseDto(CardsConstants.STATUS_417, CardsConstants.MESSAGE_417_UPDATE));
        }
    }

    @Operation(
        summary = "Delete customer card",
        description = "Deletes a customer's card record from the system. " +
                     "This operation removes all card information and transaction history.",
        operationId = "deleteCard"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Card deleted successfully",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = ResponseDto.class),
            examples = {
                @io.swagger.v3.oas.annotations.media.ExampleObject(
                    name = "Card Deleted",
                    summary = "Successful card deletion",
                    value = """
                    {
                      "statusCode": "200",
                      "statusMsg": "Card deleted successfully"
                    }
                    """
                )
            }
        )
    )
    @ApiResponse(
        responseCode = "417",
        description = "Card deletion failed",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = ResponseDto.class),
            examples = {
                @io.swagger.v3.oas.annotations.media.ExampleObject(
                    name = "Deletion Failed",
                    summary = "Card deletion operation failed",
                    value = """
                    {
                      "statusCode": "417",
                      "statusMsg": "Card deletion failed"
                    }
                    """
                )
            }
        )
    )
    @ApiAnnotations.DeleteApiResponses
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteCardDetails(
        @ApiAnnotations.MobileNumberParam
        @RequestParam
        @Pattern(regexp = "^\\d{10}$", message = "Mobile number must be exactly 10 digits")
        String mobileNumber
    ) {
        boolean isDeleted = iCardsService.deleteCard(mobileNumber);
        if (isDeleted) {
            return ResponseEntity.ok()
                               .body(new ResponseDto(CardsConstants.STATUS_200, CardsConstants.MESSAGE_200));
        } else {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                               .body(new ResponseDto(CardsConstants.STATUS_417, CardsConstants.MESSAGE_417_DELETE));
        }
    }
}

package com.sockshop.sockshopcoursework3.controllers;

import com.sockshop.sockshopcoursework3.model.ColorSocks;
import com.sockshop.sockshopcoursework3.model.SizeSocks;
import com.sockshop.sockshopcoursework3.model.Socks;
import com.sockshop.sockshopcoursework3.service.SocksService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/socks")
@Tag(name = "Носки", description = "CRUD-операции для работы с носками")
public class SocksController {

    public SocksService socksService;

    public SocksController(SocksService socksService) {
        this.socksService = socksService;
    }

    @PostMapping
    @Operation(
            summary = "Поступление новых носков",
            description = "Регистрация товара на склад"
    ) @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Носочки успешно зарегистрированы!",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema =
                                    @Schema(implementation = Socks.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Неверный запрос. Неправильный/отсутствует параметр запроса",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema =
                                    @Schema(implementation = Socks.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Внутренняя ошибка сервера",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema =
                                    @Schema(implementation = Socks.class))
                            )
                    }
            )

    })
    public ResponseEntity<Socks> addSocks(@RequestBody Socks socks, @RequestParam long quantity) {
        Socks addSocks = socksService.addSocks(socks, quantity);
        return ResponseEntity.ok(addSocks);
    }

    @GetMapping
    @Operation(
            summary = "Общее количество носков",
            description = "Показать количество носков по параметру"
    ) @Parameters(
            value = {
                    @Parameter(
                            name = "Мин % хлопка", example = "0"
                    ),
                    @Parameter(
                            name = "Макс % хлопка", example = "100"
                    )
            }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Носочки успешно найдены!",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema =
                                    @Schema(implementation = Socks.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Неверный запрос. Неправильный/отсутствует параметр запроса",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema =
                                    @Schema(implementation = Socks.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Внутренняя ошибка сервера",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema =
                                    @Schema(implementation = Socks.class))
                            )
                    }
            )
    })
    public ResponseEntity<Long> getSocksByParam(@RequestParam ColorSocks color,
                                                @RequestParam SizeSocks size,
                                                @RequestParam int cottonMin,
                                                @RequestParam int cottonMax) {
        long count = socksService.getSocksNumByParam(color, size, cottonMin, cottonMax);
        return ResponseEntity.ok(count);
    }



    @PutMapping
    @Operation(
            summary = "Продажа носочков",
            description = "Продать носочки со склада"
    )@Parameters(value = {
            @Parameter(name = "кол-во", example = "1")
    })
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Носочки успешно проданы!",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Socks.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Товара нет на складе в нужном количестве или параметры запроса имеют некорректный формат",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema =
                                    @Schema(implementation = Socks.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Внутренняя ошибка сервера",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema =
                                    @Schema(implementation = Socks.class))
                            )
                    }
            )
    })
    public ResponseEntity<Socks> putSocks(@RequestBody Socks socks, @RequestParam long quantity) {
        Socks getSocks = socksService.editSocks(socks, quantity);
        if (getSocks == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(getSocks);
    }

    @DeleteMapping
    @Operation(
            summary = "Списание бракованных носков",
            description = "Регистрация бракованных носокв"
    ) @Parameters(value = {
            @Parameter(name = "кол-во", example = "1")
    })
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Дефектные носочки списаны со склада",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Socks.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Отсутствуют носки или отсутствуют параметры запроса или имеют некорректный формат",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema =
                                    @Schema(implementation = Socks.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Внутренняя ошибка сервера",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema =
                                    @Schema(implementation = Socks.class))
                            )
                    }
            )
    })
    public ResponseEntity<Void> deleteSocks(@RequestBody Socks socks,
                                            @RequestParam long quantity) {
        if (socksService.deleteSocks(socks, quantity)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }





}

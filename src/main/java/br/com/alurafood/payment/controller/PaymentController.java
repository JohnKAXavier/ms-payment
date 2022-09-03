package br.com.alurafood.payment.controller;

import br.com.alurafood.payment.dto.PaymentDto;
import br.com.alurafood.payment.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentService service;

    @GetMapping
    public Page<PaymentDto> getAll(@PageableDefault(size = 10) Pageable pageable){
        return service.getAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentDto> getById(@PathVariable @NotNull Long id){
        PaymentDto dto = service.getById(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<PaymentDto> create(@RequestBody @Valid PaymentDto dto, UriComponentsBuilder uriComponentsBuilder){
        PaymentDto paymentDto = service.create(dto);
        URI uri = uriComponentsBuilder.path("/payments/{id}").buildAndExpand(paymentDto.getId()).toUri();

        return ResponseEntity.created(uri).body(paymentDto);
    }

    @PutMapping
    public ResponseEntity<PaymentDto> update(@PathVariable @NotNull Long id, @RequestBody @Valid PaymentDto dto){
        PaymentDto paymentDto = service.update(id, dto);
        return ResponseEntity.ok(paymentDto);
    }

    @DeleteMapping
    public ResponseEntity<PaymentDto> delete(@PathVariable @NotNull Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

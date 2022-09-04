package br.com.alurafood.payment.service;

import br.com.alurafood.payment.dto.PaymentDto;
import br.com.alurafood.payment.model.Payment;
import br.com.alurafood.payment.model.Status;
import br.com.alurafood.payment.repository.PaymentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository repository;

    @Autowired
    private ModelMapper mapper;

    public Page<PaymentDto> getAll(Pageable pageable){
        return repository.findAll(pageable).map(payment -> mapper.map(payment, PaymentDto.class));
    }

    public PaymentDto getById(Long id){
        Payment payment = repository.findById(id).orElseThrow(() -> new EntityNotFoundException());
        return mapper.map(payment, PaymentDto.class);
    }

    public PaymentDto create(PaymentDto dto){
        Payment payment = mapper.map(dto, Payment.class);
        payment.setStatus(Status.CRIADO);
        repository.save(payment);
        return mapper.map(payment, PaymentDto.class);
    }

    public PaymentDto update(Long id, PaymentDto dto){
        Payment payment = mapper.map(dto, Payment.class);
        payment.setId(id);
        payment = repository.save(payment);
        return mapper.map(payment, PaymentDto.class);
    }

    public void delete(Long id){
        repository.deleteById(id);
    }
}

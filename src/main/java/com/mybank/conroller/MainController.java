package com.mybank.conroller;

import com.mybank.DTO.BalanceDTO;
import com.mybank.entity.Deposit;
import com.mybank.entity.User;
import com.mybank.service.DepositService;
import com.mybank.service.DepositTypeService;
import com.mybank.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MainController {

    private final UserService userService;

    private final DepositService depositService;

    private final DepositTypeService depositTypeService;

    @Autowired
    public MainController(UserService userService, DepositService depositService, DepositTypeService depositTypeService) {
        this.userService = userService;
        this.depositService = depositService;
        this.depositTypeService = depositTypeService;
    }

    @RequestMapping("create-account")
    @PostMapping
    public ResponseEntity<Long> createAccount(@RequestParam String name) {
        return ResponseEntity.ok().body(userService.saveOrUpdate(User.builder().name(name).balance(0.).build()).getId());
    }

    @RequestMapping("get-balance")
    @GetMapping
    public ResponseEntity<BalanceDTO> getBalance(@RequestParam Long id) {
        return ResponseEntity.ok().body(BalanceDTO.builder().id(id).amount(userService.findById(id).getBalance()).build());
    }

    @RequestMapping("credit")
    @PostMapping
    public ResponseEntity<BalanceDTO> credit(@RequestParam Long id, @RequestParam Double amount) {
        return ResponseEntity.ok().body(BalanceDTO.builder().id(id).amount(userService.credit(id, amount)).build());
    }

    @RequestMapping("create-deposit")
    @PostMapping
    public ResponseEntity<Long> createDeposit(@RequestParam Long accountId, @RequestParam Double amount, @RequestParam Long depositTypeId) {
        return ResponseEntity.ok().body(depositService.save(Deposit.builder()
                .user(userService.findById(accountId))
                .amount(amount)
                .depositType(depositTypeService.findById(depositTypeId))
                .build()).getId());
    }

    @RequestMapping("trigger")
    @GetMapping
    public ResponseEntity trigger() {
        List<Deposit> deposits = depositService.findAll();
        deposits.forEach(depositService::transferInterest);
        deposits.forEach(depositService::checkCountOfTriggers);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}

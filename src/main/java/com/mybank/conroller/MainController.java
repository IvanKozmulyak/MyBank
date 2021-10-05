package com.mybank.conroller;

import com.mybank.entity.Deposit;
import com.mybank.entity.User;
import com.mybank.service.DepositService;
import com.mybank.service.DepositTypeService;
import com.mybank.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Long createAccount(@RequestParam String name) {
        return userService.saveOrUpdate(User.builder().name(name).balance(0.).build()).getId();
    }

    @RequestMapping("get-balance")
    @GetMapping
    public Double getBalance(@RequestParam Long id) {
        return userService.findById(id).getBalance();
    }

    @RequestMapping("credit")
    @PostMapping
    public Double credit(@RequestParam Long id, @RequestParam Double amount) {
        return userService.credit(id, amount);
    }

    @RequestMapping("create-deposit")
    @PostMapping
    public Long createDeposit(@RequestParam Long accountId, @RequestParam Double amount, @RequestParam Long depositTypeId) {
        return depositService.save(Deposit.builder()
                .user(userService.findById(accountId))
                .amount(amount)
                .depositType(depositTypeService.findById(depositTypeId))
                .build()).getId();
    }

    @RequestMapping("trigger")
    @GetMapping
    public void trigger() {
        List<Deposit> deposits = depositService.findAll();
        deposits.forEach(depositService::transferInterest);
        deposits.forEach(depositService::checkCountOfTriggers);
    }
}

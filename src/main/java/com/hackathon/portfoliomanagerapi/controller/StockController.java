package com.hackathon.portfoliomanagerapi.controller;

import com.hackathon.portfoliomanagerapi.exceptions.StockExistsException;
import com.hackathon.portfoliomanagerapi.model.Stock;
import com.hackathon.portfoliomanagerapi.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stocks")
public class StockController {

    @Autowired
    StockService stockService;

    @PostMapping("/addStock")
    public ResponseEntity<String> addStock(@RequestBody Stock stock) {
        try {
            System.out.println("Request to create Stock: " + stock);
            stockService.addStock(stock);
            return ResponseEntity.ok("Stock Added");
        }
        catch(StockExistsException stockExistsException) {
            return ResponseEntity.ok("Stock Already Exists");
        }
        catch(Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Could not add Stock");
        }
    }

    @GetMapping("/getAllStocks")
    public ResponseEntity<List<Stock>> getAllStocks() {
        try {
            List<Stock> stocks = stockService.getAllStocks();
            return ResponseEntity.ok(stocks);
        }
        catch(Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @GetMapping("/typeAhead")
    public ResponseEntity<List<Stock>> typeAhead(@RequestParam String q) {
        try {
            List<Stock> stocks = stockService.typeAhead(q.toLowerCase());
            return ResponseEntity.ok(stocks);
        }
        catch(Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(null);
        }
    }
}

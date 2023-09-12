package com.lmd.controller;

import com.lmd.pojo.FoodStatistic;
import com.lmd.service.StatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class ApiStatsController {
    @Autowired
    private StatsService statsService;

    @GetMapping("/restaurants/{id}/stats-food/")
    public ResponseEntity<List<Object[]>> list(@RequestParam Map<String, String> params, @PathVariable(value = "id") int id){
        return new ResponseEntity<>(this.statsService.statsRevenue(params, id), HttpStatus.OK);
    }
}

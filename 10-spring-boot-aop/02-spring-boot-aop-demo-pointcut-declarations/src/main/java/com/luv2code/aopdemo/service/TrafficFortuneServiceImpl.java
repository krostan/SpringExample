package com.luv2code.aopdemo.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class TrafficFortuneServiceImpl implements TrafficFortuneService{

    @Override
    public String getFortune() {

        // simlate adelay
        // 使應用程序休眠或延遲或暫停 5s

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // return a fortime

        return "Exception heavy traffic this morning";
    }

    @Override
    public String getFortune(boolean tripWire) {

        if(tripWire){
            throw new RuntimeException("Major accident! highway is colsed!");
        }

        return getFortune();
    }
}

package com.luv2code.aopdemo.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MyDemoLoggingAspect {

    // 這是我們添加所有相關 logging Advice 的地方

    // 從@Before Advice 開始

    // 在addAccount 這個方法開始之前 執行下面的方法

    // @Before("execution(public void addAccount())")
    // @Before("execution(public void com.luv2code.aopdemo.dao.AccountDAO.addAccount())")
    // @Before("execution(public void add*())")
    // @Before("execution(void add*())") // 一個方法返回是void 且是add或add開頭的方法名稱 都會匹配
    // @Before("execution(* add*())") // * 返回任何類型(如boolean, void, int等等) 且是add或add開頭的方法名稱 都會匹配

    // @Before("execution(* add*(com.luv2code.aopdemo.Account))") // 且 參數是要com.luv2code.aopdemo.Account的實例
    // @Before("execution(* add*(Account))") // 會發生錯誤 因沒有完全指定的Account類別
    // @Before("execution(* add*(com.luv2code.aopdemo.Account, ..))") // .. 代表匹配任意數量的參數

    // package name: com.luv2code.aopdemo.dao, className: *, methodName: *, 參數: ..
    // 匹配com.luv2code.aopdemo.dao中的任意類別中的任意方法(任何數量的參數)
    @Before("execution(* com.luv2code.aopdemo.dao.*.*(..))")

    public void beforeAddAccountAdvice(){

        System.out.println("\n=======>>> Executing @Before advice on method");

    }








}

package com.luv2code.aopdemo.aspect;

import com.luv2code.aopdemo.Account;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Aspect
@Component
@Order(2)
public class MyDemoLoggingAspect {

    @Around("execution(* com.luv2code.aopdemo.service.*.getFortune(..))")
    public Object aroundGetFortune(
            ProceedingJoinPoint theProceedingJoinPoint) throws Throwable{

        // print out method  we are advising on
        String method = theProceedingJoinPoint.getSignature().toString();
        System.out.println("\n=====>>> Executing @Around on method : " + method);

        // get begin timestamp
        long begin = System.currentTimeMillis();

        // now, let's execte the method
        // theProceedingJoinPoint 目標方法的句柄
        // proceed() 執行目標方法
        Object result = null;

        try {
            result = theProceedingJoinPoint.proceed();
        }catch(Exception exc){
            // log the exception
            System.out.println(exc.getMessage());

           // rethrow exception
            throw exc;
        }

        // get end timestamp
        long end = System.currentTimeMillis();

        // compute duration and display it
        long duration = end - begin;
        // 顯示的是毫秒 使其顯示為 秒
        System.out.println("\n=====>>> Duration : " + duration / 1000.0 + " seconds");

        return result;
    }

    @After("execution(* com.luv2code.aopdemo.dao.AccountDAO.findAccounts(..))")
    public void afterFinallyFindAccountsAdvice(JoinPoint theJoinPoint){

        // print out which method we are advising on
        String method = theJoinPoint.getSignature().toString();
        System.out.println("\n=====>>> Executing @After (finally) on method : " + method);

    }

    // 這個範例是當拋出例外後 會跳到這裡執行, 執行完後才會跳回去catch裡面執行
    @AfterThrowing(
            pointcut = "execution(* com.luv2code.aopdemo.dao.AccountDAO.findAccounts(..))",
            throwing = "theExc")
    public void afterThrowingFindAccountsAdvice(JoinPoint theJoinPoint, Throwable theExc){

        // print out which method we are advising on
        String method = theJoinPoint.getSignature().toString();
        System.out.println("\n=====>>> Executing @AfterThrowing on method : " + method);

        // log the exception
        System.out.println("\n=====>>> The exception is : " + method);

    }

    // 新增 一個新的 @AfterReturning 的 advice 給 findAccounts 方法
    // returning = "result" 和List<Account> result 的名稱要相同
    @AfterReturning(
            pointcut = "execution(* com.luv2code.aopdemo.dao.AccountDAO.findAccounts(..))",
            returning = "result")
    public void afterReturningFindAccountsAdvice(JoinPoint theJoinPoint, List<Account> result){

        // print out which method we are advicing on
        String method = theJoinPoint.getSignature().toString();
        System.out.println("\n=====>>> Executing @AfterReturning on method : " + method);

        // print out the results of the method call
        System.out.println("\n=====>>> result is : " + result);

        // let's post-process the data ... let's modify it

        // convert the account names to uppercase
        convertAccountNamesToUpperCase(result);

        System.out.println("\n=====>>> result is : " + result);

    }

    private void convertAccountNamesToUpperCase(List<Account> result) {

        // loop through accounts
        for(Account tempAccount : result) {

            // get uppercase version of name
            String theUpperName = tempAccount.getName().toUpperCase();

            // update the name on the account
            tempAccount.setName(theUpperName);
        }
    }


    // 需要為切入點提供完全限定的類別名稱 <package name> + <class name>
    // 將切入點 應用於 advice

    // JoinPoint 給了在給定時間執行的實際方法的元數據(當前執行的方法名稱和package)
    @Before("com.luv2code.aopdemo.aspect.LuvAopExpressions.forDaoPackageNoGetterSetter()")
    public void beforeAddAccountAdvice(JoinPoint theJoinPoint){

        System.out.println("\n=======>>> Executing @Before advice on method");

        // display the method signature
        MethodSignature methodsignature = (MethodSignature) theJoinPoint.getSignature();

        System.out.println("Method : " + methodsignature);

        // display method arguments

        // get args
        Object[] args = theJoinPoint.getArgs();

        // loop thru args
        for(Object tempArg : args){
            System.out.println(tempArg);

            if(tempArg instanceof Account){

                // downcast and point Account specific stuff
                Account theAccount = (Account) tempArg;

                System.out.println("account name : " +theAccount.getName());
                System.out.println("account level : " +theAccount.getLevel());
            }
        }




    }


}

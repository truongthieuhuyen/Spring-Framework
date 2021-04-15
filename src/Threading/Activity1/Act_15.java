package Threading.Activity1;

import java.util.Scanner;

public class Act_15 {
    public static void main(String[] args) {
        BankAccount bankAccount = new BankAccount();
        WithdrawThread card = new WithdrawThread(bankAccount, 2000);
        WithdrawThread atm = new WithdrawThread(bankAccount, 5000);
        card.setName("Card");
        atm.setName("ATM");
        atm.start();
        card.start();
    }

}

class BankAccount {
    private long amount = 20000;

    public void withdraw(String threadName, long withdrawAmount) {
//            Scanner sc = new Scanner(System.in);
        System.out.println(System.currentTimeMillis() + " : " + threadName + " need " + withdrawAmount + "$\n" +
                "          Your account balance is " + amount + "$");
//                System.out.println("How many you want to withdraw ?");
//                sc.nextLine();
        synchronized (this) {
            if (withdrawAmount <= amount) {
                amount = amount - withdrawAmount;
                System.out.println(System.currentTimeMillis() + " : " + threadName + " withdraw successfully");
            } else {
                System.out.println(System.currentTimeMillis() + " : " + threadName + " withdraw failed !\n          The amount in your account is not enough ");
            }
            System.out.println(System.currentTimeMillis() + " : The amount remaining in your account is " + amount + "$" + "\n- - - - - - -");
        }
    }
}

class WithdrawThread extends Thread {
    BankAccount bankAccount;
    long withdraw;

    public WithdrawThread(BankAccount bankAccount, long withdrawAmount) {
        this.bankAccount = bankAccount;
        this.withdraw = withdrawAmount;
    }

    @Override
    public void run() {
        while (true) {
            bankAccount.withdraw(Thread.currentThread().getName(), withdraw);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}


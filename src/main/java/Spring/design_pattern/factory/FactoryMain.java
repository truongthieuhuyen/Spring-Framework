package main.java.code.design_pattern.factory;

public class FactoryMain {
    public static void main(String[] args) {
        Bank tpb = BankFactory.getBank("TPB"); //new TPBank();
        System.out.println("TPB: " + tpb.getBankName());

        Bank vcb = BankFactory.getBank("VCB"); //new VietcomBank()
        System.out.println("VCB: " + vcb.getBankName());

        Bank tpb2 = new TPBank();
    }
}

public class Main {

    public static void main(String[] args) {

        ExpenseApprover teamLeader = new TeamLeaderApprover();
        ExpenseApprover manager = new ManagerApprover();
        ExpenseApprover director = new DirectorApprover();

        teamLeader.setNext(manager);
        manager.setNext(director);
        director.setNext(null);

        ExpenseApprover chain = teamLeader;

        System.out.println("=== Original chain: Team leader -> Manager -> Director ===");
        chain.approve(new ExpenseRequest("Office supplies", 30000));
        chain.approve(new ExpenseRequest("Training", 150000));
        chain.approve(new ExpenseRequest("Equipment", 800000));
        chain.approve(new ExpenseRequest("Office renovation", 1500000));

        System.out.println();
        System.out.println("=== Exact limits ===");
        chain.approve(new ExpenseRequest("Team lunch", 50000));
        chain.approve(new ExpenseRequest("Conference ticket", 200000));
        chain.approve(new ExpenseRequest("Laptops", 1000000));

        System.out.println();
        System.out.println("=== Extended chain: ... -> Director -> President ===");
        director.setNext(new PresidentApprover());

        chain.approve(new ExpenseRequest("Office supplies", 30000));
        chain.approve(new ExpenseRequest("Training", 150000));
        chain.approve(new ExpenseRequest("Equipment", 800000));
        chain.approve(new ExpenseRequest("Office renovation", 1500000));
        chain.approve(new ExpenseRequest("Office expansion", 5000000));
        chain.approve(new ExpenseRequest("New branch", 5000001));
    }
}

public class TeamLeaderApprover extends ExpenseApprover {

    private static final int LIMIT_IN_CENTS = 50000;

    @Override
    protected int getLimitInCents() {
        return LIMIT_IN_CENTS;
    }

    @Override
    protected String getName() {
        return "Team leader";
    }
}

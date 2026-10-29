public class ManagerApprover extends ExpenseApprover {

    private static final int LIMIT_IN_CENTS = 200000;

    @Override
    protected int getLimitInCents() {
        return LIMIT_IN_CENTS;
    }

    @Override
    protected String getName() {
        return "Manager";
    }
}

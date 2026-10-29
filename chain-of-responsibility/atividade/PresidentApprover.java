public class PresidentApprover extends ExpenseApprover {

    private static final int LIMIT_IN_CENTS = 5000000;

    @Override
    protected int getLimitInCents() {
        return LIMIT_IN_CENTS;
    }

    @Override
    protected String getName() {
        return "President";
    }
}

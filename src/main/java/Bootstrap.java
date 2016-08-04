import com.karlnosworthy.freeagent.FreeAgentClient;
import com.karlnosworthy.freeagent.model.FreeAgentCompany;
import com.karlnosworthy.freeagent.model.FreeAgentExpense;

import java.io.IOException;
import java.util.List;

public class Bootstrap {
    public static void main(String args[]) throws IOException {
        FreeAgentClient client = FreeAgentClient.authorise("", "v-");
        FreeAgentCompany company = client.getCompany();
        List<FreeAgentExpense> expenses = client.getExpenses();
        FreeAgentExpense expense = client.createExpense(getDummyExpense());
        System.out.println();
    }

    private static FreeAgentExpense getDummyExpense() {
        FreeAgentExpense freeAgentExpense = new FreeAgentExpense();
        freeAgentExpense.setUser("https://api.freeagent.com/v2/users/380994");
        freeAgentExpense.setGrossValue("-100");
        freeAgentExpense.setDescription("Test expense");
        freeAgentExpense.setDatedOn("2016-08-04");
        freeAgentExpense.setCategory("https://api.freeagent.com/v2/categories/285");
        return freeAgentExpense;
    }
}

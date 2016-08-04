import com.karlnosworthy.freeagent.FreeAgentClient;
import com.karlnosworthy.freeagent.model.FreeAgentCompany;
import com.karlnosworthy.freeagent.model.FreeAgentExpense;

import java.io.IOException;
import java.util.List;

public class Bootstrap {
    public static void main(String args[]) throws IOException {
        FreeAgentClient client = FreeAgentClient.authorise("", "");
        FreeAgentCompany company = client.getCompany();
        List<FreeAgentExpense> expenses = client.getExpenses();
        System.out.println();
    }
}

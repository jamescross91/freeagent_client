package com.karlnosworthy.freeagent.model.wrapper;

import com.google.gson.annotations.Expose;
import com.karlnosworthy.freeagent.model.FreeAgentExpense;

import java.util.List;

public class FreeAgentExpenseWrapper {

    @Expose
    private FreeAgentExpense expense;

    @Expose
    private List<FreeAgentExpense> expenses;

    public FreeAgentExpenseWrapper() {
        super();
    }

    public FreeAgentExpenseWrapper(FreeAgentExpense expense) {
        super();
        this.expense = expense;
    }

    public FreeAgentExpenseWrapper(List<FreeAgentExpense> expenses) {
        super();
        this.expenses = expenses;
    }

    public FreeAgentExpense getExpense(){
        return this.expense;
    }

    public List<FreeAgentExpense> getExpenses() {
        return this.expenses;
    }
}

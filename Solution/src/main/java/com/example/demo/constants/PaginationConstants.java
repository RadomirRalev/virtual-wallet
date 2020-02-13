package com.example.demo.constants;

import com.example.demo.models.transaction.Transaction;
import com.example.demo.models.user.User;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class PaginationConstants {
    public static final int ELEMENTS_PER_PAGE = 5;

    public static List<Transaction> getPaginatedResult(int page, List<Transaction> result) {
        List<Transaction> paginated = new ArrayList<>();
        int step = ELEMENTS_PER_PAGE;
        int firstResult = ((page - 1)*step);
        int lastResult = firstResult + step;
        if (lastResult > result.size()) {
            lastResult = result.size();
        }
        for (int i = firstResult; i < lastResult; i++) {
            paginated.add(result.get(i));
        }
        return paginated;
    }

    public static List<User> getPaginatedQueryResult(int page, Query<User> query) {
        query.setMaxResults(ELEMENTS_PER_PAGE);
        query.setFirstResult(((page - 1)*ELEMENTS_PER_PAGE));
        return query.list();
    }

}

package com.zorvyn.finance.service;

import com.zorvyn.finance.entity.User;
import com.zorvyn.finance.repository.RecordRepository;
import com.zorvyn.finance.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


    @Service
    public class DashboardService {

        @Autowired
        private UserRepository userRepository;

        @Autowired
        private RecordRepository recordRepository;

        public Map<String, Object> getDashboard(String email) {

            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            Double income = recordRepository.getTotalIncome(user);
            Double expense = recordRepository.getTotalExpense(user);

            List<Object[]> incomeData = recordRepository.getIncomeByCategory(user);
            List<Object[]> expenseData = recordRepository.getExpenseByCategory(user);
            List<Object[]> monthlyData = recordRepository.getMonthlySummary(user);

            Map<String, Double> incomeMap = new HashMap<>();
            for (Object[] row : incomeData) {
                incomeMap.put((String) row[0], (Double) row[1]);
            }
            Map<String, Double> expenseMap = new HashMap<>();

            for (Object[] row : expenseData) {
                expenseMap.put((String) row[0], (Double) row[1]);
            }


            Map<Integer, Double> monthlyMap = new HashMap<>();
            for (Object[] row : monthlyData) {
                monthlyMap.put((Integer) row[0], (Double) row[1]);
            }

            Map<String, Object> response = new HashMap<>();
            response.put("totalIncome", income != null ? income : 0);
            response.put("totalExpense", expense != null ? expense : 0);
            response.put("incomeByCategory", incomeMap);
            response.put("expenseByCategory", expenseMap);
            response.put("monthlySummary", monthlyMap);

            return response;
        }

}


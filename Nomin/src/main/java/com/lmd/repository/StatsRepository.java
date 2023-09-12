package com.lmd.repository;

import java.util.List;
import java.util.Map;

public interface StatsRepository {
    List<Object[]> statsRevenue(Map<String, String> params, int id);
}

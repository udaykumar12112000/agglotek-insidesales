package com.agglotek.insidesales.repository;

import com.agglotek.insidesales.dao.entity.SalesTarget;
import com.agglotek.insidesales.dao.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesTargetsRepository extends JpaRepository<SalesTarget, Integer> {

    @Query(value = """
    SELECT CASE TO_CHAR(CURRENT_DATE, 'Mon')
        WHEN 'Jan' THEN jan
        WHEN 'Feb' THEN feb
        WHEN 'Mar' THEN mar
        WHEN 'Apr' THEN apr
        WHEN 'May' THEN may
        WHEN 'Jun' THEN jun
        WHEN 'Jul' THEN jul
        WHEN 'Aug' THEN aug
        WHEN 'Sep' THEN sep
        WHEN 'Oct' THEN oct
        WHEN 'Nov' THEN nov
        WHEN 'Dec' THEN dec
    END
    FROM sales_targets
    WHERE user_id = :userId
    AND year = CAST(EXTRACT(YEAR FROM CURRENT_DATE) AS numeric)
    """, nativeQuery = true)
    int getMonthlyTarget(@Param("userId") Integer userId);

    @Query(value = """
    SELECT 
        COALESCE(jan, 0) + COALESCE(feb, 0) + COALESCE(mar, 0) + COALESCE(apr, 0) +
        COALESCE(may, 0) + COALESCE(jun, 0) + COALESCE(jul, 0) + COALESCE(aug, 0) +
        COALESCE(sep, 0) + COALESCE(oct, 0) + COALESCE(nov, 0) + COALESCE(dec, 0)
    FROM sales_targets
    WHERE user_id = :userId
      AND year = CAST(EXTRACT(YEAR FROM CURRENT_DATE) AS numeric)
    """, nativeQuery = true)
    int getYearlyTarget(Integer userId);
}

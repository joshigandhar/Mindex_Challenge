package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Compensation;
import java.util.Date;

public interface CompensationService {
    Compensation create(String id, double salary, Date effectiveDate);
    Compensation readCompensation(String id);
}

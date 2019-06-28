package com.my.spring.service;

import com.my.spring.model.PaperPointNumsLog;
import com.my.spring.utils.DataWrapper;

public interface PaperPointNumsLogService {
    DataWrapper<Void> addPaperPointNumsLog(PaperPointNumsLog building, String token);
    DataWrapper<Void> deletePaperPointNumsLog(Long id,String token);
    DataWrapper<Void> updatePaperPointNumsLog(PaperPointNumsLog building,String token);
}

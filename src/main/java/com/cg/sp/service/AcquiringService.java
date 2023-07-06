package com.cg.sp.service;

import com.cg.sp.model.request.AcquiringNonPosRequest;

public interface AcquiringService {
    Object createNonPosResponse(AcquiringNonPosRequest acquiringNonPosRequest, int pageNo, int size);

}

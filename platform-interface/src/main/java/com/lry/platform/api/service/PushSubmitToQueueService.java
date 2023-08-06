package com.lry.platform.api.service;



import com.lry.platform.common.model.Standard_Submit;

import java.util.List;

/**
 * Created by lry on 2022/7/14 09:47
 *
 * @author lry
 *
 */

public interface PushSubmitToQueueService {
    void sendSubmitToQueue(List<Standard_Submit> standard_submitList);
}

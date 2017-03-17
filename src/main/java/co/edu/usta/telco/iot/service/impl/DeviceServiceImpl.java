package co.edu.usta.telco.iot.service.impl;

import co.edu.usta.telco.iot.data.repository.DeviceRepository;
import co.edu.usta.telco.iot.service.DeviceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 */
@Service
public class DeviceServiceImpl implements DeviceService{

    private static final Logger LOG = LoggerFactory.getLogger(DeviceServiceImpl.class);

    @Autowired
    private DeviceRepository deviceRepository;





}

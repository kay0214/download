package com.sandman.download.service.system;

import com.sandman.download.dao.mysql.system.SessionDao;
import com.sandman.download.entity.system.SessionEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SessionService {
    private final Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    private SessionDao sessionDao;
    public void createSession(SessionEntity sessionEntity){
        sessionDao.createSession(sessionEntity);
    }
    public SessionEntity findSessionById(String id){
        SessionEntity sessionEntity = new SessionEntity();
        sessionEntity.setId(id);
        return sessionDao.findSessionById(sessionEntity);
    }
    public void updateSession(SessionEntity sessionEntity){
        sessionDao.updateSession(sessionEntity);
    }
    public void deleteSession(SessionEntity sessionEntity){
        sessionDao.deleteSession(sessionEntity);
    }
    public List<SessionEntity> findAll(){
        return sessionDao.findAll();
    }
}

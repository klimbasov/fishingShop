package com.jwd.fShop.service.impl;

import com.jwd.fShop.dao.TypeDao;
import com.jwd.fShop.dao.daoHolder.DaoHolder;
import com.jwd.fShop.dao.exception.DaoException;
import com.jwd.fShop.service.TypeService;
import com.jwd.fShop.service.exception.FatalServiceException;
import com.jwd.fShop.service.exception.ServiceException;
import com.jwd.fShop.service.typeHolder.TypeHolder;

import java.util.Map;

import static com.jwd.fShop.util.ExceptionMessageCreator.createExceptionMessage;

public class TypeServiceImpl implements TypeService {
    private final TypeHolder typeHolder;
    private final TypeDao typeDao;

    public TypeServiceImpl() throws FatalServiceException {
        try {
            typeHolder = new TypeHolder();
            typeDao = DaoHolder.getInstance().getTypeDao();
            Map<Integer, String> typesMap = typeDao.load();
            updateHolder(typesMap);
        } catch (DaoException exception) {
            throw new FatalServiceException(exception);
        }
    }

    private void updateHolder(Map<Integer, String> typesMap) {
        for (Map.Entry<Integer, String> entry : typesMap.entrySet()) {
            typeHolder.add(entry.getKey(), entry.getValue());
        }
    }


    @Override
    public synchronized void add(String name) throws ServiceException {
        try {
            int id = typeDao.save(name);
            typeHolder.add(id, name);
        } catch (DaoException exception) {
            throw new ServiceException(createExceptionMessage(),exception);
        }
    }

    @Override
    public Integer getId(String name) {
        return typeHolder.getId(name);
    }

    @Override
    public String getName(int id) {
        return typeHolder.getName(id);
    }

    @Override
    public String[] getNames() {
        return typeHolder.getNames();
    }
}

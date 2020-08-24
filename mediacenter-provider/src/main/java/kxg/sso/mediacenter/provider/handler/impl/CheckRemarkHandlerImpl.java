package kxg.sso.mediacenter.provider.handler.impl;

import kxg.sso.mediacenter.provider.dao.RemarkDao;
import kxg.sso.mediacenter.provider.handler.CheckRemarkHandler;
import kxg.sso.mediacenter.provider.pojo.Remark;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 要写注释呀
 */
@Component
public class CheckRemarkHandlerImpl implements CheckRemarkHandler {
    @Autowired
    private RemarkDao remarkDao;
    @Override
    public boolean findRemarkIsExist(String remark) {
        List<Remark> rm = remarkDao.findRemark(remark);
        if (CollectionUtils.isEmpty(rm)) {
            return false;
        }
        return true;
    }
}

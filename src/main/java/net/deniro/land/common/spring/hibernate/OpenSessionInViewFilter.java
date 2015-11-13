package net.deniro.land.common.spring.hibernate;

import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.orm.hibernate3.SessionFactoryUtils;

/**
 * 将Hibernate Session绑定到请求线程中
 *
 * @author deniro
 *         2015/11/13
 */
public class OpenSessionInViewFilter extends org.springframework.orm.hibernate3.support.OpenSessionInViewFilter {

    /**
     * 获取session
     *
     * @param sessionFactory
     * @return
     * @throws DataAccessResourceFailureException
     */
    protected Session getSession(SessionFactory sessionFactory) throws
            DataAccessResourceFailureException {
        Session session = SessionFactoryUtils.getSession(sessionFactory, true);
        session.setFlushMode(FlushMode.COMMIT);//解决无法进行写操作的问题
        return session;
    }

    /**
     * 关闭session
     *
     * @param session
     * @param factory
     */
    protected void closeSession(Session session, SessionFactory factory) {
        session.flush();
        super.closeSession(session, factory);
    }
}

/*
 *    Copyright 2010 The myBatis Team
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.github.dbutils.mybatis.extend;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.reflection.ExceptionUtil;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.MyBatisExceptionTranslator;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.SqlSessionUtils;
import org.springframework.dao.support.PersistenceExceptionTranslator;

/**
 * Thread safe, Spring managed, {@code SqlSession} that works with Spring
 * transaction management to ensure that that the actual SqlSession used is the
 * one associated with the current Spring transaction. In addition, it manages
 * the session life-cycle, including closing, committing or rolling back the
 * session as necessary based on the Spring transaction configuration.
 * <p>
 * The template needs a SqlSessionFactory to create SqlSessions, passed as a
 * constructor argument. It also can be constructed indicating the executor type
 * to be used, if not, the default executor type, defined in the session factory
 * will be used.
 * <p>
 * This template converts MyBatis PersistenceExceptions into unchecked
 * DataAccessExceptions, using, by default, a {@code MyBatisExceptionTranslator}.
 * <p>
 * Because SqlSessionTemplate is thread safe, a single instance can be shared
 * by all DAOs; there should also be a small memory savings by doing this. This
 * pattern can be used in Spring configuration files as follows:
 * 
 * <pre class="code">
 * {@code 
 * <bean id="sqlSessionTemplate" class="org.mybatis.spring.SqlSessionTemplate"> 
 *   <constructor-arg ref="sqlSessionFactory" /> 
 * </bean>
 * }
 * </pre>
 * 
 * @see SqlSessionFactory
 * @see MyBatisExceptionTranslator
 * @version $Id: SqlSessionTemplate.java 3406 2010-12-20 17:58:31Z eduardo.macarron $
 */
public class MybatisSqlSessionTemplate extends SqlSessionTemplate implements SqlSession {

    private final SqlSession sqlSessionProxy;
    
    public MybatisSqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        this(sqlSessionFactory, sqlSessionFactory.getConfiguration().getDefaultExecutorType());
    }
   
    public MybatisSqlSessionTemplate(SqlSessionFactory sqlSessionFactory, ExecutorType executorType) {
    	this(sqlSessionFactory, executorType, 
                new MyBatisExceptionTranslator(
                        sqlSessionFactory.getConfiguration().getEnvironment().getDataSource(), true));
    }

    public MybatisSqlSessionTemplate(SqlSessionFactory sqlSessionFactory, ExecutorType executorType,
            PersistenceExceptionTranslator exceptionTranslator) {
    	super(sqlSessionFactory, executorType, exceptionTranslator);
    	this.sqlSessionProxy = (SqlSession) Proxy.newProxyInstance(
                SqlSessionFactory.class.getClassLoader(),
                new Class[] { SqlSession.class }, 
                new SqlSessionInterceptor());
    }
    
    public Object selectOne(String statement) {
        return this.sqlSessionProxy.selectOne(statement);
    }

    public Object selectOne(String statement, Object parameter) {
        return this.sqlSessionProxy.selectOne(statement, parameter);
    }

    public Map<?, ?> selectMap(String statement, String mapKey) {
        return this.sqlSessionProxy.selectMap(statement, mapKey);
    }

    public Map<?, ?> selectMap(String statement, Object parameter, String mapKey) {
        return this.sqlSessionProxy.selectMap(statement, parameter, mapKey);
    }

    public Map<?, ?> selectMap(String statement, Object parameter, String mapKey, RowBounds rowBounds) {
        return this.sqlSessionProxy.selectMap(statement, parameter, mapKey, rowBounds);
    }

    public List<?> selectList(String statement) {
        return this.sqlSessionProxy.selectList(statement);
    }

    public List<?> selectList(String statement, Object parameter) {
        return this.sqlSessionProxy.selectList(statement, parameter);
    }

    public List<?> selectList(String statement, Object parameter, RowBounds rowBounds) {
        return this.sqlSessionProxy.selectList(statement, parameter, rowBounds);
    }

    public void select(String statement, ResultHandler handler) {
        this.sqlSessionProxy.select(statement, handler);
    }

    public void select(String statement, Object parameter, ResultHandler handler) {
        this.sqlSessionProxy.select(statement, parameter, handler);
    }

    public void select(String statement, Object parameter, RowBounds rowBounds, ResultHandler handler) {
        this.sqlSessionProxy.select(statement, parameter, rowBounds, handler);
    }

    public int insert(String statement) {
        return this.sqlSessionProxy.insert(statement);
    }

    public int insert(String statement, Object parameter) {
        return this.sqlSessionProxy.insert(statement, parameter);
    }

    public int update(String statement) {
        return this.sqlSessionProxy.update(statement);
    }

    public int update(String statement, Object parameter) {
        return this.sqlSessionProxy.update(statement, parameter);
    }

    public int delete(String statement) {
        return this.sqlSessionProxy.delete(statement);
    }

    public int delete(String statement, Object parameter) {
        return this.sqlSessionProxy.delete(statement, parameter);
    }

    public <T> T getMapper(Class<T> type) {
        return getConfiguration().getMapper(type, this);
    }

    public void commit() {
        throw new UnsupportedOperationException("Manual commit is not allowed over a Spring managed SqlSession");
    }

    public void commit(boolean force) {
        throw new UnsupportedOperationException("Manual commit is not allowed over a Spring managed SqlSession");
    }

    public void rollback() {
        throw new UnsupportedOperationException("Manual rollback is not allowed over a Spring managed SqlSession");
    }

    public void rollback(boolean force) {
        throw new UnsupportedOperationException("Manual rollback is not allowed over a Spring managed SqlSession");
    }

    public void close() {
        throw new UnsupportedOperationException("Manual close is not allowed over a Spring managed SqlSession");
    }

    public void clearCache() {
        this.sqlSessionProxy.clearCache();
    }

    public Configuration getConfiguration() {
        return this.getSqlSessionFactory().getConfiguration();
    }

    public Connection getConnection() {
        return this.sqlSessionProxy.getConnection();
    }
    
    /**
     * Proxy needed to route MyBatis method calls to the proper SqlSession got
     * from String's Transaction Manager
     * It also unwraps exceptions thrown by {@code Method#invoke(Object, Object...)} to
     * pass a {@code PersistenceException} to the {@code PersistenceExceptionTranslator}.
     */
    private class SqlSessionInterceptor implements InvocationHandler {
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            final SqlSession sqlSession = SqlSessionUtils.getSqlSession(
            		MybatisSqlSessionTemplate.this.getSqlSessionFactory(),
            		MybatisSqlSessionTemplate.this.getExecutorType(),
            		MybatisSqlSessionTemplate.this.getPersistenceExceptionTranslator());
            try {
//            	System.out.println("-----------------------" + method.getName() + "/" + sqlSession.getClass());
                Object result = method.invoke(sqlSession, args);
                if (!SqlSessionUtils.isSqlSessionTransactional(sqlSession, MybatisSqlSessionTemplate.this.getSqlSessionFactory())) {
                    sqlSession.commit();
                }
                return result;
            } catch (Throwable t) {
                Throwable unwrapped = ExceptionUtil.unwrapThrowable(t);
                if (MybatisSqlSessionTemplate.this.getPersistenceExceptionTranslator() != null && unwrapped instanceof PersistenceException) {
                    unwrapped = MybatisSqlSessionTemplate.this.getPersistenceExceptionTranslator().translateExceptionIfPossible((PersistenceException) unwrapped);
                }
                throw unwrapped;
            } finally {
                SqlSessionUtils.closeSqlSession(sqlSession, MybatisSqlSessionTemplate.this.getSqlSessionFactory());
            }
        }
    }

}

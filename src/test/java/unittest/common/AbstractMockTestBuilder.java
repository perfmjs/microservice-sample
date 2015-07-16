package unittest.common;

/**
 * mock测试工具对象创建类
 * @project webclient
 * @author tony.shen
 * @date 2010-11-22
 * Copyright (C) 2010-2012 www.xxx.com Inc. All rights reserved.
 */
public abstract class AbstractMockTestBuilder {

    /**
     * 准备测试环境及测试数据
     */
    public abstract void buildSetup();

    /**
     * 准备测试期望Given->When->Then
     */
    public abstract void buildExpectations();

    /**
     * 开始模拟运行真实情况
     */
    public abstract void buildExecutation();

    /**
     * 进行校验
     */
    public abstract void buildVerify();

    /**
     * 运行测试
     */
    public void runTest() {
        buildSetup();
        buildExpectations();
        buildExecutation();
        buildVerify();
    }

    public static class DefaultMockTestBuilder extends AbstractMockTestBuilder {
        @Override
        public void buildSetup() {
        }
        @Override
        public void buildExpectations() {
        }
        @Override
        public void buildExecutation() {
        }
        @Override
        public void buildVerify() {
        }
    }
}

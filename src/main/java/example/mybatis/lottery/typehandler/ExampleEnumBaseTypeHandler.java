package example.mybatis.lottery.typehandler;


import com.github.dbutils.mybatis.extend.EnumBaseTypeHandler;
import example.mybatis.lottery.enums.Game;
import example.mybatis.lottery.enums.IssueStatus;
import org.apache.ibatis.type.MappedTypes;


/**
 * ExampleEnumBaseTypeHandler
 * @project dal-common
 * @author tony.shen
 * @date 2012-5-27 下午5:39:32
 * Copyright (C) 2010-2012 www.xxx.com Inc. All rights reserved.
 */
@MappedTypes({IssueStatus.class, Game.class})
public class ExampleEnumBaseTypeHandler<E extends Enum<E>> extends EnumBaseTypeHandler<E> {

    public ExampleEnumBaseTypeHandler(Class<E> enumClass) {
        super(enumClass);
    }

}

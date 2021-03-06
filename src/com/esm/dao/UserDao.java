package com.esm.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.esm.core.BaseDao;
import com.esm.model.User;
import com.esm.util.PageInfo;

/**
* =======================================================================
* Overview : dao
* Business Rules :
* Comments :
* =======================================================================
* CHGNO              DATE             PROGRAMMER  DESCRIPTION
* ---------------------- ---------------------------------------
*  01			        2017-09-27             宁业春              新建
* ========================================================================.
*/
public interface UserDao extends BaseDao {
    
    /**
    根据传入的Map过滤统计数据数量.
    @param map 过滤条件
    @return 统计结果
    */
    int countByMap(@Param("map") Map map);

    
    /**
    根据传入的主键id删除数据.
    @param id 要删除数据的主键id
    @return 成功删除的数量
    */
    int deleteByPrimaryKey(Long id);

    
    /**
    根据传入的bean数据保存.
    @param record 传入的bean数据
    @return 成功保存的数量 
    */
    int insert(User record);

    
    /**
    根据传入的过滤条件以及分页数据进行分页查询.
    @param map (传入的过滤条件),PageInfo(分页数据)
    @return bean数据list
    */
    java.util.List<com.esm.model.User> queryByMap(@Param("map") Map map, @Param("page") PageInfo pageInfo);

    
    /**
    根据传入的主键id查询bean数据.
    @param id 主键id
    @return bean数据
    */
    User selectByPrimaryKey(Long id);

    
    /**
    根据传入的bean数据更新数据库数据，主键不能为空,其余为空的属性将不会更新.
    @param record bean数据
    @return  更新结果数
    */
    int updateByPrimaryKey(User record);

    
    /**
    根据传入的过滤条件查询,不分页.
    @param map 传入的过滤条件
    @return bean数据list
    */
    java.util.List<com.esm.model.User> selectByMap(@Param("map") Map map);
}
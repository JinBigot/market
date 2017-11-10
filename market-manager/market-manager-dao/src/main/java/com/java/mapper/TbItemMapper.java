package com.java.mapper;

import com.java.dto.TbItemQuery;
import com.java.model.TbItem;
import com.java.model.TbItemExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbItemMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_item
     *
     * @mbggenerated Mon Oct 23 13:37:40 CST 2017
     */
    int countByExample(TbItemExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_item
     *
     * @mbggenerated Mon Oct 23 13:37:40 CST 2017
     */
    int deleteByExample(TbItemExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_item
     *
     * @mbggenerated Mon Oct 23 13:37:40 CST 2017
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_item
     *
     * @mbggenerated Mon Oct 23 13:37:40 CST 2017
     */
    int insert(TbItem record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_item
     *
     * @mbggenerated Mon Oct 23 13:37:40 CST 2017
     */
    int insertSelective(TbItem record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_item
     *
     * @mbggenerated Mon Oct 23 13:37:40 CST 2017
     */
    List<TbItem> selectByExample(TbItemExample example);
    
    List<TbItemQuery> selectByExampleQuery(TbItemExample example);
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_item
     *
     * @mbggenerated Mon Oct 23 13:37:40 CST 2017
     */
    TbItem selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_item
     *
     * @mbggenerated Mon Oct 23 13:37:40 CST 2017
     */
    int updateByExampleSelective(@Param("record") TbItem record, @Param("example") TbItemExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_item
     *
     * @mbggenerated Mon Oct 23 13:37:40 CST 2017
     */
    int updateByExample(@Param("record") TbItem record, @Param("example") TbItemExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_item
     *
     * @mbggenerated Mon Oct 23 13:37:40 CST 2017
     */
    int updateByPrimaryKeySelective(TbItem record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_item
     *
     * @mbggenerated Mon Oct 23 13:37:40 CST 2017
     */
    int updateByPrimaryKey(TbItem record);
}
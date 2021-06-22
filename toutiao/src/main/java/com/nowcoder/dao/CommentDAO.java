package com.nowcoder.dao;

import com.nowcoder.model.Comment;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CommentDAO {
    String TABLE_NAME = "comment";
    String INSERT_FILEDS = "userid, content, created_date, entity_id, entity_type, status ";
    String SELECT_FILEDS = "id, " +  INSERT_FILEDS;

    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FILEDS,
            ") values (#{userId},#{content},#{created_date},#{entity_id},#{entity_type},#{status})"})
    int addComment(Comment comment);

    @Select({"select ", SELECT_FILEDS, " from ", TABLE_NAME, " where entity_type=#{entityType} and entity_id=#{entityId} order by id desc "})
    List<Comment> selectByEntity(@Param("entityId") int entityId, @Param("entityType") int entityType);

    @Select({"select count(id) from ", TABLE_NAME," where entity_id=#{entityId} and entity_type=#{entityType}"})
    int getCommentCount(@Param("entityId") int entityId, @Param("entityType") int entityType);
}

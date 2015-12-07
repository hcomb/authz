package eu.hcomb.authz.service.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface ProfileMapper {

	@Select("SELECT r.name FROM role_user ru, roles r where  ru.role_id = r.id AND ru.username = #{username}")
	List<String> getRolesByUser(@Param("username") String username);

}
package eu.hcomb.authz.service.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface RoleMapper {

	@Select("SELECT r.name FROM role_user ru, roles r, users u WHERE ru.role_id = r.id AND ru.user_id = u.id AND u.username = #{username}")
	List<String> getRolesByUser(@Param("username") String username);

}
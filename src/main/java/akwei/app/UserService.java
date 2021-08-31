package akwei.app;

import akwei.app.dao.UserMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Component
public class UserService {

    @Resource
    private UserMapper userMapper;

    public List<User> getUsers() {
        return this.userMapper.selectAll();
    }

    public User addUser(String name, Date createTime) {
        User user = new User();
        user.setName(name);
        user.setCreateTime(createTime);
        this.userMapper.insert(user);
        return user;
    }

    public void batchUpdateUser(List<User> users){

    }

}

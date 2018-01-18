package com.clj.startSpring;

import com.clj.bean.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@Api(value = "用户操作类",position = 1)
@RequestMapping(value = "/users")
public class UserControl {

    //创建线程安全的Map
    static Map<Long, User> users = Collections.synchronizedMap(new HashMap<Long, User>());

    // @RequestMapping(value = "/",method = RequestMethod.GET)
    @ApiOperation(value = "获取用户列表", notes = "")
    @GetMapping(value = "/getUserList")
    public List<User> getUserList() {
        // 处理"/users/"的GET请求，用来获取用户列表
        // 还可以通过@RequestParam从页面中传递参数来进行查询条件或者翻页信息的传递
        List<User> userList = new ArrayList<>(users.values());
        return userList;
    }

    // @RequestMapping(value = "/",method = RequestMethod.POST)
    @ApiOperation(value = "创建用户", notes = "根据user对象创建用户")
    @ApiImplicitParam(name = "user", value = "用户详细实体user", required = true, dataType = "User")
    @PostMapping(value = "/addUser")
    public String postUser(@ModelAttribute User user) {
        // 处理"/users/"的POST请求，用来创建User
        // 除了@ModelAttribute绑定参数之外，还可以通过@RequestParam从页面中传递参数
        users.put(user.getId(), user);
        return "success";
    }

    // @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    @ApiOperation(value = "查询User", notes = "根据id查询user")
    @ApiImplicitParam(name = "id", value = "用户id", required = true, dataType = "Long", paramType = "path")
    @GetMapping(value = "/getUserById/{id}")
    public User getUser(@PathVariable Long id) {
        // 处理"/users/{id}"的GET请求，用来获取url中id值的User信息
        // url中的id可通过@PathVariable绑定到函数的参数中
        return users.get(id);
    }

    // @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    @ApiOperation(value = "更新user信息", notes = "根据id更新User信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户id", required = true, dataType = "Long", paramType = "path"),
            @ApiImplicitParam(name = "user", value = "user信息", required = true, paramType = "body", dataType = "User")})
    @PutMapping(value = "/updateById/{id}")
    public String putUser(@PathVariable String id, @RequestBody User user) {
        // 处理"/users/{id}"的PUT请求，用来更新User信息
        User u = users.get(Long.valueOf(id));
        u.setName(user.getName());
        u.setAge(user.getAge());
        u.setId(user.getId());
        users.put(Long.valueOf(id), u);
        return "success";
    }

    // @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    @ApiOperation(value = "删除user", notes = "根据id删除user")
    @ApiImplicitParam(name = "id", value = "用户id", required = true, dataType = "Long", paramType = "path")
    @DeleteMapping(value = "/deleteById/{id}")
    public String deleteUser(@PathVariable Long id) {
        // 处理"/users/{id}"的DELETE请求，用来删除User
        users.remove(Long.valueOf(id));
        return "success";
    }
}

package com.statement.commerce.controller;

import com.statement.commerce.model.user.User;
import com.statement.commerce.service.UserService;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import static com.statement.commerce.controller.ControllerConstants.ID_PATH_VARIABLE;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Created with IntelliJ IDEA.
 * User: dedrick
 * Date: 7/10/13
 * Time: 9:58 PM
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class UserController
{
  private static final Log LOG = LogFactory.getLog(MerchantController.class);
  private static final String PREVIOUS_VERSION = "v0/";
  private static final String CURRENT_VERSION  = "v1/";

  private static final String USER = CURRENT_VERSION + "user";
  private static final String ACTIVE_USER = USER + "/active";
  private static final String USER_BY_ID = USER + "/{id}";

  @Autowired
  private UserService userService;
  /*
a.	POST – create a new user
i.	/v1/user [post body]
b.	GET – Get user by ID or the active user (currently logged in user)
i.	/v1/user/{id}
ii.	/v1/user/active
c.	PUT – Update user
i.	/v1/user/{id} [body]
d.	DELETE  - delete user
i.	/v1/user/{id}

   */

  @RequestMapping(value  = USER, method = POST)
  @ResponseBody
  public User createNewMerchant(@RequestBody User user)
  {
    userService.createNewUser(user);

    return user;
  }

  @RequestMapping(value = USER_BY_ID, method = PUT)
  @ResponseBody
  public User updateUser(@PathVariable(ID_PATH_VARIABLE) String userId, @RequestBody User user)
  {
    if(StringUtils.isEmpty(userId))
    {
      throw new IllegalArgumentException("No user id provided");
    }
    if(null == user)
    {
      throw new IllegalArgumentException("[PUT] data cannot be null");
    }

    user.setId(userId);

    userService.save(user);

    return user;
  }

  @RequestMapping(value  = USER_BY_ID, method = GET)
  @ResponseBody
  public User getUserById(@PathVariable(ID_PATH_VARIABLE) String userId)
  {
    if(StringUtils.isEmpty(userId))
    {
      throw new IllegalArgumentException("No user id provided");
    }

    List<User> users = userService.getByIds(userId);

    User user = null;

    if(!users.isEmpty())
    {
      user = users.get(0);
    }

    return user;
  }

  @RequestMapping(value  = USER_BY_ID, method = DELETE)
  @ResponseBody
  public void deleteUser(@PathVariable(ID_PATH_VARIABLE) String userId)
  {
    if(StringUtils.isEmpty(userId))
    {
      throw new IllegalArgumentException("No merchant ids provided");
    }

    userService.delete(userId);
  }
}

package com.statement.commerce.dao.elasticsearch;

import com.statement.commerce.context.AppContext;
import com.statement.commerce.context.LocalProfile;
import io.searchbox.client.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

/**
 * Created with IntelliJ IDEA.
 * User: dedrick
 * Date: 7/23/13
 * Time: 1:08 PM
 * To change this template use File | Settings | File Templates.
 */
@ActiveProfiles(profiles = { "LocalEnv" })
@ContextConfiguration(classes = { AppContext.class, LocalProfile.class })
public class JestSearchDaoIntTest extends AbstractTestNGSpringContextTests
{
  @Autowired
  private TestProductDataConfig testProductDataConfig;
  private JestClient client;

  @Test(groups = "int")
  public void test()
  {

  }

}

package com.statement.commerce.controller;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.statement.commerce.model.core.Address;
import com.statement.commerce.model.core.Merchant;
import com.statement.commerce.service.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import static com.statement.commerce.controller.ControllerConstants.ID_PATH_VARIABLE;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

/**
 * Created with IntelliJ IDEA.
 * User: dedrick
 * Date: 7/1/13
 * Time: 9:03 PM
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class MerchantController
{
  private static final Log LOG = LogFactory.getLog(MerchantController.class);
  private static final String PREVIOUS_VERSION = "v0/";
  private static final String CURRENT_VERSION  = "v1/";

  private static final String CREATE_MERCHANT = CURRENT_VERSION + "merchant";
  private static final String UPDATE_MERCHANT_ADDRESS = CURRENT_VERSION + "merchant/{id}/address";
  private static final String GET_MERCHANT_BY_ID_CURRENT = CURRENT_VERSION + "merchant/{id}";

  @Autowired
  private MerchantService merchantService;


  @RequestMapping(value  = CREATE_MERCHANT, method = POST)
  @ResponseBody
  public Merchant createNewMerchant(@RequestBody String name)
  {
    Merchant merchant = new Merchant();
    merchant.setName(name);
    merchantService.save(merchant);

    return merchant;
  }

  @RequestMapping(value  = UPDATE_MERCHANT_ADDRESS, method = PUT)
  public void saveAddress(@PathVariable(ID_PATH_VARIABLE) String merchantId, @RequestBody Address address)
  {
    if(StringUtils.isEmpty(merchantId))
    {
      throw new IllegalArgumentException("No merchant id provided");
    }

    merchantService.saveAddress(merchantId, address);
  }

  @RequestMapping(value = GET_MERCHANT_BY_ID_CURRENT, method = GET)
  public Merchant[] getMerchantById(@PathVariable(ID_PATH_VARIABLE) String merchantId)
  {
    if(StringUtils.isEmpty(merchantId))
    {
      throw new IllegalArgumentException("No merchant id provided");
    }

    List<Merchant> merchants = merchantService.getByIds(merchantId);
    return merchants.toArray(new Merchant[merchants.size()]);
  }
}

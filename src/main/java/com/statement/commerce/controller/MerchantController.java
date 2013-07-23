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
import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * Merchant controller
 */
@Controller
public class MerchantController
{
  private static final Log LOG = LogFactory.getLog(MerchantController.class);
  private static final String MERCHANT_BY_NAME = ControllerConstants.CURRENT_VERSION + "merchant/name";
  private static final String MERCHANT = ControllerConstants.CURRENT_VERSION + "merchant";
  private static final String UPDATE_MERCHANT_ADDRESS = ControllerConstants.CURRENT_VERSION + "merchant/{id}/address";
  private static final String MERCHANT_BY_ID_CURRENT = ControllerConstants.CURRENT_VERSION + "merchant/{id}";

  @Autowired
  private MerchantService merchantService;

  @RequestMapping(value  = MERCHANT, method = POST)
  @ResponseBody
  public Merchant createNewMerchant(@RequestBody Merchant merchant)
  {
    merchantService.save(merchant);

    return merchant;
  }

  @RequestMapping(value  = MERCHANT_BY_NAME, method = POST)
  @ResponseBody
  public Merchant createNewMerchantByName(@RequestBody String name)
  {
    Merchant merchant = new Merchant();
    merchant.setName(name);
    merchantService.save(merchant);

    return merchant;
  }

  @RequestMapping(value  = UPDATE_MERCHANT_ADDRESS, method = PUT)
  @ResponseBody
  public void saveAddress(@PathVariable(ID_PATH_VARIABLE) String merchantId, @RequestBody Address address)
  {
    if(StringUtils.isEmpty(merchantId))
    {
      throw new IllegalArgumentException("No merchant id provided");
    }

    merchantService.saveAddress(merchantId, address);
  }

  @RequestMapping(value = MERCHANT_BY_ID_CURRENT, method = GET)
  @ResponseBody
  public Merchant getMerchantById(@PathVariable(ID_PATH_VARIABLE) String merchantId)
  {
    if(StringUtils.isEmpty(merchantId))
    {
      throw new IllegalArgumentException("No merchant id provided");
    }

    List<Merchant> merchants = merchantService.getByIds(merchantId);

    Merchant result = null;
    if(!merchants.isEmpty())
    {
      result = merchants.get(0);
    }

    return result;
  }

  @RequestMapping(value  = MERCHANT_BY_ID_CURRENT, method = DELETE)
  @ResponseBody
  public void deleteMerchants(@PathVariable(ID_PATH_VARIABLE) String... merchantIds)
  {
    if(null == merchantIds || merchantIds.length < 1)
    {
      throw new IllegalArgumentException("No merchant ids provided");
    }

    merchantService.delete(merchantIds);
  }
}

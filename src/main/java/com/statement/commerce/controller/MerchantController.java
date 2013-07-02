package com.statement.commerce.controller;

import com.statement.commerce.model.core.Address;
import com.statement.commerce.model.core.Merchant;
import com.statement.commerce.service.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

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
  private static final String PREVIOUS_VERSION = "v0";
  private static final String CURRENT_VERSION = "v1";

  private static final String CREATE_MERCHANT = "merchant";
  private static final String UPDATE_MERCHANT_ADDRESS = "merchant/{id}/address";
  private static final String GET_MERCHANT_BY_ID_CURRENT = "merchant/{id}";

  @Autowired
  MerchantService merchantService;

  public Merchant createNewMerchant(@RequestBody String name)
  {
    Merchant merchant = new Merchant();
    merchant.setName(name);
    merchantService.save(merchant);

    return merchant;
  }

  public void saveAddress(@PathVariable("id") String merchantId, @RequestBody Address address)
  {
    merchantService.saveAddress(merchantId, address);
  }
}
